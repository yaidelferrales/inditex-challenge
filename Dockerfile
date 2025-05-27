# ------------------------------------
# 1. Build Stage with SDKMAN Cached
# ------------------------------------
FROM bitnami/minideb AS sdkman-java-maven

ENV SDKMAN_DIR="/root/.sdkman"
SHELL ["/bin/bash", "-c"]
ENV BASH_ENV="${SDKMAN_DIR}/bin/sdkman-init.sh"

# Install curl, unzip, zip, bash
RUN apt-get update && \
    apt-get install -y curl unzip zip bash && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Install SDKMAN and Java + Maven (with cache-friendly layer separation)
RUN curl -s "https://get.sdkman.io" | bash && \
    source "${SDKMAN_DIR}/bin/sdkman-init.sh" && \
    sdk install java 21.0.4-amzn && \
    sdk install maven 4.0.0-rc-3

# ------------------------------------
# 2. Build Java Application
# ------------------------------------
FROM sdkman-java-maven AS build
WORKDIR /app

# Only copy files needed for dependency resolution first to leverage Docker cache
COPY pom.xml ./
RUN source "$BASH_ENV" && mvn dependency:go-offline

# Now copy the rest of the source code
COPY . .

# Build the application
RUN source "$BASH_ENV" && mvn clean package -DskipTests

# ------------------------------------
# 3. Extract Layers
# ------------------------------------
FROM eclipse-temurin:21-jre AS layers
WORKDIR /layers
COPY --from=build /app/target/inditex-challenge-0.0.1-SNAPSHOT.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# ------------------------------------
# 4. Final Slim Image
# ------------------------------------
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=layers /layers/dependencies/ ./
COPY --from=layers /layers/spring-boot-loader/ ./
COPY --from=layers /layers/snapshot-dependencies/ ./
COPY --from=layers /layers/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
