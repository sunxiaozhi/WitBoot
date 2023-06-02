package com.witboot.user.service;

import com.witboot.api.TestService;
import com.witboot.infrastructure.common.utils.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 测试接口实现类
 *
 * @author sunxiaozhi
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Override
    public void testThreadPool() {
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
