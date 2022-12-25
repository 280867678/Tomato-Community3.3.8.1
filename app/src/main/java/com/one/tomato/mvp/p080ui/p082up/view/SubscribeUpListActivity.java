package com.one.tomato.mvp.p080ui.p082up.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.adapter.SubscribeUpListAdapter;
import com.one.tomato.entity.SubscribeUpList;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SubscribeUpListActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.SubscribeUpListActivity */
/* loaded from: classes3.dex */
public final class SubscribeUpListActivity extends MvpBaseRecyclerViewActivity<IBaseView, MvpBasePresenter<IBaseView>, SubscribeUpListAdapter, SubscribeUpList> {
    public static final Companion Companion = new Companion(null);

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_subscribe_up_list;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    /* compiled from: SubscribeUpListActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.up.view.SubscribeUpListActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, SubscribeUpListActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        super.initView();
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.my_subscribe_up);
        }
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    /* renamed from: createAdapter */
    public SubscribeUpListAdapter mo6446createAdapter() {
        return new SubscribeUpListAdapter(this, getRecyclerView());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void refresh() {
        setState(getGET_LIST_REFRESH());
        setPageNo(1);
        requestList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void loadMore() {
        setState(getGET_LIST_LOAD());
        requestList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        refresh();
    }

    public final void requestList() {
        ApiImplService.Companion.getApiImplService().requestSubscribeUpList(DBUtil.getMemberId(), getPageNo(), getPageSize()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<SubscribeUpList>>() { // from class: com.one.tomato.mvp.ui.up.view.SubscribeUpListActivity$requestList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<SubscribeUpList> list) {
                int state;
                Intrinsics.checkParameterIsNotNull(list, "list");
                SubscribeUpListActivity subscribeUpListActivity = SubscribeUpListActivity.this;
                state = subscribeUpListActivity.getState();
                subscribeUpListActivity.updateData(state, list);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                SubscribeUpListActivity.this.updateDataFail();
            }
        });
    }
}
