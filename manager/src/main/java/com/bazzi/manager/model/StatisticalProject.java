package com.bazzi.manager.model;

import javax.persistence.Id;
import java.util.Date;

public class StatisticalProject {
    @Id
    private Long id;

    private Integer projectId;

    private Long logCount;

    private Long logTraffic;

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

    public Long getLogCount() {
        return logCount;
    }

    public void setLogCount(Long logCount) {
        this.logCount = logCount;
    }

    public Long getLogTraffic() {
        return logTraffic;
    }

    public void setLogTraffic(Long logTraffic) {
        this.logTraffic = logTraffic;
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