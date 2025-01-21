FROM eclipse-temurin:17-jdk-alpine
# Comment the above line and uncomment the following line for computer with Apple M1 chip
#FROM --platform=linux/amd64 eclipse-temurin:17-jdk-alpine
VOLUME ["/tmp", "/logs"]
RUN mkdir /data
COPY data/* /data/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]