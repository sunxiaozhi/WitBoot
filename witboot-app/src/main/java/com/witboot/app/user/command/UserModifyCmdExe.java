package com.witboot.app.user.command;

import com.witboot.app.user.assembler.UserAssembler;
import com.witboot.client.dto.UserModifyCmd;
import com.witboot.client.dto.data.ErrorCode;
import com.witboot.client.dto.data.UserVO;
import com.witboot.domain.gateway.UserGateway;
import com.witboot.domain.user.UserEntity;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserAddCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class UserModifyCmdExe {
    @Autowired
    private UserGateway userGateway;

    public UserVO execute(UserModifyCmd cmd) {
        // check 用户名是否重复
        if (userGateway.checkByUsername(cmd.getId(), cmd.getUsername())) {
            throw new WitBootBizException(ErrorCode.B_USER_USERNAME_REPEAT);
        }

        UserEntity userEntity = userGateway.save(UserAssembler.toEntity(cmd));
        return UserAssembler.toValueObject(userEntity);
    }
}
