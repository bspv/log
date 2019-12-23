package com.bazzi.alarm.kafka;

import com.bazzi.alarm.service.AlarmService;
import com.bazzi.alarm.service.ConfigService;
import com.bazzi.common.generic.AlarmDesc;
import com.bazzi.common.generic.NotifyAlarm;
import com.bazzi.common.util.GenericConst;
import com.bazzi.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class KafkaReceive {
    @Resource
    private AlarmService alarmService;

    @Resource
    private ConfigService configService;

    /**
     * 接收报警详情，发送报警
     *
     * @param alarmDetail 报警详情
     */
    @KafkaListener(topics = {GenericConst.TOPIC_ALARM})
    public void receiveAlarm(String alarmDetail) {
        log.debug("receive--->Topic:{}, detail:{}", GenericConst.TOPIC_ALARM, alarmDetail);
        alarmService.alarm(JsonUtil.parseObject(alarmDetail, AlarmDesc.class));
    }

    /**
     * 接收配置更改通知，删除对应缓存
     *
     * @param data 通知
     */
    @KafkaListener(topics = {GenericConst.TOPIC_NOTIFY})
    public void receiveNotify(String data) {
        log.info("receive--->Topic:{}, data:{}", GenericConst.TOPIC_NOTIFY, data);
        configService.updateConfig(JsonUtil.parseObject(data, NotifyAlarm.class));
    }

}
