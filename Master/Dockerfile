FROM daocloud.io/library/java

RUN mkdir -p /app

COPY . /app

RUN chmod +x /app/master-0.0.5-SNAPSHOT.jar

EXPOSE 3000

ENTRYPOINT [ "sh", "-c", "java -jar /app/master-0.0.5-SNAPSHOT.jar" ]
