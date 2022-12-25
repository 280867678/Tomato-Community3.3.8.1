package com.opensource.svgaplayer;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.opensource.svgaplayer.drawer.SVGACanvasDrawer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SVGADrawable.kt */
/* loaded from: classes3.dex */
public final class SVGADrawable extends Drawable {
    private boolean cleared;
    private int currentFrame;
    private final SVGACanvasDrawer drawer;
    private final SVGADynamicEntity dynamicItem;
    private ImageView.ScaleType scaleType;
    private final SVGAVideoEntity videoItem;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public SVGADrawable(SVGAVideoEntity videoItem, SVGADynamicEntity dynamicItem) {
        Intrinsics.checkParameterIsNotNull(videoItem, "videoItem");
        Intrinsics.checkParameterIsNotNull(dynamicItem, "dynamicItem");
        this.videoItem = videoItem;
        this.dynamicItem = dynamicItem;
        this.cleared = true;
        this.scaleType = ImageView.ScaleType.MATRIX;
        this.drawer = new SVGACanvasDrawer(this.videoItem, this.dynamicItem);
    }

    public final SVGADynamicEntity getDynamicItem() {
        return this.dynamicItem;
    }

    public final SVGAVideoEntity getVideoItem() {
        return this.videoItem;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SVGADrawable(SVGAVideoEntity videoItem) {
        this(videoItem, new SVGADynamicEntity());
        Intrinsics.checkParameterIsNotNull(videoItem, "videoItem");
    }

    public final void setCleared$library_release(boolean z) {
        if (this.cleared == z) {
            return;
        }
        this.cleared = z;
        invalidateSelf();
    }

    public final int getCurrentFrame() {
        return this.currentFrame;
    }

    public final void setCurrentFrame$library_release(int i) {
        if (this.currentFrame == i) {
            return;
        }
        this.currentFrame = i;
        invalidateSelf();
    }

    public final void setScaleType(ImageView.ScaleType scaleType) {
        Intrinsics.checkParameterIsNotNull(scaleType, "<set-?>");
        this.scaleType = scaleType;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (!this.cleared && canvas != null) {
            this.drawer.drawFrame(canvas, this.currentFrame, this.scaleType);
        }
    }
}
