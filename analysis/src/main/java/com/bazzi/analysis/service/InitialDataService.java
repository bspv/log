package com.bazzi.analysis.service;

import com.bazzi.common.generic.NotifyAnalysis;

public interface InitialDataService {

    /**
     * 开启统计数据同步到redis功能
     */
    void startSyncTotalData();

    /**
     * 预加载配置文件到内存中，并初始化统计数据值
     */
    void loadProjectConfig();

    /**
     * 后台管理更改时，更新缓存在内存中的配置信息
     *
     * @param notifyAnalysis 被修改数据信息
     */
    void updateProjectConfig(NotifyAnalysis notifyAnalysis);
}
