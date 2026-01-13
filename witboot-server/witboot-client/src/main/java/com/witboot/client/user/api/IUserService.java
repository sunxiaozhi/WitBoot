package com.witboot.client.user.api;

import com.witboot.common.core.dto.delete.DeleteRequest;
import com.witboot.client.user.dto.UserRegisterCmd;
import com.witboot.client.user.dto.UserUpdateCmd;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.client.user.dto.query.UserListByParamQuery;
import com.witboot.client.user.dto.query.UserLoginQuery;
import com.witboot.common.core.model.PageResult;

/**
 * 用户相关
 *
 * @author sunxiaoizhi
 */
public interface IUserService {
    /**
     * 注册用户
     *
     * @param userRegisterCmd 用户注册请求
     * @return Response
     */
    UserVO register(UserRegisterCmd userRegisterCmd);

    /**
     * 用户信息修改
     *
     * @param userUpdateCmd 用户信息修改请求
     * @return Response
     */
    UserVO update(UserUpdateCmd userUpdateCmd);

    /**
     * 用户登录
     *
     * @param userLoginQuery 用户登录请求
     * @return 登录信息
     */
    String login(UserLoginQuery userLoginQuery);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserVO getUserInfo(Long id);

    /**
     * 根据用户名称查询
     *
     *
     * @param userListByParamQuery 用户查询请求
     * @return MultiResponse
     */
    PageResult<UserVO> listByParam(UserListByParamQuery userListByParamQuery);

    /**
     * 用户删除
     * @param deleteRequest 删除请求
     * @return 删除数量
     */
    int deleteByIds(DeleteRequest deleteRequest);
}
