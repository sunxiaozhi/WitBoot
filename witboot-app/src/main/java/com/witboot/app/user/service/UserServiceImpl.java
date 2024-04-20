package com.witboot.app.user.service;

import com.alibaba.cola.catchlog.CatchAndLog;
import com.witboot.app.user.executor.command.UserDeleteCmdExe;
import com.witboot.app.user.executor.command.UserModifyCmdExe;
import com.witboot.app.user.executor.command.UserRegisterCmdExe;
import com.witboot.app.user.executor.query.UserInfoQueryExe;
import com.witboot.app.user.executor.query.UserListByParamQueryExe;
import com.witboot.app.user.executor.query.UserLoginQueryExe;
import com.witboot.client.user.api.IUserService;
import com.witboot.client.user.dto.UserModifyCmd;
import com.witboot.client.user.dto.UserRegisterCmd;
import com.witboot.client.user.dto.data.UserVO;
import com.witboot.client.user.dto.query.UserListByParamQuery;
import com.witboot.client.user.dto.query.UserLoginQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private UserDeleteCmdExe userDeleteCmdExe;

    @Override
    public UserVO register(UserRegisterCmd userRegisterCmd) {
        return userRegisterCmdExe.execute(userRegisterCmd);
    }

    @Override
    public UserVO modify(UserModifyCmd cmd) {
        return userModifyCmdExe.execute(cmd);
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
    public List<UserVO> listByParam(UserListByParamQuery userListByParamQuery) {
        return userListByParamQueryExe.execute(userListByParamQuery);
    }

    /**
     * 用户删除
     * @param ids ids
     * @return 删除数量
     */
    @Override
    public int deleteByIds(String[] ids){
        return userDeleteCmdExe.execute(ids);
    }
}
