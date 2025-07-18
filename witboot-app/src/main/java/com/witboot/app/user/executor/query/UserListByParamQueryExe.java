package com.witboot.app.user.executor.query;

import com.witboot.app.user.assembler.UserAssembler;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.user.gateway.UserGateway;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.domain.user.query.UserQuerySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * UserListByParamQueryExe
 *
 * @author sunxiaoizhi
 */
@Component
public class UserListByParamQueryExe {

    @Autowired
    private UserGateway userGateway;

    public PageResult<UserVO> execute(UserQuerySpec userListByParamQuerySpec) {
        PageResult<UserEntity> userEntityPageResult = userGateway.findByParam(userListByParamQuerySpec);

        List<UserVO> userVOList = userEntityPageResult.getList().stream().map(UserAssembler::toValueObject).toList();

        return PageResult.build(userVOList, userEntityPageResult.getTotal());
    }
}
