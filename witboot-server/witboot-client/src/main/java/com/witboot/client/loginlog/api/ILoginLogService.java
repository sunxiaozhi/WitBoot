package com.witboot.client.loginlog.api;

import com.witboot.common.core.dto.delete.DeleteRequest;
import com.witboot.client.loginlog.dto.LoginLogAddCmd;
import com.witboot.client.loginlog.dto.data.LoginLogVO;
import com.witboot.client.loginlog.dto.query.LoginLogListByParamQuery;
import com.witboot.common.core.model.PageResult;

/**
 * 登录日志相关
 *
 * @author sunxiaoizhi
 */
public interface ILoginLogService {
    /**
     * 根据登录日志名称查询
     *
     * @param operationLogListByParamQuery 登录日志查询请求
     * @return MultiResponse
     */
    PageResult<LoginLogVO> listByParam(LoginLogListByParamQuery operationLogListByParamQuery);

    /**
     * 新增登录日志
     *
     * @param operationLogAddCmd 登录日志新增请求
     * @return Response
     */
    LoginLogVO add(LoginLogAddCmd operationLogAddCmd);

    /**
     * 根据id查询登录日志
     *
     * @param id id
     * @return Response
     */
    LoginLogVO getLoginLogInfo(Long id);

    /**
     * 登录日志删除
     *
     * @param deleteRequest 删除请求
     * @return 删除数量
     */
    int deleteByIds(DeleteRequest deleteRequest);
}
