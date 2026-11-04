package com.witboot.client.department.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 新增部门请求
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentAddCmd extends Command {

    @Serial
    private static final long serialVersionUID = -5726685703640910355L;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父级 ID
     */
    private Long parentId;
}
