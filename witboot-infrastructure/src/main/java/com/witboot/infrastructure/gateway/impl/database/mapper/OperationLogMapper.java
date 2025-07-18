package com.witboot.infrastructure.gateway.impl.database.mapper;

import com.witboot.domain.operationlog.query.OperationLogQuerySpec;
import com.witboot.infrastructure.gateway.impl.database.dataobject.OperationLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * User Mapper
 *
 * @author sunxiaoizhi
 */
@Mapper
public interface OperationLogMapper {
    /**
     * 通过参数查询
     *
     * @param operationLogListByParamQuerySpec 参数
     * @return 结果
     */
    List<OperationLogDO> selectByParam(OperationLogQuerySpec operationLogListByParamQuerySpec);

    /**
     * 通过id查询
     *
     * @param id id
     * @return 结果
     */
    Optional<OperationLogDO> selectById(Long id);

    /**
     * 新增
     *
     * @param operationLogDo 操作日志实体
     * @return 结果
     */
    int insert(OperationLogDO operationLogDo);
}
