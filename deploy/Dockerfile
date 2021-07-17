FROM ubuntu:18.04

ADD file:a86aea1f3a7d68f6ae03397b99ea77f2e9ee901c5c59e59f76f93adbb4035913 in / 

 CMD ["/bin/sh"]
 ENV LANG=C.UTF-8
 /bin/sh -c { 		echo '#!/bin/sh'; 		echo 'set -e'; 		echo; 		echo 'dirname "$(dirname "$(readlink -f "$(which javac || which java)")")"'; 	} > /usr/local/bin/docker-java-home 	&& chmod +x /usr/local/bin/docker-java-home

 ENV JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk
 ENV PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin
 ENV JAVA_VERSION=8u212
 ENV JAVA_ALPINE_VERSION=8.212.04-r0

/bin/sh -c set -x 	&& apk add --no-cache 		openjdk8="$JAVA_ALPINE_VERSION" 	&& [ "$JAVA_HOME" = "$(docker-java-home)" ]
ENV TZ:=America/Sao_Paulo
VOLUME [/tmp]

COPY ./Authetication-Session-0.0.1-SNAPSHOT.jar Authetication-Session.jar # buildkit
ENTRYPOINT ["java" "-jar" "Authetication-Session.jar"]

EXPOSE 8080
