package com.bazzi.manager.model;

import javax.persistence.Id;
import java.util.Date;

public class ProjectField {
    @Id
    private Long id;

    private Integer projectId;

    private String fieldName;

    private Integer fieldIdx;

    private String fieldLogRegular;

    private String fieldRegular;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public Integer getFieldIdx() {
        return fieldIdx;
    }

    public void setFieldIdx(Integer fieldIdx) {
        this.fieldIdx = fieldIdx;
    }

    public String getFieldLogRegular() {
        return fieldLogRegular;
    }

    public void setFieldLogRegular(String fieldLogRegular) {
        this.fieldLogRegular = fieldLogRegular == null ? null : fieldLogRegular.trim();
    }

    public String getFieldRegular() {
        return fieldRegular;
    }

    public void setFieldRegular(String fieldRegular) {
        this.fieldRegular = fieldRegular == null ? null : fieldRegular.trim();
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
