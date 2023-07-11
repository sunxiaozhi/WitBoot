package com.witboot.user.service;

import com.witboot.api.TestService;
import com.witboot.infrastructure.common.utils.ThreadPoolUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * 测试接口实现类
 *
 * @author sunxiaozhi
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
    @Resource(name = "demoTaskExecutor")
    ThreadPoolTaskExecutor demoTaskExecutor;

    @Override
    public void testThreadPoolUtil() {
        for(int i = 0; i<=10; i++) {
            int finalI = i;
            ThreadPoolUtil.getInstance().execute(() -> {
                log.info("测试线程池开始-{}", finalI);
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("测试线程池结束-{}", finalI);
            });
        }
    }


}
