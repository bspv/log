package com.bazzi.manager.mapper;

import com.bazzi.common.mybatis.BaseMapper;
import com.bazzi.manager.model.User;
import com.bazzi.manager.vo.request.UserListReqVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> selectByParams(UserListReqVO userListReqVO);

    int update(User user);

    User selectUserInfo(@Param("userId") Integer userId);
}
