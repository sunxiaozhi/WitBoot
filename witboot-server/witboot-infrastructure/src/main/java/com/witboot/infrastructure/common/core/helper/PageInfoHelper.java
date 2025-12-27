package com.witboot.infrastructure.common.core.helper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PageInfoHelper
 *
 * @author sunxiaozhi
 */
public class PageInfoHelper {
    @FunctionalInterface
    public interface PageInfoConvertor<T, R> {
        /**
         * convert 将 T 类型对象转换为 R 类型对象
         *
         * @param t 原始类型对象
         * @return 目标类型对象
         */
        R convert(T t);
    }

    /**
     * convertPageInfo 转换 PageInfo 泛型类型
     *
     * @param sourcePageInfo 原始的 pageInfo 对象
     * @param convertor      自定义转换器
     * @param <T>            原始类型
     * @param <R>            目标类型
     * @return 转换之后的 PageInfo 对象
     */
    public static <T, R> PageInfo<R> convertPageInfo(PageInfo<T> sourcePageInfo, PageInfoConvertor<T, R> convertor) {
        Page<R> page = new Page<>(sourcePageInfo.getPageNum(), sourcePageInfo.getPageSize());
        page.setTotal(sourcePageInfo.getTotal());

        PageInfo<R> targetPageInfo = new PageInfo<>(page);
        List<R> targetList = sourcePageInfo.getList().stream().map(convertor::convert).collect(Collectors.toList());
        targetPageInfo.setList(targetList);
        return targetPageInfo;
    }

    /**
     * convertPageInfo 转换 PageInfo 泛型类型
     *
     * @param sourcePageInfo 原始的 pageInfo 对象
     * @param targetClazz    需要转换的 PageInfo 泛型类型
     * @param <T>            原始类型
     * @param <R>            目标类型
     * @return 转换之后的 PageInfo 对象
     */
    public static <T, R> PageInfo<R> convertPageInfo(PageInfo<T> sourcePageInfo, Class<R> targetClazz) {
        Page<R> page = new Page<>(sourcePageInfo.getPageNum(), sourcePageInfo.getPageSize());
        page.setTotal(sourcePageInfo.getTotal());

        PageInfo<R> targetPageInfo = new PageInfo<>(page);
        List<R> targetList = sourcePageInfo.getList().stream().map(e -> {
            try {
                R r = targetClazz.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(e, r);
                return r;
            } catch (Exception exception) {
                throw new RuntimeException("convertPageInfo cause exception", exception);
            }
        }).collect(Collectors.toList());
        targetPageInfo.setList(targetList);
        return targetPageInfo;
    }
}
