package com.luck.picture.lib.tools;

import java.text.SimpleDateFormat;

/* loaded from: classes3.dex */
public class DateUtils {
    private static SimpleDateFormat msFormat = new SimpleDateFormat(com.tomatolive.library.utils.DateUtils.C_DATE_PATTON_DATE_CHINA_5);

    public static String timeParse(long j) {
        if (j > 1000) {
            return timeParseMinute(j);
        }
        long j2 = j / com.tomatolive.library.utils.DateUtils.ONE_MINUTE_MILLIONS;
        long round = Math.round(((float) (j % com.tomatolive.library.utils.DateUtils.ONE_MINUTE_MILLIONS)) / 1000.0f);
        String str = "";
        if (j2 < 10) {
            str = str + "0";
        }
        String str2 = str + j2 + ":";
        if (round < 10) {
            str2 = str2 + "0";
        }
        return str2 + round;
    }

    public static String timeParseMinute(long j) {
        try {
            return msFormat.format(Long.valueOf(j));
        } catch (Exception e) {
            e.printStackTrace();
            return "0:00";
        }
    }

    public static int dateDiffer(long j) {
        try {
            return (int) Math.abs(Long.parseLong(String.valueOf(System.currentTimeMillis()).substring(0, 10)) - j);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
