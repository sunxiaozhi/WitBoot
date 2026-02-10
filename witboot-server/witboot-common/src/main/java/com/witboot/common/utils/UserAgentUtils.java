package com.witboot.common.utils;

import com.witboot.common.core.model.UserAgentInfo;
import ua_parser.Client;
import ua_parser.Parser;

/**
 * 工具类，用于解析 User-Agent 字符串，提取浏览器、操作系统和设备类型信息。
 */
public class UserAgentUtils {

    // 使用静态 Parser 实例以提高性能
    private static final Parser PARSER = new Parser();

    // 私有构造函数，防止实例化
    private UserAgentUtils() {}

    /**
     * 解析 User-Agent 字符串，返回包含浏览器、操作系统和设备类型的 UserAgentInfo 对象。
     *
     * @param userAgent User-Agent 字符串
     * @return 包含解析结果的 UserAgentInfo 对象
     */
    public static UserAgentInfo parse(String userAgent) {
        UserAgentInfo userAgentInfo = new UserAgentInfo();

        // 如果 User-Agent 为空或空白，则设置默认值为 "Unknown"
        if (userAgent == null || userAgent.isBlank()) {
            userAgentInfo.setBrowser("Unknown");
            userAgentInfo.setOs("Unknown");
            userAgentInfo.setDevice("Unknown");
            return userAgentInfo;
        }

        // 使用 Parser 解析 User-Agent 字符串
        Client client = PARSER.parse(userAgent);

        // 设置浏览器信息
        userAgentInfo.setBrowser(build(client.userAgent.family, client.userAgent.major));

        // 设置操作系统信息
        userAgentInfo.setOs(build(client.os.family, client.os.major));

        // 设置设备类型信息
        userAgentInfo.setDevice(parseDevice(client.device.family));

        return userAgentInfo;
    }

    /**
     * 构建浏览器或操作系统的名称和版本号字符串。
     *
     * @param family 浏览器或操作系统的家族名称
     * @param major  主版本号
     * @return 构建后的字符串，格式为 "family major" 或 "family"
     */
    private static String build(String family, String major) {
        if (family == null) {
            return "Unknown";
        }
        return major == null ? family : family + " " + major;
    }

    /**
     * 根据设备家族名称判断设备类型。
     *
     * @param deviceFamily 设备家族名称
     * @return 设备类型："Mobile"、"Tablet" 或 "PC"
     */
    private static String parseDevice(String deviceFamily) {
        if (deviceFamily == null) {
            return "PC";
        }
        String df = deviceFamily.toLowerCase();
        if (df.contains("iphone") || df.contains("android")) {
            return "Mobile";
        }
        if (df.contains("ipad") || df.contains("tablet")) {
            return "Tablet";
        }
        return "PC";
    }
}