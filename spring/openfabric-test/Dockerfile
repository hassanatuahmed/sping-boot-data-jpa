#FROM openjdk:11-jdk
#COPY build/libs/*.jar spring-boot-docker.jar
#CMD ["java","-jar","/spring-boot-docker.jar","ai.openfabric.api.Application"]

FROM frolvlad/alpine-oraclejdk8:slim
EXPOSE 8080
RUN mkdir -p /app/
ADD build/libs/my-jar.jar /app/my-jar.jar
ENTRYPOINT ["java", "-jar", "/my-jar.jar"]