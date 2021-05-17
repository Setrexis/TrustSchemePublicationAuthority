FROM tomcat:jre8-alpine

RUN rm -rf /usr/local/tomcat/webapps/*
ADD target/tspa.war /usr/local/tomcat/webapps/tspa.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
