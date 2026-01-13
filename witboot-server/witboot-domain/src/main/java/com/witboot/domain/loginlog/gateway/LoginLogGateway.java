package com.witboot.domain.loginlog.gateway;

import com.witboot.common.core.model.PageResult;
import com.witboot.domain.base.model.delete.DeleteRequestSpec;
import com.witboot.domain.loginlog.model.LoginLogEntity;
import com.witboot.domain.loginlog.query.LoginLogQuerySpec;

/**
 * OperationLog Gateway
 *
 * @author sunxiaoizhi
 */
public interface LoginLogGateway {
    /**
     * 根据条件查询
     *
     * @param loginLogListByParamQuerySpec ip等
     * @return List 操作日志实体集合
     */
    PageResult<LoginLogEntity> findByParam(LoginLogQuerySpec loginLogListByParamQuerySpec);

    /**
     * 获取操作日志信息
     *
     * @param id 操作日志 ID
     * @return 操作日志实体
     */
    LoginLogEntity findById(Long id);
    
    /**
     * 保存操作记录
     *
     * @param loginLogEntity loginLog Domain
     */
    LoginLogEntity save(LoginLogEntity loginLogEntity);

    int deleteByIds(DeleteRequestSpec deleteRequestSpec);
}
