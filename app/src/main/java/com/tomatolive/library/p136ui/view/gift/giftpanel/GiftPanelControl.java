package com.tomatolive.library.p136ui.view.gift.giftpanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.p002v4.view.ViewPager;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.p136ui.view.divider.RVDividerGiftAdapter;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.gift.giftpanel.GiftPanelControl */
/* loaded from: classes3.dex */
public class GiftPanelControl {
    private GiftClickListener giftClickListener;
    private Context mContext;
    private List<GiftDownloadItemEntity> mDatas;
    private LinearLayout mDotsLayout;
    private LayoutInflater mInflater;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewpager;
    private int columns = 4;
    private int rows = 2;
    private List<View> mViews = new ArrayList();
    private boolean isScrolling = false;
    private int lastValue = -1;
    private boolean isClearStatus = true;
    private int mViewpagerPosition = 0;
    private int mAdapterSelectPosition = -1;

    /* renamed from: com.tomatolive.library.ui.view.gift.giftpanel.GiftPanelControl$GiftClickListener */
    /* loaded from: classes3.dex */
    public interface GiftClickListener {
        void onClick(int[] iArr, GiftDownloadItemEntity giftDownloadItemEntity);
    }

    public GiftPanelControl(Context context, ViewPager viewPager, LinearLayout linearLayout, List<GiftDownloadItemEntity> list) {
        this.mContext = context;
        this.mDatas = list;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mViewpager = viewPager;
        this.mDotsLayout = linearLayout;
        initPortraitGift();
    }

    public void updateGiftList(List<GiftDownloadItemEntity> list) {
        this.mDatas = list;
        this.mDotsLayout.removeAllViews();
        this.mViewpager.removeAllViews();
        initPortraitGift();
    }

    private void initPortraitGift() {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList();
        }
        int pagerCount = getPagerCount(this.mDatas.size(), this.columns, this.rows);
        if (this.mViews == null) {
            this.mViews = new ArrayList();
        }
        this.mViews.clear();
        int i = 0;
        for (int i2 = 0; i2 < pagerCount; i2++) {
            this.mViews.add(viewPagerItem(this.mContext, i2, this.mDatas, this.columns, this.rows));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(6.0f));
            layoutParams.setMargins(8, 0, 8, 0);
            if (pagerCount > 1) {
                this.mDotsLayout.addView(dotsItem(i2), layoutParams);
            }
        }
        this.mViewPagerAdapter = new ViewPagerAdapter(this.mViews);
        this.mViewpager.setAdapter(this.mViewPagerAdapter);
        this.mViewpager.addOnPageChangeListener(new PageChangeListener());
        this.mViewpager.setCurrentItem(this.mViewpagerPosition);
        if (pagerCount > 1) {
            this.mDotsLayout.getChildAt(this.mViewpagerPosition).setSelected(true);
        }
        LinearLayout linearLayout = this.mDotsLayout;
        if (pagerCount <= 1) {
            i = 8;
        }
        linearLayout.setVisibility(i);
    }

    @SuppressLint({"InflateParams"})
    private ImageView dotsItem(int i) {
        ImageView imageView = (ImageView) this.mInflater.inflate(R$layout.fq_dot_image, (ViewGroup) null).findViewById(R$id.face_dot);
        imageView.setId(i);
        return imageView;
    }

    public void isClearStatus(boolean z) {
        this.isClearStatus = z;
    }

    private View viewPagerItem(Context context, int i, List<GiftDownloadItemEntity> list, int i2, int i3) {
        RecyclerView.ItemDecoration rVDividerGiftAdapter = new RVDividerGiftAdapter(context, R$color.fq_view_divider_color66);
        RecyclerView recyclerView = (RecyclerView) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R$layout.fq_face_gridview, (ViewGroup) null).findViewById(R$id.chart_face_gv);
        recyclerView.setLayoutManager(new GridLayoutManager(context, i2));
        recyclerView.addItemDecoration(rVDividerGiftAdapter);
        int i4 = i2 * i3;
        int i5 = i * i4;
        int i6 = i4 * (i + 1);
        if (i6 > list.size()) {
            i6 = list.size();
        }
        final GiftAdapter giftAdapter = new GiftAdapter(new ArrayList(list.subList(i5, i6)));
        recyclerView.setAdapter(giftAdapter);
        giftAdapter.bindToRecyclerView(recyclerView);
        giftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.gift.giftpanel.-$$Lambda$GiftPanelControl$yVR1s76AcIar06ldwmyIClydvok
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i7) {
                GiftPanelControl.this.lambda$viewPagerItem$0$GiftPanelControl(giftAdapter, baseQuickAdapter, view, i7);
            }
        });
        return recyclerView;
    }

    public /* synthetic */ void lambda$viewPagerItem$0$GiftPanelControl(GiftAdapter giftAdapter, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        GiftDownloadItemEntity giftDownloadItemEntity = (GiftDownloadItemEntity) baseQuickAdapter.getItem(i);
        if (giftDownloadItemEntity != null && !giftDownloadItemEntity.isStayTuned) {
            clearSelectedPosition();
            this.mAdapterSelectPosition = i;
            giftAdapter.setSelectedPosition(i);
            GiftClickListener giftClickListener = this.giftClickListener;
            if (giftClickListener == null) {
                return;
            }
            giftClickListener.onClick(iArr, giftDownloadItemEntity);
        }
    }

    public int getmAdapterSelectPosition() {
        return this.mAdapterSelectPosition;
    }

    private int getPagerCount(int i, int i2, int i3) {
        int i4 = i2 * i3;
        int i5 = i % i4;
        int i6 = i / i4;
        return i5 == 0 ? i6 : i6 + 1;
    }

    public void setGiftClickListener(GiftClickListener giftClickListener) {
        this.giftClickListener = giftClickListener;
    }

    private void clearSelectedPosition() {
        for (View view : this.mViews) {
            if (view instanceof RecyclerView) {
                RecyclerView.Adapter adapter = ((RecyclerView) view).getAdapter();
                if ((adapter instanceof GiftAdapter) && this.isClearStatus) {
                    ((GiftAdapter) adapter).clearSelectedPosition();
                }
            }
        }
    }

    public void onDestroy() {
        setGiftClickListener(null);
        List<GiftDownloadItemEntity> list = this.mDatas;
        if (list != null) {
            list.clear();
        }
        List<View> list2 = this.mViews;
        if (list2 != null) {
            list2.clear();
        }
        ViewPagerAdapter viewPagerAdapter = this.mViewPagerAdapter;
        if (viewPagerAdapter != null) {
            viewPagerAdapter.notifyDataSetChanged();
        }
        if (this.mContext != null) {
            this.mContext = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.gift.giftpanel.GiftPanelControl$PageChangeListener */
    /* loaded from: classes3.dex */
    public class PageChangeListener implements ViewPager.OnPageChangeListener {
        private boolean left;
        private boolean right;

        private PageChangeListener() {
            this.left = false;
            this.right = false;
        }

        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
            if (GiftPanelControl.this.isScrolling) {
                if (GiftPanelControl.this.lastValue <= i2) {
                    if (GiftPanelControl.this.lastValue >= i2) {
                        if (GiftPanelControl.this.lastValue == i2) {
                            this.left = false;
                            this.right = false;
                        }
                    } else {
                        this.right = false;
                        this.left = true;
                    }
                } else {
                    this.right = true;
                    this.left = false;
                }
            }
            GiftPanelControl.this.lastValue = i2;
        }

        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            GiftPanelControl.this.mViewpagerPosition = i;
            for (int i2 = 0; i2 < GiftPanelControl.this.mDotsLayout.getChildCount(); i2++) {
                GiftPanelControl.this.mDotsLayout.getChildAt(i2).setSelected(false);
            }
            GiftPanelControl.this.mDotsLayout.getChildAt(i).setSelected(true);
        }

        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            GiftPanelControl giftPanelControl = GiftPanelControl.this;
            boolean z = true;
            if (i != 1) {
                z = false;
            }
            giftPanelControl.isScrolling = z;
            if (i == 2) {
                this.left = false;
                this.right = false;
            }
        }
    }
}
