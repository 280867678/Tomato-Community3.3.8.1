package com.p076mh.webappStart.util;

import android.annotation.SuppressLint;
import com.tomatolive.library.utils.DateUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressLint({"SimpleDateFormat"})
/* renamed from: com.mh.webappStart.util.TimeUtils */
/* loaded from: classes3.dex */
public class TimeUtils {
    static {
        new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        new SimpleDateFormat(DateUtils.C_DATE_PATTON_DATE_CHINA_2);
        new SimpleDateFormat("yyyyMMddHHmmss");
    }

    public static String getCurrentTimeForMakeName() {
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
    }

    public static String calculatTimeHMS(long j) {
        String valueOf;
        String valueOf2;
        String valueOf3;
        long j2 = j / DateUtils.ONE_HOUR_MILLIONS;
        long j3 = j - (((j2 * 60) * 60) * 1000);
        long j4 = j3 / DateUtils.ONE_MINUTE_MILLIONS;
        long j5 = (j3 - ((j4 * 60) * 1000)) / 1000;
        if (j5 >= 60) {
            j5 %= 60;
            j4 += j5 / 60;
        }
        if (j4 >= 60) {
            j4 %= 60;
            j2 += j4 / 60;
        }
        if (j2 < 10) {
            valueOf = "0" + String.valueOf(j2);
        } else {
            valueOf = String.valueOf(j2);
        }
        if (j4 < 10) {
            valueOf2 = "0" + String.valueOf(j4);
        } else {
            valueOf2 = String.valueOf(j4);
        }
        if (j5 < 10) {
            valueOf3 = "0" + String.valueOf(j5);
        } else {
            valueOf3 = String.valueOf(j5);
        }
        if (j2 > 0) {
            return valueOf + ":" + valueOf2 + ":" + valueOf3;
        }
        return valueOf2 + ":" + valueOf3;
    }
}
