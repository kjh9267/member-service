FROM eclipse-temurin:17-jdk

ARG JAR_FILE=target/member-service-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} member-service.jar

ENTRYPOINT ["java", "-jar", "/member-service.jar"]