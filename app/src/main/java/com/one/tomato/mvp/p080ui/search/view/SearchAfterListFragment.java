package com.one.tomato.mvp.p080ui.search.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.adapter.NewPostTabListAdapter;
import com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SearchAfterListFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.search.view.SearchAfterListFragment */
/* loaded from: classes3.dex */
public final class SearchAfterListFragment extends NewPostTabListFragment {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private String originBusinessType;
    private String searchKey;
    private String searchSort = AopConstants.TIME_KEY;

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment, com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
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

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment, com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: SearchAfterListFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.search.view.SearchAfterListFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final synchronized SearchAfterListFragment getInstance(int i, String businessType) {
            SearchAfterListFragment searchAfterListFragment;
            Intrinsics.checkParameterIsNotNull(businessType, "businessType");
            searchAfterListFragment = new SearchAfterListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("category", i);
            bundle.putString("business", businessType);
            searchAfterListFragment.setArguments(bundle);
            return searchAfterListFragment;
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        super.inintData();
        this.originBusinessType = getBusinessType();
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public void addHeaderView() {
        List<PostList> data;
        NewPostTabListAdapter adapter = getAdapter();
        Boolean valueOf = (adapter == null || (data = adapter.getData()) == null) ? null : Boolean.valueOf(data.isEmpty());
        if (valueOf == null) {
            Intrinsics.throwNpe();
            throw null;
        } else if (valueOf.booleanValue()) {
            LinearLayout search_header_empty = (LinearLayout) _$_findCachedViewById(R$id.search_header_empty);
            Intrinsics.checkExpressionValueIsNotNull(search_header_empty, "search_header_empty");
            search_header_empty.setVisibility(0);
            LinearLayout ll_sort = (LinearLayout) _$_findCachedViewById(R$id.ll_sort);
            Intrinsics.checkExpressionValueIsNotNull(ll_sort, "ll_sort");
            ll_sort.setVisibility(8);
        } else {
            LinearLayout ll_sort2 = (LinearLayout) _$_findCachedViewById(R$id.ll_sort);
            Intrinsics.checkExpressionValueIsNotNull(ll_sort2, "ll_sort");
            ll_sort2.setVisibility(0);
            ((TextView) _$_findCachedViewById(R$id.text_time)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.search.view.SearchAfterListFragment$addHeaderView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SearchAfterListFragment searchAfterListFragment = SearchAfterListFragment.this;
                    TextView text_time = (TextView) searchAfterListFragment._$_findCachedViewById(R$id.text_time);
                    Intrinsics.checkExpressionValueIsNotNull(text_time, "text_time");
                    TextView text_hot = (TextView) SearchAfterListFragment.this._$_findCachedViewById(R$id.text_hot);
                    Intrinsics.checkExpressionValueIsNotNull(text_hot, "text_hot");
                    searchAfterListFragment.sortFromServer(AopConstants.TIME_KEY, text_time, text_hot);
                }
            });
            ((TextView) _$_findCachedViewById(R$id.text_hot)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.search.view.SearchAfterListFragment$addHeaderView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SearchAfterListFragment searchAfterListFragment = SearchAfterListFragment.this;
                    TextView text_hot = (TextView) searchAfterListFragment._$_findCachedViewById(R$id.text_hot);
                    Intrinsics.checkExpressionValueIsNotNull(text_hot, "text_hot");
                    TextView text_time = (TextView) SearchAfterListFragment.this._$_findCachedViewById(R$id.text_time);
                    Intrinsics.checkExpressionValueIsNotNull(text_time, "text_time");
                    searchAfterListFragment.sortFromServer("score", text_hot, text_time);
                }
            });
            LinearLayout search_header_empty2 = (LinearLayout) _$_findCachedViewById(R$id.search_header_empty);
            Intrinsics.checkExpressionValueIsNotNull(search_header_empty2, "search_header_empty");
            search_header_empty2.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sortFromServer(String str, TextView textView, TextView textView2) {
        if (Intrinsics.areEqual(this.searchSort, str)) {
            return;
        }
        this.searchSort = str;
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setBackgroundResource(R.drawable.shape_post_search_sort);
        textView2.setTextColor(getResources().getColor(R.color.text_light));
        textView2.setBackgroundResource(R.drawable.common_shape_solid_corner5_grey);
        refresh();
    }

    public final void clear() {
        List<PostList> data;
        if (getAdapter() != null) {
            NewPostTabListAdapter adapter = getAdapter();
            if (adapter != null && (data = adapter.getData()) != null) {
                data.clear();
            }
            NewPostTabListAdapter adapter2 = getAdapter();
            if (adapter2 != null) {
                adapter2.notifyDataSetChanged();
            }
        }
        if (isViewCreated()) {
            this.searchSort = AopConstants.TIME_KEY;
            ((TextView) _$_findCachedViewById(R$id.text_time)).setTextColor(getResources().getColor(R.color.colorAccent));
            ((TextView) _$_findCachedViewById(R$id.text_time)).setBackgroundResource(R.drawable.shape_post_search_sort);
            ((TextView) _$_findCachedViewById(R$id.text_hot)).setTextColor(getResources().getColor(R.color.text_light));
            ((TextView) _$_findCachedViewById(R$id.text_hot)).setBackgroundResource(R.drawable.common_shape_solid_corner5_grey);
        }
    }

    public final void searchFromServer(int i, String key) {
        NewPostTabListAdapter adapter;
        SmartRefreshLayout refreshLayout;
        Intrinsics.checkParameterIsNotNull(key, "key");
        clear();
        if (getRefreshLayout() != null && (refreshLayout = getRefreshLayout()) != null) {
            refreshLayout.mo6481finishRefresh();
        }
        if (getAdapter() != null && (adapter = getAdapter()) != null) {
            adapter.loadMoreEnd();
        }
        this.searchKey = key;
        setLazyLoad(false);
        setBusinessType(this.originBusinessType);
        if (i == 0) {
            setUserVisibleHint(true);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public String getSeachKey() {
        return this.searchKey;
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public String getSearchSort() {
        return this.searchSort;
    }
}
