#!/bin/bash

INSTALL_DIR=~/src/AMQClient/target
NAME=AMQClient-0.0.1

export AMQ_USERNAME=asdf
export AMQ_PASSWORD=asdf
export AMQ_URL=tcp://localhost:61616
export TIME_TO_LIVE=100
export DEFAULT_WRITE_QUEUE=write
export READ_QUEUE=read
export DISABLE_READS=false
export CONNECTION_ATTEMPTS=3

java $@ -jar $INSTALL_DIR/$NAME.jar
