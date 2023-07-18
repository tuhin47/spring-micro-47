
```shell
export GATEWAY='http://localhost:9090'
export AUTHORIZATION="Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB0dWhpbjQ3LmNvbSIsImF1dGhlbnRpY2F0ZWQiOnRydWUsImlhdCI6MTY4OTcwMDY1OSwiZXhwIjoxNjkwNTY0NjU5fQ.wfJ6JbodAlO-1YNMXH3lkmaZpNvvEccvrirJuozj2k6ipt3_d7kl8d7QiIB61AWAZqZ1bFklKwnIIM1g_8f7cA"
```

## AUTH_SERVICE
```sh
curl ${GATEWAY}/auth/user/me -H "$AUTHORIZATION" | jq
```

## PRODUCT_SERVICE
### _/product_
```sh
curl -X POST ${GATEWAY}/product -H "accept: */*" -H "$AUTHORIZATION" -H "Content-Type: application/json" -d "{\"name\":\"Pro 1\",\"price\":10,\"quantity\":10}"
```

### _/product/all_
```shell
curl -X GET ${GATEWAY}/product/all -H "accept: */*" -H "$AUTHORIZATION" -H "Content-Type: application/json" -d "[]" | jq
```
### _/product/excel
```shell
curl -X GET ${GATEWAY}/product/excel -H "accept: */*" -H "$AUTHORIZATION" --output /tmp/data.xlsx
```
