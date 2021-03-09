FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE=target/pruebatecnica-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} pruebatecnica-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/pruebatecnica-0.0.1-SNAPSHOT.jar"]