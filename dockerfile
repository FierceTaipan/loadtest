FROM openjdk:8-jre-alpine
MAINTAINER Alex

RUN apk update && apk add bash curl

ENV APP_HOME /usr/local/app
RUN mkdir -m 0755 -p ${APP_HOME}/bin

COPY target/Example.jar ${APP_HOME}/bin
COPY docker-entrypoint.sh ${APP_HOME}/bin
RUN chmod +x ${APP_HOME}/bin/docker-entrypoint.sh

RUN addgroup -S gatling && adduser -S gatling -G gatling
RUN chown -R gatling:gatling ${APP_HOME}
RUN chown gatling:gatling ${APP_HOME}/bin/docker-entrypoint.sh

USER gatling
WORKDIR ${APP_HOME}

CMD ["/usr/local/app/bin/docker-entrypoint.sh"]
