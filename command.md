## Clone Project

```shell
git clone https://github.com/tuhin47/spring-micro-47.git
```

## Installing docker-compose

```shell
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose --version
```

Check **.env** file , update all secrets and id based on your applicatons.

## Run docker-compose file for prod

```shell
cd spring-micro-47
mvn clean install -DskipTests
docker-compose --env-file .env -f docker-compose-prod.yml  up
```

## Monitor docker containers using lazydocker

```bash
curl https://raw.githubusercontent.com/jesseduffield/lazydocker/master/scripts/install_update_linux.sh | bash
```

## Kubernetes

- Install minikube to access this link https://minikube.sigs.k8s.io/docs/start/

- Open command prompt and install kubectl through this command shown below

```shell
minikube kubectl --
```

- Start minikube through this command shown below.

```shell
minikube start
```

- Open minikube dashboard through this command shown below.

```shell
minikube dashboard
```

- Run all images coming from Docker hub on Kubernetes through this command shown below.

```shell
kubectl apply -f k8s
```

- Show all information about images running on Kubernetes through this command

```shell
kubectl get all
```

- Show all services running on Kubernetes through this command

```shell
kubectl get services
```

- Show eureka server on Kubernetes through this command

```shell
minikube service eureka-lb
```

- Show api gateway on Kubernetes through this command

```shell
minikube service cloud-gateway-svc
```

- Copy IP address and Replace it with localhost of the endpoints defined in postman collection

**Run Postman Collection from mvn**

```shell
mvn exec:exec -Dexec.executable="npm"  -Dexec.args="install"  -pl ./
mvn exec:exec -Dexec.executable="node_modules/newman/bin/newman.js" -Dexec.workingdir="postman_collection"  -Dexec.args="run Microservice_Collection.postman_collection.json --delay-request=500 -n 2"  -pl ./
mvn clean install && mvn docker-compose:up -pl ./ -f pom.xml  && mvn exec:exec && mvn docker-compose:down -pl ./ -f pom.xml
```

## Run All Basic for development

```shell
docker-compose -f docker-compose-prod.yml -p spring-micro-47 up -d --build configserver axonserver redis database mailcatcher zipkin

ssh -L 9411:localhost:9411 \
    -L 9296:localhost:9296 \
    -L 19296:localhost:19296 \
    -L 8024:localhost:8024 \
    -L 8124:localhost:8124 \
    -L 8224:localhost:8224 \
    -L 6379:localhost:6379 \
    -L 16379:localhost:16379 \
    -L 3306:localhost:3306 \
    -L 1080:localhost:1080 \
    -L 9411:localhost:9411 \
    -L 1025:localhost:1025 \
    -L 8761:localhost:8761 \
    gp
```
