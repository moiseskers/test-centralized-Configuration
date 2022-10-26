FROM openjdk:11

LABEL source="git@bitbucket.org:gingapay/split-core-application-api.git"

COPY target/split-core-application-api-1.0.0-RELEASE.jar split-core-application-api.jar

EXPOSE 80 443

ENTRYPOINT ["java","-jar","/split-core-application-api.jar"]
