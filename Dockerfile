FROM openjdk:11-jdk

WORKDIR /app

ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD
ARG JWT_SECRET
ARG AI_SERVER_URL
ARG S3_BUCKET_NAME
ARG S3_DIR_USER_IMAGE_NAME
ARG AWS_ACCESS_KEY
ARG AWS_SECRET_KEY

ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV JWT_SECRET=${JWT_SECRET}
ENV AI_SERVER_URL=${AI_SERVER_URL}
ENV S3_BUCKET_NAME=${S3_BUCKET_NAME}
ENV S3_DIR_USER_IMAGE_NAME=${S3_DIR_USER_IMAGE_NAME}
ENV AWS_ACCESS_KEY=${AWS_ACCESS_KEY}
ENV AWS_SECRET_KEY=${AWS_SECRET_KEY}

COPY . .

RUN chmod +x gradlew && ./gradlew build -x test

COPY src/main/resources/application.properties /app/application.properties
COPY src/main/resources/application-prod.properties /app/application-prod.properties

EXPOSE 8080

CMD ["java", "-jar", "build/libs/cotegory-0.0.1-SNAPSHOT.jar", "--spring.config.name=application", "--spring.profiles.active=prod"]
