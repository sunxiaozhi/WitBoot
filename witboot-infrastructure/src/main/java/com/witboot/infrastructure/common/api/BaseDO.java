package com.witboot.infrastructure.common.api;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * BaseEntity
 *
 * @author sunxiaoizhi
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class BaseDO {

    private Long id;

    private String creator;

    private LocalDateTime gmtCreate;

    private String modifier;

    private LocalDateTime gmtModified;

    private Boolean deleteFlag;

}