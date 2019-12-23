package com.bazzi.alarm.aspect;

import com.bazzi.alarm.mapper.AlarmRecordMapper;
import com.bazzi.alarm.model.AlarmRecord;
import com.bazzi.alarm.service.ConfigService;
import com.bazzi.common.generic.AlarmDesc;
import com.bazzi.common.generic.LogStatusCode;
import com.bazzi.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class AlarmAspect {
    @Resource
    private AlarmRecordMapper alarmRecordMapper;

    @Resource
    private ConfigService configService;

    @AfterThrowing(throwing = "ex", argNames = "alarmDesc,ex",
            value = "execution(public * com.hxlc.alarm.service.impl.AlarmServiceImpl.alarm(..)) && args(alarmDesc) ")
    public void afterThrowing(AlarmDesc alarmDesc, Throwable ex) {
        log.error(ex.getMessage(), ex);
        String errCode = LogStatusCode.CODE_300.getCode();
        String errMsg = ex.getMessage();
        AlarmRecord alarmRecord = buildAlarmRecord(alarmDesc, errCode, errMsg);
        alarmRecordMapper.insertUseGeneratedKeys(alarmRecord);
        log.info("AfterThrowing --- ClassName:{} --- ErrCode:{} --- ErrMsg:{} --- alarmRecordID:{}",
                ex.getClass().getSimpleName(), errCode, errMsg, alarmRecord.getId());
    }

    /**
     * 根据错误码，构建报警记录
     *
     * @param alarmDesc 报警详情
     * @param errCode   错误码
     * @param errMsg    错误信息
     * @return 报警记录
     */
    private AlarmRecord buildAlarmRecord(AlarmDesc alarmDesc, String errCode, String errMsg) {
        AlarmRecord record = new AlarmRecord();
        BeanUtils.copyProperties(alarmDesc, record);
        record.setFirstTrigger(DateUtil.getDate(alarmDesc.getFirstTrigger(), DateUtil.FULL_FORMAT));
        record.setTriggerTime(DateUtil.getDate(alarmDesc.getTriggerTime(), DateUtil.FULL_FORMAT));
        record.setNum(new BigDecimal(alarmDesc.getCount()).intValue());
        record.setStatus(1);//0成功，1失败
        record.setErrCode(errCode);
        record.setErrMsg(errMsg);
        record.setCreateTime(new Date());
        return record;
    }

}
