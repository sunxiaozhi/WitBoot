package com.witboot.app.operationlog.service;

import com.witboot.app.operationlog.executor.command.OperationLogDeleteCmdExe;
import com.witboot.app.operationlog.executor.command.OperationLogAddCmdExe;
import com.witboot.app.operationlog.executor.query.OperationLogInfoQueryExe;
import com.witboot.app.operationlog.executor.query.OperationLogListByParamQueryExe;
import com.witboot.client.operationlog.api.IOperationLogService;
import com.witboot.client.operationlog.dto.OperationLogAddCmd;
import com.witboot.client.operationlog.dto.data.OperationLogVO;
import com.witboot.client.operationlog.dto.query.OperationLogListByParamQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作记录相关
 *
 * @author sunxiaoizhi
 */
@Service
public class OperationLogServiceImpl implements IOperationLogService {
    /**
     * xxxExe 避免 Service 膨胀利器
     */
    @Autowired
    private OperationLogAddCmdExe operationLogAddCmdExe;

    @Autowired
    private OperationLogInfoQueryExe operationlogInfoQueryExe;

    @Autowired
    private OperationLogListByParamQueryExe operationlogListByParamQueryExe;

    @Autowired
    private OperationLogDeleteCmdExe operationlogDeleteCmdExe;

    @Override
    public OperationLogVO add(OperationLogAddCmd operationLogAddCmd) {
        return operationLogAddCmdExe.execute(operationLogAddCmd);
    }

    @Override
    public OperationLogVO getOperationLogInfo(Long id) {
        return operationlogInfoQueryExe.execute(id);
    }

    @Override
    public List<OperationLogVO> listByParam(OperationLogListByParamQuery operationlogListByParamQuery) {
        return operationlogListByParamQueryExe.execute(operationlogListByParamQuery);
    }

    @Override
    public int deleteByIds(String[] ids) {
        return operationlogDeleteCmdExe.execute(ids);
    }
}
