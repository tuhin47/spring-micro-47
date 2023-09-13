FROM postman/newman_alpine33
WORKDIR /app
COPY . ./

ENTRYPOINT ["/bin/sh", "./RunNewman.sh"]