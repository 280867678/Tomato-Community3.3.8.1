package com.one.tomato.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.app.FragmentTransaction;
import android.support.p002v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$styleable;
import com.one.tomato.adapter.TabFragmentAdapter;
import java.util.Locale;

/* loaded from: classes3.dex */
public class PagerSlidingTabStrip extends HorizontalScrollView {
    private static final int[] ATTRS = {16842901, 16842904, 16842964};
    private TabFragmentAdapter adapter;
    private int containerViewId;
    private int currentPosition;
    private float currentPositionOffset;
    public ViewPager.OnPageChangeListener delegatePageListener;
    private int dividerColor;
    private int dividerPadding;
    private Paint dividerPaint;
    private int dividerWidth;

    /* renamed from: dm */
    private DisplayMetrics f1762dm;
    private FragmentManager fragmentManager;
    private int indicatorColor;
    private int indicatorHeight;
    private int indicatorWith;
    private boolean isBoldText;
    private boolean isCustomIndicator;
    private boolean isShowAllTabs;
    private int lastScrollX;
    private Locale locale;
    private final PageListener pageListener;
    private ViewPager pager;
    private Paint rectPaint;
    private int screenWidth;
    private int scrollOffset;
    private int selectedPosition;
    private int selectedTabBackgroundRes;
    private int selectedTabTextColor;
    private int selectedTabTextSize;
    private boolean shouldExpand;
    private int tabBackgroundResId;
    private int tabCount;
    private int tabMargin;
    private int tabPadding;
    private int tabTextColor;
    private int tabTextSize;
    private LinearLayout tabsContainer;
    private boolean textAllCaps;
    private int underlineColor;
    private int underlineHeight;

    /* loaded from: classes3.dex */
    public interface IconTabProvider {
        int getPageIconResId(int i);
    }

    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.pageListener = new PageListener();
        this.currentPosition = 0;
        this.selectedPosition = 0;
        this.currentPositionOffset = 0.0f;
        this.indicatorColor = -10066330;
        this.underlineColor = 436207616;
        this.dividerColor = 436207616;
        this.isShowAllTabs = false;
        this.shouldExpand = false;
        this.textAllCaps = false;
        this.scrollOffset = 52;
        this.indicatorWith = 20;
        this.indicatorHeight = 2;
        this.underlineHeight = 2;
        this.dividerPadding = 12;
        this.tabPadding = 15;
        this.tabMargin = 0;
        this.dividerWidth = 0;
        this.tabTextSize = 16;
        this.tabTextColor = -10066330;
        this.selectedTabTextSize = this.tabTextSize;
        this.selectedTabTextColor = -10066330;
        this.selectedTabBackgroundRes = 0;
        this.lastScrollX = 0;
        this.tabBackgroundResId = 0;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        setFillViewport(true);
        setWillNotDraw(false);
        this.tabsContainer = new LinearLayout(context);
        this.tabsContainer.setOrientation(0);
        this.tabsContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(this.tabsContainer);
        this.f1762dm = getResources().getDisplayMetrics();
        this.scrollOffset = (int) TypedValue.applyDimension(1, this.scrollOffset, this.f1762dm);
        this.indicatorWith = (int) TypedValue.applyDimension(1, this.indicatorWith, this.f1762dm);
        this.indicatorHeight = (int) TypedValue.applyDimension(1, this.indicatorHeight, this.f1762dm);
        this.underlineHeight = (int) TypedValue.applyDimension(1, this.underlineHeight, this.f1762dm);
        this.dividerPadding = (int) TypedValue.applyDimension(1, this.dividerPadding, this.f1762dm);
        this.tabPadding = (int) TypedValue.applyDimension(1, this.tabPadding, this.f1762dm);
        this.tabMargin = (int) TypedValue.applyDimension(1, this.tabMargin, this.f1762dm);
        this.dividerWidth = (int) TypedValue.applyDimension(1, this.dividerWidth, this.f1762dm);
        this.tabTextSize = (int) TypedValue.applyDimension(2, this.tabTextSize, this.f1762dm);
        this.selectedTabTextSize = (int) TypedValue.applyDimension(2, this.selectedTabTextSize, this.f1762dm);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS);
        this.tabTextSize = obtainStyledAttributes.getDimensionPixelSize(0, this.tabTextSize);
        this.tabTextColor = obtainStyledAttributes.getColor(1, this.tabTextColor);
        this.selectedTabTextSize = obtainStyledAttributes.getDimensionPixelSize(0, this.selectedTabTextSize);
        this.selectedTabTextColor = obtainStyledAttributes.getColor(1, this.selectedTabTextColor);
        this.selectedTabBackgroundRes = obtainStyledAttributes.getResourceId(2, this.selectedTabBackgroundRes);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.PagerSlidingTabStrip);
        this.indicatorColor = obtainStyledAttributes2.getColor(2, this.indicatorColor);
        this.underlineColor = obtainStyledAttributes2.getColor(11, this.underlineColor);
        this.dividerColor = obtainStyledAttributes2.getColor(0, this.dividerColor);
        this.indicatorWith = obtainStyledAttributes2.getDimensionPixelSize(4, this.indicatorWith);
        this.indicatorHeight = obtainStyledAttributes2.getDimensionPixelSize(3, this.indicatorHeight);
        this.underlineHeight = obtainStyledAttributes2.getDimensionPixelSize(12, this.underlineHeight);
        this.dividerPadding = obtainStyledAttributes2.getDimensionPixelSize(1, this.dividerPadding);
        this.tabPadding = obtainStyledAttributes2.getDimensionPixelSize(9, this.tabPadding);
        this.tabBackgroundResId = obtainStyledAttributes2.getResourceId(7, this.tabBackgroundResId);
        this.shouldExpand = obtainStyledAttributes2.getBoolean(6, this.shouldExpand);
        this.scrollOffset = obtainStyledAttributes2.getDimensionPixelSize(5, this.scrollOffset);
        this.textAllCaps = obtainStyledAttributes2.getBoolean(10, this.textAllCaps);
        this.tabMargin = obtainStyledAttributes2.getDimensionPixelSize(8, this.tabMargin);
        obtainStyledAttributes2.recycle();
        this.rectPaint = new Paint();
        this.rectPaint.setAntiAlias(true);
        this.rectPaint.setStyle(Paint.Style.FILL);
        this.dividerPaint = new Paint();
        this.dividerPaint.setAntiAlias(true);
        this.dividerPaint.setStrokeWidth(this.dividerWidth);
        if (this.locale == null) {
            this.locale = getResources().getConfiguration().locale;
        }
    }

    public void setViewPager(ViewPager viewPager) {
        this.pager = viewPager;
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        viewPager.setOnPageChangeListener(this.pageListener);
        notifyDataSetChanged();
    }

    public void setFragmentManager(FragmentManager fragmentManager, int i, TabFragmentAdapter tabFragmentAdapter) {
        this.fragmentManager = fragmentManager;
        this.containerViewId = i;
        this.adapter = tabFragmentAdapter;
        if (tabFragmentAdapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        notifyDataSetChangedByFragment();
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.delegatePageListener = onPageChangeListener;
    }

    public void notifyDataSetChangedByFragment() {
        this.tabsContainer.removeAllViews();
        this.tabCount = this.adapter.getCount();
        for (int i = 0; i < this.tabCount; i++) {
            TabFragmentAdapter tabFragmentAdapter = this.adapter;
            if (tabFragmentAdapter instanceof IconTabProvider) {
                addIconTab(i, ((IconTabProvider) tabFragmentAdapter).getPageIconResId(i));
            } else {
                addTextTab(i, tabFragmentAdapter.getPageTitle(i).toString());
            }
        }
        setCurrentFragment(0);
        updateTabStyles();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.one.tomato.widget.PagerSlidingTabStrip.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                PagerSlidingTabStrip.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                PagerSlidingTabStrip pagerSlidingTabStrip = PagerSlidingTabStrip.this;
                pagerSlidingTabStrip.scrollToChild(pagerSlidingTabStrip.currentPosition, 0);
            }
        });
    }

    public void notifyDataSetChanged() {
        this.tabsContainer.removeAllViews();
        this.tabCount = this.pager.getAdapter().getCount();
        for (int i = 0; i < this.tabCount; i++) {
            if (this.pager.getAdapter() instanceof IconTabProvider) {
                addIconTab(i, ((IconTabProvider) this.pager.getAdapter()).getPageIconResId(i));
            } else {
                addTextTab(i, this.pager.getAdapter().getPageTitle(i).toString());
            }
        }
        updateTabStyles();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.one.tomato.widget.PagerSlidingTabStrip.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                PagerSlidingTabStrip.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                PagerSlidingTabStrip pagerSlidingTabStrip = PagerSlidingTabStrip.this;
                pagerSlidingTabStrip.currentPosition = pagerSlidingTabStrip.pager.getCurrentItem();
                PagerSlidingTabStrip pagerSlidingTabStrip2 = PagerSlidingTabStrip.this;
                pagerSlidingTabStrip2.scrollToChild(pagerSlidingTabStrip2.currentPosition, 0);
            }
        });
    }

    private void addTextTab(int i, String str) {
        TextView textView = new TextView(getContext());
        textView.setText(str);
        textView.setGravity(17);
        textView.setSingleLine();
        addTab(i, textView);
    }

    private void addIconTab(int i, int i2) {
        ImageButton imageButton = new ImageButton(getContext());
        imageButton.setImageResource(i2);
        addTab(i, imageButton);
    }

    private void addTab(final int i, View view) {
        view.setFocusable(true);
        view.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.widget.PagerSlidingTabStrip.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PagerSlidingTabStrip.this.fragmentManager != null) {
                    int i2 = PagerSlidingTabStrip.this.currentPosition;
                    int i3 = i;
                    if (i2 == i3) {
                        PagerSlidingTabStrip.this.scrollToChild(i3, 0);
                        return;
                    }
                    PagerSlidingTabStrip.this.currentPosition = i3;
                    PagerSlidingTabStrip.this.selectedPosition = i;
                    PagerSlidingTabStrip.this.hideAllFragment();
                    PagerSlidingTabStrip.this.setCurrentFragment(i);
                    PagerSlidingTabStrip.this.updateTabStyles();
                    PagerSlidingTabStrip.this.scrollToChild(i, 0);
                }
                if (PagerSlidingTabStrip.this.pager != null) {
                    PagerSlidingTabStrip.this.pager.setCurrentItem(i);
                }
            }
        });
        int i2 = this.tabPadding;
        view.setPadding(i2, 0, i2, 0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.screenWidth / this.tabCount, -1, 1.0f);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -1);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(0, -1, 1.0f);
        if (this.shouldExpand) {
            layoutParams2 = layoutParams3;
        }
        if (!this.isShowAllTabs) {
            layoutParams = layoutParams2;
        }
        int i3 = this.tabMargin;
        if (i3 > 0) {
            if (i == this.tabCount - 1) {
                layoutParams.leftMargin = i3;
                layoutParams.rightMargin = i3;
            } else {
                layoutParams.leftMargin = i3;
                layoutParams.rightMargin = 0;
            }
        }
        this.tabsContainer.addView(view, i, layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentFragment(int i) {
        Fragment item = this.adapter.getItem(i);
        String charSequence = this.adapter.getPageTitle(i).toString();
        FragmentTransaction beginTransaction = this.fragmentManager.beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out, R.anim.slide_right_in, R.anim.slide_right_out);
        if (this.fragmentManager.findFragmentByTag(charSequence) != null) {
            beginTransaction.show(item);
        } else {
            beginTransaction.add(this.containerViewId, item, charSequence);
        }
        beginTransaction.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideAllFragment() {
        FragmentTransaction beginTransaction = this.fragmentManager.beginTransaction();
        for (int i = 0; i < this.adapter.getCount(); i++) {
            if (this.adapter.getItem(i).isVisible()) {
                beginTransaction.hide(this.adapter.getItem(i));
            }
        }
        beginTransaction.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTabStyles() {
        for (int i = 0; i < this.tabCount; i++) {
            View childAt = this.tabsContainer.getChildAt(i);
            childAt.setBackgroundResource(this.tabBackgroundResId);
            if (childAt instanceof TextView) {
                TextView textView = (TextView) childAt;
                TextPaint paint = textView.getPaint();
                textView.setTextSize(0, this.tabTextSize);
                textView.setTextColor(this.tabTextColor);
                if (this.textAllCaps) {
                    if (Build.VERSION.SDK_INT >= 14) {
                        textView.setAllCaps(true);
                    } else {
                        textView.setText(textView.getText().toString().toUpperCase(this.locale));
                    }
                }
                if (i == this.selectedPosition) {
                    textView.setTextColor(this.selectedTabTextColor);
                    int i2 = this.selectedTabBackgroundRes;
                    if (i2 != 0) {
                        textView.setBackgroundResource(i2);
                    }
                    int i3 = this.selectedTabTextSize;
                    if (i3 != this.tabTextSize) {
                        textView.setTextSize(0, i3);
                    }
                }
                if (this.isBoldText) {
                    paint.setFakeBoldText(true);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollToChild(int i, int i2) {
        if (this.tabCount == 0) {
            return;
        }
        int left = (this.tabsContainer.getChildAt(i).getLeft() + i2) - this.tabMargin;
        if (i > 0 || i2 > 0) {
            left -= this.scrollOffset;
        }
        if (left == this.lastScrollX) {
            return;
        }
        this.lastScrollX = left;
        scrollTo(left, 0);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float left;
        float right;
        float f;
        float f2;
        int i;
        float left2;
        float right2;
        float f3;
        super.onDraw(canvas);
        if (isInEditMode() || this.tabCount == 0) {
            return;
        }
        int height = getHeight();
        this.rectPaint.setColor(this.underlineColor);
        float f4 = height;
        canvas.drawRect(0.0f, height - this.underlineHeight, this.tabsContainer.getWidth(), f4, this.rectPaint);
        this.rectPaint.setColor(this.indicatorColor);
        View childAt = this.tabsContainer.getChildAt(this.currentPosition);
        if (this.isCustomIndicator) {
            left = (childAt.getLeft() + ((childAt.getRight() - childAt.getLeft()) / 2)) - this.indicatorWith;
            right = childAt.getLeft() + ((childAt.getRight() - childAt.getLeft()) / 2) + this.indicatorWith;
        } else {
            left = childAt.getLeft();
            right = childAt.getRight();
        }
        if (this.currentPositionOffset <= 0.0f || (i = this.currentPosition) >= this.tabCount - 1) {
            f = right;
            f2 = left;
        } else {
            View childAt2 = this.tabsContainer.getChildAt(i + 1);
            if (this.isCustomIndicator) {
                left2 = (childAt2.getLeft() + ((childAt2.getRight() - childAt2.getLeft()) / 2)) - this.indicatorWith;
                right2 = childAt2.getLeft() + ((childAt2.getRight() - childAt2.getLeft()) / 2) + this.indicatorWith;
                f3 = this.currentPositionOffset;
            } else {
                left2 = childAt2.getLeft();
                right2 = childAt2.getRight();
                f3 = this.currentPositionOffset;
            }
            f = (right2 * f3) + ((1.0f - f3) * right);
            f2 = (left2 * f3) + ((1.0f - f3) * left);
        }
        canvas.drawRect(f2, height - this.indicatorHeight, f, f4, this.rectPaint);
        this.dividerPaint.setColor(this.dividerColor);
        for (int i2 = 0; i2 < this.tabCount - 1; i2++) {
            View childAt3 = this.tabsContainer.getChildAt(i2);
            canvas.drawLine(childAt3.getRight(), this.dividerPadding, childAt3.getRight(), height - this.dividerPadding, this.dividerPaint);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class PageListener implements ViewPager.OnPageChangeListener {
        private PageListener() {
        }

        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
            PagerSlidingTabStrip.this.currentPosition = i;
            PagerSlidingTabStrip.this.currentPositionOffset = f;
            PagerSlidingTabStrip pagerSlidingTabStrip = PagerSlidingTabStrip.this;
            pagerSlidingTabStrip.scrollToChild(i, (int) (pagerSlidingTabStrip.tabsContainer.getChildAt(i).getWidth() * f));
            PagerSlidingTabStrip.this.invalidate();
            ViewPager.OnPageChangeListener onPageChangeListener = PagerSlidingTabStrip.this.delegatePageListener;
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageScrolled(i, f, i2);
            }
        }

        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            if (i == 0) {
                PagerSlidingTabStrip pagerSlidingTabStrip = PagerSlidingTabStrip.this;
                pagerSlidingTabStrip.scrollToChild(pagerSlidingTabStrip.pager.getCurrentItem(), 0);
            }
            ViewPager.OnPageChangeListener onPageChangeListener = PagerSlidingTabStrip.this.delegatePageListener;
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageScrollStateChanged(i);
            }
        }

        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            PagerSlidingTabStrip.this.selectedPosition = i;
            PagerSlidingTabStrip.this.updateTabStyles();
            ViewPager.OnPageChangeListener onPageChangeListener = PagerSlidingTabStrip.this.delegatePageListener;
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageSelected(i);
            }
        }
    }

    public void setIndicatorColor(int i) {
        this.indicatorColor = i;
        invalidate();
    }

    public void setIndicatorColorResource(int i) {
        this.indicatorColor = getResources().getColor(i);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorHeight(int i) {
        this.indicatorHeight = i;
        invalidate();
    }

    public int getIndicatorHeight() {
        return this.indicatorHeight;
    }

    public void setUnderlineColor(int i) {
        this.underlineColor = i;
        invalidate();
    }

    public void setUnderlineColorResource(int i) {
        this.underlineColor = getResources().getColor(i);
        invalidate();
    }

    public int getUnderlineColor() {
        return this.underlineColor;
    }

    public void setDividerColor(int i) {
        this.dividerColor = i;
        invalidate();
    }

    public void setDividerColorResource(int i) {
        this.dividerColor = getResources().getColor(i);
        invalidate();
    }

    public int getDividerColor() {
        return this.dividerColor;
    }

    public void setUnderlineHeight(int i) {
        this.underlineHeight = i;
        invalidate();
    }

    public int getUnderlineHeight() {
        return this.underlineHeight;
    }

    public void setDividerPadding(int i) {
        this.dividerPadding = i;
        invalidate();
    }

    public int getDividerPadding() {
        return this.dividerPadding;
    }

    public void setScrollOffset(int i) {
        this.scrollOffset = i;
        invalidate();
    }

    public int getScrollOffset() {
        return this.scrollOffset;
    }

    public void setCustomIndicatorWith(boolean z, int i) {
        this.isCustomIndicator = z;
        this.indicatorWith = i;
        invalidate();
    }

    public void setShouldExpand(boolean z) {
        this.shouldExpand = z;
        notifyDataSetChanged();
    }

    public boolean getShouldExpand() {
        return this.shouldExpand;
    }

    public void setAllCaps(boolean z) {
        this.textAllCaps = z;
    }

    public void setTextSize(int i) {
        this.tabTextSize = i;
        updateTabStyles();
    }

    public int getTextSize() {
        return this.tabTextSize;
    }

    public void setTextColor(int i) {
        this.tabTextColor = i;
        updateTabStyles();
    }

    public void setTextColorResource(int i) {
        this.tabTextColor = getResources().getColor(i);
        updateTabStyles();
    }

    public int getTextColor() {
        return this.tabTextColor;
    }

    public void setSelectedTextSize(int i) {
        this.selectedTabTextSize = i;
        updateTabStyles();
    }

    public int getSelectedTabTextSize() {
        return this.selectedTabTextSize;
    }

    public void setSelectedTextColor(int i) {
        this.selectedTabTextColor = i;
        updateTabStyles();
    }

    public void setSelectedTabBackground(int i) {
        this.selectedTabBackgroundRes = i;
        updateTabStyles();
    }

    public void setSelectedTextColorResource(int i) {
        this.selectedTabTextColor = getResources().getColor(i);
        updateTabStyles();
    }

    public int getSelectedTextColor() {
        return this.selectedTabTextColor;
    }

    public int getSelectedTabBackgroundRes() {
        return this.selectedTabBackgroundRes;
    }

    public void setTypeface(Typeface typeface, int i) {
        updateTabStyles();
    }

    public void setSelectedTypeface(Typeface typeface, int i) {
        updateTabStyles();
    }

    public void setTabBackground(int i) {
        this.tabBackgroundResId = i;
        updateTabStyles();
    }

    public int getTabBackground() {
        return this.tabBackgroundResId;
    }

    public void setTabPaddingLeftRight(int i) {
        this.tabPadding = i;
        updateTabStyles();
    }

    public int getTabPaddingLeftRight() {
        return this.tabPadding;
    }

    public void setIsShowAllTabs(boolean z) {
        this.isShowAllTabs = z;
        notifyDataSetChanged();
    }

    public boolean getIsShowAllTabs() {
        return this.isShowAllTabs;
    }

    public void setBoldText(boolean z) {
        this.isBoldText = z;
        updateTabStyles();
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentPosition = this.currentPosition;
        return savedState;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.one.tomato.widget.PagerSlidingTabStrip.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            /* renamed from: createFromParcel */
            public SavedState mo6447createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            /* renamed from: newArray */
            public SavedState[] mo6448newArray(int i) {
                return new SavedState[i];
            }
        };
        int currentPosition;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.currentPosition = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.currentPosition);
        }
    }
}
