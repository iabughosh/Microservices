#!/bin/sh

echo "********************************************************"
echo "Waiting for the configuration server to start on port $CONFIG_PORT"
echo "********************************************************"
while ! `nc -z confsrv $CONFIG_PORT `; do sleep 10; done
echo ">>>>>>>>>>>> Configuration Server has started"


echo "********************************************************"
echo "Waiting for Eureka server to start on port $EUREKA_PORT"
echo "********************************************************"
while ! `nc -z eurekasrv $EUREKA_PORT `; do sleep 10; done
echo ">>>>>>>>>>>> Eureka Server has started"


echo "********************************************************"
echo "Starting Zuul API Gateway "
echo "CONFIGSERVER_URI,EUREKASERVER_URI & PROFILE are mandatory environment variables, "
echo "Make sure that you are passing them."
echo "********************************************************"
java $JAVA_OPT -Dspring.cloud.config.uri=$CONFIGSERVER_URI \
	       -Dspring.profiles.active=$PROFILE \
	       -Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI -jar /usr/local/zuul/zuul-0.0.1-SNAPSHOT.jar
