package com.witboot.app.operationlog.executor.query;

import com.witboot.app.operationlog.assembler.OperationLogAssembler;
import com.witboot.client.operationlog.dto.data.OperationLogVO;
import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.operationlog.gateway.OperationLogGateway;
import com.witboot.domain.operationlog.model.OperationLogEntity;
import com.witboot.domain.operationlog.query.OperationLogListByParamQuerySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * OperationLogListByParamQueryExe
 *
 * @author sunxiaoizhi
 */
@Component
public class OperationLogListByParamQueryExe {

    @Autowired
    private OperationLogGateway operationlogGateway;

    public PageResult<OperationLogVO> execute(OperationLogListByParamQuerySpec operationLogListByParamQuerySpec) {
        PageResult<OperationLogEntity> operationLogEntityPageResult = operationlogGateway.findByParam(operationLogListByParamQuerySpec);

        List<OperationLogVO> operationLogVOList = operationLogEntityPageResult.getList().stream().map(OperationLogAssembler::toValueObject).toList();

        return PageResult.build(operationLogVOList, operationLogEntityPageResult.getTotal());
    }
}