package com.witboot.app.user.service;

import com.witboot.app.user.assembler.UserAssembler;
import com.witboot.app.user.executor.command.UserDeleteCmdExe;
import com.witboot.app.user.executor.command.UserRegisterCmdExe;
import com.witboot.app.user.executor.command.UserUpdateCmdExe;
import com.witboot.app.user.executor.query.UserInfoQueryExe;
import com.witboot.app.user.executor.query.UserListByParamQueryExe;
import com.witboot.app.user.executor.query.UserLoginQueryExe;
import com.witboot.client.base.dto.delete.DeleteRequest;
import com.witboot.client.user.api.IUserService;
import com.witboot.client.user.dto.UserRegisterCmd;
import com.witboot.client.user.dto.UserUpdateCmd;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.client.user.dto.query.UserListByParamQuery;
import com.witboot.client.user.dto.query.UserLoginQuery;
import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.base.model.delete.DeleteRequestSpec;
import com.witboot.domain.user.query.UserQuerySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户相关
 *
 * @author sunxiaoizhi
 */
@Service
public class UserServiceImpl implements IUserService {
    /**
     * xxxExe 避免 Service 膨胀利器
     */
    @Autowired
    private UserRegisterCmdExe userRegisterCmdExe;

    @Autowired
    private UserUpdateCmdExe userUpdateCmdExe;

    @Autowired
    private UserLoginQueryExe userLoginQueryExe;

    @Autowired
    private UserInfoQueryExe userInfoQueryExe;

    @Autowired
    private UserListByParamQueryExe userListByParamQueryExe;

    @Autowired
    private UserDeleteCmdExe userDeleteCmdExe;

    @Override
    public UserVO register(UserRegisterCmd userRegisterCmd) {
        return userRegisterCmdExe.execute(userRegisterCmd);
    }

    @Override
    public UserVO update(UserUpdateCmd userUpdateCmd) {
        return userUpdateCmdExe.execute(userUpdateCmd);
    }

    @Override
    public String login(UserLoginQuery userLoginQuery) {
        return userLoginQueryExe.execute(userLoginQuery);
    }

    @Override
    public UserVO getUserInfo(Long id) {
        return userInfoQueryExe.execute(id);
    }

    @Override
    public PageResult<UserVO> listByParam(UserListByParamQuery userListByParamQuery) {
        UserQuerySpec userListByParamQuerySpec = UserAssembler.toUserListByParamQuerySpec(userListByParamQuery);
        return userListByParamQueryExe.execute(userListByParamQuerySpec);
    }

    @Override
    public int deleteByIds(DeleteRequest deleteRequest) {
        DeleteRequestSpec deleteRequestSpec = UserAssembler.toDeleteRequestSpec(deleteRequest);
        return userDeleteCmdExe.execute(deleteRequestSpec);
    }
}
