package com.bazzi.manager.service;

public interface SyncService {

    /**
     * 同步按天、按小时的系统级的统计数据到数据库
     * 不同步当天的数据，当天数据放redis用于仪表盘页显示
     */
    void syncTotal();

    /**
     * 同步按小时的基于项目的统计数据到数据库
     * 不同步当前小时的数据
     */
    void syncProject();

}
