FROM centos

# -y is for the continous yes when installing
RUN yum install -y java

VOLUME /tmp
# add the jar and rename it
ADD /spring-boot-web-0.0.1-SNAPSHOT.jar myapp.jar
# update time on our jar file
RUN sh -c 'touch /myapp.jar'
# run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/.urandom","-jar","/myapp.jar"]

