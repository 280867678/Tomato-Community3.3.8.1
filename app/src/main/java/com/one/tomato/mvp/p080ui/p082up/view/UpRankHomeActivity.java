package com.one.tomato.mvp.p080ui.p082up.view;

import android.support.design.widget.TabLayout;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.papa.adapter.PaPaTabAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.widget.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: UpRankHomeActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.UpRankHomeActivity */
/* loaded from: classes3.dex */
public final class UpRankHomeActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private PaPaTabAdapter fragmentAdapter;
    private ArrayList<String> listStr = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private String topListCfg = "";

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_up_rank_home;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        List<String> split$default;
        boolean z;
        boolean z2;
        SystemParam systemParam = DBUtil.getSystemParam();
        this.topListCfg = systemParam != null ? systemParam.getTopListCfg() : null;
        if (TextUtils.isEmpty(this.topListCfg)) {
            this.topListCfg = "1,2,3,4";
        }
        String str = this.topListCfg;
        if (str == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        split$default = StringsKt__StringsKt.split$default(str, new String[]{","}, false, 0, 6, null);
        boolean z3 = false;
        for (String str2 : split$default) {
            boolean z4 = true;
            switch (str2.hashCode()) {
                case 49:
                    if (!str2.equals("1")) {
                        break;
                    } else {
                        if (!z3) {
                            setTabVisible("1");
                            z2 = true;
                            z = true;
                        } else {
                            z = z3;
                            z2 = false;
                        }
                        UpIncomeRankFragment companion = UpIncomeRankFragment.Companion.getInstance("up_rank_income", 1, z2);
                        companion.setTabLayout((TabLayout) _$_findCachedViewById(R$id.hot_tab_layout));
                        this.fragmentList.add(companion);
                        this.listStr.add(AppUtil.getString(R.string.up_rank_income));
                        z3 = z;
                        break;
                    }
                case 50:
                    if (!str2.equals("2")) {
                        break;
                    } else {
                        if (!z3) {
                            setTabVisible("2");
                            z3 = true;
                        } else {
                            z4 = false;
                        }
                        UpIncomeRankFragment companion2 = UpIncomeRankFragment.Companion.getInstance("up_rank_publish", 2, z4);
                        companion2.setTabLayout((TabLayout) _$_findCachedViewById(R$id.collect_tab_layout));
                        this.fragmentList.add(companion2);
                        this.listStr.add(AppUtil.getString(R.string.up_rank_publish));
                        break;
                    }
                case 51:
                    if (!str2.equals("3")) {
                        break;
                    } else {
                        if (!z3) {
                            setTabVisible("3");
                            z3 = true;
                        } else {
                            z4 = false;
                        }
                        UpIncomeRankFragment companion3 = UpIncomeRankFragment.Companion.getInstance("up_rank_hot", 3, z4);
                        companion3.setTabLayout((TabLayout) _$_findCachedViewById(R$id.browse_tab_layout));
                        this.fragmentList.add(companion3);
                        this.listStr.add(AppUtil.getString(R.string.up_rank_hot));
                        break;
                    }
                case 52:
                    if (!str2.equals("4")) {
                        break;
                    } else {
                        if (!z3) {
                            setTabVisible("4");
                            z3 = true;
                        } else {
                            z4 = false;
                        }
                        UpIncomeRankFragment companion4 = UpIncomeRankFragment.Companion.getInstance("up_rank_fans", 4, z4);
                        companion4.setTabLayout((TabLayout) _$_findCachedViewById(R$id.pay_tab_layout));
                        this.fragmentList.add(companion4);
                        this.listStr.add(AppUtil.getString(R.string.up_rank_fans));
                        break;
                    }
            }
        }
        this.fragmentAdapter = new PaPaTabAdapter(getSupportFragmentManager(), this.fragmentList, this.listStr);
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager != null) {
            previewViewPager.setAdapter(this.fragmentAdapter);
        }
        PreviewViewPager previewViewPager2 = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager2 != null) {
            previewViewPager2.setOffscreenPageLimit(4);
        }
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_strip);
        if (pagerSlidingTabStrip != null) {
            pagerSlidingTabStrip.setViewPager((PreviewViewPager) _$_findCachedViewById(R$id.view_pager));
        }
        PagerSlidingTabUtil.setPostRankAllTabsValue(getMContext(), (PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_strip), false);
        PreviewViewPager previewViewPager3 = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager3 != null) {
            previewViewPager3.setCurrentItem(0);
        }
        PreviewViewPager previewViewPager4 = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager4 != null) {
            previewViewPager4.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.one.tomato.mvp.ui.up.view.UpRankHomeActivity$initView$1
                @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int i) {
                }

                @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i, float f, int i2) {
                }

                @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                public void onPageSelected(int i) {
                    String str3;
                    String str4;
                    str3 = UpRankHomeActivity.this.topListCfg;
                    if (TextUtils.isEmpty(str3)) {
                        UpRankHomeActivity.this.topListCfg = "1,2,3,4";
                    }
                    str4 = UpRankHomeActivity.this.topListCfg;
                    List split$default2 = str4 != null ? StringsKt__StringsKt.split$default(str4, new String[]{","}, false, 0, 6, null) : null;
                    if (split$default2 != null) {
                        try {
                            UpRankHomeActivity.this.setTabVisible((String) split$default2.get(i));
                        } catch (Exception unused) {
                        }
                    }
                }
            });
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iamge_back);
        if (imageView == null) {
            return;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.up.view.UpRankHomeActivity$initView$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UpRankHomeActivity.this.onBackPressed();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setTabVisible(String str) {
        switch (str.hashCode()) {
            case 49:
                if (str.equals("1")) {
                    TabLayout tabLayout = (TabLayout) _$_findCachedViewById(R$id.hot_tab_layout);
                    if (tabLayout != null) {
                        tabLayout.setVisibility(0);
                    }
                    TabLayout tabLayout2 = (TabLayout) _$_findCachedViewById(R$id.pay_tab_layout);
                    if (tabLayout2 != null) {
                        tabLayout2.setVisibility(8);
                    }
                    TabLayout tabLayout3 = (TabLayout) _$_findCachedViewById(R$id.browse_tab_layout);
                    if (tabLayout3 != null) {
                        tabLayout3.setVisibility(8);
                    }
                    TabLayout tabLayout4 = (TabLayout) _$_findCachedViewById(R$id.collect_tab_layout);
                    if (tabLayout4 == null) {
                        return;
                    }
                    tabLayout4.setVisibility(8);
                    return;
                }
                break;
            case 50:
                if (str.equals("2")) {
                    TabLayout tabLayout5 = (TabLayout) _$_findCachedViewById(R$id.hot_tab_layout);
                    if (tabLayout5 != null) {
                        tabLayout5.setVisibility(8);
                    }
                    TabLayout tabLayout6 = (TabLayout) _$_findCachedViewById(R$id.pay_tab_layout);
                    if (tabLayout6 != null) {
                        tabLayout6.setVisibility(8);
                    }
                    TabLayout tabLayout7 = (TabLayout) _$_findCachedViewById(R$id.browse_tab_layout);
                    if (tabLayout7 != null) {
                        tabLayout7.setVisibility(8);
                    }
                    TabLayout tabLayout8 = (TabLayout) _$_findCachedViewById(R$id.collect_tab_layout);
                    if (tabLayout8 == null) {
                        return;
                    }
                    tabLayout8.setVisibility(0);
                    return;
                }
                break;
            case 51:
                if (str.equals("3")) {
                    TabLayout tabLayout9 = (TabLayout) _$_findCachedViewById(R$id.hot_tab_layout);
                    if (tabLayout9 != null) {
                        tabLayout9.setVisibility(8);
                    }
                    TabLayout tabLayout10 = (TabLayout) _$_findCachedViewById(R$id.pay_tab_layout);
                    if (tabLayout10 != null) {
                        tabLayout10.setVisibility(8);
                    }
                    TabLayout tabLayout11 = (TabLayout) _$_findCachedViewById(R$id.browse_tab_layout);
                    if (tabLayout11 != null) {
                        tabLayout11.setVisibility(0);
                    }
                    TabLayout tabLayout12 = (TabLayout) _$_findCachedViewById(R$id.collect_tab_layout);
                    if (tabLayout12 == null) {
                        return;
                    }
                    tabLayout12.setVisibility(8);
                    return;
                }
                break;
        }
        TabLayout tabLayout13 = (TabLayout) _$_findCachedViewById(R$id.hot_tab_layout);
        if (tabLayout13 != null) {
            tabLayout13.setVisibility(8);
        }
        TabLayout tabLayout14 = (TabLayout) _$_findCachedViewById(R$id.pay_tab_layout);
        if (tabLayout14 != null) {
            tabLayout14.setVisibility(0);
        }
        TabLayout tabLayout15 = (TabLayout) _$_findCachedViewById(R$id.browse_tab_layout);
        if (tabLayout15 != null) {
            tabLayout15.setVisibility(8);
        }
        TabLayout tabLayout16 = (TabLayout) _$_findCachedViewById(R$id.collect_tab_layout);
        if (tabLayout16 != null) {
            tabLayout16.setVisibility(8);
        }
    }
}
