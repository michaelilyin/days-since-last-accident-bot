FROM quay.io/quarkus/quarkus-micro-image:1.0

WORKDIR /app/
COPY build/*-runner /app/runner
RUN chmod 775 /app

EXPOSE 8080
CMD ["./runner", "-Dquarkus.http.host=0.0.0.0"]
