package com.witboot.app.user.executor.query;

import com.witboot.client.user.dto.data.UserErrorCode;
import com.witboot.client.user.dto.query.UserLoginQuery;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.infrastructure.common.Constants;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import com.witboot.infrastructure.common.utils.JwtTokenUtil;
import com.witboot.infrastructure.common.utils.RedisUtil;
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
    private RedisUtil redisUtil;

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String execute(UserLoginQuery userLoginQuery) {
        UserEntity userEntity = userGateway.findPasswordInfo(userLoginQuery.getUsername());
        if (Objects.isNull(userEntity)) {
            throw new WitBootBizException(UserErrorCode.B_USER_PASSWORD_ERROR);
        }

        // 校验密码是否正确
        if (!userEntity.getPassword().isCorrect(userLoginQuery.getPassword())) {
            throw new WitBootBizException(UserErrorCode.B_USER_PASSWORD_ERROR);
        }

        String jwtToken = jwtTokenUtil.generateToken(userEntity);

        //将jwtToken存进Redis，用于后续登录判断jwtToken的有效性
        redisUtil.setCache(Constants.LOGIN_USER_KEY + userEntity.getUsername(), jwtToken);

        return jwtToken;
    }
}
