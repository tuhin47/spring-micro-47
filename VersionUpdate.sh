
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

sed -i "s/^PROD_IMAGE_TAG=.*/PROD_IMAGE_TAG=$newVersion/" .env*
sed -i "s/$previous_version/$newVersion/" k8s/*.yaml
sed -i "s/<PROD_IMAGE_TAG>$previous_version<\/PROD_IMAGE_TAG>/<PROD_IMAGE_TAG>$newVersion<\/PROD_IMAGE_TAG>/" pom.xml
