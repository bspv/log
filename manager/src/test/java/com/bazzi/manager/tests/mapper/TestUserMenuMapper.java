package com.bazzi.manager.tests.mapper;

import com.bazzi.manager.bean.BatchUserMenu;
import com.bazzi.manager.mapper.UserMenuMapper;
import com.bazzi.manager.model.UserMenu;
import com.bazzi.manager.tests.TestBase;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestUserMenuMapper extends TestBase {
    @Resource
    private UserMenuMapper userMenuMapper;

    @Test
    public void testDelete() {
        UserMenu um = new UserMenu();
        um.setUserId(1);
        userMenuMapper.delete(um);
//        userMenuMapper.deleteByExample(um);
        print("OK");
    }

//    @Test
    public void batchInsert() {
        BatchUserMenu um = new BatchUserMenu();
        um.setUserId(1);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        um.setMenuIds(list);
        um.setCreateUser("system");
        um.setCreateTime(new Date());
        userMenuMapper.batchInsert(um);
        print("OK");
    }
}
