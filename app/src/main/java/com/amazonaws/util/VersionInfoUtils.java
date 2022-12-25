package com.amazonaws.util;

import com.amazonaws.logging.LogFactory;
import com.tomatolive.library.utils.ConstantUtils;

/* loaded from: classes2.dex */
public class VersionInfoUtils {
    private static volatile String platform = "android";
    private static volatile String userAgent = null;
    private static volatile String version = "2.12.7";

    static {
        LogFactory.getLog(VersionInfoUtils.class);
    }

    public static String getVersion() {
        return version;
    }

    public static String getPlatform() {
        return platform;
    }

    public static String getUserAgent() {
        if (userAgent == null) {
            synchronized (VersionInfoUtils.class) {
                if (userAgent == null) {
                    initializeUserAgent();
                }
            }
        }
        return userAgent;
    }

    private static void initializeUserAgent() {
        userAgent = userAgent();
    }

    static String userAgent() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("aws-sdk-");
        sb.append(StringUtils.lowerCase(getPlatform()));
        sb.append("/");
        sb.append(getVersion());
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append(replaceSpaces(System.getProperty("os.name")));
        sb.append("/");
        sb.append(replaceSpaces(System.getProperty("os.version")));
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append(replaceSpaces(System.getProperty("java.vm.name")));
        sb.append("/");
        sb.append(replaceSpaces(System.getProperty("java.vm.version")));
        sb.append("/");
        sb.append(replaceSpaces(System.getProperty("java.version")));
        String property = System.getProperty("user.language");
        String property2 = System.getProperty("user.region");
        if (property != null && property2 != null) {
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(replaceSpaces(property));
            sb.append("_");
            sb.append(replaceSpaces(property2));
        }
        return sb.toString();
    }

    private static String replaceSpaces(String str) {
        return str.replace(' ', '_');
    }
}
