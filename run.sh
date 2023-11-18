#!/bin/bash

# Check if the number of arguments is correct
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <name> <version>"
    exit 1
fi

# Assign the command-line arguments to variables
name="$1"
version="$2"

docker run -e TZ=$(cat /etc/timezone) -p 8899:8899 $name:$version
