package com.bazzi.manager.bean;

import com.bazzi.manager.model.StatisticalProject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticalProjectDO extends StatisticalProject {

    private String projectName;

    private String projectCnName;

    private String logFileName;
}
