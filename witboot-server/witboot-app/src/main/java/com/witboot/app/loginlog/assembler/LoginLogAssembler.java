package com.witboot.app.loginlog.assembler;

import com.witboot.app.user.assembler.UserAssembler;
import com.witboot.client.base.dto.delete.DeleteRequest;
import com.witboot.client.loginlog.dto.LoginLogAddCmd;
import com.witboot.client.loginlog.dto.data.LoginLogVO;
import com.witboot.client.loginlog.dto.query.LoginLogListByParamQuery;
import com.witboot.domain.base.model.delete.DeleteRequestSpec;
import com.witboot.domain.loginlog.model.LoginLogEntity;
import com.witboot.domain.loginlog.query.LoginLogQuerySpec;

/**
 * LoginLog Application层转换器
 * 用于DTO与实体之间的互转
 *
 * @author sunxiaozhi
 */
public class LoginLogAssembler {
    public static LoginLogQuerySpec toLoginLogListByParamQuerySpec (LoginLogListByParamQuery operatorLogListByParamQuery) {
        LoginLogQuerySpec loginLogListByParamQuerySpec = new LoginLogQuerySpec();
        loginLogListByParamQuerySpec.setSearchKeyword(operatorLogListByParamQuery.getSearchKeyword());
        loginLogListByParamQuerySpec.setMethod(operatorLogListByParamQuery.getMethod());
        loginLogListByParamQuerySpec.setPageNo(operatorLogListByParamQuery.getPageNo());
        loginLogListByParamQuerySpec.setPageSize(operatorLogListByParamQuery.getPageSize());
        return loginLogListByParamQuerySpec;
    }

    public static LoginLogEntity toEntity(LoginLogAddCmd loginLogAddCmd) {
        LoginLogEntity loginLogEntity = new LoginLogEntity();
        loginLogEntity.setIp(loginLogAddCmd.getIp());
        loginLogEntity.setUserId(loginLogAddCmd.getUserId());
        loginLogEntity.setUserName(loginLogAddCmd.getUserName());
        loginLogEntity.setLocation(loginLogAddCmd.getLocation());
        loginLogEntity.setUserAgent(loginLogAddCmd.getUserAgent());
        loginLogEntity.setDeviceType(loginLogAddCmd.getDeviceType());
        loginLogEntity.setBrowser(loginLogAddCmd.getBrowser());
        loginLogEntity.setLoginTime(loginLogAddCmd.getLoginTime());

        return loginLogEntity;
    }

    /*public static LoginLogEntity toEntity(LoginLogUpdateCmd loginLogUpdateCmd) {
        LoginLogEntity loginLogEntity = new LoginLogEntity();
        loginLogEntity.setId(loginLogUpdateCmd.getId());
        loginLogEntity.setLoginLogname(loginLogUpdateCmd.getLoginLogname());
        loginLogEntity.setName(loginLogUpdateCmd.getName());
        loginLogEntity.setMobile(loginLogUpdateCmd.getPhoneNo());
        loginLogEntity.setGender(loginLogUpdateCmd.getGender());
        loginLogEntity.setBirthday(loginLogUpdateCmd.getBirthday());
        loginLogEntity.setDescription(loginLogUpdateCmd.getDescription());

        return loginLogEntity;
    }*/

    public static LoginLogVO toValueObject(LoginLogEntity loginLogEntity) {
        LoginLogVO loginLogVO = new LoginLogVO();
        loginLogVO.setIp(loginLogEntity.getIp());
        loginLogVO.setUserId(loginLogEntity.getUserId());
        loginLogVO.setUserName(loginLogEntity.getUserName());
        loginLogVO.setLocation(loginLogEntity.getLocation());
        loginLogVO.setUserAgent(loginLogEntity.getUserAgent());
        loginLogVO.setDeviceType(loginLogEntity.getDeviceType());
        loginLogVO.setBrowser(loginLogEntity.getBrowser());
        loginLogVO.setLoginTime(loginLogEntity.getLoginTime());
        return loginLogVO;
    }

    public static DeleteRequestSpec toDeleteRequestSpec(DeleteRequest deleteRequest) {
        return UserAssembler.toDeleteRequestSpec(deleteRequest);
    }
}
