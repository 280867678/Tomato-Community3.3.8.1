package com.blankj.utilcode.util;

import android.support.annotation.NonNull;
import com.tomatolive.library.utils.DateUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class TimeUtils {
    private static final ThreadLocal<SimpleDateFormat> SDF_THREAD_LOCAL = new ThreadLocal<>();

    static {
        new String[]{"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
        new String[]{"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
    }

    private static SimpleDateFormat getDefaultFormat() {
        return getDateFormat(DateUtils.C_TIME_PATTON_DEFAULT);
    }

    @NonNull
    private static SimpleDateFormat getDateFormat(String str) {
        SimpleDateFormat simpleDateFormat = SDF_THREAD_LOCAL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(str, Locale.getDefault());
            SDF_THREAD_LOCAL.set(simpleDateFormat);
        } else {
            simpleDateFormat.applyPattern(str);
        }
        if (simpleDateFormat != null) {
            return simpleDateFormat;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.TimeUtils.getDateFormat() marked by @android.support.annotation.NonNull");
    }

    public static String millis2String(long j) {
        return millis2String(j, getDefaultFormat());
    }

    public static String millis2String(long j, @NonNull String str) {
        if (str == null) {
            throw new NullPointerException("Argument 'pattern' of type String (#1 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        return millis2String(j, getDateFormat(str));
    }

    public static String millis2String(long j, @NonNull DateFormat dateFormat) {
        if (dateFormat == null) {
            throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        return dateFormat.format(new Date(j));
    }

    public static String getFitTimeSpan(long j, long j2, int i) {
        return millis2FitTimeSpan(j - j2, i);
    }

    public static String getFitTimeSpanByNow(long j, int i) {
        return getFitTimeSpan(j, System.currentTimeMillis(), i);
    }

    public static String getFriendlyTimeSpanByNow(long j) {
        long currentTimeMillis = System.currentTimeMillis() - j;
        if (currentTimeMillis < 0) {
            return String.format("%tc", Long.valueOf(j));
        }
        if (currentTimeMillis < 1000) {
            return "刚刚";
        }
        if (currentTimeMillis < DateUtils.ONE_MINUTE_MILLIONS) {
            return String.format(Locale.getDefault(), "%d秒前", Long.valueOf(currentTimeMillis / 1000));
        }
        if (currentTimeMillis < DateUtils.ONE_HOUR_MILLIONS) {
            return String.format(Locale.getDefault(), "%d分钟前", Long.valueOf(currentTimeMillis / DateUtils.ONE_MINUTE_MILLIONS));
        }
        long weeOfToday = getWeeOfToday();
        return j >= weeOfToday ? String.format("今天%tR", Long.valueOf(j)) : j >= weeOfToday - DateUtils.ONE_DAY_MILLIONS ? String.format("昨天%tR", Long.valueOf(j)) : String.format("%tF", Long.valueOf(j));
    }

    private static long getWeeOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    private static String millis2FitTimeSpan(long j, int i) {
        if (i <= 0) {
            return null;
        }
        int min = Math.min(i, 5);
        String[] strArr = {"天", "小时", "分钟", "秒", "毫秒"};
        int i2 = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i2 == 0) {
            return 0 + strArr[min - 1];
        }
        StringBuilder sb = new StringBuilder();
        if (i2 < 0) {
            sb.append("-");
            j = -j;
        }
        int[] iArr = {86400000, 3600000, 60000, 1000, 1};
        for (int i3 = 0; i3 < min; i3++) {
            if (j >= iArr[i3]) {
                long j2 = j / iArr[i3];
                j -= iArr[i3] * j2;
                sb.append(j2);
                sb.append(strArr[i3]);
            }
        }
        return sb.toString();
    }
}
