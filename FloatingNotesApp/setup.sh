#!/bin/bash

SCRIPT_PATH="/usr/local/FloatingNotes"

# Log file path
LOG_FILE="$SCRIPT_PATH/floatingnotes.log"
JAR_FILE="$SCRIPT_PATH/FloatingNotes.jar"

# Change directory to the location of the JAR file
cd "$SCRIPT_PATH" || exit

echo "$SCRIPT_PATH = $LOG_FILE = $JAR_FILE"

# Run application and redirect stderr stdout to log file
java -jar $JAR_FILE >> "$LOG_FILE" 2>&1 &

echo "FloatingNotes application started."
