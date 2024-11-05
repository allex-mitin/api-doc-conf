FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/backend-apifirst-*.jar backend-apifirst.jar
EXPOSE 8080
CMD java $JAVA_PARAMS_APP -Dfile.encoding=UTF8 -jar backend-service.jar
