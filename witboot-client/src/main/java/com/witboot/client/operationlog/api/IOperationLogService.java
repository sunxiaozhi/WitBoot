package com.witboot.client.operationlog.api;

import com.witboot.client.operationlog.dto.OperationLogAddCmd;
import com.witboot.client.operationlog.dto.data.OperationLogVO;
import com.witboot.client.operationlog.dto.query.OperationLogListByParamQuery;

import java.util.List;

/**
 * 操作日志相关
 *
 * @author sunxiaoizhi
 */
public interface IOperationLogService {
    /**
     * 根据操作日志名称查询
     *
     * @param operationLogListByParamQuery 操作日志查询请求
     * @return MultiResponse
     */
    List<OperationLogVO> listByParam(OperationLogListByParamQuery operationLogListByParamQuery);

    /**
     * 新增操作日志
     *
     * @param operationLogAddCmd 操作日志新增请求
     * @return Response
     */
    OperationLogVO add(OperationLogAddCmd operationLogAddCmd);

    /**
     * 根据id查询操作日志
     *
     * @param id id
     * @return Response
     */
    public OperationLogVO getOperationLogInfo(Long id);

    /**
     * 操作记录删除
     *
     * @param ids ids
     * @return 删除数量
     */
    public int deleteByIds(String[] ids);
}
