package com.bazzi.manager.controller;

import com.bazzi.common.generic.Page;
import com.bazzi.common.generic.Result;
import com.bazzi.manager.service.StatisticalService;
import com.bazzi.manager.vo.request.TotalByDayReqVO;
import com.bazzi.manager.vo.request.TotalByHourReqVO;
import com.bazzi.manager.vo.request.TotalByMonitorReqVO;
import com.bazzi.manager.vo.request.TotalByProjectReqVO;
import com.bazzi.manager.vo.response.TotalByDayResponseVO;
import com.bazzi.manager.vo.response.TotalByHourResponseVO;
import com.bazzi.manager.vo.response.TotalByMonitorResponseVO;
import com.bazzi.manager.vo.response.TotalByProjectResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "统计数据展示接口", tags = "统计数据展示接口")
@RestController
@RequestMapping("/s")
public class StatisticalController {

    @Resource
    private StatisticalService statisticalService;

    @PostMapping("/listTotalByDay")
    @ApiOperation(value = "按天总计列表")
    public Result<Page<TotalByDayResponseVO>> listTotalByDay(@RequestBody TotalByDayReqVO totalByDayReqVO) {
        return new Result<>(statisticalService.listTotalByDay(totalByDayReqVO));
    }

    @PostMapping("/listTotalByHour")
    @ApiOperation(value = "按小时总计列表")
    public Result<Page<TotalByHourResponseVO>> listTotalByHour(@RequestBody TotalByHourReqVO totalByHourReqVO) {
        return new Result<>(statisticalService.listTotalByHour(totalByHourReqVO));
    }

    @PostMapping("/listTotalByProject")
    @ApiOperation(value = "按项目统计列表")
    public Result<Page<TotalByProjectResponseVO>> listTotalByProject(@RequestBody TotalByProjectReqVO totalByProjectReqVO) {
        return new Result<>(statisticalService.listTotalByProject(totalByProjectReqVO));
    }

    @PostMapping("/listTotalByMonitor")
    @ApiOperation(value = "按监控策略统计列表")
    public Result<Page<TotalByMonitorResponseVO>> listTotalByMonitor(@RequestBody TotalByMonitorReqVO totalByMonitorReqVO) {
        return new Result<>(statisticalService.listTotalByMonitor(totalByMonitorReqVO));
    }

}
