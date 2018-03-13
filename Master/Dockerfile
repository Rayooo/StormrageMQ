FROM openjdk:8-jre-alpine

ARG JAR_FILE

COPY ${JAR_FILE} /stormrage-mq.jar

ENTRYPOINT [ "sh", "-c", "java -jar /stormrage-mq.jar" ]
