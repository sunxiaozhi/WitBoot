package com.witboot.client.menu.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDate;

/**
 * 新增用户请求
 *
 * @author sunxiaoizhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuUpdateCmd extends Command {

    @Serial
    private static final long serialVersionUID = 2484506238493820565L;
}
