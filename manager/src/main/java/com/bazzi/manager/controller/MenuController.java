package com.bazzi.manager.controller;

import com.bazzi.common.generic.Result;
import com.bazzi.manager.service.MenuService;
import com.bazzi.manager.vo.request.IdReqVO;
import com.bazzi.manager.vo.request.MenuReqVO;
import com.bazzi.manager.vo.request.MenuUpdateReqVO;
import com.bazzi.manager.vo.response.IdResponseVO;
import com.bazzi.manager.vo.response.ListVO;
import com.bazzi.manager.vo.response.MenuVO;
import com.bazzi.manager.vo.response.StringResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "菜单接口", tags = "菜单接口")
@RestController
@RequestMapping("/m")
public class MenuController {
    @Resource
    private MenuService menuService;

    @PostMapping("/allMenu")
    @ApiOperation(value = "所有菜单")
    public Result<ListVO<MenuVO>> allMenu() {
        return new Result<>(new ListVO<>(menuService.selectAllMenu()));
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加菜单")
    public Result<IdResponseVO> add(@RequestBody MenuReqVO menuReqVO) {
        return new Result<>(menuService.add(menuReqVO));
    }

    @PostMapping("/update")
    @ApiOperation(value = "编辑菜单")
    public Result<StringResponseVO> update(@RequestBody MenuUpdateReqVO menuUpdateReqVO) {
        return new Result<>(menuService.update(menuUpdateReqVO));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除菜单")
    public Result<StringResponseVO> delete(@RequestBody IdReqVO idReqVO) {
        return new Result<>(menuService.delete(idReqVO));
    }
}
