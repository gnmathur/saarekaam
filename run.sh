#!/bin/bash

mvn package && java -javaagent:./ext/lib/jmx_prometheus_javaagent-0.20.0.jar=8899:./ext/lib/prometheus_jmx_config.yml -cp target/saarekaam-1.0-SNAPSHOT-jar-with-dependencies.jar com.gnmathur.saarekaam.Main
