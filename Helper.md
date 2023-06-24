

### update conf file on angular

```shell
docker cp default.conf boilerplate:/etc/nginx/conf.d/default.conf && docker restart boilerplate
```
### update jar files on services
 
```shell
export PROJECT_HOME=/home/me47/IdeaProjects/spring-micro-47
export VERSION=0.1.0
#productservice 
docker cp $PROJECT_HOME/productservice/target/productservice-$VERSION.jar spring-micro-47_productservice_1:/app/app.jar && docker restart spring-micro-47_productservice_1
#apigateway
docker cp $PROJECT_HOME/apigateway/target/apigateway-$VERSION.jar spring-micro-47_apigateway_1:/app/app.jar && docker restart spring-micro-47_apigateway_1
```