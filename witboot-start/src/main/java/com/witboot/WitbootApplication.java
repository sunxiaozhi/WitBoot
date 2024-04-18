package com.witboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * WitBoot
 *
 * @author sunxiaozhi
 */
@SpringBootApplication
@EnableAsync
public class WitbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(WitbootApplication.class, args);
    }
}
