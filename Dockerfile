FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar

ENV APP_HOME=/home/bin
RUN mkdir $APP_HOME
WORKDIR $APP_HOME

COPY ${JAR_FILE} $APP_HOME/app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar $APP_HOME/app.jar