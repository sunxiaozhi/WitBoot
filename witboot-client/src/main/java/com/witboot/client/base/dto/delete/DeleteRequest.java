package com.witboot.client.base.dto.delete;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteRequest {
    @NotNull(message = "ids不能为空")
    private String[] ids;
}
