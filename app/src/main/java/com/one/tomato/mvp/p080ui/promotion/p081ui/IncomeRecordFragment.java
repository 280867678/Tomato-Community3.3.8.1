package com.one.tomato.mvp.p080ui.promotion.p081ui;

import android.os.Bundle;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.PromotionIncomeRecord;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.p080ui.promotion.adapter.IncomeRecordAdapter;
import com.one.tomato.thirdpart.promotion.PromotionUtil;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IncomeRecordFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.IncomeRecordFragment */
/* loaded from: classes3.dex */
public final class IncomeRecordFragment extends MvpBaseRecyclerViewFragment<IBaseView, MvpBasePresenter<IBaseView>, IncomeRecordAdapter, PromotionIncomeRecord.C2523Record> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.fragment_income_record;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: IncomeRecordFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.promotion.ui.IncomeRecordFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final IncomeRecordFragment getInstance() {
            return new IncomeRecordFragment();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        setUserVisibleHint(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        refresh();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void refresh() {
        setState(getGET_LIST_REFRESH());
        setPageNo(1);
        requestList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void loadMore() {
        setState(getGET_LIST_LOAD());
        requestList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        refresh();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    /* renamed from: createAdapter */
    public IncomeRecordAdapter mo6434createAdapter() {
        return new IncomeRecordAdapter(getMContext(), getRecyclerView());
    }

    private final void requestList() {
        PromotionUtil.requestToken(new IncomeRecordFragment$requestList$1(this));
    }
}
