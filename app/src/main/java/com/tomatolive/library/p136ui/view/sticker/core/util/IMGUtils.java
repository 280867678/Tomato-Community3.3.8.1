package com.tomatolive.library.p136ui.view.sticker.core.util;

import android.graphics.Matrix;
import android.graphics.RectF;
import com.tomatolive.library.p136ui.view.sticker.core.homing.IMGHoming;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.util.IMGUtils */
/* loaded from: classes3.dex */
public class IMGUtils {

    /* renamed from: M */
    private static final Matrix f5864M = new Matrix();

    public static int inSampleSize(int i) {
        int i2 = 1;
        for (int i3 = i; i3 > 1; i3 >>= 1) {
            i2 <<= 1;
        }
        return i2 != i ? i2 << 1 : i2;
    }

    private IMGUtils() {
    }

    public static void center(RectF rectF, RectF rectF2) {
        rectF2.offset(rectF.centerX() - rectF2.centerX(), rectF.centerY() - rectF2.centerY());
    }

    public static void fitCenter(RectF rectF, RectF rectF2) {
        fitCenter(rectF, rectF2, 0.0f);
    }

    public static void fitCenter(RectF rectF, RectF rectF2, float f) {
        fitCenter(rectF, rectF2, f, f, f, f);
    }

    public static void fitCenter(RectF rectF, RectF rectF2, float f, float f2, float f3, float f4) {
        if (rectF.isEmpty() || rectF2.isEmpty()) {
            return;
        }
        if (rectF.width() < f + f3) {
            f = 0.0f;
            f3 = 0.0f;
        }
        if (rectF.height() < f2 + f4) {
            f2 = 0.0f;
            f4 = 0.0f;
        }
        float min = Math.min(((rectF.width() - f) - f3) / rectF2.width(), ((rectF.height() - f2) - f4) / rectF2.height());
        rectF2.set(0.0f, 0.0f, rectF2.width() * min, rectF2.height() * min);
        rectF2.offset((rectF.centerX() + ((f - f3) / 2.0f)) - rectF2.centerX(), (rectF.centerY() + ((f2 - f4) / 2.0f)) - rectF2.centerY());
    }

    public static IMGHoming fitHoming(RectF rectF, RectF rectF2) {
        IMGHoming iMGHoming = new IMGHoming(0.0f, 0.0f, 1.0f, 0.0f);
        if (rectF2.contains(rectF)) {
            return iMGHoming;
        }
        if (rectF2.width() < rectF.width() && rectF2.height() < rectF.height()) {
            iMGHoming.scale = Math.min(rectF.width() / rectF2.width(), rectF.height() / rectF2.height());
        }
        RectF rectF3 = new RectF();
        Matrix matrix = f5864M;
        float f = iMGHoming.scale;
        matrix.setScale(f, f, rectF2.centerX(), rectF2.centerY());
        f5864M.mapRect(rectF3, rectF2);
        if (rectF3.width() < rectF.width()) {
            iMGHoming.f5856x += rectF.centerX() - rectF3.centerX();
        } else {
            float f2 = rectF3.left;
            float f3 = rectF.left;
            if (f2 > f3) {
                iMGHoming.f5856x += f3 - f2;
            } else {
                float f4 = rectF3.right;
                float f5 = rectF.right;
                if (f4 < f5) {
                    iMGHoming.f5856x += f5 - f4;
                }
            }
        }
        if (rectF3.height() < rectF.height()) {
            iMGHoming.f5857y += rectF.centerY() - rectF3.centerY();
        } else {
            float f6 = rectF3.top;
            float f7 = rectF.top;
            if (f6 > f7) {
                iMGHoming.f5857y += f7 - f6;
            } else {
                float f8 = rectF3.bottom;
                float f9 = rectF.bottom;
                if (f8 < f9) {
                    iMGHoming.f5857y += f9 - f8;
                }
            }
        }
        return iMGHoming;
    }

    public static IMGHoming fitHoming(RectF rectF, RectF rectF2, float f, float f2) {
        IMGHoming iMGHoming = new IMGHoming(0.0f, 0.0f, 1.0f, 0.0f);
        if (rectF2.contains(rectF)) {
            return iMGHoming;
        }
        if (rectF2.width() < rectF.width() && rectF2.height() < rectF.height()) {
            iMGHoming.scale = Math.min(rectF.width() / rectF2.width(), rectF.height() / rectF2.height());
        }
        RectF rectF3 = new RectF();
        Matrix matrix = f5864M;
        float f3 = iMGHoming.scale;
        matrix.setScale(f3, f3, f, f2);
        f5864M.mapRect(rectF3, rectF2);
        if (rectF3.width() < rectF.width()) {
            iMGHoming.f5856x += rectF.centerX() - rectF3.centerX();
        } else {
            float f4 = rectF3.left;
            float f5 = rectF.left;
            if (f4 > f5) {
                iMGHoming.f5856x += f5 - f4;
            } else {
                float f6 = rectF3.right;
                float f7 = rectF.right;
                if (f6 < f7) {
                    iMGHoming.f5856x += f7 - f6;
                }
            }
        }
        if (rectF3.height() < rectF.height()) {
            iMGHoming.f5857y += rectF.centerY() - rectF3.centerY();
        } else {
            float f8 = rectF3.top;
            float f9 = rectF.top;
            if (f8 > f9) {
                iMGHoming.f5857y += f9 - f8;
            } else {
                float f10 = rectF3.bottom;
                float f11 = rectF.bottom;
                if (f10 < f11) {
                    iMGHoming.f5857y += f11 - f10;
                }
            }
        }
        return iMGHoming;
    }

    public static IMGHoming fitHoming(RectF rectF, RectF rectF2, boolean z) {
        IMGHoming iMGHoming = new IMGHoming(0.0f, 0.0f, 1.0f, 0.0f);
        if (!rectF2.contains(rectF) || z) {
            if (z || (rectF2.width() < rectF.width() && rectF2.height() < rectF.height())) {
                iMGHoming.scale = Math.min(rectF.width() / rectF2.width(), rectF.height() / rectF2.height());
            }
            RectF rectF3 = new RectF();
            Matrix matrix = f5864M;
            float f = iMGHoming.scale;
            matrix.setScale(f, f, rectF2.centerX(), rectF2.centerY());
            f5864M.mapRect(rectF3, rectF2);
            if (rectF3.width() < rectF.width()) {
                iMGHoming.f5856x += rectF.centerX() - rectF3.centerX();
            } else {
                float f2 = rectF3.left;
                float f3 = rectF.left;
                if (f2 > f3) {
                    iMGHoming.f5856x += f3 - f2;
                } else {
                    float f4 = rectF3.right;
                    float f5 = rectF.right;
                    if (f4 < f5) {
                        iMGHoming.f5856x += f5 - f4;
                    }
                }
            }
            if (rectF3.height() < rectF.height()) {
                iMGHoming.f5857y += rectF.centerY() - rectF3.centerY();
            } else {
                float f6 = rectF3.top;
                float f7 = rectF.top;
                if (f6 > f7) {
                    iMGHoming.f5857y += f7 - f6;
                } else {
                    float f8 = rectF3.bottom;
                    float f9 = rectF.bottom;
                    if (f8 < f9) {
                        iMGHoming.f5857y += f9 - f8;
                    }
                }
            }
            return iMGHoming;
        }
        return iMGHoming;
    }

    public static IMGHoming fillHoming(RectF rectF, RectF rectF2) {
        IMGHoming iMGHoming = new IMGHoming(0.0f, 0.0f, 1.0f, 0.0f);
        if (rectF2.contains(rectF)) {
            return iMGHoming;
        }
        if (rectF2.width() < rectF.width() || rectF2.height() < rectF.height()) {
            iMGHoming.scale = Math.max(rectF.width() / rectF2.width(), rectF.height() / rectF2.height());
        }
        RectF rectF3 = new RectF();
        Matrix matrix = f5864M;
        float f = iMGHoming.scale;
        matrix.setScale(f, f, rectF2.centerX(), rectF2.centerY());
        f5864M.mapRect(rectF3, rectF2);
        float f2 = rectF3.left;
        float f3 = rectF.left;
        if (f2 > f3) {
            iMGHoming.f5856x += f3 - f2;
        } else {
            float f4 = rectF3.right;
            float f5 = rectF.right;
            if (f4 < f5) {
                iMGHoming.f5856x += f5 - f4;
            }
        }
        float f6 = rectF3.top;
        float f7 = rectF.top;
        if (f6 > f7) {
            iMGHoming.f5857y += f7 - f6;
        } else {
            float f8 = rectF3.bottom;
            float f9 = rectF.bottom;
            if (f8 < f9) {
                iMGHoming.f5857y += f9 - f8;
            }
        }
        return iMGHoming;
    }

    public static IMGHoming fillHoming(RectF rectF, RectF rectF2, float f, float f2) {
        IMGHoming iMGHoming = new IMGHoming(0.0f, 0.0f, 1.0f, 0.0f);
        if (rectF2.contains(rectF)) {
            return iMGHoming;
        }
        if (rectF2.width() < rectF.width() || rectF2.height() < rectF.height()) {
            iMGHoming.scale = Math.max(rectF.width() / rectF2.width(), rectF.height() / rectF2.height());
        }
        RectF rectF3 = new RectF();
        Matrix matrix = f5864M;
        float f3 = iMGHoming.scale;
        matrix.setScale(f3, f3, f, f2);
        f5864M.mapRect(rectF3, rectF2);
        float f4 = rectF3.left;
        float f5 = rectF.left;
        if (f4 > f5) {
            iMGHoming.f5856x += f5 - f4;
        } else {
            float f6 = rectF3.right;
            float f7 = rectF.right;
            if (f6 < f7) {
                iMGHoming.f5856x += f7 - f6;
            }
        }
        float f8 = rectF3.top;
        float f9 = rectF.top;
        if (f8 > f9) {
            iMGHoming.f5857y += f9 - f8;
        } else {
            float f10 = rectF3.bottom;
            float f11 = rectF.bottom;
            if (f10 < f11) {
                iMGHoming.f5857y += f11 - f10;
            }
        }
        return iMGHoming;
    }

    public static IMGHoming fill(RectF rectF, RectF rectF2) {
        IMGHoming iMGHoming = new IMGHoming(0.0f, 0.0f, 1.0f, 0.0f);
        if (rectF.equals(rectF2)) {
            return iMGHoming;
        }
        iMGHoming.scale = Math.max(rectF.width() / rectF2.width(), rectF.height() / rectF2.height());
        RectF rectF3 = new RectF();
        Matrix matrix = f5864M;
        float f = iMGHoming.scale;
        matrix.setScale(f, f, rectF2.centerX(), rectF2.centerY());
        f5864M.mapRect(rectF3, rectF2);
        iMGHoming.f5856x += rectF.centerX() - rectF3.centerX();
        iMGHoming.f5857y += rectF.centerY() - rectF3.centerY();
        return iMGHoming;
    }

    public static void rectFill(RectF rectF, RectF rectF2) {
        if (rectF.equals(rectF2)) {
            return;
        }
        float max = Math.max(rectF.width() / rectF2.width(), rectF.height() / rectF2.height());
        f5864M.setScale(max, max, rectF2.centerX(), rectF2.centerY());
        f5864M.mapRect(rectF2);
        float f = rectF2.left;
        float f2 = rectF.left;
        if (f > f2) {
            rectF2.left = f2;
        } else {
            float f3 = rectF2.right;
            float f4 = rectF.right;
            if (f3 < f4) {
                rectF2.right = f4;
            }
        }
        float f5 = rectF2.top;
        float f6 = rectF.top;
        if (f5 > f6) {
            rectF2.top = f6;
            return;
        }
        float f7 = rectF2.bottom;
        float f8 = rectF.bottom;
        if (f7 >= f8) {
            return;
        }
        rectF2.bottom = f8;
    }
}
