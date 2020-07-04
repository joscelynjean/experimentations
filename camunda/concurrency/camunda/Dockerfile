FROM camunda/camunda-bpm-platform:tomcat-latest

USER root

ENV TZ=America/Montreal
ENV PRETTY_JSON_LOG=false

# RUN chown camunda:camunda /camunda/conf/server.xml \
#     /camunda/webapps/engine-rest/WEB-INF/web.xml \
#     /camunda/conf/bpm-platform.xml \
#     /camunda/lib/camunda-json-logging-tomcat-${VERSIONLIB}.jar \
#     /camunda/conf/logback/logback.xml \
#     /camunda/conf/logging.properties \
#     /camunda/bin/setenv.sh

# Remove example invoice app
RUN rm -rf /camunda/webapps/camunda-invoice

USER camunda
