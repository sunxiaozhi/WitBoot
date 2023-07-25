package com.witboot.domain.mybatis.mapper;

import com.witboot.domain.mybatis.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户信息表 数据层
 *
 * @author sunxiaozhi
 */
@Mapper
public interface UserMapper {
    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    List<UserDO> getUserList();

    /**
     * 获取用户信息
     *
     * @param name 用户名
     * @return 用户信息
     */
    UserDO getByName(String name);

    /**
     * 获取用户信息
     *
     * @param id 用户名
     * @return 用户信息
     */
    UserDO getById(Long id);

    /**
     * 获取用户信息
     *
     * @param userDO 用户
     * @return 用户信息
     */
    Long insert(UserDO userDO);
}
