FROM openjdk:11

LABEL source="git@bitbucket.org:gingapay/template-application.git"

COPY target/template-application-1.0.0-RELEASE.jar template-application.jar

EXPOSE 80 443

ENTRYPOINT ["java","-jar","/template-application.jar"]
