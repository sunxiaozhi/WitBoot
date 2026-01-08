package com.witboot.infrastructure.gateway.impl.database.mapper;

import com.witboot.domain.base.model.delete.DeleteRequestSpec;
import com.witboot.domain.loginlog.query.LoginLogQuerySpec;
import com.witboot.infrastructure.gateway.impl.database.dataobject.LoginLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * LoginLogMapper
 *
 * @author sunxiaoizhi
 */
@Mapper
public interface LoginLogMapper {
    /**
     * 通过参数查询
     *
     * @param loginLogListByParamQuerySpec 参数
     * @return 结果
     */
    List<LoginLogDO> selectByParam(LoginLogQuerySpec loginLogListByParamQuerySpec);

    /**
     * 通过id查询
     *
     * @param id id
     * @return 结果
     */
    Optional<LoginLogDO> selectById(Long id);

    /**
     * 新增
     *
     * @param loginLogDo 操作日志实体
     * @return 结果
     */
    int insert(LoginLogDO loginLogDo);

    /**
     * 删除数据
     *
     * @param deleteRequestSpec 删除请求
     * @return 结果
     */
    int deleteByIds(DeleteRequestSpec deleteRequestSpec);
}
