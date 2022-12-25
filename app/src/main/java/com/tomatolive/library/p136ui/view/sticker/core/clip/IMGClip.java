package com.tomatolive.library.p136ui.view.sticker.core.clip;

import android.graphics.RectF;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.clip.IMGClip */
/* loaded from: classes3.dex */
public interface IMGClip {
    public static final int CLIP_CELL_STRIDES = 1935858840;
    public static final float CLIP_CORNER_SIZE = 48.0f;
    public static final int CLIP_CORNER_STRIDES = 179303760;
    public static final float CLIP_FRAME_MIN = 150.72f;
    public static final float CLIP_MARGIN = 60.0f;
    public static final float CLIP_THICKNESS_CELL = 3.0f;
    public static final float CLIP_THICKNESS_FRAME = 8.0f;
    public static final float CLIP_THICKNESS_SEWING = 14.0f;
    public static final float[] CLIP_SIZE_RATIO = {0.0f, 1.0f, 0.33f, 0.66f};
    public static final float[] CLIP_CORNER_STEPS = {0.0f, 3.0f, -3.0f};
    public static final float[] CLIP_CORNER_SIZES = {0.0f, 48.0f, -48.0f};
    public static final byte[] CLIP_CORNERS = {8, 8, 9, 8, 6, 8, 4, 8, 4, 8, 4, 1, 4, 10, 4, 8, 4, 4, 6, 4, 9, 4, 8, 4, 8, 4, 8, 6, 8, 9, 8, 8};

    /* renamed from: com.tomatolive.library.ui.view.sticker.core.clip.IMGClip$Anchor */
    /* loaded from: classes3.dex */
    public enum Anchor {
        LEFT(1),
        RIGHT(2),
        TOP(4),
        BOTTOM(8),
        LEFT_TOP(5),
        RIGHT_TOP(6),
        LEFT_BOTTOM(9),
        RIGHT_BOTTOM(10);
        

        /* renamed from: PN */
        static final int[] f5853PN = {1, -1};

        /* renamed from: v */
        int f5854v;

        Anchor(int i) {
            this.f5854v = i;
        }

        public void move(RectF rectF, RectF rectF2, float f, float f2) {
            float[] cohesion = cohesion(rectF, 60.0f);
            float[] cohesion2 = cohesion(rectF2, 150.72f);
            float[] cohesion3 = cohesion(rectF2, 0.0f);
            float[] fArr = {f, 0.0f, f2};
            for (int i = 0; i < 4; i++) {
                if (((1 << i) & this.f5854v) != 0) {
                    int[] iArr = f5853PN;
                    int i2 = i & 1;
                    float f3 = iArr[i2];
                    cohesion3[i] = f3 * revise((cohesion3[i] + fArr[i & 2]) * f3, cohesion[i] * f3, cohesion2[iArr[i2] + i] * f3);
                }
            }
            rectF2.set(cohesion3[0], cohesion3[2], cohesion3[1], cohesion3[3]);
        }

        public static float revise(float f, float f2, float f3) {
            return Math.min(Math.max(f, f2), f3);
        }

        public static float[] cohesion(RectF rectF, float f) {
            return new float[]{rectF.left + f, rectF.right - f, rectF.top + f, rectF.bottom - f};
        }

        public static boolean isCohesionContains(RectF rectF, float f, float f2, float f3) {
            return rectF.left + f < f2 && rectF.right - f > f2 && rectF.top + f < f3 && rectF.bottom - f > f3;
        }

        public static Anchor valueOf(int i) {
            Anchor[] values;
            for (Anchor anchor : values()) {
                if (anchor.f5854v == i) {
                    return anchor;
                }
            }
            return null;
        }
    }
}
