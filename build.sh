#!/usr/bin/env bash

# Check if the number of arguments is correct
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <name> <version>"
    exit 1
fi

# Assign the command-line arguments to variables
name="$1"
version="$2"

mvn package

# Build the Docker image with the specified name and version
docker build -t "$name:$version" .

