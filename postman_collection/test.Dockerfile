FROM postman/newman:5-alpine
WORKDIR /app
COPY . ./

ENTRYPOINT ["/bin/sh", "./RunNewman.sh"]