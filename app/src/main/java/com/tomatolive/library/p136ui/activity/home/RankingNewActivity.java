package com.tomatolive.library.p136ui.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import com.blankj.utilcode.util.ConvertUtils;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.p136ui.adapter.HomeMenuTagAdapter;
import com.tomatolive.library.p136ui.fragment.RankingTabFragment;
import com.tomatolive.library.p136ui.fragment.RankingWeekStarFragment;
import com.tomatolive.library.p136ui.view.widget.ScaleTransitionPagerTitleView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

/* renamed from: com.tomatolive.library.ui.activity.home.RankingNewActivity */
/* loaded from: classes3.dex */
public class RankingNewActivity extends BaseActivity {
    private int currentIndex = 0;
    private ArrayList<String> rankingList = null;

    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter */
    protected BasePresenter mo6636createPresenter() {
        return null;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_ranking_new;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setTitle(R$string.fq_home_top);
        SysConfigInfoManager.getInstance().setEnableShowCurrentTop10(false);
        this.currentIndex = getIntent().getIntExtra(ConstantUtils.RESULT_FLAG, 0);
        this.rankingList = getIntent().getStringArrayListExtra(ConstantUtils.RESULT_ITEM);
        initMagicIndicator(this.mContext, getSupportFragmentManager(), (MagicIndicator) findViewById(R$id.magic_indicator), (ViewPager) findViewById(R$id.view_pager), getFragmentList(), getLabelList(), this.currentIndex);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        findViewById(R$id.ctv_back).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingNewActivity$RVq3fNlO_wnIMPPKc0yltG6Ajno
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RankingNewActivity.this.lambda$initListener$0$RankingNewActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$RankingNewActivity(View view) {
        onBackPressed();
    }

    private List<BaseFragment> getFragmentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(RankingTabFragment.newInstance(4));
        arrayList.add(RankingTabFragment.newInstance(5));
        ArrayList<String> arrayList2 = this.rankingList;
        if (arrayList2 == null) {
            return arrayList;
        }
        Iterator<String> it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            String next = it2.next();
            char c = 65535;
            if (next.hashCode() == -622460826 && next.equals(ConstantUtils.RANK_TYPE_WEEKSTAR)) {
                c = 0;
            }
            if (c == 0) {
                arrayList.add(RankingWeekStarFragment.newInstance());
            }
        }
        return arrayList;
    }

    private List<LabelEntity> getLabelList() {
        ArrayList arrayList = new ArrayList();
        String[] stringArray = getResources().getStringArray(R$array.fq_home_ranking_menu);
        arrayList.add(new LabelEntity(stringArray[0]));
        arrayList.add(new LabelEntity(stringArray[1]));
        ArrayList<String> arrayList2 = this.rankingList;
        if (arrayList2 == null) {
            return arrayList;
        }
        Iterator<String> it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            String next = it2.next();
            char c = 65535;
            if (next.hashCode() == -622460826 && next.equals(ConstantUtils.RANK_TYPE_WEEKSTAR)) {
                c = 0;
            }
            if (c == 0) {
                arrayList.add(new LabelEntity(stringArray[2]));
            }
        }
        return arrayList;
    }

    private void initMagicIndicator(Context context, FragmentManager fragmentManager, MagicIndicator magicIndicator, ViewPager viewPager, List<BaseFragment> list, List<LabelEntity> list2, int i) {
        magicIndicator.setBackgroundColor(ContextCompat.getColor(context, R$color.fq_colorWhite));
        viewPager.setAdapter(new HomeMenuTagAdapter(list, list2, fragmentManager));
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new C39231(list, list2, viewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        viewPager.setOffscreenPageLimit(list.size());
        viewPager.setCurrentItem(i, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.home.RankingNewActivity$1 */
    /* loaded from: classes3.dex */
    public class C39231 extends CommonNavigatorAdapter {
        final /* synthetic */ List val$fragmentList;
        final /* synthetic */ List val$itemList;
        final /* synthetic */ ViewPager val$pager;

        C39231(List list, List list2, ViewPager viewPager) {
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
            ScaleTransitionPagerTitleView scaleTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
            scaleTransitionPagerTitleView.setText(((LabelEntity) this.val$itemList.get(i)).name);
            scaleTransitionPagerTitleView.setTextSize(20.0f);
            scaleTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(context, R$color.fq_tab_menu_text_color));
            scaleTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(context, this.val$fragmentList.size() == 1 ? R$color.fq_colorTextTitlePrimary : R$color.fq_tab_menu_text_select_color));
            final ViewPager viewPager = this.val$pager;
            scaleTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingNewActivity$1$jdHxu6guK485OuqhG2hEj2eCVK4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ViewPager.this.setCurrentItem(i, false);
                }
            });
            return scaleTransitionPagerTitleView;
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
            int i = 0;
            linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(((BaseActivity) RankingNewActivity.this).mContext, R$color.fq_color_transparent)));
            linePagerIndicator.setLineHeight(ConvertUtils.dp2px(0.0f));
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
