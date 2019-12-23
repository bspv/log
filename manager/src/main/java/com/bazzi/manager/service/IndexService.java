package com.bazzi.manager.service;

import com.bazzi.manager.vo.response.IndexResponseVO;

public interface IndexService {
    /**
     * 仪表盘接口数据
     *
     * @return 仪表盘数据
     */
    IndexResponseVO dashboard();
}
