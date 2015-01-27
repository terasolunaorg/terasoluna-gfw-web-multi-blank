#!/bin/sh
sh change-infra.sh MyBatis2
sed -i -e "s/terasoluna-gfw-multi-web-blank/terasoluna-gfw-multi-web-blank-mybatis2/g" pom.xml
sed -i -e "s/terasoluna-gfw-multi-web-blank/terasoluna-gfw-multi-web-blank-mybatis2/g" create-maven-archetype.sh
sed -i -e "s/Web Blank Multi Project/Web Blank Multi Project (MyBatis2)/g" pom.xml
sh create-maven-archetype.sh