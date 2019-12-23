package com.bazzi.manager.mapper;

import com.bazzi.manager.model.Menu;
import com.bazzi.manager.mybatis.BaseMapper;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findMenuByUserId(Integer userId);

    int update(Menu menu);
}
