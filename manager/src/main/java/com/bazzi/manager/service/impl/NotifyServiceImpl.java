package com.bazzi.manager.service.impl;

import com.bazzi.common.generic.NotifyAlarm;
import com.bazzi.common.generic.NotifyAnalysis;
import com.bazzi.common.util.GenericConst;
import com.bazzi.common.util.JsonUtil;
import com.bazzi.manager.config.DefinitionProperties;
import com.bazzi.manager.kafka.KafkaTransmit;
import com.bazzi.manager.service.NotifyService;
import com.bazzi.manager.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class NotifyServiceImpl implements NotifyService {
    @Resource
    private RedisService redisService;

    @Resource
    private KafkaTransmit kafkaTransmit;

    @Resource
    private DefinitionProperties definitionProperties;

    public void sendNotifyAnalysis(NotifyAnalysis notifyAnalysis) {
        ZkClient zkClient = getZkClient();
        if (!zkClient.exists(GenericConst.ZK_DATA_PATH)) {
            zkClient.createPersistent(GenericConst.ZK_DATA_PATH);
        }
        zkClient.writeData(GenericConst.ZK_DATA_PATH, notifyAnalysis);
        log.info("NotifyAnalysis------:{}", JsonUtil.toJsonString(notifyAnalysis));
    }

    public void sendNotifyAlarm(NotifyAlarm notifyAlarm) {
        kafkaTransmit.send(GenericConst.TOPIC_NOTIFY, JsonUtil.toJsonString(notifyAlarm));
        log.info("NotifyAlarm------:{}", JsonUtil.toJsonString(notifyAlarm));
    }

    private static ZkClient zkClient;

    private synchronized ZkClient getZkClient() {
        if (zkClient == null) {
            zkClient = new ZkClient(definitionProperties.getZookeeperUrl(), definitionProperties.getZookeeperTimeout());
        }
        return zkClient;
    }
}
