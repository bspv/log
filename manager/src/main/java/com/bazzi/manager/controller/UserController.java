package com.bazzi.manager.controller;

import com.bazzi.common.annotation.AllowAccess;
import com.bazzi.common.generic.LogStatusCode;
import com.bazzi.common.generic.Page;
import com.bazzi.common.generic.Result;
import com.bazzi.manager.service.UserService;
import com.bazzi.manager.util.ThreadLocalUtil;
import com.bazzi.manager.vo.request.*;
import com.bazzi.manager.vo.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "用户接口", tags = "用户接口")
@RestController
@RequestMapping("/u")
public class UserController {
    @Resource
    private UserService userService;

    @AllowAccess
    @ApiIgnore
    @RequestMapping(path = "/toLogin")
    public Result<?> errorJson(HttpServletRequest request, Exception ex, HttpServletResponse response) {
        Result<String> result = new Result<>();
        result.setError(LogStatusCode.CODE_001.getCode(), LogStatusCode.CODE_001.getMessage());
        return result;
    }

    @AllowAccess
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result<UserResponseVO> login(@RequestBody LoginReqVO loginReqVo) {
        return new Result<>(userService.login(loginReqVo));
    }

    @PostMapping("/userInfo")
    @ApiOperation(value = "用户信息")
    public Result<UserResponseVO> userInfo() {
        return new Result<>(ThreadLocalUtil.getUserInfo());
    }


    @PostMapping("/list")
    @ApiOperation(value = "用户列表")
    public Result<Page<UserVO>> list(@RequestBody UserListReqVO userListReqVO) {
        return new Result<>(userService.list(userListReqVO));
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加用户")
    public Result<IdResponseVO> add(@RequestBody UserReqVO userReqVO) {
        return new Result<>(userService.add(userReqVO));
    }

    @PostMapping("/update")
    @ApiOperation(value = "编辑用户")
    public Result<StringResponseVO> update(@RequestBody UserUpdateReqVO userUpdateReqVO) {
        return new Result<>(userService.update(userUpdateReqVO));
    }

    @ApiOperation(value = "修改用户状态")
    @PostMapping("/updateStatus")
    public Result<StringResponseVO> updateStatus(@RequestBody StatusReqVO statusReqVO){
        return new Result<>(userService.updateStatus(statusReqVO));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除用户")
    public Result<StringResponseVO> delete(@RequestBody IdReqVO idReqVO) {
        return new Result<>(userService.delete(idReqVO));
    }

    @PostMapping("/findMenuId")
    @ApiOperation(value = "查找用户对应的菜单ID集合")
    public Result<ListVO<MenuFillVO>> findMenuId(@RequestBody IdReqVO idReqVO) {
        return new Result<>(new ListVO<>(userService.findMenuId(idReqVO)));
    }

    @PostMapping("/editUserMenu")
    @ApiOperation(value = "编辑用户菜单")
    public Result<StringResponseVO> editUserMenu(@RequestBody UserMenuReqVO userMenuReqVO) {
        return new Result<>(userService.editUserMenu(userMenuReqVO));
    }

    @PostMapping("/logout")
    @ApiOperation(value = "登出")
    public Result<StringResponseVO> logout() {
        userService.logout();
        return new Result<>();
    }

    @AllowAccess
    @PostMapping("/findAllUser")
    @ApiOperation(value = "查询所有用户")
    public Result<ListVO<UserSimpleVO>> findAllUser() {
        return new Result<>(new ListVO<>(userService.findAllUser()));
    }

}
