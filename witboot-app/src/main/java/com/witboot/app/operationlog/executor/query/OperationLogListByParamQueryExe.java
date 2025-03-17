package com.witboot.app.operationlog.executor.query;

import com.witboot.app.operationlog.assembler.OperationLogAssembler;
import com.witboot.client.operationlog.dto.data.OperationLogVO;
import com.witboot.client.operationlog.dto.query.OperationLogListByParamQuery;
import com.witboot.domain.operationlog.gateway.OperationLogGateway;
import com.witboot.domain.operationlog.model.OperationLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OperationLogListByParamQueryExe
 *
 * @author sunxiaoizhi
 */
@Component
public class OperationLogListByParamQueryExe {

    @Autowired
    private OperationLogGateway operationlogGateway;

    public List<OperationLogVO> execute(OperationLogListByParamQuery operationlogListByParamQuery) {
        List<OperationLogEntity> operationLogEntityList = operationlogGateway.findByParam(operationlogListByParamQuery);

        return operationLogEntityList.stream().map(OperationLogAssembler::toValueObject).collect(Collectors.toList());
    }
}