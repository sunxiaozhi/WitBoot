package com.witboot.client.department.dto.data;

import lombok.Data;

import java.util.List;

/**
 * Department VO
 *
 * @author sunxiaoizhi
 */
@Data
public class DepartmentVO {
    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 子级
     */
    private List<DepartmentVO>  children;
}

