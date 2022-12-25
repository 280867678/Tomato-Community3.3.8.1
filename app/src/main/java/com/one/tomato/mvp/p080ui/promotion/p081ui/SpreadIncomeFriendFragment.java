package com.one.tomato.mvp.p080ui.promotion.p081ui;

import android.os.Bundle;
import android.support.p002v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PromotionFanyong;
import com.one.tomato.entity.PromotionFriend;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.promotion.adapter.SpreadIncomeFriendAdapter;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SpreadIncomeFriendFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadIncomeFriendFragment */
/* loaded from: classes3.dex */
public final class SpreadIncomeFriendFragment extends MvpBaseRecyclerViewFragment<IBaseView, MvpBasePresenter<IBaseView>, SpreadIncomeFriendAdapter, PromotionFriend> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
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
        return R.layout.fragment_spread_income_item;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: SpreadIncomeFriendFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadIncomeFriendFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final SpreadIncomeFriendFragment getInstance() {
            return new SpreadIncomeFriendFragment();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        FragmentActivity activity = getActivity();
        if (activity == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity");
        }
        PromotionFanyong promotionFanyong = ((SpreadInfoActivity) activity).getPromotionFanyong();
        SpreadIncomeFriendAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setPromotionFanyong(promotionFanyong);
        }
        ((TextView) _$_findCachedViewById(R$id.tv_title)).setText(R.string.spread_income_friend_level);
        ((TextView) _$_findCachedViewById(R$id.tv_name)).setText(R.string.spread_income_friend_account);
        ((TextView) _$_findCachedViewById(R$id.tv_money)).setText(R.string.spread_income_friend_all_money);
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
    public SpreadIncomeFriendAdapter mo6434createAdapter() {
        return new SpreadIncomeFriendAdapter(getMContext(), getRecyclerView());
    }

    private final void requestList() {
        ApiImplService.Companion.getApiImplService().requestPromotionFriend(DBUtil.getMemberId(), getPageNo(), getPageSize()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<PromotionFriend>>() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadIncomeFriendFragment$requestList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<PromotionFriend> arrayList) {
                SpreadIncomeFriendFragment.this.updateData(arrayList);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                SpreadIncomeFriendFragment.this.updateDataFail();
            }
        });
    }
}
