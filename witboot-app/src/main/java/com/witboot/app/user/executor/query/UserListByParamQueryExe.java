package com.witboot.app.user.executor.query;

import com.witboot.app.user.assembler.UserAssembler;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.client.user.dto.query.UserListByParamQuery;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.domain.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserListByParamQueryExe
 *
 * @author sunxiaoizhi
 */
@Component
public class UserListByParamQueryExe {

    @Autowired
    private UserGateway userGateway;

    public List<UserVO> execute(UserListByParamQuery userListByParamQuery) {
        List<UserEntity> userEntities = userGateway.findByParam(userListByParamQuery);

        return userEntities.stream()
                .map(UserAssembler::toValueObject)
                .collect(Collectors.toList());
    }
}
