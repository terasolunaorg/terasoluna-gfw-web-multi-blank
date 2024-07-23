#!/bin/sh
set -e

LF=$(printf '\\\012_')
LF=${LF%_}
REQUIRED_PROPERTIES="${LF}  <requiredProperties>"
REQUIRED_PROPERTIES="${REQUIRED_PROPERTIES}${LF}    <requiredProperty key=\"ProjectName\">"
REQUIRED_PROPERTIES="${REQUIRED_PROPERTIES}${LF}      <defaultValue>\${artifactId.replaceAll(\"^a|-a|_a\", \"A\").replaceAll(\"^b|-b|_b\", \"B\").replaceAll(\"^c|-c|_c\", \"C\").replaceAll(\"^d|-d|_d\", \"D\").replaceAll(\"^e|-e|_e\", \"E\").replaceAll(\"^f|-f|_f\", \"F\").replaceAll(\"^g|-g|_g\", \"G\").replaceAll(\"^h|-h|_h\", \"H\").replaceAll(\"^i|-i|_i\", \"I\").replaceAll(\"^j|-j|_j\", \"J\").replaceAll(\"^k|-k|_k\", \"K\").replaceAll(\"^l|-l|_l\", \"L\").replaceAll(\"^m|-m|_m\", \"M\").replaceAll(\"^n|-n|_n\", \"N\").replaceAll(\"^o|-o|_o\", \"O\").replaceAll(\"^p|-p|_p\", \"P\").replaceAll(\"^q|-q|_q\", \"Q\").replaceAll(\"^r|-r|_r\", \"R\").replaceAll(\"^s|-s|_s\", \"S\").replaceAll(\"^t|-t|_t\", \"T\").replaceAll(\"^u|-u|_u\", \"U\").replaceAll(\"^v|-v|_v\", \"V\").replaceAll(\"^w|-w|_w\", \"W\").replaceAll(\"^x|-x|_x\", \"X\").replaceAll(\"^y|-y|_y\", \"Y\").replaceAll(\"^z|-z|_z\", \"Z\")}<\/defaultValue>"
REQUIRED_PROPERTIES="${REQUIRED_PROPERTIES}${LF}    <\/requiredProperty>"
REQUIRED_PROPERTIES="${REQUIRED_PROPERTIES}${LF}  <\/requiredProperties>"
sed -i -e "s/<\/modules>/<\/modules>${REQUIRED_PROPERTIES}/" src/main/resources/META-INF/maven/archetype-metadata.xml

echo "ProjectName=basic" >>src/test/resources/projects/basic/archetype.properties

sed -i -e "s/public class ProjectName/public class \${ProjectName}/" src/main/resources/archetype-resources/__rootArtifactId__-*/src/main/java/config/app/__ProjectName__*.java
sed -i -e "s/config\.app\.ProjectName/config\.app\.\${ProjectName}/" src/main/resources/archetype-resources/__rootArtifactId__-*/src/main/java/config/app/__ProjectName__*.java
sed -i -e "s/\(ProjectName\)\([A-Za-z]*Config\.class\)/\${ProjectName}\2/g" src/main/resources/archetype-resources/__rootArtifactId__-*/src/main/java/config/app/*.java
sed -i -e "s/\(ProjectName\)\([A-Za-z]*Config\)/\${ProjectName}\2/" src/main/resources/archetype-resources/__rootArtifactId__-*/src/test/java/config/*.java
