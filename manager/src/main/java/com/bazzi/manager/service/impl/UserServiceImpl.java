package com.bazzi.manager.service.impl;

import com.bazzi.common.ex.BusinessException;
import com.bazzi.common.generic.LogStatusCode;
import com.bazzi.common.generic.NotifyAlarm;
import com.bazzi.common.generic.Page;
import com.bazzi.common.util.DigestUtil;
import com.bazzi.manager.bean.BatchUserMenu;
import com.bazzi.manager.bean.UserDO;
import com.bazzi.manager.config.DefinitionProperties;
import com.bazzi.manager.mapper.GroupUserMapper;
import com.bazzi.manager.mapper.MenuMapper;
import com.bazzi.manager.mapper.UserMapper;
import com.bazzi.manager.mapper.UserMenuMapper;
import com.bazzi.manager.model.GroupUser;
import com.bazzi.manager.model.Menu;
import com.bazzi.manager.model.User;
import com.bazzi.manager.model.UserMenu;
import com.bazzi.manager.service.MenuService;
import com.bazzi.manager.service.NotifyService;
import com.bazzi.manager.service.RedisService;
import com.bazzi.manager.service.UserService;
import com.bazzi.manager.util.Constant;
import com.bazzi.manager.util.ThreadLocalUtil;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private UserMenuMapper userMenuMapper;

    @Resource
    private GroupUserMapper groupUserMapper;

    @Resource
    private MenuService menuService;

    @Resource
    private RedisService redisService;

    @Resource
    private NotifyService notifyService;

    @Resource
    private DefinitionProperties properties;


    public UserResponseVO login(LoginReqVO loginReqVO) {
        User u = new User();
        u.setUserName(loginReqVO.getUserName());
        User user = userMapper.selectOne(u);
        if (user == null || !user.getPassword().equals(loginReqVO.getPassword()))
            throw new BusinessException(LogStatusCode.CODE_103);
        if (user.getStatus() != null && user.getStatus() != 0)
            throw new BusinessException(LogStatusCode.CODE_104);
        u.setLastLoginTime(new Date());
        userMapper.updateByPrimaryKey(u);//更新用户最后登录时间
        List<MenuVO> menuVOList = menuService.findMenuByUser(user);
        UserResponseVO userVO = new UserResponseVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setMenuList(menuVOList);
        String dynamic = UUID.randomUUID().toString();
        userVO.setDynamicToken(dynamic);

        //使用UUID生成token，并防止redis中，同时放到线程里
        String key = String.format(Constant.SESSION_USER_KEY, properties.getCachePrefix(), dynamic);
        redisService.setEx(key, new UserDO(user, userVO), Constant.USER_EXPIRE_TIME);
        ThreadLocalUtil.setUser(user);
        ThreadLocalUtil.setUserInfo(userVO);
        return userVO;
    }


    public Page<UserVO> list(UserListReqVO userListReqVO) {
        int pageIdx = userListReqVO.getPageIdx();
        int pageSize = userListReqVO.getPageSize();
        PageHelper.startPage(userListReqVO.getPageIdx(), userListReqVO.getPageSize());
        List<User> list = userMapper.selectByParams(userListReqVO);
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : list) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        }
        return Page.of(userVOList, pageIdx, pageSize, (int) PageInfo.of(list).getTotal());
    }

    public IdResponseVO add(UserReqVO userReqVO) {
        User u = new User();
        u.setUserName(userReqVO.getUserName());
        if (userMapper.selectOne(u) != null) {
            throw new BusinessException(LogStatusCode.CODE_105);
        }
        BeanUtils.copyProperties(userReqVO, u);
        u.setPassword(DigestUtil.toMd5Upper(u.getPassword()));
        u.setVersion(0);
        User user = ThreadLocalUtil.getUser();
        u.setCreateUser(user.getUserName());
        u.setCreateTime(new Date());
        userMapper.insertUseGeneratedKeys(u);

        notifyAlarmByUserId(u.getId());
        return new IdResponseVO(u.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,
            rollbackFor = {BusinessException.class, Exception.class})
    public StringResponseVO update(UserUpdateReqVO userUpdateReqVO) {
        User u = userMapper.selectByPrimaryKey(userUpdateReqVO.getId());
        if (u == null) {
            throw new BusinessException(LogStatusCode.CODE_106);
        }
        BeanUtils.copyProperties(userUpdateReqVO, u);
        String pwd = userUpdateReqVO.getPassword();
        if (pwd != null && !"".equals(pwd)) {
            if (!Pattern.compile("\\S{6,20}").matcher(pwd).matches()) {
                throw new BusinessException(LogStatusCode.CODE_126);
            }
            u.setPassword(DigestUtil.toMd5Upper(pwd));
        }
        User user = ThreadLocalUtil.getUser();
        u.setUpdateUser(user.getUserName());
        u.setUpdateTime(new Date());
        int i = userMapper.update(u);
        if (i == 0)
            throw new BusinessException(LogStatusCode.CODE_107);

        notifyAlarmByUserId(u.getId());
        return new StringResponseVO();
    }

    public StringResponseVO updateStatus(StatusReqVO statusReqVO) {
        User u = userMapper.selectByPrimaryKey(statusReqVO.getId());
        if (u == null) {
            throw new BusinessException(LogStatusCode.CODE_106);
        }

        u.setStatus(statusReqVO.getStatus());
        u.setUpdateUser(ThreadLocalUtil.getUser().getUserName());
        u.setUpdateTime(new Date());
        int i = userMapper.update(u);
        if (i == 0)
            throw new BusinessException(LogStatusCode.CODE_107);

        notifyAlarmByUserId(u.getId());
        return new StringResponseVO();
    }

    public StringResponseVO delete(IdReqVO idReqVO) {
        userMapper.deleteByPrimaryKey(idReqVO.getId());
        UserMenu um = new UserMenu();
        um.setUserId(idReqVO.getId());
        userMenuMapper.delete(um);

        notifyAlarmByUserId(idReqVO.getId());
        return new StringResponseVO();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,
            rollbackFor = {BusinessException.class, Exception.class})
    public StringResponseVO editUserMenu(UserMenuReqVO userMenuReqVO) {
        UserMenu um = new UserMenu();
        um.setUserId(userMenuReqVO.getUserId());
        userMenuMapper.delete(um);

        User user = ThreadLocalUtil.getUser();
        BatchUserMenu bum = new BatchUserMenu();
        bum.setUserId(userMenuReqVO.getUserId());
        bum.setMenuIds(userMenuReqVO.getMenuIds());
        bum.setCreateUser(user.getUserName());
        bum.setCreateTime(new Date());

        userMenuMapper.batchInsert(bum);
        return new StringResponseVO();
    }

    public List<MenuFillVO> findMenuId(IdReqVO idReqVO) {
        List<MenuFillVO> list = new ArrayList<>();
        List<Menu> menuList = menuMapper.findMenuByUserId(idReqVO.getId());
        for (Menu menu : menuList) {
            MenuFillVO menuFillVO = new MenuFillVO();
            BeanUtils.copyProperties(menu, menuFillVO);
            list.add(menuFillVO);
        }
        return list;
    }

    public List<UserSimpleVO> findAllUser() {
        List<UserSimpleVO> list = new ArrayList<>();
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            UserSimpleVO userSimpleVO = new UserSimpleVO();
            BeanUtils.copyProperties(user, userSimpleVO);
            list.add(userSimpleVO);
        }
        return list;
    }

    public void putUserIfExists(String dynamicToken) {
        if (dynamicToken == null || "".equals(dynamicToken))
            return;
        String key = String.format(Constant.SESSION_USER_KEY, properties.getCachePrefix(), dynamicToken);
        if (!redisService.exists(key))
            return;
        redisService.expire(key, Constant.USER_EXPIRE_TIME);
        UserDO userDO = (UserDO) redisService.get(key);
        ThreadLocalUtil.setUser(userDO.getUser());
        ThreadLocalUtil.setUserInfo(userDO.getUserResponseVO());
    }

    public void logout() {
        UserResponseVO userInfo = ThreadLocalUtil.getUserInfo();
        if (userInfo == null)
            return;
        String dynamicToken = userInfo.getDynamicToken();
        String key = String.format(Constant.SESSION_USER_KEY, properties.getCachePrefix(), dynamicToken);
        redisService.delete(key);
    }

    /**
     * 如果用户id能找到对应的报警用户组id，则通知alarm模块更新缓存
     *
     * @param id 用户id
     */
    private void notifyAlarmByUserId(int id) {
        GroupUser groupUser = new GroupUser();
        groupUser.setUserId(id);
        List<GroupUser> list = groupUserMapper.select(groupUser);
        if (list.size() > 0) {
            List<Integer> alarmGroupIdList = list.stream().
                    map(GroupUser::getAlarmGroupId).collect(Collectors.toList());
            notifyService.sendNotifyAlarm(NotifyAlarm.ofAlarmGroup(alarmGroupIdList));
        }
    }

}
