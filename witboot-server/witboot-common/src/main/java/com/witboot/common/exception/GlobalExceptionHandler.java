package com.witboot.common.exception;


import com.witboot.common.response.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 *
 * @author sunxiaozhi
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WitBootBizException .class)
    public ResponseResult<String> handleWitBootBizException(WitBootBizException witBootBizException) {
        return ResponseResult.fail(witBootBizException.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseResult<String> handleRuntimeException(RuntimeException runtimeException) {
        return ResponseResult.fail(runtimeException.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<String> handleException(Exception exception) {
        return ResponseResult.fail(exception.getMessage());
    }
}
