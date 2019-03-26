package com.jinzi8.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 转换操作工具类
 */
public class CastUtil {
    public static String castString(Object object) {
        return CastUtil.castString(object, "");
    }

    public static String castString(Object object, String defaultValue) {
        return object != null ? String.valueOf(object) : defaultValue;
    }

    public static double castDouble(Object object) {
        return CastUtil.castDouble(object, 0);
    }

    public static double castDouble(Object object, int defaultValue) {
        double doubleValue = defaultValue;
        if (object != null) {
            String strVal = castString(object);
            if (StringUtils.isNotEmpty(strVal)) {
                try {
                    doubleValue = Double.parseDouble(strVal);
                } catch (Exception e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    public static long castLong(Object object) {
        return CastUtil.castLong(object, 0);
    }

    public static long castLong(Object object, int defaultValue) {
        long longValue = defaultValue;
        if (object != null) {
            String strValue = castString(object);
            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (Exception e) {
                }
            }
        }
        return longValue;
    }

    public static int castInt(Object object) {
        return CastUtil.castInt(object, 0);
    }

    public static int castInt(Object object, int defaultValue) {
        int intValue = defaultValue;
        if (object != null) {
            String strValue = castString(object);
            if (StringUtils.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (Exception e) {
                }
            }
        }
        return intValue;
    }

    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj == null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;

    }
}
