#!/bin/sh
echo "********************************************************"
echo "Starting Organization Service "
echo "CONFIGSERVER_URI,EUREKASERVER_URI & PROFILE are mandatory environment variables, "
echo "Make sure that you are passing them."
echo "********************************************************"
java $JAVA_OPT -Dspring.cloud.config.uri=$CONFIGSERVER_URI \
	       -Dspring.profiles.active=$PROFILE \
	       -Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI -jar /usr/local/org-service/org-service-0.0.1-SNAPSHOT.jar
