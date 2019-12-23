package com.bazzi.manager.model;

import javax.persistence.Id;
import java.util.Date;

public class AlarmRecord {
    @Id
    private Long id;

    private Integer projectId;

    private String projectName;

    private String logFileName;

    private Integer captureType;

    private Integer projectStatus;

    private Integer monitorId;

    private String monitorName;

    private String monitorRegular;

    private Integer useTmp;

    private Integer handleType;

    private Integer calTime;

    private Integer calNum;

    private Integer sendInterval;

    private Integer priority;

    private Integer monitorStatus;

    private Integer alarmId;

    private String alarmName;

    private String alarmMode;

    private Integer alarmStatus;

    private Integer alarmGroupId;

    private String groupName;

    private Integer groupStatus;

    private Integer userNum;

    private String ip;

    private Date firstTrigger;

    private Date triggerTime;

    private Integer num;

    private String platform;

    private Integer status;

    private String errCode;

    private String errMsg;

    private Integer reserve;

    private String remark;

    private Date createTime;

    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
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

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Integer getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName == null ? null : monitorName.trim();
    }

    public String getMonitorRegular() {
        return monitorRegular;
    }

    public void setMonitorRegular(String monitorRegular) {
        this.monitorRegular = monitorRegular == null ? null : monitorRegular.trim();
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

    public Integer getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(Integer monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    public Integer getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName == null ? null : alarmName.trim();
    }

    public String getAlarmMode() {
        return alarmMode;
    }

    public void setAlarmMode(String alarmMode) {
        this.alarmMode = alarmMode == null ? null : alarmMode.trim();
    }

    public Integer getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(Integer alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public Integer getAlarmGroupId() {
        return alarmGroupId;
    }

    public void setAlarmGroupId(Integer alarmGroupId) {
        this.alarmGroupId = alarmGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getFirstTrigger() {
        return firstTrigger;
    }

    public void setFirstTrigger(Date firstTrigger) {
        this.firstTrigger = firstTrigger;
    }

    public Date getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Date triggerTime) {
        this.triggerTime = triggerTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode == null ? null : errCode.trim();
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg == null ? null : errMsg.trim();
    }

    public Integer getReserve() {
        return reserve;
    }

    public void setReserve(Integer reserve) {
        this.reserve = reserve;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }
}
