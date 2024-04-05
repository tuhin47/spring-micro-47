# Upload

```shell
curl --location --request POST 'localhost:8080/s3/upload' \
--header 'Content-Type: multipart/form-data' \
--form 'files=@"/home/towhidul/IdeaProjects/spring-micro-47/README.md"'
```

# References

- Spring WebFlux AWS project for [AWS Dandelion Tutorials](https://medium.com/dandelion-tutorials/aws/home)
