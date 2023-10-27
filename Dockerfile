FROM eclipse-temurin:17-jdk-alpine
COPY target/order.jar order.jar
ENTRYPOINT ["java","-jar","order.jar"]