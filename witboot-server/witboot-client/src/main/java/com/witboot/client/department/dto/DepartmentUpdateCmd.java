package com.witboot.client.department.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 修改部门请求
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentUpdateCmd extends Command {

    @Serial
    private static final long serialVersionUID = 2484506238493820565L;

    /**
     * 主键 ID
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父级 ID
     */
    private Long parentId;
}
