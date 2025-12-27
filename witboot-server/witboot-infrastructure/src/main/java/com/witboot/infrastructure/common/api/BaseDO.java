package com.witboot.infrastructure.common.api;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * BaseDO
 *
 * @author sunxiaoizhi
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class BaseDO {
    /**
     * 编码
     */
    private Long id;

    /**
     * 数据标识
     */
    private Integer deleteFlag;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}