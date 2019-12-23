package com.bazzi.manager.controller;

import com.bazzi.common.generic.Page;
import com.bazzi.common.generic.Result;
import com.bazzi.manager.service.AlarmRecordService;
import com.bazzi.manager.vo.request.AlarmRecordReqVO;
import com.bazzi.manager.vo.request.IdReqVO;
import com.bazzi.manager.vo.response.AlarmRecordResponseVO;
import com.bazzi.manager.vo.response.AlarmRecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "报警记录接口", tags = "报警记录接口")
@RestController
@RequestMapping("/alarmRecord")
public class AlarmRecordController {

    @Resource
    private AlarmRecordService alarmRecordService;

    @PostMapping("/list")
    @ApiOperation(value = "报警记录列表")
    public Result<Page<AlarmRecordVO>> list(@RequestBody AlarmRecordReqVO alarmRecordReqVO) {
        return new Result<>(alarmRecordService.list(alarmRecordReqVO));
    }

    @PostMapping("/findById")
    @ApiOperation(value = "报警记录详情")
    public Result<AlarmRecordResponseVO> findById(@RequestBody IdReqVO idReqVO) {
        return new Result<>(alarmRecordService.findById(idReqVO));
    }
}
