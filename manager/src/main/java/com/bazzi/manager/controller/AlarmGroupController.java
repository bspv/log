package com.bazzi.manager.controller;

import com.bazzi.common.generic.Page;
import com.bazzi.common.generic.Result;
import com.bazzi.manager.service.AlarmGroupService;
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
 * @ClassName AlarmGroupController
 * @Author baoyf
 * @Date 2019/1/23 16:33
 **/
@Api(value = "用户组接口", tags = "用户组接口")
@RestController
@RequestMapping("/alarmGroup")
public class AlarmGroupController {

    @Resource
    private AlarmGroupService alarmGroupService;

    @PostMapping("/list")
    @ApiOperation(value = "用户组列表")
    public Result<Page<AlarmGroupVO>> list(@RequestBody AlarmGroupListReqVO alarmGroupListReqVO){
        return new Result<>(alarmGroupService.list(alarmGroupListReqVO));
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加用户组")
    public Result<IdResponseVO> add(@RequestBody AlarmGroupReqVO alarmGroupReqVO){
        return new Result<>(alarmGroupService.add(alarmGroupReqVO));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户组")
    public Result<StringResponseVO> update(@RequestBody AlarmGroupUpdateReqVO alarmGroupUpdateReqVO){
        return new Result<>(alarmGroupService.update(alarmGroupUpdateReqVO));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除用户组")
    public Result<StringResponseVO> delete(@RequestBody IdReqVO idReqVO){
        return new Result<>(alarmGroupService.delete(idReqVO));
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改用户组状态")
    public Result<StringResponseVO> updateStatus(@RequestBody StatusReqVO statusReqVO){
        return new Result<>(alarmGroupService.updateStatus(statusReqVO));
    }

    @PostMapping("/queryGroupUser")
    @ApiOperation(value = "查询用户组下的用户")
    public Result<ListVO<GroupUserResponseVO>> queryGroupUser(@RequestBody IdReqVO idReqVO){
        return new Result<>(alarmGroupService.queryGroupUser(idReqVO));
    }

    @PostMapping("/addGroupUser")
    @ApiOperation(value = "添加用户组的用户")
    public Result<StringResponseVO> addGroupUser(@RequestBody GroupUserReqVO groupUserReqVO){
        return new Result<>(alarmGroupService.addGroupUser(groupUserReqVO));
    }

    @PostMapping("/queryGroupUserAll")
    @ApiOperation(value = "查询所有用户组")
    public Result<ListVO<GroupUserAllResponseVO>> queryGroupUserAll(){
        return new Result<>(alarmGroupService.queryGroupUserAll());
    }

}
