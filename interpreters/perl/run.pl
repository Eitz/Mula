#!/usr/bin/env perl

my $usage = q{
TITLE

	Mula - Multi language interpreter
	Perl interpreter

USAGE

	perl run.pl --code 'your perl code comes here'

	single quotes inside the code must be converted to -> '\''
	\n should be changed to MULA_LINE_BREAK

RETURN

	Prints to STDOUT this way:

		MULA_OUT:'code returned from eval'
		MULA_ERR:'error from the eval'

EXAMPLES

	./interpreters/perl/run.pl --MULA_CODE 'my $total = 0;MULA_LINE_BREAKfor (my $i = 0; $i < 50; $i++) {MULA_LINE_BREAK$total += $i+1;MULA_LINE_BREAK}MULA_LINE_BREAKreturn $total;';
	./interpreters/perl/run.pl --test '23' --MULA_CODE 'my $t = Mula->getParam("test");MULA_LINE_BREAKreturn $t+10;';
	

};

# INCLUDES

use utf8;
use strict;
use warnings;
use FindBin;

use lib "$FindBin::Bin/lib";
use Args;

# MAIN

if ($ARGV{MULA_CODE}) {

	my $code = delete $ARGV{MULA_CODE};
	
	my ($output, $error) = interpretCode(resetLines($code), \%ARGV);
	
	if (defined $error) {
		print "MULA_ERR:'$error'\n";
	}
	if (defined $output) {
		print "MULA_OUT:'$output'\n";		
	}
} else {
	print "$usage\n";
}

sub resetLines {
	my $str = shift;
	$str =~ s/MULA_LINE_BREAK/\n/g;
	$str =~ s/^\n//g;
	return $str;
}

sub interpretCode {
	my $code = shift;
	my $args = shift;

	# saving args in Mula
	$Mula::args = \%ARGV;

	# error gets stored in $@ after eval is runt
	my $output 	= scapeSingleQuotes(eval "$code");
	my $error 	= editError($@) || undef;
	
	return ($output, $error);
}

sub editError {
	my $error = shift;
	$error = scapeSingleQuotes($error);

	if ($error =~ /Mula Perl/) {
		$error =~ s/\s*at(.|\s)*$//;
	} else {
		$error =~ s/ at \(eval 5\) /, /g;
		$error =~ s/\n+$//;
	}

	return $error ? "Perl: $error" : "";
}

sub scapeSingleQuotes {
	my $str = shift;
	if ($str) {
		$str =~ s/\'/\\'/g;
	}
	return $str;
}

# Mula

package Mula;

our $args;

sub getParam {
	my (undef, $key) = @_;
	if (!defined $args->{$key}) {
		die "Perl: [Mula] The \"$key\" parameter was not declared.";
	}
	return $args->{$key};
}