package com.bazzi.analysis.topology;

import com.bazzi.analysis.bean.LogDetail;
import com.bazzi.analysis.service.InitialDataService;
import com.bazzi.analysis.service.LogService;
import com.bazzi.analysis.service.ZkService;
import com.bazzi.analysis.util.SpringContainer;
import com.bazzi.analysis.util.StatisticalData;
import com.bazzi.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
public class AnalysisBlot extends BaseRichBolt {
    private static final long serialVersionUID = 9141317321257868132L;

    private OutputCollector collector;

    @Resource
    private LogService logService;
    @Resource
    private InitialDataService initialDataService;

    @Resource
    private ZkService zkService;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        SpringContainer.run(new String[0]);
        initialDataService = SpringContainer.getBean(InitialDataService.class);
        logService = SpringContainer.getBean(LogService.class);
        zkService = SpringContainer.getBean(ZkService.class);
        initialDataService.loadProjectConfig();
        initialDataService.startSyncTotalData();
        zkService.subscribeNotify();
        this.collector = outputCollector;
    }

    public void execute(Tuple tuple) {
//        String msg = tuple.getString(0);//tuple各下标对应值：0->topic,1->partition,2->offset,3->key,4->value
//        String msg = tuple.getStringByField("value");
        String msg = tuple.getString(4);

        StatisticalData.totalLogCount.increment();

        LogDetail ld = JsonUtil.parseObject(msg, LogDetail.class);

        int length = ld.getMessage().getBytes(StandardCharsets.UTF_8).length;
        StatisticalData.totalLogTraffic.add(length);
        ld.setLen(length);

        logService.processLog(ld);

//        log.debug("----------------------------------------------------:" + msg);

        collector.ack(tuple);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    public void cleanup() {
        super.cleanup();
    }
}
