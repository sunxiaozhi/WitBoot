package com.witboot.infrastructure.gateway.impl.database.dataobject;

import lombok.Data;

import java.time.LocalDate;

/**
 * UserInfoDO
 *
 * @author sunxiaoizhi
 */
@Data
public class UserInfoDO {

    private Long id;

    private String phoneNo;

    private Integer gender;

    private LocalDate birthday;

    private String description;

}
