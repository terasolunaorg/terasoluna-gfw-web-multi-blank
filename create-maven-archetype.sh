#!/bin/sh
set -e

DEPLOY=$1
REPOSITORY=$2
ORM=$3

if [ -z ${ORM} ];then
  echo ORM is not empty
  exit 1
fi

KEYWORD="REMOVE THIS LINE IF YOU USE ${ORM}"
TARGET="projectName-*"

ARTIFACT_ID=terasoluna-gfw-multi-web-blank-${ORM,,}

echo create ${ARTIFACT_ID}

# start create tmp directory
rm -rf ./tmp
mkdir tmp
cp -r pom.xml tmp
cp -r projectName* tmp

cp -r infra/${ORM,,}/* tmp/

pushd tmp

sed -i -e "/${KEYWORD}/d" `grep -rIl "${ORM}" ${TARGET}`

sed -i -e "s/Web Blank Multi Project/Web Blank Multi Project (${ORM})/g" pom.xml


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

if [ "${REPOSITORY}" = "central" ]; then
  PROFILE="-P central"
fi
mvn archetype:create-from-project ${PROFILE}

pushd target/generated-sources/archetype

sed -i -e "s/xxxxxx\.yyyyyy\.zzzzzz/org.terasoluna.gfw.blank/g" pom.xml
sed -i -e "s/projectName/${ARTIFACT_ID}/g" pom.xml

if [ "${REPOSITORY}" = "central" ]; then
  # add plugins to deploy to Maven Central Repository
  LF=$(printf '\\\012_')
  LF=${LF%_}
  
  REPLACEMENT_TAG="    <plugins>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}      <plugin>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <groupId>org.sonatype.plugins<\/groupId>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <artifactId>nexus-staging-maven-plugin<\/artifactId>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <version>1.6.8<\/version>${LF}"
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
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <version>3.0.1<\/version>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <executions>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}          <execution>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}            <id>sign-artifacts<\/id>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}            <phase>verify<\/phase>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}            <goals>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}              <goal>sign<\/goal>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}            <\/goals>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}            <configuration>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}              <gpgArguments>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}                <arg>--pinentry-mode<\/arg>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}                <arg>loopback<\/arg>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}              <\/gpgArguments>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}            <\/configuration>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}          <\/execution>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}        <\/executions>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}      <\/plugin>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}    <\/plugins>${LF}"
  REPLACEMENT_TAG="${REPLACEMENT_TAG}  <\/build>"
  
  sed -i -e "s/  <\/build>/${REPLACEMENT_TAG}/" pom.xml
fi

if [ "${DEPLOY}" = "deploy" ]; then
  mvn deploy -X
else
  mvn install
fi

popd
popd
