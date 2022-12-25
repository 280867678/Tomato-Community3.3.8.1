package com.tencent.liteav.p125j;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import com.tencent.liteav.basic.log.TXCLog;

/* renamed from: com.tencent.liteav.j.a */
/* loaded from: classes3.dex */
public class BitmapUtil {
    /* renamed from: a */
    public static long m1446a(int i) {
        switch (i) {
            case 1:
            case 2:
            default:
                return 1000L;
            case 3:
            case 6:
                return 1500L;
            case 4:
            case 5:
                return 100L;
        }
    }

    /* renamed from: b */
    public static long m1443b(int i) {
        switch (i) {
            case 1:
            case 2:
            default:
                return 500L;
            case 3:
                return 1000L;
            case 4:
            case 5:
                return 2500L;
            case 6:
                return 1500L;
        }
    }

    /* renamed from: a */
    public static Bitmap m1447a(float f, Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (createBitmap != bitmap && bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    /* renamed from: a */
    public static Bitmap m1444a(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAlpha(i);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRect(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    /* renamed from: a */
    public static float m1445a(int i, long j) {
        long m1446a = m1446a(i);
        long m1443b = m1443b(i);
        long j2 = m1446a + m1443b;
        long j3 = j - ((j / j2) * j2);
        if (j3 >= 0 && j3 <= m1446a) {
            TXCLog.m2913i("BitmapUtil", "setBitmapsAndDisplayRatio, in stay status, cropOffsetRatio = 0, remainTimeMs = " + j3);
            return 0.0f;
        }
        float f = ((float) (j3 - m1446a)) / ((float) m1443b);
        TXCLog.m2913i("BitmapUtil", "setBitmapsAndDisplayRatio, in motion status, cropOffsetRatio = " + f + ", remainTimeMs = " + j3);
        return f;
    }

    /* renamed from: b */
    public static float m1442b(int i, long j) {
        long m1446a = m1446a(i);
        long m1443b = m1443b(i);
        long j2 = m1446a + m1443b;
        long j3 = j - ((j / j2) * j2);
        if (i != 3) {
            if (i != 4) {
                if (i == 5) {
                    if (j3 >= 0 && j3 <= m1446a) {
                        return 1.1f;
                    }
                    if (j3 > m1446a && j3 <= j2) {
                        return 1.1f - ((((float) (j3 - m1446a)) * 0.1f) / ((float) m1443b));
                    }
                }
            } else if ((j3 < 0 || j3 > m1446a) && j3 > m1446a && j3 < j2) {
                return ((((float) (j3 - m1446a)) * 0.1f) / ((float) m1443b)) + 1.0f;
            }
        } else if ((j3 < 0 || j3 > m1446a) && j3 > m1446a && j3 <= j2) {
            return 1.0f - (((float) (j3 - m1446a)) / ((float) m1443b));
        }
        return 1.0f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x004b, code lost:
        if (r7 <= (r1 + (r3 * 0.8d))) goto L8;
     */
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static float m1441c(int i, long j) {
        long m1446a = m1446a(i);
        long m1443b = m1443b(i);
        long j2 = m1446a + m1443b;
        long j3 = j - ((j / j2) * j2);
        if (i == 4 || i == 5) {
            long j4 = j3 >= 0 ? j2 : j2;
            if (j3 > m1446a + (m1443b * 0.8d) && j3 <= j4) {
                float f = (float) m1443b;
                return 1.0f - ((((float) (j3 - m1446a)) - (0.8f * f)) / (f * 0.2f));
            }
        } else if (i == 6) {
            if (j3 >= 0 && j3 <= m1446a) {
                return 1.0f;
            }
            if (j3 > m1446a && j3 <= j2) {
                return 1.0f - (((float) (j3 - m1446a)) / ((float) m1443b));
            }
        }
        return 1.0f;
    }

    /* renamed from: d */
    public static int m1440d(int i, long j) {
        long m1446a = m1446a(i);
        long m1443b = m1443b(i);
        long j2 = m1446a + m1443b;
        long j3 = j - ((j / j2) * j2);
        if (i != 3) {
            return 0;
        }
        if ((j3 > 0 && j3 <= m1446a) || j3 <= m1446a || j3 > j2) {
            return 0;
        }
        return (int) ((((float) (j3 - m1446a)) / ((float) m1443b)) * 360.0f);
    }
}
