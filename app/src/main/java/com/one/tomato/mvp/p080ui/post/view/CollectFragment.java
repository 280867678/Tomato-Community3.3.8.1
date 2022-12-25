package com.one.tomato.mvp.p080ui.post.view;

import android.os.Bundle;
import android.view.View;
import com.one.tomato.mvp.p080ui.post.adapter.NewPostTabListAdapter;
import com.one.tomato.mvp.p080ui.post.presenter.NewPostTabListPresenter;
import com.tomatolive.library.http.RequestParams;
import java.util.HashMap;
import java.util.LinkedHashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CollectFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.CollectFragment */
/* loaded from: classes3.dex */
public final class CollectFragment extends NewPostTabListFragment {
    private HashMap _$_findViewCache;
    private int memberId;

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

    public final synchronized CollectFragment getInstance(int i, String businessType, int i2) {
        CollectFragment collectFragment;
        Intrinsics.checkParameterIsNotNull(businessType, "businessType");
        collectFragment = new CollectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("category", i);
        bundle.putString("business", businessType);
        bundle.putInt("person_id", i2);
        collectFragment.setArguments(bundle);
        return collectFragment;
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        Bundle arguments = getArguments();
        setCategory(arguments != null ? arguments.getInt("category") : -1);
        setBusinessType(arguments != null ? arguments.getString("business") : null);
        this.memberId = arguments != null ? arguments.getInt("person_id") : 0;
        if (Intrinsics.areEqual(getBusinessType(), "collect")) {
            setUserVisibleHint(true);
        }
        NewPostTabListAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setBusinessType(getBusinessType());
        }
        autoRecycylerViewPlay();
        registerRxBus();
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public void requestType() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("pageNo", Integer.valueOf(getPageNo()));
        linkedHashMap.put(RequestParams.PAGE_SIZE, Integer.valueOf(getPageSize()));
        linkedHashMap.put("memberId", Integer.valueOf(this.memberId));
        NewPostTabListPresenter newPostTabListPresenter = (NewPostTabListPresenter) getMPresenter();
        if (newPostTabListPresenter != null) {
            newPostTabListPresenter.requestCollectPost(linkedHashMap);
        }
    }
}
