package com.witboot.app.user.executor.query;

import com.alibaba.cola.dto.MultiResponse;
import com.witboot.domain.gateway.UserGateway;
import com.witboot.domain.user.UserEntity;
import com.witboot.client.dto.data.UserVO;
import com.witboot.client.dto.query.UserListByParamQuery;
import com.witboot.app.user.assembler.UserAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserListByNameQueryExe
 *
 * @author sunxiaoizhi
 */
@Component
public class UserListByParamQueryExe {

    @Autowired
    private UserGateway userGateway;

    public MultiResponse<UserVO> execute(UserListByParamQuery query) {
        List<UserEntity> userEntities = userGateway.findByParam(query);
        List<UserVO> userVOList = userEntities.stream()
                .map(UserAssembler::toValueObject)
                .collect(Collectors.toList());

        return MultiResponse.of(userVOList);
    }

}
