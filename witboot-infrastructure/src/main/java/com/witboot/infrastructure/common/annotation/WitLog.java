package com.witboot.infrastructure.common.annotation;

import java.lang.annotation.*;

/**
 * 日志记录
 *
 * @author sunxiaoizhi
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WitLog {
    /**
     * 描述
     */
    String description() default "";
}
