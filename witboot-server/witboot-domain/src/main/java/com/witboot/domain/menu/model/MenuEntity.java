package com.witboot.domain.menu.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Department Domain
 *
 * @author sunxiaoizhi
 */
@Data
public class MenuEntity implements Serializable {
    /**
     * 编码
     */
    @NotNull(message = "[编码]不能为空")
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "[名称]不能为空")
    @Size(max = 100, message = "编码长度不能超过100")
    private String name;

    /**
     * 上级id
     */
    @NotNull(message = "[上级id]不能为空")
    private Long parentId;

    /**
     * 删除标识 0已删除 1正常
     */
    @NotNull(message = "[删除标识 0已删除 1正常]不能为空")
    private String deleteFlag;

    /**
     * 创建人
     */
    @NotBlank(message = "[创建人]不能为空")
    @Size(max = 255, message = "编码长度不能超过255")
    private String creator;

    /**
     * 修改人
     */
    @NotBlank(message = "[修改人]不能为空")
    @Size(max = 255, message = "编码长度不能超过255")
    private String modifier;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
