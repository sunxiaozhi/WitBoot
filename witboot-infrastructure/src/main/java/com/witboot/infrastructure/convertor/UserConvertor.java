package com.witboot.infrastructure.convertor;

import com.witboot.domain.user.model.UserEntity;
import com.witboot.domain.user.model.UserName;
import com.witboot.infrastructure.gateway.impl.database.dataobject.UserDO;
import com.witboot.infrastructure.gateway.impl.database.dataobject.UserInfoDO;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.time.LocalDateTime;

/**
 * UserConvertor DO <---> Entity
 *
 * @author sunxiaoizhi
 */
public class UserConvertor {

    public static UserEntity toEntity(UserDO userDO, UserInfoDO userInfoDO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDO.getId());
        userEntity.setUsername(new UserName(userDO.getUsername()));
        userEntity.setName(userDO.getName());

        if (userInfoDO != null) {
            userEntity.setPhoneNo(userInfoDO.getPhoneNo());
            userEntity.setGender(userInfoDO.getGender());
            userEntity.setBirthday(userInfoDO.getBirthday());
            userEntity.setDescription(userInfoDO.getDescription());
        }

        return userEntity;
    }

    public static ImmutablePair<UserDO, UserInfoDO> toAddUserDO(UserEntity userEntity) {
        UserDO userDO = new UserDO();
        userDO.setId(userEntity.getId());
        userDO.setUsername(userEntity.getUsername().getName());
        userDO.setPassword(userEntity.getPassword().getEncryptPassword());
        userDO.setName(userEntity.getName());
        userDO.setDeleteFlag(false);
        userDO.setGmtCreate(LocalDateTime.now());

        // user info
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setPhoneNo(userEntity.getPhoneNo());
        userInfoDO.setGender(userEntity.getGender());
        userInfoDO.setBirthday(userEntity.getBirthday());
        userInfoDO.setDescription(userEntity.getDescription());

        return new ImmutablePair<>(userDO, userInfoDO);
    }

    public static void toModifyUserDO(UserEntity userEntity, UserDO userDO, UserInfoDO userInfoDO) {
        userDO.setName(userEntity.getName());
        userDO.setUsername(userEntity.getUsername().getName());

        userInfoDO.setPhoneNo(userEntity.getPhoneNo());
        userInfoDO.setGender(userEntity.getGender());
        userInfoDO.setBirthday(userEntity.getBirthday());
        userInfoDO.setDescription(userEntity.getDescription());
    }
}
