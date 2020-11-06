package com.bazzi.manager.mapper;

import com.bazzi.common.mybatis.BaseMapper;
import com.bazzi.manager.model.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findMenuByUserId(Integer userId);

    int update(Menu menu);
}
