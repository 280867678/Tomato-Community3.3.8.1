package com.tomatolive.library.p136ui.adapter;

import android.content.Context;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewPager;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.GiftItemEntity;
import com.tomatolive.library.model.WeekStarRankingEntity;
import com.tomatolive.library.p136ui.fragment.RankingWeekStarAnchorFragment;
import com.tomatolive.library.p136ui.view.divider.RVDividerHorizontalLinear;
import com.tomatolive.library.p136ui.view.divider.RVDividerWeekStarReward;
import com.tomatolive.library.p136ui.view.widget.WrapContentHeightViewPager;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

/* renamed from: com.tomatolive.library.ui.adapter.WeekStarRankingAdapter */
/* loaded from: classes3.dex */
public class WeekStarRankingAdapter extends BaseMultiItemQuickAdapter<WeekStarRankingEntity, BaseViewHolder> {
    public static final int TYPE_ANCHOR_REWARD = 3;
    public static final int TYPE_CONTEST = 2;
    public static final int TYPE_SHINE = 1;
    public static final int TYPE_USER_REWARD = 4;
    private CommonNavigator commonNavigator;
    private CommonNavigatorAdapter commonNavigatorAdapter;
    private FragmentManager fragmentFM;
    private OnWeekStarRankingListener listener;
    private GiftTagAdapter menuTagAdapter;
    private RVDividerWeekStarReward rewardAnchorItemDecoration;
    private RVDividerWeekStarReward rewardUserItemDecoration;
    private RVDividerHorizontalLinear shineItemDecoration;
    private List<BaseFragment> fragmentList = new ArrayList();
    private List<GiftDownloadItemEntity> labelList = new ArrayList();
    private int currentViewPagerIndex = 0;
    private BaseFragment currentViewPagerFragment = null;

    /* renamed from: com.tomatolive.library.ui.adapter.WeekStarRankingAdapter$OnWeekStarRankingListener */
    /* loaded from: classes3.dex */
    public interface OnWeekStarRankingListener {
        void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i);

        void onRuleClick();
    }

    public void setListener(OnWeekStarRankingListener onWeekStarRankingListener) {
        this.listener = onWeekStarRankingListener;
    }

    public WeekStarRankingAdapter(FragmentManager fragmentManager, List<WeekStarRankingEntity> list) {
        super(list);
        this.fragmentFM = fragmentManager;
        addItemType();
    }

    private void addItemType() {
        addItemType(1, R$layout.fq_layout_week_star_shine);
        addItemType(2, R$layout.fq_layout_week_star_contest);
        addItemType(3, R$layout.fq_layout_week_star_reward);
        addItemType(4, R$layout.fq_layout_week_star_reward);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, WeekStarRankingEntity weekStarRankingEntity) {
        int itemViewType = baseViewHolder.getItemViewType();
        if (itemViewType == 1) {
            if (weekStarRankingEntity.shineList == null) {
                return;
            }
            if (this.shineItemDecoration == null) {
                this.shineItemDecoration = new RVDividerHorizontalLinear(this.mContext, R$color.fq_colorWhite, 10.0f);
            }
            RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R$id.recycler_view_shine);
            WeekStarShineAdapter weekStarShineAdapter = new WeekStarShineAdapter(R$layout.fq_item_list_week_star_shine);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
            linearLayoutManager.setOrientation(0);
            if (recyclerView.getItemDecorationCount() > 0) {
                recyclerView.removeItemDecoration(this.shineItemDecoration);
            }
            recyclerView.addItemDecoration(this.shineItemDecoration);
            recyclerView.setAdapter(weekStarShineAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            weekStarShineAdapter.bindToRecyclerView(recyclerView);
            weekStarShineAdapter.setNewData(weekStarRankingEntity.shineList);
            weekStarShineAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.adapter.-$$Lambda$WeekStarRankingAdapter$UVnlzaSZ05E7QARJAQGKjTfD6iI
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    WeekStarRankingAdapter.this.lambda$convert$0$WeekStarRankingAdapter(baseQuickAdapter, view, i);
                }
            });
        } else if (itemViewType == 2) {
            List<GiftDownloadItemEntity> list = weekStarRankingEntity.giftLabelList;
            if (list == null) {
                return;
            }
            MagicIndicator magicIndicator = (MagicIndicator) baseViewHolder.getView(R$id.magic_indicator_contest);
            WrapContentHeightViewPager wrapContentHeightViewPager = (WrapContentHeightViewPager) baseViewHolder.getView(R$id.view_pager_contest);
            if (this.commonNavigatorAdapter == null || this.menuTagAdapter == null) {
                initViewPager(magicIndicator, wrapContentHeightViewPager);
            }
            initFragmentLabelList(list, wrapContentHeightViewPager);
            this.commonNavigatorAdapter.notifyDataSetChanged();
            this.menuTagAdapter.notifyDataSetChanged();
            wrapContentHeightViewPager.setOffscreenPageLimit(this.fragmentList.size());
            wrapContentHeightViewPager.setCurrentItem(this.currentViewPagerIndex, false);
            baseViewHolder.getView(R$id.tv_week_star_rule_tip).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.adapter.-$$Lambda$WeekStarRankingAdapter$nNdPx7unpvENrR2DICdnAkaqCJs
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    WeekStarRankingAdapter.this.lambda$convert$1$WeekStarRankingAdapter(view);
                }
            });
        } else if (itemViewType == 3) {
            baseViewHolder.setText(R$id.tv_reward_title, R$string.fq_week_star_anchor_reward);
            if (weekStarRankingEntity.anchorRewardList == null) {
                return;
            }
            if (this.rewardAnchorItemDecoration == null) {
                this.rewardAnchorItemDecoration = new RVDividerWeekStarReward(this.mContext, R$color.fq_colorWhite);
            }
            RecyclerView recyclerView2 = (RecyclerView) baseViewHolder.getView(R$id.recycler_view_reward);
            WeekStarRewardAdapter weekStarRewardAdapter = new WeekStarRewardAdapter(R$layout.fq_item_list_week_star_reward);
            recyclerView2.setLayoutManager(new GridLayoutManager(this.mContext, 2));
            recyclerView2.setHasFixedSize(true);
            if (recyclerView2.getItemDecorationCount() > 0) {
                recyclerView2.removeItemDecoration(this.rewardAnchorItemDecoration);
            }
            recyclerView2.addItemDecoration(this.rewardAnchorItemDecoration);
            recyclerView2.setAdapter(weekStarRewardAdapter);
            weekStarRewardAdapter.bindToRecyclerView(recyclerView2);
            weekStarRewardAdapter.setNewData(weekStarRankingEntity.anchorRewardList);
        } else if (itemViewType != 4) {
        } else {
            baseViewHolder.setText(R$id.tv_reward_title, R$string.fq_week_star_user_reward);
            if (weekStarRankingEntity.userRewardList == null) {
                return;
            }
            if (this.rewardUserItemDecoration == null) {
                this.rewardUserItemDecoration = new RVDividerWeekStarReward(this.mContext, R$color.fq_colorWhite);
            }
            RecyclerView recyclerView3 = (RecyclerView) baseViewHolder.getView(R$id.recycler_view_reward);
            WeekStarRewardAdapter weekStarRewardAdapter2 = new WeekStarRewardAdapter(R$layout.fq_item_list_week_star_reward);
            recyclerView3.setLayoutManager(new GridLayoutManager(this.mContext, 2));
            recyclerView3.setHasFixedSize(true);
            if (recyclerView3.getItemDecorationCount() > 0) {
                recyclerView3.removeItemDecoration(this.rewardUserItemDecoration);
            }
            recyclerView3.addItemDecoration(this.rewardUserItemDecoration);
            recyclerView3.setAdapter(weekStarRewardAdapter2);
            weekStarRewardAdapter2.bindToRecyclerView(recyclerView3);
            weekStarRewardAdapter2.setNewData(weekStarRankingEntity.userRewardList);
        }
    }

    public /* synthetic */ void lambda$convert$0$WeekStarRankingAdapter(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        OnWeekStarRankingListener onWeekStarRankingListener = this.listener;
        if (onWeekStarRankingListener != null) {
            onWeekStarRankingListener.onItemClick(baseQuickAdapter, view, i);
        }
    }

    public /* synthetic */ void lambda$convert$1$WeekStarRankingAdapter(View view) {
        OnWeekStarRankingListener onWeekStarRankingListener = this.listener;
        if (onWeekStarRankingListener != null) {
            onWeekStarRankingListener.onRuleClick();
        }
    }

    public String getCurrentViewPagerMarkId() {
        List<GiftDownloadItemEntity> list = this.labelList;
        if (list == null || list.isEmpty()) {
            return null;
        }
        return this.labelList.get(this.currentViewPagerIndex).markId;
    }

    private void initViewPager(final MagicIndicator magicIndicator, final WrapContentHeightViewPager wrapContentHeightViewPager) {
        this.menuTagAdapter = new GiftTagAdapter(this.fragmentList, this.labelList, this.fragmentFM);
        this.commonNavigator = new CommonNavigator(this.mContext);
        this.commonNavigatorAdapter = new CommonNavigatorAdapter() { // from class: com.tomatolive.library.ui.adapter.WeekStarRankingAdapter.1
            @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

            @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public int getCount() {
                if (WeekStarRankingAdapter.this.fragmentList == null) {
                    return 0;
                }
                return WeekStarRankingAdapter.this.fragmentList.size();
            }

            @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
            public IPagerTitleView getTitleView(Context context, final int i) {
                GiftDownloadItemEntity giftDownloadItemEntity = (GiftDownloadItemEntity) WeekStarRankingAdapter.this.labelList.get(i);
                if (giftDownloadItemEntity == null) {
                    return null;
                }
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                final View inflate = LayoutInflater.from(context).inflate(R$layout.fq_layout_gift_tab_label_view, (ViewGroup) null);
                final TextView textView = (TextView) inflate.findViewById(R$id.tv_gift_name);
                textView.setText(StringUtils.formatStrLen(giftDownloadItemEntity.name, 3));
                GlideUtils.loadImage(((BaseQuickAdapter) WeekStarRankingAdapter.this).mContext, (ImageView) inflate.findViewById(R$id.iv_gift_img), giftDownloadItemEntity.imgurl, R$drawable.fq_ic_gift_default);
                commonPagerTitleView.setContentView(inflate);
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() { // from class: com.tomatolive.library.ui.adapter.WeekStarRankingAdapter.1.1
                    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                    public void onEnter(int i2, int i3, float f, boolean z) {
                    }

                    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                    public void onLeave(int i2, int i3, float f, boolean z) {
                    }

                    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                    public void onSelected(int i2, int i3) {
                        textView.setTextColor(ContextCompat.getColor(((BaseQuickAdapter) WeekStarRankingAdapter.this).mContext, R$color.fq_tab_menu_text_select_color));
                        inflate.setBackgroundColor(ContextCompat.getColor(((BaseQuickAdapter) WeekStarRankingAdapter.this).mContext, R$color.fq_week_star_gift_tab_selected));
                    }

                    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView.OnPagerTitleChangeListener
                    public void onDeselected(int i2, int i3) {
                        textView.setTextColor(ContextCompat.getColor(((BaseQuickAdapter) WeekStarRankingAdapter.this).mContext, R$color.fq_text_black));
                        inflate.setBackgroundColor(ContextCompat.getColor(((BaseQuickAdapter) WeekStarRankingAdapter.this).mContext, R$color.fq_week_star_gift_tab_normal));
                    }
                });
                commonPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.adapter.WeekStarRankingAdapter.1.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        WeekStarRankingAdapter.this.currentViewPagerIndex = i;
                        wrapContentHeightViewPager.setCurrentItem(i, false);
                    }
                });
                return commonPagerTitleView;
            }
        };
        this.commonNavigator.setAdapter(this.commonNavigatorAdapter);
        magicIndicator.setNavigator(this.commonNavigator);
        wrapContentHeightViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.tomatolive.library.ui.adapter.WeekStarRankingAdapter.2
            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
                magicIndicator.onPageScrolled(i, f, i2);
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                WeekStarRankingAdapter.this.currentViewPagerIndex = i;
                magicIndicator.onPageSelected(i);
                wrapContentHeightViewPager.resetHeight(i);
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
                magicIndicator.onPageScrollStateChanged(i);
            }
        });
        wrapContentHeightViewPager.setAdapter(this.menuTagAdapter);
        wrapContentHeightViewPager.resetHeight(0);
    }

    private void initFragmentLabelList(List<GiftDownloadItemEntity> list, WrapContentHeightViewPager wrapContentHeightViewPager) {
        List<BaseFragment> list2 = this.fragmentList;
        if (list2 != null) {
            list2.clear();
        }
        List<GiftDownloadItemEntity> list3 = this.labelList;
        if (list3 != null) {
            list3.clear();
        }
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            GiftDownloadItemEntity giftDownloadItemEntity = list.get(i);
            addFragment(RankingWeekStarAnchorFragment.newInstance(giftDownloadItemEntity.markId, false, i, wrapContentHeightViewPager), giftDownloadItemEntity.name, giftDownloadItemEntity.imgurl, giftDownloadItemEntity.markId);
        }
    }

    private void addFragment(BaseFragment baseFragment, String str, String str2, String str3) {
        if (this.fragmentList == null) {
            this.fragmentList = new ArrayList();
        }
        if (this.labelList == null) {
            this.labelList = new ArrayList();
        }
        if (baseFragment == null || baseFragment.isAdded()) {
            return;
        }
        this.fragmentList.add(baseFragment);
        GiftItemEntity giftItemEntity = new GiftItemEntity();
        giftItemEntity.name = str;
        giftItemEntity.imgurl = str2;
        giftItemEntity.markId = str3;
        this.labelList.add(giftItemEntity);
    }
}
