package com.bazzi.manager.controller;

import com.bazzi.common.generic.Page;
import com.bazzi.common.generic.Result;
import com.bazzi.manager.service.MonitorService;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.IdResponseVO;
import com.bazzi.manager.vo.response.MonitorVO;
import com.bazzi.manager.vo.response.StringResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "监控策略接口", tags = "监控策略接口")
@RestController
@RequestMapping("/monitor")
public class MonitorController {
    @Resource
    private MonitorService monitorService;

    @PostMapping("/list")
    @ApiOperation(value = "监控策略列表")
    public Result<Page<MonitorVO>> list(@RequestBody MonitorListReqVO monitorListReqVO) {
        return new Result<>(monitorService.list(monitorListReqVO));
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加监控策略")
    public Result<IdResponseVO> add(@RequestBody MonitorReqVO monitorReqVO) {
        return new Result<>(monitorService.add(monitorReqVO));
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑监控策略")
    public Result<StringResponseVO> edit(@RequestBody MonitorUpdateReqVO monitorUpdateReqVO) {
        return new Result<>(monitorService.update(monitorUpdateReqVO));
    }

    @ApiOperation(value = "修改监控策略状态")
    @PostMapping("/updateStatus")
    public Result<StringResponseVO> updateStatus(@RequestBody StatusReqVO statusReqVO){
        return new Result<>(monitorService.updateStatus(statusReqVO));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除监控策略")
    public Result<StringResponseVO> delete(@RequestBody IdReqVO idReqVO) {
        return new Result<>(monitorService.delete(idReqVO));
    }
}
