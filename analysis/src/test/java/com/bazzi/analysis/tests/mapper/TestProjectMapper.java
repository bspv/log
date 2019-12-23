package com.bazzi.analysis.tests.mapper;

import com.bazzi.analysis.mapper.ProjectHostMapper;
import com.bazzi.analysis.tests.TestBase;
import org.junit.Test;

import javax.annotation.Resource;

public class TestProjectMapper extends TestBase {
    @Resource
    private ProjectHostMapper projectHostMapper;

    @Test
    public void testLoad(){
        print(projectHostMapper.selectByPrimaryKey(1L));
    }
}
