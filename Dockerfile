# BUILD
FROM maven:3.6-openjdk-11 as build-mvn

COPY ./src /build/src
COPY ./pom.xml /build/pom.xml

RUN cd /build && mvn clean package

# IMAGE
FROM alpine:3.12

LABEL maintainer="vdrouard.pro@gmail.com"

ENV JAVA_OPTS=""

WORKDIR /mock
RUN mkdir -p /mock-response

COPY --from=build-mvn /build/target/mock-api*.jar /mock/mock-api.jar

EXPOSE 8050
HEALTHCHECK --interval=2s CMD  wget --spider -S 'http://localhost:8050/mock-api/healthcheck' 2>&1 | grep "HTTP/1.1 200" 1>/dev/null || exit 1

RUN apk add --update wget openjdk11-jre

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /mock/mock-api.jar" ]