package com.bazzi.manager.mapper;

import com.bazzi.common.mybatis.BaseMapper;
import com.bazzi.manager.bean.BatchUserMenu;
import com.bazzi.manager.model.UserMenu;

public interface UserMenuMapper extends BaseMapper<UserMenu> {

    int batchInsert(BatchUserMenu batchUserMenu);
}
