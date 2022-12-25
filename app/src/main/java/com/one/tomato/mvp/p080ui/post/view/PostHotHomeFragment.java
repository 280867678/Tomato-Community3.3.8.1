package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.content.ContextCompat;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.one.tomato.R$id;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.p080ui.papa.adapter.PaPaTabAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostHotHomeFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PostHotHomeFragment */
/* loaded from: classes3.dex */
public final class PostHotHomeFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private PaPaTabAdapter fragmentAdapter;
    private TabLayout tab_layout;
    private String rankUrl = "";
    private ArrayList<String> listStrSub = new ArrayList<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private String businessType = "";

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.post_hot_list_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: PostHotHomeFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.PostHotHomeFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final PostHotHomeFragment getInstance(String url, String businessType) {
            Intrinsics.checkParameterIsNotNull(url, "url");
            Intrinsics.checkParameterIsNotNull(businessType, "businessType");
            PostHotHomeFragment postHotHomeFragment = new PostHotHomeFragment();
            Bundle bundle = new Bundle();
            bundle.putString("post_rank_url", url);
            bundle.putString("business", businessType);
            postHotHomeFragment.setArguments(bundle);
            return postHotHomeFragment;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        String str;
        String str2;
        Bundle arguments = getArguments();
        if (arguments == null || (str = arguments.getString("post_rank_url")) == null) {
            str = "/app/articleRank/hotValueList";
        }
        this.rankUrl = str;
        Bundle arguments2 = getArguments();
        if (arguments2 == null || (str2 = arguments2.getString("business")) == null) {
            str2 = "hot_rank_post";
        }
        this.businessType = str2;
        this.listStrSub.add(AppUtil.getString(R.string.up_rank_day));
        this.listStrSub.add(AppUtil.getString(R.string.up_rank_week));
        this.listStrSub.add(AppUtil.getString(R.string.up_rank_moon));
        if (Intrinsics.areEqual(this.businessType, "hot_rank_post")) {
            setUserVisibleHint(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        TabLayout.Tab tabAt;
        super.onLazyLoad();
        this.fragmentList.add(PostHotListFragment.Companion.getInstance(-1, this.businessType, this.rankUrl, 1));
        this.fragmentList.add(PostHotListFragment.Companion.getInstance(-1, this.businessType, this.rankUrl, 2));
        this.fragmentList.add(PostHotListFragment.Companion.getInstance(-1, this.businessType, this.rankUrl, 3));
        this.fragmentAdapter = new PaPaTabAdapter(getChildFragmentManager(), this.fragmentList, this.listStrSub);
        initTab();
        TabLayout tabLayout = this.tab_layout;
        TextView textView = (TextView) ((tabLayout == null || (tabAt = tabLayout.getTabAt(0)) == null) ? null : tabAt.getCustomView());
        if (textView != null) {
            Context mContext = getMContext();
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.up_shape_solid_rank_incom_30));
        }
        if (textView != null) {
            Context mContext2 = getMContext();
            if (mContext2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView.setTextColor(ContextCompat.getColor(mContext2, R.color.colorAccent));
        }
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager != null) {
            previewViewPager.setCurrentItem(0);
        }
    }

    public final void setTabLayout(TabLayout tabLayout) {
        this.tab_layout = tabLayout;
    }

    private final void initTab() {
        if (getMContext() == null) {
            return;
        }
        TabLayout tabLayout = this.tab_layout;
        if (tabLayout != null) {
            tabLayout.setTabMode(1);
        }
        PreviewViewPager previewViewPager = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager != null) {
            previewViewPager.setAdapter(this.fragmentAdapter);
        }
        PreviewViewPager previewViewPager2 = (PreviewViewPager) _$_findCachedViewById(R$id.view_pager);
        if (previewViewPager2 != null) {
            previewViewPager2.setOffscreenPageLimit(3);
        }
        TabLayout tabLayout2 = this.tab_layout;
        if (tabLayout2 != null) {
            tabLayout2.setupWithViewPager((PreviewViewPager) _$_findCachedViewById(R$id.view_pager));
        }
        for (int i = 0; i < 3; i++) {
            TabLayout tabLayout3 = this.tab_layout;
            TabLayout.Tab tabAt = tabLayout3 != null ? tabLayout3.getTabAt(i) : null;
            TextView textView = new TextView(getMContext());
            textView.setWidth((int) DisplayMetricsUtils.dp2px(60.0f));
            textView.setHeight((int) DisplayMetricsUtils.dp2px(26.0f));
            textView.setText(this.listStrSub.get(i));
            TextPaint paint = textView.getPaint();
            Intrinsics.checkExpressionValueIsNotNull(paint, "textView.paint");
            paint.setFakeBoldText(true);
            Context mContext = getMContext();
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            textView.setTextSize(14.0f);
            textView.setGravity(17);
            if (tabAt != null) {
                tabAt.setCustomView(textView);
            }
        }
        TabLayout tabLayout4 = this.tab_layout;
        if (tabLayout4 == null) {
            return;
        }
        tabLayout4.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.one.tomato.mvp.ui.post.view.PostHotHomeFragment$initTab$1
            @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                Context mContext2;
                TextView textView2 = (TextView) (tab != null ? tab.getCustomView() : null);
                if (textView2 != null) {
                    textView2.setBackground(null);
                }
                if (textView2 != null) {
                    mContext2 = PostHotHomeFragment.this.getMContext();
                    if (mContext2 != null) {
                        textView2.setTextColor(ContextCompat.getColor(mContext2, R.color.white));
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            }

            @Override // android.support.design.widget.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                Context mContext2;
                Context mContext3;
                TextView textView2 = (TextView) (tab != null ? tab.getCustomView() : null);
                if (textView2 != null) {
                    mContext3 = PostHotHomeFragment.this.getMContext();
                    if (mContext3 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    textView2.setBackground(ContextCompat.getDrawable(mContext3, R.drawable.up_shape_solid_rank_incom_30));
                }
                if (textView2 != null) {
                    mContext2 = PostHotHomeFragment.this.getMContext();
                    if (mContext2 != null) {
                        textView2.setTextColor(ContextCompat.getColor(mContext2, R.color.colorAccent));
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            }
        });
    }
}
