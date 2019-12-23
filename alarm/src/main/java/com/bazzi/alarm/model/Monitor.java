package com.bazzi.alarm.model;

import javax.persistence.Id;
import java.util.Date;

public class Monitor {
    @Id
    private Integer id;

    private String monitorName;

    private Integer projectId;

    private Integer alarmId;

    private Integer monitorType;

    private Integer strategyType;

    private String monitorStrategy;

    private String monitorRegular;

    private String contentTemplate;

    private Integer useTmp;

    private Integer handleType;

    private Integer calTime;

    private Integer calNum;

    private Integer sendInterval;

    private Integer priority;

    private Integer status;

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

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName == null ? null : monitorName.trim();
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public Integer getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(Integer monitorType) {
        this.monitorType = monitorType;
    }

    public Integer getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(Integer strategyType) {
        this.strategyType = strategyType;
    }

    public String getMonitorStrategy() {
        return monitorStrategy;
    }

    public void setMonitorStrategy(String monitorStrategy) {
        this.monitorStrategy = monitorStrategy == null ? null : monitorStrategy.trim();
    }

    public String getMonitorRegular() {
        return monitorRegular;
    }

    public void setMonitorRegular(String monitorRegular) {
        this.monitorRegular = monitorRegular == null ? null : monitorRegular.trim();
    }

    public String getContentTemplate() {
        return contentTemplate;
    }

    public void setContentTemplate(String contentTemplate) {
        this.contentTemplate = contentTemplate == null ? null : contentTemplate.trim();
    }

    public Integer getUseTmp() {
        return useTmp;
    }

    public void setUseTmp(Integer useTmp) {
        this.useTmp = useTmp;
    }

    public Integer getHandleType() {
        return handleType;
    }

    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }

    public Integer getCalTime() {
        return calTime;
    }

    public void setCalTime(Integer calTime) {
        this.calTime = calTime;
    }

    public Integer getCalNum() {
        return calNum;
    }

    public void setCalNum(Integer calNum) {
        this.calNum = calNum;
    }

    public Integer getSendInterval() {
        return sendInterval;
    }

    public void setSendInterval(Integer sendInterval) {
        this.sendInterval = sendInterval;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
