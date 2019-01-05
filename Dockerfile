FROM openjdk:8
ADD lib/siabElasticApi.jar siabElasticApi.jar 
ADD resources/siabElasticApi.properties resources/siabElasticApi.properties
ADD src/main/resources/log4j2.xml src/main/resources/log4j2.xml
ADD src/main/resources/log4j2.xml resources/log4j2.xml 
EXPOSE 8085
ENTRYPOINT ["java","-Dlog4j.configurationFile=file:/resources/log4j2.xml", "-jar","siabElasticApi.jar"]

