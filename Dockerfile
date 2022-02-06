FROM amazoncorretto:11-alpine-jdk
MAINTAINER bbhasin
COPY target/splitwise-1.0.0.jar splitwise-1.0.0.jar
ENTRYPOINT ["java","-jar","/splitwise-1.0.0.jar"]