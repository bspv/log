package com.bazzi.manager.task;

import com.bazzi.manager.service.SyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class SyncStatisticalTask {

    @Resource
    private SyncService syncService;

    /**
     * 每天0点10触发，将前一天的总计数据同步
     */
    @Scheduled(cron = "0 10 0 * * ?")
    public void syncTotalTask() {
        long start = System.currentTimeMillis();
        syncService.syncTotal();
        long end = System.currentTimeMillis();
        log.info("同步系统总计数据，对应Method：syncTotal，消耗时间：{}ms", (end - start));
    }

    /**
     * 每个小时的第1分钟触发，同步项目级各项数据
     * 包含日志量、流量、策略命中、报警数，每个项目对应具体策略命中、策略对应的报警数等
     */
    @Scheduled(cron = "0 1 0/1 * * ?")
    public void syncProjectTask() {
        long start = System.currentTimeMillis();
        syncService.syncProject();
        long end = System.currentTimeMillis();
        log.info("同步项目数据，对应Method：syncProject，消耗时间：{}ms", (end - start));
    }

}
