# splitwiseclone
This is clone of splitwise
To run this project:

mvn clean install
docker build -t splitwise/splitwiseclone .
docker run -p 8080:8080 splitwise/splitwiseclone
docker run -p 8080:8080 splitwise/splitwiseclone

Swagger docs: http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
H2 Database Console: http://localhost:8080/h2-ui/
