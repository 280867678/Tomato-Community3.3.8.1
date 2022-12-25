package com.tomatolive.library.p136ui.view.sticker.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.p002v4.internal.view.SupportMenu;
import android.support.p002v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.tomatolive.library.model.p135db.StickerEntity;
import com.tomatolive.library.p136ui.view.sticker.core.IMGImage;
import com.tomatolive.library.p136ui.view.sticker.core.IMGMode;
import com.tomatolive.library.p136ui.view.sticker.core.IMGPath;
import com.tomatolive.library.p136ui.view.sticker.core.IMGText;
import com.tomatolive.library.p136ui.view.sticker.core.anim.IMGHomingAnimator;
import com.tomatolive.library.p136ui.view.sticker.core.homing.IMGHoming;
import com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGSticker;
import com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait;

/* renamed from: com.tomatolive.library.ui.view.sticker.view.IMGView */
/* loaded from: classes3.dex */
public class IMGView extends FrameLayout implements Runnable, ScaleGestureDetector.OnScaleGestureListener, ValueAnimator.AnimatorUpdateListener, IMGStickerPortrait.Callback, Animator.AnimatorListener {
    private static final boolean DEBUG = true;
    private static final String TAG = "IMGView";
    private Paint mDoodlePaint;
    private IMGHomingAnimator mHomingAnimator;
    private IMGImage mImage;
    private Paint mMosaicPaint;
    private Pen mPen;
    private int mPointerCount;
    private IMGMode mPreMode;

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
    }

    public IMGView(Context context) {
        this(context, null, 0);
    }

    public IMGView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public IMGView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPreMode = IMGMode.NONE;
        this.mImage = new IMGImage();
        this.mPen = new Pen();
        this.mPointerCount = 0;
        this.mDoodlePaint = new Paint(1);
        this.mMosaicPaint = new Paint(1);
        this.mDoodlePaint.setStyle(Paint.Style.STROKE);
        this.mDoodlePaint.setStrokeWidth(20.0f);
        this.mDoodlePaint.setColor(SupportMenu.CATEGORY_MASK);
        this.mDoodlePaint.setPathEffect(new CornerPathEffect(20.0f));
        this.mDoodlePaint.setStrokeCap(Paint.Cap.ROUND);
        this.mDoodlePaint.setStrokeJoin(Paint.Join.ROUND);
        this.mMosaicPaint.setStyle(Paint.Style.STROKE);
        this.mMosaicPaint.setStrokeWidth(72.0f);
        this.mMosaicPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mMosaicPaint.setPathEffect(new CornerPathEffect(72.0f));
        this.mMosaicPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mMosaicPaint.setStrokeJoin(Paint.Join.ROUND);
        initialize(context);
    }

    private void initialize(Context context) {
        this.mPen.setMode(this.mImage.getMode());
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mImage.setBitmap(bitmap);
        invalidate();
    }

    public void setMode(IMGMode iMGMode) {
        this.mPreMode = this.mImage.getMode();
        this.mImage.setMode(iMGMode);
        this.mPen.setMode(iMGMode);
        onHoming();
    }

    boolean isHoming() {
        IMGHomingAnimator iMGHomingAnimator = this.mHomingAnimator;
        return iMGHomingAnimator != null && iMGHomingAnimator.isRunning();
    }

    private void onHoming() {
        invalidate();
        stopHoming();
        startHoming(this.mImage.getStartHoming(getScrollX(), getScrollY()), this.mImage.getEndHoming(getScrollX(), getScrollY()));
    }

    private void startHoming(IMGHoming iMGHoming, IMGHoming iMGHoming2) {
        if (this.mHomingAnimator == null) {
            this.mHomingAnimator = new IMGHomingAnimator();
            this.mHomingAnimator.addUpdateListener(this);
            this.mHomingAnimator.addListener(this);
        }
        this.mHomingAnimator.setHomingValues(iMGHoming, iMGHoming2);
        this.mHomingAnimator.start();
    }

    private void stopHoming() {
        IMGHomingAnimator iMGHomingAnimator = this.mHomingAnimator;
        if (iMGHomingAnimator != null) {
            iMGHomingAnimator.cancel();
        }
    }

    public void doRotate() {
        if (!isHoming()) {
            this.mImage.rotate(-90);
            onHoming();
        }
    }

    public void resetClip() {
        this.mImage.resetClip();
        onHoming();
    }

    public void doClip() {
        this.mImage.clip(getScrollX(), getScrollY());
        setMode(this.mPreMode);
        onHoming();
    }

    public void cancelClip() {
        this.mImage.toBackupClip();
        setMode(this.mPreMode);
    }

    public void setPenColor(int i) {
        this.mPen.setColor(i);
    }

    public boolean isDoodleEmpty() {
        return this.mImage.isDoodleEmpty();
    }

    public void undoDoodle() {
        this.mImage.undoDoodle();
        invalidate();
    }

    public boolean isMosaicEmpty() {
        return this.mImage.isMosaicEmpty();
    }

    public void undoMosaic() {
        this.mImage.undoMosaic();
        invalidate();
    }

    public IMGMode getMode() {
        return this.mImage.getMode();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        onDrawImages(canvas);
    }

    private void onDrawImages(Canvas canvas) {
        canvas.save();
        RectF clipFrame = this.mImage.getClipFrame();
        canvas.rotate(this.mImage.getRotate(), clipFrame.centerX(), clipFrame.centerY());
        if (this.mImage.isFreezing()) {
            this.mImage.onDrawStickers(canvas);
        }
        this.mImage.onDrawShade(canvas);
        canvas.restore();
        if (!this.mImage.isFreezing()) {
            this.mImage.onDrawStickerClip(canvas);
            this.mImage.onDrawStickers(canvas);
        }
        if (this.mImage.getMode() == IMGMode.CLIP) {
            canvas.save();
            canvas.translate(getScrollX(), getScrollY());
            this.mImage.onDrawClip(canvas, getScrollX(), getScrollY());
            canvas.restore();
        }
    }

    public Bitmap saveBitmap() {
        this.mImage.stickAll();
        this.mImage.getScale();
        RectF rectF = new RectF(this.mImage.getClipFrame());
        Matrix matrix = new Matrix();
        matrix.setRotate(this.mImage.getRotate(), rectF.centerX(), rectF.centerY());
        matrix.mapRect(rectF);
        matrix.mapRect(rectF);
        Bitmap createBitmap = Bitmap.createBitmap(Math.round(rectF.width()), Math.round(rectF.height()), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.translate(-rectF.left, -rectF.top);
        onDrawImages(canvas);
        return createBitmap;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            this.mImage.onWindowChanged(i3 - i, i4 - i2);
        }
    }

    public <V extends View & IMGSticker> void addStickerView(V v, FrameLayout.LayoutParams layoutParams) {
        if (v != null) {
            addView(v, layoutParams);
            ((IMGStickerPortrait) v).registerCallback(this);
            this.mImage.addSticker(v);
        }
    }

    public void addStickerText(IMGText iMGText) {
        IMGStickerTextView iMGStickerTextView = new IMGStickerTextView(getContext());
        iMGStickerTextView.setText(iMGText);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        iMGStickerTextView.setX(getScrollX());
        iMGStickerTextView.setY(getScrollY());
        addStickerView(iMGStickerTextView, layoutParams);
    }

    public void addLastStickerText(IMGText iMGText, final StickerEntity stickerEntity) {
        final IMGStickerTextView iMGStickerTextView = new IMGStickerTextView(getContext());
        iMGStickerTextView.setText(iMGText);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        iMGStickerTextView.setX(getScrollX());
        iMGStickerTextView.setY(getScrollY());
        addStickerView(iMGStickerTextView, layoutParams);
        iMGStickerTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.tomatolive.library.ui.view.sticker.view.IMGView.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                iMGStickerTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                iMGStickerTextView.setTranslationX(stickerEntity.translationX);
                iMGStickerTextView.setTranslationY(stickerEntity.translationY);
                iMGStickerTextView.addScale(stickerEntity.scale);
            }
        });
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            return onInterceptTouch(motionEvent) || super.onInterceptTouchEvent(motionEvent);
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    boolean onInterceptTouch(MotionEvent motionEvent) {
        if (!isHoming()) {
            return this.mImage.getMode() == IMGMode.CLIP;
        }
        stopHoming();
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            removeCallbacks(this);
        } else if (actionMasked == 1 || actionMasked == 3) {
            postDelayed(this, 1200L);
        }
        return onTouch(motionEvent);
    }

    boolean onTouch(MotionEvent motionEvent) {
        if (isHoming()) {
            return false;
        }
        this.mPointerCount = motionEvent.getPointerCount();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mImage.onTouchDown(motionEvent.getX(), motionEvent.getY());
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.mImage.onTouchUp(getScrollX(), getScrollY());
            onHoming();
        }
        return true;
    }

    private boolean onTouchPath(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    return onPathMove(motionEvent);
                }
                if (actionMasked != 3) {
                    return false;
                }
            }
            return this.mPen.isIdentity(motionEvent.getPointerId(0)) && onPathDone();
        }
        return onPathBegin(motionEvent);
    }

    private boolean onPathBegin(MotionEvent motionEvent) {
        this.mPen.reset(motionEvent.getX(), motionEvent.getY());
        this.mPen.setIdentity(motionEvent.getPointerId(0));
        return true;
    }

    private boolean onPathMove(MotionEvent motionEvent) {
        if (this.mPen.isIdentity(motionEvent.getPointerId(0))) {
            this.mPen.lineTo(motionEvent.getX(), motionEvent.getY());
            invalidate();
            return true;
        }
        return false;
    }

    private boolean onPathDone() {
        if (this.mPen.isEmpty()) {
            return false;
        }
        this.mImage.addPath(this.mPen.toPath(), getScrollX(), getScrollY());
        this.mPen.reset();
        invalidate();
        return true;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!onSteady()) {
            postDelayed(this, 500L);
        }
    }

    boolean onSteady() {
        Log.d(TAG, "onSteady: isHoming=" + isHoming());
        if (!isHoming()) {
            this.mImage.onSteady(getScrollX(), getScrollY());
            onHoming();
            return true;
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this);
        this.mImage.release();
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        if (this.mPointerCount > 1) {
            this.mImage.onScale(scaleGestureDetector.getScaleFactor(), getScrollX() + scaleGestureDetector.getFocusX(), getScrollY() + scaleGestureDetector.getFocusY());
            invalidate();
            return true;
        }
        return false;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        if (this.mPointerCount > 1) {
            this.mImage.onScaleBegin();
            return true;
        }
        return false;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        this.mImage.onScaleEnd();
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.mImage.onHoming(valueAnimator.getAnimatedFraction());
        toApplyHoming((IMGHoming) valueAnimator.getAnimatedValue());
    }

    private void toApplyHoming(IMGHoming iMGHoming) {
        this.mImage.setScale(iMGHoming.scale);
        this.mImage.setRotate(iMGHoming.rotate);
        if (!onScrollTo(Math.round(iMGHoming.f5856x), Math.round(iMGHoming.f5857y))) {
            invalidate();
        }
    }

    private boolean onScrollTo(int i, int i2) {
        if (getScrollX() == i && getScrollY() == i2) {
            return false;
        }
        scrollTo(i, i2);
        return true;
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait.Callback
    public <V extends View & IMGSticker> void onDismiss(V v) {
        this.mImage.onDismiss(v);
        invalidate();
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait.Callback
    public <V extends View & IMGSticker> void onShowing(V v) {
        this.mImage.onShowing(v);
        invalidate();
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait.Callback
    public <V extends View & IMGSticker> boolean onRemove(V v) {
        IMGImage iMGImage = this.mImage;
        if (iMGImage != null) {
            iMGImage.onRemoveSticker(v);
        }
        ((IMGStickerPortrait) v).unregisterCallback(this);
        ViewParent parent = v.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(v);
            return true;
        }
        return true;
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.mImage.onHomingStart(this.mHomingAnimator.isRotate());
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        if (this.mImage.onHomingEnd(getScrollX(), getScrollY(), this.mHomingAnimator.isRotate())) {
            toApplyHoming(this.mImage.clip(getScrollX(), getScrollY()));
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        this.mImage.onHomingCancel(this.mHomingAnimator.isRotate());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onScroll(float f, float f2) {
        IMGHoming onScroll = this.mImage.onScroll(getScrollX(), getScrollY(), -f, -f2);
        if (onScroll != null) {
            toApplyHoming(onScroll);
            return true;
        }
        return onScrollTo(getScrollX() + Math.round(f), getScrollY() + Math.round(f2));
    }

    /* renamed from: com.tomatolive.library.ui.view.sticker.view.IMGView$MoveAdapter */
    /* loaded from: classes3.dex */
    private class MoveAdapter extends GestureDetector.SimpleOnGestureListener {
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        private MoveAdapter() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return IMGView.this.onScroll(f, f2);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return super.onFling(motionEvent, motionEvent2, f, f2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.sticker.view.IMGView$Pen */
    /* loaded from: classes3.dex */
    public static class Pen extends IMGPath {
        private int identity;

        private Pen() {
            this.identity = Integer.MIN_VALUE;
        }

        void reset() {
            this.path.reset();
            this.identity = Integer.MIN_VALUE;
        }

        void reset(float f, float f2) {
            this.path.reset();
            this.path.moveTo(f, f2);
            this.identity = Integer.MIN_VALUE;
        }

        void setIdentity(int i) {
            this.identity = i;
        }

        boolean isIdentity(int i) {
            return this.identity == i;
        }

        void lineTo(float f, float f2) {
            this.path.lineTo(f, f2);
        }

        boolean isEmpty() {
            return this.path.isEmpty();
        }

        IMGPath toPath() {
            return new IMGPath(new Path(this.path), getMode(), getColor(), getWidth());
        }
    }
}
