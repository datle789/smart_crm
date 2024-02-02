
FROM openjdk:11-jre-slim

COPY target/smart_crm.jar /app/
WORKDIR /app

CMD ["java", "-jar", "smart_crm.jar"]