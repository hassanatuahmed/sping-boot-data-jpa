#!/bin/sh

echo "The application will start in ${JAESOFT_SLEEP}s..." && sleep ${JAESOFT_SLEEP}
exec java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar "${HOME}/app.jar" "$@"