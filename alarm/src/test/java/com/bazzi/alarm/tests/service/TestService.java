package com.bazzi.alarm.tests.service;

import com.bazzi.alarm.service.AlarmService;
import com.bazzi.alarm.tests.TestBase;
import com.bazzi.common.generic.AlarmDesc;
import com.bazzi.common.util.JsonUtil;
import org.junit.Test;

import javax.annotation.Resource;

public class TestService extends TestBase {

    @Resource
    private AlarmService alarmService;

    @Test
    public void testAlarm(){
        String ss = "{\"monitorId\":4,\"monitorName\":\"字符串匹配\",\"projectId\":6,\"projectName\":\"finance-api\",\"alarmId\":3,\"content\":\"【2019-02-28 17:33:10:323】系统finance-api产生1条策略为【字符串匹配】的报警，请尽快关注!\",\"ip\":\"172.20.106.39\",\"platform\":\"windows\",\"message\":\"2019-02-28 17:33:06.494|WARN|http-nio-9001-exec-3|172.20.106.39||0|1|1.0.0|iphone7|iphone7||17f40a67c050430cb9a810a35f9e92ed|123456|o.s.w.s.m.m.a.ExceptionHandlerExceptionResolver|logException|Resolved exception caused by Handler execution: java.lang.NullPointerException\",\"triggerTime\":\"2019-02-28 17:33:10:323\"}";
        AlarmDesc desc = JsonUtil.parseObject(ss, AlarmDesc.class);
        alarmService.alarm(desc);
    }
}
