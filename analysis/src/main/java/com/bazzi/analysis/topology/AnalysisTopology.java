package com.bazzi.analysis.topology;

import com.bazzi.analysis.config.DefinitionProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff;
import org.apache.storm.kafka.spout.KafkaSpoutRetryService;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy.LATEST;

@Slf4j
@Component
public class AnalysisTopology {
    private static final String SPOUT_NAME = "analysis-spout";
    private static final String BLOT_NAME = "analysis-bolt";

    private static final String LOCAL_TOPOLOGY_NAME = "analysis-topology-local";

    @Resource
    private DefinitionProperties definitionProperties;

    public void runTopology(String[] args) {
        KafkaSpoutConfig<String, String> kafkaSpoutConfig = buildKafkaSpoutConfig(buildKafkaSpoutRetryService());

        //定义一个拓扑
        TopologyBuilder builder = new TopologyBuilder();
        //设置一个Executor(线程)，默认一个
        builder.setSpout(SPOUT_NAME, new KafkaSpout<>(kafkaSpoutConfig), definitionProperties.getSpoutNum());
        builder.setBolt(BLOT_NAME, new AnalysisBlot(), definitionProperties.getBlotNum()).shuffleGrouping(SPOUT_NAME);


        Config conf = new Config();
        conf.setDebug(false);

        if (args == null || args.length == 0) {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(LOCAL_TOPOLOGY_NAME, conf, builder.createTopology());
            try {
                // Wait for some time before exiting
                log.info("Waiting to consume from kafka=================================");
                Thread.sleep(3000000);
            } catch (Exception exception) {
                log.error("Thread interrupted exception : " + exception.getMessage());
            }
            // kill the AnalysisTopology
            cluster.killTopology(LOCAL_TOPOLOGY_NAME);
            // shut down the storm test cluster
            cluster.shutdown();
        } else {
            try {
                conf.setNumWorkers(definitionProperties.getWorkerNum());
                StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
            } catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
                log.error("集群提交失败,TopologyName:{}", args[0]);
                log.error(e.getMessage(), e);
            }
        }

    }

    private KafkaSpoutConfig<String, String> buildKafkaSpoutConfig(KafkaSpoutRetryService kafkaSpoutRetryService) {
        return KafkaSpoutConfig.builder(definitionProperties.getBootstrapServers(), definitionProperties.getLogTopic())
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, definitionProperties.getGroupId())
                .setProp(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 200)
                .setRetry(kafkaSpoutRetryService)
                .setOffsetCommitPeriodMs(10000)
                .setFirstPollOffsetStrategy(LATEST)
                .setMaxUncommittedOffsets(250)
                .build();
    }

    private KafkaSpoutRetryService buildKafkaSpoutRetryService() {
        return new KafkaSpoutRetryExponentialBackoff(
                KafkaSpoutRetryExponentialBackoff.TimeInterval.microSeconds(500),
                KafkaSpoutRetryExponentialBackoff.TimeInterval.milliSeconds(2),
                Integer.MAX_VALUE,
                KafkaSpoutRetryExponentialBackoff.TimeInterval.seconds(10));
    }

}
