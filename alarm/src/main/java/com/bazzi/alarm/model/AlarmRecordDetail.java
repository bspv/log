package com.bazzi.alarm.model;

import javax.persistence.Id;
import java.util.Date;

public class AlarmRecordDetail {
    @Id
    private Long id;

    private Long alarmRecordId;

    private String recipient;

    private Integer alarmMode;

    private Integer sendStatus;

    private String failMsg;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlarmRecordId() {
        return alarmRecordId;
    }

    public void setAlarmRecordId(Long alarmRecordId) {
        this.alarmRecordId = alarmRecordId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient == null ? null : recipient.trim();
    }

    public Integer getAlarmMode() {
        return alarmMode;
    }

    public void setAlarmMode(Integer alarmMode) {
        this.alarmMode = alarmMode;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg == null ? null : failMsg.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}