#!/bin/sh
set -e

DEPLOY=$1
REPOSITORY=$2
ORM=JPA

sh create-maven-archetype.sh "$DEPLOY" "$REPOSITORY" "$ORM"
