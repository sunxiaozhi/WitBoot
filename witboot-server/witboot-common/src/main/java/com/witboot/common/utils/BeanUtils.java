package com.witboot.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * BeanUtils
 *
 * @author sunxiaozhi
 */
@Component
public class BeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 设置应用程序上下文。Spring 框架会自动调用此方法。
     *
     * @param context 应用程序上下文
     * @throws BeansException 如果无法设置应用程序上下文
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /**
     * 根据 Bean 的名称获取 Bean。
     *
     * @param beanName Bean 的名称
     * @param <T>      Bean 的类型
     * @return 指定名称的 Bean
     */
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 根据 Bean 的类型获取 Bean。
     *
     * @param beanClass Bean 的类型
     * @param <T>       Bean 的类型
     * @return 指定类型的 Bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }
}
