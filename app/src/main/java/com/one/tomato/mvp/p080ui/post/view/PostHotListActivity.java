package com.one.tomato.mvp.p080ui.post.view;

import android.support.design.widget.TabLayout;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.papa.adapter.PaPaTabAdapter;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.widget.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: PostHotListActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PostHotListActivity */
/* loaded from: classes3.dex */
public final class PostHotListActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private PaPaTabAdapter fragmentAdapter;
    private ArrayList<String> listStr = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();

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
        return R.layout.activity_post_hot_list;
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
        PostHotHomeFragment companion = PostHotHomeFragment.Companion.getInstance("/app/articleRank/hotValueList", "hot_rank_post");
        companion.setTabLayout((TabLayout) _$_findCachedViewById(R$id.hot_tab_layout));
        PostHotHomeFragment companion2 = PostHotHomeFragment.Companion.getInstance("/app/articleRank/payHotList", "pay_rank_post");
        companion2.setTabLayout((TabLayout) _$_findCachedViewById(R$id.pay_tab_layout));
        PostHotHomeFragment companion3 = PostHotHomeFragment.Companion.getInstance("/app/articleRank/downHotList", "browse_rank_post");
        companion3.setTabLayout((TabLayout) _$_findCachedViewById(R$id.browse_tab_layout));
        PostHotHomeFragment companion4 = PostHotHomeFragment.Companion.getInstance("/app/articleRank/favorHotList", "browse_rank_post");
        companion4.setTabLayout((TabLayout) _$_findCachedViewById(R$id.collect_tab_layout));
        this.fragmentList.add(companion);
        this.fragmentList.add(companion2);
        this.fragmentList.add(companion3);
        this.fragmentList.add(companion4);
        this.listStr.add("热度榜");
        this.listStr.add("付费榜");
        this.listStr.add("下载榜");
        this.listStr.add("收藏榜");
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
            previewViewPager4.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.one.tomato.mvp.ui.post.view.PostHotListActivity$initView$1
                @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int i) {
                }

                @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i, float f, int i2) {
                }

                @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                public void onPageSelected(int i) {
                    if (i == 0) {
                        TabLayout tabLayout = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.hot_tab_layout);
                        if (tabLayout != null) {
                            tabLayout.setVisibility(0);
                        }
                        TabLayout tabLayout2 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.pay_tab_layout);
                        if (tabLayout2 != null) {
                            tabLayout2.setVisibility(8);
                        }
                        TabLayout tabLayout3 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.browse_tab_layout);
                        if (tabLayout3 != null) {
                            tabLayout3.setVisibility(8);
                        }
                        TabLayout tabLayout4 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.collect_tab_layout);
                        if (tabLayout4 == null) {
                            return;
                        }
                        tabLayout4.setVisibility(8);
                    } else if (i == 1) {
                        TabLayout tabLayout5 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.hot_tab_layout);
                        if (tabLayout5 != null) {
                            tabLayout5.setVisibility(8);
                        }
                        TabLayout tabLayout6 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.pay_tab_layout);
                        if (tabLayout6 != null) {
                            tabLayout6.setVisibility(0);
                        }
                        TabLayout tabLayout7 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.browse_tab_layout);
                        if (tabLayout7 != null) {
                            tabLayout7.setVisibility(8);
                        }
                        TabLayout tabLayout8 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.collect_tab_layout);
                        if (tabLayout8 == null) {
                            return;
                        }
                        tabLayout8.setVisibility(8);
                    } else if (i == 2) {
                        TabLayout tabLayout9 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.hot_tab_layout);
                        if (tabLayout9 != null) {
                            tabLayout9.setVisibility(8);
                        }
                        TabLayout tabLayout10 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.pay_tab_layout);
                        if (tabLayout10 != null) {
                            tabLayout10.setVisibility(8);
                        }
                        TabLayout tabLayout11 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.browse_tab_layout);
                        if (tabLayout11 != null) {
                            tabLayout11.setVisibility(0);
                        }
                        TabLayout tabLayout12 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.collect_tab_layout);
                        if (tabLayout12 == null) {
                            return;
                        }
                        tabLayout12.setVisibility(8);
                    } else {
                        TabLayout tabLayout13 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.hot_tab_layout);
                        if (tabLayout13 != null) {
                            tabLayout13.setVisibility(8);
                        }
                        TabLayout tabLayout14 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.pay_tab_layout);
                        if (tabLayout14 != null) {
                            tabLayout14.setVisibility(8);
                        }
                        TabLayout tabLayout15 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.browse_tab_layout);
                        if (tabLayout15 != null) {
                            tabLayout15.setVisibility(8);
                        }
                        TabLayout tabLayout16 = (TabLayout) PostHotListActivity.this._$_findCachedViewById(R$id.collect_tab_layout);
                        if (tabLayout16 == null) {
                            return;
                        }
                        tabLayout16.setVisibility(0);
                    }
                }
            });
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iamge_back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostHotListActivity$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostHotListActivity.this.onBackPressed();
                }
            });
        }
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (PostUtils.INSTANCE.notifyBackObserver(true)) {
            return;
        }
        super.onBackPressed();
    }
}
