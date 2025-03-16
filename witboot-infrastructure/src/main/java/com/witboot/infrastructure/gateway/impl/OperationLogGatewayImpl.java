package com.witboot.infrastructure.gateway.impl;

import com.github.pagehelper.PageInfo;
import com.witboot.client.operationlog.dto.data.OperationLogErrorCode;
import com.witboot.client.operationlog.dto.query.OperationLogListByParamQuery;
import com.witboot.domain.operationlog.gateway.OperationLogGateway;
import com.witboot.domain.operationlog.model.OperationLogEntity;
import com.witboot.infrastructure.common.Constants;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import com.witboot.infrastructure.convertor.OperationLogConvertor;
import com.witboot.infrastructure.gateway.impl.database.dataobject.OperationLogDO;
import com.witboot.infrastructure.gateway.impl.database.mapper.OperationLogMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * OperationLogGatewayImpl
 *
 * @author sunxiaozhi
 */
@Component("OperationLogGateway")
public class OperationLogGatewayImpl implements OperationLogGateway {
    @Autowired
    private OperationLogMapper operationLogMapper;

    /**
     * 根据条件查询
     *
     * @param operationLogListByParamQuery ip等
     * @return List 操作日志实体集合
     */
    @Override
    public List<OperationLogEntity> findByParam(OperationLogListByParamQuery operationLogListByParamQuery){
        List<OperationLogEntity> operationLogEntityList = new ArrayList<>();

        List<OperationLogDO> operationLogDOList = operationLogMapper.selectByParam(operationLogListByParamQuery);

        new PageInfo<>(operationLogDOList).getTotal();
        operationLogDOList.forEach(operationLogDO -> {
            operationLogEntityList.add(OperationLogConvertor.toEntity(operationLogDO));
        });
        return operationLogEntityList;
    }

    @Override
    public OperationLogEntity findById(Long id) {
        Optional<OperationLogDO> findById = operationLogMapper.selectById(id);
        if (findById.isEmpty()) {
            throw new WitBootBizException(OperationLogErrorCode.B_OPERATION_LOG_UNDEFINED);
        }

        OperationLogDO operationlogDO = findById.get();

        return OperationLogConvertor.toEntity(operationlogDO);
    }

    @Override
    public OperationLogEntity save(OperationLogEntity operationLogEntity) {
        // 新增
        if (Objects.isNull(operationLogEntity.getId())) {
            return addOperationLog(operationLogEntity);
        }

        return new OperationLogEntity();
    }

    /**
     * 新增操作日志
     *
     * @param operationLogEntity 操作日志实体
     * @return 操作日志实体
     */
    public OperationLogEntity addOperationLog(OperationLogEntity operationLogEntity) {
        OperationLogDO operationLogDo = OperationLogConvertor.toAddOperationLogDo(operationLogEntity);

        int insert = operationLogMapper.insert(operationLogDo);
        if (insert < Constants.ONE) {
            throw new PersistenceException("保存操作日志异常");
        }

        return OperationLogConvertor.toEntity(operationLogDo);
    }
}
