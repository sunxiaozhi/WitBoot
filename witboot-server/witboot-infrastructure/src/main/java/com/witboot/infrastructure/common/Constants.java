package com.witboot.infrastructure.common;

/**
 * 常量
 *
 * @author sunxiaozhi
 */
public class Constants {
    public final static String LOGIN_USER_KEY = "loginUser:username:";

    public final static int ONE = 1;

    /**
     * 已删除数据标识
     */
    public final static String DELETED_FLAG = "0";

    /**
     * 正常数据标识
     */
    public final static String NOT_DELETED_FLAG = "1";

    public static class DateTime {
        /** 默认日期时间格式 */
        public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        /** 默认日期格式 */
        public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
        /** 默认时间格式 */
        public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    }
}
