# BUILD
FROM maven:3.6-jdk-11-slim as build-mvn

COPY ./src /build/src
COPY ./pom.xml /build/pom.xml

RUN cd /build && mvn clean package

# IMAGE
FROM openjdk:11-slim

LABEL maintainer="vdrouard.pro@gmail.com"

COPY --from=build-mvn /build/target/mock-api*.jar /mock/mock-api.jar
RUN apt-get update && apt-get install -y wget

WORKDIR /mock
ENV JAVA_OPTS=""
EXPOSE 8050
HEALTHCHECK --interval=2s CMD  wget --spider -S 'http://localhost:8050/mock-api/healthcheck' 2>&1 | grep "HTTP/1.1 200" 1>/dev/null || exit 1
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /mock/mock-api.jar" ]