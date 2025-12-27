package com.witboot.infrastructure.common.utils;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageUtil {
    /**
     * 将 PageInfo<T> 转为 PageInfo<R>，转换 list 元素但保留分页元信息。
     */
    public static <T, R> PageInfo<R> convertPageInfo(PageInfo<T> sourcePageInfo, Function<T, R> mapper) {
        List<R> mappedList = sourcePageInfo.getList().stream()
                .map(mapper)
                .collect(Collectors.toList());

        // 新建 PageInfo<R> 并手动设置分页信息
        PageInfo<R> targetPageInfo = new PageInfo<>();
        targetPageInfo.setList(mappedList);
        targetPageInfo.setTotal(sourcePageInfo.getTotal());
        targetPageInfo.setPageNum(sourcePageInfo.getPageNum());
        targetPageInfo.setPageSize(sourcePageInfo.getPageSize());
        targetPageInfo.setPages(sourcePageInfo.getPages());
        targetPageInfo.setSize(sourcePageInfo.getSize());
        targetPageInfo.setStartRow(sourcePageInfo.getStartRow());
        targetPageInfo.setEndRow(sourcePageInfo.getEndRow());
        targetPageInfo.setPrePage(sourcePageInfo.getPrePage());
        targetPageInfo.setNextPage(sourcePageInfo.getNextPage());
        targetPageInfo.setIsFirstPage(sourcePageInfo.isIsFirstPage());
        targetPageInfo.setIsLastPage(sourcePageInfo.isIsLastPage());
        targetPageInfo.setHasPreviousPage(sourcePageInfo.isHasPreviousPage());
        targetPageInfo.setHasNextPage(sourcePageInfo.isHasNextPage());
        targetPageInfo.setNavigatePages(sourcePageInfo.getNavigatePages());
        targetPageInfo.setNavigatepageNums(sourcePageInfo.getNavigatepageNums());
        targetPageInfo.setNavigateFirstPage(sourcePageInfo.getNavigateFirstPage());
        targetPageInfo.setNavigateLastPage(sourcePageInfo.getNavigateLastPage());

        return targetPageInfo;
    }
}
