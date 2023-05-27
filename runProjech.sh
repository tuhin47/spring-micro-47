sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose --version
git clone https://github.com/tuhin47/spring-micro-47.git
cd spring-micro-47
chmod +x **/mvnw
docker-compose --env-file .env -f docker-compose-prod.yml  up
