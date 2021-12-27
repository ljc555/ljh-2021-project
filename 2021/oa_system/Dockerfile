FROM openjdk:8-jdk

WORKDIR /root

ADD target/*.jar /root/app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]