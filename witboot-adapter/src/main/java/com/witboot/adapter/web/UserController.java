package com.witboot.adapter.web;

import com.witboot.api.UserService;
import com.witboot.api.dto.UserRequestDTO;
import com.witboot.dao.mybatis.dataobject.UserDO;
import com.witboot.infrastructure.common.response.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 *
 * @author sunxiaozhi
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult<List<UserDO>> getUserList() {
        return ResponseResult.success(userService.getUserList());
    }

    @GetMapping("/user")
    public ResponseResult<UserDO> getUser(@RequestParam("id") Long id) {
        return ResponseResult.success(userService.getUser(id));
    }

    @GetMapping("/username")
    public ResponseResult<String> getUserName(@RequestParam("id") Long id) {
        return ResponseResult.success(userService.getUserName(id));
    }

    @PostMapping("/add")
    public ResponseResult<UserDO> addUser(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseResult.success(userService.addUser(userRequestDTO));
    }
}
