package com.witboot.common.response;

import com.witboot.common.enums.ResponseCode;
import lombok.Data;

/**
 * 响应结果实体
 *
 * @author sunxiaozhi
 */
@Data
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private int code;

    /**
     * 数据
     */
    private T data;

    /**
     * 提示语
     */
    private String message;

    protected ResponseResult() {
    }

    private static <T> ResponseResult<T> responseResult(int code, String message, T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setData(data);
        return responseResult;
    }

    public static <T> ResponseResult<T> success(int rows) {
        return responseResult(ResponseCode.SUCCESS.getCode(), rows > 0 ? "成功" : "失败", null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return responseResult(ResponseCode.SUCCESS.getCode(), "成功", data);
    }

    public static <T> ResponseResult<T> fail(String message) {
        return responseResult(ResponseCode.FAIL.getCode(), message, null);
    }
}
