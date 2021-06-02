FROM alpine
RUN apk add openjdk8
COPY build/libs/project-3-server-0.0.1-SNAPSHOT.jar usr/local/tomcat/webapps/project-3-server-0.0.1-SNAPSHOT.jar

CMD ["java","-jar","usr/local/tomcat/webapps/project-3-server-0.0.1-SNAPSHOT.jar"]
