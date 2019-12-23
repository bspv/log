package com.bazzi.alarm.tests.service;

import com.bazzi.alarm.service.EmailService;
import com.bazzi.alarm.tests.TestBase;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class TestEmailService extends TestBase {
    @Resource
    private EmailService emailService;

    @Test
    public void testSendEmail() throws Exception {
        String sendTo = "alarm@hexindai.com";
        String title = "预警邮件";
        String temp = "/email_old.ftl";
        Map<String, Object> content = new HashMap<>();
        content.put("time", "2019-01-11 17:30:15");
        content.put("level", "ERROR");
        content.put("msg", "NullPointException");

        emailService.sendTemplateMail(sendTo, title, temp, content);
        print("success");
    }
}
