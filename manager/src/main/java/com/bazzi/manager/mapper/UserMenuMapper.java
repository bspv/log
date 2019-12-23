package com.bazzi.manager.mapper;

import com.bazzi.manager.bean.BatchUserMenu;
import com.bazzi.manager.model.UserMenu;
import com.bazzi.manager.mybatis.BaseMapper;

public interface UserMenuMapper extends BaseMapper<UserMenu> {

    int batchInsert(BatchUserMenu batchUserMenu);
}
