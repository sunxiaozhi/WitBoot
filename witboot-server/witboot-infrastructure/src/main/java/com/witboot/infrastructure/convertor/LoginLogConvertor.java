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
        loginLogEntity.setUserId(loginLogDo.getUserId());
        loginLogEntity.setUserName(loginLogDo.getUserName());
        loginLogEntity.setLocation(loginLogDo.getLocation());
        loginLogEntity.setUserAgent(loginLogDo.getUserAgent());
        loginLogEntity.setOs(loginLogDo.getOs());
        loginLogEntity.setDevice(loginLogDo.getDevice());
        loginLogEntity.setBrowser(loginLogDo.getBrowser());
        loginLogEntity.setLoginTime(loginLogDo.getLoginTime());

        return loginLogEntity;
    }

    public static LoginLogDO toAddLoginLogDo(LoginLogEntity loginLogEntity) {
        LoginLogDO loginLogDo = new LoginLogDO();
        loginLogDo.setIp(loginLogEntity.getIp());
        loginLogDo.setUserId(loginLogEntity.getUserId());
        loginLogDo.setUserName(loginLogEntity.getUserName());
        loginLogDo.setLocation(loginLogEntity.getLocation());
        loginLogDo.setUserAgent(loginLogEntity.getUserAgent());
        loginLogDo.setOs(loginLogEntity.getOs());
        loginLogDo.setDevice(loginLogEntity.getDevice());
        loginLogDo.setBrowser(loginLogEntity.getBrowser());
        loginLogDo.setLoginTime(loginLogEntity.getLoginTime());

        return loginLogDo;
    }
}
