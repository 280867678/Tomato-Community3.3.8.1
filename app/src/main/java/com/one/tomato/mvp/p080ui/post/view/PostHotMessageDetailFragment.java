package com.one.tomato.mvp.p080ui.post.view;

import android.os.Bundle;
import android.view.View;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostHotMessageDetailFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PostHotMessageDetailFragment */
/* loaded from: classes3.dex */
public final class PostHotMessageDetailFragment extends NewPostTabListFragment {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;

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

    /* compiled from: PostHotMessageDetailFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.PostHotMessageDetailFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final PostHotMessageDetailFragment getInstance(int i, String businessType, String str) {
            Intrinsics.checkParameterIsNotNull(businessType, "businessType");
            PostHotMessageDetailFragment postHotMessageDetailFragment = new PostHotMessageDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("category", i);
            bundle.putString("business", businessType);
            bundle.putString("event_id", str);
            postHotMessageDetailFragment.setArguments(bundle);
            return postHotMessageDetailFragment;
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public String getEventId() {
        String string;
        Bundle arguments = getArguments();
        return (arguments == null || (string = arguments.getString("event_id", "0")) == null) ? "0" : string;
    }
}
