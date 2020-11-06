package com.bazzi.alarm.mapper;

import com.bazzi.alarm.model.User;
import com.bazzi.common.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> findByList(@Param("idList") List<Integer> idList);
}
