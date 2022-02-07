# splitwiseclone
This is clone of splitwise
<br/>To run this project:

<br/>mvn clean install
<br/>docker build -t splitwise/splitwiseclone .
<br/>docker run -p 8080:8080 splitwise/splitwiseclone
<br/>docker run -p 8080:8080 splitwise/splitwiseclone

<br/>Swagger docs: http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
<br/>H2 Database Console: http://localhost:8080/h2-ui/
