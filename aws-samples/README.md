# Initialization

```shell
docker exec -it localstack /docker-entrypoint-initaws.d/init-s3-bucket.sh \
&& docker exec -it localstack /docker-entrypoint-initaws.d/init-sns-sqs.sh
```

# Upload

```shell
bash
curl --location --request POST 'localhost:8080/s3/upload' \
--header 'Content-Type: multipart/form-data' \
--form 'files=@"/home/towhidul/IdeaProjects/spring-micro-47/README.md"'
```

# References

- Spring WebFlux AWS project for [AWS Dandelion Tutorials](https://medium.com/dandelion-tutorials/aws/home)
