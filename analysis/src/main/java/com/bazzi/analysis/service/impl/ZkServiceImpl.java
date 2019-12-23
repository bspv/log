package com.bazzi.analysis.service.impl;

import com.bazzi.analysis.config.DefinitionProperties;
import com.bazzi.analysis.service.InitialDataService;
import com.bazzi.analysis.service.ZkService;
import com.bazzi.common.generic.NotifyAnalysis;
import com.bazzi.common.util.GenericConst;
import com.bazzi.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ZkServiceImpl implements ZkService {
    @Resource
    private DefinitionProperties definitionProperties;
    @Resource
    private InitialDataService initialDataService;

    public void subscribeNotify() {
        getZkClient().subscribeDataChanges(GenericConst.ZK_DATA_PATH, new IZkDataListener() {
            public void handleDataChange(String path, Object o) {
                log.info("handleDataChange ------ :" + JsonUtil.toJsonString(o));
                NotifyAnalysis notifyAnalysis = (NotifyAnalysis) o;
                initialDataService.updateProjectConfig(notifyAnalysis);
                log.info("Handle finished ------ projectId:{} , logFileName:{}",
                        notifyAnalysis.getProjectId(), notifyAnalysis.getLogFileName());
            }

            public void handleDataDeleted(String path) {
                log.info("handleDataDeleted------>" + path);
            }
        });
    }

    private ZkClient zkClient;

    private ZkClient getZkClient() {
        if (zkClient == null) {
            zkClient = new ZkClient(definitionProperties.getZookeeperUrl(), definitionProperties.getZookeeperTimeout());
        }
        return zkClient;
    }
}
