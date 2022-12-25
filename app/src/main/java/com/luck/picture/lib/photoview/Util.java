package com.luck.picture.lib.photoview;

import android.support.p002v4.view.MotionEventCompat;
import android.widget.ImageView;

/* loaded from: classes3.dex */
class Util {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getPointerIndex(int i) {
        return (i & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void checkZoomLevels(float f, float f2, float f3) {
        if (f < f2) {
            if (f2 >= f3) {
                throw new IllegalArgumentException("Medium zoom has to be less than Maximum zoom. Call setMaximumZoom() with a more appropriate value");
            }
            return;
        }
        throw new IllegalArgumentException("Minimum zoom has to be less than Medium zoom. Call setMinimumZoom() with a more appropriate value");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean hasDrawable(ImageView imageView) {
        return imageView.getDrawable() != null;
    }

    /* renamed from: com.luck.picture.lib.photoview.Util$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C22661 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType = new int[ImageView.ScaleType.values().length];

        static {
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.MATRIX.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isSupportedScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            return false;
        }
        if (C22661.$SwitchMap$android$widget$ImageView$ScaleType[scaleType.ordinal()] == 1) {
            throw new IllegalStateException("Matrix scale type is not supported");
        }
        return true;
    }
}
