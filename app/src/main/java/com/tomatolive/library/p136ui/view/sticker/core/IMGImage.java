package com.tomatolive.library.p136ui.view.sticker.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.p002v4.internal.view.SupportMenu;
import android.util.Log;
import com.tomatolive.library.p136ui.view.sticker.core.clip.IMGClip;
import com.tomatolive.library.p136ui.view.sticker.core.clip.IMGClipWindow;
import com.tomatolive.library.p136ui.view.sticker.core.homing.IMGHoming;
import com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGSticker;
import com.tomatolive.library.p136ui.view.sticker.core.util.IMGUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.IMGImage */
/* loaded from: classes3.dex */
public class IMGImage {
    private static final int COLOR_SHADE = -872415232;
    private static final boolean DEBUG = false;
    private static final Bitmap DEFAULT_IMAGE = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    private static final int MAX_SIZE = 10000;
    private static final int MIN_SIZE = 500;
    private static final String TAG = "IMGImage";

    /* renamed from: M */
    private Matrix f5851M;
    private boolean isFreezing;
    private boolean isInitialHoming;
    private IMGClip.Anchor mAnchor;
    private List<IMGSticker> mBackStickers;
    private List<IMGPath> mDoodles;
    private IMGSticker mForeSticker;
    private Bitmap mImage;
    private Bitmap mMosaicImage;
    private Paint mMosaicPaint;
    private List<IMGPath> mMosaics;
    private Paint mPaint;
    private Paint mShadePaint;
    private RectF mWindow;
    private RectF mFrame = new RectF();
    private RectF mClipFrame = new RectF();
    private RectF mTempClipFrame = new RectF();
    private RectF mBackupClipFrame = new RectF();
    private float mBackupClipRotate = 0.0f;
    private float mRotate = 0.0f;
    private float mTargetRotate = 0.0f;
    private boolean isRequestToBaseFitting = false;
    private boolean isAnimCanceled = false;
    private boolean isSteady = true;
    private Path mShade = new Path();
    private IMGClipWindow mClipWin = new IMGClipWindow();
    private boolean isDrawClip = false;
    private IMGMode mMode = IMGMode.NONE;

    public void onScaleBegin() {
    }

    public void onScaleEnd() {
    }

    public IMGImage() {
        this.isFreezing = this.mMode == IMGMode.CLIP;
        this.mWindow = new RectF();
        this.isInitialHoming = false;
        this.mBackStickers = new ArrayList();
        this.mDoodles = new ArrayList();
        this.mMosaics = new ArrayList();
        this.f5851M = new Matrix();
        this.mShade.setFillType(Path.FillType.WINDING);
        this.mPaint = new Paint(1);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(20.0f);
        this.mPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.mPaint.setPathEffect(new CornerPathEffect(20.0f));
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mImage = DEFAULT_IMAGE;
        if (this.mMode == IMGMode.CLIP) {
            initShadePaint();
        }
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.mImage = bitmap;
        Bitmap bitmap2 = this.mMosaicImage;
        if (bitmap2 != null) {
            bitmap2.recycle();
        }
        this.mMosaicImage = null;
        makeMosaicBitmap();
        onImageChanged();
    }

    public IMGMode getMode() {
        return this.mMode;
    }

    public void setMode(IMGMode iMGMode) {
        if (this.mMode == iMGMode) {
            return;
        }
        moveToBackground(this.mForeSticker);
        if (iMGMode == IMGMode.CLIP) {
            setFreezing(true);
        }
        this.mMode = iMGMode;
        IMGMode iMGMode2 = this.mMode;
        if (iMGMode2 == IMGMode.CLIP) {
            initShadePaint();
            this.mBackupClipRotate = getRotate();
            this.mBackupClipFrame.set(this.mClipFrame);
            float scale = 1.0f / getScale();
            Matrix matrix = this.f5851M;
            RectF rectF = this.mFrame;
            matrix.setTranslate(-rectF.left, -rectF.top);
            this.f5851M.postScale(scale, scale);
            this.f5851M.mapRect(this.mBackupClipFrame);
            this.mClipWin.reset(this.mClipFrame, getTargetRotate());
            return;
        }
        if (iMGMode2 == IMGMode.MOSAIC) {
            makeMosaicBitmap();
        }
        this.mClipWin.setClipping(false);
    }

    private void rotateStickers(float f) {
        this.f5851M.setRotate(f, this.mClipFrame.centerX(), this.mClipFrame.centerY());
        for (IMGSticker iMGSticker : this.mBackStickers) {
            this.f5851M.mapRect(iMGSticker.getFrame());
            iMGSticker.setRotation(iMGSticker.getRotation() + f);
            iMGSticker.setX(iMGSticker.getFrame().centerX() - iMGSticker.getPivotX());
            iMGSticker.setY(iMGSticker.getFrame().centerY() - iMGSticker.getPivotY());
        }
    }

    private void initShadePaint() {
        if (this.mShadePaint == null) {
            this.mShadePaint = new Paint(1);
            this.mShadePaint.setColor(COLOR_SHADE);
            this.mShadePaint.setStyle(Paint.Style.FILL);
        }
    }

    public boolean isMosaicEmpty() {
        return this.mMosaics.isEmpty();
    }

    public boolean isDoodleEmpty() {
        return this.mDoodles.isEmpty();
    }

    public void undoDoodle() {
        if (!this.mDoodles.isEmpty()) {
            List<IMGPath> list = this.mDoodles;
            list.remove(list.size() - 1);
        }
    }

    public void undoMosaic() {
        if (!this.mMosaics.isEmpty()) {
            List<IMGPath> list = this.mMosaics;
            list.remove(list.size() - 1);
        }
    }

    public RectF getClipFrame() {
        return this.mClipFrame;
    }

    public IMGHoming clip(float f, float f2) {
        RectF offsetFrame = this.mClipWin.getOffsetFrame(f, f2);
        this.f5851M.setRotate(-getRotate(), this.mClipFrame.centerX(), this.mClipFrame.centerY());
        this.f5851M.mapRect(this.mClipFrame, offsetFrame);
        return new IMGHoming(f + (this.mClipFrame.centerX() - offsetFrame.centerX()), f2 + (this.mClipFrame.centerY() - offsetFrame.centerY()), getScale(), getRotate());
    }

    public void toBackupClip() {
        this.f5851M.setScale(getScale(), getScale());
        Matrix matrix = this.f5851M;
        RectF rectF = this.mFrame;
        matrix.postTranslate(rectF.left, rectF.top);
        this.f5851M.mapRect(this.mClipFrame, this.mBackupClipFrame);
        setTargetRotate(this.mBackupClipRotate);
        this.isRequestToBaseFitting = true;
    }

    public void resetClip() {
        setTargetRotate(getRotate() - (getRotate() % 360.0f));
        this.mClipFrame.set(this.mFrame);
        this.mClipWin.reset(this.mClipFrame, getTargetRotate());
    }

    private void makeMosaicBitmap() {
        Bitmap bitmap;
        if (this.mMosaicImage == null && (bitmap = this.mImage) != null && this.mMode == IMGMode.MOSAIC) {
            int round = Math.round(bitmap.getWidth() / 64.0f);
            int round2 = Math.round(this.mImage.getHeight() / 64.0f);
            int max = Math.max(round, 8);
            int max2 = Math.max(round2, 8);
            if (this.mMosaicPaint == null) {
                this.mMosaicPaint = new Paint(1);
                this.mMosaicPaint.setFilterBitmap(false);
                this.mMosaicPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            }
            this.mMosaicImage = Bitmap.createScaledBitmap(this.mImage, max, max2, false);
        }
    }

    private void onImageChanged() {
        Log.i("meme", "onImageChanged...mWindow.width=" + this.mWindow.width() + " mWindow.height" + this.mWindow.height());
        this.isInitialHoming = false;
        onWindowChanged(this.mWindow.width(), this.mWindow.height());
        if (this.mMode == IMGMode.CLIP) {
            this.mClipWin.reset(this.mClipFrame, getTargetRotate());
        }
    }

    public RectF getFrame() {
        return this.mFrame;
    }

    public boolean onClipHoming() {
        return this.mClipWin.homing();
    }

    public IMGHoming getStartHoming(float f, float f2) {
        return new IMGHoming(f, f2, getScale(), getRotate());
    }

    public IMGHoming getEndHoming(float f, float f2) {
        IMGHoming iMGHoming = new IMGHoming(f, f2, getScale(), getTargetRotate());
        if (this.mMode == IMGMode.CLIP) {
            RectF rectF = new RectF(this.mClipWin.getTargetFrame());
            rectF.offset(f, f2);
            if (this.mClipWin.isResetting()) {
                RectF rectF2 = new RectF();
                this.f5851M.setRotate(getTargetRotate(), this.mClipFrame.centerX(), this.mClipFrame.centerY());
                this.f5851M.mapRect(rectF2, this.mClipFrame);
                iMGHoming.rConcat(IMGUtils.fill(rectF, rectF2));
            } else {
                RectF rectF3 = new RectF();
                if (this.mClipWin.isHoming()) {
                    this.f5851M.setRotate(getTargetRotate() - getRotate(), this.mClipFrame.centerX(), this.mClipFrame.centerY());
                    this.f5851M.mapRect(rectF3, this.mClipWin.getOffsetFrame(f, f2));
                    iMGHoming.rConcat(IMGUtils.fitHoming(rectF, rectF3, this.mClipFrame.centerX(), this.mClipFrame.centerY()));
                } else {
                    this.f5851M.setRotate(getTargetRotate(), this.mClipFrame.centerX(), this.mClipFrame.centerY());
                    this.f5851M.mapRect(rectF3, this.mFrame);
                    iMGHoming.rConcat(IMGUtils.fillHoming(rectF, rectF3, this.mClipFrame.centerX(), this.mClipFrame.centerY()));
                }
            }
        } else {
            RectF rectF4 = new RectF();
            this.f5851M.setRotate(getTargetRotate(), this.mClipFrame.centerX(), this.mClipFrame.centerY());
            this.f5851M.mapRect(rectF4, this.mClipFrame);
            RectF rectF5 = new RectF(this.mWindow);
            rectF5.offset(f, f2);
            iMGHoming.rConcat(IMGUtils.fitHoming(rectF5, rectF4, this.isRequestToBaseFitting));
            this.isRequestToBaseFitting = false;
        }
        return iMGHoming;
    }

    public <S extends IMGSticker> void addSticker(S s) {
        if (s != null) {
            moveToForeground(s);
        }
    }

    public void addPath(IMGPath iMGPath, float f, float f2) {
        if (iMGPath == null) {
            return;
        }
        float scale = 1.0f / getScale();
        this.f5851M.setTranslate(f, f2);
        this.f5851M.postRotate(-getRotate(), this.mClipFrame.centerX(), this.mClipFrame.centerY());
        Matrix matrix = this.f5851M;
        RectF rectF = this.mFrame;
        matrix.postTranslate(-rectF.left, -rectF.top);
        this.f5851M.postScale(scale, scale);
        iMGPath.transform(this.f5851M);
        int i = C48681.$SwitchMap$com$tomatolive$library$ui$view$sticker$core$IMGMode[iMGPath.getMode().ordinal()];
        if (i == 1) {
            this.mDoodles.add(iMGPath);
        } else if (i != 2) {
        } else {
            iMGPath.setWidth(iMGPath.getWidth() * scale);
            this.mMosaics.add(iMGPath);
        }
    }

    /* renamed from: com.tomatolive.library.ui.view.sticker.core.IMGImage$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C48681 {
        static final /* synthetic */ int[] $SwitchMap$com$tomatolive$library$ui$view$sticker$core$IMGMode = new int[IMGMode.values().length];

        static {
            try {
                $SwitchMap$com$tomatolive$library$ui$view$sticker$core$IMGMode[IMGMode.DOODLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$tomatolive$library$ui$view$sticker$core$IMGMode[IMGMode.MOSAIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private void moveToForeground(IMGSticker iMGSticker) {
        if (iMGSticker == null) {
            return;
        }
        moveToBackground(this.mForeSticker);
        if (iMGSticker.isShowing()) {
            this.mForeSticker = iMGSticker;
            this.mBackStickers.remove(iMGSticker);
            return;
        }
        iMGSticker.show();
    }

    private void moveToBackground(IMGSticker iMGSticker) {
        if (iMGSticker == null) {
            return;
        }
        if (!iMGSticker.isShowing()) {
            if (!this.mBackStickers.contains(iMGSticker)) {
                this.mBackStickers.add(iMGSticker);
            }
            if (this.mForeSticker != iMGSticker) {
                return;
            }
            this.mForeSticker = null;
            return;
        }
        iMGSticker.dismiss();
    }

    public void stickAll() {
        moveToBackground(this.mForeSticker);
    }

    public void onDismiss(IMGSticker iMGSticker) {
        moveToBackground(iMGSticker);
    }

    public void onShowing(IMGSticker iMGSticker) {
        if (this.mForeSticker != iMGSticker) {
            moveToForeground(iMGSticker);
        }
    }

    public void onRemoveSticker(IMGSticker iMGSticker) {
        if (this.mForeSticker == iMGSticker) {
            this.mForeSticker = null;
        } else {
            this.mBackStickers.remove(iMGSticker);
        }
    }

    public void onWindowChanged(float f, float f2) {
        Log.i("meme", "onWindowChanged: width=" + f + " height=" + f2);
        if (f == 0.0f || f2 == 0.0f) {
            return;
        }
        this.mWindow.set(0.0f, 0.0f, f, f2);
        if (!this.isInitialHoming) {
            onInitialHoming(f, f2);
        } else {
            this.f5851M.setTranslate(this.mWindow.centerX() - this.mClipFrame.centerX(), this.mWindow.centerY() - this.mClipFrame.centerY());
            this.f5851M.mapRect(this.mFrame);
            this.f5851M.mapRect(this.mClipFrame);
        }
        this.mClipWin.setClipWinSize(f, f2);
    }

    private void onInitialHoming(float f, float f2) {
        this.mFrame.set(0.0f, 0.0f, f, f2);
        Log.i("meme", "onInitialHoming: mFrame ==> width=" + f + " height=" + f2);
        this.mClipFrame.set(this.mFrame);
        this.mClipWin.setClipWinSize(f, f2);
        if (this.mClipFrame.isEmpty()) {
            return;
        }
        toBaseHoming();
        this.isInitialHoming = true;
        onInitialHomingDone();
    }

    private void toBaseHoming() {
        if (this.mClipFrame.isEmpty()) {
            return;
        }
        float min = Math.min(this.mWindow.width() / this.mClipFrame.width(), this.mWindow.height() / this.mClipFrame.height());
        this.f5851M.setScale(min, min, this.mClipFrame.centerX(), this.mClipFrame.centerY());
        this.f5851M.postTranslate(this.mWindow.centerX() - this.mClipFrame.centerX(), this.mWindow.centerY() - this.mClipFrame.centerY());
        this.f5851M.mapRect(this.mFrame);
        this.f5851M.mapRect(this.mClipFrame);
    }

    private void onInitialHomingDone() {
        if (this.mMode == IMGMode.CLIP) {
            this.mClipWin.reset(this.mClipFrame, getTargetRotate());
        }
    }

    public void onDrawImage(Canvas canvas) {
        canvas.clipRect(this.mClipWin.isClipping() ? this.mFrame : this.mClipFrame);
        canvas.drawBitmap(this.mImage, (Rect) null, this.mFrame, (Paint) null);
    }

    public int onDrawMosaicsPath(Canvas canvas) {
        int saveLayer = canvas.saveLayer(this.mFrame, null, 31);
        if (!isMosaicEmpty()) {
            canvas.save();
            float scale = getScale();
            RectF rectF = this.mFrame;
            canvas.translate(rectF.left, rectF.top);
            canvas.scale(scale, scale);
            for (IMGPath iMGPath : this.mMosaics) {
                iMGPath.onDrawMosaic(canvas, this.mPaint);
            }
            canvas.restore();
        }
        return saveLayer;
    }

    public void onDrawMosaic(Canvas canvas, int i) {
        canvas.drawBitmap(this.mMosaicImage, (Rect) null, this.mFrame, this.mMosaicPaint);
        canvas.restoreToCount(i);
    }

    public void onDrawDoodles(Canvas canvas) {
        if (!isDoodleEmpty()) {
            canvas.save();
            float scale = getScale();
            RectF rectF = this.mFrame;
            canvas.translate(rectF.left, rectF.top);
            canvas.scale(scale, scale);
            for (IMGPath iMGPath : this.mDoodles) {
                iMGPath.onDrawDoodle(canvas, this.mPaint);
            }
            canvas.restore();
        }
    }

    public void onDrawStickerClip(Canvas canvas) {
        this.f5851M.setRotate(getRotate(), this.mClipFrame.centerX(), this.mClipFrame.centerY());
        this.f5851M.mapRect(this.mTempClipFrame, this.mClipWin.isClipping() ? this.mFrame : this.mClipFrame);
        canvas.clipRect(this.mTempClipFrame);
    }

    public void onDrawStickers(Canvas canvas) {
        if (this.mBackStickers.isEmpty()) {
            return;
        }
        canvas.save();
        for (IMGSticker iMGSticker : this.mBackStickers) {
            if (!iMGSticker.isShowing()) {
                float x = iMGSticker.getX() + iMGSticker.getPivotX();
                float y = iMGSticker.getY() + iMGSticker.getPivotY();
                canvas.save();
                this.f5851M.setTranslate(iMGSticker.getX(), iMGSticker.getY());
                this.f5851M.postScale(iMGSticker.getScale(), iMGSticker.getScale(), x, y);
                this.f5851M.postRotate(iMGSticker.getRotation(), x, y);
                canvas.concat(this.f5851M);
                iMGSticker.onSticker(canvas);
                canvas.restore();
            }
        }
        canvas.restore();
    }

    public void onDrawShade(Canvas canvas) {
        if (this.mMode != IMGMode.CLIP || !this.isSteady) {
            return;
        }
        this.mShade.reset();
        Path path = this.mShade;
        RectF rectF = this.mFrame;
        path.addRect(rectF.left - 2.0f, rectF.top - 2.0f, rectF.right + 2.0f, rectF.bottom + 2.0f, Path.Direction.CW);
        this.mShade.addRect(this.mClipFrame, Path.Direction.CCW);
        canvas.drawPath(this.mShade, this.mShadePaint);
    }

    public void onDrawClip(Canvas canvas, float f, float f2) {
        if (this.mMode == IMGMode.CLIP) {
            this.mClipWin.onDraw(canvas);
        }
    }

    public void onTouchDown(float f, float f2) {
        this.isSteady = false;
        moveToBackground(this.mForeSticker);
        if (this.mMode == IMGMode.CLIP) {
            this.mAnchor = this.mClipWin.getAnchor(f, f2);
        }
    }

    public void onTouchUp(float f, float f2) {
        if (this.mAnchor != null) {
            this.mAnchor = null;
        }
    }

    public void onSteady(float f, float f2) {
        this.isSteady = true;
        onClipHoming();
        this.mClipWin.setShowShade(true);
    }

    public IMGHoming onScroll(float f, float f2, float f3, float f4) {
        if (this.mMode == IMGMode.CLIP) {
            this.mClipWin.setShowShade(false);
            IMGClip.Anchor anchor = this.mAnchor;
            if (anchor == null) {
                return null;
            }
            this.mClipWin.onScroll(anchor, f3, f4);
            RectF rectF = new RectF();
            this.f5851M.setRotate(getRotate(), this.mClipFrame.centerX(), this.mClipFrame.centerY());
            this.f5851M.mapRect(rectF, this.mFrame);
            RectF offsetFrame = this.mClipWin.getOffsetFrame(f, f2);
            IMGHoming iMGHoming = new IMGHoming(f, f2, getScale(), getTargetRotate());
            iMGHoming.rConcat(IMGUtils.fillHoming(offsetFrame, rectF, this.mClipFrame.centerX(), this.mClipFrame.centerY()));
            return iMGHoming;
        }
        return null;
    }

    public float getTargetRotate() {
        return this.mTargetRotate;
    }

    public void setTargetRotate(float f) {
        this.mTargetRotate = f;
    }

    public void rotate(int i) {
        this.mTargetRotate = Math.round((this.mRotate + i) / 90.0f) * 90;
        this.mClipWin.reset(this.mClipFrame, getTargetRotate());
    }

    public float getRotate() {
        return this.mRotate;
    }

    public void setRotate(float f) {
        this.mRotate = f;
    }

    public float getScale() {
        return (this.mFrame.width() * 1.0f) / this.mImage.getWidth();
    }

    public void setScale(float f) {
        setScale(f, this.mClipFrame.centerX(), this.mClipFrame.centerY());
    }

    public void setScale(float f, float f2, float f3) {
        onScale(f / getScale(), f2, f3);
    }

    public void onScale(float f, float f2, float f3) {
        if (f == 1.0f) {
            return;
        }
        if (Math.max(this.mClipFrame.width(), this.mClipFrame.height()) >= 10000.0f || Math.min(this.mClipFrame.width(), this.mClipFrame.height()) <= 500.0f) {
            f += (1.0f - f) / 2.0f;
        }
        this.f5851M.setScale(f, f, f2, f3);
        this.f5851M.mapRect(this.mFrame);
        this.f5851M.mapRect(this.mClipFrame);
        this.mFrame.contains(this.mClipFrame);
        for (IMGSticker iMGSticker : this.mBackStickers) {
            this.f5851M.mapRect(iMGSticker.getFrame());
            float x = iMGSticker.getX() + iMGSticker.getPivotX();
            float y = iMGSticker.getY() + iMGSticker.getPivotY();
            iMGSticker.addScale(f);
            iMGSticker.setX((iMGSticker.getX() + iMGSticker.getFrame().centerX()) - x);
            iMGSticker.setY((iMGSticker.getY() + iMGSticker.getFrame().centerY()) - y);
        }
    }

    public void onHomingStart(boolean z) {
        this.isAnimCanceled = false;
        this.isDrawClip = true;
    }

    public void onHoming(float f) {
        this.mClipWin.homing(f);
    }

    public boolean onHomingEnd(float f, float f2, boolean z) {
        this.isDrawClip = true;
        if (this.mMode == IMGMode.CLIP) {
            boolean z2 = !this.isAnimCanceled;
            this.mClipWin.setHoming(false);
            this.mClipWin.setClipping(true);
            this.mClipWin.setResetting(false);
            return z2;
        }
        if (this.isFreezing && !this.isAnimCanceled) {
            setFreezing(false);
        }
        return false;
    }

    public boolean isFreezing() {
        return this.isFreezing;
    }

    private void setFreezing(boolean z) {
        if (z != this.isFreezing) {
            rotateStickers(z ? -getRotate() : getTargetRotate());
            this.isFreezing = z;
        }
    }

    public void onHomingCancel(boolean z) {
        this.isAnimCanceled = true;
        Log.d(TAG, "Homing cancel");
    }

    public void release() {
        Bitmap bitmap = this.mImage;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.mImage.recycle();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        Bitmap bitmap = DEFAULT_IMAGE;
        if (bitmap != null) {
            bitmap.recycle();
        }
    }
}
