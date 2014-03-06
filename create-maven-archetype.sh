#!/bin/sh
#rm -rf ${HOME}/.m2/repository/xxxxxx
rm -rf ./target
rm -rf ./tmp
mkdir tmp
cp -r  pom.xml infra projectName* tmp
pushd tmp

# rename "projectName" in filename to replace by ${artifactId}
mv projectName-domain/src/main/resources/META-INF/spring/projectName-domain.xml projectName-domain/src/main/resources/META-INF/spring/__rootArtifactId__-domain.xml
mv projectName-domain/src/main/resources/META-INF/spring/projectName-infra.xml projectName-domain/src/main/resources/META-INF/spring/__rootArtifactId__-infra.xml
mv projectName-domain/src/main/resources/META-INF/spring/projectName-codelist.xml projectName-domain/src/main/resources/META-INF/spring/__rootArtifactId__-codelist.xml
mv projectName-env/src/main/resources/META-INF/spring/projectName-env.xml projectName-env/src/main/resources/META-INF/spring/__rootArtifactId__-env.xml
mv projectName-env/src/main/resources/META-INF/spring/projectName-infra.properties projectName-env/src/main/resources/META-INF/spring/__rootArtifactId__-infra.properties
rm -rf infra
rm -rf `/usr/bin/find . -name '.svn' -type d`
mvn archetype:create-from-project

pushd target/generated-sources/archetype
sed -i -e "s/xxxxxx\.yyyyyy\.zzzzzz/org.terasoluna.gfw.blank/g" pom.xml
sed -i -e "s/projectName/terasoluna-gfw-multi-web-blank/g" pom.xml
mvn deploy
