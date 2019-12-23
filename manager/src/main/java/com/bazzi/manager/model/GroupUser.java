package com.bazzi.manager.model;

import javax.persistence.Id;
import java.util.Date;

public class GroupUser {
    @Id
    private Long id;

    private Integer userId;

    private Integer alarmGroupId;

    private String createUser;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAlarmGroupId() {
        return alarmGroupId;
    }

    public void setAlarmGroupId(Integer alarmGroupId) {
        this.alarmGroupId = alarmGroupId;
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
}
