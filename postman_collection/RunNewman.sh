set -e;
onExit() {
    exitCode=$?
    echo  "Exit Code ==> "$exitCode
    if [ $exitCode != "0" ]; then
        echo "Tests failed"
        # build failed, don't deploy
        exit 1
    else
        echo "Tests passed"
        # deploy build
    fi
}

trap onExit EXIT
# --bail
newman run Microservice_Collection.postman_collection.json --environment=env.docker.json --reporters=cli,junit --iteration-count=2