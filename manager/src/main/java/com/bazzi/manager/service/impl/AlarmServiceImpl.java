package com.bazzi.manager.service.impl;

import com.bazzi.common.ex.BusinessException;
import com.bazzi.common.generic.LogStatusCode;
import com.bazzi.common.generic.NotifyAlarm;
import com.bazzi.common.generic.Page;
import com.bazzi.manager.mapper.AlarmMapper;
import com.bazzi.manager.mapper.GroupUserMapper;
import com.bazzi.manager.mapper.MonitorMapper;
import com.bazzi.manager.mapper.UserMapper;
import com.bazzi.manager.model.Alarm;
import com.bazzi.manager.model.GroupUser;
import com.bazzi.manager.model.Monitor;
import com.bazzi.manager.model.User;
import com.bazzi.manager.service.AlarmService;
import com.bazzi.manager.service.NotifyService;
import com.bazzi.manager.util.ThreadLocalUtil;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.AlarmResponseVO;
import com.bazzi.manager.vo.response.AlarmVO;
import com.bazzi.manager.vo.response.IdResponseVO;
import com.bazzi.manager.vo.response.StringResponseVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName AlarmServiceImpl
 * @Author baoyf
 * @Date 2019/1/24 11:22
 **/
@Slf4j
@Component
public class AlarmServiceImpl implements AlarmService {

    @Resource
    private AlarmMapper alarmMapper;

    @Resource
    private GroupUserMapper groupUserMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private NotifyService notifyService;

    @Resource
    private MonitorMapper monitorMapper;

    /**
     * 报警配置列表
     * @param alarmListReqVO
     * @return
     */
    public Page<AlarmVO> list(AlarmListReqVO alarmListReqVO) {
        int pageIdx = alarmListReqVO.getPageIdx();
        int pageSize = alarmListReqVO.getPageSize();
        PageHelper.startPage(pageIdx,pageSize);
        List<Alarm> list = alarmMapper.selectAlarmList(alarmListReqVO);
        PageInfo pageInfo = PageInfo.of(list);
        List<AlarmVO> listVO = new ArrayList<>();
        for (Alarm alarm : list) {
            AlarmVO alarmVO = new AlarmVO();
            BeanUtils.copyProperties(alarm,alarmVO);
            listVO.add(alarmVO);
        }
        return Page.of(listVO,pageIdx,pageSize,(int) pageInfo.getTotal());
    }

    /**
     * 添加报警配置
     * @param alarmAddReqVO
     * @return
     */
    public IdResponseVO add(AlarmAddReqVO alarmAddReqVO) {
        User user = ThreadLocalUtil.getUser();
        List<String> alarmModeList = alarmAddReqVO.getAlarmMode();
        String alarmMode = Joiner.on("#").skipNulls().join(alarmModeList);
        //查询用户组中用户是否有微信号
        if (alarmMode.contains("2")){
            int id = alarmAddReqVO.getAlarmGroupId();
            List<GroupUser> list = groupUserMapper.selectGroupUserId(id);
            for (GroupUser groupUser : list) {
                User u = userMapper.selectUserInfo(groupUser.getUserId());
                if (u.getWechat() != null && !u.getWechat().equals("")){
                    throw new BusinessException(LogStatusCode.CODE_116);
                }
            }
        }
        Alarm alarm = new Alarm();
        alarm.setAlarmName(alarmAddReqVO.getAlarmName());
        if (alarmMapper.selectOne(alarm) != null){
            throw new BusinessException(LogStatusCode.CODE_117);
        }
        BeanUtils.copyProperties(alarmAddReqVO,alarm);
        alarm.setAlarmMode(alarmMode);
        alarm.setVersion(0);
        alarm.setCreateUser(user.getUserName());
        alarm.setCreateTime(new Date());
        alarmMapper.insertUseGeneratedKeys(alarm);

        notifyService.sendNotifyAlarm(NotifyAlarm.ofAlarm(alarm.getId()));
        return new IdResponseVO(alarm.getId());
    }

    /**
     * 修改报警配置
     * @param alarmUpdateReqVO
     * @return
     */
    public StringResponseVO update(AlarmUpdateReqVO alarmUpdateReqVO) {
        Alarm alarm = alarmMapper.selectByPrimaryKey(alarmUpdateReqVO.getId());
        if (alarm == null){
            throw new BusinessException(LogStatusCode.CODE_118);
        }
        alarm.setAlarmName(alarmUpdateReqVO.getAlarmName());
        alarm.setId(alarmUpdateReqVO.getId());
        Alarm alarmAgain = alarmMapper.selectCheckAgain(alarm);
        if (alarmAgain != null){
            throw new BusinessException(LogStatusCode.CODE_117);
        }
        List<String> alarmModeList = alarmUpdateReqVO.getAlarmMode();
        String alarmMode = Joiner.on("#").skipNulls().join(alarmModeList);
        //查询用户组中用户是否有微信号
        if (alarmMode.contains("2")){
            int id = alarmUpdateReqVO.getAlarmGroupId();
            List<GroupUser> list = groupUserMapper.selectGroupUserId(id);
            for (GroupUser groupUser : list) {
                User u = userMapper.selectUserInfo(groupUser.getUserId());
                if (u.getWechat() != null && !u.getWechat().equals("")){
                    throw new BusinessException(LogStatusCode.CODE_116);
                }
            }
        }
        User user = ThreadLocalUtil.getUser();
        BeanUtils.copyProperties(alarmUpdateReqVO,alarm);
        alarm.setAlarmMode(alarmMode);
        alarm.setUpdateUser(user.getUserName());
        alarm.setUpdateTime(new Date());
        int i = alarmMapper.update(alarm);
        if (i == 0){
            throw new BusinessException(LogStatusCode.CODE_119);
        }

        notifyService.sendNotifyAlarm(NotifyAlarm.ofAlarm(alarm.getId()));
        return new StringResponseVO();
    }

    /**
     * 删除报警配置
     * @param idReqVO
     * @return
     */
    public StringResponseVO delete(IdReqVO idReqVO) {
        Monitor monitor = new Monitor();
        monitor.setAlarmId(idReqVO.getId());
        if (monitorMapper.selectOne(monitor) != null){
            throw new BusinessException(LogStatusCode.CODE_123);
        }

        alarmMapper.deleteByPrimaryKey(idReqVO.getId());

        notifyService.sendNotifyAlarm(NotifyAlarm.ofAlarm(idReqVO.getId()));
        return new StringResponseVO();
    }

    /**
     * 修改报警配置状态
     * @param statusReqVO
     * @return
     */
    public StringResponseVO updateStatus(StatusReqVO statusReqVO) {
        Alarm alarm = alarmMapper.selectByPrimaryKey(statusReqVO.getId());
        if (alarm == null)
            throw new BusinessException(LogStatusCode.CODE_118);

        alarm.setStatus(statusReqVO.getStatus());
        alarm.setUpdateUser(ThreadLocalUtil.getUser().getUserName());
        alarm.setUpdateTime(new Date());
        int i = alarmMapper.update(alarm);
        if (i == 0){
            throw new BusinessException(LogStatusCode.CODE_113);
        }

        notifyService.sendNotifyAlarm(NotifyAlarm.ofAlarm(statusReqVO.getId()));
        return new StringResponseVO();
    }

    /**
     * 查询所有报警配置
     * @return
     */
    public List<AlarmResponseVO> findAllAlarm() {
        List<AlarmResponseVO> listVO = new ArrayList<>();
        List<Alarm> list = alarmMapper.selectAll();
        for (Alarm alarm : list) {
            AlarmResponseVO alarmResponseVO = new AlarmResponseVO();
            BeanUtils.copyProperties(alarm,alarmResponseVO);
            listVO.add(alarmResponseVO);
        }
        return listVO;
    }
}
