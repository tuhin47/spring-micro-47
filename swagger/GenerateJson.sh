#!/bin/sh

if [ $# -gt 0 ]; then
    API_GATEWAY=$1
else
    API_GATEWAY=http://127.0.0.1:9090
fi

wget ${API_GATEWAY}/auth/v3/api-docs -O  json/auth.json
wget ${API_GATEWAY}/order/v3/api-docs -O  json/order.json
wget ${API_GATEWAY}/payment/v3/api-docs -O  json/payment.json
wget ${API_GATEWAY}/product/v3/api-docs -O  json/product.json
