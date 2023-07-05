#!/usr/bin/bash

#AUTH_SERVICE
export GATEWAY='http://localhost:9090/'
export AUTHORIZATION="Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkB0dWhpbjQ3LmNvbSIsImF1dGhlbnRpY2F0ZWQiOnRydWUsImlhdCI6MTY4OTE4MDkzMSwiZXhwIjoxNjkwMDQ0OTMxfQ.TpbyWOALGVchu6lSABSIhMN1IURjyIrpdwHrll-ezUyroHoUFMmguY4bt4VfT0JhhxSeB66U6lMLP201ZQkw1Q"

# AUTH-SERVICE

curl ${GATEWAY}/auth/user/me -H "$AUTHORIZATION" | jq

# PRODUCT_SERVICE
curl -X POST ${GATEWAY}/product -H "accept: */*" -H "$AUTHORIZATION" -H "Content-Type: application/json" -d "{\"name\":\"Pro 1\",\"price\":10,\"quantity\":10}"

curl -X GET ${GATEWAY}/product/all -H "accept: */*" -H "$AUTHORIZATION" -H "Content-Type: application/json" -d "[]" | jq

curl -X GET ${GATEWAY}/product/excel?type=CSV -H "accept: */*" -H "$AUTHORIZATION" -H "Content-Type: application/json" --output ~/data.csv
