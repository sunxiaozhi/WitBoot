package com.witboot.app.operationlog.assembler;

import com.witboot.client.operationlog.dto.OperationLogAddCmd;
import com.witboot.client.operationlog.dto.data.OperationLogVO;
import com.witboot.client.operationlog.dto.query.OperationLogListByParamQuery;
import com.witboot.domain.operationlog.model.OperationLogEntity;
import com.witboot.domain.operationlog.query.OperationLogListByParamQuerySpec;

/**
 * OperationLog Application层转换器
 * 用于DTO与实体之间的互转
 *
 * @author sunxiaozhi
 */
public class OperationLogAssembler {
    public static OperationLogListByParamQuerySpec toOperationLogListByParamQuerySpec (OperationLogListByParamQuery operatorLogListByParamQuery) {
        OperationLogListByParamQuerySpec operationLogListByParamQuerySpec = new OperationLogListByParamQuerySpec();
        operationLogListByParamQuerySpec.setIp(operatorLogListByParamQuery.getIp());
        operationLogListByParamQuerySpec.setPageNo(operatorLogListByParamQuery.getPageNo());
        operationLogListByParamQuerySpec.setPageSize(operatorLogListByParamQuery.getPageSize());
        return operationLogListByParamQuerySpec;
    }

    public static OperationLogEntity toEntity(OperationLogAddCmd operationLogAddCmd) {
        OperationLogEntity operationlogEntity = new OperationLogEntity();
        operationlogEntity.setId(operationLogAddCmd.getId());
        operationlogEntity.setIp(operationLogAddCmd.getIp());
        operationlogEntity.setLocation(operationLogAddCmd.getLocation());
        operationlogEntity.setMethod(operationLogAddCmd.getMethod());
        operationlogEntity.setUri(operationLogAddCmd.getUri());
        operationlogEntity.setWasteTime(operationLogAddCmd.getWasteTime());
        operationlogEntity.setRequestParam(operationLogAddCmd.getRequestParam());
        operationlogEntity.setRequestBody(operationLogAddCmd.getRequestBody());
        operationlogEntity.setResponseResult(operationLogAddCmd.getResponseResult());

        return operationlogEntity;
    }

    /*public static OperationLogEntity toEntity(OperationLogUpdateCmd operationlogUpdateCmd) {
        OperationLogEntity operationlogEntity = new OperationLogEntity();
        operationlogEntity.setId(operationlogUpdateCmd.getId());
        operationlogEntity.setOperationLogname(operationlogUpdateCmd.getOperationLogname());
        operationlogEntity.setName(operationlogUpdateCmd.getName());
        operationlogEntity.setMobile(operationlogUpdateCmd.getPhoneNo());
        operationlogEntity.setGender(operationlogUpdateCmd.getGender());
        operationlogEntity.setBirthday(operationlogUpdateCmd.getBirthday());
        operationlogEntity.setDescription(operationlogUpdateCmd.getDescription());

        return operationlogEntity;
    }*/

    public static OperationLogVO toValueObject(OperationLogEntity operationlogEntity) {
        OperationLogVO operationlogVO = new OperationLogVO();
        operationlogVO.setId(operationlogEntity.getId());
        operationlogVO.setIp(operationlogEntity.getIp());
        operationlogVO.setLocation(operationlogEntity.getLocation());
        operationlogVO.setMethod(operationlogEntity.getMethod());
        operationlogVO.setUri(operationlogEntity.getUri());
        operationlogVO.setWasteTime(operationlogEntity.getWasteTime());
        operationlogVO.setRequestParam(operationlogEntity.getRequestParam());
        operationlogVO.setRequestBody(operationlogEntity.getRequestBody());
        operationlogVO.setResponseResult(operationlogEntity.getResponseResult());
        return operationlogVO;
    }
}
