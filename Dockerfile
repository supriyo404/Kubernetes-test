FROM openjdk:8-jre-slim
VOLUME /tmp
EXPOSE 8080
ADD target/storage-service-0.0.1-SNAPSHOT.jar storage-service-0.0.1-SNAPSHOT.jar
CMD 'bash -c touch /storage-service-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/storage-service-0.0.1-SNAPSHOT.jar"]