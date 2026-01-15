package com.witboot.app.user.assembler;

import com.witboot.common.core.dto.delete.DeleteRequest;
import com.witboot.client.user.dto.UserRegisterCmd;
import com.witboot.client.user.dto.UserUpdateCmd;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.client.user.dto.query.UserListByParamQuery;
import com.witboot.domain.base.model.delete.DeleteRequestSpec;
import com.witboot.domain.user.model.UserEntity;
import com.witboot.domain.user.model.UserPassword;
import com.witboot.domain.user.query.UserQuerySpec;
import org.springframework.beans.BeanUtils;

/**
 * User Application层转换器
 * 用于DTO与实体之间的互转
 *
 * @author sunxiaozhi
 */
public class UserAssembler {
    public static UserQuerySpec toUserListByParamQuerySpec (UserListByParamQuery userListByParamQuery) {
        UserQuerySpec userListByParamQuerySpec = new UserQuerySpec();
        BeanUtils.copyProperties(userListByParamQuery, userListByParamQuerySpec);
        return userListByParamQuerySpec;
    }

    public static UserEntity toEntity(UserRegisterCmd userRegisterCmd) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userRegisterCmd, userEntity);
        return userEntity;
    }

    public static UserEntity toEntity(UserUpdateCmd userUpdateCmd) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userUpdateCmd, userEntity);
        return userEntity;
    }

    public static UserVO toValueObject(UserEntity userEntity) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userEntity, userVO);
        return userVO;
    }

    public static DeleteRequestSpec toDeleteRequestSpec(DeleteRequest deleteRequest) {
        DeleteRequestSpec deleteRequestSpec = new DeleteRequestSpec();
        deleteRequestSpec.setIds(deleteRequest.getIds());
        return deleteRequestSpec;
    }
}
