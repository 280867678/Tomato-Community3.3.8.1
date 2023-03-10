package android.support.p002v4.p004os;

import android.os.Build;
import android.os.Trace;

/* renamed from: android.support.v4.os.TraceCompat */
/* loaded from: classes2.dex */
public final class TraceCompat {
    public static void beginSection(String str) {
        if (Build.VERSION.SDK_INT >= 18) {
            Trace.beginSection(str);
        }
    }

    public static void endSection() {
        if (Build.VERSION.SDK_INT >= 18) {
            Trace.endSection();
        }
    }

    private TraceCompat() {
    }
}
