package com.witboot.domain.base.model.delete;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteRequestSpec {
    @NotNull(message = "ids不能为空")
    private String[] ids;
}
