package com.witboot.app.loginlog.assembler;

import com.witboot.common.core.dto.delete.DeleteRequest;
import com.witboot.client.loginlog.dto.LoginLogAddCmd;
import com.witboot.client.loginlog.dto.data.LoginLogVO;
import com.witboot.client.loginlog.dto.query.LoginLogListByParamQuery;
import com.witboot.domain.base.model.delete.DeleteRequestSpec;
import com.witboot.domain.loginlog.model.LoginLogEntity;
import com.witboot.domain.loginlog.query.LoginLogQuerySpec;
import org.springframework.beans.BeanUtils;

/**
 * LoginLog Application层转换器
 * 用于DTO与实体之间的互转
 *
 * @author sunxiaozhi
 */
public class LoginLogAssembler {
    public static LoginLogQuerySpec toLoginLogListByParamQuerySpec (LoginLogListByParamQuery loginLogListByParamQuery) {
        LoginLogQuerySpec loginLogListByParamQuerySpec = new LoginLogQuerySpec();
        BeanUtils.copyProperties(loginLogListByParamQuery, loginLogListByParamQuerySpec);
        return loginLogListByParamQuerySpec;
    }

    public static LoginLogEntity toEntity(LoginLogAddCmd loginLogAddCmd) {
        LoginLogEntity loginLogEntity = new LoginLogEntity();
        BeanUtils.copyProperties(loginLogAddCmd, loginLogEntity);
        return loginLogEntity;
    }

    public static LoginLogVO toValueObject(LoginLogEntity loginLogEntity) {
        LoginLogVO loginLogVO = new LoginLogVO();
        BeanUtils.copyProperties(loginLogEntity, loginLogVO);
        return loginLogVO;
    }

    public static DeleteRequestSpec toDeleteRequestSpec(DeleteRequest deleteRequest) {
        DeleteRequestSpec deleteRequestSpec = new DeleteRequestSpec();
        deleteRequestSpec.setIds(deleteRequest.getIds());
        return deleteRequestSpec;
    }
}
