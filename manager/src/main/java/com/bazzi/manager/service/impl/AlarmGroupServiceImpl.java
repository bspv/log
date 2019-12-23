package com.bazzi.manager.service.impl;

import com.bazzi.common.ex.BusinessException;
import com.bazzi.common.generic.LogStatusCode;
import com.bazzi.common.generic.NotifyAlarm;
import com.bazzi.common.generic.Page;
import com.bazzi.manager.mapper.AlarmGroupMapper;
import com.bazzi.manager.mapper.GroupUserMapper;
import com.bazzi.manager.mapper.UserMapper;
import com.bazzi.manager.model.AlarmGroup;
import com.bazzi.manager.model.GroupUser;
import com.bazzi.manager.model.User;
import com.bazzi.manager.service.AlarmGroupService;
import com.bazzi.manager.service.NotifyService;
import com.bazzi.manager.util.ThreadLocalUtil;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName AlarmGroupServiceImpl
 * @Author baoyf
 * @Date 2019/1/23 16:35
 **/
@Slf4j
@Component
public class AlarmGroupServiceImpl implements AlarmGroupService {

    @Resource
    private AlarmGroupMapper alarmGroupMapper;

    @Resource
    private GroupUserMapper groupUserMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private NotifyService notifyService;

    /**
     * 用户组列表
     * @param alarmGroupListReqVO
     * @return
     */
    public Page<AlarmGroupVO> list(AlarmGroupListReqVO alarmGroupListReqVO) {
        int pageIdx = alarmGroupListReqVO.getPageIdx();
        int pageSize = alarmGroupListReqVO.getPageSize();
        PageHelper.startPage(pageIdx,pageSize);
        List<AlarmGroup> list = alarmGroupMapper.selectAlarmGroupList(alarmGroupListReqVO);
        PageInfo pageInfo = PageInfo.of(list);
        List<AlarmGroupVO> listVO = new ArrayList<>();
        for (AlarmGroup alarmGroup : list) {
            AlarmGroupVO alarmGroupVO = new AlarmGroupVO();
            BeanUtils.copyProperties(alarmGroup,alarmGroupVO);
            listVO.add(alarmGroupVO);
        }
        return Page.of(listVO,pageIdx,pageSize,(int) pageInfo.getTotal());
    }

    /**
     * 添加用户组
     * @param alarmGroupReqVO
     * @return
     */
    public IdResponseVO add(AlarmGroupReqVO alarmGroupReqVO) {
        User user = ThreadLocalUtil.getUser();
        AlarmGroup alarmGroup = new AlarmGroup();
        alarmGroup.setGroupName(alarmGroupReqVO.getGroupName());
        if (alarmGroupMapper.selectOne(alarmGroup) != null){
            throw new BusinessException(LogStatusCode.CODE_115);
        }
        BeanUtils.copyProperties(alarmGroupReqVO,alarmGroup);
        alarmGroup.setVersion(0);
        alarmGroup.setCreateUser(user.getUserName());
        alarmGroup.setCreateTime(new Date());
        alarmGroupMapper.insertUseGeneratedKeys(alarmGroup);

        notifyService.sendNotifyAlarm(NotifyAlarm.ofAlarmGroup(alarmGroup.getId()));
        return new IdResponseVO(alarmGroup.getId());
    }

    /**
     * 修改用户组
     * @param alarmGroupUpdateReqVO
     * @return
     */
    public StringResponseVO update(AlarmGroupUpdateReqVO alarmGroupUpdateReqVO) {
        AlarmGroup alarmGroup = alarmGroupMapper.selectByPrimaryKey(alarmGroupUpdateReqVO.getId());
        if (alarmGroup == null){
            throw new BusinessException(LogStatusCode.CODE_110);
        }
        alarmGroup.setGroupName(alarmGroupUpdateReqVO.getGroupName());
        alarmGroup.setId(alarmGroupUpdateReqVO.getId());
        AlarmGroup alarmGroupAgain = alarmGroupMapper.selectCheckAgain(alarmGroup);
        if (alarmGroupAgain != null){
            throw new BusinessException(LogStatusCode.CODE_115);
        }
        BeanUtils.copyProperties(alarmGroupUpdateReqVO,alarmGroup);
        User user = ThreadLocalUtil.getUser();
        alarmGroup.setUpdateUser(user.getUserName());
        alarmGroup.setUpdateTime(new Date());
        int i = alarmGroupMapper.update(alarmGroup);
        if (i == 0){
            throw new BusinessException(LogStatusCode.CODE_112);
        }

        notifyService.sendNotifyAlarm(NotifyAlarm.ofAlarmGroup(alarmGroup.getId()));
        return new StringResponseVO();
    }

    /**
     * 删除用户组
     * @param idReqVO
     * @return
     */
    public StringResponseVO delete(IdReqVO idReqVO) {
        alarmGroupMapper.deleteByPrimaryKey(idReqVO.getId());
        GroupUser groupUser = new GroupUser();
        groupUser.setAlarmGroupId(idReqVO.getId());
        groupUserMapper.delete(groupUser);

        notifyService.sendNotifyAlarm(NotifyAlarm.ofAlarmGroup(idReqVO.getId()));
        return new StringResponseVO();
    }

    /**
     * 修改用户组状态
     * @param statusReqVO
     * @return
     */
    public StringResponseVO updateStatus(StatusReqVO statusReqVO) {
        AlarmGroup alarmGroup = alarmGroupMapper.selectByPrimaryKey(statusReqVO.getId());
        if (alarmGroup == null)
            throw new BusinessException(LogStatusCode.CODE_110);

        alarmGroup.setStatus(statusReqVO.getStatus());
        alarmGroup.setUpdateUser(ThreadLocalUtil.getUser().getUserName());
        alarmGroup.setUpdateTime(new Date());
        int i = alarmGroupMapper.update(alarmGroup);
        if (i == 0){
            throw new BusinessException(LogStatusCode.CODE_113);
        }

        notifyService.sendNotifyAlarm(NotifyAlarm.ofAlarmGroup(statusReqVO.getId()));
        return new StringResponseVO();
    }

    /**
     * 查询用户组下的用户
     * @param idReqVO
     * @return
     */
    public ListVO<GroupUserResponseVO> queryGroupUser(IdReqVO idReqVO) {
        List<GroupUser> list = groupUserMapper.selectGroupUserId(idReqVO.getId());
        List<GroupUserResponseVO> listVO = new ArrayList<>();
        if (list != null && list.size() > 0){
            for (GroupUser groupUser : list) {
                User user = userMapper.selectUserInfo(groupUser.getUserId());
                GroupUserResponseVO groupUserResponseVO = new GroupUserResponseVO();
                if (user != null){
                    BeanUtils.copyProperties(user,groupUserResponseVO);
                }
                listVO.add(groupUserResponseVO);
            }
        }
        return new ListVO<>(listVO);
    }

    /**
     * 添加用户组的用户
     * @param groupUserReqVO
     * @return
     */
    public StringResponseVO addGroupUser(GroupUserReqVO groupUserReqVO) {
        User user = ThreadLocalUtil.getUser();
        int alarmGroupId = groupUserReqVO.getAlarmGroupId();
        GroupUser groupUser = new GroupUser();
        groupUser.setAlarmGroupId(alarmGroupId);
        groupUserMapper.delete(groupUser);
        List<Integer> list = groupUserReqVO.getUserList();
        for (Integer integer : list) {
            groupUser.setAlarmGroupId(alarmGroupId);
            List<GroupUser> groupUserId = groupUserMapper.selectByUserId(groupUser);
            for (GroupUser groupUsers : groupUserId) {
                if (groupUsers.getUserId().intValue() == integer.intValue()){
                    throw new BusinessException(LogStatusCode.CODE_120.getCode(),String.format(LogStatusCode.CODE_120.getMessage(),String.valueOf(groupUsers.getUserId())));
                }
            }
            groupUser.setAlarmGroupId(alarmGroupId);
            groupUser.setUserId(integer);
            groupUser.setCreateUser(user.getUserName());
            groupUser.setCreateTime(new Date());
            int i = groupUserMapper.insert(groupUser);
            if (i == 0){
                throw new BusinessException(LogStatusCode.CODE_114);
            }
        }

        notifyService.sendNotifyAlarm(NotifyAlarm.ofAlarmGroup(alarmGroupId));
        return new StringResponseVO();
    }

    /**
     * 查询所有用户组
     * @return
     */
    public ListVO<GroupUserAllResponseVO> queryGroupUserAll() {
        List<GroupUserAllResponseVO> listVO = new ArrayList<>();
        List<AlarmGroup> list = alarmGroupMapper.selectAll();
        if (list != null && list.size() > 0){
            for (AlarmGroup alarmGroup : list) {
                GroupUserAllResponseVO groupUserAllResponseVO = new GroupUserAllResponseVO();
                BeanUtils.copyProperties(alarmGroup,groupUserAllResponseVO);
                listVO.add(groupUserAllResponseVO);
            }
        }
        return new ListVO<>(listVO);
    }
}
