package com.tencent.liteav.p125j;

import com.tencent.liteav.basic.log.TXCLog;
import org.slf4j.Marker;

/* renamed from: com.tencent.liteav.j.c */
/* loaded from: classes3.dex */
public class TXResolutionUtils {
    /* JADX WARN: Code restructure failed: missing block: B:16:0x004f, code lost:
        if (r9 >= 640) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0051, code lost:
        r10 = 640;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0094, code lost:
        if (r9 >= 640) goto L17;
     */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int[] m1431a(int i, int i2, int i3) {
        int i4;
        if (i2 <= 0 || i3 <= 0) {
            TXCLog.m2911w("GlUtil", "no input size. " + i2 + Marker.ANY_MARKER + i3);
            return new int[]{i2, i3};
        }
        float f = (i2 * 1.0f) / i3;
        if (i == 0) {
            if ((i2 <= 640 && i3 <= 360) || (i2 <= 360 && i3 <= 640)) {
                TXCLog.m2915d("GlUtil", "target size: " + i2 + Marker.ANY_MARKER + i3);
                return new int[]{i2, i3};
            } else if (i2 < i3) {
                i2 = (int) (640.0f * f);
                if (i2 >= 360) {
                    i2 = 360;
                }
                i3 = (int) (i2 / f);
                return new int[]{((i2 + 1) >> 1) << 1, ((i3 + 1) >> 1) << 1};
            } else {
                i4 = (int) (360.0f * f);
            }
        } else if (i != 1) {
            if (i == 2) {
                if ((i2 <= 960 && i3 <= 544) || (i2 <= 544 && i3 <= 960)) {
                    TXCLog.m2915d("GlUtil", "target size: " + i2 + Marker.ANY_MARKER + i3);
                    return new int[]{i2, i3};
                }
                if (i2 >= i3) {
                    i4 = (int) (544.0f * f);
                    if (i4 >= 960) {
                        i2 = 960;
                    }
                    i2 = i4;
                } else {
                    i2 = (int) (960.0f * f);
                    if (i2 >= 544) {
                        i2 = 544;
                    }
                }
                i3 = (int) (i2 / f);
                return new int[]{((i2 + 1) >> 1) << 1, ((i3 + 1) >> 1) << 1};
            }
            if (i == 3) {
                if ((i2 <= 1280 && i3 <= 720) || (i2 <= 720 && i3 <= 1280)) {
                    TXCLog.m2915d("GlUtil", "target size: " + i2 + Marker.ANY_MARKER + i3);
                    return new int[]{i2, i3};
                }
                if (i2 >= i3) {
                    i4 = (int) (720.0f * f);
                    if (i4 >= 1280) {
                        i2 = 1280;
                    }
                    i2 = i4;
                } else {
                    i2 = (int) (1280.0f * f);
                    if (i2 >= 720) {
                        i2 = 720;
                    }
                }
                i3 = (int) (i2 / f);
            }
            return new int[]{((i2 + 1) >> 1) << 1, ((i3 + 1) >> 1) << 1};
        } else if ((i2 <= 640 && i3 <= 480) || (i2 <= 480 && i3 <= 640)) {
            TXCLog.m2915d("GlUtil", "target size: " + i2 + Marker.ANY_MARKER + i3);
            return new int[]{i2, i3};
        } else if (i2 < i3) {
            i2 = (int) (640.0f * f);
            if (i2 >= 480) {
                i2 = 480;
            }
            i3 = (int) (i2 / f);
            return new int[]{((i2 + 1) >> 1) << 1, ((i3 + 1) >> 1) << 1};
        } else {
            i4 = (int) (480.0f * f);
        }
    }
}
