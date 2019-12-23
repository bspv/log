package com.bazzi.manager.service.impl;

import com.bazzi.common.generic.Page;
import com.bazzi.manager.mapper.AlarmRecordDetailMapper;
import com.bazzi.manager.mapper.AlarmRecordMapper;
import com.bazzi.manager.model.AlarmRecord;
import com.bazzi.manager.model.AlarmRecordDetail;
import com.bazzi.manager.service.AlarmRecordService;
import com.bazzi.manager.vo.request.AlarmRecordReqVO;
import com.bazzi.manager.vo.request.IdReqVO;
import com.bazzi.manager.vo.response.AlarmRecordDetailVO;
import com.bazzi.manager.vo.response.AlarmRecordResponseVO;
import com.bazzi.manager.vo.response.AlarmRecordVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class AlarmRecordServiceImpl implements AlarmRecordService {

    @Resource
    private AlarmRecordMapper alarmRecordMapper;
    @Resource
    private AlarmRecordDetailMapper alarmRecordDetailMapper;

    public Page<AlarmRecordVO> list(AlarmRecordReqVO alarmRecordReqVO) {
        int pageIdx = alarmRecordReqVO.getPageIdx();
        int pageSize = alarmRecordReqVO.getPageSize();
        PageHelper.startPage(pageIdx, pageSize);
        List<AlarmRecord> list = alarmRecordMapper.selectByParams(alarmRecordReqVO);
        List<AlarmRecordVO> alarmRecordVOList = new ArrayList<>();
        for (AlarmRecord alarmRecord : list) {
            AlarmRecordVO alarmRecordVO = new AlarmRecordVO();
            BeanUtils.copyProperties(alarmRecord, alarmRecordVO);
            alarmRecordVOList.add(alarmRecordVO);
        }
        return Page.of(alarmRecordVOList, pageIdx, pageSize, (int) PageInfo.of(list).getTotal());
    }

    public AlarmRecordResponseVO findById(IdReqVO idReqVO) {
        AlarmRecordResponseVO responseVO = new AlarmRecordResponseVO();
        AlarmRecord alarmRecord = alarmRecordMapper.selectByPrimaryKey(idReqVO.getId());
        AlarmRecordDetail alarmRecordDetail = new AlarmRecordDetail();
        alarmRecordDetail.setAlarmRecordId(alarmRecord.getId());

        List<AlarmRecordDetailVO> list = new ArrayList<>();
        List<AlarmRecordDetail> selectList = alarmRecordDetailMapper.select(alarmRecordDetail);
        for (AlarmRecordDetail recordDetail : selectList) {
            AlarmRecordDetailVO ardVO = new AlarmRecordDetailVO();
            BeanUtils.copyProperties(recordDetail, ardVO);
            list.add(ardVO);
        }

        BeanUtils.copyProperties(alarmRecord, responseVO);
        responseVO.setList(list);
        return responseVO;
    }
}
