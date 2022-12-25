package com.one.tomato.mvp.p080ui.search.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SearchBeforeListFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.search.view.SearchBeforeListFragment */
/* loaded from: classes3.dex */
public final class SearchBeforeListFragment extends NewPostTabListFragment {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private int rankType = 1;
    private int dateType = 1;

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

    /* compiled from: SearchBeforeListFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.search.view.SearchBeforeListFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final synchronized SearchBeforeListFragment getInstance(int i, String businessType) {
            SearchBeforeListFragment searchBeforeListFragment;
            Intrinsics.checkParameterIsNotNull(businessType, "businessType");
            searchBeforeListFragment = new SearchBeforeListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("category", i);
            bundle.putString("business", businessType);
            searchBeforeListFragment.setArguments(bundle);
            return searchBeforeListFragment;
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public void filterSearchView() {
        LinearLayout ll_date = (LinearLayout) _$_findCachedViewById(R$id.ll_date);
        Intrinsics.checkExpressionValueIsNotNull(ll_date, "ll_date");
        ll_date.setVisibility(0);
        ((TextView) _$_findCachedViewById(R$id.day_list)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.search.view.SearchBeforeListFragment$filterSearchView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchBeforeListFragment searchBeforeListFragment = SearchBeforeListFragment.this;
                TextView day_list = (TextView) searchBeforeListFragment._$_findCachedViewById(R$id.day_list);
                Intrinsics.checkExpressionValueIsNotNull(day_list, "day_list");
                TextView week_list = (TextView) SearchBeforeListFragment.this._$_findCachedViewById(R$id.week_list);
                Intrinsics.checkExpressionValueIsNotNull(week_list, "week_list");
                TextView moth_list = (TextView) SearchBeforeListFragment.this._$_findCachedViewById(R$id.moth_list);
                Intrinsics.checkExpressionValueIsNotNull(moth_list, "moth_list");
                searchBeforeListFragment.sortFromServer(1, day_list, week_list, moth_list);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.week_list)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.search.view.SearchBeforeListFragment$filterSearchView$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchBeforeListFragment searchBeforeListFragment = SearchBeforeListFragment.this;
                TextView week_list = (TextView) searchBeforeListFragment._$_findCachedViewById(R$id.week_list);
                Intrinsics.checkExpressionValueIsNotNull(week_list, "week_list");
                TextView day_list = (TextView) SearchBeforeListFragment.this._$_findCachedViewById(R$id.day_list);
                Intrinsics.checkExpressionValueIsNotNull(day_list, "day_list");
                TextView moth_list = (TextView) SearchBeforeListFragment.this._$_findCachedViewById(R$id.moth_list);
                Intrinsics.checkExpressionValueIsNotNull(moth_list, "moth_list");
                searchBeforeListFragment.sortFromServer(2, week_list, day_list, moth_list);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.moth_list)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.search.view.SearchBeforeListFragment$filterSearchView$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchBeforeListFragment searchBeforeListFragment = SearchBeforeListFragment.this;
                TextView moth_list = (TextView) searchBeforeListFragment._$_findCachedViewById(R$id.moth_list);
                Intrinsics.checkExpressionValueIsNotNull(moth_list, "moth_list");
                TextView day_list = (TextView) SearchBeforeListFragment.this._$_findCachedViewById(R$id.day_list);
                Intrinsics.checkExpressionValueIsNotNull(day_list, "day_list");
                TextView week_list = (TextView) SearchBeforeListFragment.this._$_findCachedViewById(R$id.week_list);
                Intrinsics.checkExpressionValueIsNotNull(week_list, "week_list");
                searchBeforeListFragment.sortFromServer(3, moth_list, day_list, week_list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sortFromServer(int i, TextView textView, TextView textView2, TextView textView3) {
        if (this.dateType == i) {
            return;
        }
        this.dateType = i;
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setBackgroundResource(R.drawable.shape_post_search_sort);
        textView2.setTextColor(getResources().getColor(R.color.text_light));
        textView2.setBackgroundResource(R.drawable.common_shape_solid_corner5_grey);
        textView3.setTextColor(getResources().getColor(R.color.text_light));
        textView3.setBackgroundResource(R.drawable.common_shape_solid_corner5_grey);
        refresh();
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public int getRankType() {
        return this.rankType;
    }

    public final void setRankType(int i) {
        this.rankType = i;
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public int getDateType() {
        return this.dateType;
    }
}
