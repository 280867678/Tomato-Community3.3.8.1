package net.lucode.hackware.magicindicator.buildins.commonnavigator;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import net.lucode.hackware.magicindicator.NavigatorHelper;
import net.lucode.hackware.magicindicator.R$id;
import net.lucode.hackware.magicindicator.R$layout;
import net.lucode.hackware.magicindicator.abs.IPagerNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData;

/* loaded from: classes4.dex */
public class CommonNavigator extends FrameLayout implements IPagerNavigator, NavigatorHelper.OnNavigatorScrollListener {
    private CommonNavigatorAdapter mAdapter;
    private boolean mAdjustMode;
    private boolean mEnablePivotScroll;
    private IPagerIndicator mIndicator;
    private LinearLayout mIndicatorContainer;
    private boolean mIndicatorOnTop;
    private int mLeftPadding;
    private int mRightPadding;
    private HorizontalScrollView mScrollView;
    private LinearLayout mTitleContainer;
    private float mScrollPivotX = 0.5f;
    private boolean mSmoothScroll = true;
    private boolean mFollowTouch = true;
    private boolean mReselectWhenLayout = true;
    private List<PositionData> mPositionDataList = new ArrayList();
    private DataSetObserver mObserver = new DataSetObserver() { // from class: net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator.1
        @Override // android.database.DataSetObserver
        public void onInvalidated() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            CommonNavigator.this.mNavigatorHelper.setTotalCount(CommonNavigator.this.mAdapter.getCount());
            CommonNavigator.this.init();
        }
    };
    private NavigatorHelper mNavigatorHelper = new NavigatorHelper();

    @Override // net.lucode.hackware.magicindicator.abs.IPagerNavigator
    public void onDetachFromMagicIndicator() {
    }

    public CommonNavigator(Context context) {
        super(context);
        this.mNavigatorHelper.setNavigatorScrollListener(this);
    }

    public void setAdjustMode(boolean z) {
        this.mAdjustMode = z;
    }

    public CommonNavigatorAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(CommonNavigatorAdapter commonNavigatorAdapter) {
        CommonNavigatorAdapter commonNavigatorAdapter2 = this.mAdapter;
        if (commonNavigatorAdapter2 == commonNavigatorAdapter) {
            return;
        }
        if (commonNavigatorAdapter2 != null) {
            commonNavigatorAdapter2.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = commonNavigatorAdapter;
        CommonNavigatorAdapter commonNavigatorAdapter3 = this.mAdapter;
        if (commonNavigatorAdapter3 != null) {
            commonNavigatorAdapter3.registerDataSetObserver(this.mObserver);
            this.mNavigatorHelper.setTotalCount(this.mAdapter.getCount());
            if (this.mTitleContainer == null) {
                return;
            }
            this.mAdapter.notifyDataSetChanged();
            return;
        }
        this.mNavigatorHelper.setTotalCount(0);
        init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        View inflate;
        removeAllViews();
        if (this.mAdjustMode) {
            inflate = LayoutInflater.from(getContext()).inflate(R$layout.pager_navigator_layout_no_scroll, this);
        } else {
            inflate = LayoutInflater.from(getContext()).inflate(R$layout.pager_navigator_layout, this);
        }
        this.mScrollView = (HorizontalScrollView) inflate.findViewById(R$id.scroll_view);
        this.mTitleContainer = (LinearLayout) inflate.findViewById(R$id.title_container);
        this.mTitleContainer.setPadding(this.mLeftPadding, 0, this.mRightPadding, 0);
        this.mIndicatorContainer = (LinearLayout) inflate.findViewById(R$id.indicator_container);
        if (this.mIndicatorOnTop) {
            this.mIndicatorContainer.getParent().bringChildToFront(this.mIndicatorContainer);
        }
        initTitlesAndIndicator();
    }

    private void initTitlesAndIndicator() {
        LinearLayout.LayoutParams layoutParams;
        int totalCount = this.mNavigatorHelper.getTotalCount();
        for (int i = 0; i < totalCount; i++) {
            IPagerTitleView titleView = this.mAdapter.getTitleView(getContext(), i);
            if (titleView instanceof View) {
                View view = (View) titleView;
                if (this.mAdjustMode) {
                    layoutParams = new LinearLayout.LayoutParams(0, -1);
                    layoutParams.weight = this.mAdapter.getTitleWeight(getContext(), i);
                } else {
                    layoutParams = new LinearLayout.LayoutParams(-2, -1);
                }
                this.mTitleContainer.addView(view, layoutParams);
            }
        }
        CommonNavigatorAdapter commonNavigatorAdapter = this.mAdapter;
        if (commonNavigatorAdapter != null) {
            this.mIndicator = commonNavigatorAdapter.getIndicator(getContext());
            if (!(this.mIndicator instanceof View)) {
                return;
            }
            this.mIndicatorContainer.addView((View) this.mIndicator, new FrameLayout.LayoutParams(-1, -1));
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mAdapter != null) {
            preparePositionData();
            IPagerIndicator iPagerIndicator = this.mIndicator;
            if (iPagerIndicator != null) {
                iPagerIndicator.onPositionDataProvide(this.mPositionDataList);
            }
            if (!this.mReselectWhenLayout || this.mNavigatorHelper.getScrollState() != 0) {
                return;
            }
            onPageSelected(this.mNavigatorHelper.getCurrentIndex());
            onPageScrolled(this.mNavigatorHelper.getCurrentIndex(), 0.0f, 0);
        }
    }

    private void preparePositionData() {
        this.mPositionDataList.clear();
        int totalCount = this.mNavigatorHelper.getTotalCount();
        for (int i = 0; i < totalCount; i++) {
            PositionData positionData = new PositionData();
            View childAt = this.mTitleContainer.getChildAt(i);
            if (childAt != null) {
                positionData.mLeft = childAt.getLeft();
                positionData.mTop = childAt.getTop();
                positionData.mRight = childAt.getRight();
                positionData.mBottom = childAt.getBottom();
                if (childAt instanceof IMeasurablePagerTitleView) {
                    IMeasurablePagerTitleView iMeasurablePagerTitleView = (IMeasurablePagerTitleView) childAt;
                    positionData.mContentLeft = iMeasurablePagerTitleView.getContentLeft();
                    positionData.mContentTop = iMeasurablePagerTitleView.getContentTop();
                    positionData.mContentRight = iMeasurablePagerTitleView.getContentRight();
                    positionData.mContentBottom = iMeasurablePagerTitleView.getContentBottom();
                } else {
                    positionData.mContentLeft = positionData.mLeft;
                    positionData.mContentTop = positionData.mTop;
                    positionData.mContentRight = positionData.mRight;
                    positionData.mContentBottom = positionData.mBottom;
                }
            }
            this.mPositionDataList.add(positionData);
        }
    }

    @Override // net.lucode.hackware.magicindicator.abs.IPagerNavigator
    public void onPageScrolled(int i, float f, int i2) {
        if (this.mAdapter != null) {
            this.mNavigatorHelper.onPageScrolled(i, f, i2);
            IPagerIndicator iPagerIndicator = this.mIndicator;
            if (iPagerIndicator != null) {
                iPagerIndicator.onPageScrolled(i, f, i2);
            }
            if (this.mScrollView == null || this.mPositionDataList.size() <= 0 || i < 0 || i >= this.mPositionDataList.size() || !this.mFollowTouch) {
                return;
            }
            int min = Math.min(this.mPositionDataList.size() - 1, i);
            int min2 = Math.min(this.mPositionDataList.size() - 1, i + 1);
            float horizontalCenter = this.mPositionDataList.get(min).horizontalCenter() - (this.mScrollView.getWidth() * this.mScrollPivotX);
            this.mScrollView.scrollTo((int) (horizontalCenter + (((this.mPositionDataList.get(min2).horizontalCenter() - (this.mScrollView.getWidth() * this.mScrollPivotX)) - horizontalCenter) * f)), 0);
        }
    }

    public float getScrollPivotX() {
        return this.mScrollPivotX;
    }

    public void setScrollPivotX(float f) {
        this.mScrollPivotX = f;
    }

    @Override // net.lucode.hackware.magicindicator.abs.IPagerNavigator
    public void onPageSelected(int i) {
        if (this.mAdapter != null) {
            this.mNavigatorHelper.onPageSelected(i);
            IPagerIndicator iPagerIndicator = this.mIndicator;
            if (iPagerIndicator == null) {
                return;
            }
            iPagerIndicator.onPageSelected(i);
        }
    }

    @Override // net.lucode.hackware.magicindicator.abs.IPagerNavigator
    public void onPageScrollStateChanged(int i) {
        if (this.mAdapter != null) {
            this.mNavigatorHelper.onPageScrollStateChanged(i);
            IPagerIndicator iPagerIndicator = this.mIndicator;
            if (iPagerIndicator == null) {
                return;
            }
            iPagerIndicator.onPageScrollStateChanged(i);
        }
    }

    @Override // net.lucode.hackware.magicindicator.abs.IPagerNavigator
    public void onAttachToMagicIndicator() {
        init();
    }

    public IPagerIndicator getPagerIndicator() {
        return this.mIndicator;
    }

    public void setEnablePivotScroll(boolean z) {
        this.mEnablePivotScroll = z;
    }

    @Override // net.lucode.hackware.magicindicator.NavigatorHelper.OnNavigatorScrollListener
    public void onEnter(int i, int i2, float f, boolean z) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return;
        }
        View childAt = linearLayout.getChildAt(i);
        if (!(childAt instanceof IPagerTitleView)) {
            return;
        }
        ((IPagerTitleView) childAt).onEnter(i, i2, f, z);
    }

    @Override // net.lucode.hackware.magicindicator.NavigatorHelper.OnNavigatorScrollListener
    public void onLeave(int i, int i2, float f, boolean z) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return;
        }
        View childAt = linearLayout.getChildAt(i);
        if (!(childAt instanceof IPagerTitleView)) {
            return;
        }
        ((IPagerTitleView) childAt).onLeave(i, i2, f, z);
    }

    public void setSmoothScroll(boolean z) {
        this.mSmoothScroll = z;
    }

    public void setFollowTouch(boolean z) {
        this.mFollowTouch = z;
    }

    public void setSkimOver(boolean z) {
        this.mNavigatorHelper.setSkimOver(z);
    }

    @Override // net.lucode.hackware.magicindicator.NavigatorHelper.OnNavigatorScrollListener
    public void onSelected(int i, int i2) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return;
        }
        View childAt = linearLayout.getChildAt(i);
        if (childAt instanceof IPagerTitleView) {
            ((IPagerTitleView) childAt).onSelected(i, i2);
        }
        if (this.mAdjustMode || this.mFollowTouch || this.mScrollView == null || this.mPositionDataList.size() <= 0) {
            return;
        }
        PositionData positionData = this.mPositionDataList.get(Math.min(this.mPositionDataList.size() - 1, i));
        if (this.mEnablePivotScroll) {
            float horizontalCenter = positionData.horizontalCenter() - (this.mScrollView.getWidth() * this.mScrollPivotX);
            if (this.mSmoothScroll) {
                this.mScrollView.smoothScrollTo((int) horizontalCenter, 0);
                return;
            } else {
                this.mScrollView.scrollTo((int) horizontalCenter, 0);
                return;
            }
        }
        int scrollX = this.mScrollView.getScrollX();
        int i3 = positionData.mLeft;
        if (scrollX > i3) {
            if (this.mSmoothScroll) {
                this.mScrollView.smoothScrollTo(i3, 0);
                return;
            } else {
                this.mScrollView.scrollTo(i3, 0);
                return;
            }
        }
        int scrollX2 = this.mScrollView.getScrollX() + getWidth();
        int i4 = positionData.mRight;
        if (scrollX2 >= i4) {
            return;
        }
        if (this.mSmoothScroll) {
            this.mScrollView.smoothScrollTo(i4 - getWidth(), 0);
        } else {
            this.mScrollView.scrollTo(i4 - getWidth(), 0);
        }
    }

    @Override // net.lucode.hackware.magicindicator.NavigatorHelper.OnNavigatorScrollListener
    public void onDeselected(int i, int i2) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return;
        }
        View childAt = linearLayout.getChildAt(i);
        if (!(childAt instanceof IPagerTitleView)) {
            return;
        }
        ((IPagerTitleView) childAt).onDeselected(i, i2);
    }

    public LinearLayout getTitleContainer() {
        return this.mTitleContainer;
    }

    public int getRightPadding() {
        return this.mRightPadding;
    }

    public void setRightPadding(int i) {
        this.mRightPadding = i;
    }

    public int getLeftPadding() {
        return this.mLeftPadding;
    }

    public void setLeftPadding(int i) {
        this.mLeftPadding = i;
    }

    public void setIndicatorOnTop(boolean z) {
        this.mIndicatorOnTop = z;
    }

    public void setReselectWhenLayout(boolean z) {
        this.mReselectWhenLayout = z;
    }
}
