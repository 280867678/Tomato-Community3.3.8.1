package p007b.p008a.p009a.p011b;

import android.content.Context;
import com.j256.ormlite.logger.LocalLog;

/* renamed from: b.a.a.b.d */
/* loaded from: classes2.dex */
public class C0574d extends C0579i {

    /* renamed from: c */
    public static String f113c = null;

    /* renamed from: d */
    public static int f114d = -1;

    /* renamed from: e */
    public static volatile C0574d f115e;

    public C0574d(Context context) {
        super(context, f113c, null, f114d);
        System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "FATAL");
    }

    /* renamed from: a */
    public static C0574d m5557a(Context context) {
        if (f113c == null || f114d < 0) {
            throw new IllegalStateException("database name or database version not initialize");
        }
        C0574d c0574d = f115e;
        if (c0574d == null) {
            synchronized (C0574d.class) {
                c0574d = f115e;
                if (c0574d == null) {
                    c0574d = new C0574d(context);
                    f115e = c0574d;
                }
            }
        }
        return c0574d;
    }

    /* renamed from: a */
    public static void m5558a(int i) {
        f114d = i;
    }

    /* renamed from: a */
    public static void m5556a(String str) {
        f113c = str;
    }
}
