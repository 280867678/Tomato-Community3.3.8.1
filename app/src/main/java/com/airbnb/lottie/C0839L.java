package com.airbnb.lottie;

import android.support.annotation.RestrictTo;
import android.support.p002v4.p004os.TraceCompat;
import android.util.Log;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* renamed from: com.airbnb.lottie.L */
/* loaded from: classes2.dex */
public class C0839L {
    private static int depthPastMaxDepth;
    private static String[] sections;
    private static long[] startTimeNs;
    private static int traceDepth;
    private static boolean traceEnabled;

    public static void warn(String str) {
        Log.w("LOTTIE", str);
    }

    public static void beginSection(String str) {
        if (!traceEnabled) {
            return;
        }
        int i = traceDepth;
        if (i == 20) {
            depthPastMaxDepth++;
            return;
        }
        sections[i] = str;
        startTimeNs[i] = System.nanoTime();
        TraceCompat.beginSection(str);
        traceDepth++;
    }

    public static float endSection(String str) {
        int i = depthPastMaxDepth;
        if (i > 0) {
            depthPastMaxDepth = i - 1;
            return 0.0f;
        } else if (!traceEnabled) {
            return 0.0f;
        } else {
            traceDepth--;
            int i2 = traceDepth;
            if (i2 == -1) {
                throw new IllegalStateException("Can't end trace section. There are none.");
            }
            if (!str.equals(sections[i2])) {
                throw new IllegalStateException("Unbalanced trace call " + str + ". Expected " + sections[traceDepth] + ".");
            }
            TraceCompat.endSection();
            return ((float) (System.nanoTime() - startTimeNs[traceDepth])) / 1000000.0f;
        }
    }
}
