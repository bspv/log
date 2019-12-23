package com.bazzi.alarm.service.impl;

import com.bazzi.alarm.bean.EmailDetail;
import com.bazzi.alarm.config.DefinitionProperties;
import com.bazzi.alarm.mapper.AlarmRecordDetailMapper;
import com.bazzi.alarm.mapper.AlarmRecordMapper;
import com.bazzi.alarm.model.*;
import com.bazzi.alarm.service.AlarmService;
import com.bazzi.alarm.service.ConfigService;
import com.bazzi.alarm.service.EmailService;
import com.bazzi.common.generic.AlarmDesc;
import com.bazzi.common.generic.LogStatusCode;
import com.bazzi.common.util.DateUtil;
import com.bazzi.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class AlarmServiceImpl implements AlarmService {

    @Resource
    private EmailService emailService;

    @Resource
    private ConfigService configService;

    @Resource
    private DefinitionProperties definitionProperties;


    @Resource
    private AlarmRecordMapper alarmRecordMapper;
    @Resource
    private AlarmRecordDetailMapper alarmRecordDetailMapper;

    public void alarm(AlarmDesc alarmDesc) {
        //检查项目
        Integer projectId = alarmDesc.getProjectId();
        Project project = configService.findProjectById(projectId);
        if (project.getId() == 0) {
            saveErrorAlarmRecord(alarmDesc, LogStatusCode.CODE_301, projectId);
            return;
        }
        if (project.getStatus() != 0) {
            saveErrorAlarmRecord(alarmDesc, LogStatusCode.CODE_302, projectId);
            return;
        }

        //检查监控策略
        Integer monitorId = alarmDesc.getMonitorId();
        Monitor monitor = configService.findMonitorById(monitorId);
        if (monitor.getId() == 0) {
            saveErrorAlarmRecord(alarmDesc, LogStatusCode.CODE_303, monitorId);
            return;
        }
        if (monitor.getStatus() != 0) {
            saveErrorAlarmRecord(alarmDesc, LogStatusCode.CODE_304, monitorId);
            return;
        }

        //检查报警配置
        Integer alarmId = alarmDesc.getAlarmId();
        Alarm alarm = configService.findAlarmById(alarmId);
        if (alarm.getId() == 0) {
            saveErrorAlarmRecord(alarmDesc, LogStatusCode.CODE_305, alarmId);
            return;
        }
        if (alarm.getStatus() != 0) {
            saveErrorAlarmRecord(alarmDesc, LogStatusCode.CODE_306, alarmId);
            return;
        }

        //检查报警用户组配置
        Integer alarmGroupId = alarm.getAlarmGroupId();
        AlarmGroup alarmGroup = configService.findAlarmGroupById(alarmGroupId);
        if (alarmGroup.getId() == 0) {
            saveErrorAlarmRecord(alarmDesc, LogStatusCode.CODE_307, alarmGroupId);
            return;
        }
        if (alarmGroup.getStatus() != 0) {
            saveErrorAlarmRecord(alarmDesc, LogStatusCode.CODE_308, alarmGroupId);
            return;
        }

        //检查报警用户
        List<User> users = configService.findUserByAlarmGroupId(alarmGroupId);
        if (users.size() == 0) {
            saveErrorAlarmRecord(alarmDesc, LogStatusCode.CODE_309, alarmGroupId);
            return;
        }

        AlarmRecord alarmRecord = buildAlarmRecord(alarmDesc, project, monitor, alarm, alarmGroup, users);
        alarmRecordMapper.insertUseGeneratedKeys(alarmRecord);

        long alarmRecordId = alarmRecord.getId();

        List<AlarmRecordDetail> ardList = new ArrayList<>();

        //根据不同的报警方式发送对应报警
        String alarmMode = alarm.getAlarmMode();
        for (String alarmModeStr : alarmMode.split("#")) {
            int mode = Integer.parseInt(alarmModeStr);//报警方式，0邮件，1短信，2微信
            if (mode == 0) {
                sendEmailAlarm(alarmDesc, project, users, ardList, alarmRecordId, mode);
            } else if (mode == 1) {
                log.info("暂时不支持短信方式报警");
            } else if (mode == 2) {
                log.info("暂时不支持微信方式报警");
            } else {
                log.info("未知的报警方式(alarmMode:" + mode + ")");
            }
        }

        alarmRecordDetailMapper.insertList(ardList);
    }

    /**
     * 发送邮件报警
     *
     * @param alarmDesc     报警详情
     * @param project       项目
     * @param users         报警用户组的状态正常的用户
     * @param ardList       报警记录详情集合
     * @param alarmRecordId 报警记录ID
     * @param mode          报警方式，邮件0
     */
    private void sendEmailAlarm(AlarmDesc alarmDesc, Project project, List<User> users,
                                List<AlarmRecordDetail> ardList, long alarmRecordId, int mode) {
        Integer captureType = project.getCaptureType();

        Map<String, String> map = new LinkedHashMap<>();
        if (captureType == 1) {//捕获类型，0不解析捕获正则，1解析捕获正则
            List<ProjectField> projectFieldList = configService.findProjectFieldByProjectId(project.getId());
            if (projectFieldList.size() > 0) {
                analyzeByCaptureRegular(alarmDesc, project, projectFieldList, map);
            } else {
                captureType = 0;
            }
        }

        EmailDetail ed = new EmailDetail();
        BeanUtils.copyProperties(alarmDesc, ed);
        ed.setCaptureType(captureType);
        ed.setMsgMap(map);

        Map<String, Object> contentMap =
                JsonUtil.parseMap(JsonUtil.toJsonString(ed), String.class, Object.class);

        //给每个用户发送报警邮件
        for (User user : users) {
            String email = user.getEmail();
            String tips = sendEmail(email, contentMap);
            AlarmRecordDetail ard = new AlarmRecordDetail();
            ard.setAlarmMode(mode);
            ard.setAlarmRecordId(alarmRecordId);
            ard.setRecipient(email);
            ard.setSendStatus(tips == null ? 0 : 1);
            ard.setFailMsg(tips != null ? tips.substring(0, 60) : null);
            ard.setCreateTime(new Date());
            ardList.add(ard);
        }
    }

    /**
     * 根据捕获正则解析日志，拆分具体字段放入map
     *
     * @param alarmDesc        报警详情
     * @param project          项目
     * @param projectFieldList 项目日志各字段的配置
     * @param map              内容
     */
    private void analyzeByCaptureRegular(AlarmDesc alarmDesc, Project project,
                                         List<ProjectField> projectFieldList, Map<String, String> map) {
        Pattern pattern = Pattern.compile(project.getCaptureRegular());
        Matcher matcher = pattern.matcher(alarmDesc.getMessage());
        if (matcher.find()) {
            for (ProjectField field : projectFieldList) {
                map.put(field.getFieldName(), matcher.group(field.getFieldName()));
            }
        }
    }

    /**
     * 发送邮件
     *
     * @param sendTo     收件人
     * @param contentMap ftl内容
     */
    private String sendEmail(String sendTo, Map<String, Object> contentMap) {
        try {
            emailService.sendTemplateMail(
                    sendTo,
                    definitionProperties.getEmailTitle(),
                    definitionProperties.getFtlPath(),
                    contentMap);
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return e.getMessage();
        }
    }

    /**
     * 构建报警记录
     *
     * @param alarmDesc  报警详情
     * @param p          项目
     * @param alarm      报警配置
     * @param alarmGroup 报警用户组配置
     * @param users      报警用户组的状态正常的用户
     * @return 报警记录
     */
    private AlarmRecord buildAlarmRecord(AlarmDesc alarmDesc, Project p, Monitor monitor,
                                         Alarm alarm, AlarmGroup alarmGroup, List<User> users) {
        AlarmRecord record = new AlarmRecord();
        BeanUtils.copyProperties(alarmDesc, record);
        record.setFirstTrigger(DateUtil.getDate(alarmDesc.getFirstTrigger(), DateUtil.FULL_FORMAT));
        record.setTriggerTime(DateUtil.getDate(alarmDesc.getTriggerTime(), DateUtil.FULL_FORMAT));
        record.setNum(new BigDecimal(alarmDesc.getCount()).intValue());

        BeanUtils.copyProperties(p, record, "status");
        record.setProjectStatus(p.getStatus());

        BeanUtils.copyProperties(monitor, record, "status");
        record.setMonitorStatus(monitor.getStatus());

        BeanUtils.copyProperties(alarm, record, "status");
        record.setAlarmStatus(alarm.getStatus());

        BeanUtils.copyProperties(alarmGroup, record, "status");
        record.setGroupStatus(alarmGroup.getStatus());

        record.setUserNum(users.size());
        record.setStatus(0);//0成功，1失败
        record.setErrCode(LogStatusCode.CODE_000.getCode());
        record.setErrMsg(LogStatusCode.CODE_000.getMessage());
        record.setCreateTime(new Date());
        return record;
    }

    /**
     * 保存报警失败记录
     *
     * @param alarmDesc     报警信息详情
     * @param logStatusCode 错误码，错误码中必须包含一个占位符，用于替换对应id
     * @param id            替换错误码错误信息中的占位符的id
     */
    private void saveErrorAlarmRecord(AlarmDesc alarmDesc, LogStatusCode logStatusCode, Integer id) {
        if (alarmDesc == null || logStatusCode == null | id == null)
            return;
        String errMsg = String.format(logStatusCode.getMessage(), id);
        AlarmRecord alarmRecord = buildErrorAlarmRecord(alarmDesc, logStatusCode, errMsg);
        alarmRecordMapper.insertUseGeneratedKeys(alarmRecord);
        log.info("Alarm Fail ------ code:{}, --- msg:{}, --- alarmId:{}",
                logStatusCode.getCode(), errMsg, alarmRecord.getId());
    }

    /**
     * 构建报警错误的报警记录
     *
     * @param alarmDesc     报警信息详情
     * @param logStatusCode 错误码
     * @param errMsg        错误信息
     * @return
     */
    private AlarmRecord buildErrorAlarmRecord(AlarmDesc alarmDesc, LogStatusCode logStatusCode, String errMsg) {
        AlarmRecord record = new AlarmRecord();
        BeanUtils.copyProperties(alarmDesc, record);
        record.setFirstTrigger(DateUtil.getDate(alarmDesc.getFirstTrigger(), DateUtil.FULL_FORMAT));
        record.setTriggerTime(DateUtil.getDate(alarmDesc.getTriggerTime(), DateUtil.FULL_FORMAT));
        record.setNum(new BigDecimal(alarmDesc.getCount()).intValue());
        record.setStatus(1);//0成功，1失败
        record.setErrCode(logStatusCode.getCode());
        record.setErrMsg(errMsg);
        record.setCreateTime(new Date());
        //项目不存在，则只记录报警信息
        if (LogStatusCode.CODE_301.getCode().equals(logStatusCode.getCode()))
            return record;
        Project p = configService.findProjectById(alarmDesc.getProjectId());
        BeanUtils.copyProperties(p, record, "status", "createTime");
        record.setProjectStatus(p.getStatus());
        //项目状态异常或监控策略不存在，则记录项目信息
        if (LogStatusCode.CODE_302.getCode().equals(logStatusCode.getCode())
                || LogStatusCode.CODE_303.getCode().equals(logStatusCode.getCode()))
            return record;

        Monitor m = configService.findMonitorById(alarmDesc.getMonitorId());
        BeanUtils.copyProperties(m, record, "status", "createTime");
        record.setMonitorStatus(m.getStatus());
        //监控策略状态异常或报警配置不存在，则记录监控策略信息
        if (LogStatusCode.CODE_304.getCode().equals(logStatusCode.getCode())
                || LogStatusCode.CODE_305.getCode().equals(logStatusCode.getCode()))
            return record;

        Alarm alarm = configService.findAlarmById(alarmDesc.getAlarmId());
        BeanUtils.copyProperties(alarm, record, "status", "createTime");
        record.setAlarmStatus(alarm.getStatus());
        //报警配置状态异常或报警用户组不存在，则记录报警配置信息
        if (LogStatusCode.CODE_306.getCode().equals(logStatusCode.getCode())
                || LogStatusCode.CODE_307.getCode().equals(logStatusCode.getCode()))
            return record;
        AlarmGroup alarmGroup = configService.findAlarmGroupById(alarm.getAlarmGroupId());
        BeanUtils.copyProperties(alarmGroup, record, "status", "createTime");
        record.setGroupStatus(alarmGroup.getStatus());
        //报警用户组状态异常
        if (LogStatusCode.CODE_308.getCode().equals(logStatusCode.getCode()))
            return record;
        //报警用户组下没有状态正常的用户
        if (LogStatusCode.CODE_309.getCode().equals(logStatusCode.getCode()))
            record.setUserNum(0);
        return record;
    }
}
