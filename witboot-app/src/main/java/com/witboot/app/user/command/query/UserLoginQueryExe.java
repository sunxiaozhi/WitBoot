package com.witboot.app.user.command.query;

import com.witboot.infrastructure.common.exception.WitBootBizException;
import com.witboot.domain.gateway.UserGateway;
import com.witboot.domain.user.UserEntity;
import com.witboot.client.dto.data.ErrorCode;
import com.witboot.client.dto.query.UserLoginQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * UserListByNameQueryExe
 *
 * @author sunxiaoizhi
 */
@Component
public class UserLoginQueryExe {

    @Autowired
    private UserGateway userGateway;

    public void execute(UserLoginQuery query) {
        UserEntity userEntity = userGateway.findPasswordInfo(query.getUsername());
        if (Objects.isNull(userEntity)) {
            throw new WitBootBizException(ErrorCode.B_USER_PASSWORD_ERROR);
        }

        // 校验密码是否正确
        if (!userEntity.getPassword().isCorrect(query.getPassword())) {
            throw new WitBootBizException(ErrorCode.B_USER_PASSWORD_ERROR);
        }
    }
}
