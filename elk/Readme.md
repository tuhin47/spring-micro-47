### Using of ELK in this project

Here docker-compose in being used for elk. Use this command to test elk functionality here

docker for elastic search has some [issues](https://stackoverflow.com/a/51448773/7499069) please follow this

```shell
sudo sysctl -w vm.max_map_count=262144
docker-compose -f docker-compose-dev.yml --env-file .env --profile elk  up
```

visit http://localhost:5601/

