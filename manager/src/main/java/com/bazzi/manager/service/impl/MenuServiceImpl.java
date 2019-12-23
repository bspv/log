package com.bazzi.manager.service.impl;

import com.bazzi.common.ex.BusinessException;
import com.bazzi.common.generic.LogStatusCode;
import com.bazzi.manager.mapper.MenuMapper;
import com.bazzi.manager.mapper.UserMenuMapper;
import com.bazzi.manager.model.Menu;
import com.bazzi.manager.model.User;
import com.bazzi.manager.model.UserMenu;
import com.bazzi.manager.service.MenuService;
import com.bazzi.manager.util.Constant;
import com.bazzi.manager.util.ThreadLocalUtil;
import com.bazzi.manager.vo.request.IdReqVO;
import com.bazzi.manager.vo.request.MenuReqVO;
import com.bazzi.manager.vo.request.MenuUpdateReqVO;
import com.bazzi.manager.vo.response.IdResponseVO;
import com.bazzi.manager.vo.response.MenuVO;
import com.bazzi.manager.vo.response.StringResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private UserMenuMapper userMenuMapper;

    @Override
    public List<MenuVO> selectAllMenu() {
        List<Menu> menuList = menuMapper.selectAll();
        return convertMenu(menuList, -1);
    }

    @Override
    public List<MenuVO> findMenuByUser(User user) {
        List<Menu> menuList = Constant.ADMIN_NAME.equals(user.getUserName()) ?
                menuMapper.selectAll() : menuMapper.findMenuByUserId(user.getId());
        return convertMenu(menuList, -1);
    }

    @Override
    public IdResponseVO add(MenuReqVO menuReqVO) {
        Menu menu = new Menu();
        menu.setMenuUrl(menuReqVO.getMenuUrl());
        if (menuMapper.selectOne(menu) != null) {
            throw new BusinessException(LogStatusCode.CODE_101);
        }
        BeanUtils.copyProperties(menuReqVO, menu);
        menu.setVersion(0);
        User user = ThreadLocalUtil.getUser();
        menu.setCreateUser(user.getUserName());
        menu.setCreateTime(new Date());
        menuMapper.insertUseGeneratedKeys(menu);
        return new IdResponseVO(menu.getId());
    }

    @Override
    public StringResponseVO update(MenuUpdateReqVO menuUpdateReqVO) {
        Menu menu = menuMapper.selectByPrimaryKey(menuUpdateReqVO.getId());
        if (menu == null) {
            throw new BusinessException(LogStatusCode.CODE_102);
        }
        BeanUtils.copyProperties(menuUpdateReqVO, menu);
        User user = ThreadLocalUtil.getUser();
        menu.setUpdateUser(user.getUserName());
        menu.setUpdateTime(new Date());
        int i = menuMapper.update(menu);
        if (i == 0)
            throw new BusinessException(LogStatusCode.CODE_107);
        return new StringResponseVO();
    }

    public StringResponseVO delete(IdReqVO idReqVO) {
        menuMapper.deleteByPrimaryKey(idReqVO.getId());
        UserMenu um = new UserMenu();
        um.setMenuId(idReqVO.getId());
        userMenuMapper.delete(um);
        return new StringResponseVO();
    }

    /**
     * 根据pid组织菜单层级结构
     *
     * @param menuList 菜单集合
     * @param pid      菜单父id
     * @return 菜单结果
     */
    private static List<MenuVO> convertMenu(List<Menu> menuList, int pid) {
        List<MenuVO> menuVOList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getPid() == pid) {
                MenuVO menuVO = new MenuVO();
                BeanUtils.copyProperties(menu, menuVO);
                menuVO.setChildMenu(convertMenu(menuList, menu.getId()));
                menuVOList.add(menuVO);
            }
        }
        menuVOList.sort((m1, m2) -> m2.getWeight() - m1.getWeight());
        return menuVOList;
    }
}
