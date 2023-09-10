FROM openjdk:17 AS build
WORKDIR /app
COPY . /app/
RUN microdnf install findutils
RUN ./gradlew build
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/build/libs/preferencesnotificationservice-0.0.1-SNAPSHOT.jar preferencesnotifications-api.jar
EXPOSE 8080
CMD ["java", "-jar", "preferencesnotifications-api.jar"]