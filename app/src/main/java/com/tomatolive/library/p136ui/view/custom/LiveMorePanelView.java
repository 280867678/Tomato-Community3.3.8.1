package com.tomatolive.library.p136ui.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
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
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.p136ui.view.dialog.LiveMoreDialog;
import com.tomatolive.library.p136ui.view.gift.giftpanel.ViewPagerAdapter;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.custom.LiveMorePanelView */
/* loaded from: classes3.dex */
public class LiveMorePanelView {
    private Context mContext;
    private List<MenuEntity> mDatas;
    private LinearLayout mDotsLayout;
    private LayoutInflater mInflater;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewpager;
    private LiveMoreDialog.OnMenuItemClickListener onItemClickListener;
    private int columns = 4;
    private int rows = 2;
    private List<View> mViews = new ArrayList();
    private boolean isScrolling = false;
    private int lastValue = -1;
    private int mViewpagerPosition = 0;

    public LiveMorePanelView(Context context, ViewPager viewPager, LinearLayout linearLayout, List<MenuEntity> list) {
        this.mContext = context;
        this.mDatas = list;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mViewpager = viewPager;
        this.mDotsLayout = linearLayout;
        initPortraitGift();
    }

    public void updateDataList(List<MenuEntity> list) {
        this.mDatas = list;
        this.mDotsLayout.removeAllViews();
        this.mViewpager.removeAllViews();
        initPortraitGift();
    }

    public void setOnItemClickListener(LiveMoreDialog.OnMenuItemClickListener onMenuItemClickListener) {
        this.onItemClickListener = onMenuItemClickListener;
    }

    public void notifyDataAdapter(final boolean z) {
        List<View> list = this.mViews;
        if (list == null || list.isEmpty()) {
            return;
        }
        Observable.fromIterable(this.mViews).flatMap(new Function<View, ObservableSource<RecyclerView.Adapter>>() { // from class: com.tomatolive.library.ui.view.custom.LiveMorePanelView.2
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public ObservableSource<RecyclerView.Adapter> mo6755apply(View view) throws Exception {
                RecyclerView.Adapter adapter = ((RecyclerView) view).getAdapter();
                if (adapter instanceof MoreAdapter) {
                    for (MenuEntity menuEntity : ((MoreAdapter) adapter).getData()) {
                        if (menuEntity.getMenuType() == 273) {
                            menuEntity.isSelected = z;
                        }
                    }
                }
                return Observable.just(adapter);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RecyclerView.Adapter>() { // from class: com.tomatolive.library.ui.view.custom.LiveMorePanelView.1
            @Override // io.reactivex.functions.Consumer
            public void accept(RecyclerView.Adapter adapter) throws Exception {
                adapter.notifyDataSetChanged();
            }
        });
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

    private View viewPagerItem(Context context, int i, List<MenuEntity> list, int i2, int i3) {
        RecyclerView recyclerView = (RecyclerView) ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R$layout.fq_face_gridview, (ViewGroup) null).findViewById(R$id.chart_face_gv);
        recyclerView.setLayoutManager(new GridLayoutManager(context, i2));
        int i4 = i2 * i3;
        int i5 = i * i4;
        int i6 = i4 * (i + 1);
        if (i6 > list.size()) {
            i6 = list.size();
        }
        MoreAdapter moreAdapter = new MoreAdapter(new ArrayList(list.subList(i5, i6)));
        recyclerView.setAdapter(moreAdapter);
        moreAdapter.bindToRecyclerView(recyclerView);
        moreAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.custom.LiveMorePanelView.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i7) {
                MenuEntity menuEntity = (MenuEntity) baseQuickAdapter.getItem(i7);
                if (LiveMorePanelView.this.onItemClickListener != null) {
                    LiveMorePanelView.this.onItemClickListener.onItemClick(i7, menuEntity);
                }
            }
        });
        return recyclerView;
    }

    private int getPagerCount(int i, int i2, int i3) {
        int i4 = i2 * i3;
        int i5 = i % i4;
        int i6 = i / i4;
        return i5 == 0 ? i6 : i6 + 1;
    }

    public void onDestroy() {
        List<MenuEntity> list = this.mDatas;
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
    /* renamed from: com.tomatolive.library.ui.view.custom.LiveMorePanelView$PageChangeListener */
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
            if (LiveMorePanelView.this.isScrolling) {
                if (LiveMorePanelView.this.lastValue <= i2) {
                    if (LiveMorePanelView.this.lastValue >= i2) {
                        if (LiveMorePanelView.this.lastValue == i2) {
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
            LiveMorePanelView.this.lastValue = i2;
        }

        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            LiveMorePanelView.this.mViewpagerPosition = i;
            for (int i2 = 0; i2 < LiveMorePanelView.this.mDotsLayout.getChildCount(); i2++) {
                LiveMorePanelView.this.mDotsLayout.getChildAt(i2).setSelected(false);
            }
            LiveMorePanelView.this.mDotsLayout.getChildAt(i).setSelected(true);
        }

        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            LiveMorePanelView liveMorePanelView = LiveMorePanelView.this;
            boolean z = true;
            if (i != 1) {
                z = false;
            }
            liveMorePanelView.isScrolling = z;
            if (i == 2) {
                this.left = false;
                this.right = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.custom.LiveMorePanelView$MoreAdapter */
    /* loaded from: classes3.dex */
    public class MoreAdapter extends BaseQuickAdapter<MenuEntity, BaseViewHolder> {
        public MoreAdapter(@Nullable List<MenuEntity> list) {
            super(R$layout.fq_item_grid_live_more_view, list);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, MenuEntity menuEntity) {
            baseViewHolder.setText(R$id.tv_name, menuEntity.getMenuTitle());
            ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_img);
            ImageView imageView2 = (ImageView) baseViewHolder.getView(R$id.iv_dot);
            int i = 0;
            if (menuEntity.getMenuType() == 274) {
                if (!DBUtils.isUnReadBoolean()) {
                    i = 4;
                }
                imageView2.setVisibility(i);
            } else if (menuEntity.getMenuType() == 275) {
                if (!SysConfigInfoManager.getInstance().isEnableQMInteractRedDot()) {
                    i = 4;
                }
                imageView2.setVisibility(i);
            } else {
                imageView2.setVisibility(4);
            }
            if (menuEntity.getMenuType() == 273) {
                imageView.setImageResource(menuEntity.isSelected ? R$drawable.fq_ic_live_more_translate_selected : R$drawable.fq_ic_live_more_translate_def);
            } else {
                imageView.setImageResource(menuEntity.getMenuIcon());
            }
        }
    }
}
