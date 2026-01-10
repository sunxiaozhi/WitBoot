package com.witboot.infrastructure.common.utils;

import com.witboot.infrastructure.common.core.model.UserAgentInfo;
import ua_parser.Client;
import ua_parser.Parser;

public class UserAgentUtils {

    private static final Parser PARSER = new Parser();

    private UserAgentUtils() {}

    public static UserAgentInfo parse(String userAgent) {
        UserAgentInfo userAgentInfo = new UserAgentInfo();

        if (userAgent == null || userAgent.isBlank()) {
            userAgentInfo.setBrowser("Unknown");
            userAgentInfo.setOs("Unknown");
            userAgentInfo.setDevice("Unknown");
            return userAgentInfo;
        }

        Client client = PARSER.parse(userAgent);

        // 浏览器
        userAgentInfo.setBrowser(build(client.userAgent.family, client.userAgent.major));

        // 操作系统
        userAgentInfo.setOs(build(client.os.family, client.os.major));

        // 设备类型
        userAgentInfo.setDevice(parseDevice(client.device.family));

        return userAgentInfo;
    }

    private static String build(String family, String major) {
        if (family == null) {
            return "Unknown";
        }
        return major == null ? family : family + " " + major;
    }

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
