# Build stage

FROM redis:latest

ARG REDIS_PASSWORD

ENV REDIS_PASSWORD=$REDIS_PASSWORD

CMD redis-server --requirepass ${REDIS_PASSWORD}
