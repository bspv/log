package com.bazzi.manager.service;

import com.bazzi.common.generic.Page;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.AlarmResponseVO;
import com.bazzi.manager.vo.response.AlarmVO;
import com.bazzi.manager.vo.response.IdResponseVO;
import com.bazzi.manager.vo.response.StringResponseVO;

import java.util.List;

public interface AlarmService {
    /**
     * 报警配置列表
     * @param alarmListReqVO
     * @return
     */
    Page<AlarmVO> list(AlarmListReqVO alarmListReqVO);

    /**
     * 添加报警配置
     * @param alarmAddReqVO
     * @return
     */
    IdResponseVO add(AlarmAddReqVO alarmAddReqVO);

    /**
     * 修改报警配置
     * @param alarmUpdateReqVO
     * @return
     */
    StringResponseVO update(AlarmUpdateReqVO alarmUpdateReqVO);

    /**
     * 删除报警配置
     * @param idReqVO
     * @return
     */
    StringResponseVO delete(IdReqVO idReqVO);

    /**
     * 修改报警配置状态
     * @param statusReqVO
     * @return
     */
    StringResponseVO updateStatus(StatusReqVO statusReqVO);

    /**
     * 查询所有报警配置
     * @return
     */
    List<AlarmResponseVO> findAllAlarm();
}
