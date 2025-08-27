FROM amazoncorretto:21.0.3-alpine3.19 AS build
COPY . /home/gradle/
WORKDIR /home/gradle/
RUN ./gradlew clean build --no-watch-fs
RUN rm -f build/libs/drag-and-drop-api-service-*-plain.jar

FROM amazoncorretto:21.0.3-alpine3.19
RUN apk update && apk upgrade --no-cache libcrypto3 libssl3
WORKDIR /app
COPY --from=build /home/gradle/build/libs/drag-and-drop-api-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
