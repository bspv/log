package com.bazzi.manager.bean;

import com.bazzi.manager.model.StatisticalMonitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticalMonitorDO extends StatisticalMonitor {
    private String projectName;

    private String projectCnName;

    private String logFileName;

    private String monitorName;

    private String alarmName;

}
