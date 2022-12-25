package com.gen.p059mh.webapp_extensions.views.wheelView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import com.gen.p059mh.webapp_extensions.R$array;
import com.gen.p059mh.webapp_extensions.R$dimen;
import com.gen.p059mh.webapp_extensions.R$styleable;
import com.gen.p059mh.webapps.utils.Utils;
import java.util.Arrays;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.views.wheelView.WheelPicker */
/* loaded from: classes2.dex */
public class WheelPicker extends View implements Runnable {
    private static final String TAG = WheelPicker.class.getSimpleName();
    private String fontPath;
    private boolean hasAtmospheric;
    private boolean hasCurtain;
    private boolean hasIndicator;
    private boolean hasSameWidth;
    private boolean isClick;
    private boolean isCurved;
    private boolean isCyclic;
    private boolean isDebug;
    private boolean isForceFinishScroll;
    private boolean isTouchTriggered;
    private Camera mCamera;
    private int mCurrentItemPosition;
    private int mCurtainColor;
    private List mData;
    private int mDownPointY;
    private int mDrawnCenterX;
    private int mDrawnCenterY;
    private int mDrawnItemCount;
    private int mHalfDrawnItemCount;
    private int mHalfItemHeight;
    private int mHalfWheelHeight;
    private final Handler mHandler;
    private int mIndicatorColor;
    private int mIndicatorSize;
    private int mItemAlign;
    private int mItemHeight;
    private int mItemSpace;
    private int mItemTextColor;
    private int mItemTextSize;
    private int mLastPointY;
    private Matrix mMatrixDepth;
    private Matrix mMatrixRotate;
    private int mMaxFlingY;
    private String mMaxWidthText;
    private int mMaximumVelocity;
    private int mMinFlingY;
    private int mMinimumVelocity;
    private OnItemSelectedListener mOnItemSelectedListener;
    private OnWheelChangeListener mOnWheelChangeListener;
    private Paint mPaint;
    private Rect mRectCurrentItem;
    private Rect mRectDrawn;
    private Rect mRectIndicatorFoot;
    private Rect mRectIndicatorHead;
    private int mScrollOffsetY;
    private Scroller mScroller;
    private int mSelectedItemPosition;
    private int mSelectedItemTextColor;
    private int mTextMaxHeight;
    private int mTextMaxWidth;
    private int mTextMaxWidthPosition;
    private int mTouchSlop;
    private VelocityTracker mTracker;
    private int mVisibleItemCount;
    private int mWheelCenterX;
    private int mWheelCenterY;

    /* renamed from: com.gen.mh.webapp_extensions.views.wheelView.WheelPicker$OnItemSelectedListener */
    /* loaded from: classes2.dex */
    public interface OnItemSelectedListener {
        void onItemSelected(WheelPicker wheelPicker, Object obj, int i);
    }

    /* renamed from: com.gen.mh.webapp_extensions.views.wheelView.WheelPicker$OnWheelChangeListener */
    /* loaded from: classes2.dex */
    public interface OnWheelChangeListener {
        void onWheelScrollStateChanged(int i);

        void onWheelScrolled(int i);

        void onWheelSelected(int i);
    }

    public WheelPicker(Context context) {
        this(context, null);
    }

    public WheelPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHandler = new Handler();
        this.mMinimumVelocity = 50;
        this.mMaximumVelocity = 8000;
        this.mTouchSlop = 8;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.WheelPicker);
        int resourceId = obtainStyledAttributes.getResourceId(R$styleable.WheelPicker_wheel_data, 0);
        this.mData = Arrays.asList(getResources().getStringArray(resourceId == 0 ? R$array.WheelArrayDefault : resourceId));
        this.mItemTextSize = obtainStyledAttributes.getDimensionPixelSize(R$styleable.WheelPicker_wheel_item_text_size, getResources().getDimensionPixelSize(R$dimen.WheelItemTextSize));
        this.mVisibleItemCount = obtainStyledAttributes.getInt(R$styleable.WheelPicker_wheel_visible_item_count, 7);
        this.mSelectedItemPosition = obtainStyledAttributes.getInt(R$styleable.WheelPicker_wheel_selected_item_position, 0);
        this.hasSameWidth = obtainStyledAttributes.getBoolean(R$styleable.WheelPicker_wheel_same_width, false);
        this.mTextMaxWidthPosition = obtainStyledAttributes.getInt(R$styleable.WheelPicker_wheel_maximum_width_text_position, -1);
        this.mMaxWidthText = obtainStyledAttributes.getString(R$styleable.WheelPicker_wheel_maximum_width_text);
        this.mSelectedItemTextColor = obtainStyledAttributes.getColor(R$styleable.WheelPicker_wheel_selected_item_text_color, -1);
        this.mItemTextColor = obtainStyledAttributes.getColor(R$styleable.WheelPicker_wheel_item_text_color, -7829368);
        this.mItemSpace = obtainStyledAttributes.getDimensionPixelSize(R$styleable.WheelPicker_wheel_item_space, getResources().getDimensionPixelSize(R$dimen.WheelItemSpace));
        this.isCyclic = obtainStyledAttributes.getBoolean(R$styleable.WheelPicker_wheel_cyclic, false);
        this.hasIndicator = obtainStyledAttributes.getBoolean(R$styleable.WheelPicker_wheel_indicator, false);
        this.mIndicatorColor = obtainStyledAttributes.getColor(R$styleable.WheelPicker_wheel_indicator_color, -1166541);
        this.mIndicatorSize = obtainStyledAttributes.getDimensionPixelSize(R$styleable.WheelPicker_wheel_indicator_size, getResources().getDimensionPixelSize(R$dimen.WheelIndicatorSize));
        this.hasCurtain = obtainStyledAttributes.getBoolean(R$styleable.WheelPicker_wheel_curtain, false);
        this.mCurtainColor = obtainStyledAttributes.getColor(R$styleable.WheelPicker_wheel_curtain_color, -1996488705);
        this.hasAtmospheric = obtainStyledAttributes.getBoolean(R$styleable.WheelPicker_wheel_atmospheric, false);
        this.isCurved = obtainStyledAttributes.getBoolean(R$styleable.WheelPicker_wheel_curved, false);
        this.mItemAlign = obtainStyledAttributes.getInt(R$styleable.WheelPicker_wheel_item_align, 1);
        this.fontPath = obtainStyledAttributes.getString(R$styleable.WheelPicker_wheel_font_path);
        obtainStyledAttributes.recycle();
        initBuild(null);
    }

    public void initBuild(Builder builder) {
        if (builder != null) {
            List list = builder.data;
            if (list == null) {
                list = this.mData;
            }
            this.mData = list;
            this.mItemTextSize = builder.mItemTextSizeDp == 0 ? this.mIndicatorSize : (int) Utils.d2p(getContext(), builder.mItemTextSizeDp);
            int i = builder.mVisibleItemCount;
            if (i == 0) {
                i = this.mVisibleItemCount;
            }
            this.mVisibleItemCount = i;
            int i2 = builder.mSelectedItemPosition;
            if (i2 == 0) {
                i2 = this.mSelectedItemPosition;
            }
            this.mSelectedItemPosition = i2;
            boolean z = true;
            this.hasSameWidth = builder.hasSameWidth ? true : this.hasSameWidth;
            int i3 = builder.mTextMaxWidthPosition;
            if (i3 == 0) {
                i3 = this.mTextMaxWidthPosition;
            }
            this.mTextMaxWidthPosition = i3;
            String str = builder.mMaxWidthText;
            if (str == null) {
                str = this.mMaxWidthText;
            }
            this.mMaxWidthText = str;
            int i4 = builder.mSelectedItemTextColor;
            if (i4 == 0) {
                i4 = this.mSelectedItemTextColor;
            }
            this.mSelectedItemTextColor = i4;
            int i5 = builder.mItemTextColor;
            if (i5 == 0) {
                i5 = this.mItemTextColor;
            }
            this.mItemTextColor = i5;
            this.mItemSpace = builder.mItemSpaceDp == 0 ? this.mItemSpace : (int) Utils.d2p(getContext(), builder.mItemSpaceDp);
            this.isCyclic = builder.isCyclic ? true : this.isCyclic;
            this.hasIndicator = builder.hasIndicator ? true : this.hasIndicator;
            int i6 = builder.mIndicatorColor;
            if (i6 == 0) {
                i6 = this.mIndicatorColor;
            }
            this.mIndicatorColor = i6;
            this.mIndicatorSize = builder.mIndicatorSizeDp == 0 ? this.mIndicatorSize : (int) Utils.d2p(getContext(), builder.mIndicatorSizeDp);
            this.hasCurtain = builder.hasCurtain ? true : this.hasCurtain;
            int i7 = builder.mCurtainColor;
            if (i7 == 0) {
                i7 = this.mCurtainColor;
            }
            this.mCurtainColor = i7;
            this.hasAtmospheric = builder.hasAtmospheric ? true : this.hasAtmospheric;
            if (!builder.isCurved) {
                z = this.isCurved;
            }
            this.isCurved = z;
            int i8 = builder.mItemAlign;
            if (i8 == 0) {
                i8 = this.mItemAlign;
            }
            this.mItemAlign = i8;
            String str2 = builder.fontPath;
            if (str2 == null) {
                str2 = this.fontPath;
            }
            this.fontPath = str2;
        }
        updateVisibleItemCount();
        this.mPaint = new Paint(69);
        this.mPaint.setTextSize(this.mItemTextSize);
        if (this.fontPath != null) {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), this.fontPath));
        }
        updateItemTextAlign();
        computeTextSize();
        this.mScroller = new Scroller(getContext());
        if (Build.VERSION.SDK_INT >= 4) {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
            this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
            this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
            this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        }
        this.mRectDrawn = new Rect();
        this.mRectIndicatorHead = new Rect();
        this.mRectIndicatorFoot = new Rect();
        this.mRectCurrentItem = new Rect();
        this.mCamera = new Camera();
        this.mMatrixRotate = new Matrix();
        this.mMatrixDepth = new Matrix();
    }

    private void updateVisibleItemCount() {
        int i = this.mVisibleItemCount;
        if (i < 2) {
            throw new ArithmeticException("Wheel's visible item count can not be less than 2!");
        }
        if (i % 2 == 0) {
            this.mVisibleItemCount = i + 1;
        }
        this.mDrawnItemCount = this.mVisibleItemCount + 2;
        this.mHalfDrawnItemCount = this.mDrawnItemCount / 2;
    }

    private void computeTextSize() {
        this.mTextMaxHeight = 0;
        this.mTextMaxWidth = 0;
        if (this.hasSameWidth) {
            this.mTextMaxWidth = (int) this.mPaint.measureText(String.valueOf(this.mData.get(0)));
        } else if (isPosInRang(this.mTextMaxWidthPosition)) {
            this.mTextMaxWidth = (int) this.mPaint.measureText(String.valueOf(this.mData.get(this.mTextMaxWidthPosition)));
        } else if (!TextUtils.isEmpty(this.mMaxWidthText)) {
            this.mTextMaxWidth = (int) this.mPaint.measureText(this.mMaxWidthText);
        } else {
            for (Object obj : this.mData) {
                String valueOf = String.valueOf(obj);
                this.mTextMaxWidth = Math.max(this.mTextMaxWidth, (int) this.mPaint.measureText(valueOf));
            }
        }
        Paint.FontMetrics fontMetrics = this.mPaint.getFontMetrics();
        this.mTextMaxHeight = (int) (fontMetrics.bottom - fontMetrics.top);
    }

    private void updateItemTextAlign() {
        int i = this.mItemAlign;
        if (i == 2) {
            this.mPaint.setTextAlign(Paint.Align.LEFT);
        } else if (i == 3) {
            this.mPaint.setTextAlign(Paint.Align.RIGHT);
        } else {
            this.mPaint.setTextAlign(Paint.Align.CENTER);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int i3 = this.mTextMaxWidth;
        int i4 = this.mTextMaxHeight;
        int i5 = this.mVisibleItemCount;
        int i6 = (i4 * i5) + (this.mItemSpace * (i5 - 1));
        if (this.isCurved) {
            i6 = (int) ((i6 * 2) / 3.141592653589793d);
        }
        if (this.isDebug) {
            String str = TAG;
            Log.i(str, "Wheel's content size is (" + i3 + ":" + i6 + ")");
        }
        int paddingLeft = i3 + getPaddingLeft() + getPaddingRight();
        int paddingTop = i6 + getPaddingTop() + getPaddingBottom();
        if (this.isDebug) {
            String str2 = TAG;
            Log.i(str2, "Wheel's size is (" + paddingLeft + ":" + paddingTop + ")");
        }
        setMeasuredDimension(measureSize(mode, size, paddingLeft), measureSize(mode2, size2, paddingTop));
    }

    private int measureSize(int i, int i2, int i3) {
        return i == 1073741824 ? i2 : i == Integer.MIN_VALUE ? Math.min(i3, i2) : i3;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        this.mRectDrawn.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        if (this.isDebug) {
            String str = TAG;
            Log.i(str, "Wheel's drawn rect size is (" + this.mRectDrawn.width() + ":" + this.mRectDrawn.height() + ") and location is (" + this.mRectDrawn.left + ":" + this.mRectDrawn.top + ")");
        }
        this.mWheelCenterX = this.mRectDrawn.centerX();
        this.mWheelCenterY = this.mRectDrawn.centerY();
        computeDrawnCenter();
        this.mHalfWheelHeight = this.mRectDrawn.height() / 2;
        this.mItemHeight = this.mRectDrawn.height() / this.mVisibleItemCount;
        this.mHalfItemHeight = this.mItemHeight / 2;
        computeFlingLimitY();
        computeIndicatorRect();
        computeCurrentItemRect();
    }

    private void computeDrawnCenter() {
        int i = this.mItemAlign;
        if (i == 2) {
            this.mDrawnCenterX = this.mRectDrawn.left;
        } else if (i == 3) {
            this.mDrawnCenterX = this.mRectDrawn.right;
        } else {
            this.mDrawnCenterX = this.mWheelCenterX;
        }
        this.mDrawnCenterY = (int) (this.mWheelCenterY - ((this.mPaint.ascent() + this.mPaint.descent()) / 2.0f));
    }

    private void computeFlingLimitY() {
        int i = this.mSelectedItemPosition;
        int i2 = this.mItemHeight;
        int i3 = i * i2;
        this.mMinFlingY = this.isCyclic ? Integer.MIN_VALUE : ((-i2) * (this.mData.size() - 1)) + i3;
        if (this.isCyclic) {
            i3 = Integer.MAX_VALUE;
        }
        this.mMaxFlingY = i3;
    }

    private void computeIndicatorRect() {
        if (!this.hasIndicator) {
            return;
        }
        int i = this.mIndicatorSize / 2;
        int i2 = this.mWheelCenterY;
        int i3 = this.mHalfItemHeight;
        int i4 = i2 + i3;
        int i5 = i2 - i3;
        Rect rect = this.mRectIndicatorHead;
        Rect rect2 = this.mRectDrawn;
        rect.set(rect2.left, i4 - i, rect2.right, i4 + i);
        Rect rect3 = this.mRectIndicatorFoot;
        Rect rect4 = this.mRectDrawn;
        rect3.set(rect4.left, i5 - i, rect4.right, i5 + i);
    }

    private void computeCurrentItemRect() {
        if (this.hasCurtain || this.mSelectedItemTextColor != -1) {
            Rect rect = this.mRectCurrentItem;
            Rect rect2 = this.mRectDrawn;
            int i = rect2.left;
            int i2 = this.mWheelCenterY;
            int i3 = this.mHalfItemHeight;
            rect.set(i, i2 - i3, rect2.right, i2 + i3);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        String valueOf;
        int i;
        int i2;
        OnWheelChangeListener onWheelChangeListener = this.mOnWheelChangeListener;
        if (onWheelChangeListener != null) {
            onWheelChangeListener.onWheelScrolled(this.mScrollOffsetY);
        }
        if (this.mData.size() == 0) {
            return;
        }
        int i3 = (-this.mScrollOffsetY) / this.mItemHeight;
        int i4 = this.mHalfDrawnItemCount;
        int i5 = i3 - i4;
        int i6 = this.mSelectedItemPosition + i5;
        int i7 = -i4;
        while (i6 < this.mSelectedItemPosition + i5 + this.mDrawnItemCount) {
            if (this.isCyclic) {
                int size = i6 % this.mData.size();
                if (size < 0) {
                    size += this.mData.size();
                }
                valueOf = String.valueOf(this.mData.get(size));
            } else {
                valueOf = isPosInRang(i6) ? String.valueOf(this.mData.get(i6)) : "";
            }
            this.mPaint.setColor(this.mItemTextColor);
            this.mPaint.setStyle(Paint.Style.FILL);
            int i8 = this.mDrawnCenterY;
            int i9 = this.mItemHeight;
            int i10 = (i7 * i9) + i8 + (this.mScrollOffsetY % i9);
            if (this.isCurved) {
                int abs = i8 - Math.abs(i8 - i10);
                int i11 = this.mRectDrawn.top;
                int i12 = this.mDrawnCenterY;
                float f = (-(1.0f - (((abs - i11) * 1.0f) / (i12 - i11)))) * 90.0f * (i10 > i12 ? 1 : i10 < i12 ? -1 : 0);
                if (f < -90.0f) {
                    f = -90.0f;
                }
                if (f > 90.0f) {
                    f = 90.0f;
                }
                i = computeSpace((int) f);
                int i13 = this.mWheelCenterX;
                int i14 = this.mItemAlign;
                if (i14 == 2) {
                    i13 = this.mRectDrawn.left;
                } else if (i14 == 3) {
                    i13 = this.mRectDrawn.right;
                }
                int i15 = this.mWheelCenterY - i;
                this.mCamera.save();
                this.mCamera.rotateX(f);
                this.mCamera.getMatrix(this.mMatrixRotate);
                this.mCamera.restore();
                float f2 = -i13;
                float f3 = -i15;
                this.mMatrixRotate.preTranslate(f2, f3);
                float f4 = i13;
                float f5 = i15;
                this.mMatrixRotate.postTranslate(f4, f5);
                this.mCamera.save();
                this.mCamera.translate(0.0f, 0.0f, computeDepth(i2));
                this.mCamera.getMatrix(this.mMatrixDepth);
                this.mCamera.restore();
                this.mMatrixDepth.preTranslate(f2, f3);
                this.mMatrixDepth.postTranslate(f4, f5);
                this.mMatrixRotate.postConcat(this.mMatrixDepth);
            } else {
                i = 0;
            }
            if (this.hasAtmospheric) {
                int i16 = this.mDrawnCenterY;
                int abs2 = (int) ((((i16 - Math.abs(i16 - i10)) * 1.0f) / this.mDrawnCenterY) * 255.0f);
                if (abs2 < 0) {
                    abs2 = 0;
                }
                this.mPaint.setAlpha(abs2);
            }
            if (this.isCurved) {
                i10 = this.mDrawnCenterY - i;
            }
            if (this.mSelectedItemTextColor != -1) {
                canvas.save();
                if (this.isCurved) {
                    canvas.concat(this.mMatrixRotate);
                }
                canvas.clipRect(this.mRectCurrentItem, Region.Op.DIFFERENCE);
                float f6 = i10;
                canvas.drawText(valueOf, this.mDrawnCenterX, f6, this.mPaint);
                canvas.restore();
                this.mPaint.setColor(this.mSelectedItemTextColor);
                canvas.save();
                if (this.isCurved) {
                    canvas.concat(this.mMatrixRotate);
                }
                canvas.clipRect(this.mRectCurrentItem);
                canvas.drawText(valueOf, this.mDrawnCenterX, f6, this.mPaint);
                canvas.restore();
            } else {
                canvas.save();
                canvas.clipRect(this.mRectDrawn);
                if (this.isCurved) {
                    canvas.concat(this.mMatrixRotate);
                }
                canvas.drawText(valueOf, this.mDrawnCenterX, i10, this.mPaint);
                canvas.restore();
            }
            if (this.isDebug) {
                canvas.save();
                canvas.clipRect(this.mRectDrawn);
                this.mPaint.setColor(-1166541);
                int i17 = this.mWheelCenterY + (this.mItemHeight * i7);
                Rect rect = this.mRectDrawn;
                float f7 = i17;
                canvas.drawLine(rect.left, f7, rect.right, f7, this.mPaint);
                this.mPaint.setColor(-13421586);
                this.mPaint.setStyle(Paint.Style.STROKE);
                int i18 = i17 - this.mHalfItemHeight;
                Rect rect2 = this.mRectDrawn;
                canvas.drawRect(rect2.left, i18, rect2.right, i18 + this.mItemHeight, this.mPaint);
                canvas.restore();
            }
            i6++;
            i7++;
        }
        if (this.hasCurtain) {
            this.mPaint.setColor(this.mCurtainColor);
            this.mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(this.mRectCurrentItem, this.mPaint);
        }
        if (this.hasIndicator) {
            this.mPaint.setColor(this.mIndicatorColor);
            this.mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(this.mRectIndicatorHead, this.mPaint);
            canvas.drawRect(this.mRectIndicatorFoot, this.mPaint);
        }
        if (!this.isDebug) {
            return;
        }
        this.mPaint.setColor(1144254003);
        this.mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0.0f, 0.0f, getPaddingLeft(), getHeight(), this.mPaint);
        canvas.drawRect(0.0f, 0.0f, getWidth(), getPaddingTop(), this.mPaint);
        canvas.drawRect(getWidth() - getPaddingRight(), 0.0f, getWidth(), getHeight(), this.mPaint);
        canvas.drawRect(0.0f, getHeight() - getPaddingBottom(), getWidth(), getHeight(), this.mPaint);
    }

    private boolean isPosInRang(int i) {
        return i >= 0 && i < this.mData.size();
    }

    private int computeSpace(int i) {
        return (int) (Math.sin(Math.toRadians(i)) * this.mHalfWheelHeight);
    }

    private int computeDepth(int i) {
        return (int) (this.mHalfWheelHeight - (Math.cos(Math.toRadians(i)) * this.mHalfWheelHeight));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.isTouchTriggered = true;
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            VelocityTracker velocityTracker = this.mTracker;
            if (velocityTracker == null) {
                this.mTracker = VelocityTracker.obtain();
            } else {
                velocityTracker.clear();
            }
            this.mTracker.addMovement(motionEvent);
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                this.isForceFinishScroll = true;
            }
            int y = (int) motionEvent.getY();
            this.mLastPointY = y;
            this.mDownPointY = y;
        } else if (action == 1) {
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
            if (!this.isClick || this.isForceFinishScroll) {
                this.mTracker.addMovement(motionEvent);
                if (Build.VERSION.SDK_INT >= 4) {
                    this.mTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                } else {
                    this.mTracker.computeCurrentVelocity(1000);
                }
                this.isForceFinishScroll = false;
                int yVelocity = (int) this.mTracker.getYVelocity();
                if (Math.abs(yVelocity) > this.mMinimumVelocity) {
                    this.mScroller.fling(0, this.mScrollOffsetY, 0, yVelocity, 0, 0, this.mMinFlingY, this.mMaxFlingY);
                    Scroller scroller = this.mScroller;
                    scroller.setFinalY(scroller.getFinalY() + computeDistanceToEndPoint(this.mScroller.getFinalY() % this.mItemHeight));
                } else {
                    Scroller scroller2 = this.mScroller;
                    int i = this.mScrollOffsetY;
                    scroller2.startScroll(0, i, 0, computeDistanceToEndPoint(i % this.mItemHeight));
                }
                if (!this.isCyclic) {
                    int finalY = this.mScroller.getFinalY();
                    int i2 = this.mMaxFlingY;
                    if (finalY > i2) {
                        this.mScroller.setFinalY(i2);
                    } else {
                        int finalY2 = this.mScroller.getFinalY();
                        int i3 = this.mMinFlingY;
                        if (finalY2 < i3) {
                            this.mScroller.setFinalY(i3);
                        }
                    }
                }
                this.mHandler.post(this);
                VelocityTracker velocityTracker2 = this.mTracker;
                if (velocityTracker2 != null) {
                    velocityTracker2.recycle();
                    this.mTracker = null;
                }
            }
        } else if (action != 2) {
            if (action == 3) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                VelocityTracker velocityTracker3 = this.mTracker;
                if (velocityTracker3 != null) {
                    velocityTracker3.recycle();
                    this.mTracker = null;
                }
            }
        } else if (Math.abs(this.mDownPointY - motionEvent.getY()) < this.mTouchSlop) {
            this.isClick = true;
        } else {
            this.isClick = false;
            this.mTracker.addMovement(motionEvent);
            OnWheelChangeListener onWheelChangeListener = this.mOnWheelChangeListener;
            if (onWheelChangeListener != null) {
                onWheelChangeListener.onWheelScrollStateChanged(1);
            }
            float y2 = motionEvent.getY() - this.mLastPointY;
            if (Math.abs(y2) >= 1.0f) {
                this.mScrollOffsetY = (int) (this.mScrollOffsetY + y2);
                this.mLastPointY = (int) motionEvent.getY();
                invalidate();
            }
        }
        return true;
    }

    private int computeDistanceToEndPoint(int i) {
        int i2;
        if (Math.abs(i) > this.mHalfItemHeight) {
            if (this.mScrollOffsetY < 0) {
                i2 = -this.mItemHeight;
            } else {
                i2 = this.mItemHeight;
            }
            return i2 - i;
        }
        return -i;
    }

    @Override // java.lang.Runnable
    public void run() {
        List list = this.mData;
        if (list == null || list.size() == 0) {
            return;
        }
        if (this.mScroller.isFinished() && !this.isForceFinishScroll) {
            int i = this.mItemHeight;
            if (i == 0) {
                return;
            }
            int size = (((-this.mScrollOffsetY) / i) + this.mSelectedItemPosition) % this.mData.size();
            if (size < 0) {
                size += this.mData.size();
            }
            if (this.isDebug) {
                String str = TAG;
                Log.i(str, size + ":" + this.mData.get(size) + ":" + this.mScrollOffsetY);
            }
            this.mCurrentItemPosition = size;
            OnItemSelectedListener onItemSelectedListener = this.mOnItemSelectedListener;
            if (onItemSelectedListener != null && this.isTouchTriggered) {
                onItemSelectedListener.onItemSelected(this, this.mData.get(size), size);
            }
            OnWheelChangeListener onWheelChangeListener = this.mOnWheelChangeListener;
            if (onWheelChangeListener != null && this.isTouchTriggered) {
                onWheelChangeListener.onWheelSelected(size);
                this.mOnWheelChangeListener.onWheelScrollStateChanged(0);
            }
        }
        if (!this.mScroller.computeScrollOffset()) {
            return;
        }
        OnWheelChangeListener onWheelChangeListener2 = this.mOnWheelChangeListener;
        if (onWheelChangeListener2 != null) {
            onWheelChangeListener2.onWheelScrollStateChanged(2);
        }
        this.mScrollOffsetY = this.mScroller.getCurrY();
        postInvalidate();
        this.mHandler.postDelayed(this, 16L);
    }

    public void setDebug(boolean z) {
        this.isDebug = z;
    }

    public int getVisibleItemCount() {
        return this.mVisibleItemCount;
    }

    public void setVisibleItemCount(int i) {
        this.mVisibleItemCount = i;
        updateVisibleItemCount();
        requestLayout();
    }

    public void setCyclic(boolean z) {
        this.isCyclic = z;
        computeFlingLimitY();
        invalidate();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public int getSelectedItemPosition() {
        return this.mSelectedItemPosition;
    }

    public void setSelectedItemPosition(int i) {
        setSelectedItemPosition(i, true);
    }

    public void setSelectedItemPosition(int i, boolean z) {
        this.isTouchTriggered = false;
        if (z && this.mScroller.isFinished()) {
            int size = getData().size();
            int i2 = i - this.mCurrentItemPosition;
            if (i2 == 0) {
                return;
            }
            if (this.isCyclic && Math.abs(i2) > size / 2) {
                if (i2 > 0) {
                    size = -size;
                }
                i2 += size;
            }
            Scroller scroller = this.mScroller;
            scroller.startScroll(0, scroller.getCurrY(), 0, (-i2) * this.mItemHeight);
            this.mHandler.post(this);
            return;
        }
        if (!this.mScroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        int max = Math.max(Math.min(i, this.mData.size() - 1), 0);
        this.mSelectedItemPosition = max;
        this.mCurrentItemPosition = max;
        this.mScrollOffsetY = 0;
        computeFlingLimitY();
        requestLayout();
        invalidate();
    }

    public int getCurrentItemPosition() {
        return this.mCurrentItemPosition;
    }

    public List getData() {
        return this.mData;
    }

    public void setData(List list) {
        if (list == null) {
            throw new NullPointerException("WheelPicker's data can not be null!");
        }
        this.mData = list;
        if (this.mSelectedItemPosition > list.size() - 1 || this.mCurrentItemPosition > list.size() - 1) {
            int size = list.size() - 1;
            this.mCurrentItemPosition = size;
            this.mSelectedItemPosition = size;
        } else {
            this.mSelectedItemPosition = this.mCurrentItemPosition;
        }
        this.mScrollOffsetY = 0;
        computeTextSize();
        computeFlingLimitY();
        requestLayout();
        invalidate();
    }

    public void setSameWidth(boolean z) {
        this.hasSameWidth = z;
        computeTextSize();
        requestLayout();
        invalidate();
    }

    public void setOnWheelChangeListener(OnWheelChangeListener onWheelChangeListener) {
        this.mOnWheelChangeListener = onWheelChangeListener;
    }

    public String getMaximumWidthText() {
        return this.mMaxWidthText;
    }

    public void setMaximumWidthText(String str) {
        if (str == null) {
            throw new NullPointerException("Maximum width text can not be null!");
        }
        this.mMaxWidthText = str;
        computeTextSize();
        requestLayout();
        invalidate();
    }

    public int getMaximumWidthTextPosition() {
        return this.mTextMaxWidthPosition;
    }

    public void setMaximumWidthTextPosition(int i) {
        if (!isPosInRang(i)) {
            throw new ArrayIndexOutOfBoundsException("Maximum width text Position must in [0, " + this.mData.size() + "), but current is " + i);
        }
        this.mTextMaxWidthPosition = i;
        computeTextSize();
        requestLayout();
        invalidate();
    }

    public int getSelectedItemTextColor() {
        return this.mSelectedItemTextColor;
    }

    public void setSelectedItemTextColor(int i) {
        this.mSelectedItemTextColor = i;
        computeCurrentItemRect();
        invalidate();
    }

    public int getItemTextColor() {
        return this.mItemTextColor;
    }

    public void setItemTextColor(int i) {
        this.mItemTextColor = i;
        invalidate();
    }

    public int getItemTextSize() {
        return this.mItemTextSize;
    }

    public void setItemTextSize(int i) {
        this.mItemTextSize = i;
        this.mPaint.setTextSize(this.mItemTextSize);
        computeTextSize();
        requestLayout();
        invalidate();
    }

    public int getItemSpace() {
        return this.mItemSpace;
    }

    public void setItemSpace(int i) {
        this.mItemSpace = i;
        requestLayout();
        invalidate();
    }

    public void setIndicator(boolean z) {
        this.hasIndicator = z;
        computeIndicatorRect();
        invalidate();
    }

    public int getIndicatorSize() {
        return this.mIndicatorSize;
    }

    public void setIndicatorSize(int i) {
        this.mIndicatorSize = i;
        computeIndicatorRect();
        invalidate();
    }

    public int getIndicatorColor() {
        return this.mIndicatorColor;
    }

    public void setIndicatorColor(int i) {
        this.mIndicatorColor = i;
        invalidate();
    }

    public void setCurtain(boolean z) {
        this.hasCurtain = z;
        computeCurrentItemRect();
        invalidate();
    }

    public int getCurtainColor() {
        return this.mCurtainColor;
    }

    public void setCurtainColor(int i) {
        this.mCurtainColor = i;
        invalidate();
    }

    public void setAtmospheric(boolean z) {
        this.hasAtmospheric = z;
        invalidate();
    }

    public void setCurved(boolean z) {
        this.isCurved = z;
        requestLayout();
        invalidate();
    }

    public int getItemAlign() {
        return this.mItemAlign;
    }

    public void setItemAlign(int i) {
        this.mItemAlign = i;
        updateItemTextAlign();
        computeDrawnCenter();
        invalidate();
    }

    public Typeface getTypeface() {
        Paint paint = this.mPaint;
        if (paint != null) {
            return paint.getTypeface();
        }
        return null;
    }

    public void setTypeface(Typeface typeface) {
        Paint paint = this.mPaint;
        if (paint != null) {
            paint.setTypeface(typeface);
        }
        computeTextSize();
        requestLayout();
        invalidate();
    }
}
