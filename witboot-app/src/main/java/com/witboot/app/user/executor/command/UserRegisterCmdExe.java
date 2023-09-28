package com.witboot.app.user.executor.command;

import com.witboot.infrastructure.common.exception.WitBootBizException;
import com.witboot.domain.gateway.UserGateway;
import com.witboot.domain.user.UserEntity;
import com.witboot.client.dto.UserRegisterCmd;
import com.witboot.client.dto.data.ErrorCode;
import com.witboot.client.dto.data.UserVO;
import com.witboot.app.user.assembler.UserAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserAddCmdExe
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
            throw new WitBootBizException(ErrorCode.B_USER_USERNAME_REPEAT);
        }

        UserEntity userEntity = userGateway.save(UserAssembler.toEntity(userRegisterCmd));

        return UserAssembler.toValueObject(userEntity);
    }
}
