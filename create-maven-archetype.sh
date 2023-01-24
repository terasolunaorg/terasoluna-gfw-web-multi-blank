#!/bin/sh
CONFIG=$1
VIEW=$2
ORM=$3
DEPRLOY=$4
REPOSITORY=$5
if [ "$CONFIG" = "JavaConfig" ]; then
  ARTIFACT_ID=terasoluna-gfw-multi-web-blank-${VIEW,,}-${ORM,,}
else
  ARTIFACT_ID=terasoluna-gfw-multi-web-blank-${CONFIG,,}-${VIEW,,}-${ORM,,}
fi
echo create $ARTIFACT_ID

# start create tmp directory ###################
#rm -rf ./target
rm -rf ./tmp
mkdir tmp
cp -r  parts/base/pom.xml tmp
cp -r  parts/base/projectName-* tmp

cp -r  parts/$CONFIG/projectName-* tmp

cp -r  parts/UseORM/projectName-env tmp

cp -r  parts/$CONFIG-$ORM/projectName-domain tmp
if [ $ORM = "MyBatis3" ]; then
  cp -r  parts/$ORM/projectName-domain tmp
fi

cp -r  parts/$CONFIG-$VIEW/projectName-web tmp
cp -r  parts/$VIEW/projectName-web tmp
# end create tmp directory ###################

# start work at tmp ###################
pushd tmp

sed -i -e "s/Web Blank Multi Project/Web Blank Multi Project (${CONFIG})(${VIEW})(${ORM})/g" pom.xml

# rename "projectName" in filename to replace by ${artifactId}
if [ "$CONFIG" = "XMLConfig" ]; then
  mv projectName-domain/src/main/resources/META-INF/spring/projectName-domain.xml projectName-domain/src/main/resources/META-INF/spring/__rootArtifactId__-domain.xml
  mv projectName-domain/src/main/resources/META-INF/spring/projectName-infra.xml projectName-domain/src/main/resources/META-INF/spring/__rootArtifactId__-infra.xml
  mv projectName-domain/src/main/resources/META-INF/spring/projectName-codelist.xml projectName-domain/src/main/resources/META-INF/spring/__rootArtifactId__-codelist.xml
  mv projectName-env/src/main/resources/META-INF/spring/projectName-env.xml projectName-env/src/main/resources/META-INF/spring/__rootArtifactId__-env.xml
  mv projectName-env/src/main/resources/META-INF/spring/projectName-infra.properties projectName-env/src/main/resources/META-INF/spring/__rootArtifactId__-infra.properties
else
  mv projectName-domain/src/main/java/xxxxxx/yyyyyy/zzzzzz/config/app/ProjectNameCodeListConfig.java projectName-domain/src/main/java/xxxxxx/yyyyyy/zzzzzz/config/app/__ProjectName__CodeListConfig.java
  mv projectName-domain/src/main/java/xxxxxx/yyyyyy/zzzzzz/config/app/ProjectNameDomainConfig.java projectName-domain/src/main/java/xxxxxx/yyyyyy/zzzzzz/config/app/__ProjectName__DomainConfig.java
  mv projectName-domain/src/main/java/xxxxxx/yyyyyy/zzzzzz/config/app/ProjectNameInfraConfig.java projectName-domain/src/main/java/xxxxxx/yyyyyy/zzzzzz/config/app/__ProjectName__InfraConfig.java
  mv projectName-env/src/main/java/xxxxxx/yyyyyy/zzzzzz/config/app/ProjectNameEnvConfig.java projectName-env/src/main/java/xxxxxx/yyyyyy/zzzzzz/config/app/__ProjectName__EnvConfig.java
fi

if [ -d projectName-domain/src/main/resources/xxxxxx ];then
  echo "rename to __packageInPathFormat__"
  mkdir -p projectName-domain/src/main/resources/__packageInPathFormat__
  mv projectName-domain/src/main/resources/xxxxxx/yyyyyy/zzzzzz/domain projectName-domain/src/main/resources/__packageInPathFormat__/
  rm -rf projectName-domain/src/main/resources/xxxxxx
fi

sed -i -e "/REMOVE THIS LINE IF YOU USE $ORM/d" `grep -rIl $ORM projectName-* | grep -v '.svn'`
sed -i -e "s/REMOVE THIS COMMENT IF YOU USE $ORM//g" `grep -rIl $ORM projectName-* | grep -v '.svn'`

rm -rf `/usr/bin/find . -name '.svn' -type d`

if [ "$REPOSITORY" = "central" ]; then
  PROFILE="-P central"
fi
mvn archetype:create-from-project ${PROFILE}
# end work at tmp ###################

# start work at tmp target/generated-sources/archetype ###################
pushd target/generated-sources/archetype

sed -i -e "s/xxxxxx\.yyyyyy\.zzzzzz/org.terasoluna.gfw.blank/g" pom.xml
sed -i -e "s/projectName/${ARTIFACT_ID}/g" pom.xml

if [ "$REPOSITORY" = "central" ]; then
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

# convert config classes to Camel
if [ "$CONFIG" = "JavaConfig" ]; then
  sh ../../../../convert-camelclass.sh
fi

if [ "$DEPLOY" = "deploy" ]; then
  mvn deploy
else
  mvn install
fi
# end work at tmp target/generated-sources/archetype ###################

popd
popd
