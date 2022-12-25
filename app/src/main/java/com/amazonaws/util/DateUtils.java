package com.amazonaws.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: classes2.dex */
public class DateUtils {
    private static final TimeZone GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
    private static final Map<String, ThreadLocal<SimpleDateFormat>> SDF_MAP = new HashMap();

    private static ThreadLocal<SimpleDateFormat> getSimpleDateFormat(final String str) {
        ThreadLocal<SimpleDateFormat> threadLocal = SDF_MAP.get(str);
        if (threadLocal == null) {
            synchronized (SDF_MAP) {
                threadLocal = SDF_MAP.get(str);
                if (threadLocal == null) {
                    threadLocal = new ThreadLocal<SimpleDateFormat>() { // from class: com.amazonaws.util.DateUtils.1
                        /* JADX INFO: Access modifiers changed from: protected */
                        @Override // java.lang.ThreadLocal
                        public SimpleDateFormat initialValue() {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, Locale.US);
                            simpleDateFormat.setTimeZone(DateUtils.GMT_TIMEZONE);
                            simpleDateFormat.setLenient(false);
                            return simpleDateFormat;
                        }
                    };
                    SDF_MAP.put(str, threadLocal);
                }
            }
        }
        return threadLocal;
    }

    public static Date parse(String str, String str2) {
        try {
            return getSimpleDateFormat(str).get().parse(str2);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String format(String str, Date date) {
        return getSimpleDateFormat(str).get().format(date);
    }

    public static Date parseISO8601Date(String str) {
        try {
            return parse("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", str);
        } catch (IllegalArgumentException unused) {
            return parse("yyyy-MM-dd'T'HH:mm:ss'Z'", str);
        }
    }

    public static Date parseRFC822Date(String str) {
        return parse("EEE, dd MMM yyyy HH:mm:ss z", str);
    }

    public static String formatRFC822Date(Date date) {
        return format("EEE, dd MMM yyyy HH:mm:ss z", date);
    }

    public static Date parseCompressedISO8601Date(String str) {
        return parse("yyyyMMdd'T'HHmmss'Z'", str);
    }

    public static Date cloneDate(Date date) {
        if (date == null) {
            return null;
        }
        return new Date(date.getTime());
    }
}
