#!/bin/sh

DEPLOY=$1
REPOSITORY=$2

sh create-maven-archetype-xmlconfig-jsp-jpa.sh "$DEPLOY" "$REPOSITORY"
sh create-maven-archetype-xmlconfig-jsp-mybatis3.sh "$DEPLOY" "$REPOSITORY"