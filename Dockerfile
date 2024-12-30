# syntax=docker/dockerfile:1

# Create a stage for resolving and downloading dependencies.
FROM eclipse-temurin:21-jdk-jammy as deps

WORKDIR /build

# Copy the mvnw wrapper with executable permissions.
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/

# Download dependencies as a separate step to take advantage of Docker's caching.
# Leverage a cache mount to /root/.m2 so that subsequent builds don't have to
# re-download packages.
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,id=maven-cache-key,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests

################################################################################

# Create a stage for building the application based on the stage with downloaded dependencies.
FROM deps as package

WORKDIR /build

COPY ./src src/
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,id=maven-cache-key,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

################################################################################

# Create a stage for extracting the application into separate layers.
FROM package as extract

WORKDIR /build

RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

################################################################################

# Create a new stage for running the application.
FROM eclipse-temurin:21-jre-jammy AS final

ARG UID=10001
RUN adduser --disabled-password --gecos "" --home "/nonexistent" --shell "/sbin/nologin" --no-create-home --uid "${UID}" appuser
USER appuser

COPY --from=extract build/target/extracted/dependencies/ ./ 
COPY --from=extract build/target/extracted/spring-boot-loader/ ./ 
COPY --from=extract build/target/extracted/snapshot-dependencies/ ./ 
COPY --from=extract build/target/extracted/application/ ./ 

EXPOSE 8080

# Enable Docker BuildKit
# syntax=docker/dockerfile:1.0.0-experimental
FROM openjdk:11-jre-slim

RUN --mount=type=cache,id=maven-cache-key,target=/root/.m2 \
    mkdir -p /root/.m2 \
    && curl -sSL https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.8.4/apache-maven-3.8.4-bin.tar.gz | tar -xz -C /root/.m2

COPY target/your-app-name.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ]
