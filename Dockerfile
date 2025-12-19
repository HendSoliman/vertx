# -----------------------------------------
# Runtime image (Java 21)

# docker build -t my-vertx-app:latest .
# docker run --rm -p 8888:8888 my-vertx-app:latest
# -----------------------------------------
FROM eclipse-temurin:21-jre
FROM adoptopenjdk:11-jre-hotspot

ENV APP_HOME=/usr/app
ENV APP_JAR=vertx-starter-1.0.0-SNAPSHOT-fat.jar

WORKDIR ${APP_HOME}
EXPOSE 8888

# Copy fat jar produced by Maven
COPY target/${APP_JAR} ${APP_JAR}

# Run Vert.x app
CMD ["java", "-jar", "vertx-starter-1.0.0-SNAPSHOT-fat.jar"]
