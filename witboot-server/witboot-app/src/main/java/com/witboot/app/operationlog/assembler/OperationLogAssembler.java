package com.witboot.app.operationlog.assembler;

import com.witboot.common.core.dto.delete.DeleteRequest;
import com.witboot.client.operationlog.dto.OperationLogAddCmd;
import com.witboot.client.operationlog.dto.data.OperationLogVO;
import com.witboot.client.operationlog.dto.query.OperationLogListByParamQuery;
import com.witboot.domain.base.model.delete.DeleteRequestSpec;
import com.witboot.domain.operationlog.model.OperationLogEntity;
import com.witboot.domain.operationlog.query.OperationLogQuerySpec;
import org.springframework.beans.BeanUtils;

/**
 * OperationLog Application层转换器
 * 用于DTO与实体之间的互转
 *
 * @author sunxiaozhi
 */
public class OperationLogAssembler {
    public static OperationLogQuerySpec toOperationLogListByParamQuerySpec (OperationLogListByParamQuery operatorLogListByParamQuery) {
        OperationLogQuerySpec operationLogListByParamQuerySpec = new OperationLogQuerySpec();
        BeanUtils.copyProperties(operatorLogListByParamQuery, operationLogListByParamQuerySpec);
        return operationLogListByParamQuerySpec;
    }

    public static OperationLogEntity toEntity(OperationLogAddCmd operationLogAddCmd) {
        OperationLogEntity operationlogEntity = new OperationLogEntity();
        BeanUtils.copyProperties(operationLogAddCmd, operationlogEntity);
        return operationlogEntity;
    }

    public static OperationLogVO toValueObject(OperationLogEntity operationlogEntity) {
        OperationLogVO operationlogVO = new OperationLogVO();
        BeanUtils.copyProperties(operationlogEntity, operationlogVO);
        return operationlogVO;
    }

    public static DeleteRequestSpec toDeleteRequestSpec(DeleteRequest deleteRequest) {
        DeleteRequestSpec deleteRequestSpec = new DeleteRequestSpec();
        deleteRequestSpec.setIds(deleteRequest.getIds());
        return deleteRequestSpec;
    }
}
