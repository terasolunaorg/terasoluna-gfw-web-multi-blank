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

if [ -d projectName-domain/src/main/resources/xxxxxx ];then
  echo "rename to __packageInPathFormat__"
  mkdir -p projectName-domain/src/main/resources/__packageInPathFormat__
  mv projectName-domain/src/main/resources/xxxxxx/yyyyyy/zzzzzz/domain projectName-domain/src/main/resources/__packageInPathFormat__/
  rm -rf projectName-domain/src/main/resources/xxxxxx
fi

rm -rf infra
rm -rf `/usr/bin/find . -name '.svn' -type d`

if [ "$1" = "central" ]; then
  profile="-P central"
fi
mvn archetype:create-from-project ${profile}

pushd target/generated-sources/archetype
sed -i -e "s/xxxxxx\.yyyyyy\.zzzzzz/org.terasoluna.gfw.blank/g" pom.xml
sed -i -e "s/projectName/terasoluna-gfw-multi-web-blank/g" pom.xml

if [ "$1" = "central" ]; then
  # add plugins to deploy to Maven Central Repository
  LF=$(printf '\\\012_')
  LF=${LF%_}
  
  REPLACEMENT_TAG="    <plugins>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}      <plugin>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <groupId>org.sonatype.plugins<\/groupId>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <artifactId>nexus-staging-maven-plugin<\/artifactId>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <version>1.6.7<\/version>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <extensions>true<\/extensions>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <configuration>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}          <serverId>ossrh<\/serverId>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}          <nexusUrl>https:\/\/oss.sonatype.org\/<\/nexusUrl>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}          <autoReleaseAfterClose>true<\/autoReleaseAfterClose>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <\/configuration>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}      <\/plugin>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}      <plugin>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <groupId>org.apache.maven.plugins<\/groupId>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <artifactId>maven-gpg-plugin<\/artifactId>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <version>1.6<\/version>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <executions>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}          <execution>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}            <id>sign-artifacts<\/id>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}            <phase>verify<\/phase>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}            <goals>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}              <goal>sign<\/goal>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}            <\/goals>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}          <\/execution>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <\/executions>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}      <\/plugin>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}    <\/plugins>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}  <\/build>"
  
  sed -i -e "s/  <\/build>/${REPLACEMENT_TAG}/" pom.xml
fi
mvn deploy

popd
popd
