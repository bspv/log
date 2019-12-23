package com.bazzi.manager.tests.service;

import com.bazzi.manager.service.RedisService;
import com.bazzi.manager.tests.TestBase;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

public class TestRedisService extends TestBase {
    @Resource
    private RedisService redisService;

    @Test
    public void testPublish(){
        redisService.publish("logTestAnalysis","CCTV is good!-0327-1659");
        print("testPublish success");
    }

    @Test
    public void testDelByPipeline(){
//        redisService.set("notebook:1",1);
//        redisService.set("notebook:2",2);
//        redisService.set("notebook:3",3);
//        redisService.set("notebook:4",4);
//        redisService.set("notebook:5",5);
        List<String> keys = Arrays.asList("notebook:1",
                "notebook:2","notebook:3","notebook:4","notebook:5");
        redisService.delByPipeline(keys);
    }
}
