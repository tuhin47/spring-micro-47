# Initialization

```shell
docker exec -it localstack 'sh /docker-entrypoint-initaws.d/init-s3-bucket.sh' \
&& docker exec -it localstack 'sh /docker-entrypoint-initaws.d/init-sns-sqs.sh'
```

# Upload

```shell
bash
curl --location --request POST 'localhost:8080/s3/upload-large?sessionId=2' \
--header 'Content-Type: multipart/form-data' \
--form 'files=@"/tmp/README.md"'
```

# References

- Spring WebFlux AWS project for [AWS Dandelion Tutorials](https://medium.com/dandelion-tutorials/aws/home)
