# Stage 1: Build with Maven
FROM maven:3.8.3-openjdk-17 as builder
WORKDIR /build

# Copy project files
COPY . /build

# Run Maven build
RUN mvn package

# Stage 2: Create the final Docker image
FROM eclipse-temurin:17-jre

LABEL maintainer="Gaurav Mathur <gaurav.mathur@ymail.com>"
LABEL version="0.1"
LABEL description="SaareKaam"

WORKDIR /app

# Environment variables
ENV SAAREKAAM_SRC_JAR saarekaam-1.0-SNAPSHOT-jar-with-dependencies.jar
ENV SAAREKAAM_TGT_JAR saarekaam-1.0-SNAPSHOT.jar
ENV JMX_AGENT_JAR jmx_prometheus_javaagent-0.20.0.jar
ENV JMX_CONFIG prometheus_jmx_config.yml
ENV SAAREKAAM_TASKS_JAR saarekaam-1.0-SNAPSHOT.jar
ENV MAX_HEAP 64M

# Copy artifacts from the builder stage
COPY --from=builder /build/target/${SAAREKAAM_SRC_JAR} ${SAAREKAAM_TGT_JAR}
COPY ext/lib/${JMX_AGENT_JAR} .
COPY ext/lib/${JMX_CONFIG} .

EXPOSE 8899

CMD java -Xmx${MAX_HEAP} -javaagent:${JMX_AGENT_JAR}=8899:${JMX_CONFIG} -jar ${SAAREKAAM_TGT_JAR} ${SAAREKAAM_TASKS_JAR}

