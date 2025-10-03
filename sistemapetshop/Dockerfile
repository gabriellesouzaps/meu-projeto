# Use JDK oficial
FROM openjdk:17-jdk-slim

# Copia o jar para dentro do container
COPY target/seu-projeto.jar app.jar

# Expõe a porta que o Render vai usar
ENV PORT 10000
EXPOSE 10000

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
