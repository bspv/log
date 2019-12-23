package com.bazzi.manager.service;

import com.bazzi.common.generic.Page;
import com.bazzi.manager.vo.request.AlarmRecordReqVO;
import com.bazzi.manager.vo.request.IdReqVO;
import com.bazzi.manager.vo.response.AlarmRecordResponseVO;
import com.bazzi.manager.vo.response.AlarmRecordVO;

public interface AlarmRecordService {
    /**
     * 查询报警记录列表数据
     *
     * @param alarmRecordReqVO 查询条件
     * @return 结果
     */
    Page<AlarmRecordVO> list(AlarmRecordReqVO alarmRecordReqVO);

    /**
     * 根据ID查询报警记录详情，包括发送记录详情
     *
     * @param idReqVO ID
     * @return 结果
     */
    AlarmRecordResponseVO findById(IdReqVO idReqVO);
}
