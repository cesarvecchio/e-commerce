FROM amazoncorretto:17-alpine
WORKDIR /app
COPY build/libs/*.jar ms-user.jar
ENTRYPOINT ["java", "-jar", "ms-user.jar"]