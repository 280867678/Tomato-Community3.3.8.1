package com.tomatolive.library.p136ui.view.gift.giftpanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.p002v4.view.ViewPager;
import android.support.p005v7.widget.DefaultItemAnimator;
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
import com.tomatolive.library.model.BackpackItemEntity;
import com.tomatolive.library.p136ui.view.divider.RVDividerGiftAdapter;
import com.tomatolive.library.utils.NumberUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.tomatolive.library.ui.view.gift.giftpanel.BackpackPanelControl */
/* loaded from: classes3.dex */
public class BackpackPanelControl {
    private GiftClickListener giftClickListener;
    private Context mContext;
    private LinearLayout mDotsLayout;
    private LayoutInflater mInflater;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewpager;
    private int columns = 4;
    private int rows = 2;
    private List<View> mViews = new ArrayList();
    private List<BackpackItemEntity> mDatas = new ArrayList();
    private boolean isScrolling = false;
    private int lastValue = -1;
    private boolean isClearStatus = true;
    private int mViewpagerPosition = 0;
    private int mAdapterSelectPosition = -1;

    /* renamed from: com.tomatolive.library.ui.view.gift.giftpanel.BackpackPanelControl$GiftClickListener */
    /* loaded from: classes3.dex */
    public interface GiftClickListener {
        void onClick(int[] iArr, BackpackItemEntity backpackItemEntity);
    }

    public BackpackPanelControl(Context context, ViewPager viewPager, LinearLayout linearLayout) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mViewpager = viewPager;
        this.mDotsLayout = linearLayout;
        initPortraitBackpack();
    }

    public void updateBackpackList(List<BackpackItemEntity> list) {
        this.mDatas = formatBackpackList(list);
        this.mDotsLayout.removeAllViews();
        this.mViewpager.removeAllViews();
        initPortraitBackpack();
    }

    public boolean isCountEnabled() {
        List<BackpackItemEntity> list = this.mDatas;
        return list != null && list.size() > 0;
    }

    private void initPortraitBackpack() {
        int pagerCount = getPagerCount(this.mDatas.size(), this.columns, this.rows);
        if (this.mViews == null) {
            this.mViews = new ArrayList();
        }
        this.mViews.clear();
        int i = 8;
        if (pagerCount != 0) {
            for (int i2 = 0; i2 < pagerCount; i2++) {
                this.mViews.add(viewPagerItem(this.mContext, i2, this.mDatas, this.columns, this.rows));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ConvertUtils.dp2px(6.0f), ConvertUtils.dp2px(6.0f));
                layoutParams.setMargins(8, 0, 8, 0);
                if (pagerCount > 1) {
                    this.mDotsLayout.addView(dotsItem(i2), layoutParams);
                }
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
        if (pagerCount > 1) {
            i = 0;
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

    private View viewPagerItem(Context context, int i, List<BackpackItemEntity> list, int i2, int i3) {
        RecyclerView.ItemDecoration rVDividerGiftAdapter = new RVDividerGiftAdapter(context, R$color.fq_view_divider_color66);
        RecyclerView recyclerView = (RecyclerView) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R$layout.fq_face_gridview, (ViewGroup) null).findViewById(R$id.chart_face_gv);
        recyclerView.setLayoutManager(new GridLayoutManager(context, i2));
        recyclerView.addItemDecoration(rVDividerGiftAdapter);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        int i4 = i2 * i3;
        int i5 = i * i4;
        int i6 = i4 * (i + 1);
        if (i6 > list.size()) {
            i6 = list.size();
        }
        final BackpackAdapter backpackAdapter = new BackpackAdapter(new ArrayList(list.subList(i5, i6)));
        recyclerView.setAdapter(backpackAdapter);
        backpackAdapter.bindToRecyclerView(recyclerView);
        backpackAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.gift.giftpanel.-$$Lambda$BackpackPanelControl$gy6twMNkgxzekkALPKcD1W677yY
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i7) {
                BackpackPanelControl.this.lambda$viewPagerItem$0$BackpackPanelControl(backpackAdapter, baseQuickAdapter, view, i7);
            }
        });
        return recyclerView;
    }

    public /* synthetic */ void lambda$viewPagerItem$0$BackpackPanelControl(BackpackAdapter backpackAdapter, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        BackpackItemEntity backpackItemEntity = (BackpackItemEntity) baseQuickAdapter.getItem(i);
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        if (backpackItemEntity != null && !backpackItemEntity.isStayTuned) {
            clearSelectedPosition();
            this.mAdapterSelectPosition = i;
            backpackAdapter.setSelectedPosition(i);
            GiftClickListener giftClickListener = this.giftClickListener;
            if (giftClickListener == null) {
                return;
            }
            giftClickListener.onClick(iArr, backpackItemEntity);
        }
    }

    private int getPagerCount(int i, int i2, int i3) {
        int i4 = i2 * i3;
        int i5 = i % i4;
        int i6 = i / i4;
        return i5 == 0 ? i6 : i6 + 1;
    }

    private List<BackpackItemEntity> formatBackpackList(List<BackpackItemEntity> list) {
        int size;
        ArrayList arrayList = new ArrayList(list);
        if (arrayList.size() != 0 && (size = arrayList.size() % 8) > 0) {
            int i = 8 - size;
            for (int i2 = 0; i2 < i; i2++) {
                BackpackItemEntity backpackItemEntity = new BackpackItemEntity();
                backpackItemEntity.isStayTuned = true;
                arrayList.add(backpackItemEntity);
            }
            return arrayList;
        }
        return arrayList;
    }

    private void clearSelectedPosition() {
        for (View view : this.mViews) {
            if (view instanceof RecyclerView) {
                RecyclerView.Adapter adapter = ((RecyclerView) view).getAdapter();
                if ((adapter instanceof BackpackAdapter) && this.isClearStatus) {
                    ((BackpackAdapter) adapter).clearSelectedPosition();
                }
            }
        }
    }

    public boolean updateSelectedItemCount() {
        int i = 0;
        if (this.mViews.size() == 0) {
            return false;
        }
        View view = this.mViews.get(this.mViewpagerPosition);
        if (view instanceof RecyclerView) {
            RecyclerView.Adapter adapter = ((RecyclerView) view).getAdapter();
            if (adapter instanceof BackpackAdapter) {
                BackpackAdapter backpackAdapter = (BackpackAdapter) adapter;
                int i2 = this.mAdapterSelectPosition;
                if (i2 >= 0) {
                    BackpackItemEntity item = backpackAdapter.getItem(i2);
                    if (!item.isNobilityTrumpetBoolean() && !item.isPropFragmentBoolean()) {
                        AtomicInteger atomicInteger = new AtomicInteger(NumberUtils.string2int(item.count));
                        atomicInteger.decrementAndGet();
                        if (atomicInteger.get() == 0) {
                            backpackAdapter.setSelectedPosition(-1);
                            backpackAdapter.remove(this.mAdapterSelectPosition);
                            this.mAdapterSelectPosition = -1;
                            GiftClickListener giftClickListener = this.giftClickListener;
                            if (giftClickListener != null) {
                                giftClickListener.onClick(null, null);
                            }
                            BackpackItemEntity backpackItemEntity = new BackpackItemEntity();
                            backpackItemEntity.isStayTuned = true;
                            backpackAdapter.addData(7, (int) backpackItemEntity);
                            if (isClearSelectedView(backpackAdapter)) {
                                this.mViews.get(this.mViewpagerPosition).setVisibility(8);
                                this.mViews.remove(this.mViewpagerPosition);
                                this.mViewPagerAdapter.notifyDataSetChanged();
                                LinearLayout linearLayout = this.mDotsLayout;
                                if (this.mViews.size() <= 1) {
                                    i = 8;
                                }
                                linearLayout.setVisibility(i);
                            }
                            return true;
                        }
                        item.count = String.valueOf(atomicInteger.get());
                        backpackAdapter.setData(this.mAdapterSelectPosition, item);
                    }
                }
            }
        }
        return false;
    }

    private boolean isClearSelectedView(BackpackAdapter backpackAdapter) {
        ArrayList arrayList = new ArrayList();
        for (BackpackItemEntity backpackItemEntity : backpackAdapter.getData()) {
            boolean z = backpackItemEntity.isStayTuned;
            if (z) {
                arrayList.add(Boolean.valueOf(z));
            }
        }
        return arrayList.size() == 8;
    }

    public boolean isEmpty() {
        return this.mViews.size() == 0;
    }

    public void setGiftClickListener(GiftClickListener giftClickListener) {
        this.giftClickListener = giftClickListener;
    }

    public void onDestroy() {
        setGiftClickListener(null);
        List<BackpackItemEntity> list = this.mDatas;
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
    /* renamed from: com.tomatolive.library.ui.view.gift.giftpanel.BackpackPanelControl$PageChangeListener */
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
            if (BackpackPanelControl.this.isScrolling) {
                if (BackpackPanelControl.this.lastValue <= i2) {
                    if (BackpackPanelControl.this.lastValue >= i2) {
                        if (BackpackPanelControl.this.lastValue == i2) {
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
            BackpackPanelControl.this.lastValue = i2;
        }

        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            BackpackPanelControl.this.mViewpagerPosition = i;
            for (int i2 = 0; i2 < BackpackPanelControl.this.mDotsLayout.getChildCount(); i2++) {
                BackpackPanelControl.this.mDotsLayout.getChildAt(i2).setSelected(false);
            }
            BackpackPanelControl.this.mDotsLayout.getChildAt(i).setSelected(true);
        }

        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            BackpackPanelControl backpackPanelControl = BackpackPanelControl.this;
            boolean z = true;
            if (i != 1) {
                z = false;
            }
            backpackPanelControl.isScrolling = z;
            if (i == 2) {
                this.left = false;
                this.right = false;
            }
        }
    }
}
