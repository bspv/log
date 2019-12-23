package com.bazzi.manager.tests.mapper;

import com.bazzi.manager.mapper.UserMapper;
import com.bazzi.manager.model.User;
import com.bazzi.manager.tests.TestBase;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

public class TestUserMapper extends TestBase {
    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelectOne() {
        User user = new User();
        user.setUserName("admin");
        User u = userMapper.selectOne(user);
        u.setUpdateUser("system");
        u.setUpdateTime(new Date());
        userMapper.update(u);
        print("OK");
    }
}
