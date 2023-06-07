set -e
#!/bin/sh
CONFIG=XMLConfig
VIEW=Thymeleaf
ORM=MyBatis3
DEPLOY=$1
REPOSITORY=$2

sh create-maven-archetype.sh $CONFIG $VIEW $ORM "$DEPLOY" "$REPOSITORY"
