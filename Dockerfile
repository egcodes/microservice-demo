FROM adoptopenjdk/openjdk11:ubi
MAINTAINER egcodes
COPY target/advertisement-0.0.1-SNAPSHOT.jar advertisement-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/advertisement-0.0.1-SNAPSHOT.jar"]
