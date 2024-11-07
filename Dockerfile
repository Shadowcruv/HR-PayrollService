#FROM maven:3.8.4 AS maven_build
#WORKDIR /appff
#COPY pom.xml /appff/
#RUN mvn dependency:resolve
#COPY ./ /appff/
#RUN mvn clean package -DskipTests
#
## Stage 2: Create the runtime image with OpenJDK
#FROM openjdk:17-jdk-alpine
#WORKDIR /appp
#ARG JAR_FILE=/appff/target/VotingSystem-1.0-SNAPSHOT.jar
#COPY --from=maven_build ${JAR_FILE} .
#EXPOSE 8083 FROM eclipse-temurin:17.0.4_8-jre-alpine
#CMD ["java", "-jar", "VotingSystem-1.0-SNAPSHOT.jar"]

# Stage 1: Build application with Maven (JDK required here)
#FROM maven:3.8.4-eclipse-temurin-17-alpine AS maven_build
#WORKDIR /app
#COPY pom.xml .
#RUN mvn dependency:resolve
#
#COPY . .
#RUN mvn clean package -DskipTests

# Stage 2: Run application with JRE (lighter for runtime)
FROM eclipse-temurin:17.0.4_8-jre-alpine
WORKDIR /app
ARG JAR_FILE=/app/target/ChallengeTwo-0.0.1-SNAPSHOT.jar
COPY --from=maven_build ${JAR_FILE} app.jar

EXPOSE 8083
CMD ["java", "-jar", "app.jar"]
