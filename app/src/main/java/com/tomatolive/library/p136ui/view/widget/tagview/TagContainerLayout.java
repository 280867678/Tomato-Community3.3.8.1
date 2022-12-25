package com.tomatolive.library.p136ui.view.widget.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.support.p002v4.view.ViewCompat;
import android.support.p002v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.tomatolive.library.R$styleable;
import com.tomatolive.library.p136ui.view.widget.tagview.ColorFactory;
import com.tomatolive.library.p136ui.view.widget.tagview.TagView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.widget.tagview.TagContainerLayout */
/* loaded from: classes4.dex */
public class TagContainerLayout extends ViewGroup {
    private static final float DEFAULT_INTERVAL = 5.0f;
    private static final int TAG_MIN_LENGTH = 3;
    private boolean isTagViewClickable;
    private boolean isTagViewSelectable;
    private int mBackgroundColor;
    private int mBorderColor;
    private float mBorderRadius;
    private float mBorderWidth;
    private int mChildHeight;
    private List<View> mChildViews;
    private List<int[]> mColorArrayList;
    private float mCrossAreaPadding;
    private float mCrossAreaWidth;
    private int mCrossColor;
    private float mCrossLineWidth;
    private int mDefaultImageDrawableID;
    private boolean mDragEnable;
    private boolean mEnableCross;
    private int mGravity;
    private int mHorizontalInterval;
    private int mMaxLines;
    private TagView.OnTagClickListener mOnTagClickListener;
    private Paint mPaint;
    private RectF mRectF;
    private int mRippleAlpha;
    private int mRippleColor;
    private int mRippleDuration;
    private int mSelectedTagBackgroundColor;
    private float mSensitivity;
    private int mTagBackgroundColor;
    private int mTagBackgroundResource;
    private float mTagBdDistance;
    private int mTagBorderColor;
    private float mTagBorderRadius;
    private float mTagBorderWidth;
    private int mTagHorizontalPadding;
    private int mTagMaxLength;
    private boolean mTagSupportLettersRTL;
    private int mTagTextColor;
    private int mTagTextDirection;
    private float mTagTextSize;
    private Typeface mTagTypeface;
    private int mTagVerticalPadding;
    private int mTagViewState;
    private List<String> mTags;
    private int mTheme;
    private int mVerticalInterval;
    private ViewDragHelper mViewDragHelper;
    private int[] mViewPos;

    public TagContainerLayout(Context context) {
        this(context, null);
    }

    public TagContainerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TagContainerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBorderWidth = 0.5f;
        this.mBorderRadius = 10.0f;
        this.mSensitivity = 1.0f;
        this.mBorderColor = Color.parseColor("#22FF0000");
        this.mBackgroundColor = Color.parseColor("#11FF0000");
        this.mGravity = 3;
        this.mMaxLines = 0;
        this.mTagMaxLength = 23;
        this.mTagBorderWidth = 0.5f;
        this.mTagBorderRadius = 15.0f;
        this.mTagTextSize = 14.0f;
        this.mTagTextDirection = 3;
        this.mTagHorizontalPadding = 10;
        this.mTagVerticalPadding = 8;
        this.mTagBorderColor = Color.parseColor("#88F44336");
        this.mTagBackgroundColor = Color.parseColor("#33F44336");
        this.mSelectedTagBackgroundColor = Color.parseColor("#33FF7669");
        this.mTagTextColor = Color.parseColor("#FF666666");
        this.mTagTypeface = Typeface.DEFAULT;
        this.mDefaultImageDrawableID = -1;
        this.mTagViewState = 0;
        this.mTagBdDistance = 2.75f;
        this.mTagSupportLettersRTL = false;
        this.mTheme = 1;
        this.mRippleDuration = 1000;
        this.mRippleAlpha = 128;
        this.mEnableCross = false;
        this.mCrossAreaWidth = 0.0f;
        this.mCrossAreaPadding = 10.0f;
        this.mCrossColor = ViewCompat.MEASURED_STATE_MASK;
        this.mCrossLineWidth = 1.0f;
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AndroidTagView, i, 0);
        this.mVerticalInterval = (int) obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_vertical_interval, Utils.dp2px(context, DEFAULT_INTERVAL));
        this.mHorizontalInterval = (int) obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_horizontal_interval, Utils.dp2px(context, DEFAULT_INTERVAL));
        this.mBorderWidth = obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_container_border_width, Utils.dp2px(context, this.mBorderWidth));
        this.mBorderRadius = obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_container_border_radius, Utils.dp2px(context, this.mBorderRadius));
        this.mTagBdDistance = obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_tag_bd_distance, Utils.dp2px(context, this.mTagBdDistance));
        this.mBorderColor = obtainStyledAttributes.getColor(R$styleable.AndroidTagView_container_border_color, this.mBorderColor);
        this.mBackgroundColor = obtainStyledAttributes.getColor(R$styleable.AndroidTagView_container_background_color, this.mBackgroundColor);
        this.mDragEnable = obtainStyledAttributes.getBoolean(R$styleable.AndroidTagView_container_enable_drag, false);
        this.mSensitivity = obtainStyledAttributes.getFloat(R$styleable.AndroidTagView_container_drag_sensitivity, this.mSensitivity);
        this.mGravity = obtainStyledAttributes.getInt(R$styleable.AndroidTagView_container_gravity, this.mGravity);
        this.mMaxLines = obtainStyledAttributes.getInt(R$styleable.AndroidTagView_container_max_lines, this.mMaxLines);
        this.mTagMaxLength = obtainStyledAttributes.getInt(R$styleable.AndroidTagView_tag_max_length, this.mTagMaxLength);
        this.mTheme = obtainStyledAttributes.getInt(R$styleable.AndroidTagView_tag_theme, this.mTheme);
        this.mTagBorderWidth = obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_tag_border_width, Utils.dp2px(context, this.mTagBorderWidth));
        this.mTagBorderRadius = obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_tag_corner_radius, Utils.dp2px(context, this.mTagBorderRadius));
        this.mTagHorizontalPadding = (int) obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_tag_horizontal_padding, Utils.dp2px(context, this.mTagHorizontalPadding));
        this.mTagVerticalPadding = (int) obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_tag_vertical_padding, Utils.dp2px(context, this.mTagVerticalPadding));
        this.mTagTextSize = obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_tag_text_size, Utils.sp2px(context, this.mTagTextSize));
        this.mTagBorderColor = obtainStyledAttributes.getColor(R$styleable.AndroidTagView_tag_border_color, this.mTagBorderColor);
        this.mTagBackgroundColor = obtainStyledAttributes.getColor(R$styleable.AndroidTagView_tag_background_color, this.mTagBackgroundColor);
        this.mTagTextColor = obtainStyledAttributes.getColor(R$styleable.AndroidTagView_tag_text_color, this.mTagTextColor);
        this.mTagTextDirection = obtainStyledAttributes.getInt(R$styleable.AndroidTagView_tag_text_direction, this.mTagTextDirection);
        this.isTagViewClickable = obtainStyledAttributes.getBoolean(R$styleable.AndroidTagView_tag_clickable, false);
        this.isTagViewSelectable = obtainStyledAttributes.getBoolean(R$styleable.AndroidTagView_tag_selectable, false);
        this.mRippleColor = obtainStyledAttributes.getColor(R$styleable.AndroidTagView_tag_ripple_color, Color.parseColor("#EEEEEE"));
        this.mRippleAlpha = obtainStyledAttributes.getInteger(R$styleable.AndroidTagView_tag_ripple_alpha, this.mRippleAlpha);
        this.mRippleDuration = obtainStyledAttributes.getInteger(R$styleable.AndroidTagView_tag_ripple_duration, this.mRippleDuration);
        this.mEnableCross = obtainStyledAttributes.getBoolean(R$styleable.AndroidTagView_tag_enable_cross, this.mEnableCross);
        this.mCrossAreaWidth = obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_tag_cross_width, Utils.dp2px(context, this.mCrossAreaWidth));
        this.mCrossAreaPadding = obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_tag_cross_area_padding, Utils.dp2px(context, this.mCrossAreaPadding));
        this.mCrossColor = obtainStyledAttributes.getColor(R$styleable.AndroidTagView_tag_cross_color, this.mCrossColor);
        this.mCrossLineWidth = obtainStyledAttributes.getDimension(R$styleable.AndroidTagView_tag_cross_line_width, Utils.dp2px(context, this.mCrossLineWidth));
        this.mTagSupportLettersRTL = obtainStyledAttributes.getBoolean(R$styleable.AndroidTagView_tag_support_letters_rlt, this.mTagSupportLettersRTL);
        this.mTagBackgroundResource = obtainStyledAttributes.getResourceId(R$styleable.AndroidTagView_tag_background, this.mTagBackgroundResource);
        obtainStyledAttributes.recycle();
        this.mPaint = new Paint(1);
        this.mRectF = new RectF();
        this.mChildViews = new ArrayList();
        this.mViewDragHelper = ViewDragHelper.create(this, this.mSensitivity, new DragHelperCallBack());
        setWillNotDraw(false);
        setTagMaxLength(this.mTagMaxLength);
        setTagHorizontalPadding(this.mTagHorizontalPadding);
        setTagVerticalPadding(this.mTagVerticalPadding);
        if (isInEditMode()) {
            addTag("sample tag");
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        measureChildren(i, i2);
        int childCount = getChildCount();
        int childLines = childCount == 0 ? 0 : getChildLines(childCount);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (mode == Integer.MIN_VALUE || mode == 0) {
            int i3 = this.mVerticalInterval;
            setMeasuredDimension(size, (((this.mChildHeight + i3) * childLines) - i3) + getPaddingTop() + getPaddingBottom());
        } else {
            setMeasuredDimension(size, size2);
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mRectF.set(0.0f, 0.0f, i, i2);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return;
        }
        int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        int measuredWidth2 = getMeasuredWidth() - getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        this.mViewPos = new int[childCount * 2];
        int i5 = paddingTop;
        int i6 = 0;
        int i7 = measuredWidth2;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                int measuredWidth3 = childAt.getMeasuredWidth();
                int i9 = this.mGravity;
                if (i9 == 5) {
                    if (i7 - measuredWidth3 < getPaddingLeft()) {
                        i7 = getMeasuredWidth() - getPaddingRight();
                        i5 += this.mChildHeight + this.mVerticalInterval;
                    }
                    int[] iArr = this.mViewPos;
                    int i10 = i8 * 2;
                    iArr[i10] = i7 - measuredWidth3;
                    iArr[i10 + 1] = i5;
                    i7 -= measuredWidth3 + this.mHorizontalInterval;
                } else if (i9 == 17) {
                    if ((paddingLeft + measuredWidth3) - getPaddingLeft() > measuredWidth) {
                        int i11 = i8 - 1;
                        int measuredWidth4 = ((getMeasuredWidth() - this.mViewPos[i11 * 2]) - getChildAt(i11).getMeasuredWidth()) - getPaddingRight();
                        while (i6 < i8) {
                            int[] iArr2 = this.mViewPos;
                            int i12 = i6 * 2;
                            iArr2[i12] = iArr2[i12] + (measuredWidth4 / 2);
                            i6++;
                        }
                        paddingLeft = getPaddingLeft();
                        i5 += this.mChildHeight + this.mVerticalInterval;
                        i6 = i8;
                    }
                    int[] iArr3 = this.mViewPos;
                    int i13 = i8 * 2;
                    iArr3[i13] = paddingLeft;
                    iArr3[i13 + 1] = i5;
                    paddingLeft += measuredWidth3 + this.mHorizontalInterval;
                    if (i8 == childCount - 1) {
                        int measuredWidth5 = ((getMeasuredWidth() - this.mViewPos[i13]) - childAt.getMeasuredWidth()) - getPaddingRight();
                        for (int i14 = i6; i14 < childCount; i14++) {
                            int[] iArr4 = this.mViewPos;
                            int i15 = i14 * 2;
                            iArr4[i15] = iArr4[i15] + (measuredWidth5 / 2);
                        }
                    }
                } else {
                    if ((paddingLeft + measuredWidth3) - getPaddingLeft() > measuredWidth) {
                        paddingLeft = getPaddingLeft();
                        i5 += this.mChildHeight + this.mVerticalInterval;
                    }
                    int[] iArr5 = this.mViewPos;
                    int i16 = i8 * 2;
                    iArr5[i16] = paddingLeft;
                    iArr5[i16 + 1] = i5;
                    paddingLeft += measuredWidth3 + this.mHorizontalInterval;
                }
            }
        }
        for (int i17 = 0; i17 < this.mViewPos.length / 2; i17++) {
            View childAt2 = getChildAt(i17);
            int[] iArr6 = this.mViewPos;
            int i18 = i17 * 2;
            int i19 = i18 + 1;
            childAt2.layout(iArr6[i18], iArr6[i19], iArr6[i18] + childAt2.getMeasuredWidth(), this.mViewPos[i19] + this.mChildHeight);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(this.mBackgroundColor);
        RectF rectF = this.mRectF;
        float f = this.mBorderRadius;
        canvas.drawRoundRect(rectF, f, f, this.mPaint);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(this.mBorderWidth);
        this.mPaint.setColor(this.mBorderColor);
        RectF rectF2 = this.mRectF;
        float f2 = this.mBorderRadius;
        canvas.drawRoundRect(rectF2, f2, f2, this.mPaint);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mViewDragHelper.shouldInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mViewDragHelper.processTouchEvent(motionEvent);
        return true;
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.mViewDragHelper.continueSettling(true)) {
            requestLayout();
        }
    }

    private int getChildLines(int i) {
        int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        int i2 = 1;
        int i3 = 0;
        for (int i4 = 0; i4 < i; i4++) {
            View childAt = getChildAt(i4);
            int measuredWidth2 = childAt.getMeasuredWidth() + this.mHorizontalInterval;
            int measuredHeight = childAt.getMeasuredHeight();
            if (i4 != 0) {
                measuredHeight = Math.min(this.mChildHeight, measuredHeight);
            }
            this.mChildHeight = measuredHeight;
            i3 += measuredWidth2;
            if (i3 - this.mHorizontalInterval > measuredWidth) {
                i2++;
                i3 = measuredWidth2;
            }
        }
        int i5 = this.mMaxLines;
        return i5 <= 0 ? i2 : i5;
    }

    private int[] onUpdateColorFactory() {
        int i = this.mTheme;
        if (i == 0) {
            return ColorFactory.onRandomBuild();
        }
        if (i == 2) {
            return ColorFactory.onPureBuild(ColorFactory.PURE_COLOR.TEAL);
        }
        return i == 1 ? ColorFactory.onPureBuild(ColorFactory.PURE_COLOR.CYAN) : new int[]{this.mTagBackgroundColor, this.mTagBorderColor, this.mTagTextColor, this.mSelectedTagBackgroundColor};
    }

    private void onSetTag() {
        if (this.mTags == null) {
            throw new RuntimeException("NullPointer exception!");
        }
        removeAllTags();
        if (this.mTags.size() == 0) {
            return;
        }
        for (int i = 0; i < this.mTags.size(); i++) {
            onAddTag(this.mTags.get(i), this.mChildViews.size());
        }
        postInvalidate();
    }

    private void onAddTag(String str, int i) {
        TagView tagView;
        if (i < 0 || i > this.mChildViews.size()) {
            throw new RuntimeException("Illegal position!");
        }
        if (this.mDefaultImageDrawableID != -1) {
            tagView = new TagView(getContext(), str, this.mDefaultImageDrawableID);
        } else {
            tagView = new TagView(getContext(), str);
        }
        initTagView(tagView, i);
        this.mChildViews.add(i, tagView);
        if (i < this.mChildViews.size()) {
            for (int i2 = i; i2 < this.mChildViews.size(); i2++) {
                this.mChildViews.get(i2).setTag(Integer.valueOf(i2));
            }
        } else {
            tagView.setTag(Integer.valueOf(i));
        }
        addView(tagView, i);
    }

    private void initTagView(TagView tagView, int i) {
        int[] onUpdateColorFactory;
        List<int[]> list = this.mColorArrayList;
        if (list != null && list.size() > 0) {
            if (this.mColorArrayList.size() == this.mTags.size() && this.mColorArrayList.get(i).length >= 4) {
                onUpdateColorFactory = this.mColorArrayList.get(i);
            } else {
                throw new RuntimeException("Illegal color list!");
            }
        } else {
            onUpdateColorFactory = onUpdateColorFactory();
        }
        tagView.setTagBackgroundColor(onUpdateColorFactory[0]);
        tagView.setTagBorderColor(onUpdateColorFactory[1]);
        tagView.setTagTextColor(onUpdateColorFactory[2]);
        tagView.setTagSelectedBackgroundColor(onUpdateColorFactory[3]);
        tagView.setTagMaxLength(this.mTagMaxLength);
        tagView.setTextDirection(this.mTagTextDirection);
        tagView.setTypeface(this.mTagTypeface);
        tagView.setBorderWidth(this.mTagBorderWidth);
        tagView.setBorderRadius(this.mTagBorderRadius);
        tagView.setTextSize(this.mTagTextSize);
        tagView.setHorizontalPadding(this.mTagHorizontalPadding);
        tagView.setVerticalPadding(this.mTagVerticalPadding);
        tagView.setIsViewClickable(this.isTagViewClickable);
        tagView.setIsViewSelectable(this.isTagViewSelectable);
        tagView.setBdDistance(this.mTagBdDistance);
        tagView.setOnTagClickListener(this.mOnTagClickListener);
        tagView.setRippleAlpha(this.mRippleAlpha);
        tagView.setRippleColor(this.mRippleColor);
        tagView.setRippleDuration(this.mRippleDuration);
        tagView.setEnableCross(this.mEnableCross);
        tagView.setCrossAreaWidth(this.mCrossAreaWidth);
        tagView.setCrossAreaPadding(this.mCrossAreaPadding);
        tagView.setCrossColor(this.mCrossColor);
        tagView.setCrossLineWidth(this.mCrossLineWidth);
        tagView.setTagSupportLettersRTL(this.mTagSupportLettersRTL);
        tagView.setBackgroundResource(this.mTagBackgroundResource);
    }

    private void invalidateTags() {
        Iterator<View> it2 = this.mChildViews.iterator();
        while (it2.hasNext()) {
            ((TagView) it2.next()).setOnTagClickListener(this.mOnTagClickListener);
        }
    }

    private void onRemoveTag(int i) {
        if (i < 0 || i >= this.mChildViews.size()) {
            throw new RuntimeException("Illegal position!");
        }
        this.mChildViews.remove(i);
        removeViewAt(i);
        while (i < this.mChildViews.size()) {
            this.mChildViews.get(i).setTag(Integer.valueOf(i));
            i++;
        }
    }

    private void onRemoveConsecutiveTags(List<Integer> list) {
        int intValue = ((Integer) Collections.min(list)).intValue();
        for (Integer num : list) {
            int intValue2 = num.intValue();
            if (intValue2 < 0 || intValue2 >= this.mChildViews.size()) {
                throw new RuntimeException("Illegal position!");
            }
            this.mChildViews.remove(intValue);
            removeViewAt(intValue);
        }
        while (intValue < this.mChildViews.size()) {
            this.mChildViews.get(intValue).setTag(Integer.valueOf(intValue));
            intValue++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] onGetNewPosition(View view) {
        int left = view.getLeft();
        int top2 = view.getTop();
        int i = this.mViewPos[((Integer) view.getTag()).intValue() * 2];
        int i2 = this.mViewPos[(((Integer) view.getTag()).intValue() * 2) + 1];
        int abs = Math.abs(top2 - i2);
        int i3 = i2;
        int i4 = 0;
        while (true) {
            int[] iArr = this.mViewPos;
            if (i4 >= iArr.length / 2) {
                break;
            }
            int i5 = (i4 * 2) + 1;
            if (Math.abs(top2 - iArr[i5]) < abs) {
                int[] iArr2 = this.mViewPos;
                i3 = iArr2[i5];
                abs = Math.abs(top2 - iArr2[i5]);
            }
            i4++;
        }
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (true) {
            int[] iArr3 = this.mViewPos;
            if (i6 < iArr3.length / 2) {
                int i9 = i6 * 2;
                if (iArr3[i9 + 1] == i3) {
                    if (i7 == 0) {
                        i = iArr3[i9];
                        i8 = Math.abs(left - i);
                    } else if (Math.abs(left - iArr3[i9]) < i8) {
                        i = this.mViewPos[i9];
                        i8 = Math.abs(left - i);
                    }
                    i7++;
                }
                i6++;
            } else {
                return new int[]{i, i3};
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int onGetCoordinateReferPos(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int[] iArr = this.mViewPos;
            if (i3 < iArr.length / 2) {
                int i5 = i3 * 2;
                if (i == iArr[i5] && i2 == iArr[i5 + 1]) {
                    i4 = i3;
                }
                i3++;
            } else {
                return i4;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChangeView(View view, int i, int i2) {
        this.mChildViews.remove(i2);
        this.mChildViews.add(i, view);
        for (View view2 : this.mChildViews) {
            view2.setTag(Integer.valueOf(this.mChildViews.indexOf(view2)));
        }
        removeViewAt(i2);
        addView(view, i);
    }

    private int ceilTagBorderWidth() {
        return (int) Math.ceil(this.mTagBorderWidth);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.widget.tagview.TagContainerLayout$DragHelperCallBack */
    /* loaded from: classes4.dex */
    public class DragHelperCallBack extends ViewDragHelper.Callback {
        private DragHelperCallBack() {
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i) {
            super.onViewDragStateChanged(i);
            TagContainerLayout.this.mTagViewState = i;
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(View view, int i) {
            TagContainerLayout.this.requestDisallowInterceptTouchEvent(true);
            return TagContainerLayout.this.mDragEnable;
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(View view, int i, int i2) {
            int paddingLeft = TagContainerLayout.this.getPaddingLeft();
            return Math.min(Math.max(i, paddingLeft), (TagContainerLayout.this.getWidth() - view.getWidth()) - TagContainerLayout.this.getPaddingRight());
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public int clampViewPositionVertical(View view, int i, int i2) {
            int paddingTop = TagContainerLayout.this.getPaddingTop();
            return Math.min(Math.max(i, paddingTop), (TagContainerLayout.this.getHeight() - view.getHeight()) - TagContainerLayout.this.getPaddingBottom());
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public int getViewHorizontalDragRange(View view) {
            return TagContainerLayout.this.getMeasuredWidth() - view.getMeasuredWidth();
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public int getViewVerticalDragRange(View view) {
            return TagContainerLayout.this.getMeasuredHeight() - view.getMeasuredHeight();
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public void onViewReleased(View view, float f, float f2) {
            super.onViewReleased(view, f, f2);
            TagContainerLayout.this.requestDisallowInterceptTouchEvent(false);
            int[] onGetNewPosition = TagContainerLayout.this.onGetNewPosition(view);
            TagContainerLayout.this.onChangeView(view, TagContainerLayout.this.onGetCoordinateReferPos(onGetNewPosition[0], onGetNewPosition[1]), ((Integer) view.getTag()).intValue());
            TagContainerLayout.this.mViewDragHelper.settleCapturedViewAt(onGetNewPosition[0], onGetNewPosition[1]);
            TagContainerLayout.this.invalidate();
        }
    }

    public int getTagViewState() {
        return this.mTagViewState;
    }

    public float getTagBdDistance() {
        return this.mTagBdDistance;
    }

    public void setTagBdDistance(float f) {
        this.mTagBdDistance = Utils.dp2px(getContext(), f);
    }

    public void setTags(List<String> list) {
        this.mTags = list;
        onSetTag();
    }

    public void setTags(List<String> list, List<int[]> list2) {
        this.mTags = list;
        this.mColorArrayList = list2;
        onSetTag();
    }

    public void setTags(String... strArr) {
        this.mTags = Arrays.asList(strArr);
        onSetTag();
    }

    public void addTag(String str) {
        addTag(str, this.mChildViews.size());
    }

    public void addTag(String str, int i) {
        onAddTag(str, i);
        postInvalidate();
    }

    public void removeTag(int i) {
        onRemoveTag(i);
        postInvalidate();
    }

    public void removeConsecutiveTags(List<Integer> list) {
        onRemoveConsecutiveTags(list);
        postInvalidate();
    }

    public void removeAllTags() {
        this.mChildViews.clear();
        removeAllViews();
        postInvalidate();
    }

    public void setOnTagClickListener(TagView.OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
        invalidateTags();
    }

    public void toggleSelectTagView(int i) {
        if (this.isTagViewSelectable) {
            TagView tagView = (TagView) this.mChildViews.get(i);
            if (tagView.getIsViewSelected()) {
                tagView.deselectView();
            } else {
                tagView.selectView();
            }
        }
    }

    public void selectTagView(int i) {
        if (this.isTagViewSelectable) {
            ((TagView) this.mChildViews.get(i)).selectView();
        }
    }

    public void deselectTagView(int i) {
        if (this.isTagViewSelectable) {
            ((TagView) this.mChildViews.get(i)).deselectView();
        }
    }

    public List<Integer> getSelectedTagViewPositions() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mChildViews.size(); i++) {
            if (((TagView) this.mChildViews.get(i)).getIsViewSelected()) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        return arrayList;
    }

    public List<String> getSelectedTagViewText() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mChildViews.size(); i++) {
            TagView tagView = (TagView) this.mChildViews.get(i);
            if (tagView.getIsViewSelected()) {
                arrayList.add(tagView.getText());
            }
        }
        return arrayList;
    }

    public int size() {
        return this.mChildViews.size();
    }

    public String getTagText(int i) {
        return ((TagView) this.mChildViews.get(i)).getText();
    }

    public List<String> getTags() {
        ArrayList arrayList = new ArrayList();
        for (View view : this.mChildViews) {
            if (view instanceof TagView) {
                arrayList.add(((TagView) view).getText());
            }
        }
        return arrayList;
    }

    public void setDragEnable(boolean z) {
        this.mDragEnable = z;
    }

    public boolean getDragEnable() {
        return this.mDragEnable;
    }

    public void setVerticalInterval(float f) {
        this.mVerticalInterval = (int) Utils.dp2px(getContext(), f);
        postInvalidate();
    }

    public int getVerticalInterval() {
        return this.mVerticalInterval;
    }

    public void setHorizontalInterval(float f) {
        this.mHorizontalInterval = (int) Utils.dp2px(getContext(), f);
        postInvalidate();
    }

    public int getHorizontalInterval() {
        return this.mHorizontalInterval;
    }

    public float getBorderWidth() {
        return this.mBorderWidth;
    }

    public void setBorderWidth(float f) {
        this.mBorderWidth = f;
    }

    public float getBorderRadius() {
        return this.mBorderRadius;
    }

    public void setBorderRadius(float f) {
        this.mBorderRadius = f;
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public void setBorderColor(int i) {
        this.mBorderColor = i;
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setGravity(int i) {
        this.mGravity = i;
    }

    public float getSensitivity() {
        return this.mSensitivity;
    }

    public void setSensitivity(float f) {
        this.mSensitivity = f;
    }

    public int getDefaultImageDrawableID() {
        return this.mDefaultImageDrawableID;
    }

    public void setDefaultImageDrawableID(int i) {
        this.mDefaultImageDrawableID = i;
    }

    public void setMaxLines(int i) {
        this.mMaxLines = i;
        postInvalidate();
    }

    public int getMaxLines() {
        return this.mMaxLines;
    }

    public void setTagMaxLength(int i) {
        if (i < 3) {
            i = 3;
        }
        this.mTagMaxLength = i;
    }

    public int getTagMaxLength() {
        return this.mTagMaxLength;
    }

    public void setTheme(int i) {
        this.mTheme = i;
    }

    public int getTheme() {
        return this.mTheme;
    }

    public boolean getIsTagViewClickable() {
        return this.isTagViewClickable;
    }

    public void setIsTagViewClickable(boolean z) {
        this.isTagViewClickable = z;
    }

    public boolean getIsTagViewSelectable() {
        return this.isTagViewSelectable;
    }

    public void setIsTagViewSelectable(boolean z) {
        this.isTagViewSelectable = z;
    }

    public float getTagBorderWidth() {
        return this.mTagBorderWidth;
    }

    public void setTagBorderWidth(float f) {
        this.mTagBorderWidth = f;
    }

    public float getTagBorderRadius() {
        return this.mTagBorderRadius;
    }

    public void setTagBorderRadius(float f) {
        this.mTagBorderRadius = f;
    }

    public float getTagTextSize() {
        return this.mTagTextSize;
    }

    public void setTagTextSize(float f) {
        this.mTagTextSize = f;
    }

    public int getTagHorizontalPadding() {
        return this.mTagHorizontalPadding;
    }

    public void setTagHorizontalPadding(int i) {
        int ceilTagBorderWidth = ceilTagBorderWidth();
        if (i < ceilTagBorderWidth) {
            i = ceilTagBorderWidth;
        }
        this.mTagHorizontalPadding = i;
    }

    public int getTagVerticalPadding() {
        return this.mTagVerticalPadding;
    }

    public void setTagVerticalPadding(int i) {
        int ceilTagBorderWidth = ceilTagBorderWidth();
        if (i < ceilTagBorderWidth) {
            i = ceilTagBorderWidth;
        }
        this.mTagVerticalPadding = i;
    }

    public int getTagBorderColor() {
        return this.mTagBorderColor;
    }

    public void setTagBorderColor(int i) {
        this.mTagBorderColor = i;
    }

    public int getTagBackgroundColor() {
        return this.mTagBackgroundColor;
    }

    public void setTagBackgroundColor(int i) {
        this.mTagBackgroundColor = i;
    }

    public int getTagTextColor() {
        return this.mTagTextColor;
    }

    public void setTagTextDirection(int i) {
        this.mTagTextDirection = i;
    }

    public Typeface getTagTypeface() {
        return this.mTagTypeface;
    }

    public void setTagTypeface(Typeface typeface) {
        this.mTagTypeface = typeface;
    }

    public int getTagTextDirection() {
        return this.mTagTextDirection;
    }

    public void setTagTextColor(int i) {
        this.mTagTextColor = i;
    }

    public int getRippleAlpha() {
        return this.mRippleAlpha;
    }

    public void setRippleAlpha(int i) {
        this.mRippleAlpha = i;
    }

    public int getRippleColor() {
        return this.mRippleColor;
    }

    public void setRippleColor(int i) {
        this.mRippleColor = i;
    }

    public int getRippleDuration() {
        return this.mRippleDuration;
    }

    public void setRippleDuration(int i) {
        this.mRippleDuration = i;
    }

    public int getCrossColor() {
        return this.mCrossColor;
    }

    public void setCrossColor(int i) {
        this.mCrossColor = i;
    }

    public float getCrossAreaPadding() {
        return this.mCrossAreaPadding;
    }

    public void setCrossAreaPadding(float f) {
        this.mCrossAreaPadding = f;
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

    public boolean isTagSupportLettersRTL() {
        return this.mTagSupportLettersRTL;
    }

    public void setTagSupportLettersRTL(boolean z) {
        this.mTagSupportLettersRTL = z;
    }

    public TagView getTagView(int i) {
        if (i < 0 || i >= this.mChildViews.size()) {
            throw new RuntimeException("Illegal position!");
        }
        return (TagView) this.mChildViews.get(i);
    }

    public int getTagBackgroundResource() {
        return this.mTagBackgroundResource;
    }

    public void setTagBackgroundResource(@DrawableRes int i) {
        this.mTagBackgroundResource = i;
    }
}
