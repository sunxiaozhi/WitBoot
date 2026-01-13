package com.witboot.app.user.executor.command;

import com.witboot.app.user.assembler.UserAssembler;
import com.witboot.client.user.dto.UserRegisterCmd;
import com.witboot.client.user.dto.data.UserErrorCode;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.common.exception.WitBootBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserRegisterCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class UserRegisterCmdExe {

    @Autowired
    private UserGateway userGateway;

    public UserVO execute(UserRegisterCmd userRegisterCmd) {
        // check 用户名是否重复
        if (userGateway.checkByUsername(null, userRegisterCmd.getUsername())) {
            throw new WitBootBizException(UserErrorCode.B_USER_USERNAME_REPEAT);
        }

        if (!userRegisterCmd.getPassword().equals(userRegisterCmd.getRePassword())) {
            throw new WitBootBizException(UserErrorCode.B_USER_PASSWORD_NOT_EQUALS);
        }

        UserEntity userEntity = userGateway.save(UserAssembler.toEntity(userRegisterCmd));

        return UserAssembler.toValueObject(userEntity);
    }
}
