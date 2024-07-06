FROM amazoncorretto:17-alpine
WORKDIR /app
COPY build/libs/*.jar gestao-itens.jar
ENTRYPOINT ["java", "-jar", "gestao-itens.jar"]