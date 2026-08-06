sdk use java 21.0.03-tem

cd ../discovery-server
mvn clean package -DskipTests
cd ../connections-service
mvn clean package -DskipTests
cd ../notification-service
mvn clean package -DskipTests
cd ../posts-service
mvn clean package -DskipTests
cd ../user-service
mvn clean package -DskipTests