package com.bazzi.manager.service;

import com.bazzi.common.generic.NotifyAlarm;
import com.bazzi.common.generic.NotifyAnalysis;

public interface NotifyService {

    /**
     * 给Analysis发送通知，redis
     *
     * @param notifyAnalysis 通知信息
     */
    void sendNotifyAnalysis(NotifyAnalysis notifyAnalysis);

    /**
     * 给Alarm发送通知，kafka
     *
     * @param notifyAlarm 通知信息
     */
    void sendNotifyAlarm(NotifyAlarm notifyAlarm);
}
