package Args;
use Encode qw();

# Heavily based on the software
# Getopt::Mini (https://metacpan.org/pod/Getopt::Mini).

sub import {
    read_args();
    return;
}

sub read_args {
    
    my @argv;
    foreach my $argv (@ARGV) {
        push @argv, Encode::decode('UTF-8', $argv);
    }
    return () unless @argv;

    my %hash;
    my ($last_opt, $last_done);

    while (my $arg = shift @argv) {
        if ( $arg =~ m/^-+(.+)/ ) {
            $last_opt = $1;
            $last_done=0;
            if( $last_opt =~ m/^(.*)\=(.*)$/ ) {
                push @{ $hash{$1} }, $2 ;
                $last_done= 1;
            } else {
                $hash{$last_opt} = [] unless ref $hash{$last_opt};
            }
        }
        else {
            $last_opt = '' if ($last_done || ! defined $last_opt );
            push @{ $hash{$last_opt} }, $arg; 
            $last_done = 1;
        }
    }
    # convert single option => scalar
    foreach my $key ( keys %hash ) {
        next unless ref( $hash{$key} ) eq 'ARRAY';
        if( @{ $hash{$key} } == 0 ) {
            $hash{$key} = ();
        } elsif( @{ $hash{$key} } == 1 ) {
            $hash{$key} = $hash{$key}->[0];
        }
    }
    
    %ARGV = %hash;
}

1;