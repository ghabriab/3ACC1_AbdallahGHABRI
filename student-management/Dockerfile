FROM eclipse-temurin:21-jdk-jammy
# Updated to match server.port=8089
EXPOSE 8089

COPY target/student-management-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]