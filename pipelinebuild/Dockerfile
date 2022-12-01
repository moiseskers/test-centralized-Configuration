FROM openjdk:11

LABEL source="git@bitbucket.org:gingapay/gingapay-centralized-configuration--application.git"

COPY target/gingapay-centralized-configuration--application-1.0.0-RELEASE.jar gingapay-centralized-configuration--application.jar

EXPOSE 80 443

ENTRYPOINT ["java","-jar","/gingapay-centralized-configuration--application.jar"]
