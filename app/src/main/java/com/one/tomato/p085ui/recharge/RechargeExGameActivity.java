package com.one.tomato.p085ui.recharge;

import android.content.Context;
import android.content.Intent;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.adapter.RechargeExGameAdapter;
import com.one.tomato.dialog.RechargeExGameDialog;
import com.one.tomato.entity.RechargeAccount;
import com.one.tomato.entity.RechargeExGame;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RechargeExGameActivity.kt */
/* renamed from: com.one.tomato.ui.recharge.RechargeExGameActivity */
/* loaded from: classes3.dex */
public final class RechargeExGameActivity extends MvpBaseRecyclerViewActivity<IBaseView, MvpBasePresenter<IBaseView>, RechargeExGameAdapter, RechargeExGame> implements RechargeExGameDialog.RechargeExGameListener {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private RechargeAccount rechargeAccount;

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_recharge_ex_game;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void loadMore() {
    }

    /* compiled from: RechargeExGameActivity.kt */
    /* renamed from: com.one.tomato.ui.recharge.RechargeExGameActivity$Companion */
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
            intent.setClass(context, RechargeExGameActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        super.initView();
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.recharge_ex);
        }
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setVisibility(0);
        }
        TextView rightTV2 = getRightTV();
        if (rightTV2 != null) {
            rightTV2.setText(R.string.income_sub_title);
        }
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void initRecyclerView() {
        super.initRecyclerView();
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(getMContext(), 2));
        }
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getMContext());
        builder.setColorResource(R.color.transparent);
        builder.setHorizontalSpan(R.dimen.dimen_9);
        builder.setVerticalSpan(R.dimen.dimen_8);
        GridItemDecoration build = builder.build();
        RecyclerView recyclerView2 = getRecyclerView();
        if (recyclerView2 != null) {
            recyclerView2.addItemDecoration(build);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        addListener();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    /* renamed from: createAdapter */
    public RechargeExGameAdapter mo6446createAdapter() {
        return new RechargeExGameAdapter(this, getRecyclerView());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void refresh() {
        requestData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        requestData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getRechargeAccountBalance(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestData() {
        getRechargeAccountBalance(false);
        requestList();
    }

    private final void addListener() {
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeExGameActivity$addListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    mContext = RechargeExGameActivity.this.getMContext();
                    if (mContext != null) {
                        RechargeRecordActivity.startActivity(mContext, 1);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
        ((TextView) _$_findCachedViewById(R$id.tv_recycle)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeExGameActivity$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RechargeExGameActivity.this.requestRecycleAll();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_recharge)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeExGameActivity$addListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = RechargeExGameActivity.this.getMContext();
                RechargeActivity.startActivity(mContext);
            }
        });
        RechargeExGameAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setOnRechargeExTypeListener(new RechargeExGameAdapter.OnRechargeExTypeListener() { // from class: com.one.tomato.ui.recharge.RechargeExGameActivity$addListener$4
                @Override // com.one.tomato.adapter.RechargeExGameAdapter.OnRechargeExTypeListener
                public void onClickItem(RechargeExGame itemData, int i) {
                    Intrinsics.checkParameterIsNotNull(itemData, "itemData");
                    RechargeExGameActivity.this.showRechargeExGameDialog(itemData, i);
                }
            });
        }
    }

    public final void getRechargeAccountBalance(boolean z) {
        if (z) {
            showWaitingDialog();
        }
        ApiImplService.Companion.getApiImplService().requestRechargeAccount(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<RechargeAccount>() { // from class: com.one.tomato.ui.recharge.RechargeExGameActivity$getRechargeAccountBalance$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(RechargeAccount bean) {
                RechargeAccount rechargeAccount;
                RechargeExGameAdapter adapter;
                RechargeAccount rechargeAccount2;
                Intrinsics.checkParameterIsNotNull(bean, "bean");
                RechargeExGameActivity.this.hideWaitingDialog();
                RechargeExGameActivity.this.rechargeAccount = bean;
                TextView textView = (TextView) RechargeExGameActivity.this._$_findCachedViewById(R$id.tv_balance);
                rechargeAccount = RechargeExGameActivity.this.rechargeAccount;
                if (rechargeAccount != null) {
                    textView.setText(FormatUtil.formatTomato2RMB(rechargeAccount.balance));
                    adapter = RechargeExGameActivity.this.getAdapter();
                    if (adapter == null) {
                        return;
                    }
                    rechargeAccount2 = RechargeExGameActivity.this.rechargeAccount;
                    adapter.setRechargeAccount(rechargeAccount2);
                    return;
                }
                Intrinsics.throwNpe();
                throw null;
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                RechargeExGameActivity.this.hideWaitingDialog();
            }
        });
    }

    public final void requestList() {
        ApiImplService.Companion.getApiImplService().requestExGameList(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<RechargeExGame>>() { // from class: com.one.tomato.ui.recharge.RechargeExGameActivity$requestList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<RechargeExGame> rechargeExGameArrayList) {
                SmartRefreshLayout refreshLayout;
                RechargeExGameAdapter adapter;
                RechargeExGameAdapter adapter2;
                Intrinsics.checkParameterIsNotNull(rechargeExGameArrayList, "rechargeExGameArrayList");
                refreshLayout = RechargeExGameActivity.this.getRefreshLayout();
                if (refreshLayout != null) {
                    refreshLayout.mo6481finishRefresh();
                }
                adapter = RechargeExGameActivity.this.getAdapter();
                if (adapter != null) {
                    adapter.setNewData(rechargeExGameArrayList);
                }
                adapter2 = RechargeExGameActivity.this.getAdapter();
                if (adapter2 != null) {
                    adapter2.setEnableLoadMore(false);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                RechargeExGameActivity.this.updateDataFail();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestRecycleAll() {
        showWaitingDialog();
        ApiImplService.Companion.getApiImplService().requestRecycleAll(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.ui.recharge.RechargeExGameActivity$requestRecycleAll$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                RechargeExGameActivity.this.requestData();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                RechargeExGameActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showRechargeExGameDialog(RechargeExGame rechargeExGame, int i) {
        new RechargeExGameDialog(this, 1, this.rechargeAccount, rechargeExGame, i, this);
    }

    @Override // com.one.tomato.dialog.RechargeExGameDialog.RechargeExGameListener
    public void rechargeExGameStart() {
        showWaitingDialog();
    }

    @Override // com.one.tomato.dialog.RechargeExGameDialog.RechargeExGameListener
    public void rechargeExGameSuccess(RechargeExGame rechargeExGame, int i, String inputStr) {
        Intrinsics.checkParameterIsNotNull(rechargeExGame, "rechargeExGame");
        Intrinsics.checkParameterIsNotNull(inputStr, "inputStr");
        hideWaitingDialog();
        ToastUtil.showCenterToast((int) R.string.recharge_ex_success);
        double parseDouble = Double.parseDouble(inputStr);
        if (i == 1) {
            rechargeExGame.balance -= parseDouble * 100;
        } else if (i == 2) {
            rechargeExGame.balance += parseDouble * 100;
        }
        RechargeExGameAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setRechargeExGame(rechargeExGame);
        }
        getRechargeAccountBalance(false);
    }

    @Override // com.one.tomato.dialog.RechargeExGameDialog.RechargeExGameListener
    public void rechargeExGameFail() {
        hideWaitingDialog();
    }
}
