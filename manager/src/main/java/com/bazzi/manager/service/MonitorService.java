package com.bazzi.manager.service;

import com.bazzi.common.generic.Page;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.IdResponseVO;
import com.bazzi.manager.vo.response.MonitorVO;
import com.bazzi.manager.vo.response.StringResponseVO;

public interface MonitorService {
    /**
     * 根据条件查询监控策略列表
     *
     * @param monitorListReqVO 查询条件
     * @return 监控策略列表
     */
    Page<MonitorVO> list(MonitorListReqVO monitorListReqVO);

    /**
     * 添加监控策略
     *
     * @param monitorReqVO 监控策略
     * @return 新增结果
     */
    IdResponseVO add(MonitorReqVO monitorReqVO);

    /**
     * 更新监控策略
     *
     * @param monitorUpdateReqVO 更新的监控策略信息
     * @return 更新结果
     */
    StringResponseVO update(MonitorUpdateReqVO monitorUpdateReqVO);

    /**
     * 删除监控策略
     *
     * @param idReqVO id
     * @return 删除结果
     */
    StringResponseVO delete(IdReqVO idReqVO);

    /**
     * 更改监控策略状态
     *
     * @param statusReqVO id和status
     * @return 更改策略状态结果
     */
    StringResponseVO updateStatus(StatusReqVO statusReqVO);
}
