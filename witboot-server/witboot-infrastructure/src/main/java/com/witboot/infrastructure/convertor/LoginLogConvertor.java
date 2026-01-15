package com.witboot.infrastructure.convertor;

import com.witboot.domain.loginlog.model.LoginLogEntity;
import com.witboot.infrastructure.gateway.impl.database.dataobject.LoginLogDO;
import org.springframework.beans.BeanUtils;

/**
 * LoginLogConvertor DO <---> Entity
 *
 * @author sunxiaoizhi
 */
public class LoginLogConvertor {
    public static LoginLogEntity toEntity(LoginLogDO loginLogDo) {
        LoginLogEntity loginLogEntity = new LoginLogEntity();
        BeanUtils.copyProperties(loginLogDo, loginLogEntity);
        return loginLogEntity;
    }

    public static LoginLogDO toAddLoginLogDo(LoginLogEntity loginLogEntity) {
        LoginLogDO loginLogDo = new LoginLogDO();
        BeanUtils.copyProperties(loginLogEntity, loginLogDo);
        return loginLogDo;
    }
}
