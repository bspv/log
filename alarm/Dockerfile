#
FROM openjdk:8-jre-slim

VOLUME /export/data/logs

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime;\
     echo "Asia/Shanghai" > /etc/timezone

#
COPY ./alarm/target/alarm.jar /alarm.jar

EXPOSE 9022

#
ENTRYPOINT ["java","-jar","/alarm.jar"]