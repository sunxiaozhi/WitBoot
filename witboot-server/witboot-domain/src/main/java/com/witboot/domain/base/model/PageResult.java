package com.witboot.domain.base.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageResult<T> {
    private List<T> list;
    private long total;

    public PageResult(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }

    public static <T> PageResult<T> build(List<T> list, long total) {
        return new PageResult<>(list, total);
    }
}