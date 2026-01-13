package com.witboot.app.loginlog.executor.query;

import com.witboot.app.loginlog.assembler.LoginLogAssembler;
import com.witboot.client.loginlog.dto.data.LoginLogVO;
import com.witboot.common.core.model.PageResult;
import com.witboot.domain.loginlog.gateway.LoginLogGateway;
import com.witboot.domain.loginlog.model.LoginLogEntity;
import com.witboot.domain.loginlog.query.LoginLogQuerySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * LoginLogListByParamQueryExe
 *
 * @author sunxiaoizhi
 */
@Component
public class LoginLogListByParamQueryExe {

    @Autowired
    private LoginLogGateway loginLogGateway;

    public PageResult<LoginLogVO> execute(LoginLogQuerySpec loginLogListByParamQuerySpec) {
        PageResult<LoginLogEntity> loginLogEntityPageResult = loginLogGateway.findByParam(loginLogListByParamQuerySpec);

        List<LoginLogVO> loginLogVOList = loginLogEntityPageResult.getList().stream().map(LoginLogAssembler::toValueObject).toList();

        return PageResult.build(loginLogVOList, loginLogEntityPageResult.getTotal());
    }
}