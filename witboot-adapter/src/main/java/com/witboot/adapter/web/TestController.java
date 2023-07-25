package com.witboot.adapter.web;

import com.witboot.client.TestService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author sunxiaozhi
 */
@RestController
@RequestMapping("test/")
public class TestController {
    @Resource
    TestService testService;

    @GetMapping("threadPool")
    public void threadPool(){
        testService.testThreadPoolUtil();
    }
}
