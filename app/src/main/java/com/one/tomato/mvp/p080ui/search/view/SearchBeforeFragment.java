package com.one.tomato.mvp.p080ui.search.view;

import android.os.Bundle;
import android.support.p002v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.google.gson.reflect.TypeToken;
import com.one.tomato.R$id;
import com.one.tomato.adapter.PostSearchTagAdapter;
import com.one.tomato.adapter.TabFragmentAdapter;
import com.one.tomato.constants.PreferencesConstant;
import com.one.tomato.entity.p079db.PostHotSearch;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.search.view.SearchBeforeFragment;
import com.one.tomato.mvp.p080ui.search.view.SearchBeforeListFragment;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.widget.NoHorScrollViewPager;
import com.one.tomato.widget.PagerSlidingTabStrip;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SearchBeforeFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.search.view.SearchBeforeFragment */
/* loaded from: classes3.dex */
public final class SearchBeforeFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private TabFragmentAdapter fragmentAdapter;
    private PostSearchTagAdapter historyAdapter;
    private PostSearchTagAdapter hotAdapter;
    private OnSearchListener searchListener;
    private final ArrayList<String> historyStringList = new ArrayList<>();
    private final ArrayList<PostHotSearch> hotList = new ArrayList<>();
    private final ArrayList<String> hotStringList = new ArrayList<>();
    private final ArrayList<String> stringList = new ArrayList<>();
    private final ArrayList<Integer> categoryList = new ArrayList<>();
    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

    /* compiled from: SearchBeforeFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.search.view.SearchBeforeFragment$OnSearchListener */
    /* loaded from: classes3.dex */
    public interface OnSearchListener {
        void search(String str);
    }

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
        return R.layout.fragment_search;
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

    public final OnSearchListener getSearchListener() {
        return this.searchListener;
    }

    public final void setSearchListener(OnSearchListener onSearchListener) {
        this.searchListener = onSearchListener;
    }

    public final void setOnSearchListener(OnSearchListener searchListener) {
        Intrinsics.checkParameterIsNotNull(searchListener, "searchListener");
        this.searchListener = searchListener;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        initHistoryAdapter();
        initHotAdapter();
        initTabs();
        ((ImageView) _$_findCachedViewById(R$id.iv_clear_history)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.search.view.SearchBeforeFragment$initView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchBeforeFragment.this.clearHistory();
            }
        });
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        initHistoryData();
        this.hotList.addAll(DBUtil.getHotSearchList());
        initHotData();
        ((TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_hot)).postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.search.view.SearchBeforeFragment$inintData$1
            @Override // java.lang.Runnable
            public final void run() {
                SearchBeforeFragment.this.requestHotData();
            }
        }, 1000L);
    }

    private final void initHistoryAdapter() {
        this.historyAdapter = new PostSearchTagAdapter(getMContext(), (TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_hot), this.historyStringList);
        ((TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_history)).setAdapter(this.historyAdapter);
        ((TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_history)).setOnTagClickListener(new TagFlowLayout.OnTagClickListener() { // from class: com.one.tomato.mvp.ui.search.view.SearchBeforeFragment$initHistoryAdapter$1
            @Override // com.zhy.view.flowlayout.TagFlowLayout.OnTagClickListener
            public final boolean onTagClick(View view, int i, FlowLayout flowLayout) {
                ArrayList arrayList;
                ArrayList arrayList2;
                arrayList = SearchBeforeFragment.this.historyStringList;
                if (!arrayList.isEmpty()) {
                    arrayList2 = SearchBeforeFragment.this.historyStringList;
                    Object obj = arrayList2.get(i);
                    Intrinsics.checkExpressionValueIsNotNull(obj, "historyStringList[position]");
                    String str = (String) obj;
                    SearchBeforeFragment.this.addHistory(str);
                    SearchBeforeFragment.OnSearchListener searchListener = SearchBeforeFragment.this.getSearchListener();
                    if (searchListener == null) {
                        return false;
                    }
                    searchListener.search(str);
                    return false;
                }
                return false;
            }
        });
    }

    private final void initHistoryData() {
        List list = (List) BaseApplication.getGson().fromJson(PreferencesUtil.getInstance().getString(PreferencesConstant.HISTORY_POST), new TypeToken<ArrayList<String>>() { // from class: com.one.tomato.mvp.ui.search.view.SearchBeforeFragment$initHistoryData$list$1
        }.getType());
        if (list != null && list.size() > 0) {
            this.historyStringList.addAll(list);
            RelativeLayout rl_search_history = (RelativeLayout) _$_findCachedViewById(R$id.rl_search_history);
            Intrinsics.checkExpressionValueIsNotNull(rl_search_history, "rl_search_history");
            rl_search_history.setVisibility(0);
            TagFlowLayout flowlayout_history = (TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_history);
            Intrinsics.checkExpressionValueIsNotNull(flowlayout_history, "flowlayout_history");
            flowlayout_history.setVisibility(0);
            PostSearchTagAdapter postSearchTagAdapter = this.historyAdapter;
            if (postSearchTagAdapter == null) {
                return;
            }
            postSearchTagAdapter.notifyDataChanged();
            return;
        }
        RelativeLayout rl_search_history2 = (RelativeLayout) _$_findCachedViewById(R$id.rl_search_history);
        Intrinsics.checkExpressionValueIsNotNull(rl_search_history2, "rl_search_history");
        rl_search_history2.setVisibility(8);
        TagFlowLayout flowlayout_history2 = (TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_history);
        Intrinsics.checkExpressionValueIsNotNull(flowlayout_history2, "flowlayout_history");
        flowlayout_history2.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clearHistory() {
        this.historyStringList.clear();
        PreferencesUtil.getInstance().putString(PreferencesConstant.HISTORY_POST, "");
        PostSearchTagAdapter postSearchTagAdapter = this.historyAdapter;
        if (postSearchTagAdapter != null) {
            postSearchTagAdapter.notifyDataChanged();
        }
        RelativeLayout rl_search_history = (RelativeLayout) _$_findCachedViewById(R$id.rl_search_history);
        Intrinsics.checkExpressionValueIsNotNull(rl_search_history, "rl_search_history");
        rl_search_history.setVisibility(8);
        TagFlowLayout flowlayout_history = (TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_history);
        Intrinsics.checkExpressionValueIsNotNull(flowlayout_history, "flowlayout_history");
        flowlayout_history.setVisibility(8);
    }

    public final void addHistory(String searchKey) {
        Intrinsics.checkParameterIsNotNull(searchKey, "searchKey");
        if (this.historyStringList.contains(searchKey)) {
            this.historyStringList.remove(searchKey);
        }
        this.historyStringList.add(0, searchKey);
        if (this.historyStringList.size() > 10) {
            this.historyStringList.remove(10);
        }
        RelativeLayout rl_search_history = (RelativeLayout) _$_findCachedViewById(R$id.rl_search_history);
        Intrinsics.checkExpressionValueIsNotNull(rl_search_history, "rl_search_history");
        rl_search_history.setVisibility(0);
        TagFlowLayout flowlayout_history = (TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_history);
        Intrinsics.checkExpressionValueIsNotNull(flowlayout_history, "flowlayout_history");
        flowlayout_history.setVisibility(0);
        PostSearchTagAdapter postSearchTagAdapter = this.historyAdapter;
        if (postSearchTagAdapter != null) {
            postSearchTagAdapter.notifyDataChanged();
        }
        PreferencesUtil.getInstance().putString(PreferencesConstant.HISTORY_POST, BaseApplication.getGson().toJson(this.historyStringList));
    }

    private final void initHotAdapter() {
        this.hotAdapter = new PostSearchTagAdapter(getMContext(), (TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_hot), this.hotStringList);
        ((TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_hot)).setAdapter(this.hotAdapter);
        ((TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_hot)).setOnTagClickListener(new TagFlowLayout.OnTagClickListener() { // from class: com.one.tomato.mvp.ui.search.view.SearchBeforeFragment$initHotAdapter$1
            @Override // com.zhy.view.flowlayout.TagFlowLayout.OnTagClickListener
            public final boolean onTagClick(View view, int i, FlowLayout flowLayout) {
                ArrayList arrayList;
                ArrayList arrayList2;
                arrayList = SearchBeforeFragment.this.hotStringList;
                if (!arrayList.isEmpty()) {
                    arrayList2 = SearchBeforeFragment.this.hotStringList;
                    Object obj = arrayList2.get(i);
                    Intrinsics.checkExpressionValueIsNotNull(obj, "hotStringList[position]");
                    String str = (String) obj;
                    SearchBeforeFragment.this.addHistory(str);
                    SearchBeforeFragment.OnSearchListener searchListener = SearchBeforeFragment.this.getSearchListener();
                    if (searchListener == null) {
                        return false;
                    }
                    searchListener.search(str);
                    return false;
                }
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestHotData() {
        ApiImplService.Companion.getApiImplService().requestHotSearch(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxFragment) this)).subscribe(new ApiDisposableObserver<ArrayList<PostHotSearch>>() { // from class: com.one.tomato.mvp.ui.search.view.SearchBeforeFragment$requestHotData$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PostHotSearch> arrayList) {
                ArrayList arrayList2;
                ArrayList arrayList3;
                ArrayList arrayList4;
                ArrayList arrayList5;
                if (arrayList != null) {
                    arrayList2 = SearchBeforeFragment.this.hotList;
                    if (arrayList2.size() == arrayList.size()) {
                        arrayList5 = SearchBeforeFragment.this.hotList;
                        if (arrayList5.containsAll(arrayList)) {
                            return;
                        }
                    }
                    arrayList3 = SearchBeforeFragment.this.hotList;
                    arrayList3.clear();
                    arrayList4 = SearchBeforeFragment.this.hotList;
                    arrayList4.addAll(arrayList);
                    SearchBeforeFragment.this.initHotData();
                    DBUtil.saveHotSearchList(arrayList);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initHotData() {
        ArrayList<PostHotSearch> arrayList = this.hotList;
        if (arrayList != null && arrayList.size() > 0) {
            this.hotStringList.clear();
            Iterator<PostHotSearch> it2 = this.hotList.iterator();
            while (it2.hasNext()) {
                PostHotSearch history = it2.next();
                ArrayList<String> arrayList2 = this.hotStringList;
                Intrinsics.checkExpressionValueIsNotNull(history, "history");
                arrayList2.add(history.getHotWord());
            }
            PostSearchTagAdapter postSearchTagAdapter = this.hotAdapter;
            if (postSearchTagAdapter != null) {
                postSearchTagAdapter.notifyDataChanged();
            }
            TextView tv_hot_search_hint = (TextView) _$_findCachedViewById(R$id.tv_hot_search_hint);
            Intrinsics.checkExpressionValueIsNotNull(tv_hot_search_hint, "tv_hot_search_hint");
            tv_hot_search_hint.setVisibility(0);
            TagFlowLayout flowlayout_hot = (TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_hot);
            Intrinsics.checkExpressionValueIsNotNull(flowlayout_hot, "flowlayout_hot");
            flowlayout_hot.setVisibility(0);
            return;
        }
        TextView tv_hot_search_hint2 = (TextView) _$_findCachedViewById(R$id.tv_hot_search_hint);
        Intrinsics.checkExpressionValueIsNotNull(tv_hot_search_hint2, "tv_hot_search_hint");
        tv_hot_search_hint2.setVisibility(8);
        TagFlowLayout flowlayout_hot2 = (TagFlowLayout) _$_findCachedViewById(R$id.flowlayout_hot);
        Intrinsics.checkExpressionValueIsNotNull(flowlayout_hot2, "flowlayout_hot");
        flowlayout_hot2.setVisibility(8);
    }

    private final void initTabs() {
        this.fragmentList.clear();
        this.stringList.clear();
        if (this.fragmentAdapter == null) {
            this.fragmentAdapter = new TabFragmentAdapter(getChildFragmentManager(), this.fragmentList, this.stringList);
            ((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager)).setAdapter(this.fragmentAdapter);
        }
        this.stringList.add(AppUtil.getString(R.string.post_search_renqi));
        this.stringList.add(AppUtil.getString(R.string.post_search_liulan));
        this.stringList.add(AppUtil.getString(R.string.post_search_dianzan));
        this.stringList.add(AppUtil.getString(R.string.post_search_shoucang));
        this.categoryList.add(0);
        this.categoryList.add(0);
        this.categoryList.add(0);
        this.categoryList.add(0);
        int size = this.stringList.size();
        int i = 0;
        while (i < size) {
            String str = i == 0 ? "postRecommend0" : "postRecommend";
            SearchBeforeListFragment.Companion companion = SearchBeforeListFragment.Companion;
            Integer num = this.categoryList.get(i);
            Intrinsics.checkExpressionValueIsNotNull(num, "categoryList.get(i)");
            SearchBeforeListFragment companion2 = companion.getInstance(num.intValue(), str);
            i++;
            companion2.setRankType(i);
            this.fragmentList.add(companion2);
        }
        TabFragmentAdapter tabFragmentAdapter = this.fragmentAdapter;
        if (tabFragmentAdapter != null) {
            tabFragmentAdapter.notifyDataSetChanged();
        }
        ((PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout)).setViewPager((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager));
        PagerSlidingTabUtil.setAllTabsValue(getMContext(), (PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_layout), false);
        ((NoHorScrollViewPager) _$_findCachedViewById(R$id.viewpager)).setOffscreenPageLimit(this.fragmentList.size());
    }
}
