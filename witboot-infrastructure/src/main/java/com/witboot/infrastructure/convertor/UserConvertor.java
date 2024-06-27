package com.witboot.infrastructure.convertor;

import com.witboot.domain.user.model.UserEntity;
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
        /*userEntity.setId(userDO.getId());
        userEntity.setUsername(userDO.getUsername());
        userEntity.setName(userDO.getName());
        userEntity.setMobile(userDO.getMobile());
        userEntity.setGender(userDO.getGender());
        userEntity.setBirthday(userDO.getBirthday());
        userEntity.setDescription(userDO.getDescription());*/
        BeanUtils.copyProperties(userDO, userEntity);

        return userEntity;
    }

    public static UserDO toAddUserDO(UserEntity userEntity) {
        UserDO userDO = new UserDO();
        userDO.setId(userEntity.getId());
        userDO.setUsername(userEntity.getUsername());
        userDO.setPassword(userEntity.getPassword().getEncryptPassword());
        userDO.setName(userEntity.getName());
        userDO.setDeleteFlag(1);
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
        userDO.setUsername(userEntity.getUsername());
        userDO.setMobile(userEntity.getMobile());
        userDO.setGender(userEntity.getGender());
        userDO.setBirthday(userEntity.getBirthday());
        userDO.setDescription(userEntity.getDescription());
    }
}
