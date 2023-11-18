FROM eclipse-temurin:17-jre

LABEL maintainer="Gaurav Mathur <gaurav.mathur@ymail.com>"
LABEL version="0.1"
LABEL description="SaareKaam"

WORKDIR /app

ENV SAAREKAAM_SRC_JAR saarekaam-1.0-SNAPSHOT-jar-with-dependencies.jar
ENV SAAREKAAM_TGT_JAR saarekaam-1.0-SNAPSHOT.jar
ENV JMX_AGENT_JAR jmx_prometheus_javaagent-0.20.0.jar
ENV JMX_CONFIG prometheus_jmx_config.yml
ENV SAAREKAAM_TASKS_JAR saarekaam-1.0-SNAPSHOT.jar
 
ENV MAX_HEAP 256M

COPY target/${SAAREKAAM_SRC_JAR} ${SAAREKAAM_TGT_JAR}
COPY ext/lib/${JMX_AGENT_JAR} .
COPY ext/lib/${JMX_CONFIG} .

EXPOSE 8899

CMD java -Xmx${MAX_HEAP} -javaagent:${JMX_AGENT_JAR}=8899:${JMX_CONFIG} -jar ${SAAREKAAM_TGT_JAR} ${SAAREKAAM_TASKS_JAR}

