package com.witboot.infrastructure.common.aspectj;

import com.witboot.domain.operationLog.gateway.OperationLogGateway;
import com.witboot.domain.operationLog.model.OperationLogEntity;
import com.witboot.infrastructure.common.annotation.WitLog;
import com.witboot.infrastructure.common.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 日志记录
 *
 * @author sunxiaoizhi
 */
@Aspect
@Component
@Slf4j
public class WitLogAspect {
    @Before("@annotation(com.witboot.infrastructure.common.annotation.WitLog)")
    public void before() {
        log.info("WitLog切入点之前操作");
    }

    @After("@annotation(com.witboot.infrastructure.common.annotation.WitLog)")
    public void after() {
        log.info("WitLog切入点之后操作");
    }

    @AfterReturning(pointcut = "@annotation(witLog)")
    public void afterReturning(JoinPoint point, WitLog witLog) {
        log.info("WitLog切入点请求数据返回之后操作,描述信息：{}", witLog.description());

        OperationLogEntity operationLogEntity = new OperationLogEntity();
        operationLogEntity.setIp("127.0.0.1");
        operationLogEntity.setLocation("位置地址");
        operationLogEntity.setMethod("Get");
        operationLogEntity.setUri("/test");
        operationLogEntity.setStatus(200);
        operationLogEntity.setRequestContent("{}");
        operationLogEntity.setResponseContent("[]");

        OperationLogGateway operationLogGateway = BeanUtils.getBean(OperationLogGateway.class);
        operationLogGateway.save(operationLogEntity);
    }

    @AfterThrowing(pointcut = "@annotation(witLog)")
    public void afterThrowing(JoinPoint point, WitLog witLog) {
        log.info("WitLog切入点错误信息捕捉之后操作,描述信息：{}", witLog.description());
    }
}
