package com.bazzi.analysis.service;

import com.bazzi.analysis.bean.LogDetail;

public interface LogService {
    /**
     * 日志处理：策略匹配，根据策略配置进行报警合并或者直接报警
     *
     * @param logDetail 单条日志明细
     */
    void processLog(LogDetail logDetail);
}
