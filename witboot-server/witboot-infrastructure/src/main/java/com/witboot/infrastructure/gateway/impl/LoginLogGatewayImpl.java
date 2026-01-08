package com.witboot.infrastructure.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.witboot.client.loginlog.dto.data.LoginLogErrorCode;
import com.witboot.domain.base.model.PageResult;
import com.witboot.domain.base.model.delete.DeleteRequestSpec;
import com.witboot.domain.loginlog.gateway.LoginLogGateway;
import com.witboot.domain.loginlog.model.LoginLogEntity;
import com.witboot.domain.loginlog.query.LoginLogQuerySpec;
import com.witboot.infrastructure.common.Constants;
import com.witboot.infrastructure.common.exception.WitBootBizException;
import com.witboot.infrastructure.convertor.LoginLogConvertor;
import com.witboot.infrastructure.gateway.impl.database.dataobject.LoginLogDO;
import com.witboot.infrastructure.gateway.impl.database.mapper.LoginLogMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * LoginLogGatewayImpl
 *
 * @author sunxiaozhi
 */
@Component("LoginLogGateway")
public class LoginLogGatewayImpl implements LoginLogGateway {
    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 根据条件查询
     *
     * @param LoginLogListByParamQuerySpec ip等
     * @return List 操作日志实体集合
     */
    @Override
    public PageResult<LoginLogEntity> findByParam(LoginLogQuerySpec LoginLogListByParamQuerySpec) {
        PageHelper.startPage(LoginLogListByParamQuerySpec.getPageNo(), LoginLogListByParamQuerySpec.getPageSize());
        List<LoginLogDO> loginLogDOList = loginLogMapper.selectByParam(LoginLogListByParamQuerySpec);
        PageInfo<LoginLogDO> pageInfo = new PageInfo<>(loginLogDOList);

        List<LoginLogEntity> loginLogEntityList = loginLogDOList.stream().map(LoginLogConvertor::toEntity).collect(Collectors.toList());
        return PageResult.build(loginLogEntityList, pageInfo.getTotal());
    }

    @Override
    public LoginLogEntity findById(Long id) {
        Optional<LoginLogDO> findById = loginLogMapper.selectById(id);
        if (findById.isEmpty()) {
            throw new WitBootBizException(LoginLogErrorCode.B_OPERATION_LOG_UNDEFINED);
        }

        LoginLogDO loginLogDO = findById.get();

        return LoginLogConvertor.toEntity(loginLogDO);
    }

    @Override
    public LoginLogEntity save(LoginLogEntity loginLogEntity) {
        // 新增
        if (Objects.isNull(loginLogEntity.getId())) {
            return addLoginLog(loginLogEntity);
        }

        return new LoginLogEntity();
    }

    /**
     * 新增操作日志
     *
     * @param loginLogEntity 操作日志实体
     * @return 操作日志实体
     */
    public LoginLogEntity addLoginLog(LoginLogEntity loginLogEntity) {
        LoginLogDO loginLogDo = LoginLogConvertor.toAddLoginLogDo(loginLogEntity);

        int insert = loginLogMapper.insert(loginLogDo);
        if (insert < Constants.ONE) {
            throw new PersistenceException("保存操作日志异常");
        }

        return LoginLogConvertor.toEntity(loginLogDo);
    }

    @Override
    public int deleteByIds(DeleteRequestSpec deleteRequestSpec) {
        return loginLogMapper.deleteByIds(deleteRequestSpec);
    }
}
