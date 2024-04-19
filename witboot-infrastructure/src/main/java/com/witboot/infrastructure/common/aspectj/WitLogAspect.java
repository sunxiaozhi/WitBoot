package com.witboot.infrastructure.common.aspectj;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.witboot.domain.operationLog.gateway.OperationLogGateway;
import com.witboot.domain.operationLog.model.OperationLogEntity;
import com.witboot.infrastructure.common.annotation.WitLog;
import com.witboot.infrastructure.common.response.ResponseResult;
import com.witboot.infrastructure.common.utils.BeanUtils;
import com.witboot.infrastructure.common.utils.JakartaServletUtil;
import jakarta.servlet.http.HttpServletRequest;
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

    @AfterReturning(pointcut = "@annotation(witLog)", returning = "responseResult")
    public void afterReturning(JoinPoint point, WitLog witLog, Object responseResult) {
        log.info("WitLog切入点请求数据返回之后操作,描述信息：{}", witLog.description());

        try {
            HttpServletRequest httpServletRequest = JakartaServletUtil.getRequest();

            OperationLogEntity operationLogEntity = new OperationLogEntity();

            operationLogEntity.setIp(JakartaServletUtil.getClientIP(httpServletRequest));

            operationLogEntity.setLocation("未知地址");

            operationLogEntity.setMethod(httpServletRequest.getMethod());

            operationLogEntity.setUri(httpServletRequest.getRequestURI());

            operationLogEntity.setStatus(((ResponseResult<?>) responseResult).getCode());

            operationLogEntity.setRequestParam("{}");

            operationLogEntity.setRequestBody("{}");

            // 创建 ObjectMapper 对象
            ObjectMapper objectMapper = new ObjectMapper();
            // 将对象转换为 JSON 字符串
            String responseResultJson = objectMapper.writeValueAsString(responseResult);
            operationLogEntity.setResponseResult(responseResultJson);

            OperationLogGateway operationLogGateway = BeanUtils.getBean(OperationLogGateway.class);
            operationLogGateway.save(operationLogEntity);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error(jsonProcessingException.getMessage());
        }
    }

    @AfterThrowing(pointcut = "@annotation(witLog)")
    public void afterThrowing(JoinPoint point, WitLog witLog) {
        log.info("WitLog切入点错误信息捕捉之后操作,描述信息：{}", witLog.description());
    }
}
