set -e;
onExit() {
    echo  "Exit Code ==> "$?

    # shellcheck disable=SC2181
    if [ "$?" != "0" ]; then
        echo "Tests failed"
        # build failed, don't deploy
        exit 1
    else
        echo "Tests passed"
        # deploy build
    fi
}

trap onExit EXIT
#--iteration-count=1 --bail
newman run Microservice_Collection.postman_collection.json --delay-request=500 --environment=env.docker.json --reporters=cli,junit