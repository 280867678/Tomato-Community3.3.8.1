package com.one.tomato.mvp.p080ui.search.view;

import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.adapter.TabFragmentAdapter;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.p080ui.papa.view.PapaGridListFragment;
import com.one.tomato.mvp.p080ui.search.view.SearchAfterListFragment;
import com.one.tomato.p085ui.mine.MemberListFragment;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.widget.NoHorScrollViewPager;
import com.one.tomato.widget.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SearchAfterFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.search.view.SearchAfterFragment */
/* loaded from: classes3.dex */
public final class SearchAfterFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private TabFragmentAdapter fragmentAdapter;
    private String searchKey;
    private boolean startSearch;
    private final ArrayList<String> stringList = new ArrayList<>();
    private final ArrayList<Integer> categoryList = new ArrayList<>();
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
        return R.layout.fragment_search_content;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        initTabs();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        if (this.startSearch) {
            startSearch();
        }
    }

    private final void initTabs() {
        this.fragmentList.clear();
        this.stringList.clear();
        if (this.fragmentAdapter == null) {
            this.fragmentAdapter = new TabFragmentAdapter(getChildFragmentManager(), this.fragmentList, this.stringList);
            ((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager)).setAdapter(this.fragmentAdapter);
        }
        this.stringList.add(AppUtil.getString(R.string.post_search_all));
        this.stringList.add(AppUtil.getString(R.string.post_tab_video));
        this.stringList.add(AppUtil.getString(R.string.post_tab_image));
        this.stringList.add(AppUtil.getString(R.string.post_tab_read));
        this.stringList.add(AppUtil.getString(R.string.post_search_papa));
        this.stringList.add(AppUtil.getString(R.string.post_search_member));
        this.categoryList.add(0);
        this.categoryList.add(2);
        this.categoryList.add(1);
        this.categoryList.add(3);
        this.categoryList.add(4);
        this.categoryList.add(-1);
        int size = this.stringList.size();
        int i = 0;
        while (i < size) {
            if (i < 4) {
                String str = i == 0 ? "postSearch0" : "postSearch";
                SearchAfterListFragment.Companion companion = SearchAfterListFragment.Companion;
                Integer num = this.categoryList.get(i);
                Intrinsics.checkExpressionValueIsNotNull(num, "categoryList.get(i)");
                this.fragmentList.add(companion.getInstance(num.intValue(), str));
            } else if (i == 4) {
                this.fragmentList.add(PapaGridListFragment.getInstance("/app/search/article", "papa_search"));
            } else if (i == 5) {
                this.fragmentList.add(MemberListFragment.getInstance("/app/search/member", "search_member_list"));
            }
            i++;
        }
        TabFragmentAdapter tabFragmentAdapter = this.fragmentAdapter;
        if (tabFragmentAdapter != null) {
            tabFragmentAdapter.notifyDataSetChanged();
        }
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setViewPager((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager));
        PagerSlidingTabUtil.setAllTabsValue(getMContext(), (PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout), false);
        ((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager)).setOffscreenPageLimit(this.fragmentList.size());
    }

    public final void startSearch(String searchKey) {
        Intrinsics.checkParameterIsNotNull(searchKey, "searchKey");
        this.startSearch = true;
        this.searchKey = searchKey;
        if (!isViewCreated()) {
            return;
        }
        startSearch();
    }

    private final void startSearch() {
        NoHorScrollViewPager viewpager = (NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager);
        Intrinsics.checkExpressionValueIsNotNull(viewpager, "viewpager");
        viewpager.setCurrentItem(0);
        int size = this.fragmentList.size();
        for (int i = 0; i < size; i++) {
            if (i < 4) {
                Fragment fragment = this.fragmentList.get(i);
                if (fragment == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.search.view.SearchAfterListFragment");
                }
                SearchAfterListFragment searchAfterListFragment = (SearchAfterListFragment) fragment;
                String str = this.searchKey;
                if (str == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("searchKey");
                    throw null;
                }
                searchAfterListFragment.searchFromServer(i, str);
            } else if (i == 4) {
                Fragment fragment2 = this.fragmentList.get(i);
                if (fragment2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.papa.view.PapaGridListFragment");
                }
                PapaGridListFragment papaGridListFragment = (PapaGridListFragment) fragment2;
                String str2 = this.searchKey;
                if (str2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("searchKey");
                    throw null;
                }
                papaGridListFragment.searchFromServer(str2);
            } else if (i != 5) {
                continue;
            } else {
                Fragment fragment3 = this.fragmentList.get(i);
                if (fragment3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.ui.mine.MemberListFragment");
                }
                MemberListFragment memberListFragment = (MemberListFragment) fragment3;
                String str3 = this.searchKey;
                if (str3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("searchKey");
                    throw null;
                }
                memberListFragment.searchFromServer(str3);
            }
        }
    }
}
