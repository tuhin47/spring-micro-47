
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