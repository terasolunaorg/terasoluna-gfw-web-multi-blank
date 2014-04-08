#!/bin/sh
sh change-infra.sh MyBatis3
sed -i -e "s/terasoluna-gfw-multi-web-blank/terasoluna-gfw-multi-web-blank-mybatis3/g" pom.xml
sed -i -e "s/terasoluna-gfw-multi-web-blank/terasoluna-gfw-multi-web-blank-mybatis3/g" create-maven-archetype.sh
sed -i -e "s/TERASOLUNA Web Blank Multi Project/TERASOLUNA Web Blank Multi Project (MyBatis3)/g" pom.xml
sh create-maven-archetype.sh