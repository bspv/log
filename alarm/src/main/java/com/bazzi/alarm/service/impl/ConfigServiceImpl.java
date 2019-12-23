package com.bazzi.alarm.service.impl;

import com.bazzi.alarm.config.DefinitionProperties;
import com.bazzi.alarm.mapper.*;
import com.bazzi.alarm.model.*;
import com.bazzi.alarm.service.ConfigService;
import com.bazzi.alarm.service.RedisService;
import com.bazzi.alarm.util.Constant;
import com.bazzi.common.generic.NotifyAlarm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private DefinitionProperties definitionProperties;

    @Resource
    private RedisService redisService;

    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ProjectFieldMapper projectFieldMapper;

    @Resource
    private MonitorMapper monitorMapper;

    @Resource
    private AlarmMapper alarmMapper;
    @Resource
    private AlarmGroupMapper alarmGroupMapper;
    @Resource
    private GroupUserMapper groupUserMapper;
    @Resource
    private UserMapper userMapper;

    public Project findProjectById(Integer id) {
        String redisKey = String.format(Constant.PROJECT_REDIS_KEY,
                definitionProperties.getPermCachePrefix(), id);
        Project project = (Project) redisService.get(redisKey);

        if (project != null)
            return project;

        synchronized (redisKey.intern()) {
            project = (Project) redisService.get(redisKey);
            if (project != null)
                return project;

            project = projectMapper.selectByPrimaryKey(id);
            if (project == null) {
                project = new Project();
            }

            redisService.set(redisKey, project);
        }

        return project;
    }

    public Monitor findMonitorById(Integer id) {
        String redisKey = String.format(Constant.MONITOR_REDIS_KEY,
                definitionProperties.getPermCachePrefix(), id);
        Monitor monitor = (Monitor) redisService.get(redisKey);

        if (monitor != null)
            return monitor;

        synchronized (redisKey.intern()) {
            monitor = (Monitor) redisService.get(redisKey);
            if (monitor != null)
                return monitor;

            monitor = monitorMapper.selectByPrimaryKey(id);
            if (monitor == null) {
                monitor = new Monitor();
            }

            redisService.set(redisKey, monitor);
        }

        return monitor;
    }

    @SuppressWarnings("unchecked")
    public List<ProjectField> findProjectFieldByProjectId(Integer projectId) {
        String redisKey = String.format(Constant.PROJECT_FIELD_REDIS_KEY,
                definitionProperties.getPermCachePrefix(), projectId);
        List<ProjectField> list = (List<ProjectField>) redisService.get(redisKey);

        if (list != null)
            return list;

        synchronized (redisKey.intern()) {
            list = (List<ProjectField>) redisService.get(redisKey);

            if (list != null)
                return list;

            ProjectField projectField = new ProjectField();
            projectField.setProjectId(projectId);
            list = projectFieldMapper.select(projectField);
            if (list == null) {
                list = new ArrayList<>();
            }

            redisService.set(redisKey, list);
        }

        return list;
    }

    public Alarm findAlarmById(Integer id) {
        String redisKey = String.format(Constant.ALARM_REDIS_KEY,
                definitionProperties.getPermCachePrefix(), id);
        Alarm alarm = (Alarm) redisService.get(redisKey);

        if (alarm != null)
            return alarm;

        synchronized (redisKey.intern()) {
            alarm = (Alarm) redisService.get(redisKey);
            if (alarm != null)
                return alarm;

            alarm = alarmMapper.selectByPrimaryKey(id);
            if (alarm == null) {
                alarm = new Alarm();
            }

            redisService.set(redisKey, alarm);
        }

        return alarm;
    }

    public AlarmGroup findAlarmGroupById(Integer id) {
        String redisKey = String.format(Constant.ALARM_REDIS_GROUP_KEY,
                definitionProperties.getPermCachePrefix(), id);
        AlarmGroup alarmGroup = (AlarmGroup) redisService.get(redisKey);

        if (alarmGroup != null)
            return alarmGroup;

        synchronized (redisKey.intern()) {
            alarmGroup = (AlarmGroup) redisService.get(redisKey);
            if (alarmGroup != null)
                return alarmGroup;

            alarmGroup = alarmGroupMapper.selectByPrimaryKey(id);
            if (alarmGroup == null) {
                alarmGroup = new AlarmGroup();
            }

            redisService.set(redisKey, alarmGroup);
        }

        return alarmGroup;
    }

    @SuppressWarnings("unchecked")
    public List<User> findUserByAlarmGroupId(Integer alarmGroupId) {
        String redisKey = String.format(Constant.USER_REDIS_KEY,
                definitionProperties.getPermCachePrefix(), alarmGroupId);
        List<User> list = (List<User>) redisService.get(redisKey);

        if (list != null)
            return list;

        synchronized (redisKey.intern()) {
            list = (List<User>) redisService.get(redisKey);

            if (list != null)
                return list;

            list = findUsers(alarmGroupId);

            redisService.set(redisKey, list);
        }

        return list;
    }

    /**
     * 根据报警用户组ID查询组内所有用户，并筛选出用户（status：0）的用户
     *
     * @param alarmGroupId 警用户组ID
     * @return 用户集合
     */
    private List<User> findUsers(Integer alarmGroupId) {
        GroupUser groupUser = new GroupUser();
        groupUser.setAlarmGroupId(alarmGroupId);
        List<GroupUser> groupUserList = groupUserMapper.select(groupUser);
        if (groupUserList == null || groupUserList.size() == 0)
            return new ArrayList<>();

        List<Integer> idList = groupUserList.stream().
                map(GroupUser::getUserId).collect(Collectors.toList());

        List<User> userList = userMapper.findByList(idList);
        return userList.stream().filter(user -> user.getStatus() != null && user.getStatus() == 0).collect(Collectors.toList());
    }

    public void updateConfig(NotifyAlarm notifyAlarm) {
        if (notifyAlarm == null)
            return;
        int id = notifyAlarm.getId();
        if (notifyAlarm.isProject()) {//project有更新
            String project = String.format(Constant.PROJECT_REDIS_KEY,
                    definitionProperties.getPermCachePrefix(), id);
            String projectField = String.format(Constant.PROJECT_FIELD_REDIS_KEY,
                    definitionProperties.getPermCachePrefix(), id);
            redisService.delete(project);
            redisService.delete(projectField);
        } else if (notifyAlarm.isAlarm()) {//报警配置有更新
            String redisKey = String.format(Constant.ALARM_REDIS_KEY,
                    definitionProperties.getPermCachePrefix(), id);
            redisService.delete(redisKey);
        } else if (notifyAlarm.isAlarmGroup()) {//报警用户组配置有更新
            String alarmGroup = String.format(Constant.ALARM_REDIS_GROUP_KEY,
                    definitionProperties.getPermCachePrefix(), id);
            String redisKey = String.format(Constant.USER_REDIS_KEY,
                    definitionProperties.getPermCachePrefix(), id);
            redisService.delete(alarmGroup);
            redisService.delete(redisKey);
        } else if (notifyAlarm.isAlarmGroupList()) {//用户信息有更新
            for (int alarmGroupId : notifyAlarm.getIdList()) {
                String alarmGroup = String.format(Constant.ALARM_REDIS_GROUP_KEY,
                        definitionProperties.getPermCachePrefix(), alarmGroupId);
                String redisKey = String.format(Constant.USER_REDIS_KEY,
                        definitionProperties.getPermCachePrefix(), alarmGroupId);
                redisService.delete(alarmGroup);
                redisService.delete(redisKey);
            }
        } else if (notifyAlarm.isMonitor()) {//监控策略有更新
            String redisKey = String.format(Constant.MONITOR_REDIS_KEY,
                    definitionProperties.getPermCachePrefix(), id);
            redisService.delete(redisKey);
        }
    }
}
