package com.witboot.common.utils;

import oshi.util.FormatUtil;

/**
 * 可读格式工具类，提供各种数据格式化为人类可读字符串的方法。
 */
public class ReadableFormatUtil {

    /**
     * 将字节数格式化为可读字符串（例如：1024 -> "1 KB"）。
     *
     * @param valueBytes 字节数
     * @return 格式化后的字符串
     */
    public static String bytes(long valueBytes) {
        return FormatUtil.formatBytes(valueBytes);
    }

    /**
     * 将每秒字节数格式化为可读字符串（例如：1024 -> "1 KB/s"）。
     *
     * @param bytesPerSecond 每秒字节数
     * @return 格式化后的字符串
     */
    public static String bytesPerSecond(double bytesPerSecond) {
        if (bytesPerSecond < 0) bytesPerSecond = 0;
        return FormatUtil.formatBytes((long) bytesPerSecond) + "/s";
    }

    /**
     * 将频率（赫兹）格式化为可读字符串（例如：1000000000 -> "1 GHz"）。
     *
     * @param frequencyHertz 频率（赫兹）
     * @return 格式化后的字符串，若频率小于等于0则返回null
     */
    public static String frequencyHertz(long frequencyHertz) {
        return frequencyHertz <= 0 ? null : FormatUtil.formatHertz(frequencyHertz);
    }

    /**
     * 将百分比值格式化为保留两位小数的字符串（例如：45.678 -> "45.68%"）。
     *
     * @param percentValue 百分比值
     * @return 格式化后的字符串
     */
    public static String percent(double percentValue) {
        return String.format("%.2f%%", percentValue);
    }

    /**
     * 将网络速率（比特每秒）格式化为可读字符串（例如：1000000 -> "1.00 Mbps"）。
     *
     * @param bitsPerSecond 比特每秒
     * @return 格式化后的字符串，若速率小于等于0则返回null
     */
    public static String networkBitRateBitsPerSecond(long bitsPerSecond) {
        if (bitsPerSecond <= 0) return null;
        double value = bitsPerSecond;
        String[] units = {"bps", "Kbps", "Mbps", "Gbps", "Tbps"};
        int index = 0;
        while (value >= 1000.0 && index < units.length - 1) {
            value /= 1000.0;
            index++;
        }
        return String.format("%.2f %s", value, units[index]);
    }

    /**
     * 将数值四舍五入到两位小数。
     *
     * @param value 原始数值
     * @return 四舍五入后的数值
     */
    public static double roundTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
