# Usando a imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# ARG e ENV
ARG JAR_FILE=target/GlobalSolution-Java-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/

# Cria o diretório de trabalho
WORKDIR $APP_HOME

# Adiciona o JAR da aplicação
COPY $JAR_FILE app.jar

# Cria um usuário sem privilégios administrativos
RUN useradd -m appuser
USER appuser

# Adiciona o healthcheck
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s CMD curl -f http://localhost:8080/alertas/todos || exit 1

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
