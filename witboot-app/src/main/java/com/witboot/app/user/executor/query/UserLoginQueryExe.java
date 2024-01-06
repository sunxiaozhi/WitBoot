package com.witboot.app.user.executor.query;

import com.witboot.client.user.dto.data.ErrorCode;
import com.witboot.client.user.dto.query.UserLoginQuery;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import com.witboot.infrastructure.common.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * UserLoginQueryExe
 *
 * @author sunxiaoizhi
 */
@Component
public class UserLoginQueryExe {

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String execute(UserLoginQuery userLoginQuery) {
        UserEntity userEntity = userGateway.findPasswordInfo(userLoginQuery.getUsername());
        if (Objects.isNull(userEntity)) {
            throw new WitBootBizException(ErrorCode.B_USER_PASSWORD_ERROR);
        }

        // 校验密码是否正确
        if (!userEntity.getPassword().isCorrect(userLoginQuery.getPassword())) {
            throw new WitBootBizException(ErrorCode.B_USER_PASSWORD_ERROR);
        }

        return jwtTokenUtil.generateToken(userEntity);
    }
}
