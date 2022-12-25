package com.youdao.sdk.app;

import android.content.Context;
import android.support.p002v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.LogConstants;
import com.youdao.sdk.app.other.C5156a;
import com.youdao.sdk.app.other.C5160e;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class Stats {
    private static C5156a AGENT;
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat(DateUtils.C_TIME_PATTON_DEFAULT, Locale.US);
    private static C5160e statistics;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void init(Context context) {
        try {
            if (statistics == null) {
                statistics = new C5160e(context, "statistics");
            }
            if (AGENT == null) {
                AGENT = new C5156a(context.getApplicationContext());
            }
            if (!Utils.isFirstTime("init" + new C5156a(context).m228a() + "stats", context)) {
                return;
            }
            statistics.m217a();
        } catch (Exception unused) {
        }
    }

    public static void doOtherStatistics(String str, String str2, String str3, Language language, Language language2) {
        if (str2 == null) {
            return;
        }
        if (language == null || language2 == null) {
            if (Utils.hasChineseCharacter(str2)) {
                language = Language.CHINESE;
                language2 = Language.ENGLISH;
            } else {
                language2 = Language.CHINESE;
                language = Language.ENGLISH;
            }
        }
        String[] split = str.split("querysdk_");
        String str4 = split.length == 2 ? split[1] : "";
        C5155a c5155a = new C5155a();
        c5155a.m229a(LogConstants.FOLLOW_OPERATION_TYPE, str4);
        c5155a.m229a("query", str2);
        c5155a.m229a(NotificationCompat.CATEGORY_MESSAGE, str3);
        c5155a.m229a("from", language.getCode());
        c5155a.m229a("to", language2.getCode());
        doStatistics(c5155a.m231a());
    }

    private static String buildLogString(Map<String, ?> map) {
        JSONObject jSONObject = new JSONObject();
        try {
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    jSONObject.put(entry.getKey(), entry.getValue());
                }
            }
            if (!TextUtils.isEmpty(AGENT.m226c())) {
                jSONObject.put("ssid", AGENT.m226c());
            }
            String m225d = AGENT.m225d();
            if (!TextUtils.isEmpty(m225d)) {
                jSONObject.put("network", m225d);
            }
            jSONObject.put("date", DATETIME_FORMAT.format(new Date()));
            return jSONObject.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    private static void doStatistics(Map<String, ? extends Object> map) {
        String buildLogString;
        if (statistics == null || (buildLogString = buildLogString(map)) == null) {
            return;
        }
        statistics.m215a(buildLogString);
        Log.i("Stats", String.format("doStatistics ----> %s", buildLogString));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void checkAndUpload() {
        C5160e c5160e = statistics;
        if (c5160e == null) {
            return;
        }
        c5160e.m213b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.youdao.sdk.app.Stats$a */
    /* loaded from: classes4.dex */
    public static class C5155a {

        /* renamed from: a */
        private Map<String, Object> f5892a;

        C5155a() {
            this.f5892a = new HashMap();
            this.f5892a = new HashMap();
        }

        /* renamed from: a */
        private C5155a m230a(String str, Object obj) {
            if (obj instanceof String) {
                if (!TextUtils.isEmpty((String) obj)) {
                    this.f5892a.put(str, obj);
                }
            } else if (obj != null) {
                this.f5892a.put(str, obj);
            }
            return this;
        }

        /* renamed from: a */
        C5155a m229a(String str, String str2) {
            m230a(str, (Object) str2);
            return this;
        }

        /* renamed from: a */
        public Map<String, Object> m231a() {
            return this.f5892a;
        }
    }
}
