cp mula /usr/bin/
chmod 777 /usr/bin/mula
rm -r $HOME/.mula
mkdir $HOME/.mula
mkdir $HOME/.mula/interpreters
cp -r ./interpreters/perl $HOME/.mula/interpreters
