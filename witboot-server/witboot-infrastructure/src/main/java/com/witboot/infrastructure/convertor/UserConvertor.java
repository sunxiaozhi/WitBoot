package com.witboot.infrastructure.convertor;

import com.witboot.domain.user.model.UserEntity;
import com.witboot.common.Constants;
import com.witboot.infrastructure.gateway.impl.database.dataobject.UserDO;
import org.springframework.beans.BeanUtils;

/**
 * UserConvertor DO <---> Entity
 *
 * @author sunxiaoizhi
 */
public class UserConvertor {
    public static UserEntity toEntity(UserDO userDO) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDO, userEntity);
        return userEntity;
    }

    public static UserDO toAddUserDO(UserEntity userEntity) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userEntity, userDO);

        userDO.setDeleteFlag(Constants.NOT_DELETED_FLAG);
        userDO.setCreator("");
        userDO.setModifier("");

        return userDO;
    }

    public static void toModifyUserDO(UserEntity userEntity, UserDO userDO) {
        userDO.setName(userEntity.getName());
        userDO.setUsername(userEntity.getUsername());
        userDO.setMobile(userEntity.getMobile());
        userDO.setGender(userEntity.getGender());
        userDO.setBirthday(userEntity.getBirthday());
        userDO.setDescription(userEntity.getDescription());
    }
}
