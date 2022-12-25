package com.one.tomato.mvp.base.okhttp.interceptor.logging;

import android.text.TextUtils;
import com.one.tomato.mvp.base.okhttp.interceptor.logging.LoggingInterceptor;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
class Printer {
    private static final String[] OMITTED_RESPONSE;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String DOUBLE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR;

    static {
        String str = LINE_SEPARATOR;
        OMITTED_RESPONSE = new String[]{str, "Omitted response body"};
        String[] strArr = {str, "Omitted request body"};
    }

    private static synchronized boolean isEmpty(String str) {
        boolean z;
        synchronized (Printer.class) {
            if (!TextUtils.isEmpty(str) && !"\n".equals(str) && !"\t".equals(str)) {
                if (!TextUtils.isEmpty(str.trim())) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized void printJsonResponse(LoggingInterceptor.Builder builder, long j, boolean z, int i, String str, String str2, List<String> list) {
        synchronized (Printer.class) {
            String str3 = LINE_SEPARATOR + "Body:" + LINE_SEPARATOR + getJsonString(str2);
            String tag = builder.getTag(false);
            if (builder.getLogger() == null) {
                C2543I.log(builder.getType(), tag, "┌────── Response ───────────────────────────────────────────────────────────────────────");
            }
            logLines(builder.getType(), tag, getResponse(str, j, i, z, builder.getLevel(), list), builder.getLogger(), true);
            if (builder.getLevel() == Level.BASIC || builder.getLevel() == Level.BODY) {
                logLines(builder.getType(), tag, str3.split(LINE_SEPARATOR), builder.getLogger(), true);
            }
            if (builder.getLogger() == null) {
                C2543I.log(builder.getType(), tag, "└───────────────────────────────────────────────────────────────────────────────────────");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized void printFileResponse(LoggingInterceptor.Builder builder, long j, boolean z, int i, String str, List<String> list) {
        synchronized (Printer.class) {
            String tag = builder.getTag(false);
            if (builder.getLogger() == null) {
                C2543I.log(builder.getType(), tag, "┌────── Response ───────────────────────────────────────────────────────────────────────");
            }
            logLines(builder.getType(), tag, getResponse(str, j, i, z, builder.getLevel(), list), builder.getLogger(), true);
            logLines(builder.getType(), tag, OMITTED_RESPONSE, builder.getLogger(), true);
            if (builder.getLogger() == null) {
                C2543I.log(builder.getType(), tag, "└───────────────────────────────────────────────────────────────────────────────────────");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001e A[Catch: all -> 0x00a2, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0007, B:10:0x000f, B:12:0x001e, B:13:0x0034, B:16:0x0093, B:22:0x0074), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static synchronized String[] getResponse(String str, long j, int i, boolean z, Level level, List<String> list) {
        boolean z2;
        String slashSegments;
        String str2;
        String str3;
        String[] split;
        synchronized (Printer.class) {
            if (level != Level.HEADERS && level != Level.BASIC) {
                z2 = false;
                slashSegments = slashSegments(list);
                StringBuilder sb = new StringBuilder();
                if (TextUtils.isEmpty(slashSegments)) {
                    str2 = slashSegments + " - ";
                } else {
                    str2 = "";
                }
                sb.append(str2);
                sb.append("is success : ");
                sb.append(z);
                sb.append(" - ");
                sb.append("Received in: ");
                sb.append(j);
                sb.append("ms");
                sb.append(DOUBLE_SEPARATOR);
                sb.append("Status Code: ");
                sb.append(i);
                sb.append(DOUBLE_SEPARATOR);
                if (!isEmpty(str)) {
                    str3 = "";
                } else if (z2) {
                    str3 = "Headers:" + LINE_SEPARATOR + dotHeaders(str);
                } else {
                    str3 = "";
                }
                sb.append(str3);
                split = sb.toString().split(LINE_SEPARATOR);
            }
            z2 = true;
            slashSegments = slashSegments(list);
            StringBuilder sb2 = new StringBuilder();
            if (TextUtils.isEmpty(slashSegments)) {
            }
            sb2.append(str2);
            sb2.append("is success : ");
            sb2.append(z);
            sb2.append(" - ");
            sb2.append("Received in: ");
            sb2.append(j);
            sb2.append("ms");
            sb2.append(DOUBLE_SEPARATOR);
            sb2.append("Status Code: ");
            sb2.append(i);
            sb2.append(DOUBLE_SEPARATOR);
            if (!isEmpty(str)) {
            }
            sb2.append(str3);
            split = sb2.toString().split(LINE_SEPARATOR);
        }
        return split;
    }

    private static synchronized String slashSegments(List<String> list) {
        String sb;
        synchronized (Printer.class) {
            StringBuilder sb2 = new StringBuilder();
            for (String str : list) {
                sb2.append("/");
                sb2.append(str);
            }
            sb = sb2.toString();
        }
        return sb;
    }

    private static synchronized String dotHeaders(String str) {
        String sb;
        String str2;
        synchronized (Printer.class) {
            String[] split = str.split(LINE_SEPARATOR);
            StringBuilder sb2 = new StringBuilder();
            int i = 0;
            if (split.length > 1) {
                while (i < split.length) {
                    if (i == 0) {
                        str2 = "┌ ";
                    } else {
                        str2 = i == split.length - 1 ? "└ " : "├ ";
                    }
                    sb2.append(str2);
                    sb2.append(split[i]);
                    sb2.append("\n");
                    i++;
                }
            } else {
                int length = split.length;
                while (i < length) {
                    String str3 = split[i];
                    sb2.append("─ ");
                    sb2.append(str3);
                    sb2.append("\n");
                    i++;
                }
            }
            sb = sb2.toString();
        }
        return sb;
    }

    private static synchronized void logLines(int i, String str, String[] strArr, Logger logger, boolean z) {
        synchronized (Printer.class) {
            for (String str2 : strArr) {
                int length = str2.length();
                int i2 = z ? 110 : length;
                int i3 = 0;
                while (i3 <= length / i2) {
                    int i4 = i3 * i2;
                    i3++;
                    int i5 = i3 * i2;
                    if (i5 > str2.length()) {
                        i5 = str2.length();
                    }
                    if (logger == null) {
                        C2543I.log(i, str, "│ " + str2.substring(i4, i5));
                    } else {
                        logger.log(i, str, str2.substring(i4, i5));
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized String getJsonString(String str) {
        synchronized (Printer.class) {
            try {
                if (str.startsWith("{")) {
                    str = new JSONObject(str).toString(3);
                } else if (str.startsWith("[")) {
                    str = new JSONArray(str).toString(3);
                }
            } catch (JSONException unused) {
            }
        }
        return str;
    }
}
