package com.witboot.client.menu.dto.data;

import lombok.Data;

import java.util.Date;

/**
 * Menu VO
 *
 * @author sunxiaoizhi
 */
@Data
public class MenuVO {
    /**
     * 编码
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级id
     */
    private Long parentId;

    /**
     * 删除标识 0已删除 1正常
     */
    private String deleteFlag;

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
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
