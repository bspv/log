package com.bazzi.alarm.tests.mapper;

import com.bazzi.alarm.mapper.UserMapper;
import com.bazzi.alarm.model.User;
import com.bazzi.alarm.tests.TestBase;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class TestUserMapper extends TestBase {
    @Resource
    private UserMapper userMapper;

    @Test
    public void testFindByList(){
        List<Integer> lt = new ArrayList<>();
        lt.add(100);
        List<User> byList = userMapper.findByList(lt);
        print(byList);
    }
}
