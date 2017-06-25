FROM openjdk:8u111-jre-alpine

MAINTAINER David Szabo <david.marcell.szabo@gmail.com>

#ENV SPRING_DATASOURCE_URL: 'jdbc:mysql://mysqldb:3307/db_example'

# add the jar and rename it
ADD /build/libs/backendfriendfinder2-0.1.0.jar myapp.jar
# update time on our jar file
RUN sh -c 'touch /myapp.jar'

# run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/myapp.jar"]
