package com.one.tomato.mvp.p080ui.vip.p083ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.VipHistory;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.vip.adapter.VipHistoryAdapter;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VipHistoryActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.vip.ui.VipHistoryActivity */
/* loaded from: classes3.dex */
public final class VipHistoryActivity extends MvpBaseRecyclerViewActivity<IBaseView, MvpBasePresenter<IBaseView>, VipHistoryAdapter, VipHistory> {
    public static final Companion Companion = new Companion(null);

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_vip_history;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    /* compiled from: VipHistoryActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.vip.ui.VipHistoryActivity$Companion */
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
            intent.setClass(context, VipHistoryActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        super.initView();
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.vip_open_history);
        }
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    /* renamed from: createAdapter */
    public VipHistoryAdapter mo6446createAdapter() {
        return new VipHistoryAdapter(this, getRecyclerView());
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

    private final void requestList() {
        ApiImplService.Companion.getApiImplService().requestVipHistory(DBUtil.getMemberId(), getPageNo(), getPageSize()).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).subscribe(new ApiDisposableObserver<ArrayList<VipHistory>>() { // from class: com.one.tomato.mvp.ui.vip.ui.VipHistoryActivity$requestList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<VipHistory> t) {
                int state;
                Intrinsics.checkParameterIsNotNull(t, "t");
                VipHistoryActivity vipHistoryActivity = VipHistoryActivity.this;
                state = vipHistoryActivity.getState();
                vipHistoryActivity.updateData(state, t);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                VipHistoryActivity.this.updateDataFail();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        refresh();
    }
}
