package com.alipay.sdk.app.statistic;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.data.C0962a;
import com.alipay.sdk.packet.impl.C0983d;
import com.alipay.sdk.packet.impl.C0984e;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.util.C1004j;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;

/* renamed from: com.alipay.sdk.app.statistic.a */
/* loaded from: classes2.dex */
public class C0954a {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.alipay.sdk.app.statistic.a$c */
    /* loaded from: classes2.dex */
    public static final class C0958c {
        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: a */
        public static synchronized long m4617a(Context context) {
            long j;
            synchronized (C0958c.class) {
                long j2 = 0;
                try {
                    String m4407b = C1004j.m4407b(null, context, "alipay_cashier_statistic_v", null);
                    if (!TextUtils.isEmpty(m4407b)) {
                        j2 = Long.parseLong(m4407b);
                    }
                } catch (Throwable unused) {
                }
                j = j2 + 1;
                try {
                    C1004j.m4408a(null, context, "alipay_cashier_statistic_v", Long.toString(j));
                } catch (Throwable unused2) {
                }
            }
            return j;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.alipay.sdk.app.statistic.a$a */
    /* loaded from: classes2.dex */
    public static final class C0955a {
        /* renamed from: a */
        static synchronized String m4624a(Context context, String str, String str2) {
            synchronized (C0955a.class) {
                C0996c.m4438a("RecordPref", "stat append " + str2 + " , " + str);
                if (context != null && !TextUtils.isEmpty(str)) {
                    if (TextUtils.isEmpty(str2)) {
                        str2 = UUID.randomUUID().toString();
                    }
                    C0956a m4623b = m4623b(context);
                    if (m4623b.f946a.size() > 20) {
                        m4623b.f946a.clear();
                    }
                    m4623b.f946a.put(str2, str);
                    m4626a(context, m4623b);
                    return str2;
                }
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: a */
        public static synchronized String m4627a(Context context) {
            synchronized (C0955a.class) {
                C0996c.m4438a("RecordPref", "stat peek");
                if (context == null) {
                    return null;
                }
                C0956a m4623b = m4623b(context);
                if (m4623b.f946a.isEmpty()) {
                    return null;
                }
                return m4623b.f946a.entrySet().iterator().next().getValue();
            }
        }

        /* renamed from: a */
        static synchronized int m4625a(Context context, String str) {
            synchronized (C0955a.class) {
                C0996c.m4438a("RecordPref", "stat remove " + str);
                if (context != null && !TextUtils.isEmpty(str)) {
                    C0956a m4623b = m4623b(context);
                    if (m4623b.f946a.isEmpty()) {
                        return 0;
                    }
                    ArrayList arrayList = new ArrayList();
                    for (Map.Entry<String, String> entry : m4623b.f946a.entrySet()) {
                        if (str.equals(entry.getValue())) {
                            arrayList.add(entry.getKey());
                        }
                    }
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        m4623b.f946a.remove((String) it2.next());
                    }
                    m4626a(context, m4623b);
                    return arrayList.size();
                }
                return 0;
            }
        }

        /* renamed from: b */
        private static synchronized C0956a m4623b(Context context) {
            synchronized (C0955a.class) {
                try {
                    String m4407b = C1004j.m4407b(null, context, "alipay_cashier_statistic_record", null);
                    if (TextUtils.isEmpty(m4407b)) {
                        return new C0956a();
                    }
                    return new C0956a(m4407b);
                } catch (Throwable th) {
                    C0996c.m4436a(th);
                    return new C0956a();
                }
            }
        }

        /* renamed from: a */
        private static synchronized void m4626a(Context context, C0956a c0956a) {
            synchronized (C0955a.class) {
                if (c0956a == null) {
                    try {
                        c0956a = new C0956a();
                    } catch (Throwable th) {
                        C0996c.m4436a(th);
                    }
                }
                C1004j.m4408a(null, context, "alipay_cashier_statistic_record", c0956a.m4622a());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: com.alipay.sdk.app.statistic.a$a$a */
        /* loaded from: classes2.dex */
        public static final class C0956a {

            /* renamed from: a */
            final LinkedHashMap<String, String> f946a = new LinkedHashMap<>();

            C0956a() {
            }

            C0956a(String str) {
                try {
                    JSONArray jSONArray = new JSONArray(str);
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONArray jSONArray2 = jSONArray.getJSONArray(i);
                        this.f946a.put(jSONArray2.getString(0), jSONArray2.getString(1));
                    }
                } catch (Throwable th) {
                    C0996c.m4436a(th);
                }
            }

            /* renamed from: a */
            String m4622a() {
                try {
                    JSONArray jSONArray = new JSONArray();
                    for (Map.Entry<String, String> entry : this.f946a.entrySet()) {
                        JSONArray jSONArray2 = new JSONArray();
                        jSONArray2.put(entry.getKey()).put(entry.getValue());
                        jSONArray.put(jSONArray2);
                    }
                    return jSONArray.toString();
                } catch (Throwable th) {
                    C0996c.m4436a(th);
                    return new JSONArray().toString();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.alipay.sdk.app.statistic.a$b */
    /* loaded from: classes2.dex */
    public static final class C0957b {
        /* renamed from: a */
        static synchronized void m4621a(Context context, C0960c c0960c, String str, String str2) {
            synchronized (C0957b.class) {
                if (context == null || c0960c == null || str == null) {
                    return;
                }
                m4619a(context, c0960c.m4613a(str), str2);
            }
        }

        /* renamed from: a */
        private static synchronized void m4619a(Context context, String str, String str2) {
            synchronized (C0957b.class) {
                if (context == null) {
                    return;
                }
                if (!TextUtils.isEmpty(str)) {
                    C0955a.m4624a(context, str, str2);
                }
                new Thread(new RunnableC0959b(str, context)).start();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: b */
        public static synchronized boolean m4618b(Context context, String str) {
            synchronized (C0957b.class) {
                C0996c.m4438a("mspl", "stat sub " + str);
                if ((C0962a.m4581j().m4584g() ? new C0983d() : new C0984e()).mo4506a((C0988a) null, context, str) != null) {
                    C0955a.m4625a(context, str);
                    return true;
                }
                return false;
            }
        }
    }

    /* renamed from: a */
    public static synchronized void m4635a(Context context, C0988a c0988a, String str, String str2) {
        synchronized (C0954a.class) {
            if (context == null || c0988a == null) {
                return;
            }
            try {
                C0955a.m4624a(context, c0988a.f1016s.m4613a(str), str2);
            } catch (Throwable th) {
                C0996c.m4436a(th);
            }
        }
    }

    /* renamed from: b */
    public static synchronized void m4629b(Context context, C0988a c0988a, String str, String str2) {
        synchronized (C0954a.class) {
            if (context == null || c0988a == null) {
                return;
            }
            C0957b.m4621a(context, c0988a.f1016s, str, str2);
        }
    }

    /* renamed from: a */
    public static void m4630a(C0988a c0988a, String str, Throwable th) {
        if (c0988a == null || th == null || th.getClass() == null) {
            return;
        }
        c0988a.f1016s.m4610a(str, th.getClass().getSimpleName(), th);
    }

    /* renamed from: a */
    public static void m4631a(C0988a c0988a, String str, String str2, Throwable th, String str3) {
        if (c0988a == null) {
            return;
        }
        c0988a.f1016s.m4609a(str, str2, th, str3);
    }

    /* renamed from: a */
    public static void m4632a(C0988a c0988a, String str, String str2, Throwable th) {
        if (c0988a == null) {
            return;
        }
        c0988a.f1016s.m4610a(str, str2, th);
    }

    /* renamed from: a */
    public static void m4633a(C0988a c0988a, String str, String str2, String str3) {
        if (c0988a == null) {
            return;
        }
        c0988a.f1016s.m4611a(str, str2, str3);
    }

    /* renamed from: b */
    public static void m4628b(C0988a c0988a, String str, String str2, String str3) {
        if (c0988a == null) {
            return;
        }
        c0988a.f1016s.m4604b(str, str2, str3);
    }

    /* renamed from: a */
    public static void m4634a(C0988a c0988a, String str, String str2) {
        if (c0988a == null) {
            return;
        }
        c0988a.f1016s.m4612a(str, str2);
    }
}
