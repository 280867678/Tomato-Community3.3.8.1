package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.adapter.PKRankingAdapter;
import com.tomatolive.library.p136ui.interfaces.OnUserCardCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.headview.PKRankHeadView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.PKRankDialog */
/* loaded from: classes3.dex */
public class PKRankDialog extends BaseBottomDialogFragment {
    private String liveId;
    private OnUserCardCallback onUserCardCallback;
    private PKRankHeadView pkRankHeadView;
    private PKRankingAdapter pkRankingAdapter;
    private RecyclerView recyclerViewRanking;
    private TextView tvLoading;
    private TextView tvLoadingFail;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected double getHeightScale() {
        return 0.6d;
    }

    public static PKRankDialog newInstance(String str, OnUserCardCallback onUserCardCallback) {
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtils.RESULT_ID, str);
        PKRankDialog pKRankDialog = new PKRankDialog();
        pKRankDialog.setArguments(bundle);
        pKRankDialog.setOnUserCardCallback(onUserCardCallback);
        return pKRankDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.liveId = bundle.getString(ConstantUtils.RESULT_ID);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_pk_rank;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.tvLoading = (TextView) view.findViewById(R$id.tv_loading);
        this.tvLoadingFail = (TextView) view.findViewById(R$id.tv_loading_fail);
        this.recyclerViewRanking = (RecyclerView) view.findViewById(R$id.recycler_view_ranking);
        initRankAdapter();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.pkRankHeadView.setOnUserCardCallback(this.onUserCardCallback);
        this.pkRankingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PKRankDialog.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                AnchorEntity anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i);
                if (anchorEntity == null || PKRankDialog.this.onUserCardCallback == null) {
                    return;
                }
                PKRankDialog.this.onUserCardCallback.onAnchorItemClickListener(anchorEntity);
            }
        });
        this.tvLoadingFail.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PKRankDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                PKRankDialog.this.sendRequest();
            }
        });
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        sendRequest();
    }

    private void initRankAdapter() {
        this.pkRankHeadView = new PKRankHeadView(this.mContext);
        this.pkRankingAdapter = new PKRankingAdapter(R$layout.fq_item_list_pk_rank);
        this.recyclerViewRanking.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.recyclerViewRanking.setAdapter(this.pkRankingAdapter);
        this.pkRankingAdapter.bindToRecyclerView(this.recyclerViewRanking);
        this.pkRankingAdapter.addHeaderView(this.pkRankHeadView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest() {
        ApiRetrofit.getInstance().getApiService().getPKRankListService(new RequestParams().getLiveId(this.liveId)).map(new ServerResultFunction<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.PKRankDialog.4
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.PKRankDialog.3
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                PKRankDialog.this.tvLoading.setVisibility(0);
                PKRankDialog.this.tvLoadingFail.setVisibility(4);
                PKRankDialog.this.recyclerViewRanking.setVisibility(4);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<AnchorEntity> list) {
                PKRankDialog.this.tvLoading.setVisibility(4);
                PKRankDialog.this.tvLoadingFail.setVisibility(4);
                PKRankDialog.this.recyclerViewRanking.setVisibility(0);
                PKRankDialog.this.pkRankHeadView.initData(list);
                if (list.size() > 3) {
                    PKRankDialog.this.pkRankingAdapter.setNewData(list.subList(3, list.size()));
                } else {
                    PKRankDialog.this.pkRankingAdapter.setNewData(PKRankDialog.this.getEmptyAnchorList());
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                PKRankDialog.this.tvLoading.setVisibility(4);
                PKRankDialog.this.tvLoadingFail.setVisibility(0);
                PKRankDialog.this.recyclerViewRanking.setVisibility(4);
            }
        });
    }

    public OnUserCardCallback getOnUserCardCallback() {
        return this.onUserCardCallback;
    }

    public void setOnUserCardCallback(OnUserCardCallback onUserCardCallback) {
        this.onUserCardCallback = onUserCardCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<AnchorEntity> getEmptyAnchorList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(null);
        return arrayList;
    }
}
