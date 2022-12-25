package com.scwang.smartrefresh.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.view.NestedScrollingChildHelper;
import android.support.p002v4.view.NestedScrollingParent;
import android.support.p002v4.view.NestedScrollingParentHelper;
import android.support.p005v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import android.widget.TextView;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshContent;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshInternal;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.constant.DimensionStatus;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.impl.RefreshContentWrapper;
import com.scwang.smartrefresh.layout.impl.RefreshFooterWrapper;
import com.scwang.smartrefresh.layout.impl.RefreshHeaderWrapper;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.util.DelayedRunnable;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.scwang.smartrefresh.layout.util.SmartUtil;
import com.scwang.smartrefresh.layout.util.ViscousFluidInterpolator;
import com.tomatolive.library.p136ui.view.dialog.LotteryDialog;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"RestrictedApi"})
/* loaded from: classes3.dex */
public class SmartRefreshLayout extends ViewGroup implements RefreshLayout, NestedScrollingParent {
    protected static DefaultRefreshFooterCreator sFooterCreator;
    protected static DefaultRefreshHeaderCreator sHeaderCreator;
    protected static DefaultRefreshInitializer sRefreshInitializer;
    protected Runnable animationRunnable;
    protected int mCurrentVelocity;
    protected boolean mDisableContentWhenLoading;
    protected boolean mDisableContentWhenRefresh;
    protected char mDragDirection;
    protected float mDragRate;
    protected boolean mEnableAutoLoadMore;
    protected boolean mEnableClipFooterWhenFixedBehind;
    protected boolean mEnableClipHeaderWhenFixedBehind;
    protected boolean mEnableFooterFollowWhenNoMoreData;
    protected boolean mEnableFooterTranslationContent;
    protected boolean mEnableHeaderTranslationContent;
    protected boolean mEnableLoadMore;
    protected boolean mEnableLoadMoreWhenContentNotFull;
    protected boolean mEnableOverScrollBounce;
    protected boolean mEnableOverScrollDrag;
    protected boolean mEnablePreviewInEditMode;
    protected boolean mEnablePureScrollMode;
    protected boolean mEnableRefresh;
    protected boolean mEnableScrollContentWhenLoaded;
    protected boolean mEnableScrollContentWhenRefreshed;
    protected MotionEvent mFalsifyEvent;
    protected int mFixedFooterViewId;
    protected int mFixedHeaderViewId;
    protected int mFloorDuration;
    protected int mFooterBackgroundColor;
    protected int mFooterHeight;
    protected DimensionStatus mFooterHeightStatus;
    protected int mFooterInsetStart;
    protected boolean mFooterLocked;
    protected float mFooterMaxDragRate;
    protected boolean mFooterNeedTouchEventWhenLoading;
    protected boolean mFooterNoMoreData;
    protected int mFooterTranslationViewId;
    protected float mFooterTriggerRate;
    protected Handler mHandler;
    protected int mHeaderBackgroundColor;
    protected int mHeaderHeight;
    protected DimensionStatus mHeaderHeightStatus;
    protected int mHeaderInsetStart;
    protected float mHeaderMaxDragRate;
    protected boolean mHeaderNeedTouchEventWhenRefreshing;
    protected int mHeaderTranslationViewId;
    protected float mHeaderTriggerRate;
    protected boolean mIsBeingDragged;
    protected RefreshKernel mKernel;
    protected long mLastOpenTime;
    protected int mLastSpinner;
    protected float mLastTouchX;
    protected float mLastTouchY;
    protected List<DelayedRunnable> mListDelayedRunnable;
    protected OnLoadMoreListener mLoadMoreListener;
    protected boolean mManualFooterTranslationContent;
    protected boolean mManualHeaderTranslationContent;
    protected boolean mManualLoadMore;
    protected int mMaximumVelocity;
    protected int mMinimumVelocity;
    protected NestedScrollingChildHelper mNestedChild;
    protected boolean mNestedInProgress;
    protected NestedScrollingParentHelper mNestedParent;
    protected OnMultiPurposeListener mOnMultiPurposeListener;
    protected Paint mPaint;
    protected int[] mParentOffsetInWindow;
    protected int[] mPrimaryColors;
    protected int mReboundDuration;
    protected Interpolator mReboundInterpolator;
    protected RefreshContent mRefreshContent;
    protected RefreshInternal mRefreshFooter;
    protected RefreshInternal mRefreshHeader;
    protected OnRefreshListener mRefreshListener;
    protected int mScreenHeightPixels;
    protected ScrollBoundaryDecider mScrollBoundaryDecider;
    protected Scroller mScroller;
    protected int mSpinner;
    protected RefreshState mState;
    protected boolean mSuperDispatchTouchEvent;
    protected int mTotalUnconsumed;
    protected int mTouchSlop;
    protected int mTouchSpinner;
    protected float mTouchX;
    protected float mTouchY;
    protected VelocityTracker mVelocityTracker;
    protected boolean mVerticalPermit;
    protected RefreshState mViceState;
    protected ValueAnimator reboundAnimator;

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    /* renamed from: getLayout  reason: collision with other method in class */
    public SmartRefreshLayout mo6485getLayout() {
        return this;
    }

    public SmartRefreshLayout(Context context) {
        this(context, null);
    }

    public SmartRefreshLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SmartRefreshLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFloorDuration = ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
        this.mReboundDuration = ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
        this.mDragRate = 0.5f;
        this.mDragDirection = 'n';
        this.mFixedHeaderViewId = -1;
        this.mFixedFooterViewId = -1;
        this.mHeaderTranslationViewId = -1;
        this.mFooterTranslationViewId = -1;
        this.mEnableRefresh = true;
        this.mEnableLoadMore = false;
        this.mEnableClipHeaderWhenFixedBehind = true;
        this.mEnableClipFooterWhenFixedBehind = true;
        this.mEnableHeaderTranslationContent = true;
        this.mEnableFooterTranslationContent = true;
        this.mEnableFooterFollowWhenNoMoreData = false;
        this.mEnablePreviewInEditMode = true;
        this.mEnableOverScrollBounce = true;
        this.mEnableOverScrollDrag = false;
        this.mEnableAutoLoadMore = true;
        this.mEnablePureScrollMode = false;
        this.mEnableScrollContentWhenLoaded = true;
        this.mEnableScrollContentWhenRefreshed = true;
        this.mEnableLoadMoreWhenContentNotFull = true;
        this.mDisableContentWhenRefresh = false;
        this.mDisableContentWhenLoading = false;
        this.mFooterNoMoreData = false;
        this.mManualLoadMore = false;
        this.mManualHeaderTranslationContent = false;
        this.mManualFooterTranslationContent = false;
        this.mParentOffsetInWindow = new int[2];
        this.mNestedChild = new NestedScrollingChildHelper(this);
        this.mNestedParent = new NestedScrollingParentHelper(this);
        DimensionStatus dimensionStatus = DimensionStatus.DefaultUnNotify;
        this.mHeaderHeightStatus = dimensionStatus;
        this.mFooterHeightStatus = dimensionStatus;
        this.mHeaderMaxDragRate = 2.5f;
        this.mFooterMaxDragRate = 2.5f;
        this.mHeaderTriggerRate = 1.0f;
        this.mFooterTriggerRate = 1.0f;
        this.mKernel = new RefreshKernelImpl();
        RefreshState refreshState = RefreshState.None;
        this.mState = refreshState;
        this.mViceState = refreshState;
        this.mLastOpenTime = 0L;
        this.mHeaderBackgroundColor = 0;
        this.mFooterBackgroundColor = 0;
        this.mFooterLocked = false;
        this.mVerticalPermit = false;
        this.mFalsifyEvent = null;
        super.setClipToPadding(false);
        DensityUtil densityUtil = new DensityUtil();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mScroller = new Scroller(context);
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mScreenHeightPixels = context.getResources().getDisplayMetrics().heightPixels;
        this.mReboundInterpolator = new ViscousFluidInterpolator();
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mFooterHeight = densityUtil.dip2px(60.0f);
        this.mHeaderHeight = densityUtil.dip2px(100.0f);
        this.mNestedChild.setNestedScrollingEnabled(true);
        DefaultRefreshInitializer defaultRefreshInitializer = sRefreshInitializer;
        if (defaultRefreshInitializer != null) {
            defaultRefreshInitializer.initialize(context, this);
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SmartRefreshLayout);
        NestedScrollingChildHelper nestedScrollingChildHelper = this.mNestedChild;
        nestedScrollingChildHelper.setNestedScrollingEnabled(obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableNestedScrolling, nestedScrollingChildHelper.isNestedScrollingEnabled()));
        this.mDragRate = obtainStyledAttributes.getFloat(R$styleable.SmartRefreshLayout_srlDragRate, this.mDragRate);
        this.mHeaderMaxDragRate = obtainStyledAttributes.getFloat(R$styleable.SmartRefreshLayout_srlHeaderMaxDragRate, this.mHeaderMaxDragRate);
        this.mFooterMaxDragRate = obtainStyledAttributes.getFloat(R$styleable.SmartRefreshLayout_srlFooterMaxDragRate, this.mFooterMaxDragRate);
        this.mHeaderTriggerRate = obtainStyledAttributes.getFloat(R$styleable.SmartRefreshLayout_srlHeaderTriggerRate, this.mHeaderTriggerRate);
        this.mFooterTriggerRate = obtainStyledAttributes.getFloat(R$styleable.SmartRefreshLayout_srlFooterTriggerRate, this.mFooterTriggerRate);
        this.mEnableRefresh = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableRefresh, this.mEnableRefresh);
        this.mReboundDuration = obtainStyledAttributes.getInt(R$styleable.SmartRefreshLayout_srlReboundDuration, this.mReboundDuration);
        this.mEnableLoadMore = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableLoadMore, this.mEnableLoadMore);
        this.mHeaderHeight = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.SmartRefreshLayout_srlHeaderHeight, this.mHeaderHeight);
        this.mFooterHeight = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.SmartRefreshLayout_srlFooterHeight, this.mFooterHeight);
        this.mHeaderInsetStart = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.SmartRefreshLayout_srlHeaderInsetStart, this.mHeaderInsetStart);
        this.mFooterInsetStart = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.SmartRefreshLayout_srlFooterInsetStart, this.mFooterInsetStart);
        this.mDisableContentWhenRefresh = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlDisableContentWhenRefresh, this.mDisableContentWhenRefresh);
        this.mDisableContentWhenLoading = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlDisableContentWhenLoading, this.mDisableContentWhenLoading);
        this.mEnableHeaderTranslationContent = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableHeaderTranslationContent, this.mEnableHeaderTranslationContent);
        this.mEnableFooterTranslationContent = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableFooterTranslationContent, this.mEnableFooterTranslationContent);
        this.mEnablePreviewInEditMode = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnablePreviewInEditMode, this.mEnablePreviewInEditMode);
        this.mEnableAutoLoadMore = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableAutoLoadMore, this.mEnableAutoLoadMore);
        this.mEnableOverScrollBounce = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableOverScrollBounce, this.mEnableOverScrollBounce);
        this.mEnablePureScrollMode = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnablePureScrollMode, this.mEnablePureScrollMode);
        this.mEnableScrollContentWhenLoaded = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableScrollContentWhenLoaded, this.mEnableScrollContentWhenLoaded);
        this.mEnableScrollContentWhenRefreshed = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableScrollContentWhenRefreshed, this.mEnableScrollContentWhenRefreshed);
        this.mEnableLoadMoreWhenContentNotFull = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableLoadMoreWhenContentNotFull, this.mEnableLoadMoreWhenContentNotFull);
        this.mEnableFooterFollowWhenNoMoreData = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableFooterFollowWhenLoadFinished, this.mEnableFooterFollowWhenNoMoreData);
        this.mEnableFooterFollowWhenNoMoreData = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableFooterFollowWhenNoMoreData, this.mEnableFooterFollowWhenNoMoreData);
        this.mEnableClipHeaderWhenFixedBehind = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableClipHeaderWhenFixedBehind, this.mEnableClipHeaderWhenFixedBehind);
        this.mEnableClipFooterWhenFixedBehind = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableClipFooterWhenFixedBehind, this.mEnableClipFooterWhenFixedBehind);
        this.mEnableOverScrollDrag = obtainStyledAttributes.getBoolean(R$styleable.SmartRefreshLayout_srlEnableOverScrollDrag, this.mEnableOverScrollDrag);
        this.mFixedHeaderViewId = obtainStyledAttributes.getResourceId(R$styleable.SmartRefreshLayout_srlFixedHeaderViewId, this.mFixedHeaderViewId);
        this.mFixedFooterViewId = obtainStyledAttributes.getResourceId(R$styleable.SmartRefreshLayout_srlFixedFooterViewId, this.mFixedFooterViewId);
        this.mHeaderTranslationViewId = obtainStyledAttributes.getResourceId(R$styleable.SmartRefreshLayout_srlHeaderTranslationViewId, this.mHeaderTranslationViewId);
        this.mFooterTranslationViewId = obtainStyledAttributes.getResourceId(R$styleable.SmartRefreshLayout_srlFooterTranslationViewId, this.mFooterTranslationViewId);
        if (this.mEnablePureScrollMode && !obtainStyledAttributes.hasValue(R$styleable.SmartRefreshLayout_srlEnableOverScrollDrag)) {
            this.mEnableOverScrollDrag = true;
        }
        this.mManualLoadMore = this.mManualLoadMore || obtainStyledAttributes.hasValue(R$styleable.SmartRefreshLayout_srlEnableLoadMore);
        this.mManualHeaderTranslationContent = this.mManualHeaderTranslationContent || obtainStyledAttributes.hasValue(R$styleable.SmartRefreshLayout_srlEnableHeaderTranslationContent);
        this.mManualFooterTranslationContent = this.mManualFooterTranslationContent || obtainStyledAttributes.hasValue(R$styleable.SmartRefreshLayout_srlEnableFooterTranslationContent);
        this.mHeaderHeightStatus = obtainStyledAttributes.hasValue(R$styleable.SmartRefreshLayout_srlHeaderHeight) ? DimensionStatus.XmlLayoutUnNotify : this.mHeaderHeightStatus;
        this.mFooterHeightStatus = obtainStyledAttributes.hasValue(R$styleable.SmartRefreshLayout_srlFooterHeight) ? DimensionStatus.XmlLayoutUnNotify : this.mFooterHeightStatus;
        int color = obtainStyledAttributes.getColor(R$styleable.SmartRefreshLayout_srlAccentColor, 0);
        int color2 = obtainStyledAttributes.getColor(R$styleable.SmartRefreshLayout_srlPrimaryColor, 0);
        if (color2 != 0) {
            if (color != 0) {
                this.mPrimaryColors = new int[]{color2, color};
            } else {
                this.mPrimaryColors = new int[]{color2};
            }
        } else if (color != 0) {
            this.mPrimaryColors = new int[]{0, color};
        }
        obtainStyledAttributes.recycle();
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0052  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onFinishInflate() {
        int i;
        int i2;
        int i3;
        super.onFinishInflate();
        int childCount = super.getChildCount();
        if (childCount <= 3) {
            int i4 = 0;
            int i5 = -1;
            char c = 0;
            while (true) {
                i = 2;
                char c2 = 1;
                if (i4 >= childCount) {
                    break;
                }
                View childAt = super.getChildAt(i4);
                if (SmartUtil.isContentView(childAt) && (c < 2 || i4 == 1)) {
                    i5 = i4;
                    c = 2;
                } else if (!(childAt instanceof RefreshInternal) && c < 1) {
                    if (i4 <= 0) {
                        c2 = 0;
                    }
                    i5 = i4;
                    c = c2;
                }
                i4++;
            }
            if (i5 >= 0) {
                this.mRefreshContent = new RefreshContentWrapper(super.getChildAt(i5));
                if (i5 == 1) {
                    if (childCount == 3) {
                        i2 = 0;
                    } else {
                        i2 = 0;
                        i = -1;
                    }
                } else if (childCount == 2) {
                    i2 = -1;
                    i = 1;
                }
                for (i3 = 0; i3 < childCount; i3++) {
                    View childAt2 = super.getChildAt(i3);
                    if (i3 == i2 || (i3 != i && i2 == -1 && this.mRefreshHeader == null && (childAt2 instanceof RefreshHeader))) {
                        this.mRefreshHeader = childAt2 instanceof RefreshHeader ? (RefreshHeader) childAt2 : new RefreshHeaderWrapper(childAt2);
                    } else if (i3 == i || (i == -1 && (childAt2 instanceof RefreshFooter))) {
                        this.mEnableLoadMore = this.mEnableLoadMore || !this.mManualLoadMore;
                        this.mRefreshFooter = childAt2 instanceof RefreshFooter ? (RefreshFooter) childAt2 : new RefreshFooterWrapper(childAt2);
                    }
                }
                return;
            }
            i2 = -1;
            i = -1;
            while (i3 < childCount) {
            }
            return;
        }
        throw new RuntimeException("最多只支持3个子View，Most only support three sub view");
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        RefreshInternal refreshInternal;
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            if (this.mHandler == null) {
                this.mHandler = new Handler();
            }
            List<DelayedRunnable> list = this.mListDelayedRunnable;
            View view = null;
            if (list != null) {
                for (DelayedRunnable delayedRunnable : list) {
                    this.mHandler.postDelayed(delayedRunnable, delayedRunnable.delayMillis);
                }
                this.mListDelayedRunnable.clear();
                this.mListDelayedRunnable = null;
            }
            if (this.mRefreshHeader == null) {
                DefaultRefreshHeaderCreator defaultRefreshHeaderCreator = sHeaderCreator;
                if (defaultRefreshHeaderCreator != null) {
                    setRefreshHeader(defaultRefreshHeaderCreator.createRefreshHeader(getContext(), this));
                } else {
                    setRefreshHeader(new BezierRadarHeader(getContext()));
                }
            }
            if (this.mRefreshFooter == null) {
                DefaultRefreshFooterCreator defaultRefreshFooterCreator = sFooterCreator;
                if (defaultRefreshFooterCreator != null) {
                    setRefreshFooter(defaultRefreshFooterCreator.createRefreshFooter(getContext(), this));
                } else {
                    boolean z = this.mEnableLoadMore;
                    setRefreshFooter(new BallPulseFooter(getContext()));
                    this.mEnableLoadMore = z;
                }
            } else {
                this.mEnableLoadMore = this.mEnableLoadMore || !this.mManualLoadMore;
            }
            if (this.mRefreshContent == null) {
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = getChildAt(i);
                    RefreshInternal refreshInternal2 = this.mRefreshHeader;
                    if ((refreshInternal2 == null || childAt != refreshInternal2.getView()) && ((refreshInternal = this.mRefreshFooter) == null || childAt != refreshInternal.getView())) {
                        this.mRefreshContent = new RefreshContentWrapper(childAt);
                    }
                }
            }
            if (this.mRefreshContent == null) {
                int dp2px = DensityUtil.dp2px(20.0f);
                TextView textView = new TextView(getContext());
                textView.setTextColor(-39424);
                textView.setGravity(17);
                textView.setTextSize(20.0f);
                textView.setText(R$string.srl_content_empty);
                super.addView(textView, -1, -1);
                this.mRefreshContent = new RefreshContentWrapper(textView);
                this.mRefreshContent.getView().setPadding(dp2px, dp2px, dp2px, dp2px);
            }
            int i2 = this.mFixedHeaderViewId;
            View findViewById = i2 > 0 ? findViewById(i2) : null;
            int i3 = this.mFixedFooterViewId;
            if (i3 > 0) {
                view = findViewById(i3);
            }
            this.mRefreshContent.setScrollBoundaryDecider(this.mScrollBoundaryDecider);
            this.mRefreshContent.setEnableLoadMoreWhenContentNotFull(this.mEnableLoadMoreWhenContentNotFull);
            this.mRefreshContent.setUpComponent(this.mKernel, findViewById, view);
            if (this.mSpinner != 0) {
                notifyStateChanged(RefreshState.None);
                RefreshContent refreshContent = this.mRefreshContent;
                this.mSpinner = 0;
                refreshContent.moveSpinner(0, this.mHeaderTranslationViewId, this.mFooterTranslationViewId);
            }
        }
        int[] iArr = this.mPrimaryColors;
        if (iArr != null) {
            RefreshInternal refreshInternal3 = this.mRefreshHeader;
            if (refreshInternal3 != null) {
                refreshInternal3.setPrimaryColors(iArr);
            }
            RefreshInternal refreshInternal4 = this.mRefreshFooter;
            if (refreshInternal4 != null) {
                refreshInternal4.setPrimaryColors(this.mPrimaryColors);
            }
        }
        RefreshContent refreshContent2 = this.mRefreshContent;
        if (refreshContent2 != null) {
            super.bringChildToFront(refreshContent2.getView());
        }
        RefreshInternal refreshInternal5 = this.mRefreshHeader;
        if (refreshInternal5 != null && refreshInternal5.getSpinnerStyle() != SpinnerStyle.FixedBehind) {
            super.bringChildToFront(this.mRefreshHeader.getView());
        }
        RefreshInternal refreshInternal6 = this.mRefreshFooter;
        if (refreshInternal6 == null || refreshInternal6.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            return;
        }
        super.bringChildToFront(this.mRefreshFooter.getView());
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00d3  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int i, int i2) {
        int i3;
        DimensionStatus dimensionStatus;
        boolean z = isInEditMode() && this.mEnablePreviewInEditMode;
        int childCount = super.getChildCount();
        int i4 = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = super.getChildAt(i5);
            RefreshInternal refreshInternal = this.mRefreshHeader;
            if (refreshInternal != null && refreshInternal.getView() == childAt) {
                View view = this.mRefreshHeader.getView();
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, ((ViewGroup.MarginLayoutParams) layoutParams).width);
                int i6 = this.mHeaderHeight;
                if (this.mHeaderHeightStatus.ordinal() < DimensionStatus.XmlLayoutUnNotify.ordinal()) {
                    if (((ViewGroup.MarginLayoutParams) layoutParams).height > 0) {
                        i3 = ((ViewGroup.MarginLayoutParams) layoutParams).height + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                        if (this.mHeaderHeightStatus.canReplaceWith(DimensionStatus.XmlExactUnNotify)) {
                            this.mHeaderHeight = ((ViewGroup.MarginLayoutParams) layoutParams).height + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                            this.mHeaderHeightStatus = DimensionStatus.XmlExactUnNotify;
                        }
                    } else if (((ViewGroup.MarginLayoutParams) layoutParams).height == -2 && (this.mRefreshHeader.getSpinnerStyle() != SpinnerStyle.MatchLayout || !this.mHeaderHeightStatus.notified)) {
                        int max = Math.max((View.MeasureSpec.getSize(i2) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, 0);
                        view.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(max, Integer.MIN_VALUE));
                        int measuredHeight = view.getMeasuredHeight();
                        if (measuredHeight > 0) {
                            if (measuredHeight != max && this.mHeaderHeightStatus.canReplaceWith(DimensionStatus.XmlWrapUnNotify)) {
                                this.mHeaderHeight = measuredHeight + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                                this.mHeaderHeightStatus = DimensionStatus.XmlWrapUnNotify;
                            }
                            i3 = -1;
                        }
                    }
                    if (this.mRefreshHeader.getSpinnerStyle() != SpinnerStyle.MatchLayout) {
                        i3 = View.MeasureSpec.getSize(i2);
                    } else if (this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Scale && !z) {
                        i3 = Math.max(0, isEnableRefreshOrLoadMore(this.mEnableRefresh) ? this.mSpinner : 0);
                    }
                    if (i3 != -1) {
                        view.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(Math.max((i3 - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, 0), 1073741824));
                    }
                    dimensionStatus = this.mHeaderHeightStatus;
                    if (!dimensionStatus.notified) {
                        this.mHeaderHeightStatus = dimensionStatus.notified();
                        RefreshInternal refreshInternal2 = this.mRefreshHeader;
                        RefreshKernel refreshKernel = this.mKernel;
                        int i7 = this.mHeaderHeight;
                        refreshInternal2.onInitialized(refreshKernel, i7, (int) (this.mHeaderMaxDragRate * i7));
                    }
                    if (z && isEnableRefreshOrLoadMore(this.mEnableRefresh)) {
                        i4 += view.getMeasuredHeight();
                    }
                }
                i3 = i6;
                if (this.mRefreshHeader.getSpinnerStyle() != SpinnerStyle.MatchLayout) {
                }
                if (i3 != -1) {
                }
                dimensionStatus = this.mHeaderHeightStatus;
                if (!dimensionStatus.notified) {
                }
                if (z) {
                    i4 += view.getMeasuredHeight();
                }
            }
            RefreshInternal refreshInternal3 = this.mRefreshFooter;
            if (refreshInternal3 != null && refreshInternal3.getView() == childAt) {
                View view2 = this.mRefreshFooter.getView();
                LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
                int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i, ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin, ((ViewGroup.MarginLayoutParams) layoutParams2).width);
                int i8 = this.mFooterHeight;
                if (this.mFooterHeightStatus.ordinal() < DimensionStatus.XmlLayoutUnNotify.ordinal()) {
                    if (((ViewGroup.MarginLayoutParams) layoutParams2).height > 0) {
                        i8 = ((ViewGroup.MarginLayoutParams) layoutParams2).height + ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin;
                        if (this.mFooterHeightStatus.canReplaceWith(DimensionStatus.XmlExactUnNotify)) {
                            this.mFooterHeight = ((ViewGroup.MarginLayoutParams) layoutParams2).height + ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin;
                            this.mFooterHeightStatus = DimensionStatus.XmlExactUnNotify;
                        }
                    } else if (((ViewGroup.MarginLayoutParams) layoutParams2).height == -2 && (this.mRefreshFooter.getSpinnerStyle() != SpinnerStyle.MatchLayout || !this.mFooterHeightStatus.notified)) {
                        int max2 = Math.max((View.MeasureSpec.getSize(i2) - ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin) - ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin, 0);
                        view2.measure(childMeasureSpec2, View.MeasureSpec.makeMeasureSpec(max2, Integer.MIN_VALUE));
                        int measuredHeight2 = view2.getMeasuredHeight();
                        if (measuredHeight2 > 0) {
                            if (measuredHeight2 != max2 && this.mFooterHeightStatus.canReplaceWith(DimensionStatus.XmlWrapUnNotify)) {
                                this.mFooterHeight = measuredHeight2 + ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin;
                                this.mFooterHeightStatus = DimensionStatus.XmlWrapUnNotify;
                            }
                            i8 = -1;
                        }
                    }
                }
                if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.MatchLayout) {
                    i8 = View.MeasureSpec.getSize(i2);
                } else if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Scale && !z) {
                    i8 = Math.max(0, isEnableRefreshOrLoadMore(this.mEnableLoadMore) ? -this.mSpinner : 0);
                }
                if (i8 != -1) {
                    view2.measure(childMeasureSpec2, View.MeasureSpec.makeMeasureSpec(Math.max((i8 - ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin) - ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin, 0), 1073741824));
                }
                DimensionStatus dimensionStatus2 = this.mFooterHeightStatus;
                if (!dimensionStatus2.notified) {
                    this.mFooterHeightStatus = dimensionStatus2.notified();
                    RefreshInternal refreshInternal4 = this.mRefreshFooter;
                    RefreshKernel refreshKernel2 = this.mKernel;
                    int i9 = this.mFooterHeight;
                    refreshInternal4.onInitialized(refreshKernel2, i9, (int) (this.mFooterMaxDragRate * i9));
                }
                if (z && isEnableRefreshOrLoadMore(this.mEnableLoadMore)) {
                    i4 += view2.getMeasuredHeight();
                }
            }
            RefreshContent refreshContent = this.mRefreshContent;
            if (refreshContent != null && refreshContent.getView() == childAt) {
                View view3 = this.mRefreshContent.getView();
                LayoutParams layoutParams3 = (LayoutParams) view3.getLayoutParams();
                view3.measure(ViewGroup.getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams3).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams3).rightMargin, ((ViewGroup.MarginLayoutParams) layoutParams3).width), ViewGroup.getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom() + ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams3).bottomMargin + ((!z || !(this.mRefreshHeader != null && isEnableRefreshOrLoadMore(this.mEnableRefresh) && isEnableTranslationContent(this.mEnableHeaderTranslationContent, this.mRefreshHeader))) ? 0 : this.mHeaderHeight) + ((!z || !(this.mRefreshFooter != null && isEnableRefreshOrLoadMore(this.mEnableLoadMore) && isEnableTranslationContent(this.mEnableFooterTranslationContent, this.mRefreshFooter))) ? 0 : this.mFooterHeight), ((ViewGroup.MarginLayoutParams) layoutParams3).height));
                i4 += view3.getMeasuredHeight();
            }
        }
        super.setMeasuredDimension(View.resolveSize(super.getSuggestedMinimumWidth(), i), View.resolveSize(i4, i2));
        this.mLastTouchX = getMeasuredWidth() / 2;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        getPaddingBottom();
        int childCount = super.getChildCount();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = super.getChildAt(i6);
            RefreshContent refreshContent = this.mRefreshContent;
            boolean z2 = true;
            if (refreshContent != null && refreshContent.getView() == childAt) {
                boolean z3 = isInEditMode() && this.mEnablePreviewInEditMode && isEnableRefreshOrLoadMore(this.mEnableRefresh) && this.mRefreshHeader != null;
                View view = this.mRefreshContent.getView();
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                int i7 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + paddingLeft;
                int i8 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + paddingTop;
                int measuredWidth = view.getMeasuredWidth() + i7;
                int measuredHeight = view.getMeasuredHeight() + i8;
                if (z3 && isEnableTranslationContent(this.mEnableHeaderTranslationContent, this.mRefreshHeader)) {
                    int i9 = this.mHeaderHeight;
                    i8 += i9;
                    measuredHeight += i9;
                }
                view.layout(i7, i8, measuredWidth, measuredHeight);
            }
            RefreshInternal refreshInternal = this.mRefreshHeader;
            if (refreshInternal != null && refreshInternal.getView() == childAt) {
                boolean z4 = isInEditMode() && this.mEnablePreviewInEditMode && isEnableRefreshOrLoadMore(this.mEnableRefresh);
                View view2 = this.mRefreshHeader.getView();
                LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
                int i10 = ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin;
                int i11 = ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin + this.mHeaderInsetStart;
                int measuredWidth2 = view2.getMeasuredWidth() + i10;
                int measuredHeight2 = view2.getMeasuredHeight() + i11;
                if (!z4 && this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                    int i12 = this.mHeaderHeight;
                    i11 -= i12;
                    measuredHeight2 -= i12;
                }
                view2.layout(i10, i11, measuredWidth2, measuredHeight2);
            }
            RefreshInternal refreshInternal2 = this.mRefreshFooter;
            if (refreshInternal2 != null && refreshInternal2.getView() == childAt) {
                if (!isInEditMode() || !this.mEnablePreviewInEditMode || !isEnableRefreshOrLoadMore(this.mEnableLoadMore)) {
                    z2 = false;
                }
                View view3 = this.mRefreshFooter.getView();
                LayoutParams layoutParams3 = (LayoutParams) view3.getLayoutParams();
                SpinnerStyle spinnerStyle = this.mRefreshFooter.getSpinnerStyle();
                int i13 = ((ViewGroup.MarginLayoutParams) layoutParams3).leftMargin;
                int measuredHeight3 = ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin + getMeasuredHeight();
                int i14 = this.mFooterInsetStart;
                int i15 = measuredHeight3 - i14;
                if (spinnerStyle == SpinnerStyle.MatchLayout) {
                    i15 = ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin - i14;
                } else {
                    if (z2 || spinnerStyle == SpinnerStyle.FixedFront || spinnerStyle == SpinnerStyle.FixedBehind) {
                        i5 = this.mFooterHeight;
                    } else if (spinnerStyle == SpinnerStyle.Scale && this.mSpinner < 0) {
                        i5 = Math.max(isEnableRefreshOrLoadMore(this.mEnableLoadMore) ? -this.mSpinner : 0, 0);
                    }
                    i15 -= i5;
                }
                view3.layout(i13, i15, view3.getMeasuredWidth() + i13, view3.getMeasuredHeight() + i15);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mKernel.moveSpinner(0, true);
        notifyStateChanged(RefreshState.None);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.mHandler = null;
        }
        List<DelayedRunnable> list = this.mListDelayedRunnable;
        if (list != null) {
            list.clear();
            this.mListDelayedRunnable = null;
        }
        this.mManualLoadMore = true;
        this.animationRunnable = null;
        ValueAnimator valueAnimator = this.reboundAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
            this.reboundAnimator.removeAllUpdateListeners();
            this.reboundAnimator.cancel();
            this.reboundAnimator = null;
        }
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        Paint paint;
        Paint paint2;
        RefreshContent refreshContent = this.mRefreshContent;
        View view2 = refreshContent != null ? refreshContent.getView() : null;
        RefreshInternal refreshInternal = this.mRefreshHeader;
        if (refreshInternal != null && refreshInternal.getView() == view) {
            if (!isEnableRefreshOrLoadMore(this.mEnableRefresh) || (!this.mEnablePreviewInEditMode && isInEditMode())) {
                return true;
            }
            if (view2 != null) {
                int max = Math.max(view2.getTop() + view2.getPaddingTop() + this.mSpinner, view.getTop());
                int i = this.mHeaderBackgroundColor;
                if (i != 0 && (paint2 = this.mPaint) != null) {
                    paint2.setColor(i);
                    if (this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Scale) {
                        max = view.getBottom();
                    } else if (this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                        max = view.getBottom() + this.mSpinner;
                    }
                    canvas.drawRect(view.getLeft(), view.getTop(), view.getRight(), max, this.mPaint);
                }
                if (this.mEnableClipHeaderWhenFixedBehind && this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                    canvas.save();
                    canvas.clipRect(view.getLeft(), view.getTop(), view.getRight(), max);
                    boolean drawChild = super.drawChild(canvas, view, j);
                    canvas.restore();
                    return drawChild;
                }
            }
        }
        RefreshInternal refreshInternal2 = this.mRefreshFooter;
        if (refreshInternal2 != null && refreshInternal2.getView() == view) {
            if (!isEnableRefreshOrLoadMore(this.mEnableLoadMore) || (!this.mEnablePreviewInEditMode && isInEditMode())) {
                return true;
            }
            if (view2 != null) {
                int min = Math.min((view2.getBottom() - view2.getPaddingBottom()) + this.mSpinner, view.getBottom());
                int i2 = this.mFooterBackgroundColor;
                if (i2 != 0 && (paint = this.mPaint) != null) {
                    paint.setColor(i2);
                    if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Scale) {
                        min = view.getTop();
                    } else if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Translate) {
                        min = view.getTop() + this.mSpinner;
                    }
                    canvas.drawRect(view.getLeft(), min, view.getRight(), view.getBottom(), this.mPaint);
                }
                if (this.mEnableClipFooterWhenFixedBehind && this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                    canvas.save();
                    canvas.clipRect(view.getLeft(), min, view.getRight(), view.getBottom());
                    boolean drawChild2 = super.drawChild(canvas, view, j);
                    canvas.restore();
                    return drawChild2;
                }
            }
        }
        return super.drawChild(canvas, view, j);
    }

    @Override // android.view.View
    public void computeScroll() {
        float currY;
        this.mScroller.getCurrY();
        if (this.mScroller.computeScrollOffset()) {
            int finalY = this.mScroller.getFinalY();
            if ((finalY < 0 && ((this.mEnableOverScrollDrag || isEnableRefreshOrLoadMore(this.mEnableRefresh)) && this.mRefreshContent.canRefresh())) || (finalY > 0 && ((this.mEnableOverScrollDrag || isEnableRefreshOrLoadMore(this.mEnableLoadMore)) && this.mRefreshContent.canLoadMore()))) {
                if (this.mVerticalPermit) {
                    if (Build.VERSION.SDK_INT >= 14) {
                        currY = finalY > 0 ? -this.mScroller.getCurrVelocity() : this.mScroller.getCurrVelocity();
                    } else {
                        currY = ((this.mScroller.getCurrY() - finalY) * 1.0f) / Math.max(this.mScroller.getDuration() - this.mScroller.timePassed(), 1);
                    }
                    animSpinnerBounce(currY);
                }
                this.mScroller.forceFinished(true);
                return;
            }
            this.mVerticalPermit = true;
            invalidate();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x00ce, code lost:
        if (r4.isFinishing == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d4, code lost:
        if (r22.mState.isHeader == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00e2, code lost:
        if (r4.isFinishing == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00e8, code lost:
        if (r22.mState.isFooter == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0116, code lost:
        if (r6 != 3) goto L98;
     */
    /* JADX WARN: Removed duplicated region for block: B:198:0x02cb  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        char c;
        RefreshInternal refreshInternal;
        RefreshInternal refreshInternal2;
        int actionMasked = motionEvent.getActionMasked();
        int i = 1;
        boolean z = actionMasked == 6;
        int actionIndex = z ? motionEvent.getActionIndex() : -1;
        int pointerCount = motionEvent.getPointerCount();
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i2 = 0; i2 < pointerCount; i2++) {
            if (actionIndex != i2) {
                f += motionEvent.getX(i2);
                f2 += motionEvent.getY(i2);
            }
        }
        if (z) {
            pointerCount--;
        }
        float f3 = pointerCount;
        float f4 = f / f3;
        float f5 = f2 / f3;
        if ((actionMasked == 6 || actionMasked == 5) && this.mIsBeingDragged) {
            this.mTouchY += f5 - this.mLastTouchY;
        }
        this.mLastTouchX = f4;
        this.mLastTouchY = f5;
        if (this.mNestedInProgress) {
            int i3 = this.mTotalUnconsumed;
            boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
            if (actionMasked == 2 && i3 == this.mTotalUnconsumed) {
                int i4 = (int) this.mLastTouchX;
                int width = getWidth();
                float f6 = this.mLastTouchX;
                if (width != 0) {
                    i = width;
                }
                float f7 = f6 / i;
                if (isEnableRefreshOrLoadMore(this.mEnableRefresh) && this.mSpinner > 0 && (refreshInternal2 = this.mRefreshHeader) != null && refreshInternal2.isSupportHorizontalDrag()) {
                    this.mRefreshHeader.onHorizontalDrag(f7, i4, width);
                } else if (isEnableRefreshOrLoadMore(this.mEnableLoadMore) && this.mSpinner < 0 && (refreshInternal = this.mRefreshFooter) != null && refreshInternal.isSupportHorizontalDrag()) {
                    this.mRefreshFooter.onHorizontalDrag(f7, i4, width);
                }
            }
            return dispatchTouchEvent;
        }
        if (isEnabled() && (isEnableRefreshOrLoadMore(this.mEnableRefresh) || isEnableRefreshOrLoadMore(this.mEnableLoadMore) || this.mEnableOverScrollDrag)) {
            if (this.mHeaderNeedTouchEventWhenRefreshing) {
                RefreshState refreshState = this.mState;
                if (!refreshState.isOpening) {
                }
            }
            if (this.mFooterNeedTouchEventWhenLoading) {
                RefreshState refreshState2 = this.mState;
                if (!refreshState2.isOpening) {
                }
            }
            if (!interceptAnimatorByAction(actionMasked)) {
                RefreshState refreshState3 = this.mState;
                if (!refreshState3.isFinishing && ((refreshState3 != RefreshState.Loading || !this.mDisableContentWhenLoading) && (this.mState != RefreshState.Refreshing || !this.mDisableContentWhenRefresh))) {
                    if (actionMasked == 0) {
                        this.mCurrentVelocity = 0;
                        this.mVelocityTracker.addMovement(motionEvent);
                        this.mScroller.forceFinished(true);
                        this.mTouchX = f4;
                        this.mTouchY = f5;
                        this.mLastSpinner = 0;
                        this.mTouchSpinner = this.mSpinner;
                        this.mIsBeingDragged = false;
                        this.mSuperDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
                        if (this.mState == RefreshState.TwoLevel && this.mTouchY < (getMeasuredHeight() * 5) / 6) {
                            this.mDragDirection = 'h';
                            return this.mSuperDispatchTouchEvent;
                        }
                        RefreshContent refreshContent = this.mRefreshContent;
                        if (refreshContent != null) {
                            refreshContent.onActionDown(motionEvent);
                        }
                        return true;
                    }
                    if (actionMasked == 1) {
                        this.mVelocityTracker.addMovement(motionEvent);
                        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                        this.mCurrentVelocity = (int) this.mVelocityTracker.getYVelocity();
                        startFlingIfNeed(null);
                    } else {
                        if (actionMasked == 2) {
                            float f8 = f4 - this.mTouchX;
                            float f9 = f5 - this.mTouchY;
                            this.mVelocityTracker.addMovement(motionEvent);
                            if (!this.mIsBeingDragged && (c = this.mDragDirection) != 'h' && this.mRefreshContent != null) {
                                if (c == 'v' || (Math.abs(f9) >= this.mTouchSlop && Math.abs(f8) < Math.abs(f9))) {
                                    this.mDragDirection = 'v';
                                    if (f9 > 0.0f && (this.mSpinner < 0 || ((this.mEnableOverScrollDrag || isEnableRefreshOrLoadMore(this.mEnableRefresh)) && this.mRefreshContent.canRefresh()))) {
                                        this.mIsBeingDragged = true;
                                        this.mTouchY = f5 - this.mTouchSlop;
                                    } else if (f9 < 0.0f && (this.mSpinner > 0 || ((this.mEnableOverScrollDrag || isEnableRefreshOrLoadMore(this.mEnableLoadMore)) && ((this.mState == RefreshState.Loading && this.mFooterLocked) || this.mRefreshContent.canLoadMore())))) {
                                        this.mIsBeingDragged = true;
                                        this.mTouchY = this.mTouchSlop + f5;
                                    }
                                    if (this.mIsBeingDragged) {
                                        f9 = f5 - this.mTouchY;
                                        if (this.mSuperDispatchTouchEvent) {
                                            motionEvent.setAction(3);
                                            super.dispatchTouchEvent(motionEvent);
                                        }
                                        int i5 = this.mSpinner;
                                        if (i5 > 0 || (i5 == 0 && f9 > 0.0f)) {
                                            this.mKernel.setState(RefreshState.PullDownToRefresh);
                                        } else {
                                            this.mKernel.setState(RefreshState.PullUpToLoad);
                                        }
                                        ViewParent parent = getParent();
                                        if (parent != null) {
                                            parent.requestDisallowInterceptTouchEvent(true);
                                        }
                                    }
                                } else if (Math.abs(f8) >= this.mTouchSlop && Math.abs(f8) > Math.abs(f9) && this.mDragDirection != 'v') {
                                    this.mDragDirection = 'h';
                                }
                            }
                            if (this.mIsBeingDragged) {
                                int i6 = ((int) f9) + this.mTouchSpinner;
                                if ((this.mViceState.isHeader && (i6 < 0 || this.mLastSpinner < 0)) || (this.mViceState.isFooter && (i6 > 0 || this.mLastSpinner > 0))) {
                                    this.mLastSpinner = i6;
                                    long eventTime = motionEvent.getEventTime();
                                    if (this.mFalsifyEvent == null) {
                                        this.mFalsifyEvent = MotionEvent.obtain(eventTime, eventTime, 0, this.mTouchX + f8, this.mTouchY, 0);
                                        super.dispatchTouchEvent(this.mFalsifyEvent);
                                    }
                                    MotionEvent obtain = MotionEvent.obtain(eventTime, eventTime, 2, this.mTouchX + f8, this.mTouchY + i6, 0);
                                    super.dispatchTouchEvent(obtain);
                                    if (this.mFooterLocked && f9 > this.mTouchSlop && this.mSpinner < 0) {
                                        this.mFooterLocked = false;
                                    }
                                    if (i6 > 0 && ((this.mEnableOverScrollDrag || isEnableRefreshOrLoadMore(this.mEnableRefresh)) && this.mRefreshContent.canRefresh())) {
                                        this.mLastTouchY = f5;
                                        this.mTouchY = f5;
                                        this.mTouchSpinner = 0;
                                        this.mKernel.setState(RefreshState.PullDownToRefresh);
                                    } else {
                                        if (i6 < 0 && ((this.mEnableOverScrollDrag || isEnableRefreshOrLoadMore(this.mEnableLoadMore)) && this.mRefreshContent.canLoadMore())) {
                                            this.mLastTouchY = f5;
                                            this.mTouchY = f5;
                                            this.mTouchSpinner = 0;
                                            this.mKernel.setState(RefreshState.PullUpToLoad);
                                        }
                                        if ((!this.mViceState.isHeader && i6 < 0) || (this.mViceState.isFooter && i6 > 0)) {
                                            if (this.mSpinner != 0) {
                                                moveSpinnerInfinitely(0.0f);
                                            }
                                            return true;
                                        }
                                        if (this.mFalsifyEvent != null) {
                                            this.mFalsifyEvent = null;
                                            obtain.setAction(3);
                                            super.dispatchTouchEvent(obtain);
                                        }
                                        obtain.recycle();
                                    }
                                    i6 = 0;
                                    if (!this.mViceState.isHeader) {
                                    }
                                    if (this.mFalsifyEvent != null) {
                                    }
                                    obtain.recycle();
                                }
                                moveSpinnerInfinitely(i6);
                                return true;
                            } else if (this.mFooterLocked && f9 > this.mTouchSlop && this.mSpinner < 0) {
                                this.mFooterLocked = false;
                            }
                        }
                        return super.dispatchTouchEvent(motionEvent);
                    }
                    this.mVelocityTracker.clear();
                    this.mDragDirection = 'n';
                    MotionEvent motionEvent2 = this.mFalsifyEvent;
                    if (motionEvent2 != null) {
                        motionEvent2.recycle();
                        this.mFalsifyEvent = null;
                        long eventTime2 = motionEvent.getEventTime();
                        MotionEvent obtain2 = MotionEvent.obtain(eventTime2, eventTime2, actionMasked, this.mTouchX, f5, 0);
                        super.dispatchTouchEvent(obtain2);
                        obtain2.recycle();
                    }
                    overSpinner();
                    if (this.mIsBeingDragged) {
                        this.mIsBeingDragged = false;
                        return true;
                    }
                    return super.dispatchTouchEvent(motionEvent);
                }
            }
            return false;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    protected boolean startFlingIfNeed(Float f) {
        float floatValue = f == null ? this.mCurrentVelocity : f.floatValue();
        if (Math.abs(floatValue) > this.mMinimumVelocity) {
            int i = this.mSpinner;
            if (i * floatValue < 0.0f) {
                RefreshState refreshState = this.mState;
                if (refreshState.isOpening) {
                    if (refreshState != RefreshState.TwoLevel && refreshState != this.mViceState) {
                        this.animationRunnable = new FlingRunnable(floatValue).start();
                        return true;
                    }
                } else if (i > this.mHeaderHeight * this.mHeaderTriggerRate || (-i) > this.mFooterHeight * this.mFooterTriggerRate) {
                    return true;
                }
            }
            if ((floatValue < 0.0f && ((this.mEnableOverScrollBounce && (this.mEnableOverScrollDrag || isEnableRefreshOrLoadMore(this.mEnableLoadMore))) || ((this.mState == RefreshState.Loading && this.mSpinner >= 0) || (this.mEnableAutoLoadMore && isEnableRefreshOrLoadMore(this.mEnableLoadMore))))) || (floatValue > 0.0f && ((this.mEnableOverScrollBounce && (this.mEnableOverScrollDrag || isEnableRefreshOrLoadMore(this.mEnableRefresh))) || (this.mState == RefreshState.Refreshing && this.mSpinner <= 0)))) {
                this.mVerticalPermit = false;
                this.mScroller.fling(0, 0, 0, (int) (-floatValue), 0, 0, -2147483647, Integer.MAX_VALUE);
                this.mScroller.computeScrollOffset();
                invalidate();
            }
        }
        return false;
    }

    protected boolean interceptAnimatorByAction(int i) {
        if (i == 0) {
            if (this.reboundAnimator != null) {
                RefreshState refreshState = this.mState;
                if (refreshState.isFinishing || refreshState == RefreshState.TwoLevelReleased) {
                    return true;
                }
                if (refreshState == RefreshState.PullDownCanceled) {
                    this.mKernel.setState(RefreshState.PullDownToRefresh);
                } else if (refreshState == RefreshState.PullUpCanceled) {
                    this.mKernel.setState(RefreshState.PullUpToLoad);
                }
                this.reboundAnimator.cancel();
                this.reboundAnimator = null;
            }
            this.animationRunnable = null;
        }
        return this.reboundAnimator != null;
    }

    protected void notifyStateChanged(RefreshState refreshState) {
        RefreshState refreshState2 = this.mState;
        if (refreshState2 != refreshState) {
            this.mState = refreshState;
            this.mViceState = refreshState;
            RefreshInternal refreshInternal = this.mRefreshHeader;
            RefreshInternal refreshInternal2 = this.mRefreshFooter;
            OnMultiPurposeListener onMultiPurposeListener = this.mOnMultiPurposeListener;
            if (refreshInternal != null) {
                refreshInternal.onStateChanged(this, refreshState2, refreshState);
            }
            if (refreshInternal2 != null) {
                refreshInternal2.onStateChanged(this, refreshState2, refreshState);
            }
            if (onMultiPurposeListener == null) {
                return;
            }
            onMultiPurposeListener.onStateChanged(this, refreshState2, refreshState);
        }
    }

    protected void setStateDirectLoading(boolean z) {
        if (this.mState != RefreshState.Loading) {
            this.mLastOpenTime = System.currentTimeMillis();
            this.mFooterLocked = true;
            notifyStateChanged(RefreshState.Loading);
            OnLoadMoreListener onLoadMoreListener = this.mLoadMoreListener;
            if (onLoadMoreListener != null) {
                if (z) {
                    onLoadMoreListener.onLoadMore(this);
                }
            } else if (this.mOnMultiPurposeListener == null) {
                finishLoadMore(2000);
            }
            RefreshInternal refreshInternal = this.mRefreshFooter;
            if (refreshInternal != null) {
                int i = this.mFooterHeight;
                refreshInternal.onStartAnimator(this, i, (int) (this.mFooterMaxDragRate * i));
            }
            OnMultiPurposeListener onMultiPurposeListener = this.mOnMultiPurposeListener;
            if (onMultiPurposeListener == null || !(this.mRefreshFooter instanceof RefreshFooter)) {
                return;
            }
            if (onMultiPurposeListener != null && z) {
                onMultiPurposeListener.onLoadMore(this);
            }
            int i2 = this.mFooterHeight;
            this.mOnMultiPurposeListener.onFooterStartAnimator((RefreshFooter) this.mRefreshFooter, i2, (int) (this.mFooterMaxDragRate * i2));
        }
    }

    protected void setStateLoading() {
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SmartRefreshLayout.this.setStateDirectLoading(true);
            }
        };
        notifyStateChanged(RefreshState.LoadReleased);
        ValueAnimator animSpinner = this.mKernel.animSpinner(-this.mFooterHeight);
        if (animSpinner != null) {
            animSpinner.addListener(animatorListenerAdapter);
        }
        RefreshInternal refreshInternal = this.mRefreshFooter;
        if (refreshInternal != null) {
            int i = this.mFooterHeight;
            refreshInternal.onReleased(this, i, (int) (this.mFooterMaxDragRate * i));
        }
        OnMultiPurposeListener onMultiPurposeListener = this.mOnMultiPurposeListener;
        if (onMultiPurposeListener != null) {
            RefreshInternal refreshInternal2 = this.mRefreshFooter;
            if (refreshInternal2 instanceof RefreshFooter) {
                int i2 = this.mFooterHeight;
                onMultiPurposeListener.onFooterReleased((RefreshFooter) refreshInternal2, i2, (int) (this.mFooterMaxDragRate * i2));
            }
        }
        if (animSpinner == null) {
            animatorListenerAdapter.onAnimationEnd(null);
        }
    }

    protected void setStateRefreshing() {
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SmartRefreshLayout.this.mLastOpenTime = System.currentTimeMillis();
                SmartRefreshLayout.this.notifyStateChanged(RefreshState.Refreshing);
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                OnRefreshListener onRefreshListener = smartRefreshLayout.mRefreshListener;
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh(smartRefreshLayout);
                } else if (smartRefreshLayout.mOnMultiPurposeListener == null) {
                    smartRefreshLayout.finishRefresh(3000);
                }
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                RefreshInternal refreshInternal = smartRefreshLayout2.mRefreshHeader;
                if (refreshInternal != null) {
                    int i = smartRefreshLayout2.mHeaderHeight;
                    refreshInternal.onStartAnimator(smartRefreshLayout2, i, (int) (smartRefreshLayout2.mHeaderMaxDragRate * i));
                }
                SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                OnMultiPurposeListener onMultiPurposeListener = smartRefreshLayout3.mOnMultiPurposeListener;
                if (onMultiPurposeListener == null || !(smartRefreshLayout3.mRefreshHeader instanceof RefreshHeader)) {
                    return;
                }
                onMultiPurposeListener.onRefresh(smartRefreshLayout3);
                SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                int i2 = smartRefreshLayout4.mHeaderHeight;
                smartRefreshLayout4.mOnMultiPurposeListener.onHeaderStartAnimator((RefreshHeader) smartRefreshLayout4.mRefreshHeader, i2, (int) (smartRefreshLayout4.mHeaderMaxDragRate * i2));
            }
        };
        notifyStateChanged(RefreshState.RefreshReleased);
        ValueAnimator animSpinner = this.mKernel.animSpinner(this.mHeaderHeight);
        if (animSpinner != null) {
            animSpinner.addListener(animatorListenerAdapter);
        }
        RefreshInternal refreshInternal = this.mRefreshHeader;
        if (refreshInternal != null) {
            int i = this.mHeaderHeight;
            refreshInternal.onReleased(this, i, (int) (this.mHeaderMaxDragRate * i));
        }
        OnMultiPurposeListener onMultiPurposeListener = this.mOnMultiPurposeListener;
        if (onMultiPurposeListener != null) {
            RefreshInternal refreshInternal2 = this.mRefreshHeader;
            if (refreshInternal2 instanceof RefreshHeader) {
                int i2 = this.mHeaderHeight;
                onMultiPurposeListener.onHeaderReleased((RefreshHeader) refreshInternal2, i2, (int) (this.mHeaderMaxDragRate * i2));
            }
        }
        if (animSpinner == null) {
            animatorListenerAdapter.onAnimationEnd(null);
        }
    }

    protected void resetStatus() {
        RefreshState refreshState = this.mState;
        RefreshState refreshState2 = RefreshState.None;
        if (refreshState != refreshState2 && this.mSpinner == 0) {
            notifyStateChanged(refreshState2);
        }
        if (this.mSpinner != 0) {
            this.mKernel.animSpinner(0);
        }
    }

    protected void setViceState(RefreshState refreshState) {
        RefreshState refreshState2 = this.mState;
        if (refreshState2.isDragging && refreshState2.isHeader != refreshState.isHeader) {
            notifyStateChanged(RefreshState.None);
        }
        if (this.mViceState != refreshState) {
            this.mViceState = refreshState;
        }
    }

    protected boolean isEnableTranslationContent(boolean z, RefreshInternal refreshInternal) {
        return z || this.mEnablePureScrollMode || refreshInternal == null || refreshInternal.getSpinnerStyle() == SpinnerStyle.FixedBehind;
    }

    protected boolean isEnableRefreshOrLoadMore(boolean z) {
        return z && !this.mEnablePureScrollMode;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes3.dex */
    public class FlingRunnable implements Runnable {
        int mOffset;
        float mVelocity;
        int mFrameDelay = 10;
        float mDamping = 0.98f;
        long mStartTime = 0;
        long mLastTime = AnimationUtils.currentAnimationTimeMillis();

        FlingRunnable(float f) {
            this.mVelocity = f;
            this.mOffset = SmartRefreshLayout.this.mSpinner;
        }

        /* JADX WARN: Code restructure failed: missing block: B:31:0x00a1, code lost:
            if (r0 < (-r1.mFooterHeight)) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x004f, code lost:
            if (r0.mSpinner > r0.mHeaderHeight) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x0041, code lost:
            if (r0.mSpinner >= (-r0.mFooterHeight)) goto L42;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Runnable start() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            RefreshState refreshState = smartRefreshLayout.mState;
            if (refreshState.isFinishing) {
                return null;
            }
            if (smartRefreshLayout.mSpinner != 0) {
                if (refreshState.isOpening || (smartRefreshLayout.mFooterNoMoreData && smartRefreshLayout.mEnableFooterFollowWhenNoMoreData && smartRefreshLayout.isEnableRefreshOrLoadMore(smartRefreshLayout.mEnableLoadMore))) {
                    SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                    if (smartRefreshLayout2.mState == RefreshState.Loading || (smartRefreshLayout2.mFooterNoMoreData && smartRefreshLayout2.mEnableFooterFollowWhenNoMoreData && smartRefreshLayout2.isEnableRefreshOrLoadMore(smartRefreshLayout2.mEnableLoadMore))) {
                        SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                    }
                    SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                    if (smartRefreshLayout4.mState == RefreshState.Refreshing) {
                    }
                }
                int i = SmartRefreshLayout.this.mSpinner;
                float f = this.mVelocity;
                int i2 = i;
                int i3 = 0;
                while (true) {
                    if (i * i2 <= 0) {
                        break;
                    }
                    i3++;
                    f = (float) (f * Math.pow(this.mDamping, (this.mFrameDelay * i3) / 10));
                    float f2 = ((this.mFrameDelay * 1.0f) / 1000.0f) * f;
                    if (Math.abs(f2) < 1.0f) {
                        SmartRefreshLayout smartRefreshLayout5 = SmartRefreshLayout.this;
                        RefreshState refreshState2 = smartRefreshLayout5.mState;
                        if (refreshState2.isOpening && (refreshState2 != RefreshState.Refreshing || i2 <= smartRefreshLayout5.mHeaderHeight)) {
                            SmartRefreshLayout smartRefreshLayout6 = SmartRefreshLayout.this;
                            if (smartRefreshLayout6.mState != RefreshState.Refreshing) {
                            }
                        }
                        return null;
                    }
                    i2 = (int) (i2 + f2);
                }
            }
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            SmartRefreshLayout.this.postDelayed(this, this.mFrameDelay);
            return this;
        }

        @Override // java.lang.Runnable
        public void run() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.animationRunnable != this || smartRefreshLayout.mState.isFinishing) {
                return;
            }
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.mVelocity = (float) (this.mVelocity * Math.pow(this.mDamping, (currentAnimationTimeMillis - this.mStartTime) / (1000 / this.mFrameDelay)));
            float f = this.mVelocity * ((((float) (currentAnimationTimeMillis - this.mLastTime)) * 1.0f) / 1000.0f);
            if (Math.abs(f) > 1.0f) {
                this.mLastTime = currentAnimationTimeMillis;
                this.mOffset = (int) (this.mOffset + f);
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                int i = smartRefreshLayout2.mSpinner;
                int i2 = this.mOffset;
                if (i * i2 > 0) {
                    smartRefreshLayout2.mKernel.moveSpinner(i2, true);
                    SmartRefreshLayout.this.postDelayed(this, this.mFrameDelay);
                    return;
                }
                smartRefreshLayout2.animationRunnable = null;
                smartRefreshLayout2.mKernel.moveSpinner(0, true);
                SmartUtil.fling(SmartRefreshLayout.this.mRefreshContent.getScrollableView(), (int) (-this.mVelocity));
                SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                if (!smartRefreshLayout3.mFooterLocked || f <= 0.0f) {
                    return;
                }
                smartRefreshLayout3.mFooterLocked = false;
                return;
            }
            SmartRefreshLayout.this.animationRunnable = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes3.dex */
    public class BounceRunnable implements Runnable {
        int mSmoothDistance;
        float mVelocity;
        int mFrame = 0;
        int mFrameDelay = 10;
        float mOffset = 0.0f;
        long mLastTime = AnimationUtils.currentAnimationTimeMillis();

        BounceRunnable(float f, int i) {
            this.mVelocity = f;
            this.mSmoothDistance = i;
            SmartRefreshLayout.this.postDelayed(this, this.mFrameDelay);
        }

        @Override // java.lang.Runnable
        public void run() {
            int i;
            int i2;
            int i3;
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.animationRunnable != this || smartRefreshLayout.mState.isFinishing) {
                return;
            }
            if (Math.abs(smartRefreshLayout.mSpinner) >= Math.abs(this.mSmoothDistance)) {
                if (this.mSmoothDistance != 0) {
                    this.mFrame = this.mFrame + 1;
                    this.mVelocity = (float) (this.mVelocity * Math.pow(0.44999998807907104d, i3 * 2));
                } else {
                    this.mFrame = this.mFrame + 1;
                    this.mVelocity = (float) (this.mVelocity * Math.pow(0.8500000238418579d, i2 * 2));
                }
            } else {
                this.mFrame = this.mFrame + 1;
                this.mVelocity = (float) (this.mVelocity * Math.pow(0.949999988079071d, i * 2));
            }
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            float f = this.mVelocity * ((((float) (currentAnimationTimeMillis - this.mLastTime)) * 1.0f) / 1000.0f);
            if (Math.abs(f) >= 1.0f) {
                this.mLastTime = currentAnimationTimeMillis;
                this.mOffset += f;
                SmartRefreshLayout.this.moveSpinnerInfinitely(this.mOffset);
                SmartRefreshLayout.this.postDelayed(this, this.mFrameDelay);
                return;
            }
            SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
            smartRefreshLayout2.animationRunnable = null;
            if (Math.abs(smartRefreshLayout2.mSpinner) < Math.abs(this.mSmoothDistance)) {
                return;
            }
            SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
            smartRefreshLayout3.animSpinner(this.mSmoothDistance, 0, smartRefreshLayout3.mReboundInterpolator, Math.min(Math.max((int) DensityUtil.px2dp(Math.abs(SmartRefreshLayout.this.mSpinner - this.mSmoothDistance)), 30), 100) * 10);
        }
    }

    protected ValueAnimator animSpinner(int i, int i2, Interpolator interpolator, int i3) {
        if (this.mSpinner != i) {
            ValueAnimator valueAnimator = this.reboundAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.animationRunnable = null;
            this.reboundAnimator = ValueAnimator.ofInt(this.mSpinner, i);
            this.reboundAnimator.setDuration(i3);
            this.reboundAnimator.setInterpolator(interpolator);
            this.reboundAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    super.onAnimationEnd(animator);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    smartRefreshLayout.reboundAnimator = null;
                    if (smartRefreshLayout.mSpinner == 0) {
                        RefreshState refreshState = smartRefreshLayout.mState;
                        RefreshState refreshState2 = RefreshState.None;
                        if (refreshState == refreshState2 || refreshState.isOpening) {
                            return;
                        }
                        smartRefreshLayout.notifyStateChanged(refreshState2);
                        return;
                    }
                    RefreshState refreshState3 = smartRefreshLayout.mState;
                    if (refreshState3 == smartRefreshLayout.mViceState) {
                        return;
                    }
                    smartRefreshLayout.setViceState(refreshState3);
                }
            });
            this.reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    SmartRefreshLayout.this.mKernel.moveSpinner(((Integer) valueAnimator2.getAnimatedValue()).intValue(), false);
                }
            });
            this.reboundAnimator.setStartDelay(i2);
            this.reboundAnimator.start();
            return this.reboundAnimator;
        }
        return null;
    }

    protected void animSpinnerBounce(float f) {
        RefreshState refreshState;
        if (this.reboundAnimator == null) {
            if (f > 0.0f && ((refreshState = this.mState) == RefreshState.Refreshing || refreshState == RefreshState.TwoLevel)) {
                this.animationRunnable = new BounceRunnable(f, this.mHeaderHeight);
            } else if (f < 0.0f && (this.mState == RefreshState.Loading || ((this.mEnableFooterFollowWhenNoMoreData && this.mFooterNoMoreData && isEnableRefreshOrLoadMore(this.mEnableLoadMore)) || (this.mEnableAutoLoadMore && !this.mFooterNoMoreData && isEnableRefreshOrLoadMore(this.mEnableLoadMore) && this.mState != RefreshState.Refreshing)))) {
                this.animationRunnable = new BounceRunnable(f, -this.mFooterHeight);
            } else if (this.mSpinner != 0 || !this.mEnableOverScrollBounce) {
            } else {
                this.animationRunnable = new BounceRunnable(f, 0);
            }
        }
    }

    protected void overSpinner() {
        RefreshState refreshState = this.mState;
        if (refreshState == RefreshState.TwoLevel) {
            if (this.mCurrentVelocity > -1000 && this.mSpinner > getMeasuredHeight() / 2) {
                ValueAnimator animSpinner = this.mKernel.animSpinner(getMeasuredHeight());
                if (animSpinner == null) {
                    return;
                }
                animSpinner.setDuration(this.mFloorDuration);
            } else if (!this.mIsBeingDragged) {
            } else {
                this.mKernel.finishTwoLevel();
            }
        } else if (refreshState == RefreshState.Loading || (this.mEnableFooterFollowWhenNoMoreData && this.mFooterNoMoreData && this.mSpinner < 0 && isEnableRefreshOrLoadMore(this.mEnableLoadMore))) {
            int i = this.mSpinner;
            int i2 = this.mFooterHeight;
            if (i < (-i2)) {
                this.mKernel.animSpinner(-i2);
            } else if (i <= 0) {
            } else {
                this.mKernel.animSpinner(0);
            }
        } else {
            RefreshState refreshState2 = this.mState;
            if (refreshState2 == RefreshState.Refreshing) {
                int i3 = this.mSpinner;
                int i4 = this.mHeaderHeight;
                if (i3 > i4) {
                    this.mKernel.animSpinner(i4);
                } else if (i3 >= 0) {
                } else {
                    this.mKernel.animSpinner(0);
                }
            } else if (refreshState2 == RefreshState.PullDownToRefresh) {
                this.mKernel.setState(RefreshState.PullDownCanceled);
            } else if (refreshState2 == RefreshState.PullUpToLoad) {
                this.mKernel.setState(RefreshState.PullUpCanceled);
            } else if (refreshState2 == RefreshState.ReleaseToRefresh) {
                this.mKernel.setState(RefreshState.Refreshing);
            } else if (refreshState2 == RefreshState.ReleaseToLoad) {
                this.mKernel.setState(RefreshState.Loading);
            } else if (refreshState2 == RefreshState.ReleaseToTwoLevel) {
                this.mKernel.setState(RefreshState.TwoLevelReleased);
            } else if (refreshState2 == RefreshState.RefreshReleased) {
                if (this.reboundAnimator != null) {
                    return;
                }
                this.mKernel.animSpinner(this.mHeaderHeight);
            } else if (refreshState2 == RefreshState.LoadReleased) {
                if (this.reboundAnimator != null) {
                    return;
                }
                this.mKernel.animSpinner(-this.mFooterHeight);
            } else if (this.mSpinner == 0) {
            } else {
                this.mKernel.animSpinner(0);
            }
        }
    }

    protected void moveSpinnerInfinitely(float f) {
        RefreshState refreshState;
        if (this.mState == RefreshState.TwoLevel && f > 0.0f) {
            this.mKernel.moveSpinner(Math.min((int) f, getMeasuredHeight()), true);
        } else if (this.mState == RefreshState.Refreshing && f >= 0.0f) {
            int i = this.mHeaderHeight;
            if (f < i) {
                this.mKernel.moveSpinner((int) f, true);
            } else {
                double d = (this.mHeaderMaxDragRate - 1.0f) * i;
                int max = Math.max((this.mScreenHeightPixels * 4) / 3, getHeight());
                int i2 = this.mHeaderHeight;
                double d2 = max - i2;
                double max2 = Math.max(0.0f, (f - i2) * this.mDragRate);
                double d3 = -max2;
                if (d2 == 0.0d) {
                    d2 = 1.0d;
                }
                this.mKernel.moveSpinner(((int) Math.min(d * (1.0d - Math.pow(100.0d, d3 / d2)), max2)) + this.mHeaderHeight, true);
            }
        } else if (f < 0.0f && (this.mState == RefreshState.Loading || ((this.mEnableFooterFollowWhenNoMoreData && this.mFooterNoMoreData && isEnableRefreshOrLoadMore(this.mEnableLoadMore)) || (this.mEnableAutoLoadMore && !this.mFooterNoMoreData && isEnableRefreshOrLoadMore(this.mEnableLoadMore))))) {
            int i3 = this.mFooterHeight;
            if (f > (-i3)) {
                this.mKernel.moveSpinner((int) f, true);
            } else {
                double d4 = (this.mFooterMaxDragRate - 1.0f) * i3;
                int max3 = Math.max((this.mScreenHeightPixels * 4) / 3, getHeight());
                int i4 = this.mFooterHeight;
                double d5 = max3 - i4;
                double d6 = -Math.min(0.0f, (i4 + f) * this.mDragRate);
                double d7 = -d6;
                if (d5 == 0.0d) {
                    d5 = 1.0d;
                }
                this.mKernel.moveSpinner(((int) (-Math.min(d4 * (1.0d - Math.pow(100.0d, d7 / d5)), d6))) - this.mFooterHeight, true);
            }
        } else if (f >= 0.0f) {
            double d8 = this.mHeaderMaxDragRate * this.mHeaderHeight;
            double max4 = Math.max(this.mScreenHeightPixels / 2, getHeight());
            double max5 = Math.max(0.0f, this.mDragRate * f);
            double d9 = -max5;
            if (max4 == 0.0d) {
                max4 = 1.0d;
            }
            this.mKernel.moveSpinner((int) Math.min(d8 * (1.0d - Math.pow(100.0d, d9 / max4)), max5), true);
        } else {
            double d10 = this.mFooterMaxDragRate * this.mFooterHeight;
            double max6 = Math.max(this.mScreenHeightPixels / 2, getHeight());
            double d11 = -Math.min(0.0f, this.mDragRate * f);
            double d12 = -d11;
            if (max6 == 0.0d) {
                max6 = 1.0d;
            }
            this.mKernel.moveSpinner((int) (-Math.min(d10 * (1.0d - Math.pow(100.0d, d12 / max6)), d11)), true);
        }
        if (!this.mEnableAutoLoadMore || this.mFooterNoMoreData || !isEnableRefreshOrLoadMore(this.mEnableLoadMore) || f >= 0.0f || (refreshState = this.mState) == RefreshState.Refreshing || refreshState == RefreshState.Loading || refreshState == RefreshState.LoadFinish) {
            return;
        }
        if (this.mDisableContentWhenLoading) {
            this.animationRunnable = null;
            this.mKernel.animSpinner(-this.mFooterHeight);
        }
        setStateDirectLoading(false);
        postDelayed(new Runnable() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.5
            @Override // java.lang.Runnable
            public void run() {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                OnLoadMoreListener onLoadMoreListener = smartRefreshLayout.mLoadMoreListener;
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore(smartRefreshLayout);
                } else if (smartRefreshLayout.mOnMultiPurposeListener == null) {
                    smartRefreshLayout.finishLoadMore(2000);
                }
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                OnMultiPurposeListener onMultiPurposeListener = smartRefreshLayout2.mOnMultiPurposeListener;
                if (onMultiPurposeListener != null) {
                    onMultiPurposeListener.onLoadMore(smartRefreshLayout2);
                }
            }
        }, this.mReboundDuration);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* loaded from: classes3.dex */
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int backgroundColor;
        public SpinnerStyle spinnerStyle;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.backgroundColor = 0;
            this.spinnerStyle = null;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SmartRefreshLayout_Layout);
            this.backgroundColor = obtainStyledAttributes.getColor(R$styleable.SmartRefreshLayout_Layout_layout_srlBackgroundColor, this.backgroundColor);
            if (obtainStyledAttributes.hasValue(R$styleable.SmartRefreshLayout_Layout_layout_srlSpinnerStyle)) {
                this.spinnerStyle = SpinnerStyle.values()[obtainStyledAttributes.getInt(R$styleable.SmartRefreshLayout_Layout_layout_srlSpinnerStyle, SpinnerStyle.Translate.ordinal())];
            }
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.backgroundColor = 0;
            this.spinnerStyle = null;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.backgroundColor = 0;
            this.spinnerStyle = null;
        }
    }

    @Override // android.view.ViewGroup, android.support.p002v4.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return this.mNestedParent.getNestedScrollAxes();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.p002v4.view.NestedScrollingParent
    public boolean onStartNestedScroll(@NonNull View view, @NonNull View view2, int i) {
        return (isEnabled() && isNestedScrollingEnabled() && (i & 2) != 0) && (this.mEnableOverScrollDrag || isEnableRefreshOrLoadMore(this.mEnableRefresh) || isEnableRefreshOrLoadMore(this.mEnableLoadMore));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.p002v4.view.NestedScrollingParent
    public void onNestedScrollAccepted(@NonNull View view, @NonNull View view2, int i) {
        this.mNestedParent.onNestedScrollAccepted(view, view2, i);
        this.mNestedChild.startNestedScroll(i & 2);
        this.mTotalUnconsumed = this.mSpinner;
        this.mNestedInProgress = true;
        interceptAnimatorByAction(0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.p002v4.view.NestedScrollingParent
    public void onNestedPreScroll(@NonNull View view, int i, int i2, @NonNull int[] iArr) {
        int i3;
        int i4 = this.mTotalUnconsumed;
        if (i2 * i4 > 0) {
            if (Math.abs(i2) > Math.abs(this.mTotalUnconsumed)) {
                i3 = this.mTotalUnconsumed;
                this.mTotalUnconsumed = 0;
            } else {
                this.mTotalUnconsumed -= i2;
                i3 = i2;
            }
            moveSpinnerInfinitely(this.mTotalUnconsumed);
            RefreshState refreshState = this.mViceState;
            if (refreshState.isOpening || refreshState == RefreshState.None) {
                if (this.mSpinner > 0) {
                    this.mKernel.setState(RefreshState.PullDownToRefresh);
                } else {
                    this.mKernel.setState(RefreshState.PullUpToLoad);
                }
            }
        } else if (i2 <= 0 || !this.mFooterLocked) {
            i3 = 0;
        } else {
            this.mTotalUnconsumed = i4 - i2;
            moveSpinnerInfinitely(this.mTotalUnconsumed);
            i3 = i2;
        }
        this.mNestedChild.dispatchNestedPreScroll(i, i2 - i3, iArr, null);
        iArr[1] = iArr[1] + i3;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.p002v4.view.NestedScrollingParent
    public void onNestedScroll(@NonNull View view, int i, int i2, int i3, int i4) {
        this.mNestedChild.dispatchNestedScroll(i, i2, i3, i4, this.mParentOffsetInWindow);
        int i5 = i4 + this.mParentOffsetInWindow[1];
        if (i5 != 0 && (this.mEnableOverScrollDrag || ((i5 < 0 && isEnableRefreshOrLoadMore(this.mEnableRefresh)) || (i5 > 0 && isEnableRefreshOrLoadMore(this.mEnableLoadMore))))) {
            if (this.mViceState == RefreshState.None) {
                this.mKernel.setState(i5 > 0 ? RefreshState.PullUpToLoad : RefreshState.PullDownToRefresh);
            }
            int i6 = this.mTotalUnconsumed - i5;
            this.mTotalUnconsumed = i6;
            moveSpinnerInfinitely(i6);
        }
        if (!this.mFooterLocked || i2 >= 0) {
            return;
        }
        this.mFooterLocked = false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.p002v4.view.NestedScrollingParent
    public boolean onNestedPreFling(@NonNull View view, float f, float f2) {
        return (this.mFooterLocked && f2 > 0.0f) || startFlingIfNeed(Float.valueOf(-f2)) || this.mNestedChild.dispatchNestedPreFling(f, f2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.p002v4.view.NestedScrollingParent
    public boolean onNestedFling(@NonNull View view, float f, float f2, boolean z) {
        return this.mNestedChild.dispatchNestedFling(f, f2, z);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, android.support.p002v4.view.NestedScrollingParent
    public void onStopNestedScroll(@NonNull View view) {
        this.mNestedParent.onStopNestedScroll(view);
        this.mNestedInProgress = false;
        this.mTotalUnconsumed = 0;
        overSpinner();
        this.mNestedChild.stopNestedScroll();
    }

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean z) {
        this.mNestedChild.setNestedScrollingEnabled(z);
    }

    @Override // android.view.View
    public boolean isNestedScrollingEnabled() {
        return this.mNestedChild.isNestedScrollingEnabled();
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    /* renamed from: setHeaderMaxDragRate */
    public SmartRefreshLayout mo6488setHeaderMaxDragRate(float f) {
        this.mHeaderMaxDragRate = f;
        RefreshInternal refreshInternal = this.mRefreshHeader;
        if (refreshInternal != null && this.mHandler != null) {
            RefreshKernel refreshKernel = this.mKernel;
            int i = this.mHeaderHeight;
            refreshInternal.onInitialized(refreshKernel, i, (int) (this.mHeaderMaxDragRate * i));
        } else {
            this.mHeaderHeightStatus = this.mHeaderHeightStatus.unNotify();
        }
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    /* renamed from: setEnableLoadMore */
    public SmartRefreshLayout mo6487setEnableLoadMore(boolean z) {
        this.mManualLoadMore = true;
        this.mEnableLoadMore = z;
        return this;
    }

    public SmartRefreshLayout setEnableRefresh(boolean z) {
        this.mEnableRefresh = z;
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    /* renamed from: setEnableAutoLoadMore */
    public SmartRefreshLayout mo6486setEnableAutoLoadMore(boolean z) {
        this.mEnableAutoLoadMore = z;
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    public RefreshLayout setEnableNestedScroll(boolean z) {
        setNestedScrollingEnabled(z);
        return this;
    }

    public SmartRefreshLayout setRefreshHeader(@NonNull RefreshHeader refreshHeader) {
        return setRefreshHeader(refreshHeader, -1, -2);
    }

    public SmartRefreshLayout setRefreshHeader(@NonNull RefreshHeader refreshHeader, int i, int i2) {
        RefreshInternal refreshInternal = this.mRefreshHeader;
        if (refreshInternal != null) {
            super.removeView(refreshInternal.getView());
        }
        this.mRefreshHeader = refreshHeader;
        this.mHeaderBackgroundColor = 0;
        this.mHeaderNeedTouchEventWhenRefreshing = false;
        this.mHeaderHeightStatus = this.mHeaderHeightStatus.unNotify();
        if (this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            super.addView(this.mRefreshHeader.getView(), 0, new LayoutParams(i, i2));
        } else {
            super.addView(this.mRefreshHeader.getView(), i, i2);
        }
        return this;
    }

    public SmartRefreshLayout setRefreshFooter(@NonNull RefreshFooter refreshFooter) {
        return setRefreshFooter(refreshFooter, -1, -2);
    }

    public SmartRefreshLayout setRefreshFooter(@NonNull RefreshFooter refreshFooter, int i, int i2) {
        RefreshInternal refreshInternal = this.mRefreshFooter;
        if (refreshInternal != null) {
            super.removeView(refreshInternal.getView());
        }
        this.mRefreshFooter = refreshFooter;
        this.mFooterBackgroundColor = 0;
        this.mFooterNeedTouchEventWhenLoading = false;
        this.mFooterHeightStatus = this.mFooterHeightStatus.unNotify();
        this.mEnableLoadMore = !this.mManualLoadMore || this.mEnableLoadMore;
        if (this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            super.addView(this.mRefreshFooter.getView(), 0, new LayoutParams(i, i2));
        } else {
            super.addView(this.mRefreshFooter.getView(), i, i2);
        }
        return this;
    }

    @Nullable
    public RefreshFooter getRefreshFooter() {
        RefreshInternal refreshInternal = this.mRefreshFooter;
        if (refreshInternal instanceof RefreshFooter) {
            return (RefreshFooter) refreshInternal;
        }
        return null;
    }

    @Nullable
    public RefreshHeader getRefreshHeader() {
        RefreshInternal refreshInternal = this.mRefreshHeader;
        if (refreshInternal instanceof RefreshHeader) {
            return (RefreshHeader) refreshInternal;
        }
        return null;
    }

    public RefreshState getState() {
        return this.mState;
    }

    public SmartRefreshLayout setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mRefreshListener = onRefreshListener;
        return this;
    }

    public SmartRefreshLayout setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mLoadMoreListener = onLoadMoreListener;
        this.mEnableLoadMore = this.mEnableLoadMore || (!this.mManualLoadMore && onLoadMoreListener != null);
        return this;
    }

    public SmartRefreshLayout setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener onRefreshLoadMoreListener) {
        this.mRefreshListener = onRefreshLoadMoreListener;
        this.mLoadMoreListener = onRefreshLoadMoreListener;
        this.mEnableLoadMore = this.mEnableLoadMore || (!this.mManualLoadMore && onRefreshLoadMoreListener != null);
        return this;
    }

    @Deprecated
    public SmartRefreshLayout setNoMoreData(boolean z) {
        if (this.mState == RefreshState.Loading && z) {
            finishLoadMore();
        }
        this.mFooterNoMoreData = z;
        RefreshInternal refreshInternal = this.mRefreshFooter;
        if ((refreshInternal instanceof RefreshFooter) && !((RefreshFooter) refreshInternal).setNoMoreData(z)) {
            PrintStream printStream = System.out;
            printStream.println("Footer:" + this.mRefreshFooter + " NoMoreData is not supported.(不支持NoMoreData，请使用ClassicsFooter或者自定义)");
        }
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    public RefreshLayout resetNoMoreData() {
        this.mFooterNoMoreData = false;
        RefreshInternal refreshInternal = this.mRefreshFooter;
        if ((refreshInternal instanceof RefreshFooter) && !((RefreshFooter) refreshInternal).setNoMoreData(false)) {
            PrintStream printStream = System.out;
            printStream.println("Footer:" + this.mRefreshFooter + " NoMoreData is not supported.(不支持NoMoreData，请使用ClassicsFooter或者自定义)");
        }
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshLayout
    /* renamed from: finishRefresh */
    public SmartRefreshLayout mo6481finishRefresh() {
        return finishRefresh(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.mLastOpenTime))), 300));
    }

    public SmartRefreshLayout finishLoadMore() {
        return finishLoadMore(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.mLastOpenTime))), 300));
    }

    public SmartRefreshLayout finishRefresh(int i) {
        return finishRefresh(i, true);
    }

    public SmartRefreshLayout finishRefresh(int i, final boolean z) {
        if (this.mState == RefreshState.Refreshing && z) {
            resetNoMoreData();
        }
        postDelayed(new Runnable() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.6
            @Override // java.lang.Runnable
            public void run() {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                if (smartRefreshLayout.mState != RefreshState.Refreshing || smartRefreshLayout.mRefreshHeader == null || smartRefreshLayout.mRefreshContent == null) {
                    return;
                }
                smartRefreshLayout.notifyStateChanged(RefreshState.RefreshFinish);
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                int onFinish = smartRefreshLayout2.mRefreshHeader.onFinish(smartRefreshLayout2, z);
                SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                OnMultiPurposeListener onMultiPurposeListener = smartRefreshLayout3.mOnMultiPurposeListener;
                if (onMultiPurposeListener != null) {
                    RefreshInternal refreshInternal = smartRefreshLayout3.mRefreshHeader;
                    if (refreshInternal instanceof RefreshHeader) {
                        onMultiPurposeListener.onHeaderFinish((RefreshHeader) refreshInternal, z);
                    }
                }
                if (onFinish >= Integer.MAX_VALUE) {
                    return;
                }
                SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                if (smartRefreshLayout4.mIsBeingDragged || smartRefreshLayout4.mNestedInProgress) {
                    SmartRefreshLayout smartRefreshLayout5 = SmartRefreshLayout.this;
                    if (smartRefreshLayout5.mIsBeingDragged) {
                        smartRefreshLayout5.mTouchY = smartRefreshLayout5.mLastTouchY;
                        smartRefreshLayout5.mTouchSpinner = 0;
                        smartRefreshLayout5.mIsBeingDragged = false;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    SmartRefreshLayout smartRefreshLayout6 = SmartRefreshLayout.this;
                    SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis, 0, smartRefreshLayout6.mLastTouchX, (smartRefreshLayout6.mLastTouchY + smartRefreshLayout6.mSpinner) - (smartRefreshLayout6.mTouchSlop * 2), 0));
                    SmartRefreshLayout smartRefreshLayout7 = SmartRefreshLayout.this;
                    SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis, 2, smartRefreshLayout7.mLastTouchX, smartRefreshLayout7.mLastTouchY + smartRefreshLayout7.mSpinner, 0));
                    SmartRefreshLayout smartRefreshLayout8 = SmartRefreshLayout.this;
                    if (smartRefreshLayout8.mNestedInProgress) {
                        smartRefreshLayout8.mTotalUnconsumed = 0;
                    }
                }
                SmartRefreshLayout smartRefreshLayout9 = SmartRefreshLayout.this;
                int i2 = smartRefreshLayout9.mSpinner;
                if (i2 <= 0) {
                    if (i2 < 0) {
                        smartRefreshLayout9.animSpinner(0, onFinish, smartRefreshLayout9.mReboundInterpolator, smartRefreshLayout9.mReboundDuration);
                        return;
                    }
                    smartRefreshLayout9.mKernel.moveSpinner(0, false);
                    SmartRefreshLayout.this.resetStatus();
                    return;
                }
                ValueAnimator.AnimatorUpdateListener animatorUpdateListener = null;
                ValueAnimator animSpinner = smartRefreshLayout9.animSpinner(0, onFinish, smartRefreshLayout9.mReboundInterpolator, smartRefreshLayout9.mReboundDuration);
                SmartRefreshLayout smartRefreshLayout10 = SmartRefreshLayout.this;
                if (smartRefreshLayout10.mEnableScrollContentWhenRefreshed) {
                    animatorUpdateListener = smartRefreshLayout10.mRefreshContent.scrollContentWhenFinished(smartRefreshLayout10.mSpinner);
                }
                if (animSpinner == null || animatorUpdateListener == null) {
                    return;
                }
                animSpinner.addUpdateListener(animatorUpdateListener);
            }
        }, i <= 0 ? 1L : i);
        return this;
    }

    public SmartRefreshLayout finishLoadMore(int i) {
        return finishLoadMore(i, true, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.scwang.smartrefresh.layout.SmartRefreshLayout$7 */
    /* loaded from: classes3.dex */
    public class RunnableC30647 implements Runnable {
        final /* synthetic */ boolean val$noMoreData;
        final /* synthetic */ boolean val$success;

        RunnableC30647(boolean z, boolean z2) {
            this.val$success = z;
            this.val$noMoreData = z2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x004f, code lost:
            if (r2.mRefreshContent.canLoadMore() != false) goto L22;
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            boolean z = true;
            if (smartRefreshLayout.mState == RefreshState.Loading && smartRefreshLayout.mRefreshFooter != null && smartRefreshLayout.mRefreshContent != null) {
                smartRefreshLayout.notifyStateChanged(RefreshState.LoadFinish);
                SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                int onFinish = smartRefreshLayout2.mRefreshFooter.onFinish(smartRefreshLayout2, this.val$success);
                SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                OnMultiPurposeListener onMultiPurposeListener = smartRefreshLayout3.mOnMultiPurposeListener;
                if (onMultiPurposeListener != null) {
                    RefreshInternal refreshInternal = smartRefreshLayout3.mRefreshFooter;
                    if (refreshInternal instanceof RefreshFooter) {
                        onMultiPurposeListener.onFooterFinish((RefreshFooter) refreshInternal, this.val$success);
                    }
                }
                if (onFinish >= Integer.MAX_VALUE) {
                    return;
                }
                if (this.val$noMoreData) {
                    SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                    if (smartRefreshLayout4.mEnableFooterFollowWhenNoMoreData) {
                        if (smartRefreshLayout4.mSpinner < 0) {
                        }
                    }
                }
                z = false;
                SmartRefreshLayout smartRefreshLayout5 = SmartRefreshLayout.this;
                int i = smartRefreshLayout5.mSpinner;
                final int max = i - (z ? Math.max(i, -smartRefreshLayout5.mFooterHeight) : 0);
                SmartRefreshLayout smartRefreshLayout6 = SmartRefreshLayout.this;
                if (smartRefreshLayout6.mIsBeingDragged || smartRefreshLayout6.mNestedInProgress) {
                    SmartRefreshLayout smartRefreshLayout7 = SmartRefreshLayout.this;
                    if (smartRefreshLayout7.mIsBeingDragged) {
                        smartRefreshLayout7.mTouchY = smartRefreshLayout7.mLastTouchY;
                        smartRefreshLayout7.mIsBeingDragged = false;
                        smartRefreshLayout7.mTouchSpinner = smartRefreshLayout7.mSpinner - max;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    SmartRefreshLayout smartRefreshLayout8 = SmartRefreshLayout.this;
                    float f = max;
                    SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis, 0, smartRefreshLayout8.mLastTouchX, smartRefreshLayout8.mLastTouchY + f + (smartRefreshLayout8.mTouchSlop * 2), 0));
                    SmartRefreshLayout smartRefreshLayout9 = SmartRefreshLayout.this;
                    SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(currentTimeMillis, currentTimeMillis, 2, smartRefreshLayout9.mLastTouchX, smartRefreshLayout9.mLastTouchY + f, 0));
                    SmartRefreshLayout smartRefreshLayout10 = SmartRefreshLayout.this;
                    if (smartRefreshLayout10.mNestedInProgress) {
                        smartRefreshLayout10.mTotalUnconsumed = 0;
                    }
                }
                SmartRefreshLayout.this.postDelayed(new Runnable() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ValueAnimator valueAnimator;
                        SmartRefreshLayout smartRefreshLayout11 = SmartRefreshLayout.this;
                        ValueAnimator.AnimatorUpdateListener scrollContentWhenFinished = (!smartRefreshLayout11.mEnableScrollContentWhenLoaded || max >= 0) ? null : smartRefreshLayout11.mRefreshContent.scrollContentWhenFinished(smartRefreshLayout11.mSpinner);
                        if (scrollContentWhenFinished != null) {
                            scrollContentWhenFinished.onAnimationUpdate(ValueAnimator.ofInt(0, 0));
                        }
                        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.7.1.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationCancel(Animator animator) {
                                super.onAnimationEnd(animator);
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                RunnableC30647 runnableC30647 = RunnableC30647.this;
                                SmartRefreshLayout smartRefreshLayout12 = SmartRefreshLayout.this;
                                smartRefreshLayout12.mFooterLocked = false;
                                if (runnableC30647.val$noMoreData) {
                                    smartRefreshLayout12.setNoMoreData(true);
                                }
                                SmartRefreshLayout smartRefreshLayout13 = SmartRefreshLayout.this;
                                if (smartRefreshLayout13.mState == RefreshState.LoadFinish) {
                                    smartRefreshLayout13.notifyStateChanged(RefreshState.None);
                                }
                            }
                        };
                        RunnableC30647 runnableC30647 = RunnableC30647.this;
                        SmartRefreshLayout smartRefreshLayout12 = SmartRefreshLayout.this;
                        int i2 = smartRefreshLayout12.mSpinner;
                        if (i2 > 0) {
                            valueAnimator = smartRefreshLayout12.mKernel.animSpinner(0);
                        } else {
                            if (scrollContentWhenFinished != null || i2 == 0) {
                                ValueAnimator valueAnimator2 = SmartRefreshLayout.this.reboundAnimator;
                                if (valueAnimator2 != null) {
                                    valueAnimator2.cancel();
                                    SmartRefreshLayout.this.reboundAnimator = null;
                                }
                                SmartRefreshLayout.this.mKernel.moveSpinner(0, false);
                                SmartRefreshLayout.this.resetStatus();
                            } else if (runnableC30647.val$noMoreData && smartRefreshLayout12.mEnableFooterFollowWhenNoMoreData) {
                                int i3 = smartRefreshLayout12.mFooterHeight;
                                if (i2 >= (-i3)) {
                                    smartRefreshLayout12.notifyStateChanged(RefreshState.None);
                                } else {
                                    valueAnimator = smartRefreshLayout12.mKernel.animSpinner(-i3);
                                }
                            } else {
                                valueAnimator = SmartRefreshLayout.this.mKernel.animSpinner(0);
                            }
                            valueAnimator = null;
                        }
                        if (valueAnimator != null) {
                            valueAnimator.addListener(animatorListenerAdapter);
                        } else {
                            animatorListenerAdapter.onAnimationEnd(null);
                        }
                    }
                }, SmartRefreshLayout.this.mSpinner < 0 ? onFinish : 0L);
            } else if (!this.val$noMoreData) {
            } else {
                SmartRefreshLayout.this.setNoMoreData(true);
            }
        }
    }

    public SmartRefreshLayout finishLoadMore(int i, boolean z, boolean z2) {
        postDelayed(new RunnableC30647(z, z2), i <= 0 ? 1L : i);
        return this;
    }

    public SmartRefreshLayout finishLoadMoreWithNoMoreData() {
        return finishLoadMore(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.mLastOpenTime))), 300), true, true);
    }

    public boolean autoRefresh() {
        int i = this.mHandler == null ? LotteryDialog.MAX_VALUE : 0;
        int i2 = this.mReboundDuration;
        int i3 = this.mHeaderHeight;
        float f = ((this.mHeaderMaxDragRate / 2.0f) + 0.5f) * i3 * 1.0f;
        if (i3 == 0) {
            i3 = 1;
        }
        return autoRefresh(i, i2, f / i3, false);
    }

    @Deprecated
    public boolean autoRefresh(int i) {
        int i2 = this.mReboundDuration;
        int i3 = this.mHeaderHeight;
        float f = ((this.mHeaderMaxDragRate / 2.0f) + 0.5f) * i3 * 1.0f;
        if (i3 == 0) {
            i3 = 1;
        }
        return autoRefresh(i, i2, f / i3, false);
    }

    public boolean autoRefresh(int i, final int i2, final float f, final boolean z) {
        if (this.mState != RefreshState.None || !isEnableRefreshOrLoadMore(this.mEnableRefresh)) {
            return false;
        }
        ValueAnimator valueAnimator = this.reboundAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        Runnable runnable = new Runnable() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.8
            @Override // java.lang.Runnable
            public void run() {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                smartRefreshLayout.reboundAnimator = ValueAnimator.ofInt(smartRefreshLayout.mSpinner, (int) (smartRefreshLayout.mHeaderHeight * f));
                SmartRefreshLayout.this.reboundAnimator.setDuration(i2);
                SmartRefreshLayout.this.reboundAnimator.setInterpolator(new DecelerateInterpolator());
                SmartRefreshLayout.this.reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.8.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        SmartRefreshLayout.this.mKernel.moveSpinner(((Integer) valueAnimator2.getAnimatedValue()).intValue(), true);
                    }
                });
                SmartRefreshLayout.this.reboundAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.8.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                        smartRefreshLayout2.mLastTouchX = smartRefreshLayout2.getMeasuredWidth() / 2;
                        SmartRefreshLayout.this.mKernel.setState(RefreshState.PullDownToRefresh);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        RunnableC30678 runnableC30678 = RunnableC30678.this;
                        SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                        smartRefreshLayout2.reboundAnimator = null;
                        if (z) {
                            if (smartRefreshLayout2.mState == RefreshState.ReleaseToRefresh) {
                                smartRefreshLayout2.mKernel.setState(RefreshState.PullDownToRefresh);
                            }
                        } else {
                            RefreshState refreshState = smartRefreshLayout2.mState;
                            RefreshState refreshState2 = RefreshState.ReleaseToRefresh;
                            if (refreshState != refreshState2) {
                                smartRefreshLayout2.mKernel.setState(refreshState2);
                            }
                        }
                        SmartRefreshLayout.this.overSpinner();
                    }
                });
                SmartRefreshLayout.this.reboundAnimator.start();
            }
        };
        if (i > 0) {
            this.reboundAnimator = new ValueAnimator();
            postDelayed(runnable, i);
            return true;
        }
        runnable.run();
        return true;
    }

    public static void setDefaultRefreshHeaderCreator(@NonNull DefaultRefreshHeaderCreator defaultRefreshHeaderCreator) {
        sHeaderCreator = defaultRefreshHeaderCreator;
    }

    public static void setDefaultRefreshFooterCreator(@NonNull DefaultRefreshFooterCreator defaultRefreshFooterCreator) {
        sFooterCreator = defaultRefreshFooterCreator;
    }

    public static void setDefaultRefreshInitializer(@NonNull DefaultRefreshInitializer defaultRefreshInitializer) {
        sRefreshInitializer = defaultRefreshInitializer;
    }

    /* loaded from: classes3.dex */
    public class RefreshKernelImpl implements RefreshKernel {
        public RefreshKernelImpl() {
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        @NonNull
        public RefreshLayout getRefreshLayout() {
            return SmartRefreshLayout.this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel setState(@NonNull RefreshState refreshState) {
            switch (C305810.$SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[refreshState.ordinal()]) {
                case 1:
                    SmartRefreshLayout.this.resetStatus();
                    return null;
                case 2:
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    if (!smartRefreshLayout.mState.isOpening && smartRefreshLayout.isEnableRefreshOrLoadMore(smartRefreshLayout.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullDownToRefresh);
                        return null;
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.PullDownToRefresh);
                    return null;
                case 3:
                    SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                    if (smartRefreshLayout2.isEnableRefreshOrLoadMore(smartRefreshLayout2.mEnableLoadMore)) {
                        SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                        RefreshState refreshState2 = smartRefreshLayout3.mState;
                        if (!refreshState2.isOpening && !refreshState2.isFinishing && (!smartRefreshLayout3.mFooterNoMoreData || !smartRefreshLayout3.mEnableFooterFollowWhenNoMoreData)) {
                            SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullUpToLoad);
                            return null;
                        }
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.PullUpToLoad);
                    return null;
                case 4:
                    SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout4.mState.isOpening && smartRefreshLayout4.isEnableRefreshOrLoadMore(smartRefreshLayout4.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullDownCanceled);
                        SmartRefreshLayout.this.resetStatus();
                        return null;
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.PullDownCanceled);
                    return null;
                case 5:
                    SmartRefreshLayout smartRefreshLayout5 = SmartRefreshLayout.this;
                    if (smartRefreshLayout5.isEnableRefreshOrLoadMore(smartRefreshLayout5.mEnableLoadMore)) {
                        SmartRefreshLayout smartRefreshLayout6 = SmartRefreshLayout.this;
                        if (!smartRefreshLayout6.mState.isOpening && (!smartRefreshLayout6.mFooterNoMoreData || !smartRefreshLayout6.mEnableFooterFollowWhenNoMoreData)) {
                            SmartRefreshLayout.this.notifyStateChanged(RefreshState.PullUpCanceled);
                            SmartRefreshLayout.this.resetStatus();
                            return null;
                        }
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.PullUpCanceled);
                    return null;
                case 6:
                    SmartRefreshLayout smartRefreshLayout7 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout7.mState.isOpening && smartRefreshLayout7.isEnableRefreshOrLoadMore(smartRefreshLayout7.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.ReleaseToRefresh);
                        return null;
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.ReleaseToRefresh);
                    return null;
                case 7:
                    SmartRefreshLayout smartRefreshLayout8 = SmartRefreshLayout.this;
                    if (smartRefreshLayout8.isEnableRefreshOrLoadMore(smartRefreshLayout8.mEnableLoadMore)) {
                        SmartRefreshLayout smartRefreshLayout9 = SmartRefreshLayout.this;
                        RefreshState refreshState3 = smartRefreshLayout9.mState;
                        if (!refreshState3.isOpening && !refreshState3.isFinishing && (!smartRefreshLayout9.mFooterNoMoreData || !smartRefreshLayout9.mEnableFooterFollowWhenNoMoreData)) {
                            SmartRefreshLayout.this.notifyStateChanged(RefreshState.ReleaseToLoad);
                            return null;
                        }
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.ReleaseToLoad);
                    return null;
                case 8:
                    SmartRefreshLayout smartRefreshLayout10 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout10.mState.isOpening && smartRefreshLayout10.isEnableRefreshOrLoadMore(smartRefreshLayout10.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.ReleaseToTwoLevel);
                        return null;
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.ReleaseToTwoLevel);
                    return null;
                case 9:
                    SmartRefreshLayout smartRefreshLayout11 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout11.mState.isOpening && smartRefreshLayout11.isEnableRefreshOrLoadMore(smartRefreshLayout11.mEnableRefresh)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.RefreshReleased);
                        return null;
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.RefreshReleased);
                    return null;
                case 10:
                    SmartRefreshLayout smartRefreshLayout12 = SmartRefreshLayout.this;
                    if (!smartRefreshLayout12.mState.isOpening && smartRefreshLayout12.isEnableRefreshOrLoadMore(smartRefreshLayout12.mEnableLoadMore)) {
                        SmartRefreshLayout.this.notifyStateChanged(RefreshState.LoadReleased);
                        return null;
                    }
                    SmartRefreshLayout.this.setViceState(RefreshState.LoadReleased);
                    return null;
                case 11:
                    SmartRefreshLayout.this.setStateRefreshing();
                    return null;
                case 12:
                    SmartRefreshLayout.this.setStateLoading();
                    return null;
                case 13:
                    SmartRefreshLayout smartRefreshLayout13 = SmartRefreshLayout.this;
                    if (smartRefreshLayout13.mState != RefreshState.Refreshing) {
                        return null;
                    }
                    smartRefreshLayout13.notifyStateChanged(RefreshState.RefreshFinish);
                    return null;
                case 14:
                    SmartRefreshLayout smartRefreshLayout14 = SmartRefreshLayout.this;
                    if (smartRefreshLayout14.mState != RefreshState.Loading) {
                        return null;
                    }
                    smartRefreshLayout14.notifyStateChanged(RefreshState.LoadFinish);
                    return null;
                case 15:
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.TwoLevelReleased);
                    return null;
                case 16:
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.TwoLevelFinish);
                    return null;
                case 17:
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.TwoLevel);
                    return null;
                default:
                    return null;
            }
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel startTwoLevel(boolean z) {
            if (z) {
                AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.scwang.smartrefresh.layout.SmartRefreshLayout.RefreshKernelImpl.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        SmartRefreshLayout.this.mKernel.setState(RefreshState.TwoLevel);
                    }
                };
                ValueAnimator animSpinner = animSpinner(SmartRefreshLayout.this.getMeasuredHeight());
                if (animSpinner != null) {
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    if (animSpinner == smartRefreshLayout.reboundAnimator) {
                        animSpinner.setDuration(smartRefreshLayout.mFloorDuration);
                        animSpinner.addListener(animatorListenerAdapter);
                    }
                }
                animatorListenerAdapter.onAnimationEnd(null);
            } else if (animSpinner(0) == null) {
                SmartRefreshLayout.this.notifyStateChanged(RefreshState.None);
            }
            return this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel finishTwoLevel() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.mState == RefreshState.TwoLevel) {
                smartRefreshLayout.mKernel.setState(RefreshState.TwoLevelFinish);
                if (SmartRefreshLayout.this.mSpinner == 0) {
                    moveSpinner(0, false);
                    SmartRefreshLayout.this.notifyStateChanged(RefreshState.None);
                } else {
                    animSpinner(0).setDuration(SmartRefreshLayout.this.mFloorDuration);
                }
            }
            return this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel moveSpinner(int i, boolean z) {
            OnMultiPurposeListener onMultiPurposeListener;
            OnMultiPurposeListener onMultiPurposeListener2;
            RefreshInternal refreshInternal;
            RefreshInternal refreshInternal2;
            SmartRefreshLayout smartRefreshLayout;
            RefreshInternal refreshInternal3;
            RefreshInternal refreshInternal4;
            RefreshInternal refreshInternal5;
            RefreshInternal refreshInternal6;
            SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
            if (smartRefreshLayout2.mSpinner != i || (((refreshInternal5 = smartRefreshLayout2.mRefreshHeader) != null && refreshInternal5.isSupportHorizontalDrag()) || ((refreshInternal6 = SmartRefreshLayout.this.mRefreshFooter) != null && refreshInternal6.isSupportHorizontalDrag()))) {
                SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                int i2 = smartRefreshLayout3.mSpinner;
                smartRefreshLayout3.mSpinner = i;
                if (z && smartRefreshLayout3.mViceState.isDragging) {
                    int i3 = smartRefreshLayout3.mSpinner;
                    if (i3 > smartRefreshLayout3.mHeaderHeight * smartRefreshLayout3.mHeaderTriggerRate) {
                        if (smartRefreshLayout3.mState != RefreshState.ReleaseToTwoLevel) {
                            smartRefreshLayout3.mKernel.setState(RefreshState.ReleaseToRefresh);
                        }
                    } else if ((-i3) > smartRefreshLayout3.mFooterHeight * smartRefreshLayout3.mFooterTriggerRate && !smartRefreshLayout3.mFooterNoMoreData) {
                        smartRefreshLayout3.mKernel.setState(RefreshState.ReleaseToLoad);
                    } else {
                        SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                        if (smartRefreshLayout4.mSpinner < 0 && !smartRefreshLayout4.mFooterNoMoreData) {
                            smartRefreshLayout4.mKernel.setState(RefreshState.PullUpToLoad);
                        } else {
                            SmartRefreshLayout smartRefreshLayout5 = SmartRefreshLayout.this;
                            if (smartRefreshLayout5.mSpinner > 0) {
                                smartRefreshLayout5.mKernel.setState(RefreshState.PullDownToRefresh);
                            }
                        }
                    }
                }
                SmartRefreshLayout smartRefreshLayout6 = SmartRefreshLayout.this;
                int i4 = 1;
                if (smartRefreshLayout6.mRefreshContent != null) {
                    Integer num = null;
                    if (i >= 0 && (refreshInternal4 = smartRefreshLayout6.mRefreshHeader) != null) {
                        if (smartRefreshLayout6.isEnableTranslationContent(smartRefreshLayout6.mEnableHeaderTranslationContent, refreshInternal4)) {
                            num = Integer.valueOf(i);
                        } else if (i2 < 0) {
                            num = 0;
                        }
                    }
                    if (i <= 0 && (refreshInternal3 = (smartRefreshLayout = SmartRefreshLayout.this).mRefreshFooter) != null) {
                        if (smartRefreshLayout.isEnableTranslationContent(smartRefreshLayout.mEnableFooterTranslationContent, refreshInternal3)) {
                            num = Integer.valueOf(i);
                        } else if (i2 > 0) {
                            num = 0;
                        }
                    }
                    if (num != null) {
                        RefreshContent refreshContent = SmartRefreshLayout.this.mRefreshContent;
                        int intValue = num.intValue();
                        SmartRefreshLayout smartRefreshLayout7 = SmartRefreshLayout.this;
                        refreshContent.moveSpinner(intValue, smartRefreshLayout7.mHeaderTranslationViewId, smartRefreshLayout7.mFooterTranslationViewId);
                        SmartRefreshLayout smartRefreshLayout8 = SmartRefreshLayout.this;
                        boolean z2 = (smartRefreshLayout8.mEnableClipHeaderWhenFixedBehind && (refreshInternal2 = smartRefreshLayout8.mRefreshHeader) != null && refreshInternal2.getSpinnerStyle() == SpinnerStyle.FixedBehind) || SmartRefreshLayout.this.mHeaderBackgroundColor != 0;
                        SmartRefreshLayout smartRefreshLayout9 = SmartRefreshLayout.this;
                        boolean z3 = (smartRefreshLayout9.mEnableClipFooterWhenFixedBehind && (refreshInternal = smartRefreshLayout9.mRefreshFooter) != null && refreshInternal.getSpinnerStyle() == SpinnerStyle.FixedBehind) || SmartRefreshLayout.this.mFooterBackgroundColor != 0;
                        if ((z2 && (num.intValue() >= 0 || i2 > 0)) || (z3 && (num.intValue() <= 0 || i2 < 0))) {
                            smartRefreshLayout3.invalidate();
                        }
                    }
                }
                if ((i >= 0 || i2 > 0) && SmartRefreshLayout.this.mRefreshHeader != null) {
                    int max = Math.max(i, 0);
                    SmartRefreshLayout smartRefreshLayout10 = SmartRefreshLayout.this;
                    int i5 = smartRefreshLayout10.mHeaderHeight;
                    int i6 = (int) (i5 * smartRefreshLayout10.mHeaderMaxDragRate);
                    float f = (max * 1.0f) / (i5 == 0 ? 1 : i5);
                    SmartRefreshLayout smartRefreshLayout11 = SmartRefreshLayout.this;
                    if (smartRefreshLayout11.isEnableRefreshOrLoadMore(smartRefreshLayout11.mEnableRefresh) || (SmartRefreshLayout.this.mState == RefreshState.RefreshFinish && !z)) {
                        SmartRefreshLayout smartRefreshLayout12 = SmartRefreshLayout.this;
                        if (i2 != smartRefreshLayout12.mSpinner) {
                            if (smartRefreshLayout12.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                                SmartRefreshLayout.this.mRefreshHeader.getView().setTranslationY(SmartRefreshLayout.this.mSpinner);
                                SmartRefreshLayout smartRefreshLayout13 = SmartRefreshLayout.this;
                                if (smartRefreshLayout13.mHeaderBackgroundColor != 0 && smartRefreshLayout13.mPaint != null && !smartRefreshLayout13.isEnableTranslationContent(smartRefreshLayout13.mEnableHeaderTranslationContent, smartRefreshLayout13.mRefreshHeader)) {
                                    smartRefreshLayout3.invalidate();
                                }
                            } else if (SmartRefreshLayout.this.mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Scale) {
                                SmartRefreshLayout.this.mRefreshHeader.getView().requestLayout();
                            }
                            SmartRefreshLayout.this.mRefreshHeader.onMoving(z, f, max, i5, i6);
                        }
                        if (z && SmartRefreshLayout.this.mRefreshHeader.isSupportHorizontalDrag()) {
                            int i7 = (int) SmartRefreshLayout.this.mLastTouchX;
                            int width = smartRefreshLayout3.getWidth();
                            SmartRefreshLayout.this.mRefreshHeader.onHorizontalDrag(SmartRefreshLayout.this.mLastTouchX / (width == 0 ? 1 : width), i7, width);
                        }
                    }
                    SmartRefreshLayout smartRefreshLayout14 = SmartRefreshLayout.this;
                    if (i2 != smartRefreshLayout14.mSpinner && (onMultiPurposeListener = smartRefreshLayout14.mOnMultiPurposeListener) != null) {
                        RefreshInternal refreshInternal7 = smartRefreshLayout14.mRefreshHeader;
                        if (refreshInternal7 instanceof RefreshHeader) {
                            onMultiPurposeListener.onHeaderMoving((RefreshHeader) refreshInternal7, z, f, max, i5, i6);
                        }
                    }
                }
                if ((i <= 0 || i2 < 0) && SmartRefreshLayout.this.mRefreshFooter != null) {
                    int i8 = -Math.min(i, 0);
                    SmartRefreshLayout smartRefreshLayout15 = SmartRefreshLayout.this;
                    int i9 = smartRefreshLayout15.mFooterHeight;
                    int i10 = (int) (i9 * smartRefreshLayout15.mFooterMaxDragRate);
                    float f2 = (i8 * 1.0f) / (i9 == 0 ? 1 : i9);
                    SmartRefreshLayout smartRefreshLayout16 = SmartRefreshLayout.this;
                    if (smartRefreshLayout16.isEnableRefreshOrLoadMore(smartRefreshLayout16.mEnableLoadMore) || (SmartRefreshLayout.this.mState == RefreshState.LoadFinish && !z)) {
                        SmartRefreshLayout smartRefreshLayout17 = SmartRefreshLayout.this;
                        if (i2 != smartRefreshLayout17.mSpinner) {
                            if (smartRefreshLayout17.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Translate) {
                                SmartRefreshLayout.this.mRefreshFooter.getView().setTranslationY(SmartRefreshLayout.this.mSpinner);
                                SmartRefreshLayout smartRefreshLayout18 = SmartRefreshLayout.this;
                                if (smartRefreshLayout18.mFooterBackgroundColor != 0 && smartRefreshLayout18.mPaint != null && !smartRefreshLayout18.isEnableTranslationContent(smartRefreshLayout18.mEnableFooterTranslationContent, smartRefreshLayout18.mRefreshFooter)) {
                                    smartRefreshLayout3.invalidate();
                                }
                            } else if (SmartRefreshLayout.this.mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Scale) {
                                SmartRefreshLayout.this.mRefreshFooter.getView().requestLayout();
                            }
                            SmartRefreshLayout.this.mRefreshFooter.onMoving(z, f2, i8, i9, i10);
                        }
                        if (z && SmartRefreshLayout.this.mRefreshFooter.isSupportHorizontalDrag()) {
                            int i11 = (int) SmartRefreshLayout.this.mLastTouchX;
                            int width2 = smartRefreshLayout3.getWidth();
                            float f3 = SmartRefreshLayout.this.mLastTouchX;
                            if (width2 != 0) {
                                i4 = width2;
                            }
                            SmartRefreshLayout.this.mRefreshFooter.onHorizontalDrag(f3 / i4, i11, width2);
                        }
                    }
                    SmartRefreshLayout smartRefreshLayout19 = SmartRefreshLayout.this;
                    if (i2 != smartRefreshLayout19.mSpinner && (onMultiPurposeListener2 = smartRefreshLayout19.mOnMultiPurposeListener) != null) {
                        RefreshInternal refreshInternal8 = smartRefreshLayout19.mRefreshFooter;
                        if (refreshInternal8 instanceof RefreshFooter) {
                            onMultiPurposeListener2.onFooterMoving((RefreshFooter) refreshInternal8, z, f2, i8, i9, i10);
                        }
                    }
                }
                return this;
            }
            return this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public ValueAnimator animSpinner(int i) {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            return smartRefreshLayout.animSpinner(i, 0, smartRefreshLayout.mReboundInterpolator, smartRefreshLayout.mReboundDuration);
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel requestDrawBackgroundFor(@NonNull RefreshInternal refreshInternal, int i) {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.mPaint == null && i != 0) {
                smartRefreshLayout.mPaint = new Paint();
            }
            if (refreshInternal.equals(SmartRefreshLayout.this.mRefreshHeader)) {
                SmartRefreshLayout.this.mHeaderBackgroundColor = i;
            } else if (refreshInternal.equals(SmartRefreshLayout.this.mRefreshFooter)) {
                SmartRefreshLayout.this.mFooterBackgroundColor = i;
            }
            return this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel requestNeedTouchEventFor(@NonNull RefreshInternal refreshInternal, boolean z) {
            if (refreshInternal.equals(SmartRefreshLayout.this.mRefreshHeader)) {
                SmartRefreshLayout.this.mHeaderNeedTouchEventWhenRefreshing = z;
            } else if (refreshInternal.equals(SmartRefreshLayout.this.mRefreshFooter)) {
                SmartRefreshLayout.this.mFooterNeedTouchEventWhenLoading = z;
            }
            return this;
        }

        @Override // com.scwang.smartrefresh.layout.api.RefreshKernel
        public RefreshKernel requestFloorDuration(int i) {
            SmartRefreshLayout.this.mFloorDuration = i;
            return this;
        }
    }

    /* renamed from: com.scwang.smartrefresh.layout.SmartRefreshLayout$10 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C305810 {
        static final /* synthetic */ int[] $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState = new int[RefreshState.values().length];

        static {
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.PullDownToRefresh.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.PullUpToLoad.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.PullDownCanceled.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.PullUpCanceled.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.ReleaseToRefresh.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.ReleaseToLoad.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.ReleaseToTwoLevel.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.RefreshReleased.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.LoadReleased.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.Refreshing.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.Loading.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.RefreshFinish.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.LoadFinish.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.TwoLevelReleased.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.TwoLevelFinish.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.TwoLevel.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    @Override // android.view.View
    public boolean post(@NonNull Runnable runnable) {
        Handler handler = this.mHandler;
        if (handler == null) {
            List<DelayedRunnable> list = this.mListDelayedRunnable;
            if (list == null) {
                list = new ArrayList<>();
            }
            this.mListDelayedRunnable = list;
            this.mListDelayedRunnable.add(new DelayedRunnable(runnable, 0L));
            return false;
        }
        return handler.post(new DelayedRunnable(runnable, 0L));
    }

    @Override // android.view.View
    public boolean postDelayed(@NonNull Runnable runnable, long j) {
        if (j == 0) {
            new DelayedRunnable(runnable, 0L).run();
            return true;
        }
        Handler handler = this.mHandler;
        if (handler == null) {
            List<DelayedRunnable> list = this.mListDelayedRunnable;
            if (list == null) {
                list = new ArrayList<>();
            }
            this.mListDelayedRunnable = list;
            this.mListDelayedRunnable.add(new DelayedRunnable(runnable, j));
            return false;
        }
        return handler.postDelayed(new DelayedRunnable(runnable, 0L), j);
    }
}
