package com.witboot.util;

import com.witboot.WitbootApplication;
import com.witboot.infrastructure.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = {WitbootApplication.class})
@RunWith(SpringRunner.class)
@WebAppConfiguration
@Slf4j
public class RedisUtilTest {
    @Autowired
    RedisUtil redisUtil;

    @Test
    public void redisUtilTest() {
        redisUtil.setCache("WitBoot", "helloWorld");

        String value = redisUtil.getCache("WitBoot");
        log.info("WitBoot:{}", value);

        Assertions.assertEquals("helloWorld", value);
    }
}
