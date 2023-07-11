#!/bin/sh
set -e

sh change-infra.sh JPA
sed -i -e "s/terasoluna-gfw-multi-web-blank/terasoluna-gfw-multi-web-blank-jpa/g" pom.xml
sed -i -e "s/terasoluna-gfw-multi-web-blank/terasoluna-gfw-multi-web-blank-jpa/g" create-maven-archetype.sh
sed -i -e "s/Web Blank Multi Project/Web Blank Multi Project (JPA)/g" pom.xml
sh create-maven-archetype.sh "$1" "$2"