package com.bazzi.manager.controller;

import com.bazzi.common.generic.Page;
import com.bazzi.common.generic.Result;
import com.bazzi.manager.service.ProjectService;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "项目接口", tags = "项目接口")
@RestController
@RequestMapping("/p")
public class ProjectController {
    @Resource
    private ProjectService projectService;

    @PostMapping("/list")
    @ApiOperation(value = "项目列表")
    public Result<Page<ProjectVO>> list(@RequestBody ProjectListReqVO projectListReqVO) {
        return new Result<>(projectService.list(projectListReqVO));
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加项目")
    public Result<IdResponseVO> add(@RequestBody ProjectReqVO projectReqVO) {
        return new Result<>(projectService.add(projectReqVO));
    }

    @PostMapping("/findHostById")
    @ApiOperation(value = "根据项目ID查询Host信息")
    public Result<ListVO<ProjectHostVO>> findHostById(@RequestBody IdReqVO idReqVO) {
        return new Result<>(new ListVO<>(projectService.findHostById(idReqVO)));
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑项目")
    public Result<StringResponseVO> edit(@RequestBody ProjectUpdateReqVO projectUpdateReqVO) {
        return new Result<>(projectService.update(projectUpdateReqVO));
    }

    @ApiOperation(value = "修改项目状态")
    @PostMapping("/updateStatus")
    public Result<StringResponseVO> updateStatus(@RequestBody StatusReqVO statusReqVO){
        return new Result<>(projectService.updateStatus(statusReqVO));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除项目")
    public Result<StringResponseVO> delete(@RequestBody IdReqVO idReqVO) {
        return new Result<>(projectService.delete(idReqVO));
    }

    @PostMapping("/findAllProject")
    @ApiOperation(value = "查询所有项目")
    public Result<ListVO<ProjectSimpleVO>> findAllProject() {
        return new Result<>(new ListVO<>(projectService.findAllProject()));
    }
}
