package com.witboot.adapter.web;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.witboot.client.user.api.IUserService;
import com.witboot.client.user.dto.UserRegisterCmd;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.client.user.dto.query.UserListByParamQuery;
import com.witboot.client.user.dto.query.UserLoginQuery;
import com.witboot.infrastructure.common.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author sunxiaozhi
 */
@RestController
@RequestMapping("user/")
@Tag(name = "用户接口")
public class UserController {
    @Resource
    private IUserService userService;

    @GetMapping(value = "/list")
    @Operation(summary = "列表")
    public MultiResponse<UserVO> list(UserListByParamQuery userListByParamQuery) {
        return userService.listByParam(userListByParamQuery);
    }

    @PostMapping(value = "/register")
    @Operation(summary = "注册")
    public ResponseResult<UserVO> register(@RequestBody UserRegisterCmd userRegisterCmd) {
        return ResponseResult.success(userService.register(userRegisterCmd));
    }

    @PostMapping(value = "/login")
    @Operation(summary = "登录")
    public Response login(@RequestBody UserLoginQuery userLoginQuery) {
        userService.login(userLoginQuery);
        return Response.buildSuccess();
    }
}