package com.tomatolive.library.p136ui.view.widget.tagview;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

/* renamed from: com.tomatolive.library.ui.view.widget.tagview.TagView */
/* loaded from: classes4.dex */
public class TagView extends View {
    private float bdDistance;
    private float fontH;
    private float fontW;
    private boolean isExecLongClick;
    private boolean isMoved;
    private boolean isUp;
    private boolean isViewClickable;
    private boolean isViewSelectable;
    private boolean isViewSelected;
    private String mAbstractText;
    private int mBackgroundColor;
    private Bitmap mBitmapImage;
    private int mBorderColor;
    private float mBorderRadius;
    private float mBorderWidth;
    private float mCrossAreaPadding;
    private float mCrossAreaWidth;
    private int mCrossColor;
    private float mCrossLineWidth;
    private boolean mEnableCross;
    private int mHorizontalPadding;
    private int mLastX;
    private int mLastY;
    private OnTagClickListener mOnTagClickListener;
    private String mOriginText;
    private Paint mPaint;
    private Path mPath;
    private RectF mRectF;
    private int mRippleAlpha;
    private int mRippleColor;
    private Paint mRipplePaint;
    private float mRippleRadius;
    private ValueAnimator mRippleValueAnimator;
    private int mSelectedBackgroundColor;
    private int mTagMaxLength;
    private int mTextColor;
    private float mTextSize;
    private float mTouchX;
    private float mTouchY;
    private Typeface mTypeface;
    private int mVerticalPadding;
    private int mMoveSlop = 5;
    private int mSlopThreshold = 4;
    private int mLongPressTime = 500;
    private int mTextDirection = 3;
    private boolean mTagSupportLettersRTL = false;
    private int mRippleDuration = 1000;
    private boolean unSupportedClipPath = false;
    private Runnable mLongClickHandle = new Runnable() { // from class: com.tomatolive.library.ui.view.widget.tagview.TagView.1
        @Override // java.lang.Runnable
        public void run() {
            if (TagView.this.isMoved || TagView.this.isUp || ((TagContainerLayout) TagView.this.getParent()).getTagViewState() != 0) {
                return;
            }
            TagView.this.isExecLongClick = true;
            TagView.this.mOnTagClickListener.onTagLongClick(((Integer) TagView.this.getTag()).intValue(), TagView.this.getText());
        }
    };

    /* renamed from: com.tomatolive.library.ui.view.widget.tagview.TagView$OnTagClickListener */
    /* loaded from: classes4.dex */
    public interface OnTagClickListener {
        void onSelectedTagDrag(int i, String str);

        void onTagClick(int i, String str);

        void onTagCrossClick(int i);

        void onTagLongClick(int i, String str);
    }

    public TagView(Context context, String str) {
        super(context);
        init(context, str);
    }

    public TagView(Context context, String str, int i) {
        super(context);
        init(context, str);
        this.mBitmapImage = BitmapFactory.decodeResource(getResources(), i);
    }

    private void init(Context context, String str) {
        this.mPaint = new Paint(1);
        this.mRipplePaint = new Paint(1);
        this.mRipplePaint.setStyle(Paint.Style.FILL);
        this.mRectF = new RectF();
        this.mPath = new Path();
        if (str == null) {
            str = "";
        }
        this.mOriginText = str;
        this.mMoveSlop = (int) Utils.dp2px(context, this.mMoveSlop);
        this.mSlopThreshold = (int) Utils.dp2px(context, this.mSlopThreshold);
    }

    private void onDealText() {
        if (!TextUtils.isEmpty(this.mOriginText)) {
            this.mAbstractText = this.mOriginText.length() <= this.mTagMaxLength ? this.mOriginText : this.mOriginText.substring(0, this.mTagMaxLength - 3) + "...";
        } else {
            this.mAbstractText = "";
        }
        this.mPaint.setTypeface(this.mTypeface);
        this.mPaint.setTextSize(this.mTextSize);
        Paint.FontMetrics fontMetrics = this.mPaint.getFontMetrics();
        this.fontH = fontMetrics.descent - fontMetrics.ascent;
        if (this.mTextDirection == 4) {
            this.fontW = 0.0f;
            for (char c : this.mAbstractText.toCharArray()) {
                this.fontW += this.mPaint.measureText(String.valueOf(c));
            }
            return;
        }
        this.fontW = this.mPaint.measureText(this.mAbstractText);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int i3 = (this.mVerticalPadding * 2) + ((int) this.fontH);
        int i4 = 0;
        int i5 = (this.mHorizontalPadding * 2) + ((int) this.fontW) + (isEnableCross() ? i3 : 0);
        if (isEnableImage()) {
            i4 = i3;
        }
        int i6 = i5 + i4;
        this.mCrossAreaWidth = Math.min(Math.max(this.mCrossAreaWidth, i3), i6);
        setMeasuredDimension(i6, i3);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        RectF rectF = this.mRectF;
        float f = this.mBorderWidth;
        rectF.set(f, f, i - f, i2 - f);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(getIsViewSelected() ? this.mSelectedBackgroundColor : this.mBackgroundColor);
        RectF rectF = this.mRectF;
        float f = this.mBorderRadius;
        canvas.drawRoundRect(rectF, f, f, this.mPaint);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(this.mBorderWidth);
        this.mPaint.setColor(this.mBorderColor);
        RectF rectF2 = this.mRectF;
        float f2 = this.mBorderRadius;
        canvas.drawRoundRect(rectF2, f2, f2, this.mPaint);
        drawRipple(canvas);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(this.mTextColor);
        int i = 0;
        if (this.mTextDirection == 4) {
            if (this.mTagSupportLettersRTL) {
                float width = ((isEnableCross() ? getWidth() + getHeight() : getWidth()) / 2) + (this.fontW / 2.0f);
                char[] charArray = this.mAbstractText.toCharArray();
                int length = charArray.length;
                while (i < length) {
                    String valueOf = String.valueOf(charArray[i]);
                    width -= this.mPaint.measureText(valueOf);
                    canvas.drawText(valueOf, width, ((getHeight() / 2) + (this.fontH / 2.0f)) - this.bdDistance, this.mPaint);
                    i++;
                }
            } else {
                canvas.drawText(this.mAbstractText, ((isEnableCross() ? getWidth() + this.fontW : getWidth()) / 2.0f) - (this.fontW / 2.0f), ((getHeight() / 2) + (this.fontH / 2.0f)) - this.bdDistance, this.mPaint);
            }
        } else {
            String str = this.mAbstractText;
            float width2 = ((isEnableCross() ? getWidth() - getHeight() : getWidth()) / 2) - (this.fontW / 2.0f);
            if (isEnableImage()) {
                i = getHeight() / 2;
            }
            canvas.drawText(str, width2 + i, ((getHeight() / 2) + (this.fontH / 2.0f)) - this.bdDistance, this.mPaint);
        }
        drawCross(canvas);
        drawImage(canvas);
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.isViewClickable) {
            int y = (int) motionEvent.getY();
            int x = (int) motionEvent.getX();
            int action = motionEvent.getAction();
            if (action == 0) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                this.mLastY = y;
                this.mLastX = x;
            } else if (action == 2 && !this.isViewSelected && (Math.abs(this.mLastY - y) > this.mSlopThreshold || Math.abs(this.mLastX - x) > this.mSlopThreshold)) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                this.isMoved = true;
                return false;
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        OnTagClickListener onTagClickListener;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mRippleRadius = 0.0f;
            this.mTouchX = motionEvent.getX();
            this.mTouchY = motionEvent.getY();
            splashRipple();
        }
        if (isEnableCross() && isClickCrossArea(motionEvent) && (onTagClickListener = this.mOnTagClickListener) != null) {
            if (action == 1) {
                onTagClickListener.onTagCrossClick(((Integer) getTag()).intValue());
            }
            return true;
        } else if (this.isViewClickable && this.mOnTagClickListener != null) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 0) {
                this.mLastY = y;
                this.mLastX = x;
                this.isMoved = false;
                this.isUp = false;
                this.isExecLongClick = false;
                postDelayed(this.mLongClickHandle, this.mLongPressTime);
            } else if (action != 1) {
                if (action == 2 && !this.isMoved && (Math.abs(this.mLastX - x) > this.mMoveSlop || Math.abs(this.mLastY - y) > this.mMoveSlop)) {
                    this.isMoved = true;
                    if (this.isViewSelected) {
                        this.mOnTagClickListener.onSelectedTagDrag(((Integer) getTag()).intValue(), getText());
                    }
                }
            } else {
                this.isUp = true;
                if (!this.isExecLongClick && !this.isMoved) {
                    this.mOnTagClickListener.onTagClick(((Integer) getTag()).intValue(), getText());
                }
            }
            return true;
        } else {
            return super.onTouchEvent(motionEvent);
        }
    }

    private boolean isClickCrossArea(MotionEvent motionEvent) {
        return this.mTextDirection == 4 ? motionEvent.getX() <= this.mCrossAreaWidth : motionEvent.getX() >= ((float) getWidth()) - this.mCrossAreaWidth;
    }

    private void drawImage(Canvas canvas) {
        if (isEnableImage()) {
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(this.mBitmapImage, Math.round(getHeight() - this.mBorderWidth), Math.round(getHeight() - this.mBorderWidth), false);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            paint.setShader(new BitmapShader(createScaledBitmap, tileMode, tileMode));
            float f = this.mBorderWidth;
            RectF rectF = new RectF(f, f, getHeight() - this.mBorderWidth, getHeight() - this.mBorderWidth);
            canvas.drawRoundRect(rectF, rectF.height() / 2.0f, rectF.height() / 2.0f, paint);
        }
    }

    private void drawCross(Canvas canvas) {
        if (isEnableCross()) {
            this.mCrossAreaPadding = this.mCrossAreaPadding > ((float) (getHeight() / 2)) ? getHeight() / 2 : this.mCrossAreaPadding;
            int width = (int) (this.mTextDirection == 4 ? this.mCrossAreaPadding : (getWidth() - getHeight()) + this.mCrossAreaPadding);
            int i = this.mTextDirection;
            int i2 = (int) this.mCrossAreaPadding;
            int width2 = (int) (this.mTextDirection == 4 ? this.mCrossAreaPadding : (getWidth() - getHeight()) + this.mCrossAreaPadding);
            int i3 = this.mTextDirection;
            int height = (int) (getHeight() - this.mCrossAreaPadding);
            int height2 = (int) ((this.mTextDirection == 4 ? getHeight() : getWidth()) - this.mCrossAreaPadding);
            int i4 = this.mTextDirection;
            int i5 = (int) this.mCrossAreaPadding;
            int height3 = this.mTextDirection == 4 ? getHeight() : getWidth();
            int i6 = this.mTextDirection;
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setColor(this.mCrossColor);
            this.mPaint.setStrokeWidth(this.mCrossLineWidth);
            canvas.drawLine(width, i2, (int) (height3 - this.mCrossAreaPadding), (int) (getHeight() - this.mCrossAreaPadding), this.mPaint);
            canvas.drawLine(width2, height, height2, i5, this.mPaint);
        }
    }

    @TargetApi(11)
    private void drawRipple(Canvas canvas) {
        int i;
        if (!this.isViewClickable || (i = Build.VERSION.SDK_INT) < 11 || canvas == null || this.unSupportedClipPath) {
            return;
        }
        if (i < 18) {
            setLayerType(1, null);
        }
        try {
            canvas.save();
            this.mPath.reset();
            canvas.clipPath(this.mPath);
            this.mPath.addRoundRect(this.mRectF, this.mBorderRadius, this.mBorderRadius, Path.Direction.CCW);
            if (Build.VERSION.SDK_INT >= 26) {
                canvas.clipPath(this.mPath);
            } else {
                canvas.clipPath(this.mPath, Region.Op.REPLACE);
            }
            canvas.drawCircle(this.mTouchX, this.mTouchY, this.mRippleRadius, this.mRipplePaint);
            canvas.restore();
        } catch (UnsupportedOperationException unused) {
            this.unSupportedClipPath = true;
        }
    }

    @TargetApi(11)
    private void splashRipple() {
        if (Build.VERSION.SDK_INT < 11 || this.mTouchX <= 0.0f || this.mTouchY <= 0.0f) {
            return;
        }
        this.mRipplePaint.setColor(this.mRippleColor);
        this.mRipplePaint.setAlpha(this.mRippleAlpha);
        final float max = Math.max(Math.max(Math.max(this.mTouchX, this.mTouchY), Math.abs(getMeasuredWidth() - this.mTouchX)), Math.abs(getMeasuredHeight() - this.mTouchY));
        this.mRippleValueAnimator = ValueAnimator.ofFloat(0.0f, max).setDuration(this.mRippleDuration);
        this.mRippleValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.tomatolive.library.ui.view.widget.tagview.TagView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                TagView tagView = TagView.this;
                if (floatValue >= max) {
                    floatValue = 0.0f;
                }
                tagView.mRippleRadius = floatValue;
                TagView.this.postInvalidate();
            }
        });
        this.mRippleValueAnimator.start();
    }

    public String getText() {
        return this.mOriginText;
    }

    public boolean getIsViewClickable() {
        return this.isViewClickable;
    }

    public boolean getIsViewSelected() {
        return this.isViewSelected;
    }

    public void setTagMaxLength(int i) {
        this.mTagMaxLength = i;
        onDealText();
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
    }

    public int getTagBackgroundColor() {
        return this.mBackgroundColor;
    }

    public int getTagSelectedBackgroundColor() {
        return this.mSelectedBackgroundColor;
    }

    public void setTagBackgroundColor(int i) {
        this.mBackgroundColor = i;
    }

    public void setTagSelectedBackgroundColor(int i) {
        this.mSelectedBackgroundColor = i;
    }

    public void setTagBorderColor(int i) {
        this.mBorderColor = i;
    }

    public void setTagTextColor(int i) {
        this.mTextColor = i;
    }

    public void setBorderWidth(float f) {
        this.mBorderWidth = f;
    }

    public void setBorderRadius(float f) {
        this.mBorderRadius = f;
    }

    public void setTextSize(float f) {
        this.mTextSize = f;
        onDealText();
    }

    public void setHorizontalPadding(int i) {
        this.mHorizontalPadding = i;
    }

    public void setVerticalPadding(int i) {
        this.mVerticalPadding = i;
    }

    public void setIsViewClickable(boolean z) {
        this.isViewClickable = z;
    }

    public void setImage(Bitmap bitmap) {
        this.mBitmapImage = bitmap;
        invalidate();
    }

    public void setIsViewSelectable(boolean z) {
        this.isViewSelectable = z;
    }

    public void selectView() {
        if (!this.isViewSelectable || getIsViewSelected()) {
            return;
        }
        this.isViewSelected = true;
        postInvalidate();
    }

    public void deselectView() {
        if (!this.isViewSelectable || !getIsViewSelected()) {
            return;
        }
        this.isViewSelected = false;
        postInvalidate();
    }

    @Override // android.view.View
    public int getTextDirection() {
        return this.mTextDirection;
    }

    @Override // android.view.View
    public void setTextDirection(int i) {
        this.mTextDirection = i;
    }

    public void setTypeface(Typeface typeface) {
        this.mTypeface = typeface;
        onDealText();
    }

    public void setRippleAlpha(int i) {
        this.mRippleAlpha = i;
    }

    public void setRippleColor(int i) {
        this.mRippleColor = i;
    }

    public void setRippleDuration(int i) {
        this.mRippleDuration = i;
    }

    public void setBdDistance(float f) {
        this.bdDistance = f;
    }

    public boolean isEnableImage() {
        return (this.mBitmapImage == null || this.mTextDirection == 4) ? false : true;
    }

    public boolean isEnableCross() {
        return this.mEnableCross;
    }

    public void setEnableCross(boolean z) {
        this.mEnableCross = z;
    }

    public float getCrossAreaWidth() {
        return this.mCrossAreaWidth;
    }

    public void setCrossAreaWidth(float f) {
        this.mCrossAreaWidth = f;
    }

    public float getCrossLineWidth() {
        return this.mCrossLineWidth;
    }

    public void setCrossLineWidth(float f) {
        this.mCrossLineWidth = f;
    }

    public float getCrossAreaPadding() {
        return this.mCrossAreaPadding;
    }

    public void setCrossAreaPadding(float f) {
        this.mCrossAreaPadding = f;
    }

    public int getCrossColor() {
        return this.mCrossColor;
    }

    public void setCrossColor(int i) {
        this.mCrossColor = i;
    }

    public boolean isTagSupportLettersRTL() {
        return this.mTagSupportLettersRTL;
    }

    public void setTagSupportLettersRTL(boolean z) {
        this.mTagSupportLettersRTL = z;
    }
}
