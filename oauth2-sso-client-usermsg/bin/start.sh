#!/usr/bin/env bash
export JAVA_HOME='/opt/jdk1.8.0_162'
if [ "x$JAVA_HOME" = "x" ]; then
   echo "JAVA_HOME is not configured, please configure and then execute again!"
   exit 1
fi
path=$(cd `dirname $0`/..;pwd)
nohup ${JAVA_HOME}/bin/java -jar ${path}/lib/oauth2-sso-client-usermsg-0.0.1-SNAPSHOT.jar > ${path}/server.log 2>&1 &