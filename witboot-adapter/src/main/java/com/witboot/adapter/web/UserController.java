package com.witboot.adapter.web;

import com.witboot.client.user.api.IUserService;
import com.witboot.client.user.dto.UserRegisterCmd;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.client.user.dto.query.UserListByParamQuery;
import com.witboot.client.user.dto.query.UserLoginQuery;
import com.witboot.domain.base.model.PageResult;
import com.witboot.infrastructure.common.annotation.WitLog;
import com.witboot.infrastructure.common.core.controller.BaseController;
import com.witboot.infrastructure.common.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 *
 * @author sunxiaozhi
 */
@RestController
@RequestMapping("user/")
@Tag(name = "用户")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/register")
    @Operation(summary = "用户注册")
    @WitLog(description = "用户注册")
    public ResponseResult<UserVO> register(@RequestBody UserRegisterCmd userRegisterCmd) {
        return ResponseResult.success(userService.register(userRegisterCmd));
    }

    @PostMapping(value = "/login")
    @Operation(summary = "用户登录")
    @WitLog(description = "用户登录")
    public ResponseResult<Map<String, String>> login(@RequestBody UserLoginQuery userLoginQuery) {
        String jwtToken = userService.login(userLoginQuery);
        return ResponseResult.success(new HashMap<>(16) {{
            put("accessToken", jwtToken);
        }});
    }

    @GetMapping(value = "/list")
    @Operation(summary = "用户列表")
    @WitLog(description = "用户列表")
    public ResponseResult<PageResult<UserVO>> list(UserListByParamQuery userListByParamQuery) {
        startPage();
        return ResponseResult.success(userService.listByParam(userListByParamQuery));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "用户删除")
    @WitLog(description = "用户删除")
    public ResponseResult<Integer> delete(String[] ids) {
        return ResponseResult.success(userService.deleteByIds(ids));
    }
}