
json=$(cat ./angular-boilerplate/package.json)
previous_version=$(echo $json | jq -r '.version')
echo "Previous Version : $previous_version"

newVersion=$1

if [ -z "$newVersion" ]; then
    echo "Enter New Version:"
    read newVersion
fi

npm -C ./angular-boilerplate version $newVersion
/opt/apache-maven-3.6.0/bin/mvn -B versions:set -DnewVersion=$newVersion -DgenerateBackupPoms=false

sed -i "s/^IMAGE_TAG=.*/IMAGE_TAG=$newVersion/" .env*
sed -i "s/$previous_version/$newVersion/" k8s/*.yaml
sed -i "s/<IMAGE_TAG>$previous_version<\/IMAGE_TAG>/<IMAGE_TAG>$newVersion<\/IMAGE_TAG>/" pom.xml

export JAVA_HOME=$HOME/.jdks/corretto-17.0.10
/opt/apache-maven-3.6.0/bin/mvn clean install