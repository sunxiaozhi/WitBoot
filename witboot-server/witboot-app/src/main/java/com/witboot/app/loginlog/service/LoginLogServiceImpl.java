package com.witboot.app.loginlog.service;

import com.witboot.app.loginlog.assembler.LoginLogAssembler;
import com.witboot.app.loginlog.executor.command.LoginLogAddCmdExe;
import com.witboot.app.loginlog.executor.command.LoginLogDeleteCmdExe;
import com.witboot.app.loginlog.executor.query.LoginLogInfoQueryExe;
import com.witboot.app.loginlog.executor.query.LoginLogListByParamQueryExe;
import com.witboot.client.base.dto.delete.DeleteRequest;
import com.witboot.client.loginlog.api.ILoginLogService;
import com.witboot.client.loginlog.dto.LoginLogAddCmd;
import com.witboot.client.loginlog.dto.data.LoginLogVO;
import com.witboot.client.loginlog.dto.query.LoginLogListByParamQuery;
import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.base.model.delete.DeleteRequestSpec;
import com.witboot.domain.loginlog.query.LoginLogQuerySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作记录相关
 *
 * @author sunxiaoizhi
 */
@Service
public class LoginLogServiceImpl implements ILoginLogService {
    /**
     * xxxExe 避免 Service 膨胀利器
     */
    @Autowired
    private LoginLogAddCmdExe loginLogAddCmdExe;

    @Autowired
    private LoginLogInfoQueryExe loginLogInfoQueryExe;

    @Autowired
    private LoginLogListByParamQueryExe loginLogListByParamQueryExe;

    @Autowired
    private LoginLogDeleteCmdExe loginLogDeleteCmdExe;

    @Override
    public LoginLogVO add(LoginLogAddCmd loginLogAddCmd) {
        return loginLogAddCmdExe.execute(loginLogAddCmd);
    }

    @Override
    public LoginLogVO getLoginLogInfo(Long id) {
        return loginLogInfoQueryExe.execute(id);
    }

    @Override
    public PageResult<LoginLogVO> listByParam(LoginLogListByParamQuery loginLogListByParamQuery) {
        LoginLogQuerySpec opLogListByParamQuerySpec = LoginLogAssembler.toLoginLogListByParamQuerySpec(loginLogListByParamQuery);
        return loginLogListByParamQueryExe.execute(opLogListByParamQuerySpec);
    }

    @Override
    public int deleteByIds(DeleteRequest deleteRequest) {
        DeleteRequestSpec deleteRequestSpec = LoginLogAssembler.toDeleteRequestSpec(deleteRequest);
        return loginLogDeleteCmdExe.execute(deleteRequestSpec);
    }
}
