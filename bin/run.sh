#!/bin/bash

INSTALL_DIR=/opt/bluetooth-backend
NAME=bluetooth-backend
/usr/bin/java -DtestMode=true -DconfigFile=$INSTALL_DIR/conf/config.txt -jar $INSTALL_DIR/$NAME.jar
