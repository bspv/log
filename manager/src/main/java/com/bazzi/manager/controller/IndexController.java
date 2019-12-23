package com.bazzi.manager.controller;

import com.bazzi.common.annotation.AllowAccess;
import com.bazzi.common.generic.Result;
import com.bazzi.manager.service.IndexService;
import com.bazzi.manager.vo.response.IndexResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "首页接口", tags = "首页接口")
@RestController
@RequestMapping("/idx")
public class IndexController {

    @Resource
    private IndexService indexService;

    @AllowAccess
    @PostMapping("/dashboard")
    @ApiOperation(value = "仪表盘接口")
    public Result<IndexResponseVO> dashboard() {
        return new Result<>(indexService.dashboard());
    }

}
