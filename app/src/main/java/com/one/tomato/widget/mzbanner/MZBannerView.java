package com.one.tomato.widget.mzbanner;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.p002v4.view.PagerAdapter;
import android.support.p002v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import com.broccoli.p150bh.R;
import com.one.tomato.R$styleable;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.widget.mzbanner.holder.MZHolderCreator;
import com.one.tomato.widget.mzbanner.holder.MZViewHolder;
import com.one.tomato.widget.mzbanner.transformer.CoverModeTransformer;
import com.one.tomato.widget.mzbanner.transformer.ScaleYTransformer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MZBannerView<T> extends RelativeLayout {
    private MZPagerAdapter mAdapter;
    private BannerPageClickListener mBannerPageClickListener;
    private List<T> mDatas;
    private LinearLayout mIndicatorContainer;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private CustomViewPager mViewPager;
    private ViewPagerScroller mViewPagerScroller;
    private boolean mIsAutoPlay = true;
    private int mCurrentItem = 0;
    private Handler mHandler = new Handler();
    private int mDelayedTime = 3000;
    private boolean mIsOpenMZEffect = true;
    private boolean mIsCanLoop = true;
    private ArrayList<ImageView> mIndicators = new ArrayList<>();
    private int[] mIndicatorRes = {R.drawable.indicator_normal, R.drawable.indicator_selected};
    private int mIndicatorPaddingLeft = 0;
    private int mIndicatorPaddingRight = 0;
    private int mIndicatorPaddingTop = 0;
    private int mIndicatorPaddingBottom = 0;
    private int mMZModePadding = 0;
    private int mIndicatorAlign = 1;
    private boolean mIsMiddlePageCover = true;
    private final Runnable mLoopRunnable = new Runnable() { // from class: com.one.tomato.widget.mzbanner.MZBannerView.1
        @Override // java.lang.Runnable
        public void run() {
            if (!MZBannerView.this.mIsAutoPlay) {
                MZBannerView.this.mHandler.postDelayed(this, MZBannerView.this.mDelayedTime);
                return;
            }
            MZBannerView mZBannerView = MZBannerView.this;
            mZBannerView.mCurrentItem = mZBannerView.mViewPager.getCurrentItem();
            MZBannerView.access$108(MZBannerView.this);
            if (MZBannerView.this.mCurrentItem == MZBannerView.this.mAdapter.getCount() - 1) {
                MZBannerView.this.mCurrentItem = 0;
                MZBannerView.this.mViewPager.setCurrentItem(MZBannerView.this.mCurrentItem, false);
                MZBannerView.this.mHandler.postDelayed(this, MZBannerView.this.mDelayedTime);
                return;
            }
            MZBannerView.this.mViewPager.setCurrentItem(MZBannerView.this.mCurrentItem);
            MZBannerView.this.mHandler.postDelayed(this, MZBannerView.this.mDelayedTime);
        }
    };

    /* loaded from: classes3.dex */
    public interface BannerPageClickListener {
        void onPageClick(View view, int i);
    }

    /* loaded from: classes3.dex */
    public enum IndicatorAlign {
        LEFT,
        CENTER,
        RIGHT
    }

    static /* synthetic */ int access$108(MZBannerView mZBannerView) {
        int i = mZBannerView.mCurrentItem;
        mZBannerView.mCurrentItem = i + 1;
        return i;
    }

    public MZBannerView(@NonNull Context context) {
        super(context);
        init();
    }

    public MZBannerView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        readAttrs(context, attributeSet);
        init();
    }

    public MZBannerView(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        super(context, attributeSet, i);
        readAttrs(context, attributeSet);
        init();
    }

    @RequiresApi(api = 21)
    public MZBannerView(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        super(context, attributeSet, i, i2);
        readAttrs(context, attributeSet);
        init();
    }

    private void readAttrs(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MZBannerView);
        this.mIsOpenMZEffect = obtainStyledAttributes.getBoolean(7, true);
        this.mIsMiddlePageCover = obtainStyledAttributes.getBoolean(6, true);
        this.mIsCanLoop = obtainStyledAttributes.getBoolean(0, true);
        this.mIndicatorAlign = obtainStyledAttributes.getInt(1, IndicatorAlign.CENTER.ordinal());
        this.mIndicatorPaddingLeft = obtainStyledAttributes.getDimensionPixelSize(3, 0);
        this.mIndicatorPaddingRight = obtainStyledAttributes.getDimensionPixelSize(4, 0);
        this.mIndicatorPaddingTop = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        this.mIndicatorPaddingBottom = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        obtainStyledAttributes.recycle();
    }

    private void init() {
        View inflate;
        if (this.mIsOpenMZEffect) {
            inflate = LayoutInflater.from(getContext()).inflate(R.layout.banner_effect_layout, (ViewGroup) this, true);
        } else {
            inflate = LayoutInflater.from(getContext()).inflate(R.layout.banner_normal_layout, (ViewGroup) this, true);
        }
        this.mIndicatorContainer = (LinearLayout) inflate.findViewById(R.id.banner_indicator_container);
        this.mViewPager = (CustomViewPager) inflate.findViewById(R.id.mzbanner_vp);
        this.mViewPager.setOffscreenPageLimit(4);
        this.mViewPager.setPageMargin((int) DisplayMetricsUtils.dp2px(8.0f));
        this.mMZModePadding = dpToPx(30);
        initViewPagerScroll();
        sureIndicatorPosition();
    }

    private void setOpenMZEffect() {
        if (this.mIsOpenMZEffect) {
            if (this.mIsMiddlePageCover) {
                CustomViewPager customViewPager = this.mViewPager;
                customViewPager.setPageTransformer(true, new CoverModeTransformer(customViewPager));
                return;
            }
            this.mViewPager.setPageTransformer(false, new ScaleYTransformer());
        }
    }

    private void sureIndicatorPosition() {
        if (this.mIndicatorAlign == IndicatorAlign.LEFT.ordinal()) {
            setIndicatorAlign(IndicatorAlign.LEFT);
        } else if (this.mIndicatorAlign == IndicatorAlign.CENTER.ordinal()) {
            setIndicatorAlign(IndicatorAlign.CENTER);
        } else {
            setIndicatorAlign(IndicatorAlign.RIGHT);
        }
    }

    private void initViewPagerScroll() {
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            this.mViewPagerScroller = new ViewPagerScroller(this.mViewPager.getContext());
            declaredField.set(this.mViewPager, this.mViewPagerScroller);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
        }
    }

    private void initIndicator() {
        this.mIndicatorContainer.removeAllViews();
        this.mIndicators.clear();
        for (int i = 0; i < this.mDatas.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            if (this.mIndicatorAlign == IndicatorAlign.LEFT.ordinal()) {
                if (i == 0) {
                    imageView.setPadding((this.mIsOpenMZEffect ? this.mIndicatorPaddingLeft + this.mMZModePadding : this.mIndicatorPaddingLeft) + 6, 0, 6, 0);
                } else {
                    imageView.setPadding(6, 0, 6, 0);
                }
            } else if (this.mIndicatorAlign == IndicatorAlign.RIGHT.ordinal()) {
                if (i == this.mDatas.size() - 1) {
                    imageView.setPadding(6, 0, (this.mIsOpenMZEffect ? this.mMZModePadding + this.mIndicatorPaddingRight : this.mIndicatorPaddingRight) + 6, 0);
                } else {
                    imageView.setPadding(6, 0, 6, 0);
                }
            } else {
                imageView.setPadding(6, 0, 6, 0);
            }
            if (i == this.mCurrentItem % this.mDatas.size()) {
                imageView.setImageResource(this.mIndicatorRes[1]);
            } else {
                imageView.setImageResource(this.mIndicatorRes[0]);
            }
            this.mIndicators.add(imageView);
            this.mIndicatorContainer.addView(imageView);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0019, code lost:
        if (r0 != 4) goto L16;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.mIsCanLoop) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                start();
            } else if (action != 2) {
                if (action != 3) {
                }
            }
            return super.dispatchTouchEvent(motionEvent);
        }
        int left = this.mViewPager.getLeft();
        float rawX = motionEvent.getRawX();
        if (rawX >= left && rawX < getScreenWidth(getContext()) - left) {
            pause();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public void start() {
        if (this.mAdapter != null && this.mIsCanLoop) {
            pause();
            this.mIsAutoPlay = true;
            this.mHandler.postDelayed(this.mLoopRunnable, this.mDelayedTime);
        }
    }

    public void pause() {
        this.mIsAutoPlay = false;
        this.mHandler.removeCallbacks(this.mLoopRunnable);
    }

    public void setCanLoop(boolean z) {
        this.mIsCanLoop = z;
        if (!z) {
            pause();
        }
    }

    public void setDelayedTime(int i) {
        this.mDelayedTime = i;
    }

    public void addPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setBannerPageClickListener(BannerPageClickListener bannerPageClickListener) {
        this.mBannerPageClickListener = bannerPageClickListener;
    }

    public void setIndicatorVisible(boolean z) {
        if (z) {
            this.mIndicatorContainer.setVisibility(0);
        } else {
            this.mIndicatorContainer.setVisibility(8);
        }
    }

    public void setIndicatorPadding(int i, int i2, int i3, int i4) {
        this.mIndicatorPaddingLeft = i;
        this.mIndicatorPaddingTop = i2;
        this.mIndicatorPaddingRight = i3;
        this.mIndicatorPaddingBottom = i4;
        sureIndicatorPosition();
    }

    public ViewPager getViewPager() {
        return this.mViewPager;
    }

    public void setIndicatorRes(@DrawableRes int i, @DrawableRes int i2) {
        int[] iArr = this.mIndicatorRes;
        iArr[0] = i;
        iArr[1] = i2;
    }

    public void setPages(List<T> list, MZHolderCreator mZHolderCreator) {
        if (list == null || mZHolderCreator == null) {
            return;
        }
        this.mDatas = list;
        pause();
        if (list.size() < 3) {
            this.mIsOpenMZEffect = false;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mViewPager.getLayoutParams();
            marginLayoutParams.setMargins(0, 0, 0, 0);
            this.mViewPager.setLayoutParams(marginLayoutParams);
            setClipChildren(true);
            this.mViewPager.setClipChildren(true);
        }
        setOpenMZEffect();
        initIndicator();
        this.mAdapter = new MZPagerAdapter(list, mZHolderCreator, this.mIsCanLoop);
        this.mAdapter.setUpViewViewPager(this.mViewPager);
        this.mAdapter.setPageClickListener(this.mBannerPageClickListener);
        this.mViewPager.clearOnPageChangeListeners();
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.one.tomato.widget.mzbanner.MZBannerView.2
            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
                int size = i % MZBannerView.this.mIndicators.size();
                if (MZBannerView.this.mOnPageChangeListener != null) {
                    MZBannerView.this.mOnPageChangeListener.onPageScrolled(size, f, i2);
                }
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                MZBannerView.this.mCurrentItem = i;
                int size = MZBannerView.this.mCurrentItem % MZBannerView.this.mIndicators.size();
                for (int i2 = 0; i2 < MZBannerView.this.mDatas.size(); i2++) {
                    if (i2 == size) {
                        ((ImageView) MZBannerView.this.mIndicators.get(i2)).setImageResource(MZBannerView.this.mIndicatorRes[1]);
                    } else {
                        ((ImageView) MZBannerView.this.mIndicators.get(i2)).setImageResource(MZBannerView.this.mIndicatorRes[0]);
                    }
                }
                if (MZBannerView.this.mOnPageChangeListener != null) {
                    MZBannerView.this.mOnPageChangeListener.onPageSelected(size);
                }
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
                if (i == 1) {
                    MZBannerView.this.mIsAutoPlay = false;
                } else if (i == 2) {
                    MZBannerView.this.mIsAutoPlay = true;
                }
                if (MZBannerView.this.mOnPageChangeListener != null) {
                    MZBannerView.this.mOnPageChangeListener.onPageScrollStateChanged(i);
                }
            }
        });
    }

    public void setIndicatorAlign(IndicatorAlign indicatorAlign) {
        this.mIndicatorAlign = indicatorAlign.ordinal();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mIndicatorContainer.getLayoutParams();
        if (indicatorAlign == IndicatorAlign.LEFT) {
            layoutParams.addRule(9);
        } else if (indicatorAlign == IndicatorAlign.RIGHT) {
            layoutParams.addRule(11);
        } else {
            layoutParams.addRule(14);
        }
        layoutParams.setMargins(0, this.mIndicatorPaddingTop, 0, this.mIndicatorPaddingBottom);
        this.mIndicatorContainer.setLayoutParams(layoutParams);
    }

    public LinearLayout getIndicatorContainer() {
        return this.mIndicatorContainer;
    }

    public void setDuration(int i) {
        this.mViewPagerScroller.setDuration(i);
    }

    public void setUseDefaultDuration(boolean z) {
        this.mViewPagerScroller.setUseDefaultDuration(z);
    }

    public int getDuration() {
        return this.mViewPagerScroller.getScrollDuration();
    }

    /* loaded from: classes3.dex */
    public static class MZPagerAdapter<T> extends PagerAdapter {
        private boolean canLoop;
        private List<T> mDatas;
        private MZHolderCreator mMZHolderCreator;
        private BannerPageClickListener mPageClickListener;
        private ViewPager mViewPager;

        @Override // android.support.p002v4.view.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public MZPagerAdapter(List<T> list, MZHolderCreator mZHolderCreator, boolean z) {
            if (this.mDatas == null) {
                this.mDatas = new ArrayList();
            }
            for (T t : list) {
                this.mDatas.add(t);
            }
            this.mMZHolderCreator = mZHolderCreator;
            this.canLoop = z;
        }

        public void setPageClickListener(BannerPageClickListener bannerPageClickListener) {
            this.mPageClickListener = bannerPageClickListener;
        }

        public void setUpViewViewPager(ViewPager viewPager) {
            this.mViewPager = viewPager;
            this.mViewPager.setAdapter(this);
            this.mViewPager.getAdapter().notifyDataSetChanged();
            this.mViewPager.setCurrentItem(this.canLoop ? getStartSelectItem() : 0);
        }

        private int getStartSelectItem() {
            if (getRealCount() == 0) {
                return 0;
            }
            int realCount = (getRealCount() * 500) / 2;
            if (realCount % getRealCount() == 0) {
                return realCount;
            }
            while (realCount % getRealCount() != 0) {
                realCount++;
            }
            return realCount;
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public int getCount() {
            return this.canLoop ? getRealCount() * 500 : getRealCount();
        }

        @Override // android.support.p002v4.view.PagerAdapter
        /* renamed from: instantiateItem */
        public Object mo6346instantiateItem(ViewGroup viewGroup, int i) {
            View view = getView(i, viewGroup);
            viewGroup.addView(view);
            return view;
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public void finishUpdate(ViewGroup viewGroup) {
            if (!this.canLoop || this.mViewPager.getCurrentItem() != getCount() - 1) {
                return;
            }
            setCurrentItem(0);
        }

        private void setCurrentItem(int i) {
            try {
                this.mViewPager.setCurrentItem(i, false);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        private int getRealCount() {
            List<T> list = this.mDatas;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        private View getView(int i, ViewGroup viewGroup) {
            final int realCount = i % getRealCount();
            MZViewHolder createViewHolder = this.mMZHolderCreator.createViewHolder();
            if (createViewHolder == null) {
                throw new RuntimeException("can not return a null holder");
            }
            View createView = createViewHolder.createView(viewGroup.getContext());
            List<T> list = this.mDatas;
            if (list != null && list.size() > 0) {
                createViewHolder.onBind(viewGroup.getContext(), realCount, this.mDatas.get(realCount));
            }
            createView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.widget.mzbanner.MZBannerView.MZPagerAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (MZPagerAdapter.this.mPageClickListener != null) {
                        MZPagerAdapter.this.mPageClickListener.onPageClick(view, realCount);
                    }
                }
            });
            return createView;
        }
    }

    /* loaded from: classes3.dex */
    public static class ViewPagerScroller extends Scroller {
        private int mDuration = 800;
        private boolean mIsUseDefaultDuration = false;

        public ViewPagerScroller(Context context) {
            super(context);
        }

        @Override // android.widget.Scroller
        public void startScroll(int i, int i2, int i3, int i4) {
            super.startScroll(i, i2, i3, i4, this.mDuration);
        }

        @Override // android.widget.Scroller
        public void startScroll(int i, int i2, int i3, int i4, int i5) {
            if (!this.mIsUseDefaultDuration) {
                i5 = this.mDuration;
            }
            super.startScroll(i, i2, i3, i4, i5);
        }

        public void setUseDefaultDuration(boolean z) {
            this.mIsUseDefaultDuration = z;
        }

        public void setDuration(int i) {
            this.mDuration = i;
        }

        public int getScrollDuration() {
            return this.mDuration;
        }
    }

    public static int dpToPx(int i) {
        return (int) TypedValue.applyDimension(1, i, Resources.getSystem().getDisplayMetrics());
    }
}
