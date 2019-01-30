#!/bin/bash

INSTALL_DIR=/opt/bluetooth-backend
NAME=bluetooth-backend
PIDFILE=$INSTALL_DIR/$NAME.pid

do_start() {
        echo "start"
        /usr/bin/java -DtestMode=true -DconfigFile=$INSTALL_DIR/conf/config.txt -Djavax.net.ssl.trustStore=$INSTALL_DIR/client.ts -Djavax.net.ssl.trustStorePassword=changeit -jar $INSTALL_DIR/$NAME.jar &
        PID=$!
        echo $PID > $PIDFILE
}

do_stop() {
        echo "stop"
        PID=$(cat $PIDFILE)
        kill $PID
}

do_restart() {
       do_stop
       do_start
}

case "$1" in
  start|"")
        do_start
        ;;
  stop)
        do_stop
        ;;
  restart)
        do_stop
        ;;

esac

