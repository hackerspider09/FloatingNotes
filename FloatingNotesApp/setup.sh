#!/bin/bash

###########################################################
# Author : Prasad Balaji Khatake
# Date : 18/02/2024
# Use : Use to activate Floating Sticky Notes
#
# Desc : Floating Sticky notes application 

#############################################################

SCRIPT_PATH=$(dirname "$(realpath "$0")")

sleep 2

# Log flie path
LOG_FILE="$SCRIPT_PATH/floatingnotes.log"
JAR_FILE="$SCRIPT_PATH/FloatingNotes.jar"

# Change directory to the location of the JAR file
cd "$SCRIPT_PATH" || exit

# run application and redirect stderr stdout in log file
java -jar $JAR_FILE >> "$LOG_FILE" 2>&1 &

echo "FloatingNotes application started."
