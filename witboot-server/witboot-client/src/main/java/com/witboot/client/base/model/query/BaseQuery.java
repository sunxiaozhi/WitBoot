package com.witboot.client.base.model.query;

import lombok.Getter;
import lombok.Setter;

import javax.management.Query;

@Getter
@Setter
public class BaseQuery extends Query {
    /**
     * 默认第一页
     */
    private int pageNo = 1;

    /**
     * 默认每页10条数据
     */
    private int pageSize = 10;
}
