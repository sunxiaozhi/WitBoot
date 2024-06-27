package com.witboot.infrastructure.common.exception;

import com.alibaba.cola.exception.BizException;
import com.witboot.client.base.dato.data.BaseErrorCode;

import java.io.Serial;

/**
 * 业务异常
 *
 * @author sunxiaoizhi
 */
public class WitBootBizException extends BizException {

    @Serial
    private static final long serialVersionUID = -2776433598172531409L;

    public WitBootBizException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode.getErrorCode(), baseErrorCode.getErrorDescription());
    }
}
