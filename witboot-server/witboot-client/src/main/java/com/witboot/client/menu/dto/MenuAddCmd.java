package com.witboot.client.menu.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 新增菜单请求
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuAddCmd extends Command {

    @Serial
    private static final long serialVersionUID = -5726685703640910355L;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 父级ID
     */
    private Long parentId;
}
