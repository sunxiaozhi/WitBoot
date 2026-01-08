package com.witboot.app.loginlog.executor.command;

import com.witboot.app.loginlog.assembler.LoginLogAssembler;
import com.witboot.client.loginlog.dto.LoginLogAddCmd;
import com.witboot.client.loginlog.dto.data.LoginLogVO;
import com.witboot.domain.loginlog.gateway.LoginLogGateway;
import com.witboot.domain.loginlog.model.LoginLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * LoginLogInsertCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class LoginLogAddCmdExe {

    @Autowired
    private LoginLogGateway loginLogGateway;

    public LoginLogVO execute(LoginLogAddCmd loginLogAddCmd) {
        LoginLogEntity loginLogEntity = loginLogGateway.save(LoginLogAssembler.toEntity(loginLogAddCmd));

        return LoginLogAssembler.toValueObject(loginLogEntity);
    }
}
