FROM tomcat:9.0.53-jdk8-openjdk

COPY ./SDORG.war /usr/local/tomcat/webapps/ROOT.war
