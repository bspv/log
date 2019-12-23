package com.bazzi.analysis.service.impl;

import com.bazzi.analysis.bean.LogDetail;
import com.bazzi.analysis.bean.StrategyConfig;
import com.bazzi.analysis.kafka.KafkaTransmit;
import com.bazzi.analysis.service.LogService;
import com.bazzi.analysis.service.RedisService;
import com.bazzi.analysis.util.Constant;
import com.bazzi.analysis.util.LogNameUtil;
import com.bazzi.analysis.util.StrategyCache;
import com.bazzi.common.generic.AlarmDesc;
import com.bazzi.common.util.DateUtil;
import com.bazzi.common.util.GenericConst;
import com.bazzi.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

import static com.bazzi.analysis.util.StatisticalData.*;

@Slf4j
@Component
public class LogServiceImpl implements LogService {

    @Resource
    private KafkaTransmit kafkaTransmit;

//    @Resource
//    private CheckService checkService;

    @Resource
    private RedisService redisService;

    @Override
    public void processLog(LogDetail logDetail) {
        if (logDetail == null)
            return;
        String logName = LogNameUtil.getLogName(logDetail);
        //依据项目维度来统计日志数和字节数
        if (projectLogCountMap.containsKey(logName)) {
            projectLogCountMap.get(logName).increment();
            projectLogTrafficMap.get(logName).add(logDetail.getLen());
        }
        //没有对应的报警策略匹配，则处理结束
        if (!StrategyCache.containsKey(logName)) {
            return;
        }
        //获取匹配策略
        List<StrategyConfig> scList = StrategyCache.get(logName);
        for (StrategyConfig strategyConfig : scList) {
            //处理具体的单条策略
            process(strategyConfig, logDetail);
        }
    }

    /**
     * 具体策略的匹配，如果命中，则根据handleType字段来进行实时报警，或延迟合并报警
     *
     * @param sc        策略配置
     * @param logDetail 单条日志明细
     */
    private void process(StrategyConfig sc, LogDetail logDetail) {
        if (sc.configMatcher(logDetail.getMessage())) {
            //统计策略命中、报警等信息
            addMonitorTotal(sc);

            if (sc.getHandleType() == 0) {//实时报警
                sendAlarmToKafka(sc, logDetail);
            } else {//延迟报警，进行统计合并再报警
                handleByInterval(sc, logDetail);
            }
        }
    }

    /**
     * 延迟处理<br/>
     * 1.将命中信息放入命中队列，并提交线程检查任务到线程池<br/>
     * 2.将信息放入缓冲队列，并根据合并标识的状态，以及缓冲队列元素信息来决定是否需要报警<br/>
     * 3.如果需要报警，则进行合并报警
     *
     * @param sc        策略配置
     * @param logDetail 单条日志明细
     */
    private void handleByInterval(StrategyConfig sc, LogDetail logDetail) {
        String happenKey = Constant.getHappenKey(sc);
        String mergeKey = Constant.getMergeKey(sc);

        String bufferQueueKey = Constant.getBufferKey(sc);

        //如果需要合并日志，则将日志放到缓冲队列中去
        if (redisService.isLogNeedMerge(happenKey, mergeKey, sc.getCalNum(), sc.getCalTime(), sc.getSendInterval())) {
            long current = System.currentTimeMillis();

            String bufferQueueVal = current + RandomStringUtils.randomNumeric(Constant.BUFFER_QUEUE_RANDOM_LEN);

            //把命中策略信息放入缓冲队列
            redisService.rPush(bufferQueueKey, Long.parseLong(bufferQueueVal));
            redisService.expire(bufferQueueKey, Constant.BUFFER_QUEUE_SECOND);
            return;
        }

        String earlyVal = String.valueOf(redisService.lIndex(bufferQueueKey, 0));
        long bufferQueueLen = redisService.lLen(bufferQueueKey);

        //缓冲队列中有值时，且队头为earlyVal，删除bufferQueueLen个元素成功，则触发合并报警
        //删除失败时，则队列的命中策略信息已经被使用，同一份数据，不多次报警
        if (bufferQueueLen > 0 && redisService.lTrimNumByHead(bufferQueueKey, earlyVal, bufferQueueLen)) {
            //从值中获取时间戳，即去掉尾部5位随机数
            long earlyTime = Long.parseLong(earlyVal.substring(0, earlyVal.length() - Constant.BUFFER_QUEUE_RANDOM_LEN));
            String firstTrigger = DateUtil.formatDate(new Date(earlyTime), DateUtil.FULL_FORMAT);
            sendAlarmToKafka(sc, logDetail, firstTrigger, bufferQueueLen + 1);//+1是要算上当前记录数
        } else {//缓冲队列中没有值，或者缓冲队列数据已被其他线程使用，按照单条数据报警
            sendAlarmToKafka(sc, logDetail);
        }

    }

    /**
     * 发送 <b>单条</b> 报警到kafka中
     *
     * @param sc        策略配置
     * @param logDetail 单条日志明细
     */
    private void sendAlarmToKafka(StrategyConfig sc, LogDetail logDetail) {
        sendAlarmToKafka(sc, logDetail, DateUtil.formatDate(logDetail.getTimestamp(), DateUtil.FULL_FORMAT), 1);
    }

    /**
     * 发送报警到kafka中
     *
     * @param sc           策略配置
     * @param logDetail    单条日志明细
     * @param firstTrigger 首次触发时间，实时报警为日志明细中的时间
     * @param count        报警数量，实时报警次数为1
     */
    private void sendAlarmToKafka(StrategyConfig sc, LogDetail logDetail, String firstTrigger, long count) {
        //增加报警统计
        addAlarmTotal(sc);
        //组合报警信息
        AlarmDesc alarmDesc = new AlarmDesc();
        BeanUtils.copyProperties(sc, alarmDesc);
        alarmDesc.setIp(logDetail.getServerIp());
        alarmDesc.setMessage(logDetail.getMessage());
        alarmDesc.setPlatform(logDetail.getHost().getOs().getPlatform());
        alarmDesc.setTriggerTime(DateUtil.formatDate(new Date(), DateUtil.FULL_FORMAT));
        String currentTrigger = DateUtil.formatDate(logDetail.getTimestamp(), DateUtil.FULL_FORMAT);
        alarmDesc.setContent(sc.getContent(firstTrigger, count, currentTrigger));
        alarmDesc.setFirstTrigger(firstTrigger);
        alarmDesc.setCount(count);
        String jsonString = JsonUtil.toJsonString(alarmDesc);
        kafkaTransmit.send(GenericConst.TOPIC_ALARM, jsonString);
    }

    /**
     * 更新策略命中对应的命中数据
     *
     * @param sc 策略配置
     */
    private void addMonitorTotal(StrategyConfig sc) {
        String projectKey = sc.getLogFileName();
        //统计策略命中信息
        totalMonitor.increment();
        LongAdder projectMonitor = projectMonitorMap.get(projectKey);
        projectMonitor.increment();

        //对每个项目的具体策略统计
        String key = String.format(Constant.DETAIL_FORMAT, sc.getMonitorId(), sc.getAlarmId());
        LongAdder monitorDetail = projectDetailMonitorMap.get(projectKey).get(key);
        monitorDetail.increment();
    }

    /**
     * 更新报警对应的报警数据
     *
     * @param sc 策略配置
     */
    private void addAlarmTotal(StrategyConfig sc) {
        String projectKey = sc.getLogFileName();
        //统计策略报警信息
        totalAlarm.increment();
        LongAdder projectAlarm = projectAlarmMap.get(projectKey);
        projectAlarm.increment();

        //对每个项目的具体报警进行统计
        String key = String.format(Constant.DETAIL_FORMAT, sc.getMonitorId(), sc.getAlarmId());
        LongAdder alarmDetail = projectDetailAlarmMap.get(projectKey).get(key);
        alarmDetail.increment();
    }

}
