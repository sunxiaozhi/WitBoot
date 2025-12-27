package com.witboot.infrastructure.config;

import com.witboot.infrastructure.common.filter.RequestLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Web 过滤配置
 *
 * @author sunxiaozhi
 */
@Configuration
public class WebFilterConfiguration {
    @Bean
    public FilterRegistrationBean<RequestLogFilter> requestLogFilter() {
        FilterRegistrationBean<RequestLogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestLogFilter());

        // 拦截请求 可优化成针对特定api
        registrationBean.addUrlPatterns("/*");

        // 执行顺序最靠前
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return registrationBean;
    }
}
