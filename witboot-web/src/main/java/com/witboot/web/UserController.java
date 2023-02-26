package com.witboot.web;

import com.witboot.api.UserService;
import com.witboot.api.dto.UserRequestDTO;
import com.witboot.common.base.response.ResponseResult;
import com.witboot.dao.mybatis.dataobject.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author sunxiaozhi
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
