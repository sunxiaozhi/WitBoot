package com.witboot.app.loginlog.executor.query;

import com.witboot.app.loginlog.assembler.LoginLogAssembler;
import com.witboot.client.loginlog.dto.data.LoginLogErrorCode;
import com.witboot.client.loginlog.dto.data.LoginLogVO;
import com.witboot.domain.loginlog.gateway.LoginLogGateway;
import com.witboot.domain.loginlog.model.LoginLogEntity;
import com.witboot.common.exception.WitBootBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 用户信息查询
 *
 * @author sunxiaoizhi
 */
@Component
public class LoginLogInfoQueryExe {
    @Autowired
    private LoginLogGateway loginLogGateway;

    public LoginLogVO execute(Long id) {
        LoginLogEntity loginLogEntity = loginLogGateway.findById(id);
        if (Objects.isNull(loginLogEntity)) {
            throw new WitBootBizException(LoginLogErrorCode.B_OPERATION_LOG_UNDEFINED);
        }

        return LoginLogAssembler.toValueObject(loginLogEntity);
    }
}
