package com.witboot.infrastructure.convertor;

import com.witboot.domain.loginlog.model.LoginLogEntity;
import com.witboot.infrastructure.gateway.impl.database.dataobject.LoginLogDO;

/**
 * LoginLogConvertor DO <---> Entity
 *
 * @author sunxiaoizhi
 */
public class LoginLogConvertor {
    public static LoginLogEntity toEntity(LoginLogDO loginLogDo) {
        LoginLogEntity loginLogEntity = new LoginLogEntity();
        loginLogEntity.setId(loginLogDo.getId());
        loginLogEntity.setIp(loginLogDo.getIp());
        loginLogEntity.setLocation(loginLogDo.getLocation());
        loginLogEntity.setUri(loginLogDo.getUri());
        loginLogEntity.setMethod(loginLogDo.getMethod());
        loginLogEntity.setRequestTime(loginLogDo.getRequestTime());
        loginLogEntity.setWasteTime(loginLogDo.getWasteTime());
        loginLogEntity.setRequestParam(loginLogDo.getRequestParam());
        loginLogEntity.setRequestBody(loginLogDo.getRequestBody());
        loginLogEntity.setResponseResult(loginLogDo.getResponseResult());

        return loginLogEntity;
    }

    public static LoginLogDO toAddLoginLogDo(LoginLogEntity loginLogEntity) {
        LoginLogDO loginLogDo = new LoginLogDO();
        loginLogDo.setIp(loginLogEntity.getIp());
        loginLogDo.setLocation(loginLogEntity.getLocation());
        loginLogDo.setUri(loginLogEntity.getUri());
        loginLogDo.setMethod(loginLogEntity.getMethod());
        loginLogDo.setRequestTime(loginLogEntity.getRequestTime());
        loginLogDo.setWasteTime(loginLogEntity.getWasteTime());
        loginLogDo.setRequestParam(loginLogEntity.getRequestParam());
        loginLogDo.setRequestBody(loginLogEntity.getRequestBody());
        loginLogDo.setResponseResult(loginLogEntity.getResponseResult());

        return loginLogDo;
    }
}
