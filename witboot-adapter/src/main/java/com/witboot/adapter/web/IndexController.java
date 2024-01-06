package com.witboot.adapter.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 欢迎控制器
 *
 * @author sunxiaozhi
 */
@RestController
@Tag(name = "演示接口")
@RequestMapping("index/")
public class IndexController {
    @GetMapping(value = "/hello")
    @Operation(summary = "hello")
    public String hello() {
        return "Hello, welcome to WitBoot!";
    }
}
