## Installing docker-compose

```shell
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose --version
```

```shell
chmod +x **/mvnw
```

## Run docker file 
```shell
docker-compose --env-file .env -f docker-compose-prod.yml  up
```

## lazydocker

```shell
go install github.com/jesseduffield/lazydocker@latest
```
