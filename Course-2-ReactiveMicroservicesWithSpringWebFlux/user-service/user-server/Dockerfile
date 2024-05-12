FROM eclipse-temurin:17-jdk-jammy

# The author of the image
LABEL maintainer="thanhsang.it.01@gmail.com"

# Set the current working directory inside the image
WORKDIR /app

ARG JAR_FILE=./target/user-server-*.jar
COPY ${JAR_FILE} user-service.jar

# Set Environment Variables
ENV PORT=8092
ENV SPRING_PROFILE_ACTIVE=""

EXPOSE ${PORT}

ENTRYPOINT ["java","-jar","user-service.jar","--spring.profiles.active=${SPRING_PROFILE_ACTIVE}"]

#command:
#    1.  docker build -t user-service .
#    2.  docker run user-service