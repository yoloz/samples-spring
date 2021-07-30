#!/usr/bin/env bash
PID=$(ps -ef | grep 'oauth2-sso-auth-server-0.0.1-SNAPSHOT.jar' |grep -v grep|awk '{print $2}')
if [ -z "$PID" ]; then
    echo "oauth2-sso-auth-server-0.0.1-SNAPSHOT not running"
else
    echo "stop oauth2-sso-auth-server-0.0.1-SNAPSHOT,pid is " ${PID}
    kill -s TERM ${PID}
fi
exit 0
