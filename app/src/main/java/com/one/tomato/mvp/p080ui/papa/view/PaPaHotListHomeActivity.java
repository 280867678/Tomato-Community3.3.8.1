package com.one.tomato.mvp.p080ui.papa.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.papa.adapter.PaPaTabAdapter;
import com.one.tomato.utils.LogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PaPaHotListHomeActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.PaPaHotListHomeActivity */
/* loaded from: classes3.dex */
public final class PaPaHotListHomeActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> implements IBaseView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private PaPaTabAdapter adapter;
    private List<Fragment> listFragment = new ArrayList();
    private List<String> listStr = new ArrayList();

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
        return R.layout.papa_hot_detail__list_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    /* compiled from: PaPaHotListHomeActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.papa.view.PaPaHotListHomeActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            context.startActivity(new Intent(context, PaPaHotListHomeActivity.class));
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        TabLayout.Tab tabAt;
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaHotListHomeActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PaPaHotListHomeActivity.this.onBackPressed();
                }
            });
        }
        List<String> list = this.listStr;
        String string = getString(R.string.post_most_video_text_today_hot);
        Intrinsics.checkExpressionValueIsNotNull(string, "getString(R.string.post_most_video_text_today_hot)");
        list.add(string);
        List<String> list2 = this.listStr;
        String string2 = getString(R.string.post_most_video_text_week_hot);
        Intrinsics.checkExpressionValueIsNotNull(string2, "getString(R.string.post_most_video_text_week_hot)");
        list2.add(string2);
        List<String> list3 = this.listStr;
        String string3 = getString(R.string.post_most_video_text_month_hot);
        Intrinsics.checkExpressionValueIsNotNull(string3, "getString(R.string.post_most_video_text_month_hot)");
        list3.add(string3);
        this.listFragment.add(PaPaHotListFragment.Companion.initInstance(1));
        this.listFragment.add(PaPaHotListFragment.Companion.initInstance(2));
        this.listFragment.add(PaPaHotListFragment.Companion.initInstance(3));
        this.adapter = new PaPaTabAdapter(getSupportFragmentManager(), this.listFragment, this.listStr);
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.preview_pager);
        if (previewViewPager != null) {
            previewViewPager.setAdapter(this.adapter);
        }
        PreviewViewPager previewViewPager2 = (PreviewViewPager) _$_findCachedViewById(R$id.preview_pager);
        if (previewViewPager2 != null) {
            previewViewPager2.setCurrentItem(0);
        }
        PreviewViewPager previewViewPager3 = (PreviewViewPager) _$_findCachedViewById(R$id.preview_pager);
        if (previewViewPager3 != null) {
            previewViewPager3.setOffscreenPageLimit(this.listFragment.size());
        }
        TabLayout tabLayout = (TabLayout) _$_findCachedViewById(R$id.tab_layout);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager((PreviewViewPager) _$_findCachedViewById(R$id.preview_pager));
        }
        int i = 0;
        while (true) {
            View view = null;
            if (i < 3) {
                TabLayout tabLayout2 = (TabLayout) _$_findCachedViewById(R$id.tab_layout);
                TabLayout.Tab tabAt2 = tabLayout2 != null ? tabLayout2.getTabAt(i) : null;
                TextView textView = new TextView(getMContext());
                textView.setText(this.listStr.get(i));
                Context mContext = getMContext();
                if (mContext == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                textView.setTextSize(14.0f);
                if (tabAt2 != null) {
                    tabAt2.setCustomView(textView);
                }
                i++;
            } else {
                TabLayout tabLayout3 = (TabLayout) _$_findCachedViewById(R$id.tab_layout);
                if (tabLayout3 != null && (tabAt = tabLayout3.getTabAt(0)) != null) {
                    view = tabAt.getCustomView();
                }
                TextView textView2 = (TextView) view;
                if (textView2 != null) {
                    textView2.setTextSize(18.0f);
                }
                PreviewViewPager previewViewPager4 = (PreviewViewPager) _$_findCachedViewById(R$id.preview_pager);
                if (previewViewPager4 != null) {
                    previewViewPager4.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaHotListHomeActivity$initView$2
                        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                        public void onPageScrollStateChanged(int i2) {
                        }

                        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                        public void onPageScrolled(int i2, float f, int i3) {
                        }

                        @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                        public void onPageSelected(int i2) {
                            AppBarLayout appBarLayout;
                            if (i2 == 0) {
                                AppBarLayout appBarLayout2 = (AppBarLayout) PaPaHotListHomeActivity.this._$_findCachedViewById(R$id.appbar_layout);
                                if (appBarLayout2 == null) {
                                    return;
                                }
                                appBarLayout2.setBackground(ContextCompat.getDrawable(PaPaHotListHomeActivity.this, R.drawable.papa_hot_today));
                            } else if (i2 != 1) {
                                if (i2 != 2 || (appBarLayout = (AppBarLayout) PaPaHotListHomeActivity.this._$_findCachedViewById(R$id.appbar_layout)) == null) {
                                    return;
                                }
                                appBarLayout.setBackground(ContextCompat.getDrawable(PaPaHotListHomeActivity.this, R.drawable.papa_hot_month));
                            } else {
                                AppBarLayout appBarLayout3 = (AppBarLayout) PaPaHotListHomeActivity.this._$_findCachedViewById(R$id.appbar_layout);
                                if (appBarLayout3 == null) {
                                    return;
                                }
                                appBarLayout3.setBackground(ContextCompat.getDrawable(PaPaHotListHomeActivity.this, R.drawable.papa_hot_week));
                            }
                        }
                    });
                }
                TabLayout tabLayout4 = (TabLayout) _$_findCachedViewById(R$id.tab_layout);
                if (tabLayout4 != null) {
                    tabLayout4.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaHotListHomeActivity$initView$3
                        @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
                        public void onTabReselected(TabLayout.Tab tab) {
                        }

                        @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
                        public void onTabUnselected(TabLayout.Tab tab) {
                            TextView textView3 = (TextView) (tab != null ? tab.getCustomView() : null);
                            if (textView3 != null) {
                                textView3.setTextSize(14.0f);
                            }
                        }

                        @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
                        public void onTabSelected(TabLayout.Tab tab) {
                            TextView textView3 = (TextView) (tab != null ? tab.getCustomView() : null);
                            if (textView3 != null) {
                                textView3.setTextSize(18.0f);
                            }
                        }
                    });
                }
                AppBarLayout appBarLayout = (AppBarLayout) _$_findCachedViewById(R$id.appbar_layout);
                if (appBarLayout == null) {
                    return;
                }
                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaHotListHomeActivity$initView$4
                    @Override // android.support.design.widget.AppBarLayout.OnOffsetChangedListener, android.support.design.widget.AppBarLayout.BaseOnOffsetChangedListener
                    public void onOffsetChanged(AppBarLayout appBarLayout2, int i2) {
                        float abs = Math.abs(i2) / (appBarLayout2 != null ? appBarLayout2.getTotalScrollRange() : 0);
                        LogUtil.m3787d("yan2", "偏移量-------" + abs);
                        TextView textView3 = (TextView) PaPaHotListHomeActivity.this._$_findCachedViewById(R$id.toolbar_title);
                        if (textView3 != null) {
                            textView3.setAlpha(abs);
                        }
                        ImageView imageView2 = (ImageView) PaPaHotListHomeActivity.this._$_findCachedViewById(R$id.image_hot);
                        if (imageView2 != null) {
                            imageView2.setAlpha(1 - abs);
                        }
                    }
                });
                return;
            }
        }
    }
}
