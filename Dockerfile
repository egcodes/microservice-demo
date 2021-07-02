FROM adoptopenjdk/openjdk11:ubi
MAINTAINER sahibinden.com
COPY target/advertisement-0.0.1-SNAPSHOT.jar advertisement-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/advertisement-0.0.1-SNAPSHOT.jar"]