package com.witboot.infrastructure.convertor;

import com.witboot.domain.user.model.UserEntity;
import com.witboot.domain.user.model.UserName;
import com.witboot.infrastructure.gateway.impl.database.dataobject.UserDO;

/**
 * UserConvertor DO <---> Entity
 *
 * @author sunxiaoizhi
 */
public class UserConvertor {
    public static UserEntity toEntity(UserDO userDO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDO.getId());
        userEntity.setUsername(new UserName(userDO.getUsername()));
        userEntity.setName(userDO.getName());
        userEntity.setMobile(userDO.getMobile());
        userEntity.setGender(userDO.getGender());
        userEntity.setBirthday(userDO.getBirthday());
        userEntity.setDescription(userDO.getDescription());

        return userEntity;
    }

    public static UserDO toAddUserDO(UserEntity userEntity) {
        UserDO userDO = new UserDO();
        userDO.setId(userEntity.getId());
        userDO.setUsername(userEntity.getUsername().name());
        userDO.setPassword(userEntity.getPassword().getEncryptPassword());
        userDO.setName(userEntity.getName());
        userDO.setDeleteFlag(false);
        userDO.setMobile(userEntity.getMobile());
        userDO.setGender(userEntity.getGender());
        userDO.setBirthday(userEntity.getBirthday());
        userDO.setDescription(userEntity.getDescription());
        userDO.setCreator("");
        userDO.setModifier("");

        return userDO;
    }

    public static void toModifyUserDO(UserEntity userEntity, UserDO userDO) {
        userDO.setName(userEntity.getName());
        userDO.setUsername(userEntity.getUsername().name());
        userDO.setMobile(userEntity.getMobile());
        userDO.setGender(userEntity.getGender());
        userDO.setBirthday(userEntity.getBirthday());
        userDO.setDescription(userEntity.getDescription());
    }
}
