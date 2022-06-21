package me.bigmonkey.structure.common.utils;

import java.util.regex.Pattern;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class IpUtil {
    /**
     * IP 문자열을 Scope(숫자)로 변환
     * @param ipAddress
     * @return
     */
    public static long ipToLong(String ipAddress) {
        final String[] ipAddressInArray = ipAddress.split("\\.");

        long converted = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            converted += ip * Math.pow(256, power);
        }

        return converted;
    }

    /**
     * IP Scope(숫자)를 문자열로 변환
     *
     * @param ip
     * @return
     */
    public static String longToIp(long ip) {
        return ((ip >> 24) & 0xFF) + "."
                + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 8) & 0xFF) + "."
                + (ip & 0xFF);
    }

    public static boolean isIPv4(String ipAddress) {
        return Pattern.matches("((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])([.](?!$)|$)){4}", ipAddress);
    }
}
