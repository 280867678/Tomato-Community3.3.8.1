package com.sensorsdata.analytics.android.sdk.util;

import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DateFormatUtils {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    private static Map<String, ThreadLocal<SimpleDateFormat>> formatMaps = new HashMap();

    private static synchronized SimpleDateFormat getDateFormat(final String str, final Locale locale) {
        SimpleDateFormat simpleDateFormat;
        synchronized (DateFormatUtils.class) {
            ThreadLocal<SimpleDateFormat> threadLocal = formatMaps.get(str);
            if (threadLocal == null) {
                threadLocal = new ThreadLocal<SimpleDateFormat>() { // from class: com.sensorsdata.analytics.android.sdk.util.DateFormatUtils.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // java.lang.ThreadLocal
                    public SimpleDateFormat initialValue() {
                        SimpleDateFormat simpleDateFormat2;
                        try {
                            if (locale == null) {
                                simpleDateFormat2 = new SimpleDateFormat(str, Locale.getDefault());
                            } else {
                                simpleDateFormat2 = new SimpleDateFormat(str, locale);
                            }
                            return simpleDateFormat2;
                        } catch (Exception e) {
                            SALog.printStackTrace(e);
                            return null;
                        }
                    }
                };
                if (threadLocal.get() != null) {
                    formatMaps.put(str, threadLocal);
                }
            }
            simpleDateFormat = threadLocal.get();
        }
        return simpleDateFormat;
    }

    public static String formatTime(long j, String str) {
        if (TextUtils.isEmpty(str)) {
            str = YYYY_MM_DD_HH_MM_SS_SSS;
        }
        SimpleDateFormat dateFormat = getDateFormat(str, Locale.getDefault());
        if (dateFormat == null) {
            return "";
        }
        try {
            return dateFormat.format(Long.valueOf(j));
        } catch (IllegalArgumentException e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public static String formatDate(Date date) {
        return formatDate(date, YYYY_MM_DD_HH_MM_SS_SSS);
    }

    public static String formatDate(Date date, String str) {
        return formatDate(date, str, Locale.getDefault());
    }

    public static String formatDate(Date date, Locale locale) {
        return formatDate(date, YYYY_MM_DD_HH_MM_SS_SSS, locale);
    }

    public static String formatDate(Date date, String str, Locale locale) {
        if (TextUtils.isEmpty(str)) {
            str = YYYY_MM_DD_HH_MM_SS_SSS;
        }
        SimpleDateFormat dateFormat = getDateFormat(str, locale);
        if (dateFormat == null) {
            return "";
        }
        try {
            return dateFormat.format(date);
        } catch (IllegalArgumentException e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public static boolean isDateValid(Date date) {
        try {
            return date.after(getDateFormat(YYYY_MM_DD_HH_MM_SS_SSS, Locale.getDefault()).parse("2015-05-15 10:24:00.000"));
        } catch (ParseException e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public static boolean isDateValid(long j) {
        try {
            Date parse = getDateFormat(YYYY_MM_DD_HH_MM_SS_SSS, Locale.getDefault()).parse("2015-05-15 10:24:00.000");
            if (parse == null) {
                return false;
            }
            return parse.getTime() < j;
        } catch (ParseException e) {
            SALog.printStackTrace(e);
            return false;
        }
    }

    public static JSONObject formatDate(JSONObject jSONObject) {
        if (jSONObject == null) {
            return new JSONObject();
        }
        try {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                Object obj = jSONObject.get(next);
                if (obj instanceof Date) {
                    jSONObject.put(next, formatDate((Date) obj, Locale.CHINA));
                }
            }
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
        return jSONObject;
    }
}
