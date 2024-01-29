package com.witboot.app.user.executor.command;

import com.witboot.app.user.assembler.UserAssembler;
import com.witboot.client.user.dto.UserModifyCmd;
import com.witboot.client.user.dto.data.ErrorCode;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserModifyCmdExe
 *
 * @author sunxiaoizhi
 */
@Component
public class UserModifyCmdExe {
    @Autowired
    private UserGateway userGateway;

    public UserVO execute(UserModifyCmd userModifyCmd) {
        // check 用户名是否重复
        if (userGateway.checkByUsername(userModifyCmd.getId(), userModifyCmd.getUsername())) {
            throw new WitBootBizException(ErrorCode.B_USER_USERNAME_REPEAT);
        }

        UserEntity userEntity = userGateway.save(UserAssembler.toEntity(userModifyCmd));
        return UserAssembler.toValueObject(userEntity);
    }
}
