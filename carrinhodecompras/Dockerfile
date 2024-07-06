FROM amazoncorretto:17-alpine
WORKDIR /app
COPY build/libs/*.jar carrinho-compras.jar
ENTRYPOINT ["java", "-jar", "carrinho-compras.jar"]