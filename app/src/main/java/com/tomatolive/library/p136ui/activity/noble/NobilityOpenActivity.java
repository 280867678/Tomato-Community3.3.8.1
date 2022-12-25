package com.tomatolive.library.p136ui.activity.noble;

import android.content.Context;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import com.blankj.utilcode.util.ConvertUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.model.NobilityEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.NobilityOpenEvent;
import com.tomatolive.library.p136ui.activity.noble.NobilityOpenActivity;
import com.tomatolive.library.p136ui.adapter.HomeMenuTagAdapter;
import com.tomatolive.library.p136ui.fragment.NobilityOpenFragment;
import com.tomatolive.library.p136ui.presenter.NobilityOpenPresenter;
import com.tomatolive.library.p136ui.view.iview.INobilityOpenView;
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
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/* renamed from: com.tomatolive.library.ui.activity.noble.NobilityOpenActivity */
/* loaded from: classes3.dex */
public class NobilityOpenActivity extends BaseActivity<NobilityOpenPresenter> implements INobilityOpenView {
    private CommonNavigator commonNavigator;
    private CommonNavigatorAdapter commonNavigatorAdapter;
    private ViewPager mViewPager;
    private MagicIndicator magicIndicator;
    private HomeMenuTagAdapter menuTagAdapter;
    private List<BaseFragment> fragmentList = new ArrayList();
    private List<LabelEntity> labelList = new ArrayList();
    private AnchorEntity anchorInfoItem = null;

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityOpenView
    public void onInitDataFail() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public NobilityOpenPresenter mo6636createPresenter() {
        return new NobilityOpenPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_nobility_open;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar immersionBar = this.mImmersionBar;
        immersionBar.statusBarDarkFont(false);
        immersionBar.init();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.anchorInfoItem = (AnchorEntity) getIntent().getParcelableExtra(ConstantUtils.RESULT_ITEM);
        setActivityTitle(R$drawable.fq_ic_title_back_white, getString(R$string.fq_nobility));
        this.magicIndicator = (MagicIndicator) findViewById(R$id.magic_indicator);
        this.mViewPager = (ViewPager) findViewById(R$id.view_pager);
        initViewPager();
        ((NobilityOpenPresenter) this.mPresenter).getInitData(this.mStateView);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobilityOpenActivity$AGOVrZKWvMQ-MarEz5OUHyBKuEE
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                NobilityOpenActivity.this.lambda$initListener$0$NobilityOpenActivity();
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$NobilityOpenActivity() {
        ((NobilityOpenPresenter) this.mPresenter).getInitData(this.mStateView);
    }

    private void initViewPager() {
        this.menuTagAdapter = new HomeMenuTagAdapter(this.fragmentList, this.labelList, getSupportFragmentManager());
        this.commonNavigator = new CommonNavigator(this.mContext);
        this.commonNavigator.setAdjustMode(true);
        this.commonNavigatorAdapter = new C40811();
        this.commonNavigator.setAdapter(this.commonNavigatorAdapter);
        this.magicIndicator.setNavigator(this.commonNavigator);
        ViewPagerHelper.bind(this.magicIndicator, this.mViewPager);
        this.mViewPager.setAdapter(this.menuTagAdapter);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.noble.NobilityOpenActivity$1 */
    /* loaded from: classes3.dex */
    public class C40811 extends CommonNavigatorAdapter {
        C40811() {
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            if (NobilityOpenActivity.this.fragmentList == null) {
                return 0;
            }
            return NobilityOpenActivity.this.fragmentList.size();
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int i) {
            SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
            simplePagerTitleView.setText(((LabelEntity) NobilityOpenActivity.this.labelList.get(i)).name);
            simplePagerTitleView.setTextSize(14.0f);
            simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R$color.fq_nobility_tab_normal));
            simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, NobilityOpenActivity.this.fragmentList.size() == 1 ? R$color.fq_nobility_tab_normal : R$color.fq_nobility_tab_selected));
            simplePagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.noble.-$$Lambda$NobilityOpenActivity$1$mIIbPSSrVV9ZI_lkGZ4x7__TmNs
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NobilityOpenActivity.C40811.this.lambda$getTitleView$0$NobilityOpenActivity$1(i, view);
                }
            });
            return simplePagerTitleView;
        }

        public /* synthetic */ void lambda$getTitleView$0$NobilityOpenActivity$1(int i, View view) {
            NobilityOpenActivity.this.mViewPager.setCurrentItem(i, false);
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            int i = 0;
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(((BaseActivity) NobilityOpenActivity.this).mContext, R$color.fq_nobility_tab_selected)));
            linePagerIndicator.setLineHeight(ConvertUtils.dp2px(2.0f));
            linePagerIndicator.setLineWidth(ConvertUtils.dp2px(30.0f));
            linePagerIndicator.setRoundRadius(ConvertUtils.dp2px(3.0f));
            linePagerIndicator.setMode(2);
            if (NobilityOpenActivity.this.fragmentList.size() <= 1) {
                i = 4;
            }
            linePagerIndicator.setVisibility(i);
            return linePagerIndicator;
        }
    }

    private void initFragmentLabelList(List<NobilityEntity> list) {
        List<BaseFragment> list2 = this.fragmentList;
        if (list2 != null) {
            list2.clear();
        }
        List<LabelEntity> list3 = this.labelList;
        if (list3 != null) {
            list3.clear();
        }
        for (NobilityEntity nobilityEntity : list) {
            AnchorEntity anchorEntity = this.anchorInfoItem;
            if (anchorEntity != null) {
                nobilityEntity.anchorInfoItem = anchorEntity;
            }
            addFragment(NobilityOpenFragment.newInstance(nobilityEntity), nobilityEntity.name);
        }
    }

    private void notifyDataSetChangedViewPager() {
        this.commonNavigatorAdapter.notifyDataSetChanged();
        this.menuTagAdapter.notifyDataSetChanged();
        this.mViewPager.setOffscreenPageLimit(this.fragmentList.size());
        this.mViewPager.setCurrentItem(0, false);
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

    @Override // com.tomatolive.library.p136ui.view.iview.INobilityOpenView
    public void onInitDataSuccess(List<NobilityEntity> list) {
        try {
            initFragmentLabelList(list);
            notifyDataSetChangedViewPager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onEventMainThread(BaseEvent baseEvent) {
        super.onEventMainThread(baseEvent);
        if (!(baseEvent instanceof NobilityOpenEvent) || !((NobilityOpenEvent) baseEvent).isOpenSuccess) {
            return;
        }
        finish();
    }
}
