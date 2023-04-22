package com.witboot.infrastructure.exception;

import com.witboot.infrastructure.common.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 *
 * @author sunxiaozhi
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseResult<String> handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        return ResponseResult.fail(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<String> handleException(Exception e, HttpServletRequest request)
    {
        return ResponseResult.fail(e.getMessage());
    }
}
