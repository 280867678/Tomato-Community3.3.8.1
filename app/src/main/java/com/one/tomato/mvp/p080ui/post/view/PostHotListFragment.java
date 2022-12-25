package com.one.tomato.mvp.p080ui.post.view;

import android.os.Bundle;
import android.view.View;
import com.one.tomato.entity.PostList;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostHotListFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PostHotListFragment */
/* loaded from: classes3.dex */
public final class PostHotListFragment extends NewPostTabListFragment {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private int isNullNum;
    private int postRankListType = 1;
    private String rankUrl = "";

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

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment, com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
    }

    /* compiled from: PostHotListFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.PostHotListFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final synchronized PostHotListFragment getInstance(int i, String businessType, String url, int i2) {
            PostHotListFragment postHotListFragment;
            Intrinsics.checkParameterIsNotNull(businessType, "businessType");
            Intrinsics.checkParameterIsNotNull(url, "url");
            postHotListFragment = new PostHotListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("category", i);
            bundle.putString("business", businessType);
            bundle.putString("post_rank_url", url);
            bundle.putInt("rank_type", i2);
            postHotListFragment.setArguments(bundle);
            return postHotListFragment;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        String str;
        super.initView();
        Bundle arguments = getArguments();
        this.postRankListType = arguments != null ? arguments.getInt("rank_type") : 1;
        Bundle arguments2 = getArguments();
        if (arguments2 == null || (str = arguments2.getString("post_rank_url")) == null) {
            str = "/app/articleRank/hotValueList";
        }
        this.rankUrl = str;
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public int postRankType() {
        return this.postRankListType;
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public String postRankUrl() {
        return this.rankUrl;
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment, com.one.tomato.mvp.p080ui.post.impl.IPostTabListContact$IPostTabListView
    public void handlerPostListData(ArrayList<PostList> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            int i = this.isNullNum;
            if (i == 2) {
                updateData(arrayList);
                this.isNullNum = 0;
                return;
            }
            this.isNullNum = i + 1;
            setPageNo(getPageNo() + 1);
            loadMore();
            return;
        }
        this.isNullNum = 0;
        updateData(arrayList);
    }
}
