package com.witboot.app.user.executor.query;

import com.witboot.app.user.assembler.UserAssembler;
import com.witboot.client.user.dto.data.UserErrorCode;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.common.exception.WitBootBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

;

/**
 * 用户信息查询
 *
 * @author sunxiaoizhi
 */
@Component
public class UserInfoQueryExe {
    @Autowired
    private UserGateway userGateway;

    public UserVO execute(Long id) {
        UserEntity userEntity = userGateway.findById(id);
        if (Objects.isNull(userEntity)) {
            throw new WitBootBizException(UserErrorCode.B_USER_UNDEFINED);
        }

        return UserAssembler.toValueObject(userEntity);
    }
}
