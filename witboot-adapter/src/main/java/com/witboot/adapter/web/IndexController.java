package com.witboot.adapter.web;

import com.witboot.client.api.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 欢迎控制器
 *
 * @author sunxiaozhi
 */
@RestController
@RequestMapping("index/")
public class IndexController {
    @Resource
    private IUserService userService;

    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello, welcome to WitBoot world!";
    }
}
