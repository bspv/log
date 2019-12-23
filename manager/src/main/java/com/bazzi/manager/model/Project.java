package com.bazzi.manager.model;

import javax.persistence.Id;
import java.util.Date;

public class Project {
    @Id
    private Integer id;

    private String projectName;

    private String projectCnName;

    private String logFileName;

    private Integer captureType;

    private String logRegular;

    private String delimiter;

    private String captureRegular;

    private String projectDesc;

    private Integer status;

    private String remark;

    private Integer version;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProjectCnName() {
        return projectCnName;
    }

    public void setProjectCnName(String projectCnName) {
        this.projectCnName = projectCnName == null ? null : projectCnName.trim();
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName == null ? null : logFileName.trim();
    }

    public Integer getCaptureType() {
        return captureType;
    }

    public void setCaptureType(Integer captureType) {
        this.captureType = captureType;
    }

    public String getLogRegular() {
        return logRegular;
    }

    public void setLogRegular(String logRegular) {
        this.logRegular = logRegular == null ? null : logRegular.trim();
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter == null ? null : delimiter.trim();
    }

    public String getCaptureRegular() {
        return captureRegular;
    }

    public void setCaptureRegular(String captureRegular) {
        this.captureRegular = captureRegular == null ? null : captureRegular.trim();
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc == null ? null : projectDesc.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
