package com.witboot.infrastructure.gateway.impl.database.dataobject;

import com.witboot.infrastructure.common.api.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserDO
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDO extends BaseDO {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 姓名（较常用，故放在用户主表）
     */
    private String name;

    /**
     * 附加信息主键ID
     */
    private Long infoId;
}
