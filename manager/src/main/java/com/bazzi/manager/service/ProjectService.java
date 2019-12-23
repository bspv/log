package com.bazzi.manager.service;

import com.bazzi.common.generic.Page;
import com.bazzi.manager.model.Project;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.*;

import java.util.List;

public interface ProjectService {
    /**
     * 根据查询条件查询项目列表
     *
     * @param projectListReqVO 查询条件
     * @return 项目列表
     */
    Page<ProjectVO> list(ProjectListReqVO projectListReqVO);

    /**
     * 添加项目
     *
     * @param projectReqVO 项目信息
     * @return 添加结果
     */
    IdResponseVO add(ProjectReqVO projectReqVO);

    /**
     * 根据项目id查询host列表
     *
     * @param idReqVO 项目id
     * @return host列表
     */
    List<ProjectHostVO> findHostById(IdReqVO idReqVO);

    /**
     * 更新项目
     *
     * @param projectUpdateReqVO 项目更新信息
     * @return 更新结果
     */
    StringResponseVO update(ProjectUpdateReqVO projectUpdateReqVO);

    /**
     * 删除项目，并删除project_host、project_field表对应记录
     *
     * @param idReqVO id
     * @return 删除结果
     */
    StringResponseVO delete(IdReqVO idReqVO);

    /**
     * 查询所有项目
     *
     * @return 项目集合
     */
    List<ProjectSimpleVO> findAllProject();

    /**
     * 更改项目状态
     *
     * @param statusReqVO id和status
     * @return 更改项目状态结果
     */
    StringResponseVO updateStatus(StatusReqVO statusReqVO);

    /**
     * 根据ID查询项目信息，先冲redis缓存中查询，没有再从数据库里查
     *
     * @param id 项目id
     * @return 项目信息
     */
    Project findByIdWithCache(Integer id);
}
