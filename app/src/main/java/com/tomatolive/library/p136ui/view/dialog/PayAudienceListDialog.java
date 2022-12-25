package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.TicketRoomInfoEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.adapter.PayAudienceAdapter;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.RxViewUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog */
/* loaded from: classes3.dex */
public class PayAudienceListDialog extends BaseDialogFragment {
    private ImageView ivRankDown;
    private LinearLayout llAudienceContentView;
    private LinearLayout llBasicContentView;
    private PayAudienceAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SimpleUserCardCallback simpleUserCardCallback;
    private SmartRefreshLayout smartRefreshLayout;
    private String switchAction;
    private TicketRoomInfoEntity ticketRoomInfoEntity;
    private TextView tvAudienceList;
    private TextView tvBasicInformation;
    private TextView tvCurrentPersonal;
    private TextView tvLiveIncome;
    private TextView tvLiveTime;
    private TextView tvLoading;
    private TextView tvLoadingFail;
    private TextView tvPersonal;
    private TextView tvStopBuy;
    private TextView tvTotalPersonal;
    private final int CONTENT_TYPE_1 = 1;
    private final int CONTENT_TYPE_2 = 2;
    private int contentType = 1;
    private boolean rankUpSort = false;
    private String liveId = "";
    private String liveCount = "";
    private long startLiveTimeMillis = 0;

    public static PayAudienceListDialog newInstance(String str, String str2, long j, SimpleUserCardCallback simpleUserCardCallback) {
        PayAudienceListDialog payAudienceListDialog = new PayAudienceListDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtils.RESULT_ID, str);
        bundle.putString(ConstantUtils.RESULT_COUNT, str2);
        bundle.putLong(ConstantUtils.RESULT_ITEM, j);
        payAudienceListDialog.setSimpleUserCardCallback(simpleUserCardCallback);
        payAudienceListDialog.setArguments(bundle);
        return payAudienceListDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.liveId = bundle.getString(ConstantUtils.RESULT_ID);
        this.liveCount = bundle.getString(ConstantUtils.RESULT_COUNT);
        this.startLiveTimeMillis = bundle.getLong(ConstantUtils.RESULT_ITEM, 0L);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_pay_audience_list;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected void initView(View view) {
        this.tvBasicInformation = (TextView) view.findViewById(R$id.tv_basic_information);
        this.tvAudienceList = (TextView) view.findViewById(R$id.tv_audience_list);
        this.tvTotalPersonal = (TextView) view.findViewById(R$id.tv_total_personal);
        this.tvCurrentPersonal = (TextView) view.findViewById(R$id.tv_current_personal);
        this.tvLiveTime = (TextView) view.findViewById(R$id.tv_live_time);
        this.tvLiveIncome = (TextView) view.findViewById(R$id.tv_live_income);
        this.tvStopBuy = (TextView) view.findViewById(R$id.tv_stop_buy);
        this.tvLoadingFail = (TextView) view.findViewById(R$id.tv_loading_fail);
        this.tvLoading = (TextView) view.findViewById(R$id.tv_loading);
        this.tvPersonal = (TextView) view.findViewById(R$id.tv_personal);
        this.ivRankDown = (ImageView) view.findViewById(R$id.iv_rank_down);
        this.llBasicContentView = (LinearLayout) view.findViewById(R$id.ll_basic_content_view);
        this.llAudienceContentView = (LinearLayout) view.findViewById(R$id.ll_list_content_view);
        this.smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.record_refreshLayout);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view_record);
        initAdapter();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        this.pageNum = 1;
        sendBaseInfoRequest();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        RxViewUtils.getInstance().throttleFirst(this.tvBasicInformation, 600, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.1
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                if (PayAudienceListDialog.this.tvBasicInformation.isSelected()) {
                    return;
                }
                PayAudienceListDialog.this.sendBaseInfoRequest();
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.tvAudienceList, 600, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.2
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                if (PayAudienceListDialog.this.tvAudienceList.isSelected()) {
                    return;
                }
                PayAudienceListDialog payAudienceListDialog = PayAudienceListDialog.this;
                payAudienceListDialog.pageNum = 1;
                payAudienceListDialog.sendAudienceListRequest(payAudienceListDialog.rankUpSort, PayAudienceListDialog.this.pageNum, false, true);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.tvStopBuy, 600, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.3
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                PayAudienceListDialog.this.sendStopRequest();
            }
        });
        this.smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.4
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                PayAudienceListDialog payAudienceListDialog = PayAudienceListDialog.this;
                payAudienceListDialog.pageNum++;
                payAudienceListDialog.sendAudienceListRequest(payAudienceListDialog.rankUpSort, PayAudienceListDialog.this.pageNum, false, false);
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                UserEntity userEntity = (UserEntity) baseQuickAdapter.getItem(i);
                if (userEntity == null || PayAudienceListDialog.this.simpleUserCardCallback == null) {
                    return;
                }
                PayAudienceListDialog.this.simpleUserCardCallback.onUserItemClickListener(userEntity);
            }
        });
        this.tvLoadingFail.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PayAudienceListDialog.this.tvBasicInformation.isSelected()) {
                    PayAudienceListDialog.this.sendBaseInfoRequest();
                } else if (!PayAudienceListDialog.this.tvAudienceList.isSelected()) {
                } else {
                    PayAudienceListDialog payAudienceListDialog = PayAudienceListDialog.this;
                    payAudienceListDialog.pageNum = 1;
                    payAudienceListDialog.sendAudienceListRequest(payAudienceListDialog.rankUpSort, PayAudienceListDialog.this.pageNum, true, true);
                }
            }
        });
        this.ivRankDown.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                PayAudienceListDialog payAudienceListDialog = PayAudienceListDialog.this;
                payAudienceListDialog.rankUpSort = !payAudienceListDialog.rankUpSort;
                PayAudienceListDialog payAudienceListDialog2 = PayAudienceListDialog.this;
                payAudienceListDialog2.pageNum = 1;
                payAudienceListDialog2.sendAudienceListRequest(payAudienceListDialog2.rankUpSort, PayAudienceListDialog.this.pageNum, false, true);
            }
        });
    }

    public void setSimpleUserCardCallback(SimpleUserCardCallback simpleUserCardCallback) {
        this.simpleUserCardCallback = simpleUserCardCallback;
    }

    private void initAdapter() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerView.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_color_transparent, ConvertUtils.dp2px(6.0f)));
        this.mAdapter = new PayAudienceAdapter(R$layout.fq_item_list_pay_audience);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 50));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showContentView(int i) {
        this.contentType = i;
        int i2 = 0;
        this.llBasicContentView.setVisibility(i == 1 ? 0 : 4);
        LinearLayout linearLayout = this.llAudienceContentView;
        if (i != 2) {
            i2 = 4;
        }
        linearLayout.setVisibility(i2);
        this.tvLoadingFail.setVisibility(4);
        this.tvLoading.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoadingView() {
        this.llBasicContentView.setVisibility(4);
        this.llAudienceContentView.setVisibility(4);
        this.tvLoadingFail.setVisibility(4);
        this.tvLoading.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFailView() {
        this.llBasicContentView.setVisibility(4);
        this.llAudienceContentView.setVisibility(4);
        this.tvLoadingFail.setVisibility(0);
        this.tvLoading.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBaseInfoRequest() {
        this.tvBasicInformation.setSelected(true);
        this.tvAudienceList.setSelected(false);
        ApiRetrofit.getInstance().getApiService().getTicketRoomBaseInfoService(new RequestParams().getTicketRoomBaseInfoParams(this.liveId, this.liveCount)).map(new ServerResultFunction<TicketRoomInfoEntity>() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.9
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<TicketRoomInfoEntity>() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.8
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                PayAudienceListDialog.this.showLoadingView();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(TicketRoomInfoEntity ticketRoomInfoEntity) {
                if (ticketRoomInfoEntity == null) {
                    return;
                }
                PayAudienceListDialog.this.showContentView(1);
                PayAudienceListDialog.this.initBaseInfo(ticketRoomInfoEntity);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                PayAudienceListDialog.this.showFailView();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAudienceListRequest(boolean z, int i, final boolean z2, final boolean z3) {
        this.tvBasicInformation.setSelected(false);
        this.tvAudienceList.setSelected(true);
        ApiRetrofit.getInstance().getApiService().getTicketRoomUserListService(new RequestParams().getTicketRoomBaseInfoParams(this.liveId, this.liveCount, z, i)).map(new ServerResultFunction<HttpResultPageModel<UserEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.11
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<HttpResultPageModel<UserEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.10
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                if (z2) {
                    PayAudienceListDialog.this.showLoadingView();
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(HttpResultPageModel<UserEntity> httpResultPageModel) {
                List<UserEntity> list;
                if (httpResultPageModel == null || (list = httpResultPageModel.dataList) == null) {
                    return;
                }
                int i2 = httpResultPageModel.totalRowsCount;
                if (i2 < 0) {
                    i2 = 0;
                }
                PayAudienceListDialog.this.tvPersonal.setText(((BaseRxDialogFragment) PayAudienceListDialog.this).mContext.getString(R$string.fq_pay_current_personal, String.valueOf(i2)));
                PayAudienceListDialog.this.ivRankDown.setImageResource(PayAudienceListDialog.this.rankUpSort ? R$drawable.fq_ic_pay_audience_rank_up : R$drawable.fq_ic_pay_audience_rank_down);
                if (z3) {
                    PayAudienceListDialog.this.mAdapter.setNewData(list);
                } else {
                    PayAudienceListDialog.this.mAdapter.addData((Collection) list);
                }
                AppUtils.updateRefreshLayoutFinishStatus(PayAudienceListDialog.this.smartRefreshLayout, httpResultPageModel.isMorePage(), z3);
                PayAudienceListDialog.this.showContentView(2);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                PayAudienceListDialog.this.showFailView();
                if (!z3) {
                    PayAudienceListDialog.this.smartRefreshLayout.finishLoadMore();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendStopRequest() {
        TicketRoomInfoEntity ticketRoomInfoEntity = this.ticketRoomInfoEntity;
        this.switchAction = ticketRoomInfoEntity != null ? ticketRoomInfoEntity.isOpenTicketEnterEnable() ? "0" : "1" : "";
        ApiRetrofit.getInstance().getApiService().getSwitchTicketRoomService(new RequestParams().getSwitchTicketRoomParams(this.liveId, this.liveCount, this.switchAction)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.13
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.view.dialog.PayAudienceListDialog.12
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                if (TextUtils.equals(PayAudienceListDialog.this.switchAction, "1")) {
                    PayAudienceListDialog.this.tvStopBuy.setText(R$string.fq_pay_open_buying_tickets);
                } else {
                    PayAudienceListDialog.this.tvStopBuy.setText(R$string.fq_pay_stopping_buying_tickets);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                if (obj == null) {
                    return;
                }
                if (TextUtils.equals(PayAudienceListDialog.this.switchAction, "1")) {
                    PayAudienceListDialog.this.ticketRoomInfoEntity.setTicketRoomSwitch("1");
                    PayAudienceListDialog.this.tvStopBuy.setText(R$string.fq_pay_stop_buying_tickets);
                    return;
                }
                PayAudienceListDialog.this.ticketRoomInfoEntity.setTicketRoomSwitch("0");
                PayAudienceListDialog.this.tvStopBuy.setText(R$string.fq_pay_open_tickets);
                PayAudienceListDialog.this.showToast(R$string.fq_pay_stopped_buying_tickets_tips);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBaseInfo(TicketRoomInfoEntity ticketRoomInfoEntity) {
        long currentTimeMillis = ((System.currentTimeMillis() - this.startLiveTimeMillis) % DateUtils.ONE_HOUR_MILLIONS) / DateUtils.ONE_MINUTE_MILLIONS;
        if (currentTimeMillis < 1) {
            currentTimeMillis = 0;
        }
        this.ticketRoomInfoEntity = ticketRoomInfoEntity;
        this.tvTotalPersonal.setText(ticketRoomInfoEntity.totalUserNum);
        this.tvCurrentPersonal.setText(ticketRoomInfoEntity.currentUserNum);
        this.tvLiveTime.setText(this.mContext.getString(R$string.fq_live_time_minute, String.valueOf(currentTimeMillis)));
        this.tvLiveIncome.setText(AppUtils.formatMoneyUnitStr(this.mContext, ticketRoomInfoEntity.income, true));
        this.tvStopBuy.setText(ticketRoomInfoEntity.isOpenTicketEnterEnable() ? R$string.fq_pay_stop_buying_tickets : R$string.fq_pay_open_tickets);
    }
}
