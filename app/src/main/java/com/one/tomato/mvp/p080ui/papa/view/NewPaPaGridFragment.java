package com.one.tomato.mvp.p080ui.papa.view;

import android.os.Bundle;
import android.view.View;
import com.one.tomato.mvp.p080ui.papa.presenter.NewPaPaPresenterMvp;
import com.one.tomato.utils.DBUtil;
import com.tomatolive.library.http.RequestParams;
import java.util.HashMap;
import java.util.LinkedHashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: NewPaPaGridFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaGridFragment */
/* loaded from: classes3.dex */
public final class NewPaPaGridFragment extends NewPaPaTabFragment {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private int personMemberId;

    @Override // com.one.tomato.mvp.p080ui.papa.view.NewPaPaTabFragment, com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.one.tomato.mvp.p080ui.papa.view.NewPaPaTabFragment
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

    @Override // com.one.tomato.mvp.p080ui.papa.view.NewPaPaTabFragment, com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: NewPaPaGridFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaGridFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final NewPaPaGridFragment initInstance(int i, int i2) {
            Bundle bundle = new Bundle();
            NewPaPaGridFragment newPaPaGridFragment = new NewPaPaGridFragment();
            bundle.putInt("papa_tab", i);
            bundle.putInt("personId", i2);
            newPaPaGridFragment.setArguments(bundle);
            return newPaPaGridFragment;
        }
    }

    @Override // com.one.tomato.mvp.p080ui.papa.view.NewPaPaTabFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        super.inintData();
        Bundle arguments = getArguments();
        this.personMemberId = arguments != null ? arguments.getInt("personId") : 0;
        setAddAD(false);
    }

    @Override // com.one.tomato.mvp.p080ui.papa.view.NewPaPaTabFragment
    public void requestData() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("pageNo", Integer.valueOf(getPageNo()));
        linkedHashMap.put(RequestParams.PAGE_SIZE, Integer.valueOf(getPageSize()));
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        linkedHashMap.put("postType", 4);
        linkedHashMap.put("personMemberId", Integer.valueOf(this.personMemberId));
        NewPaPaPresenterMvp newPaPaPresenterMvp = (NewPaPaPresenterMvp) getMPresenter();
        if (newPaPaPresenterMvp != null) {
            newPaPaPresenterMvp.requestArticleList(linkedHashMap);
        }
    }

    public final void setPersonMemberId(int i) {
        this.personMemberId = i;
        setPageNo(1);
        setState(getGET_LIST_REFRESH());
        refresh();
    }
}
