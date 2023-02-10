#!/bin/sh

DEPLOY=$1
REPOSITORY=$2

#sh create-maven-archetype-javaconfig-jsp-jpa.sh "$DEPLOY" "$REPOSITORY"
#sh create-maven-archetype-javaconfig-jsp-mybatis3.sh "$DEPLOY" "$REPOSITORY"
#sh create-maven-archetype-javaconfig-thymeleaf-jpa.sh "$DEPLOY" "$REPOSITORY"
#sh create-maven-archetype-javaconfig-thymeleaf-mybatis3.sh "$DEPLOY" "$REPOSITORY"
sh create-maven-archetype-xmlconfig-jsp-jpa.sh "$DEPLOY" "$REPOSITORY"
sh create-maven-archetype-xmlconfig-jsp-mybatis3.sh "$DEPLOY" "$REPOSITORY"
#sh create-maven-archetype-xmlconfig-thymeleaf-jpa.sh "$DEPLOY" "$REPOSITORY"
#sh create-maven-archetype-xmlconfig-thymeleaf-mybatis3.sh "$DEPLOY" "$REPOSITORY"