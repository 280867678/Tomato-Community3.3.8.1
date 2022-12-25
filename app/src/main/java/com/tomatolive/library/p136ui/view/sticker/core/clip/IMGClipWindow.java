package com.tomatolive.library.p136ui.view.sticker.core.clip;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import com.tomatolive.library.p136ui.view.sticker.core.clip.IMGClip;
import com.tomatolive.library.p136ui.view.sticker.core.util.IMGUtils;
import java.lang.reflect.Array;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.clip.IMGClipWindow */
/* loaded from: classes3.dex */
public class IMGClipWindow implements IMGClip {
    private static final int COLOR_CELL = -2130706433;
    private static final int COLOR_CORNER = -1;
    private static final int COLOR_FRAME = -1;
    private static final int COLOR_SHADE = -872415232;
    private static final float VERTICAL_RATIO = 0.8f;
    private RectF mFrame = new RectF();
    private RectF mBaseFrame = new RectF();
    private RectF mTargetFrame = new RectF();
    private RectF mWinFrame = new RectF();
    private RectF mWin = new RectF();
    private float[] mCells = new float[16];
    private float[] mCorners = new float[32];
    private float[][] mBaseSizes = (float[][]) Array.newInstance(float.class, 2, 4);
    private boolean isClipping = false;
    private boolean isResetting = true;
    private boolean isShowShade = false;
    private boolean isHoming = false;

    /* renamed from: M */
    private Matrix f5855M = new Matrix();
    private Path mShadePath = new Path();
    private Paint mPaint = new Paint(1);

    public IMGClipWindow() {
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
    }

    public void setClipWinSize(float f, float f2) {
        this.mWin.set(0.0f, 0.0f, f, f2);
        this.mWinFrame.set(0.0f, 0.0f, f, f2 * VERTICAL_RATIO);
        if (!this.mFrame.isEmpty()) {
            IMGUtils.center(this.mWinFrame, this.mFrame);
            this.mTargetFrame.set(this.mFrame);
        }
    }

    public void reset(RectF rectF, float f) {
        RectF rectF2 = new RectF();
        this.f5855M.setRotate(f, rectF.centerX(), rectF.centerY());
        this.f5855M.mapRect(rectF2, rectF);
        reset(rectF2.width(), rectF2.height());
    }

    private void reset(float f, float f2) {
        setResetting(true);
        this.mFrame.set(0.0f, 0.0f, f, f2);
        IMGUtils.fitCenter(this.mWinFrame, this.mFrame, 60.0f);
        this.mTargetFrame.set(this.mFrame);
    }

    public boolean homing() {
        this.mBaseFrame.set(this.mFrame);
        this.mTargetFrame.set(this.mFrame);
        IMGUtils.fitCenter(this.mWinFrame, this.mTargetFrame, 60.0f);
        boolean z = !this.mTargetFrame.equals(this.mBaseFrame);
        this.isHoming = z;
        return z;
    }

    public void homing(float f) {
        if (this.isHoming) {
            RectF rectF = this.mFrame;
            RectF rectF2 = this.mBaseFrame;
            float f2 = rectF2.left;
            RectF rectF3 = this.mTargetFrame;
            float f3 = f2 + ((rectF3.left - f2) * f);
            float f4 = rectF2.top;
            float f5 = f4 + ((rectF3.top - f4) * f);
            float f6 = rectF2.right;
            float f7 = rectF2.bottom;
            rectF.set(f3, f5, f6 + ((rectF3.right - f6) * f), f7 + ((rectF3.bottom - f7) * f));
        }
    }

    public boolean isHoming() {
        return this.isHoming;
    }

    public void setHoming(boolean z) {
        this.isHoming = z;
    }

    public boolean isClipping() {
        return this.isClipping;
    }

    public void setClipping(boolean z) {
        this.isClipping = z;
    }

    public boolean isResetting() {
        return this.isResetting;
    }

    public void setResetting(boolean z) {
        this.isResetting = z;
    }

    public RectF getFrame() {
        return this.mFrame;
    }

    public RectF getWinFrame() {
        return this.mWinFrame;
    }

    public RectF getOffsetFrame(float f, float f2) {
        RectF rectF = new RectF(this.mFrame);
        rectF.offset(f, f2);
        return rectF;
    }

    public RectF getTargetFrame() {
        return this.mTargetFrame;
    }

    public RectF getOffsetTargetFrame(float f, float f2) {
        RectF rectF = new RectF(this.mFrame);
        rectF.offset(f, f2);
        return rectF;
    }

    public boolean isShowShade() {
        return this.isShowShade;
    }

    public void setShowShade(boolean z) {
        this.isShowShade = z;
    }

    public void onDraw(Canvas canvas) {
        if (this.isResetting) {
            return;
        }
        int i = 0;
        float[] fArr = {this.mFrame.width(), this.mFrame.height()};
        for (int i2 = 0; i2 < this.mBaseSizes.length; i2++) {
            int i3 = 0;
            while (true) {
                float[][] fArr2 = this.mBaseSizes;
                if (i3 < fArr2[i2].length) {
                    fArr2[i2][i3] = fArr[i2] * IMGClip.CLIP_SIZE_RATIO[i3];
                    i3++;
                }
            }
        }
        int i4 = 0;
        while (true) {
            float[] fArr3 = this.mCells;
            if (i4 < fArr3.length) {
                fArr3[i4] = this.mBaseSizes[i4 & 1][(IMGClip.CLIP_CELL_STRIDES >>> (i4 << 1)) & 3];
                i4++;
            }
        }
        while (true) {
            float[] fArr4 = this.mCorners;
            if (i < fArr4.length) {
                fArr4[i] = this.mBaseSizes[i & 1][(IMGClip.CLIP_CORNER_STRIDES >>> i) & 1] + IMGClip.CLIP_CORNER_SIZES[IMGClip.CLIP_CORNERS[i] & 3] + IMGClip.CLIP_CORNER_STEPS[IMGClip.CLIP_CORNERS[i] >> 2];
                i++;
            } else {
                RectF rectF = this.mFrame;
                canvas.translate(rectF.left, rectF.top);
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setColor(COLOR_CELL);
                this.mPaint.setStrokeWidth(3.0f);
                canvas.drawLines(this.mCells, this.mPaint);
                RectF rectF2 = this.mFrame;
                canvas.translate(-rectF2.left, -rectF2.top);
                this.mPaint.setColor(-1);
                this.mPaint.setStrokeWidth(8.0f);
                canvas.drawRect(this.mFrame, this.mPaint);
                RectF rectF3 = this.mFrame;
                canvas.translate(rectF3.left, rectF3.top);
                this.mPaint.setColor(-1);
                this.mPaint.setStrokeWidth(14.0f);
                canvas.drawLines(this.mCorners, this.mPaint);
                return;
            }
        }
    }

    public void onDrawShade(Canvas canvas) {
        if (!this.isShowShade) {
            return;
        }
        this.mShadePath.reset();
        this.mShadePath.setFillType(Path.FillType.WINDING);
        Path path = this.mShadePath;
        RectF rectF = this.mFrame;
        path.addRect(rectF.left + 100.0f, rectF.top + 100.0f, rectF.right - 100.0f, rectF.bottom - 100.0f, Path.Direction.CW);
        this.mPaint.setColor(COLOR_SHADE);
        this.mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(this.mShadePath, this.mPaint);
    }

    public IMGClip.Anchor getAnchor(float f, float f2) {
        if (!IMGClip.Anchor.isCohesionContains(this.mFrame, -48.0f, f, f2) || IMGClip.Anchor.isCohesionContains(this.mFrame, 48.0f, f, f2)) {
            return null;
        }
        float[] cohesion = IMGClip.Anchor.cohesion(this.mFrame, 0.0f);
        float[] fArr = {f, f2};
        int i = 0;
        for (int i2 = 0; i2 < cohesion.length; i2++) {
            if (Math.abs(cohesion[i2] - fArr[i2 >> 1]) < 48.0f) {
                i |= 1 << i2;
            }
        }
        IMGClip.Anchor valueOf = IMGClip.Anchor.valueOf(i);
        if (valueOf != null) {
            this.isHoming = false;
        }
        return valueOf;
    }

    public void onScroll(IMGClip.Anchor anchor, float f, float f2) {
        anchor.move(this.mWinFrame, this.mFrame, f, f2);
    }
}
