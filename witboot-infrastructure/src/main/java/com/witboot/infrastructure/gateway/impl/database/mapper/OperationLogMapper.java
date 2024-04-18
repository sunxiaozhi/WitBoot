package com.witboot.infrastructure.gateway.impl.database.mapper;

import com.witboot.infrastructure.gateway.impl.database.dataobject.OperationLogDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * User Mapper
 *
 * @author sunxiaoizhi
 */
@Mapper
public interface OperationLogMapper {
    /**
     * 新增
     *
     * @param operationLogDo 操作日志实体
     * @return 结果
     */
    int insert(OperationLogDo operationLogDo);
}
