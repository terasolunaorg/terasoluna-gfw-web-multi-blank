#!/bin/sh
set -e

DEPLOY=$1
REPOSITORY=$2
ORM=MyBatis3

sh create-maven-archetype.sh "$DEPLOY" "$REPOSITORY" "$ORM"
