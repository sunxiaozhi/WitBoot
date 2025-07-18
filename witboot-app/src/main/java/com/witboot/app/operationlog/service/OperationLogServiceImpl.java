package com.witboot.app.operationlog.service;

import com.witboot.app.operationlog.assembler.OperationLogAssembler;
import com.witboot.app.operationlog.executor.command.OperationLogAddCmdExe;
import com.witboot.app.operationlog.executor.command.OperationLogDeleteCmdExe;
import com.witboot.app.operationlog.executor.query.OperationLogInfoQueryExe;
import com.witboot.app.operationlog.executor.query.OperationLogListByParamQueryExe;
import com.witboot.client.operationlog.api.IOperationLogService;
import com.witboot.client.operationlog.dto.OperationLogAddCmd;
import com.witboot.client.operationlog.dto.data.OperationLogVO;
import com.witboot.client.operationlog.dto.query.OperationLogListByParamQuery;
import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.operationlog.query.OperationLogQuerySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageResult<OperationLogVO> listByParam(OperationLogListByParamQuery operationLogListByParamQuery) {
        OperationLogQuerySpec opLogListByParamQuerySpec = OperationLogAssembler.toOperationLogListByParamQuerySpec(operationLogListByParamQuery);
        return operationlogListByParamQueryExe.execute(opLogListByParamQuerySpec);
    }

    @Override
    public int deleteByIds(String[] ids) {
        return operationlogDeleteCmdExe.execute(ids);
    }
}
