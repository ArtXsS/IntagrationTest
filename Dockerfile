FROM openjdk:21
EXPOSE 8081
ADD target/IntagrationTest-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar", "myapp.jar"]