
json=$(cat ./angular-boilerplate/package.json)
previous_version=$(echo $json | jq -r '.version')
echo "Previous Version : $previous_version"

newVersion=$1

if [ -z "$newVersion" ]; then
    echo "Enter New Version:"
    read newVersion
fi

MAVEN_HOME=$HOME/.local/share/JetBrains/Toolbox/apps/intellij-idea/plugins/maven-plugin/lib/maven3/bin
npm -C ./angular-boilerplate version $newVersion
$MAVEN_HOME/mvn -B versions:set -DnewVersion=$newVersion -DgenerateBackupPoms=false

sed -i "s/^IMAGE_TAG=.*/IMAGE_TAG=$newVersion/" .env*
sed -i "s/$previous_version/$newVersion/" k8s/*.yaml
sed -i "s/<IMAGE_TAG>$previous_version<\/IMAGE_TAG>/<IMAGE_TAG>$newVersion<\/IMAGE_TAG>/" pom.xml

$MAVEN_HOME/mvn clean install