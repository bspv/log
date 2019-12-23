package com.bazzi.manager.mapper;

import com.bazzi.manager.model.Project;
import com.bazzi.manager.mybatis.BaseMapper;
import com.bazzi.manager.vo.request.ProjectListReqVO;

import java.util.List;

public interface ProjectMapper extends BaseMapper<Project> {
    List<Project> selectByParams(ProjectListReqVO projectListReqVO);

    int update(Project p);
}
