package com.tomatolive.library.p136ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import com.blankj.utilcode.util.ConvertUtils;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$dimen;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.p136ui.adapter.HomeMenuTagAdapter;
import com.tomatolive.library.p136ui.fragment.RankingTabFragment;
import com.tomatolive.library.p136ui.presenter.RankingTabPresenter;
import com.tomatolive.library.p136ui.view.iview.IRankingTabView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.List;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

/* renamed from: com.tomatolive.library.ui.fragment.RankingTabFragment */
/* loaded from: classes3.dex */
public class RankingTabFragment extends BaseFragment<RankingTabPresenter> implements IRankingTabView {
    private CommonNavigator commonNavigator;
    private CommonNavigatorAdapter commonNavigatorAdapter;
    private List<BaseFragment> fragmentList = new ArrayList();
    private List<LabelEntity> labelList = new ArrayList();
    private ViewPager mViewPager;
    private MagicIndicator magicIndicator;
    private HomeMenuTagAdapter menuTagAdapter;
    private int rankingType;

    @Override // com.tomatolive.library.base.BaseFragment
    public boolean isLazyLoad() {
        return true;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRankingTabView
    public void onRankConfigFail() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    public static RankingTabFragment newInstance(int i) {
        Bundle bundle = new Bundle();
        RankingTabFragment rankingTabFragment = new RankingTabFragment();
        bundle.putInt(ConstantUtils.RESULT_ID, i);
        rankingTabFragment.setArguments(bundle);
        return rankingTabFragment;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.rankingType = bundle.getInt(ConstantUtils.RESULT_ID, 4);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public RankingTabPresenter mo6641createPresenter() {
        return new RankingTabPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_ranking_tab;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    protected View injectStateView(View view) {
        return view.findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        this.magicIndicator = (MagicIndicator) view.findViewById(R$id.magic_indicator);
        this.mViewPager = (ViewPager) view.findViewById(R$id.view_pager);
        initViewPager();
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onLazyLoad() {
        ((RankingTabPresenter) this.mPresenter).getRankConfig(this.mStateView);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.RankingTabFragment.1
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public void onRetryClick() {
                ((RankingTabPresenter) ((BaseFragment) RankingTabFragment.this).mPresenter).getRankConfig(((BaseFragment) RankingTabFragment.this).mStateView);
            }
        });
    }

    private void initViewPager() {
        this.menuTagAdapter = new HomeMenuTagAdapter(this.fragmentList, this.labelList, getChildFragmentManager());
        this.commonNavigator = new CommonNavigator(this.mContext);
        this.commonNavigatorAdapter = new C41242();
        this.commonNavigator.setAdapter(this.commonNavigatorAdapter);
        this.magicIndicator.setNavigator(this.commonNavigator);
        ViewPagerHelper.bind(this.magicIndicator, this.mViewPager);
        this.mViewPager.setAdapter(this.menuTagAdapter);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.fragment.RankingTabFragment$2 */
    /* loaded from: classes3.dex */
    public class C41242 extends CommonNavigatorAdapter {
        C41242() {
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            if (RankingTabFragment.this.fragmentList == null) {
                return 0;
            }
            return RankingTabFragment.this.fragmentList.size();
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int i) {
            ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
            int dp2px = ConvertUtils.dp2px(27.0f);
            clipPagerTitleView.setPadding(dp2px, 0, dp2px, 0);
            clipPagerTitleView.setText(((LabelEntity) RankingTabFragment.this.labelList.get(i)).name);
            clipPagerTitleView.setTextColor(ContextCompat.getColor(context, R$color.fq_tab_menu_text_color));
            clipPagerTitleView.setClipColor(ContextCompat.getColor(context, R$color.fq_tab_menu_text_select_color));
            clipPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$RankingTabFragment$2$NnIv3Mbkryboi-CgConvMB3S99A
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RankingTabFragment.C41242.this.lambda$getTitleView$0$RankingTabFragment$2(i, view);
                }
            });
            return clipPagerTitleView;
        }

        public /* synthetic */ void lambda$getTitleView$0$RankingTabFragment$2(int i, View view) {
            RankingTabFragment.this.mViewPager.setCurrentItem(i, false);
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            float dimension = context.getResources().getDimension(R$dimen.fq_home_ranking_tab_height);
            linePagerIndicator.setLineHeight(dimension);
            linePagerIndicator.setRoundRadius(dimension / 2.0f);
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(context, R$color.fq_colorWhite)));
            return linePagerIndicator;
        }
    }

    private void initFragmentLabelList(List<LabelEntity> list) {
        List<BaseFragment> list2 = this.fragmentList;
        if (list2 != null) {
            list2.clear();
        }
        List<LabelEntity> list3 = this.labelList;
        if (list3 != null) {
            list3.clear();
        }
        if (list == null || list.size() <= 0) {
            return;
        }
        for (LabelEntity labelEntity : list) {
            addFragment(RankingFragment.newInstance(this.rankingType, labelEntity.f5839id, labelEntity.isSelected), labelEntity.name);
        }
    }

    private void addFragment(BaseFragment baseFragment, String str) {
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
        this.labelList.add(new LabelEntity(str));
    }

    private void notifyDataSetChangedViewPager() {
        this.commonNavigatorAdapter.notifyDataSetChanged();
        this.menuTagAdapter.notifyDataSetChanged();
        this.mViewPager.setOffscreenPageLimit(this.fragmentList.size());
        this.mViewPager.setCurrentItem(0, false);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRankingTabView
    public void onRankConfigSuccess(List<LabelEntity> list) {
        initFragmentLabelList(list);
        notifyDataSetChangedViewPager();
    }
}
