FROM openjdk:21-slim

WORKDIR /app

COPY target/sistemapetshop-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8088

CMD ["java", "-jar", "app.jar"]