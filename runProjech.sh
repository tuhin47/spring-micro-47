sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose --version
sudo curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube
wget tinyurl.com/47alias -O $HOME/.bash_aliases
. $HOME/.bashrc
git clone https://github.com/tuhin47/spring-micro-47.git
cd spring-micro-47
export JAVA_HOME=/usr/lib/jvm/java-1.17.0-openjdk-amd64/
mvn clean install -DskipTests
minikube start --memory=10240 --cpus=3 --driver=docker --force
eval $(minikube -p minikube docker-env)
docker-compose -f docker-compose-prod.yml build
kubectl apply -f k8s