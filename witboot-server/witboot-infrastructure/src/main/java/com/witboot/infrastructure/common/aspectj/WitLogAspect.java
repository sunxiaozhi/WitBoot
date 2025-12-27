package com.witboot.infrastructure.common.aspectj;

import cn.hutool.core.date.DateUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 日志记录切面
 *
 * @author sunxiaoizhi
 */
@Aspect
@Component
@Slf4j
public class WitLogAspect {
    @Autowired
    private ObjectMapper objectMapper;

    /** 计算操作消耗时间 */
    private static final ThreadLocal<Long> WASTE_TIME_THREADLOCAL = new NamedThreadLocal<>("wasteTime");

    /** 复用ObjectMapper实例 */
    //private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Before("@annotation(com.witboot.infrastructure.common.annotation.WitLog)")
    public void before() {
        log.debug("WitLog切入点之前操作");
        WASTE_TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    @After("@annotation(com.witboot.infrastructure.common.annotation.WitLog)")
    public void after() {
        log.debug("WitLog切入点之后操作");
    }

    @AfterReturning(pointcut = "@annotation(witLog)", returning = "responseResult")
    public void afterReturning(JoinPoint point, WitLog witLog, Object responseResult) {
        log.debug("WitLog切入点请求数据返回之后操作,描述信息：{}", witLog.description());

        try {
            // 在主线程中获取请求信息，然后传递给异步任务
            RequestInfo requestInfo = captureRequestInfo();
            if (requestInfo != null) {
                // 异步处理日志保存，避免阻塞主业务流程
                CompletableFuture.runAsync(() -> saveOperationLog(requestInfo, witLog, responseResult))
                        .exceptionally(throwable -> {
                            log.error("保存操作日志失败: {}", throwable.getMessage(), throwable);
                            return null;
                        });
            } else {
                log.warn("无法获取请求信息，跳过操作日志记录");
            }
        } finally {
            WASTE_TIME_THREADLOCAL.remove();
        }
    }

    @AfterThrowing(pointcut = "@annotation(witLog)", throwing = "exception")
    public void afterThrowing(JoinPoint point, WitLog witLog, Exception exception) {
        log.info("WitLog切入点错误信息捕捉之后操作,描述信息：{}", witLog.description());

        try {
            // 在主线程中获取请求信息，然后传递给异步任务
            RequestInfo requestInfo = captureRequestInfo();
            if (requestInfo != null) {
                // 异步处理异常日志保存
                CompletableFuture.runAsync(() -> saveExceptionLog(requestInfo, witLog, exception))
                        .exceptionally(throwable -> {
                            log.error("保存异常日志失败: {}", throwable.getMessage(), throwable);
                            return null;
                        });
            } else {
                log.warn("无法获取请求信息，跳过异常日志记录");
            }
        } finally {
            WASTE_TIME_THREADLOCAL.remove();
        }
    }

    /**
     * 捕获当前线程的请求信息
     */
    private RequestInfo captureRequestInfo() {
        try {
            HttpServletRequest request = JakartaServletUtil.getRequest();

            RequestInfo requestInfo = new RequestInfo();
            requestInfo.ip = JakartaServletUtil.getClientIP(request);
            requestInfo.method = request.getMethod();
            requestInfo.uri = request.getRequestURI();
            requestInfo.parameterMap = request.getParameterMap();
            requestInfo.requestBody = getRequestBody(request);

            requestInfo.startTime = WASTE_TIME_THREADLOCAL.get();

            return requestInfo;
        } catch (Exception e) {
            log.error("捕获请求信息失败: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 保存操作日志
     */
    private void saveOperationLog(RequestInfo requestInfo, WitLog witLog, Object responseResult) {
        try {
            OperationLogEntity operationLogEntity = new OperationLogEntity();

            operationLogEntity.setIp(requestInfo.ip);
            operationLogEntity.setLocation("未知地址");
            operationLogEntity.setMethod(requestInfo.method);
            operationLogEntity.setUri(requestInfo.uri);
            operationLogEntity.setRequestTime(DateUtil.now());

            if (requestInfo.startTime != null) {
                operationLogEntity.setWasteTime(System.currentTimeMillis() - requestInfo.startTime);
            }

            // 记录请求参数
            operationLogEntity.setRequestParam(objectMapper.writeValueAsString(requestInfo.parameterMap));

            // 记录请求体
            operationLogEntity.setRequestBody(requestInfo.requestBody);

            // 记录响应结果
            operationLogEntity.setResponseResult(objectMapper.writeValueAsString(responseResult));

            OperationLogGateway operationLogGateway = BeanUtils.getBean(OperationLogGateway.class);
            operationLogGateway.save(operationLogEntity);
        } catch (Exception e) {
            log.error("保存操作日志异常: {}", e.getMessage(), e);
        }
    }

    /**
     * 保存异常日志
     */
    private void saveExceptionLog(RequestInfo requestInfo, WitLog witLog, Exception exception) {
        try {
            OperationLogEntity operationLogEntity = new OperationLogEntity();

            operationLogEntity.setIp(requestInfo.ip);
            operationLogEntity.setLocation("未知地址");
            operationLogEntity.setMethod(requestInfo.method);
            operationLogEntity.setUri(requestInfo.uri);
            operationLogEntity.setRequestTime(DateUtil.now());

            if (requestInfo.startTime != null) {
                operationLogEntity.setWasteTime(System.currentTimeMillis() - requestInfo.startTime);
            }

            // 记录请求参数
            operationLogEntity.setRequestParam(objectMapper.writeValueAsString(requestInfo.parameterMap));

            // 记录请求体
            operationLogEntity.setRequestBody(requestInfo.requestBody);

            // 记录异常信息
            operationLogEntity.setResponseResult("Exception: " + exception.getClass().getName() + ": " + exception.getMessage());

            OperationLogGateway operationLogGateway = BeanUtils.getBean(OperationLogGateway.class);
            operationLogGateway.save(operationLogEntity);
        } catch (Exception e) {
            log.error("保存异常日志异常: {}", e.getMessage(), e);
        }
    }

    /**
     * 获取请求体内容
     */
    private String getRequestBody(HttpServletRequest request) {
        try {
            if (request instanceof ContentCachingRequestWrapper wrapper) {
                byte[] buf = wrapper.getContentAsByteArray();
                if (buf.length > 0) {
                    return new String(buf, 0, Math.min(buf.length, 1000)); // 限制长度避免过大
                }
            }
            return "{}";
        } catch (Exception e) {
            log.warn("获取请求体失败: {}", e.getMessage());
            return "{}";
        }
    }

    /**
     * 请求信息封装类
     */
    private static class RequestInfo {
        private String ip;
        private String method;
        private String uri;
        private Map<String, String[]> parameterMap;
        private String requestBody;
        private Long startTime;
    }
}
