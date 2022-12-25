package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.ReceiveGiftRecordEntity;
import com.tomatolive.library.model.ReceiveGiftRecordPageEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.adapter.GiftRecordAdapter;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.Collection;

/* renamed from: com.tomatolive.library.ui.view.dialog.GiftRecordDialog */
/* loaded from: classes3.dex */
public class GiftRecordDialog extends BaseBottomDialogFragment {
    private GiftRecordAdapter adapter;
    private boolean isDownFresh;
    private int liveCount;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView recyclerView;
    private SimpleUserCardCallback simpleUserCardCallback;
    private TextView tv_expected_profits;
    private TextView tv_rewards_personal;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        return 0.0f;
    }

    public static GiftRecordDialog newInstance(int i, SimpleUserCardCallback simpleUserCardCallback) {
        GiftRecordDialog giftRecordDialog = new GiftRecordDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtils.RESULT_COUNT, i);
        giftRecordDialog.setArguments(bundle);
        giftRecordDialog.setSimpleUserCardCallback(simpleUserCardCallback);
        return giftRecordDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.liveCount = bundle.getInt(ConstantUtils.RESULT_COUNT);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_layout_bottom_gift_record_view;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initView(View view) {
        this.mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.refreshLayout);
        this.recyclerView = (RecyclerView) view.findViewById(R$id.rv_operate);
        this.tv_expected_profits = (TextView) view.findViewById(R$id.tv_expected_profits);
        this.tv_rewards_personal = (TextView) view.findViewById(R$id.tv_rewards_personal);
        initAdapter();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        this.isDownFresh = true;
        this.pageNum = 1;
        this.mSmartRefreshLayout.autoRefresh();
        sendRequest();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.view.dialog.GiftRecordDialog.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                GiftRecordDialog.this.isDownFresh = false;
                GiftRecordDialog giftRecordDialog = GiftRecordDialog.this;
                giftRecordDialog.pageNum++;
                giftRecordDialog.sendRequest();
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                GiftRecordDialog.this.isDownFresh = true;
                GiftRecordDialog giftRecordDialog = GiftRecordDialog.this;
                giftRecordDialog.pageNum = 1;
                giftRecordDialog.sendRequest();
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.GiftRecordDialog.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                ReceiveGiftRecordEntity receiveGiftRecordEntity = (ReceiveGiftRecordEntity) baseQuickAdapter.getItem(i);
                if (receiveGiftRecordEntity == null || GiftRecordDialog.this.simpleUserCardCallback == null) {
                    return;
                }
                UserEntity userEntity = new UserEntity();
                userEntity.setUserId(receiveGiftRecordEntity.userId);
                userEntity.setAvatar(receiveGiftRecordEntity.avatar);
                userEntity.setName(receiveGiftRecordEntity.name);
                userEntity.setSex(receiveGiftRecordEntity.sex);
                userEntity.setExpGrade(receiveGiftRecordEntity.expGrade);
                userEntity.setGuardType(NumberUtils.string2int(receiveGiftRecordEntity.guardType));
                userEntity.setRole(receiveGiftRecordEntity.role);
                userEntity.setNobilityType(receiveGiftRecordEntity.nobilityType);
                userEntity.setUserRole(receiveGiftRecordEntity.userRole);
                GiftRecordDialog.this.simpleUserCardCallback.onUserItemClickListener(userEntity);
            }
        });
    }

    private void initAdapter() {
        this.adapter = new GiftRecordAdapter(R$layout.fq_item_list_gift_record_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.bindToRecyclerView(this.recyclerView);
        this.adapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 40));
        this.adapter.setHeaderAndEmpty(true);
    }

    public void setSimpleUserCardCallback(SimpleUserCardCallback simpleUserCardCallback) {
        this.simpleUserCardCallback = simpleUserCardCallback;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public double getHeightScale() {
        return this.maxHeightScale;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest() {
        ApiRetrofit.getInstance().getApiService().getLiveCountPageService(new RequestParams().getLiveCountPageService(this.pageNum, this.liveCount)).map(new ServerResultFunction<ReceiveGiftRecordPageEntity>() { // from class: com.tomatolive.library.ui.view.dialog.GiftRecordDialog.4
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<ReceiveGiftRecordPageEntity>() { // from class: com.tomatolive.library.ui.view.dialog.GiftRecordDialog.3
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(ReceiveGiftRecordPageEntity receiveGiftRecordPageEntity) {
                if (receiveGiftRecordPageEntity == null) {
                    return;
                }
                ReceiveGiftRecordPageEntity.GiftStatisticsEntity giftStatisticsEntity = receiveGiftRecordPageEntity.statis;
                if (giftStatisticsEntity != null) {
                    GiftRecordDialog.this.tv_expected_profits.setText(((BaseRxDialogFragment) GiftRecordDialog.this).mContext.getString(R$string.fq_git_expected_profits, AppUtils.formatDisplayPrice(giftStatisticsEntity.totalPracticalPrice, false)));
                    GiftRecordDialog.this.tv_rewards_personal.setText(((BaseRxDialogFragment) GiftRecordDialog.this).mContext.getString(R$string.fq_git_rewards_personal, String.valueOf(giftStatisticsEntity.payMemberCount)));
                }
                HttpResultPageModel<ReceiveGiftRecordEntity> httpResultPageModel = receiveGiftRecordPageEntity.page;
                if (httpResultPageModel == null) {
                    return;
                }
                if (GiftRecordDialog.this.isDownFresh) {
                    GiftRecordDialog.this.adapter.setNewData(httpResultPageModel.dataList);
                } else {
                    GiftRecordDialog.this.adapter.addData((Collection) httpResultPageModel.dataList);
                }
                AppUtils.updateRefreshLayoutFinishStatus(GiftRecordDialog.this.mSmartRefreshLayout, httpResultPageModel.isMorePage(), GiftRecordDialog.this.isDownFresh);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                if (!GiftRecordDialog.this.isDownFresh) {
                    GiftRecordDialog.this.mSmartRefreshLayout.finishLoadMore();
                }
            }
        });
    }
}
