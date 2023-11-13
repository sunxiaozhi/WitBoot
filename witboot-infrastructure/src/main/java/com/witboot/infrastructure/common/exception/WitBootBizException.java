package com.witboot.infrastructure.common.exception;

import com.alibaba.cola.exception.BizException;
import com.witboot.client.user.dto.data.ErrorCode;

import java.io.Serial;

/**
 * 业务异常
 *
 * @author sunxiaoizhi
 */
public class WitBootBizException extends BizException {

    @Serial
    private static final long serialVersionUID = -2776433598172531409L;

    public WitBootBizException(ErrorCode errorCode) {
        super(errorCode.getErrorCode(), errorCode.getErrorDescription());
    }
}
