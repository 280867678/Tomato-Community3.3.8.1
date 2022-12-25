package com.opensource.svgaplayer.utils;

import android.widget.ImageView;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SVGAScaleInfo.kt */
/* loaded from: classes3.dex */
public final class SVGAScaleInfo {
    private boolean ratioX;
    private float scaleFx = 1.0f;
    private float scaleFy = 1.0f;
    private float tranFx;
    private float tranFy;

    /* loaded from: classes3.dex */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[ImageView.ScaleType.values().length];

        static {
            $EnumSwitchMapping$0[ImageView.ScaleType.CENTER.ordinal()] = 1;
            $EnumSwitchMapping$0[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            $EnumSwitchMapping$0[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 3;
            $EnumSwitchMapping$0[ImageView.ScaleType.FIT_CENTER.ordinal()] = 4;
            $EnumSwitchMapping$0[ImageView.ScaleType.FIT_START.ordinal()] = 5;
            $EnumSwitchMapping$0[ImageView.ScaleType.FIT_END.ordinal()] = 6;
            $EnumSwitchMapping$0[ImageView.ScaleType.FIT_XY.ordinal()] = 7;
        }
    }

    public final float getTranFx() {
        return this.tranFx;
    }

    public final float getTranFy() {
        return this.tranFy;
    }

    public final float getScaleFx() {
        return this.scaleFx;
    }

    public final float getScaleFy() {
        return this.scaleFy;
    }

    public final boolean getRatioX() {
        return this.ratioX;
    }

    private final void resetVar() {
        this.tranFx = 0.0f;
        this.tranFy = 0.0f;
        this.scaleFx = 1.0f;
        this.scaleFy = 1.0f;
        this.ratioX = false;
    }

    public final void performScaleType(float f, float f2, float f3, float f4, ImageView.ScaleType scaleType) {
        Intrinsics.checkParameterIsNotNull(scaleType, "scaleType");
        if (f == 0.0f || f2 == 0.0f || f3 == 0.0f || f4 == 0.0f) {
            return;
        }
        resetVar();
        float f5 = (f - f3) / 2.0f;
        float f6 = (f2 - f4) / 2.0f;
        float f7 = f3 / f4;
        float f8 = f / f2;
        float f9 = f2 / f4;
        float f10 = f / f3;
        boolean z = false;
        switch (WhenMappings.$EnumSwitchMapping$0[scaleType.ordinal()]) {
            case 1:
                this.tranFx = f5;
                this.tranFy = f6;
                return;
            case 2:
                if (f7 > f8) {
                    this.ratioX = false;
                    this.scaleFx = f9;
                    this.scaleFy = f9;
                    this.tranFx = (f - (f3 * f9)) / 2.0f;
                    return;
                }
                this.ratioX = true;
                this.scaleFx = f10;
                this.scaleFy = f10;
                this.tranFy = (f2 - (f4 * f10)) / 2.0f;
                return;
            case 3:
                if (f3 < f && f4 < f2) {
                    this.tranFx = f5;
                    this.tranFy = f6;
                    return;
                } else if (f7 > f8) {
                    this.ratioX = true;
                    this.scaleFx = f10;
                    this.scaleFy = f10;
                    this.tranFy = (f2 - (f4 * f10)) / 2.0f;
                    return;
                } else {
                    this.ratioX = false;
                    this.scaleFx = f9;
                    this.scaleFy = f9;
                    this.tranFx = (f - (f3 * f9)) / 2.0f;
                    return;
                }
            case 4:
                if (f7 > f8) {
                    this.ratioX = true;
                    this.scaleFx = f10;
                    this.scaleFy = f10;
                    this.tranFy = (f2 - (f4 * f10)) / 2.0f;
                    return;
                }
                this.ratioX = false;
                this.scaleFx = f9;
                this.scaleFy = f9;
                this.tranFx = (f - (f3 * f9)) / 2.0f;
                return;
            case 5:
                if (f7 > f8) {
                    this.ratioX = true;
                    this.scaleFx = f10;
                    this.scaleFy = f10;
                    return;
                }
                this.ratioX = false;
                this.scaleFx = f9;
                this.scaleFy = f9;
                return;
            case 6:
                if (f7 > f8) {
                    this.ratioX = true;
                    this.scaleFx = f10;
                    this.scaleFy = f10;
                    this.tranFy = f2 - (f4 * f10);
                    return;
                }
                this.ratioX = false;
                this.scaleFx = f9;
                this.scaleFy = f9;
                this.tranFx = f - (f3 * f9);
                return;
            case 7:
                Math.max(f10, f9);
                if (f10 > f9) {
                    z = true;
                }
                this.ratioX = z;
                this.scaleFx = f10;
                this.scaleFy = f9;
                return;
            default:
                this.ratioX = true;
                this.scaleFx = f10;
                this.scaleFy = f10;
                return;
        }
    }
}
