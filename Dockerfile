FROM us-central1-docker.pkg.dev/united-coil-415507/ad-sync-core:9a06d6a5da666b8cff0fd858f6f51a8d52e115a1
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
