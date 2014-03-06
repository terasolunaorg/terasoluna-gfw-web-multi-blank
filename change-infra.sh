#!/bin/sh
MODE=$1
KEYWORD="REMOVE THIS LINE IF YOU USE $1"
TARGET="projectName-*"
DIRNAME=`echo $MODE | tr "[:upper:]" "[:lower:]"`

echo "change to $MODE"

rm -rf tmp
mkdir tmp
cp -r infra/$DIRNAME/* tmp/
rm -rf `/usr/bin/find tmp -name '.svn' -type d `

echo "copy infra/$DIRNAME"
#cp -rf tmp/* src/main/resources
#rm -rf tmp
cp -rf tmp/* ./


sed -i -e "/$KEYWORD/d" `grep -rIl "$1" $TARGET | grep -v '.svn'`
