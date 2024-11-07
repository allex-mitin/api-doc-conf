FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/info-service-*.jar info-service.jar
EXPOSE 8080
CMD java $JAVA_PARAMS_APP -Dfile.encoding=UTF8 -jar info-service.jar
