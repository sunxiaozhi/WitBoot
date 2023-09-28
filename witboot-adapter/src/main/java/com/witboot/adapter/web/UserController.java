package com.witboot.adapter.web;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.witboot.client.api.IUserService;
import com.witboot.client.dto.UserRegisterCmd;
import com.witboot.client.dto.data.UserVO;
import com.witboot.client.dto.query.UserListByParamQuery;
import com.witboot.client.dto.query.UserLoginQuery;
import com.witboot.infrastructure.common.response.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author sunxiaozhi
 */
@RestController
@RequestMapping("user/")
public class UserController {
    @Resource
    private IUserService userService;

    @PostMapping(value = "/register")
    public ResponseResult<UserVO> register(@RequestBody UserRegisterCmd cmd) {
        return ResponseResult.success(userService.register(cmd));
    }

    @PostMapping(value = "/login")
    public Response login(@RequestBody UserLoginQuery userLoginQuery) {
        userService.login(userLoginQuery);
        return Response.buildSuccess();
    }

    @GetMapping(value = "/list")
    public MultiResponse<UserVO> list(UserListByParamQuery userListByParamQuery) {
        return userService.listByName(userListByParamQuery);
    }
}
