FROM openjdk:8-jdk-alpine

# RUN apk add tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
#    && echo "Asia/Shanghai" > /etc/timezone \
#    && apk del tzdata

COPY target/jd-union-service-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "-Xms256m", "-Xmx256m", "/app/app.jar"]