package com.bazzi.manager.mapper;

import com.bazzi.manager.model.GroupUser;
import com.bazzi.manager.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupUserMapper  extends BaseMapper<GroupUser> {

    List<GroupUser> selectGroupUserId(@Param("id") Integer id);

    List<GroupUser> selectByUserId(GroupUser groupUser);
}
