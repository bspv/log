#
FROM openjdk:8-jre-slim

VOLUME /export/data/logs

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime;\
     echo "Asia/Shanghai" > /etc/timezone

#
COPY ./manager/target/manager.jar /manager.jar

EXPOSE 9021

#
ENTRYPOINT ["java","-jar","/manager.jar"]