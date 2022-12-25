package com.one.tomato.mvp.p080ui.promotion.p081ui;

import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.adapter.TabFragmentAdapter;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.widget.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SpreadInComeFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadInComeFragment */
/* loaded from: classes3.dex */
public final class SpreadInComeFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private TabFragmentAdapter fragmentAdapter;
    private final ArrayList<String> stringList = new ArrayList<>();
    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

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
        return R.layout.fragment_spread_income;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: SpreadInComeFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadInComeFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final SpreadInComeFragment getInstance() {
            return new SpreadInComeFragment();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, android.support.p002v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        return super.onCreateView(inflater, viewGroup, bundle);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        initTabs();
    }

    private final void initTabs() {
        this.stringList.add(AppUtil.getString(R.string.spread_income_top));
        this.stringList.add(AppUtil.getString(R.string.spread_income_friend));
        this.fragmentList.add(SpreadIncomeTop50Fragment.Companion.getInstance());
        this.fragmentList.add(SpreadIncomeFriendFragment.Companion.getInstance());
        this.fragmentAdapter = new TabFragmentAdapter(getChildFragmentManager(), this.fragmentList, this.stringList);
        ((ViewPager) _$_findCachedViewById(R$id.viewpager)).setAdapter(this.fragmentAdapter);
        TabFragmentAdapter tabFragmentAdapter = this.fragmentAdapter;
        if (tabFragmentAdapter != null) {
            tabFragmentAdapter.notifyDataSetChanged();
        }
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setViewPager((ViewPager) _$_findCachedViewById(R$id.viewpager));
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setTextColor(getResources().getColor(R.color.text_light));
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setSelectedTextColor(getResources().getColor(R.color.colorAccent));
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setCustomIndicatorWith(true, 40);
        ((ViewPager) _$_findCachedViewById(R$id.viewpager)).setOffscreenPageLimit(this.fragmentList.size());
    }
}
