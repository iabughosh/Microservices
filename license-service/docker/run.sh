#!/bin/sh
echo "********************************************************"
echo "Starting Licensing Service "
echo "CONFIGSERVER_URI & PROFILE are mandatory environment variables, kindly to pass them."
echo "********************************************************"
java $JAVA_OPT -Dspring.cloud.config.uri=$CONFIGSERVER_URI -Dspring.profiles.active=$PROFILE -jar /usr/local/license-service/license-service-0.0.1-SNAPSHOT.jar
