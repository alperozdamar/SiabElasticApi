FROM openjdk:8
ADD target/siabElasticApi.jar siabElasticApi.jar 
ADD resources/siabElasticApi.properties resources/siabElasticApi.properties
ADD resources/log4j2.properties resources/log4j2.properties
EXPOSE 8085
ENTRYPOINT ["java","-Dlog4j.configurationFile=file:/resources/log4j2.properties", "-jar","siabElasticApi.jar"]

