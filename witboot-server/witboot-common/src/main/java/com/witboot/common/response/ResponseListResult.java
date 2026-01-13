package com.witboot.common.response;

import com.github.pagehelper.PageInfo;
import com.witboot.common.enums.ResponseCode;
import lombok.Data;

import java.util.List;

/**
 * 响应结果实体
 *
 * @author sunxiaozhi
 */
@Data
public class ResponseListResult {
    /**
     * 状态码
     */
    private int code;

    /**
     * 数据
     */
    private List<?> rows;

    /**
     * 数据
     */
    private Long total;

    /**
     * 提示语
     */
    private String msg;

    protected ResponseListResult() {
    }

    private static ResponseListResult responseListResult(int code, List<?> data) {
        ResponseListResult responseResult = new ResponseListResult();
        responseResult.setCode(code);
        responseResult.setRows(data);
        responseResult.setTotal(new PageInfo<>(data).getTotal());
        responseResult.setMsg("成功");
        return responseResult;
    }

    public static <T> ResponseListResult listInfo(List<?> data) {
        return responseListResult(ResponseCode.SUCCESS.getCode(), data);
    }
}
