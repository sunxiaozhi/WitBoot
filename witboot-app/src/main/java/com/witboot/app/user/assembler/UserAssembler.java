package com.witboot.app.user.assembler;

import com.witboot.client.dto.UserModifyCmd;
import com.witboot.client.dto.UserRegisterCmd;
import com.witboot.client.dto.data.UserVO;
import com.witboot.domain.user.UserEntity;
import com.witboot.domain.user.UserName;
import com.witboot.domain.user.UserPassword;

/**
 * User Application层 转换器
 * 用于DTO与实体之间的互转
 *
 * @author sunxiaozhi
 */
public class UserAssembler {
    public static UserEntity toEntity(UserRegisterCmd co) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(new UserName(co.getUsername()));
        userEntity.setPassword(new UserPassword(co.getPassword()));
        userEntity.setName(co.getName());
        userEntity.setPhoneNo(co.getPhoneNo());
        userEntity.setGender(co.getGender());
        userEntity.setBirthday(co.getBirthday());
        userEntity.setDescription(co.getDescription());

        return userEntity;
    }

    public static UserEntity toEntity(UserModifyCmd co) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(co.getId());
        userEntity.setUsername(new UserName(co.getUsername()));
        userEntity.setName(co.getName());
        userEntity.setPhoneNo(co.getPhoneNo());
        userEntity.setGender(co.getGender());
        userEntity.setBirthday(co.getBirthday());
        userEntity.setDescription(co.getDescription());

        return userEntity;
    }

    public static UserVO toValueObject(UserEntity userEntity) {
        UserVO userVO = new UserVO();
        userVO.setId(userEntity.getId());
        userVO.setName(userEntity.getName());
        userVO.setUsername(userEntity.getUsername().getName());
        userVO.setPhoneNo(userEntity.getPhoneNo());
        userVO.setGender(userEntity.getGender());
        userVO.setBirthday(userEntity.getBirthday());
        userVO.setDescription(userEntity.getDescription());

        return userVO;
    }
}
