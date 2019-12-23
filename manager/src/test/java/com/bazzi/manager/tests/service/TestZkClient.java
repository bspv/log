package com.bazzi.manager.tests.service;

import com.bazzi.common.generic.NotifyAnalysis;
import com.bazzi.common.util.GenericConst;
import com.bazzi.manager.config.DefinitionProperties;
import com.bazzi.manager.tests.TestBase;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;

import javax.annotation.Resource;

public class TestZkClient extends TestBase {

    private static ZkClient zkClientInit;

    @Resource
    private DefinitionProperties definitionProperties;

    @Test
    public void testWriteData() {
        NotifyAnalysis na = new NotifyAnalysis();
        na.setProjectId(6);
        ZkClient zkClient = getZkClient();
        if (!zkClient.exists(GenericConst.ZK_DATA_PATH)) {
            zkClient.createEphemeral(GenericConst.ZK_DATA_PATH);
        }
        zkClient.writeData(GenericConst.ZK_DATA_PATH, na);
    }

    private ZkClient getZkClient() {
        if (zkClientInit == null) {
            zkClientInit = new ZkClient(definitionProperties.getZookeeperUrl(), definitionProperties.getZookeeperTimeout());
        }
        return zkClientInit;
    }
}
