package com.witboot.app.user.executor.command;

import com.witboot.app.user.assembler.UserAssembler;
import com.witboot.client.user.dto.UserUpdateCmd;
import com.witboot.client.user.dto.data.UserErrorCode;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * userUpdateCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class UserUpdateCmdExe {
    @Autowired
    private UserGateway userGateway;

    public UserVO execute(UserUpdateCmd userUpdateCmd) {
        // check 用户名是否重复
        if (userGateway.checkByUsername(userUpdateCmd.getId(), userUpdateCmd.getUsername())) {
            throw new WitBootBizException(UserErrorCode.B_USER_USERNAME_REPEAT);
        }

        UserEntity userEntity = userGateway.save(UserAssembler.toEntity(userUpdateCmd));
        return UserAssembler.toValueObject(userEntity);
    }
}
