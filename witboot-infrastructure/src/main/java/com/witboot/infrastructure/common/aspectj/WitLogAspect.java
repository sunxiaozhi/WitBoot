package com.witboot.infrastructure.common.aspectj;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.witboot.domain.operationlog.gateway.OperationLogGateway;
import com.witboot.domain.operationlog.model.OperationLogEntity;
import com.witboot.infrastructure.common.annotation.WitLog;
import com.witboot.infrastructure.common.utils.BeanUtils;
import com.witboot.infrastructure.common.utils.JakartaServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.NamedThreadLocal;
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
    /** 计算操作消耗时间 */
    private static final ThreadLocal<Long> WASTE_TIME_THREADLOCAL = new NamedThreadLocal<Long>("wasteTime");

    @Before("@annotation(com.witboot.infrastructure.common.annotation.WitLog)")
    public void before() {
        log.info("WitLog切入点之前操作");
        WASTE_TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    @After("@annotation(com.witboot.infrastructure.common.annotation.WitLog)")
    public void after() {
        log.info("WitLog切入点之后操作");
    }

    @AfterReturning(pointcut = "@annotation(witLog)", returning = "responseResult")
    public void afterReturning(JoinPoint point, WitLog witLog, Object responseResult) {
        log.info("WitLog切入点请求数据返回之后操作,描述信息：{}", witLog.description());

        try {
            HttpServletRequest httpServletRequest = JakartaServletUtil.getRequest();

            // 创建 ObjectMapper 对象
            ObjectMapper objectMapper = new ObjectMapper();

            OperationLogEntity operationLogEntity = new OperationLogEntity();

            operationLogEntity.setIp(JakartaServletUtil.getClientIP(httpServletRequest));

            operationLogEntity.setLocation("未知地址");

            operationLogEntity.setMethod(httpServletRequest.getMethod());

            operationLogEntity.setUri(httpServletRequest.getRequestURI());

            operationLogEntity.setRequestTime(DateUtil.now());

            operationLogEntity.setWasteTime(System.currentTimeMillis() - WASTE_TIME_THREADLOCAL.get());

            operationLogEntity.setRequestParam(objectMapper.writeValueAsString(httpServletRequest.getParameterMap()));

            operationLogEntity.setRequestBody("{}");

            // 将对象转换为 JSON 字符串
            operationLogEntity.setResponseResult(objectMapper.writeValueAsString(responseResult));

            OperationLogGateway operationLogGateway = BeanUtils.getBean(OperationLogGateway.class);
            operationLogGateway.save(operationLogEntity);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error(jsonProcessingException.getMessage());
        } finally {
            WASTE_TIME_THREADLOCAL.remove();
        }
    }

    @AfterThrowing(pointcut = "@annotation(witLog)")
    public void afterThrowing(JoinPoint point, WitLog witLog) {
        log.info("WitLog切入点错误信息捕捉之后操作,描述信息：{}", witLog.description());
    }
}
