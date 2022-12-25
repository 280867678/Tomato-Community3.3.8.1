package com.tomatolive.library.p136ui.view.sticker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGSticker;
import com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerAdjustHelper;
import com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerHelper;
import com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerMoveHelper;
import com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait;

/* renamed from: com.tomatolive.library.ui.view.sticker.view.IMGStickerView */
/* loaded from: classes3.dex */
public abstract class IMGStickerView extends ViewGroup implements IMGSticker, View.OnClickListener {
    private static final int ANCHOR_SIZE = 38;
    private static final int ANCHOR_SIZE_HALF = 19;
    private static final float MAX_SCALE_VALUE = 4.0f;
    private static final float STROKE_WIDTH = 1.0f;
    private static final String TAG = "IMGStickerView";
    private Paint PAINT;
    private ImageView mAdjustView;
    private View mContentView;
    private int mDownShowing;
    private RectF mFrame;
    private Matrix mMatrix;
    private float mMaxScaleValue;
    private IMGStickerMoveHelper mMoveHelper;
    private ImageView mRemoveView;
    private float mScale;
    private IMGStickerHelper<IMGStickerView> mStickerHelper;
    private Rect mTempFrame;

    public void onContentTap() {
    }

    public abstract View onCreateContentView(Context context);

    public IMGStickerView(Context context) {
        this(context, null, 0);
    }

    public IMGStickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public IMGStickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mScale = STROKE_WIDTH;
        this.mDownShowing = 0;
        this.mMaxScaleValue = MAX_SCALE_VALUE;
        this.mMatrix = new Matrix();
        this.mFrame = new RectF();
        this.mTempFrame = new Rect();
        this.PAINT = new Paint(1);
        this.PAINT.setColor(-1);
        this.PAINT.setStyle(Paint.Style.STROKE);
        this.PAINT.setStrokeWidth(STROKE_WIDTH);
        onInitialize(context);
    }

    public void onInitialize(Context context) {
        setBackgroundColor(0);
        this.mContentView = onCreateContentView(context);
        addView(this.mContentView, getContentLayoutParams());
        this.mRemoveView = new ImageView(context);
        this.mRemoveView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.mRemoveView.setImageResource(R$drawable.fq_icons_fork_4);
        addView(this.mRemoveView, getAnchorLayoutParams());
        this.mRemoveView.setOnClickListener(this);
        this.mAdjustView = new ImageView(context);
        this.mAdjustView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.mAdjustView.setImageResource(R$drawable.fq_icon_scaling);
        addView(this.mAdjustView, getAnchorLayoutParams());
        new IMGStickerAdjustHelper(this, this.mAdjustView);
        this.mStickerHelper = new IMGStickerHelper<>(this);
        this.mMoveHelper = new IMGStickerMoveHelper(this);
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.IMGViewPortrait
    public float getScale() {
        return this.mScale;
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.IMGViewPortrait
    public void setScale(float f) {
        if (f > 1.2f || f < 0.9f) {
            return;
        }
        this.mScale = f;
        float left = (getLeft() + getRight()) >> 1;
        float top2 = (getTop() + getBottom()) >> 1;
        this.mFrame.set(left, top2, left, top2);
        this.mFrame.inset(-(this.mContentView.getMeasuredWidth() >> 1), -(this.mContentView.getMeasuredHeight() >> 1));
        Matrix matrix = this.mMatrix;
        float f2 = this.mScale;
        matrix.setScale(f2, f2, this.mFrame.centerX(), this.mFrame.centerY());
        this.mMatrix.mapRect(this.mFrame);
        this.mFrame.round(this.mTempFrame);
        this.mContentView.setScaleX(this.mScale);
        this.mContentView.setScaleY(this.mScale);
        Rect rect = this.mTempFrame;
        layout(rect.left, rect.top, rect.right, rect.bottom);
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.IMGViewPortrait
    public void addScale(float f) {
        setScale(getScale() * f);
    }

    private ViewGroup.LayoutParams getContentLayoutParams() {
        return new ViewGroup.LayoutParams(-2, -2);
    }

    private ViewGroup.LayoutParams getAnchorLayoutParams() {
        return new ViewGroup.LayoutParams(38, 38);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (isShowing()) {
            canvas.drawRect(19.0f, 19.0f, getWidth() - 19, getHeight() - 19, this.PAINT);
        }
        super.draw(canvas);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                childAt.measure(i, i2);
                i4 = Math.round(Math.max(i4, childAt.getMeasuredWidth() * childAt.getScaleX()));
                i3 = Math.round(Math.max(i3, childAt.getMeasuredHeight() * childAt.getScaleY()));
                i5 = ViewGroup.combineMeasuredStates(i5, childAt.getMeasuredState());
            }
        }
        setMeasuredDimension(ViewGroup.resolveSizeAndState(Math.max(i4, getSuggestedMinimumWidth()), i, i5), ViewGroup.resolveSizeAndState(Math.max(i3, getSuggestedMinimumHeight()), i2, i5 << 16));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mFrame.set(i, i2, i3, i4);
        if (getChildCount() == 0) {
            return;
        }
        ImageView imageView = this.mRemoveView;
        imageView.layout(0, 0, imageView.getMeasuredWidth(), this.mRemoveView.getMeasuredHeight());
        ImageView imageView2 = this.mAdjustView;
        int i5 = i3 - i;
        int i6 = i4 - i2;
        imageView2.layout(i5 - imageView2.getMeasuredWidth(), i6 - this.mAdjustView.getMeasuredHeight(), i5, i6);
        int i7 = i5 >> 1;
        int i8 = i6 >> 1;
        int measuredWidth = this.mContentView.getMeasuredWidth() >> 1;
        int measuredHeight = this.mContentView.getMeasuredHeight() >> 1;
        this.mContentView.layout(i7 - measuredWidth, i8 - measuredHeight, i7 + measuredWidth, i8 + measuredHeight);
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        return isShowing() && super.drawChild(canvas, view, j);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (isShowing() || motionEvent.getAction() != 0) {
            return isShowing() && super.onInterceptTouchEvent(motionEvent);
        }
        this.mDownShowing = 0;
        show();
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouch = this.mMoveHelper.onTouch(this, motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mDownShowing++;
        } else if (actionMasked == 1 && this.mDownShowing > 1 && motionEvent.getEventTime() - motionEvent.getDownTime() < ViewConfiguration.getTapTimeout()) {
            onContentTap();
            return true;
        }
        return onTouch || super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mRemoveView) {
            onRemove();
        }
    }

    public void onRemove() {
        this.mStickerHelper.remove();
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public boolean show() {
        return this.mStickerHelper.show();
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public boolean remove() {
        return this.mStickerHelper.remove();
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public boolean dismiss() {
        return this.mStickerHelper.dismiss();
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public boolean isShowing() {
        return this.mStickerHelper.isShowing();
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public RectF getFrame() {
        return this.mStickerHelper.getFrame();
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public void onSticker(Canvas canvas) {
        canvas.translate(this.mContentView.getX(), this.mContentView.getY());
        this.mContentView.draw(canvas);
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public void registerCallback(IMGStickerPortrait.Callback callback) {
        this.mStickerHelper.registerCallback(callback);
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.core.sticker.IMGStickerPortrait
    public void unregisterCallback(IMGStickerPortrait.Callback callback) {
        this.mStickerHelper.unregisterCallback(callback);
    }
}
