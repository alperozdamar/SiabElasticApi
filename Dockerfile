FROM openjdk:8
ADD lib/siabElasticApi.jar siabElasticApi.jar 
ADD resources/siabElasticApi.properties resources/siabElasticApi.properties
EXPOSE 8085
ENTRYPOINT ["java","-jar","siabElasticApi.jar"]

