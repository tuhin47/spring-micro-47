#!/bin/bash
# -- > Create S3 Bucket
echo $(awslocal s3 mb s3://spring47-s3)
# --> List S3 Buckets
echo $(awslocal s3 ls)