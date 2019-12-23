package com.bazzi.alarm.tests.mapper;

import com.bazzi.alarm.mapper.ProjectMapper;
import com.bazzi.alarm.tests.TestBase;
import org.junit.Test;

import javax.annotation.Resource;

public class TestProjectMapper extends TestBase {

    @Resource
    private ProjectMapper projectMapper;

    @Test
    public void testLoadProject() {
        print(projectMapper.selectByPrimaryKey(1));
    }
}
