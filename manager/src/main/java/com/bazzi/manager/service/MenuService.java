package com.bazzi.manager.service;

import com.bazzi.manager.model.User;
import com.bazzi.manager.vo.request.IdReqVO;
import com.bazzi.manager.vo.request.MenuReqVO;
import com.bazzi.manager.vo.request.MenuUpdateReqVO;
import com.bazzi.manager.vo.response.IdResponseVO;
import com.bazzi.manager.vo.response.MenuVO;
import com.bazzi.manager.vo.response.StringResponseVO;

import java.util.List;

public interface MenuService {

    /**
     * 查询所有菜单
     *
     * @return 菜单集合
     */
    List<MenuVO> selectAllMenu();

    /**
     * 查询某个用户的菜单
     *
     * @param user 用户
     * @return 菜单集合
     */
    List<MenuVO> findMenuByUser(User user);

    /**
     * 添加菜单
     *
     * @param menuReqVO 菜单信息
     * @return 新增结果
     */
    IdResponseVO add(MenuReqVO menuReqVO);

    /**
     * 更新菜单
     *
     * @param menuUpdateReqVO 菜单更新信息
     * @return 更新结果
     */
    StringResponseVO update(MenuUpdateReqVO menuUpdateReqVO);

    /**
     * 删除菜单，并删除给用户授权关联表数据
     *
     * @param idReqVO id
     * @return 删除结果
     */
    StringResponseVO delete(IdReqVO idReqVO);
}
