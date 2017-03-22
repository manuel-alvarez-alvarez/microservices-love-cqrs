FROM openjdk:8-alpine

VOLUME /tmp
ADD gateway-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
HEALTHCHECK CMD wget -s http://localhost:8080/health || exit 1
ENTRYPOINT [ "sh", "-c", "sleep $SLEEP && java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]