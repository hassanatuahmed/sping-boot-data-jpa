#FROM openjdk:11-jdk
#COPY build/libs/*.jar spring-boot-docker.jar
#CMD ["java","-jar","/spring-boot-docker.jar","ai.openfabric.api.Application"]

#FROM openjdk:11-jdk
#EXPOSE 8080
#ADD target/*.jar my-jar.jar
#ENTRYPOINT ["java", "-jar", "/my-jar.jar"]

#FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
#
#FROM openjdk:11-jre
#
#ENTRYPOINT ["./entrypoint.sh"]
#
#
#ARG user=demo-app
#RUN useradd -ms /bin/bash ${user}
#WORKDIR /home/${user}
#
#ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
#    JAESOFT_SLEEP=0 \
#    JAVA_OPTS=""
#
#ADD entrypoint.sh entrypoint.sh
#RUN chmod 755 entrypoint.sh && chown ${user}:${user} entrypoint.sh
#USER ${user}
#
#ADD *.jar app.jar
#EXPOSE 8080
FROM openjdk:8 AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build || return 0
COPY . .
