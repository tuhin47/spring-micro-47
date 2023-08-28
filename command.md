
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

## Kubernetes**

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
