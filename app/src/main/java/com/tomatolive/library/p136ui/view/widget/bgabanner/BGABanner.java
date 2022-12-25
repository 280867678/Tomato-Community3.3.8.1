package com.tomatolive.library.p136ui.view.widget.bgabanner;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.p002v4.view.PagerAdapter;
import android.support.p002v4.view.ViewCompat;
import android.support.p002v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$styleable;
import com.tomatolive.library.p136ui.view.widget.BannerWebView;
import com.tomatolive.library.p136ui.view.widget.bgabanner.BGAViewPager;
import com.tomatolive.library.p136ui.view.widget.bgabanner.transformer.BGAPageTransformer;
import com.tomatolive.library.p136ui.view.widget.bgabanner.transformer.TransitionEffect;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.BGABanner */
/* loaded from: classes4.dex */
public class BGABanner extends RelativeLayout implements BGAViewPager.AutoPlayDelegate, ViewPager.OnPageChangeListener {
    private static final int LWC = -2;
    private static final int NO_PLACEHOLDER_DRAWABLE = -1;
    private static final int RMP = -1;
    private static final int RWC = -2;
    private static final int VEL_THRESHOLD = 400;
    private static final ImageView.ScaleType[] sScaleTypeArray = {ImageView.ScaleType.MATRIX, ImageView.ScaleType.FIT_XY, ImageView.ScaleType.FIT_START, ImageView.ScaleType.FIT_CENTER, ImageView.ScaleType.FIT_END, ImageView.ScaleType.CENTER, ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.CENTER_INSIDE};
    private Adapter mAdapter;
    private boolean mAllowUserScrollable;
    private float mAspectRatio;
    private boolean mAutoPlayAble;
    private int mAutoPlayInterval;
    private AutoPlayTask mAutoPlayTask;
    private int mContentBottomMargin;
    private Delegate mDelegate;
    private View mEnterView;
    private GuideDelegate mGuideDelegate;
    private BGAOnNoDoubleClickListener mGuideOnNoDoubleClickListener;
    private List<View> mHackyViews;
    private boolean mIsFirstInvisible;
    private boolean mIsNeedShowIndicatorOnOnlyOnePage;
    private boolean mIsNumberIndicator;
    private List<? extends Object> mModels;
    private Drawable mNumberIndicatorBackground;
    private int mNumberIndicatorTextColor;
    private int mNumberIndicatorTextSize;
    private TextView mNumberIndicatorTv;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mOverScrollMode;
    private int mPageChangeDuration;
    private int mPageScrollPosition;
    private float mPageScrollPositionOffset;
    private int mPlaceholderDrawableResId;
    private ImageView mPlaceholderIv;
    private Drawable mPointContainerBackgroundDrawable;
    private int mPointContainerLeftRightPadding;
    private RelativeLayout mPointContainerRl;
    private int mPointDrawableResId;
    private int mPointGravity;
    private int mPointLeftRightMargin;
    private LinearLayout mPointRealContainerLl;
    private int mPointTopBottomMargin;
    private ImageView.ScaleType mScaleType;
    private View mSkipView;
    private int mTipTextColor;
    private int mTipTextSize;
    private TextView mTipTv;
    private List<String> mTips;
    private TransitionEffect mTransitionEffect;
    private BGAViewPager mViewPager;
    private List<View> mViews;

    /* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.BGABanner$Adapter */
    /* loaded from: classes4.dex */
    public interface Adapter<V extends View, M> {
        void fillBannerItem(BGABanner bGABanner, V v, @Nullable M m, int i);
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.BGABanner$Delegate */
    /* loaded from: classes4.dex */
    public interface Delegate<V extends View, M> {
        void onBannerItemClick(BGABanner bGABanner, V v, @Nullable M m, int i);
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.BGABanner$GuideDelegate */
    /* loaded from: classes4.dex */
    public interface GuideDelegate {
        void onClickEnterOrSkip();
    }

    @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGAViewPager.AutoPlayDelegate
    public void onTouchListener(MotionEvent motionEvent) {
    }

    public BGABanner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BGABanner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAutoPlayAble = true;
        this.mAutoPlayInterval = 3000;
        this.mPageChangeDuration = 800;
        this.mPointGravity = 81;
        this.mTipTextColor = -1;
        this.mPointDrawableResId = R$drawable.bga_banner_selector_point_solid;
        this.mScaleType = ImageView.ScaleType.CENTER_CROP;
        this.mPlaceholderDrawableResId = -1;
        this.mOverScrollMode = 2;
        this.mIsNumberIndicator = false;
        this.mNumberIndicatorTextColor = -1;
        this.mAllowUserScrollable = true;
        this.mIsFirstInvisible = true;
        this.mGuideOnNoDoubleClickListener = new BGAOnNoDoubleClickListener() { // from class: com.tomatolive.library.ui.view.widget.bgabanner.BGABanner.1
            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGAOnNoDoubleClickListener
            public void onNoDoubleClick(View view) {
                if (BGABanner.this.mGuideDelegate != null) {
                    BGABanner.this.mGuideDelegate.onClickEnterOrSkip();
                }
            }
        };
        initDefaultAttrs(context);
        initCustomAttrs(context, attributeSet);
        initView(context);
    }

    private void initDefaultAttrs(Context context) {
        this.mAutoPlayTask = new AutoPlayTask();
        this.mPointLeftRightMargin = BGABannerUtil.dp2px(context, 3.0f);
        this.mPointTopBottomMargin = BGABannerUtil.dp2px(context, 6.0f);
        this.mPointContainerLeftRightPadding = BGABannerUtil.dp2px(context, 10.0f);
        this.mTipTextSize = BGABannerUtil.sp2px(context, 10.0f);
        this.mPointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#44aaaaaa"));
        this.mTransitionEffect = TransitionEffect.Default;
        this.mNumberIndicatorTextSize = BGABannerUtil.sp2px(context, 10.0f);
        this.mContentBottomMargin = 0;
        this.mAspectRatio = 0.0f;
    }

    private void initCustomAttrs(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BGABanner);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            initCustomAttr(obtainStyledAttributes.getIndex(i), obtainStyledAttributes);
        }
        obtainStyledAttributes.recycle();
    }

    private void initCustomAttr(int i, TypedArray typedArray) {
        int i2;
        if (i == R$styleable.BGABanner_banner_pointDrawable) {
            this.mPointDrawableResId = typedArray.getResourceId(i, R$drawable.bga_banner_selector_point_solid);
        } else if (i == R$styleable.BGABanner_banner_pointContainerBackground) {
            this.mPointContainerBackgroundDrawable = typedArray.getDrawable(i);
        } else if (i == R$styleable.BGABanner_banner_pointLeftRightMargin) {
            this.mPointLeftRightMargin = typedArray.getDimensionPixelSize(i, this.mPointLeftRightMargin);
        } else if (i == R$styleable.BGABanner_banner_pointContainerLeftRightPadding) {
            this.mPointContainerLeftRightPadding = typedArray.getDimensionPixelSize(i, this.mPointContainerLeftRightPadding);
        } else if (i == R$styleable.BGABanner_banner_pointTopBottomMargin) {
            this.mPointTopBottomMargin = typedArray.getDimensionPixelSize(i, this.mPointTopBottomMargin);
        } else if (i == R$styleable.BGABanner_banner_indicatorGravity) {
            this.mPointGravity = typedArray.getInt(i, this.mPointGravity);
        } else if (i == R$styleable.BGABanner_banner_pointAutoPlayAble) {
            this.mAutoPlayAble = typedArray.getBoolean(i, this.mAutoPlayAble);
        } else if (i == R$styleable.BGABanner_banner_pointAutoPlayInterval) {
            this.mAutoPlayInterval = typedArray.getInteger(i, this.mAutoPlayInterval);
        } else if (i == R$styleable.BGABanner_banner_pageChangeDuration) {
            this.mPageChangeDuration = typedArray.getInteger(i, this.mPageChangeDuration);
        } else if (i == R$styleable.BGABanner_banner_transitionEffect) {
            this.mTransitionEffect = TransitionEffect.values()[typedArray.getInt(i, TransitionEffect.Accordion.ordinal())];
        } else if (i == R$styleable.BGABanner_banner_tipTextColor) {
            this.mTipTextColor = typedArray.getColor(i, this.mTipTextColor);
        } else if (i == R$styleable.BGABanner_banner_tipTextSize) {
            this.mTipTextSize = typedArray.getDimensionPixelSize(i, this.mTipTextSize);
        } else if (i == R$styleable.BGABanner_banner_placeholderDrawable) {
            this.mPlaceholderDrawableResId = typedArray.getResourceId(i, this.mPlaceholderDrawableResId);
        } else if (i == R$styleable.BGABanner_banner_isNumberIndicator) {
            this.mIsNumberIndicator = typedArray.getBoolean(i, this.mIsNumberIndicator);
        } else if (i == R$styleable.BGABanner_banner_numberIndicatorTextColor) {
            this.mNumberIndicatorTextColor = typedArray.getColor(i, this.mNumberIndicatorTextColor);
        } else if (i == R$styleable.BGABanner_banner_numberIndicatorTextSize) {
            this.mNumberIndicatorTextSize = typedArray.getDimensionPixelSize(i, this.mNumberIndicatorTextSize);
        } else if (i == R$styleable.BGABanner_banner_numberIndicatorBackground) {
            this.mNumberIndicatorBackground = typedArray.getDrawable(i);
        } else if (i == R$styleable.BGABanner_banner_isNeedShowIndicatorOnOnlyOnePage) {
            this.mIsNeedShowIndicatorOnOnlyOnePage = typedArray.getBoolean(i, this.mIsNeedShowIndicatorOnOnlyOnePage);
        } else if (i == R$styleable.BGABanner_banner_contentBottomMargin) {
            this.mContentBottomMargin = typedArray.getDimensionPixelSize(i, this.mContentBottomMargin);
        } else if (i == R$styleable.BGABanner_banner_aspectRatio) {
            this.mAspectRatio = typedArray.getFloat(i, this.mAspectRatio);
        } else if (i != R$styleable.BGABanner_android_scaleType || (i2 = typedArray.getInt(i, -1)) < 0) {
        } else {
            ImageView.ScaleType[] scaleTypeArr = sScaleTypeArray;
            if (i2 >= scaleTypeArr.length) {
                return;
            }
            this.mScaleType = scaleTypeArr[i2];
        }
    }

    private void initView(Context context) {
        this.mPointContainerRl = new RelativeLayout(context);
        if (Build.VERSION.SDK_INT >= 16) {
            this.mPointContainerRl.setBackground(this.mPointContainerBackgroundDrawable);
        } else {
            this.mPointContainerRl.setBackgroundDrawable(this.mPointContainerBackgroundDrawable);
        }
        RelativeLayout relativeLayout = this.mPointContainerRl;
        int i = this.mPointContainerLeftRightPadding;
        int i2 = this.mPointTopBottomMargin;
        relativeLayout.setPadding(i, i2, i, i2);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        if ((this.mPointGravity & 112) == 48) {
            layoutParams.addRule(10);
        } else {
            layoutParams.addRule(12);
        }
        addView(this.mPointContainerRl, layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(15);
        if (this.mIsNumberIndicator) {
            this.mNumberIndicatorTv = new TextView(context);
            this.mNumberIndicatorTv.setId(R$id.banner_indicatorId);
            this.mNumberIndicatorTv.setGravity(16);
            this.mNumberIndicatorTv.setSingleLine(true);
            this.mNumberIndicatorTv.setEllipsize(TextUtils.TruncateAt.END);
            this.mNumberIndicatorTv.setTextColor(this.mNumberIndicatorTextColor);
            this.mNumberIndicatorTv.setTextSize(0, this.mNumberIndicatorTextSize);
            this.mNumberIndicatorTv.setVisibility(4);
            Drawable drawable = this.mNumberIndicatorBackground;
            if (drawable != null) {
                if (Build.VERSION.SDK_INT >= 16) {
                    this.mNumberIndicatorTv.setBackground(drawable);
                } else {
                    this.mNumberIndicatorTv.setBackgroundDrawable(drawable);
                }
            }
            this.mPointContainerRl.addView(this.mNumberIndicatorTv, layoutParams2);
        } else {
            this.mPointRealContainerLl = new LinearLayout(context);
            this.mPointRealContainerLl.setId(R$id.banner_indicatorId);
            this.mPointRealContainerLl.setOrientation(0);
            this.mPointRealContainerLl.setGravity(16);
            this.mPointContainerRl.addView(this.mPointRealContainerLl, layoutParams2);
        }
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams3.addRule(15);
        this.mTipTv = new TextView(context);
        this.mTipTv.setGravity(16);
        this.mTipTv.setSingleLine(true);
        this.mTipTv.setEllipsize(TextUtils.TruncateAt.END);
        this.mTipTv.setTextColor(this.mTipTextColor);
        this.mTipTv.setTextSize(0, this.mTipTextSize);
        this.mPointContainerRl.addView(this.mTipTv, layoutParams3);
        int i3 = this.mPointGravity & 7;
        if (i3 == 3) {
            layoutParams2.addRule(9);
            layoutParams3.addRule(1, R$id.banner_indicatorId);
            this.mTipTv.setGravity(21);
        } else if (i3 == 5) {
            layoutParams2.addRule(11);
            layoutParams3.addRule(0, R$id.banner_indicatorId);
        } else {
            layoutParams2.addRule(14);
            layoutParams3.addRule(0, R$id.banner_indicatorId);
        }
        showPlaceholder();
    }

    public void showPlaceholder() {
        if (this.mPlaceholderIv != null || this.mPlaceholderDrawableResId == -1) {
            return;
        }
        this.mPlaceholderIv = BGABannerUtil.getItemImageView(getContext(), this.mPlaceholderDrawableResId, new BGALocalImageSize(720, 360, 640.0f, 320.0f), this.mScaleType);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.setMargins(0, 0, 0, this.mContentBottomMargin);
        addView(this.mPlaceholderIv, layoutParams);
    }

    public void setPageChangeDuration(int i) {
        if (i < 0 || i > 2000) {
            return;
        }
        this.mPageChangeDuration = i;
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager == null) {
            return;
        }
        bGAViewPager.setPageChangeDuration(i);
    }

    public void setAutoPlayAble(boolean z) {
        this.mAutoPlayAble = z;
        stopAutoPlay();
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager == null || bGAViewPager.getAdapter() == null) {
            return;
        }
        this.mViewPager.getAdapter().notifyDataSetChanged();
    }

    public void setAutoPlayInterval(int i) {
        this.mAutoPlayInterval = i;
    }

    public void setData(List<View> list, List<? extends Object> list2, List<String> list3) {
        if (BGABannerUtil.isCollectionEmpty(list, new Collection[0])) {
            this.mAutoPlayAble = false;
            list = new ArrayList<>();
            list2 = new ArrayList<>();
            list3 = new ArrayList<>();
        }
        if (this.mAutoPlayAble && list.size() < 3 && this.mHackyViews == null) {
            this.mAutoPlayAble = false;
        }
        this.mModels = list2;
        this.mViews = list;
        this.mTips = list3;
        initIndicator();
        initViewPager();
        removePlaceholder();
        handleGuideViewVisibility(0, 0.0f);
    }

    public void setDataWithWebView(List<View> list, List<? extends Object> list2) {
        if (BGABannerUtil.isCollectionEmpty(list, new Collection[0])) {
            this.mAutoPlayAble = false;
            list = new ArrayList<>();
            list2 = new ArrayList<>();
        }
        this.mModels = list2;
        this.mViews = list;
        initIndicator();
        initViewPager();
        removePlaceholder();
        handleGuideViewVisibility(0, 0.0f);
    }

    public void setData(@LayoutRes int i, List<? extends Object> list, List<String> list2) {
        this.mViews = new ArrayList();
        if (list == null) {
            list = new ArrayList<>();
            list2 = new ArrayList<>();
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.mViews.add(inflateItemView(i));
        }
        if (this.mAutoPlayAble && this.mViews.size() < 3) {
            this.mHackyViews = new ArrayList(this.mViews);
            this.mHackyViews.add(inflateItemView(i));
            if (this.mHackyViews.size() == 2) {
                this.mHackyViews.add(inflateItemView(i));
            }
        }
        setData(this.mViews, list, list2);
    }

    private View inflateItemView(@LayoutRes int i) {
        View inflate = View.inflate(getContext(), i, null);
        if (inflate instanceof ImageView) {
            ((ImageView) inflate).setScaleType(this.mScaleType);
        }
        return inflate;
    }

    public void setData(List<? extends Object> list, List<String> list2) {
        setData(R$layout.bga_banner_item_image, list, list2);
    }

    public void setData(List<View> list) {
        setData(list, (List<? extends Object>) null, (List<String>) null);
    }

    public void setData(@Nullable BGALocalImageSize bGALocalImageSize, @Nullable ImageView.ScaleType scaleType, @DrawableRes int... iArr) {
        if (bGALocalImageSize == null) {
            bGALocalImageSize = new BGALocalImageSize(720, 1280, 320.0f, 640.0f);
        }
        if (scaleType != null) {
            this.mScaleType = scaleType;
        }
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            arrayList.add(BGABannerUtil.getItemImageView(getContext(), i, bGALocalImageSize, this.mScaleType));
        }
        setData(arrayList);
    }

    public void setAllowUserScrollable(boolean z) {
        this.mAllowUserScrollable = z;
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager != null) {
            bGAViewPager.setAllowUserScrollable(this.mAllowUserScrollable);
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setEnterSkipViewId(int i, int i2) {
        if (i != 0) {
            this.mEnterView = ((Activity) getContext()).findViewById(i);
        }
        if (i2 != 0) {
            this.mSkipView = ((Activity) getContext()).findViewById(i2);
        }
    }

    public void setEnterSkipViewIdAndDelegate(int i, int i2, GuideDelegate guideDelegate) {
        if (guideDelegate != null) {
            this.mGuideDelegate = guideDelegate;
            if (i != 0) {
                this.mEnterView = ((Activity) getContext()).findViewById(i);
                this.mEnterView.setOnClickListener(this.mGuideOnNoDoubleClickListener);
            }
            if (i2 != 0) {
                this.mSkipView = ((Activity) getContext()).findViewById(i2);
                this.mSkipView.setOnClickListener(this.mGuideOnNoDoubleClickListener);
            }
        }
        handleGuideViewVisibility(0, 0.0f);
    }

    public int getCurrentItem() {
        if (this.mViewPager == null || BGABannerUtil.isCollectionEmpty(this.mViews, new Collection[0])) {
            return -1;
        }
        return this.mViewPager.getCurrentItem() % this.mViews.size();
    }

    public int getItemCount() {
        List<View> list = this.mViews;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public List<? extends View> getViews() {
        return this.mViews;
    }

    public <VT extends View> VT getItemView(int i) {
        List<View> list = this.mViews;
        if (list == null) {
            return null;
        }
        return (VT) list.get(i);
    }

    public ImageView getItemImageView(int i) {
        return (ImageView) getItemView(i);
    }

    public List<String> getTips() {
        return this.mTips;
    }

    public BGAViewPager getViewPager() {
        return this.mViewPager;
    }

    @Override // android.view.View
    public void setOverScrollMode(int i) {
        this.mOverScrollMode = i;
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager != null) {
            bGAViewPager.setOverScrollMode(this.mOverScrollMode);
        }
    }

    public void setIndicatorVisibility(boolean z) {
        this.mPointContainerRl.setVisibility(z ? 0 : 8);
    }

    public void setIndicatorTopBottomMarginDp(int i) {
        setIndicatorTopBottomMarginPx(BGABannerUtil.dp2px(getContext(), i));
    }

    public void setIndicatorTopBottomMarginRes(@DimenRes int i) {
        setIndicatorTopBottomMarginPx(getResources().getDimensionPixelOffset(i));
    }

    public void setIndicatorTopBottomMarginPx(int i) {
        this.mPointTopBottomMargin = i;
        RelativeLayout relativeLayout = this.mPointContainerRl;
        int i2 = this.mPointContainerLeftRightPadding;
        int i3 = this.mPointTopBottomMargin;
        relativeLayout.setPadding(i2, i3, i2, i3);
    }

    private void initIndicator() {
        LinearLayout linearLayout = this.mPointRealContainerLl;
        if (linearLayout != null) {
            linearLayout.removeAllViews();
            boolean z = this.mIsNeedShowIndicatorOnOnlyOnePage;
            if (z || (!z && this.mViews.size() > 1)) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                int i = this.mPointLeftRightMargin;
                layoutParams.setMargins(i, 0, i, 0);
                for (int i2 = 0; i2 < this.mViews.size(); i2++) {
                    ImageView imageView = new ImageView(getContext());
                    imageView.setLayoutParams(layoutParams);
                    imageView.setImageResource(this.mPointDrawableResId);
                    this.mPointRealContainerLl.addView(imageView);
                }
            }
        }
        if (this.mNumberIndicatorTv != null) {
            boolean z2 = this.mIsNeedShowIndicatorOnOnlyOnePage;
            if (z2 || (!z2 && this.mViews.size() > 1)) {
                this.mNumberIndicatorTv.setVisibility(0);
            } else {
                this.mNumberIndicatorTv.setVisibility(4);
            }
        }
    }

    private void initViewPager() {
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager != null && equals(bGAViewPager.getParent())) {
            removeView(this.mViewPager);
            this.mViewPager = null;
        }
        this.mViewPager = new BGAViewPager(getContext());
        this.mViewPager.setAdapter(new PageAdapter());
        this.mViewPager.addOnPageChangeListener(this);
        this.mViewPager.setOverScrollMode(this.mOverScrollMode);
        this.mViewPager.setAllowUserScrollable(this.mAllowUserScrollable);
        this.mViewPager.setPageTransformer(true, BGAPageTransformer.getPageTransformer(this.mTransitionEffect));
        setPageChangeDuration(this.mPageChangeDuration);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.setMargins(0, 0, 0, this.mContentBottomMargin);
        addView(this.mViewPager, 0, layoutParams);
        if (this.mAutoPlayAble && !BGABannerUtil.isCollectionEmpty(this.mViews, new Collection[0])) {
            this.mViewPager.setAutoPlayDelegate(this);
            this.mViewPager.setCurrentItem(1073741823 - (1073741823 % this.mViews.size()));
            startAutoPlay();
            return;
        }
        switchToPoint(0);
    }

    public void removePlaceholder() {
        ImageView imageView = this.mPlaceholderIv;
        if (imageView == null || !equals(imageView.getParent())) {
            return;
        }
        removeView(this.mPlaceholderIv);
        this.mPlaceholderIv = null;
    }

    public void setAspectRatio(float f) {
        this.mAspectRatio = f;
        requestLayout();
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        try {
            if (this.mAspectRatio > 0.0f) {
                i2 = View.MeasureSpec.makeMeasureSpec((int) (View.MeasureSpec.getSize(i) / this.mAspectRatio), 1073741824);
            }
            setMeasuredDimension(i, i2);
            super.onMeasure(i, i2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mAutoPlayAble) {
            int action = motionEvent.getAction();
            if (action == 0) {
                stopAutoPlay();
            } else if (action == 1 || action == 3) {
                startAutoPlay();
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void setIsNeedShowIndicatorOnOnlyOnePage(boolean z) {
        this.mIsNeedShowIndicatorOnOnlyOnePage = z;
    }

    public void setCurrentItem(int i) {
        if (this.mViewPager == null || this.mViews == null) {
            return;
        }
        if (i > getItemCount() - 1) {
            return;
        }
        if (this.mAutoPlayAble) {
            int currentItem = this.mViewPager.getCurrentItem();
            int size = i - (currentItem % this.mViews.size());
            if (size < 0) {
                for (int i2 = -1; i2 >= size; i2--) {
                    this.mViewPager.setCurrentItem(currentItem + i2, false);
                }
            } else if (size > 0) {
                for (int i3 = 1; i3 <= size; i3++) {
                    this.mViewPager.setCurrentItem(currentItem + i3, false);
                }
            }
            startAutoPlay();
            return;
        }
        this.mViewPager.setCurrentItem(i, false);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            startAutoPlay();
        } else if (i != 4 && i != 8) {
        } else {
            onInvisibleToUser();
        }
    }

    private void onInvisibleToUser() {
        stopAutoPlay();
        if (!this.mIsFirstInvisible && this.mAutoPlayAble && this.mViewPager != null && getItemCount() > 0 && this.mPageScrollPositionOffset != 0.0f) {
            BGAViewPager bGAViewPager = this.mViewPager;
            bGAViewPager.setCurrentItem(bGAViewPager.getCurrentItem() - 1);
            BGAViewPager bGAViewPager2 = this.mViewPager;
            bGAViewPager2.setCurrentItem(bGAViewPager2.getCurrentItem() + 1);
        }
        this.mIsFirstInvisible = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onInvisibleToUser();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAutoPlay();
    }

    public void startAutoPlay() {
        stopAutoPlay();
        if (this.mAutoPlayAble) {
            postDelayed(this.mAutoPlayTask, this.mAutoPlayInterval);
        }
    }

    public void stopAutoPlay() {
        AutoPlayTask autoPlayTask = this.mAutoPlayTask;
        if (autoPlayTask != null) {
            removeCallbacks(autoPlayTask);
        }
    }

    private void switchToPoint(int i) {
        boolean z;
        boolean z2;
        if (this.mTipTv != null) {
            List<String> list = this.mTips;
            if (list == null || list.size() < 1 || i >= this.mTips.size()) {
                this.mTipTv.setVisibility(8);
            } else {
                this.mTipTv.setVisibility(0);
                this.mTipTv.setText(this.mTips.get(i));
            }
        }
        if (this.mPointRealContainerLl != null) {
            List<View> list2 = this.mViews;
            if (list2 != null && list2.size() > 0 && i < this.mViews.size() && ((z2 = this.mIsNeedShowIndicatorOnOnlyOnePage) || (!z2 && this.mViews.size() > 1))) {
                this.mPointRealContainerLl.setVisibility(0);
                int i2 = 0;
                while (i2 < this.mPointRealContainerLl.getChildCount()) {
                    this.mPointRealContainerLl.getChildAt(i2).setSelected(i2 == i);
                    this.mPointRealContainerLl.getChildAt(i2).requestLayout();
                    i2++;
                }
            } else {
                this.mPointRealContainerLl.setVisibility(8);
            }
        }
        if (this.mNumberIndicatorTv != null) {
            List<View> list3 = this.mViews;
            if (list3 != null && list3.size() > 0 && i < this.mViews.size() && ((z = this.mIsNeedShowIndicatorOnOnlyOnePage) || (!z && this.mViews.size() > 1))) {
                this.mNumberIndicatorTv.setVisibility(0);
                TextView textView = this.mNumberIndicatorTv;
                textView.setText((i + 1) + "/" + this.mViews.size());
                return;
            }
            this.mNumberIndicatorTv.setVisibility(8);
        }
    }

    public void setTransitionEffect(TransitionEffect transitionEffect) {
        this.mTransitionEffect = transitionEffect;
        if (this.mViewPager != null) {
            initViewPager();
            List<View> list = this.mHackyViews;
            if (list == null) {
                BGABannerUtil.resetPageTransformer(this.mViews);
            } else {
                BGABannerUtil.resetPageTransformer(list);
            }
        }
    }

    public void setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        BGAViewPager bGAViewPager;
        if (pageTransformer == null || (bGAViewPager = this.mViewPager) == null) {
            return;
        }
        bGAViewPager.setPageTransformer(true, pageTransformer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchToNextPage() {
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager != null) {
            bGAViewPager.setCurrentItem(bGAViewPager.getCurrentItem() + 1);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGAViewPager.AutoPlayDelegate
    public void handleAutoPlayActionUpOrCancel(float f) {
        BGAViewPager bGAViewPager = this.mViewPager;
        if (bGAViewPager != null) {
            if (this.mPageScrollPosition < bGAViewPager.getCurrentItem()) {
                if (f > 400.0f || (this.mPageScrollPositionOffset < 0.7f && f > -400.0f)) {
                    this.mViewPager.setBannerCurrentItemInternal(this.mPageScrollPosition, true);
                } else {
                    this.mViewPager.setBannerCurrentItemInternal(this.mPageScrollPosition + 1, true);
                }
            } else if (this.mPageScrollPosition == this.mViewPager.getCurrentItem()) {
                if (f < -400.0f || (this.mPageScrollPositionOffset > 0.3f && f < 400.0f)) {
                    this.mViewPager.setBannerCurrentItemInternal(this.mPageScrollPosition + 1, true);
                } else {
                    this.mViewPager.setBannerCurrentItemInternal(this.mPageScrollPosition, true);
                }
            } else {
                this.mViewPager.setBannerCurrentItemInternal(this.mPageScrollPosition, true);
            }
        }
    }

    @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        if (BGABannerUtil.isCollectionEmpty(this.mViews, new Collection[0])) {
            return;
        }
        int size = i % this.mViews.size();
        switchToPoint(size);
        ViewPager.OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener == null) {
            return;
        }
        onPageChangeListener.onPageSelected(size);
    }

    @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
        if (BGABannerUtil.isCollectionEmpty(this.mViews, new Collection[0])) {
            return;
        }
        handleGuideViewVisibility(i % this.mViews.size(), f);
        this.mPageScrollPosition = i;
        this.mPageScrollPositionOffset = f;
        if (this.mTipTv != null) {
            if (BGABannerUtil.isCollectionNotEmpty(this.mTips, new Collection[0])) {
                this.mTipTv.setVisibility(0);
                int size = i % this.mTips.size();
                int size2 = (i + 1) % this.mTips.size();
                if (size2 < this.mTips.size() && size < this.mTips.size()) {
                    if (f > 0.5d) {
                        this.mTipTv.setText(this.mTips.get(size2));
                        ViewCompat.setAlpha(this.mTipTv, f);
                    } else {
                        ViewCompat.setAlpha(this.mTipTv, 1.0f - f);
                        this.mTipTv.setText(this.mTips.get(size));
                    }
                }
            } else {
                this.mTipTv.setVisibility(8);
            }
        }
        ViewPager.OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener == null) {
            return;
        }
        onPageChangeListener.onPageScrolled(i % this.mViews.size(), f, i2);
    }

    @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
        ViewPager.OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(i);
        }
    }

    private void handleGuideViewVisibility(int i, float f) {
        if (this.mEnterView == null && this.mSkipView == null) {
            return;
        }
        if (getItemCount() < 2) {
            View view = this.mEnterView;
            if (view != null) {
                view.setVisibility(0);
                View view2 = this.mSkipView;
                if (view2 == null) {
                    return;
                }
                view2.setVisibility(8);
                return;
            }
            View view3 = this.mSkipView;
            if (view3 != null) {
                view3.setVisibility(0);
                return;
            }
        }
        if (i == getItemCount() - 2) {
            View view4 = this.mEnterView;
            if (view4 != null) {
                ViewCompat.setAlpha(view4, f);
            }
            View view5 = this.mSkipView;
            if (view5 != null) {
                ViewCompat.setAlpha(view5, 1.0f - f);
            }
            if (f > 0.5f) {
                View view6 = this.mEnterView;
                if (view6 != null) {
                    view6.setVisibility(0);
                }
                View view7 = this.mSkipView;
                if (view7 == null) {
                    return;
                }
                view7.setVisibility(8);
                return;
            }
            View view8 = this.mEnterView;
            if (view8 != null) {
                view8.setVisibility(8);
            }
            View view9 = this.mSkipView;
            if (view9 == null) {
                return;
            }
            view9.setVisibility(0);
        } else if (i == getItemCount() - 1) {
            View view10 = this.mEnterView;
            if (view10 != null) {
                ViewCompat.setAlpha(view10, 1.0f - f);
            }
            View view11 = this.mSkipView;
            if (view11 != null) {
                ViewCompat.setAlpha(view11, f);
            }
            if (f < 0.5f) {
                View view12 = this.mEnterView;
                if (view12 != null) {
                    view12.setVisibility(0);
                }
                View view13 = this.mSkipView;
                if (view13 == null) {
                    return;
                }
                view13.setVisibility(8);
                return;
            }
            View view14 = this.mEnterView;
            if (view14 != null) {
                view14.setVisibility(8);
            }
            View view15 = this.mSkipView;
            if (view15 == null) {
                return;
            }
            view15.setVisibility(0);
        } else {
            View view16 = this.mSkipView;
            if (view16 != null) {
                view16.setVisibility(0);
                ViewCompat.setAlpha(this.mSkipView, 1.0f);
            }
            View view17 = this.mEnterView;
            if (view17 == null) {
                return;
            }
            view17.setVisibility(8);
        }
    }

    public void setDelegate(Delegate delegate) {
        this.mDelegate = delegate;
    }

    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
    }

    public void onDestroyWebView() {
        stopAutoPlay();
        List<View> list = this.mViews;
        if (list != null) {
            for (View view : list) {
                if (view instanceof BannerWebView) {
                    ((BannerWebView) view).onDestroyWebView();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.BGABanner$PageAdapter */
    /* loaded from: classes4.dex */
    public class PageAdapter extends PagerAdapter {
        @Override // android.support.p002v4.view.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public int getItemPosition(Object obj) {
            return -2;
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        private PageAdapter() {
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public int getCount() {
            if (BGABanner.this.mViews == null) {
                return 0;
            }
            if (!BGABanner.this.mAutoPlayAble) {
                return BGABanner.this.mViews.size();
            }
            return Integer.MAX_VALUE;
        }

        @Override // android.support.p002v4.view.PagerAdapter
        /* renamed from: instantiateItem */
        public Object mo6346instantiateItem(ViewGroup viewGroup, int i) {
            if (BGABannerUtil.isCollectionEmpty(BGABanner.this.mViews, new Collection[0])) {
                return null;
            }
            int size = i % BGABanner.this.mViews.size();
            View view = BGABanner.this.mHackyViews == null ? (View) BGABanner.this.mViews.get(size) : (View) BGABanner.this.mHackyViews.get(i % BGABanner.this.mHackyViews.size());
            if (BGABanner.this.mDelegate != null) {
                view.setOnClickListener(new BGAOnNoDoubleClickListener() { // from class: com.tomatolive.library.ui.view.widget.bgabanner.BGABanner.PageAdapter.1
                    @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGAOnNoDoubleClickListener
                    public void onNoDoubleClick(View view2) {
                        int currentItem = BGABanner.this.mViewPager.getCurrentItem() % BGABanner.this.mViews.size();
                        if (BGABannerUtil.isIndexNotOutOfBounds(currentItem, BGABanner.this.mModels)) {
                            Delegate delegate = BGABanner.this.mDelegate;
                            BGABanner bGABanner = BGABanner.this;
                            delegate.onBannerItemClick(bGABanner, view2, bGABanner.mModels.get(currentItem), currentItem);
                        } else if (!BGABannerUtil.isCollectionEmpty(BGABanner.this.mModels, new Collection[0])) {
                        } else {
                            BGABanner.this.mDelegate.onBannerItemClick(BGABanner.this, view2, null, currentItem);
                        }
                    }
                });
            }
            if (BGABanner.this.mAdapter != null) {
                if (BGABannerUtil.isIndexNotOutOfBounds(size, BGABanner.this.mModels)) {
                    Adapter adapter = BGABanner.this.mAdapter;
                    BGABanner bGABanner = BGABanner.this;
                    adapter.fillBannerItem(bGABanner, view, bGABanner.mModels.get(size), size);
                } else if (BGABannerUtil.isCollectionEmpty(BGABanner.this.mModels, new Collection[0])) {
                    BGABanner.this.mAdapter.fillBannerItem(BGABanner.this, view, null, size);
                }
            }
            ViewParent parent = view.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(view);
            }
            viewGroup.addView(view);
            return view;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.BGABanner$AutoPlayTask */
    /* loaded from: classes4.dex */
    public static class AutoPlayTask implements Runnable {
        private final WeakReference<BGABanner> mBanner;

        private AutoPlayTask(BGABanner bGABanner) {
            this.mBanner = new WeakReference<>(bGABanner);
        }

        @Override // java.lang.Runnable
        public void run() {
            BGABanner bGABanner = this.mBanner.get();
            if (bGABanner != null) {
                bGABanner.startAutoPlay();
                bGABanner.switchToNextPage();
            }
        }
    }

    public void removeData(View view) {
        BGAViewPager bGAViewPager;
        if (view == null || this.mViews == null || (bGAViewPager = this.mViewPager) == null || bGAViewPager.getAdapter() == null) {
            return;
        }
        this.mModels.remove(this.mViews.indexOf(view));
        this.mViews.remove(view);
        boolean z = true;
        if (this.mViews.size() <= 1) {
            z = false;
        }
        setAutoPlayAble(z);
        setDataWithWebView(this.mViews, this.mModels);
    }
}
