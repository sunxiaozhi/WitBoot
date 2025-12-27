package com.witboot.infrastructure.gateway.impl.database.dataobject;

import com.witboot.infrastructure.common.api.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * MenuDO
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuDO extends BaseDO {
    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父级id
     */
    private Long parentId;
}
