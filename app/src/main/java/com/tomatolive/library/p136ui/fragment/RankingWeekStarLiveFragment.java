package com.tomatolive.library.p136ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import com.blankj.utilcode.util.ConvertUtils;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.p136ui.adapter.HomeMenuTagAdapter;
import com.tomatolive.library.p136ui.fragment.RankingWeekStarLiveFragment;
import com.tomatolive.library.p136ui.interfaces.OnUserCardCallback;
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
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/* renamed from: com.tomatolive.library.ui.fragment.RankingWeekStarLiveFragment */
/* loaded from: classes3.dex */
public class RankingWeekStarLiveFragment extends BaseFragment {
    private AnchorEntity anchorItemEntity;
    private CommonNavigator commonNavigator;
    private CommonNavigatorAdapter commonNavigatorAdapter;
    private ViewPager mViewPager;
    private MagicIndicator magicIndicator;
    private String markId;
    private HomeMenuTagAdapter menuTagAdapter;
    private List<BaseFragment> fragmentList = new ArrayList();
    private List<LabelEntity> labelList = new ArrayList();
    private OnUserCardCallback onUserCardCallback = null;

    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter */
    protected BasePresenter mo6641createPresenter() {
        return null;
    }

    public static RankingWeekStarLiveFragment newInstance(AnchorEntity anchorEntity, String str, OnUserCardCallback onUserCardCallback) {
        Bundle bundle = new Bundle();
        RankingWeekStarLiveFragment rankingWeekStarLiveFragment = new RankingWeekStarLiveFragment();
        bundle.putString(ConstantUtils.RESULT_ID, str);
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
        rankingWeekStarLiveFragment.setArguments(bundle);
        rankingWeekStarLiveFragment.setOnUserCardCallback(onUserCardCallback);
        return rankingWeekStarLiveFragment;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.markId = bundle.getString(ConstantUtils.RESULT_ID);
        this.anchorItemEntity = (AnchorEntity) bundle.getParcelable(ConstantUtils.RESULT_ITEM);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_ranking_week_star_live;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        this.magicIndicator = (MagicIndicator) view.findViewById(R$id.magic_indicator);
        this.mViewPager = (ViewPager) view.findViewById(R$id.view_pager);
        initViewPager();
        initFragmentLabelList();
        notifyDataSetChangedViewPager();
    }

    public void setOnUserCardCallback(OnUserCardCallback onUserCardCallback) {
        this.onUserCardCallback = onUserCardCallback;
    }

    private void initViewPager() {
        this.menuTagAdapter = new HomeMenuTagAdapter(this.fragmentList, this.labelList, getChildFragmentManager());
        this.commonNavigator = new CommonNavigator(this.mContext);
        this.commonNavigator.setAdjustMode(true);
        this.commonNavigatorAdapter = new C41291();
        this.commonNavigator.setAdapter(this.commonNavigatorAdapter);
        this.magicIndicator.setNavigator(this.commonNavigator);
        ViewPagerHelper.bind(this.magicIndicator, this.mViewPager);
        this.mViewPager.setAdapter(this.menuTagAdapter);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.fragment.RankingWeekStarLiveFragment$1 */
    /* loaded from: classes3.dex */
    public class C41291 extends CommonNavigatorAdapter {
        C41291() {
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            if (RankingWeekStarLiveFragment.this.fragmentList == null) {
                return 0;
            }
            return RankingWeekStarLiveFragment.this.fragmentList.size();
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int i) {
            SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
            simplePagerTitleView.setText(((LabelEntity) RankingWeekStarLiveFragment.this.labelList.get(i)).name);
            simplePagerTitleView.setTextSize(16.0f);
            simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R$color.fq_tab_menu_text_color));
            simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, RankingWeekStarLiveFragment.this.fragmentList.size() == 1 ? R$color.fq_colorTextTitlePrimary : R$color.fq_tab_menu_text_select_color));
            simplePagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$RankingWeekStarLiveFragment$1$f1mKj8_oqj4yhzMiq9jKHWxs3bE
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RankingWeekStarLiveFragment.C41291.this.lambda$getTitleView$0$RankingWeekStarLiveFragment$1(i, view);
                }
            });
            return simplePagerTitleView;
        }

        public /* synthetic */ void lambda$getTitleView$0$RankingWeekStarLiveFragment$1(int i, View view) {
            RankingWeekStarLiveFragment.this.mViewPager.setCurrentItem(i, false);
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            int i = 0;
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(((BaseFragment) RankingWeekStarLiveFragment.this).mContext, R$color.fq_tab_menu_text_select_color)));
            linePagerIndicator.setLineHeight(ConvertUtils.dp2px(2.0f));
            linePagerIndicator.setLineWidth(ConvertUtils.dp2px(18.0f));
            linePagerIndicator.setRoundRadius(ConvertUtils.dp2px(3.0f));
            linePagerIndicator.setMode(2);
            if (RankingWeekStarLiveFragment.this.fragmentList.size() <= 1) {
                i = 4;
            }
            linePagerIndicator.setVisibility(i);
            return linePagerIndicator;
        }
    }

    private void initFragmentLabelList() {
        String[] stringArray = getResources().getStringArray(R$array.fq_week_star_live_tab);
        addFragment(RankingWeekStarAnchorFragment.newInstance(this.anchorItemEntity, this.markId, false, this.onUserCardCallback), stringArray[0]);
        addFragment(RankingWeekStarAnchorFragment.newInstance(this.anchorItemEntity, this.markId, true, this.onUserCardCallback), stringArray[1]);
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
}
