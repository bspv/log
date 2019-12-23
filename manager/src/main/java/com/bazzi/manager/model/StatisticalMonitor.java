package com.bazzi.manager.model;

import javax.persistence.Id;
import java.util.Date;

public class StatisticalMonitor {
    @Id
    private Long id;

    private Integer projectId;

    private Integer monitorId;

    private Integer alarmId;

    private Long monitorNum;

    private Long alarmNum;

    private String totalDay;

    private String totalHour;

    private String totalTime;

    private Date createTime;

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

    public Integer getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
    }

    public Integer getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public Long getMonitorNum() {
        return monitorNum;
    }

    public void setMonitorNum(Long monitorNum) {
        this.monitorNum = monitorNum;
    }

    public Long getAlarmNum() {
        return alarmNum;
    }

    public void setAlarmNum(Long alarmNum) {
        this.alarmNum = alarmNum;
    }

    public String getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(String totalDay) {
        this.totalDay = totalDay == null ? null : totalDay.trim();
    }

    public String getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(String totalHour) {
        this.totalHour = totalHour == null ? null : totalHour.trim();
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime == null ? null : totalTime.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}