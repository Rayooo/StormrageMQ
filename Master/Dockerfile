FROM daocloud.io/library/java

RUN mkdir -p /app

COPY . /app

EXPOSE 3000

#Error: Unable to access jarfile /app/master-0.0.5-SNAPSHOT.jar

ENTRYPOINT [ "sh", "-c", "java -jar /app/master-0.0.5-SNAPSHOT.jar" ]
