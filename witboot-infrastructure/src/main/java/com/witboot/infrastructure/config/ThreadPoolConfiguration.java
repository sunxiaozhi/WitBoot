package com.witboot.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 全局异常处理类
 *
 * @author sunxiaozhi
 */
@Configuration
@Slf4j
public class ThreadPoolConfiguration {
    @Value("${spring.task.execution.pool.core-size:8}")
    private int coreSize;

    @Value("${spring.task.execution.pool.max-size:8}")
    private int maxSize;

    @Value("${spring.task.execution.pool.queue-capacity:9999}")
    private int queueCapacity;

    @Value("${spring.task.execution.pool.keep-alive:60}")
    private int keepAlive;

    @Bean("demoTaskExecutor")
    public ThreadPoolTaskExecutor demoTaskExecutor() {
        return threadPoolTaskExecutor("demo-task-worker");
    }

    /**
     * 线程池创建
     *
     * @param prefix 线程池前缀
     * @return 线程池
     */
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(String prefix) {
        log.info("线程池创建，coreSize：{}，maxSize：{}，queueCapacity：{}，keepAlive：{}，prefix：{}", coreSize, maxSize, queueCapacity, keepAlive, prefix);

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(coreSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAlive);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setThreadNamePrefix(prefix);
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }
}
