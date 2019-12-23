package com.bazzi.manager.service;

import com.bazzi.common.generic.Page;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.*;

public interface AlarmGroupService {
    /**
     * 用户组列表
     * @param alarmGroupListReqVO
     * @return
     */
    Page<AlarmGroupVO> list(AlarmGroupListReqVO alarmGroupListReqVO);

    /**
     * 添加用户组
     * @param alarmGroupReqVO
     * @return
     */
    IdResponseVO add(AlarmGroupReqVO alarmGroupReqVO);

    /**
     * 修改用户组
     * @param alarmGroupUpdateReqVO
     * @return
     */
    StringResponseVO update(AlarmGroupUpdateReqVO alarmGroupUpdateReqVO);

    /**
     * 删除用户组
     * @param idReqVO
     * @return
     */
    StringResponseVO delete(IdReqVO idReqVO);

    /**
     * 修改用户组状态
     * @param statusReqVO
     * @return
     */
    StringResponseVO updateStatus(StatusReqVO statusReqVO);

    /**
     * 查询用户组下的用户
     * @param idReqVO
     * @return
     */
    ListVO<GroupUserResponseVO> queryGroupUser(IdReqVO idReqVO);

    /**
     * 添加用户组的用户
     * @param groupUserReqVO
     * @return
     */
    StringResponseVO addGroupUser(GroupUserReqVO groupUserReqVO);

    /**
     * 查询所有用户组
     * @return
     */
    ListVO<GroupUserAllResponseVO> queryGroupUserAll();
}
