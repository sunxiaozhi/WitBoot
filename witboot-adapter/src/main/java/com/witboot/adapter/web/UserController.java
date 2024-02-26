package com.witboot.adapter.web;

import com.alibaba.cola.dto.MultiResponse;
import com.witboot.client.user.api.IUserService;
import com.witboot.client.user.dto.UserRegisterCmd;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.client.user.dto.query.UserListByParamQuery;
import com.witboot.client.user.dto.query.UserLoginQuery;
import com.witboot.infrastructure.common.annotation.WitLog;
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
@Tag(name = "用户接口")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/register")
    @Operation(summary = "注册")
    @WitLog(description = "用户注册")
    public ResponseResult<UserVO> register(@RequestBody UserRegisterCmd userRegisterCmd) {
        return ResponseResult.success(userService.register(userRegisterCmd));
    }

    @PostMapping(value = "/login")
    @Operation(summary = "登录")
    @WitLog(description = "用户登录")
    public ResponseResult<Map<String, String>> login(@RequestBody UserLoginQuery userLoginQuery) {
        String jwtToken = userService.login(userLoginQuery);
        return ResponseResult.success(new HashMap<>(16) {{
            put("accessToken", jwtToken);
        }});
    }

    @GetMapping(value = "/list")
    @Operation(summary = "列表")
    @WitLog(description = "用户列表")
    public MultiResponse<UserVO> list(UserListByParamQuery userListByParamQuery) {
        return userService.listByParam(userListByParamQuery);
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "删除")
    @WitLog(description = "用户删除")
    public ResponseResult<Integer> delete(String[] ids) {
        return ResponseResult.success(userService.deleteByIds(ids));
    }
}