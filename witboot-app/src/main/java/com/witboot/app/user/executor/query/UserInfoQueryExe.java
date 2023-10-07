package com.witboot.app.user.executor.query;

import com.alibaba.cola.dto.SingleResponse;
import com.witboot.app.user.assembler.UserAssembler;
import com.witboot.client.user.dto.data.ErrorCode;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 用户信息查询
 *
 * @author sunxiaoizhi
 */
@Component
public class UserInfoQueryExe {

    @Autowired
    private UserGateway userGateway;

    public SingleResponse<UserVO> execute(Long id) {
        UserEntity userEntity = userGateway.findById(id);
        if (Objects.isNull(userEntity)) {
            throw new WitBootBizException(ErrorCode.B_USER_UNDEFINED);
        }

        return SingleResponse.of(UserAssembler.toValueObject(userEntity));
    }
}
