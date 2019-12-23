package com.bazzi.manager.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BatchUserMenu {
    private Integer userId;

    private List<Integer> menuIds;

    private String createUser;

    private Date createTime;
}
