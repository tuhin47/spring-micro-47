
```shell
export GATEWAY='http://localhost:9090'

curl ${GATEWAY}/auth/user/me -H "$AUTHORIZATION" | jq
```
#### login
```shell
curl -X POST ${GATEWAY}/auth/signin -H "accept: */*" -H "Content-Type: application/json" -d "{\"email\":\"admin@tuhin47.com\",\"password\":\"admin@\"}"
```


## PRODUCT_SERVICE
### _/product_
```sh
curl -X POST ${GATEWAY}/product -H "accept: */*" -H "$AUTHORIZATION" -H "Content-Type: application/json" -d "{\"name\":\"Pro 5\",\"price\":-1,\"quantity\":10}"
```

### _/product/all_
```shell
curl -X GET ${GATEWAY}/product/all -H "accept: */*" -H "$AUTHORIZATION" -H "Content-Type: application/json" -d "[]" | jq > /tmp/product_all.json && code /tmp/product_all.json
```
### _/product/excel
```shell
curl -X GET ${GATEWAY}/product/excel -H "accept: */*" -H "$AUTHORIZATION" --output /tmp/data.xlsx
```

### _/order/_
**create order using saga pattern**
```shell
curl -X POST "http://localhost:9090/order" -H "accept: */*" -H "$AUTHORIZATION" -H "Content-Type: application/json" -d "{\"paymentMode\":\"CASH\",\"productId\":\"2c97808a8976713c0189767557340000\",\"quantity\":1,\"totalAmount\":10}"
```


```json
{
    "Content-Type": "application/json",
    "Authorization" : "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB0dWhpbjQ3LmNvbSIsImF1dGhlbnRpY2F0ZWQiOnRydWUsImlhdCI6MTY5NDI4MDgyOSwiZXhwIjoxNjk1MTQ0ODI5fQ.wAMCHb22uiS6JrOoaegaXMv-1fY3QwDYtp5YODkytqiiQCJf8tcCwJpCiW3Xe3JvPxSiEIYhPR8EQAGG0H_XHQ"
}

```