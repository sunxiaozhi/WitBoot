package com.witboot.infrastructure.gateway.impl.database.mapper;

import com.witboot.client.user.dto.query.UserListByParamQuery;
import com.witboot.infrastructure.gateway.impl.database.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * User Mapper
 *
 * @author sunxiaoizhi
 */
@Mapper
public interface UserMapper {
    int insert(UserDO userDO);

    int update(UserDO userDO);

    Optional<UserDO> selectById(Long id);

    Optional<UserDO> selectByUsername(String username);

    List<UserDO> selectByParam(UserListByParamQuery query);

    Boolean existByUsername(@Param("userId") Long userId, @Param("username") String username);

    int deleteByIds(String[] ids);
}
