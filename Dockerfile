FROM openjdk:11
ADD target/docker-weather-service.jar docker-weather-service.jar
EXPOSE 8088
ENTRYPOINT ["java","jar","docker-weather-service.jar"]