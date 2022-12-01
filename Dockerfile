FROM openjdk:11
ADD target/jwtserver.jar jwtserver.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","jwtserver.jar"]