package com.bazzi.analysis.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties
public class DefinitionProperties {

    @Value("${spring.cache.redis.key-prefix}")
    private String cachePrefix;

    @Value("${spring.cache.redis.key-prefix-permanent}")
    private String permCachePrefix;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.log-topic}")
    private String logTopic;

    @Value("${topology.spout-num}")
    private Integer spoutNum;

    @Value("${topology.blot-num}")
    private Integer blotNum;

    @Value("${topology.worker-num}")
    private Integer workerNum;

    @Value("${spring.zookeeper.url}")
    private String zookeeperUrl;

    @Value("${spring.zookeeper.timeout}")
    private Integer zookeeperTimeout;

}
