package com.witboot.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;

/**
 * Mybatis配置
 *
 * @author sunxiaozhi
 */
@MapperScan("com.witboot.domain.mybatis.mapper")
public class MybatisConfiguration {
}
