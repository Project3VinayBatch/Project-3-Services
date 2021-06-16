FROM alpine
RUN apk add openjdk8
EXPOSE 8080
COPY build/libs/project-3-server-0.0.1-SNAPSHOT.jar usr/local/tomcat/webapps/project-3-server-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","usr/local/tomcat/webapps/project-3-server-0.0.1-SNAPSHOT.jar"]

