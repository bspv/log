package com.bazzi.manager.model;

import javax.persistence.Id;
import java.util.Date;

public class Statistical {
    @Id
    private Long id;

    private Long totalLogCount;

    private Long totalLogTraffic;

    private Long totalMonitorNum;

    private Long totalAlarmNum;

    private String totalDay;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalLogCount() {
        return totalLogCount;
    }

    public void setTotalLogCount(Long totalLogCount) {
        this.totalLogCount = totalLogCount;
    }

    public Long getTotalLogTraffic() {
        return totalLogTraffic;
    }

    public void setTotalLogTraffic(Long totalLogTraffic) {
        this.totalLogTraffic = totalLogTraffic;
    }

    public Long getTotalMonitorNum() {
        return totalMonitorNum;
    }

    public void setTotalMonitorNum(Long totalMonitorNum) {
        this.totalMonitorNum = totalMonitorNum;
    }

    public Long getTotalAlarmNum() {
        return totalAlarmNum;
    }

    public void setTotalAlarmNum(Long totalAlarmNum) {
        this.totalAlarmNum = totalAlarmNum;
    }

    public String getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(String totalDay) {
        this.totalDay = totalDay == null ? null : totalDay.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}