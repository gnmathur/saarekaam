FROM eclipse-temurin:17-jre

# Metadata labels
LABEL maintainer="Gaurav Mathur <gaurav.mathur@outlook.com>"
LABEL version="0.1"
LABEL description="SaareKaam"

# Set the working directory in the container
WORKDIR /app

# Define environment variables for JAR files and config file
ENV SAAREKAAM_SRC_JAR saarekaam-1.0-SNAPSHOT-jar-with-dependencies.jar
ENV SAAREKAAM_TGT_JAR saarekaam-1.0-SNAPSHOT.jar
ENV JMX_AGENT_JAR jmx_prometheus_javaagent-0.20.0.jar
ENV JMX_CONFIG prometheus_jmx_config.yml

# Copy dependencies and JAR files from the local directory to /app/
COPY target/${SAAREKAAM_SRC_JAR} ${SAAREKAAM_TGT_JAR}
COPY ext/lib/${JMX_AGENT_JAR} .
COPY ext/lib/${JMX_CONFIG} .

# Expose the port your application will run on (if applicable)
EXPOSE 8989

# Define the command to run your application
CMD java -javaagent:${JMX_AGENT_JAR}=8899:${JMX_CONFIG} -jar ${SAAREKAAM_TGT_JAR} com.gnmathur.saarekaam.Main

