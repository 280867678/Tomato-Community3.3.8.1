package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Context;
import android.os.Bundle;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import com.blankj.utilcode.util.ConvertUtils;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.p136ui.adapter.HomeMenuTagAdapter;
import com.tomatolive.library.p136ui.fragment.ConsumeFragment;
import com.tomatolive.library.p136ui.fragment.IncomeFragment;
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

/* renamed from: com.tomatolive.library.ui.activity.mylive.IncomeRecordActivity */
/* loaded from: classes3.dex */
public class IncomeRecordActivity extends BaseActivity {
    private boolean isAuth = false;

    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter */
    protected BasePresenter mo6636createPresenter() {
        return null;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_income_record;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setTitle(getString(R$string.fq_my_live_income_record));
        this.isAuth = getIntent().getBooleanExtra(ConstantUtils.IS_AUTH, false);
        initMagicIndicator(this.mContext, getSupportFragmentManager(), (MagicIndicator) findViewById(R$id.magic_indicator), (ViewPager) findViewById(R$id.view_pager), getFragmentList(), getLabelList(), 0);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        findViewById(R$id.ctv_back).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$IncomeRecordActivity$PlnZUmc8rhyADzZuI6ul-otcS-0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IncomeRecordActivity.this.lambda$initListener$0$IncomeRecordActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$IncomeRecordActivity(View view) {
        onBackPressed();
    }

    private List<BaseFragment> getFragmentList() {
        ArrayList arrayList = new ArrayList();
        if (this.isAuth) {
            arrayList.add(IncomeFragment.newInstance());
        }
        arrayList.add(ConsumeFragment.newInstance());
        return arrayList;
    }

    private List<LabelEntity> getLabelList() {
        ArrayList arrayList = new ArrayList();
        if (this.isAuth) {
            arrayList.add(new LabelEntity(getString(R$string.fq_my_live_income_detail)));
        }
        arrayList.add(new LabelEntity(getString(R$string.fq_my_live_consume_detail)));
        return arrayList;
    }

    private void initMagicIndicator(Context context, FragmentManager fragmentManager, MagicIndicator magicIndicator, ViewPager viewPager, List<BaseFragment> list, List<LabelEntity> list2, int i) {
        magicIndicator.setBackgroundColor(ContextCompat.getColor(context, R$color.fq_colorWhite));
        viewPager.setAdapter(new HomeMenuTagAdapter(list, list2, fragmentManager));
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new C40511(list, list2, viewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        viewPager.setOffscreenPageLimit(list.size());
        viewPager.setCurrentItem(i, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.mylive.IncomeRecordActivity$1 */
    /* loaded from: classes3.dex */
    public class C40511 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$fragmentList;
        final /* synthetic */ List val$itemList;
        final /* synthetic */ ViewPager val$pager;

        C40511(List list, List list2, ViewPager viewPager) {
            this.val$fragmentList = list;
            this.val$itemList = list2;
            this.val$pager = viewPager;
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            List list = this.val$fragmentList;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int i) {
            SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
            simplePagerTitleView.setText(((LabelEntity) this.val$itemList.get(i)).name);
            simplePagerTitleView.setTextSize(18.0f);
            simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R$color.fq_tab_menu_text_color));
            simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, this.val$fragmentList.size() == 1 ? R$color.fq_colorTextTitlePrimary : R$color.fq_tab_menu_text_select_color));
            final ViewPager viewPager = this.val$pager;
            simplePagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$IncomeRecordActivity$1$ZxSZFhuUE4E8NTZpuQJurp9OpFE
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ViewPager.this.setCurrentItem(i, false);
                }
            });
            return simplePagerTitleView;
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            int i = 0;
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(((BaseActivity) IncomeRecordActivity.this).mContext, R$color.fq_tab_menu_text_select_color)));
            linePagerIndicator.setLineHeight(ConvertUtils.dp2px(2.0f));
            linePagerIndicator.setLineWidth(ConvertUtils.dp2px(30.0f));
            linePagerIndicator.setRoundRadius(ConvertUtils.dp2px(3.0f));
            linePagerIndicator.setMode(2);
            if (this.val$fragmentList.size() <= 1) {
                i = 4;
            }
            linePagerIndicator.setVisibility(i);
            return linePagerIndicator;
        }
    }
}
