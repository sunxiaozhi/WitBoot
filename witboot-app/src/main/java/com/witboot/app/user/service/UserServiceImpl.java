package com.witboot.app.user.service;

import com.alibaba.cola.catchlog.CatchAndLog;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.SingleResponse;
import com.witboot.app.user.command.UserModifyCmdExe;
import com.witboot.app.user.command.UserRegisterCmdExe;
import com.witboot.app.user.command.query.UserInfoQueryExe;
import com.witboot.app.user.command.query.UserListByParamQueryExe;
import com.witboot.app.user.command.query.UserLoginQueryExe;
import com.witboot.client.api.IUserService;
import com.witboot.client.dto.UserModifyCmd;
import com.witboot.client.dto.UserRegisterCmd;
import com.witboot.client.dto.data.UserVO;
import com.witboot.client.dto.query.UserListByParamQuery;
import com.witboot.client.dto.query.UserLoginQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户相关
 *
 * @author sunxiaoizhi
 */
@Service
@CatchAndLog
public class UserServiceImpl implements IUserService {

    /**
     * xxxExe 避免 Service 膨胀利器
     */
    @Autowired
    private UserRegisterCmdExe userRegisterCmdExe;
    @Autowired
    private UserModifyCmdExe userModifyCmdExe;
    @Autowired
    private UserLoginQueryExe userLoginQueryExe;
    @Autowired
    private UserInfoQueryExe userInfoQueryExe;
    @Autowired
    private UserListByParamQueryExe userListByParamQueryExe;

    @Override
    public UserVO register(UserRegisterCmd cmd) {
        return userRegisterCmdExe.execute(cmd);
    }

    @Override
    public UserVO modify(UserModifyCmd cmd) {
        return userModifyCmdExe.execute(cmd);
    }

    @Override
    public void login(UserLoginQuery query) {
        userLoginQueryExe.execute(query);
    }

    @Override
    public SingleResponse<UserVO> getUserInfo(Long id) {
        return userInfoQueryExe.execute(id);
    }

    @Override
    public MultiResponse<UserVO> listByName(UserListByParamQuery query) {
        return userListByParamQueryExe.execute(query);
    }
}
