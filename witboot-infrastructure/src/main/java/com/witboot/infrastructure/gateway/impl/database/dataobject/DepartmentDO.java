package com.witboot.infrastructure.gateway.impl.database.dataobject;

import com.witboot.infrastructure.common.api.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DepartmentDO
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentDO extends BaseDO {
    /**
     * 部门名称
     */
    private String name;

    /**
     * 父级id
     */
    private Long parentId;
}
