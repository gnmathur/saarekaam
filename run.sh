#!/bin/bash

mvn package && java -cp target/saarekaam-1.0-SNAPSHOT-jar-with-dependencies.jar com.gnmathur.saarekaam.Main
