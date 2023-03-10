package com.witboot.api;

import com.witboot.api.dto.UserRequestDTO;
import com.witboot.dao.mybatis.dataobject.UserDO;

import java.util.List;

/**
 * 用户接口类
 *
 * @author sunxiaozhi
 */
public interface UserService {
    /**
     * 获取用户列表
     *
     * @return 用户信息
     */
    List<UserDO> getUserList();

    /**
     * 获取用户信息
     *
     * @param id id
     * @return 用户信息
     */
    UserDO getUser(Long id);

    /**
     * 获取用户姓名
     *
     * @param id id
     * @return 用户姓名
     */
    String getUserName(Long id);

    /**
     * 新增用户
     * @param userRequestDTO 用户请求类
     * @return 用户信息
     */
    UserDO addUser(UserRequestDTO userRequestDTO);
}
