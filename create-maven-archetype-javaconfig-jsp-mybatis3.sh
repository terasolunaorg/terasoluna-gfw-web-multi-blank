#!/bin/sh
set -e

CONFIG=JavaConfig
VIEW=JSP
ORM=MyBatis3
DEPLOY=$1
REPOSITORY=$2

sh create-maven-archetype.sh $CONFIG $VIEW $ORM "$DEPLOY" "$REPOSITORY"