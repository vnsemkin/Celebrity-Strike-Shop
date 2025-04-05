# Stage 1: Build the application (builder image)
FROM gradle:8.13-jdk23-corretto AS builder

ARG BUILD_DIR=/build

ENV BUILD_DIR=${BUILD_DIR}

# Set the working directory inside the container
WORKDIR ${BUILD_DIR}

# Copy the Gradle wrapper and configuration files
COPY build.gradle settings.gradle gradlew gradlew.bat ${BUILD_DIR}/
COPY gradle ${BUILD_DIR}/gradle
# Copy the source code
COPY src ${BUILD_DIR}/src

# Run the Gradle build
RUN gradle bootJar

# Stage 2: Create the runtime image
FROM openjdk:23-slim AS build

ARG BUILD_DIR=/build
ENV BUILD_DIR=${BUILD_DIR}
ARG HOME=/app
ENV APP_HOME=${HOME}
# Set the working directory for the runtime
WORKDIR ${BUILD_DIR}

# The application's jar file (copied from the builder stage)
ARG JAR_FILE=${BUILD_DIR}/build/libs/*.jar

# Copy the jar file from the builder image
COPY --from=builder ${JAR_FILE} ${APP_HOME}/app.jar

# Unpackage the jar file
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ${APP_HOME}/app.jar)

# Stage 3: Final runtime image
FROM openjdk:23-slim

ARG BUILD_DIR=/build
ENV BUILD_DIR=${BUILD_DIR}
ARG HOME=/app
ENV APP_HOME=${HOME}
# Set working directory
WORKDIR ${APP_HOME}

# Add volume pointing to /tmp
VOLUME /tmp

# Copy the unpackaged application files to the final container
ARG DEPENDENCY=${BUILD_DIR}/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Expose the application port
EXPOSE 18080

# Run the jar file
CMD ["java", "-cp", ".:lib/*", "org.duckdns.celebritystrike.celebritystrike.CelebrityStrikeApplication"]