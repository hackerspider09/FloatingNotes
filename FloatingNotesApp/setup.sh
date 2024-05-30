#!/bin/bash

# Log flie path
LOG_FILE="floatingnotes.log"

# run application and redirect stderr stdout in log file
java -jar FloatingNotes.jar >> "$LOG_FILE" 2>&1 &

echo "FloatingNotes application started."
