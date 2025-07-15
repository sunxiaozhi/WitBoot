package com.witboot.app.operationlog.executor.query;

import com.github.pagehelper.PageInfo;
import com.witboot.app.operationlog.assembler.OperationLogAssembler;
import com.witboot.client.base.dato.data.model.PageResult;
import com.witboot.client.operationlog.dto.data.OperationLogVO;
import com.witboot.client.operationlog.dto.query.OperationLogListByParamQuery;
import com.witboot.domain.operationlog.gateway.OperationLogGateway;
import com.witboot.domain.operationlog.model.OperationLogEntity;
import com.witboot.infrastructure.common.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OperationLogListByParamQueryExe
 *
 * @author sunxiaoizhi
 */
@Component
public class OperationLogListByParamQueryExe {

    @Autowired
    private OperationLogGateway operationlogGateway;

    public PageInfo<OperationLogVO> execute(OperationLogListByParamQuery operationlogListByParamQuery) {
        PageInfo<OperationLogEntity> operationLogPageResult =  operationlogGateway.findByParam(operationlogListByParamQuery);
        //PageInfo<OperationLogDO> pageInfo = new PageInfo<>(operationLogPageResult.getList());

        return PageUtil.convertPageInfo(operationLogPageResult, OperationLogAssembler::toValueObject);
        //return operationLogEntityList.stream().map(OperationLogAssembler::toValueObject).collect(Collectors.toList());
    }
}