package com.witboot.client.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.witboot.client.dto.UserModifyCmd;
import com.witboot.client.dto.UserRegisterCmd;
import com.witboot.client.dto.data.UserVO;
import com.witboot.client.dto.query.UserListByParamQuery;
import com.witboot.client.dto.query.UserLoginQuery;

/**
 * 用户相关
 *
 * @author sunxiaoizhi
 */
public interface IUserService {

    /**
     * 注册用户
     *
     * @param cmd 用户注册请求
     * @return Response
     */
    UserVO register(UserRegisterCmd cmd);

    /**
     * 用户信息修改
     *
     * @param cmd 用户信息修改请求
     * @return Response
     */
    UserVO modify(UserModifyCmd cmd);

    /**
     * 用户登录
     *
     * @param query 用户登录请求
     */
    void login(UserLoginQuery query);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    SingleResponse<UserVO> getUserInfo(Long id);

    /**
     * 根据用户名称查询
     *
     * @param userListByParamQuery 用户查询请求
     * @return MultiResponse
     */
    MultiResponse<UserVO> listByName(UserListByParamQuery userListByParamQuery);
}
