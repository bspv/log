package com.bazzi.manager.controller;

import com.bazzi.common.generic.Page;
import com.bazzi.common.generic.Result;
import com.bazzi.manager.service.AlarmService;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName AlarmController
 * @Author baoyf
 * @Date 2019/1/24 11:21
 **/
@Api(value = "报警配置接口", tags = "报警配置接口")
@RestController
@RequestMapping("/alarm")
public class AlarmController {

    @Resource
    private AlarmService alarmService;

    @ApiOperation(value = "报警配置列表")
    @PostMapping("/list")
    public Result<Page<AlarmVO>> list(@RequestBody AlarmListReqVO alarmListReqVO){
        return new Result<>(alarmService.list(alarmListReqVO));
    }

    @ApiOperation(value = "添加报警配置")
    @PostMapping("/add")
    public Result<IdResponseVO> add(@RequestBody AlarmAddReqVO alarmAddReqVO){
        return new Result<>(alarmService.add(alarmAddReqVO));
    }

    @ApiOperation(value = "修改报警配置")
    @PostMapping("/update")
    public Result<StringResponseVO> update(@RequestBody AlarmUpdateReqVO alarmUpdateReqVO){
        return new Result<>(alarmService.update(alarmUpdateReqVO));
    }

    @ApiOperation(value = "删除报警配置")
    @PostMapping("/delete")
    public Result<StringResponseVO> delete(@RequestBody IdReqVO idReqVO){
        return new Result<>(alarmService.delete(idReqVO));
    }

    @ApiOperation(value = "修改报警配置状态")
    @PostMapping("/updateStatus")
    public Result<StringResponseVO> updateStatus(@RequestBody StatusReqVO statusReqVO){
        return new Result<>(alarmService.updateStatus(statusReqVO));
    }

    @ApiOperation(value = "查询所有报警配置")
    @PostMapping("/findAllAlarm")
    public Result<ListVO<AlarmResponseVO>> findAllAlarm(){
        return new Result<>(new ListVO<>(alarmService.findAllAlarm()));
    }

}
