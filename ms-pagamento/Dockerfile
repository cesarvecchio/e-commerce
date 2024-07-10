FROM amazoncorretto:17-alpine
WORKDIR /app
COPY build/libs/*.jar pagamento.jar
ENTRYPOINT ["java", "-jar", "pagamento.jar"]