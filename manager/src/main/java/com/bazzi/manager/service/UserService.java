package com.bazzi.manager.service;

import com.bazzi.common.generic.Page;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.*;

import java.util.List;

public interface UserService {
    /**
     * 用户登录
     *
     * @param loginReqVO 用户信息
     * @return 登录返回的用户信息
     */
    UserResponseVO login(LoginReqVO loginReqVO);

    /**
     * 根据条件查询用户列表
     *
     * @param userListReqVO 查询条件
     * @return 用户列表
     */
    Page<UserVO> list(UserListReqVO userListReqVO);

    /**
     * 添加用户
     *
     * @param userReqVO 用户信息
     * @return 添加结果
     */
    IdResponseVO add(UserReqVO userReqVO);

    /**
     * 更新用户信息
     *
     * @param userUpdateReqVO 用户信息
     * @return 更新结果
     */
    StringResponseVO update(UserUpdateReqVO userUpdateReqVO);

    /**
     * 更改用户状态
     *
     * @param statusReqVO 用户信息
     * @return 更改状态结果
     */
    StringResponseVO updateStatus(StatusReqVO statusReqVO);

    /**
     * 删除用户
     *
     * @param idReqVO 用户id
     * @return 删除结果
     */
    StringResponseVO delete(IdReqVO idReqVO);

    /**
     * 编辑用户菜单
     *
     * @param userMenuReqVO 用户菜单信息
     * @return 编辑结果
     */
    StringResponseVO editUserMenu(UserMenuReqVO userMenuReqVO);

    /**
     * 根据用户id查询菜单
     *
     * @param idReqVO 用户id
     * @return 菜单信息
     */
    List<MenuFillVO> findMenuId(IdReqVO idReqVO);

    /**
     * 查询所有用户
     *
     * @return 用户集合
     */
    List<UserSimpleVO> findAllUser();

    /**
     * 如果dynamicToken对应用户已登录，则放到线程本地变量，并延长token有效期
     *
     * @param dynamicToken 动态token
     */
    void putUserIfExists(String dynamicToken);

    /**
     * 登出
     */
    void logout();

}
