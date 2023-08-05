package com.witboot.infrastructure.gateway.impl.database.mapper;

import com.witboot.infrastructure.gateway.impl.database.dataobject.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * User Info Mapper
 *
 * @author sunxiaoizhi
 */
@Mapper
public interface UserInfoMapper {

    int insert(UserInfoDO userInfoDO);

    int update(UserInfoDO userInfoDO);

    UserInfoDO findById(Long id);

}
