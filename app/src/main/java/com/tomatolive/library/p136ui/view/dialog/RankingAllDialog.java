package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.adapter.RankingAdapter;
import com.tomatolive.library.p136ui.interfaces.OnUserCardCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.utils.AppUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.RankingAllDialog */
/* loaded from: classes3.dex */
public class RankingAllDialog extends BaseBottomDialogFragment {
    private static final String TOP_TAG_VALUE = "topTagValue";
    private RankingAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private OnUserCardCallback onUserCardCallback;
    private ProgressBar progressBar;
    private int topTagValue;

    public static RankingAllDialog newInstance(int i, OnUserCardCallback onUserCardCallback) {
        Bundle bundle = new Bundle();
        RankingAllDialog rankingAllDialog = new RankingAllDialog();
        bundle.putInt(TOP_TAG_VALUE, i);
        rankingAllDialog.setArguments(bundle);
        rankingAllDialog.setOnUserCardCallback(onUserCardCallback);
        return rankingAllDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_live_top_all;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initView(View view) {
        this.topTagValue = getArgumentsInt(TOP_TAG_VALUE, 4);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        this.progressBar = (ProgressBar) view.findViewById(R$id.progress_wheel);
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new RankingAdapter(R$layout.fq_item_list_live_top_new, 4);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 42));
        sendRequest();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.RankingAllDialog.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                AnchorEntity anchorEntity;
                if (RankingAllDialog.this.onUserCardCallback == null || (anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i)) == null) {
                    return;
                }
                RankingAllDialog.this.onUserCardCallback.onAnchorItemClickListener(anchorEntity);
                if (RankingAllDialog.this.topTagValue != 4) {
                    return;
                }
                RankingAllDialog.this.dismiss();
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$RankingAllDialog$6ZCzhUKZ8mJ2WBNabodCn6GtMmQ
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                RankingAllDialog.this.lambda$initListener$0$RankingAllDialog(baseQuickAdapter, view2, i);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$RankingAllDialog(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AnchorEntity anchorEntity;
        if (view.getId() != R$id.tv_attention || this.onUserCardCallback == null || (anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i)) == null || !AppUtils.isAttentionUser(this.mContext, anchorEntity.userId)) {
            return;
        }
        this.onUserCardCallback.onAttentionAnchorListener(view, anchorEntity);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public double getHeightScale() {
        return this.maxHeightScale;
    }

    private void sendRequest() {
        if (this.topTagValue == 4) {
            ApiRetrofit.getInstance().getApiService().getCharmTopListService(new RequestParams().getHomeTopParams("all")).map(new ServerResultFunction<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.RankingAllDialog.3
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.RankingAllDialog.2
                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    RankingAllDialog.this.showLoading(true);
                }

                @Override // io.reactivex.Observer
                public void onNext(List<AnchorEntity> list) {
                    if (list == null) {
                        return;
                    }
                    RankingAllDialog.this.mAdapter.setType(4);
                    RankingAllDialog.this.mAdapter.setNewData(list);
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    RankingAllDialog.this.showLoading(false);
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    RankingAllDialog.this.showLoading(false);
                }
            });
        } else {
            ApiRetrofit.getInstance().getApiService().getStrengthTopListService(new RequestParams().getHomeStrengthTopParams("all")).map(new ServerResultFunction<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.RankingAllDialog.5
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.RankingAllDialog.4
                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    RankingAllDialog.this.showLoading(true);
                }

                @Override // io.reactivex.Observer
                public void onNext(List<AnchorEntity> list) {
                    if (list == null) {
                        return;
                    }
                    RankingAllDialog.this.mAdapter.setType(5);
                    RankingAllDialog.this.mAdapter.setNewData(list);
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    RankingAllDialog.this.showLoading(false);
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    RankingAllDialog.this.showLoading(false);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoading(boolean z) {
        int i = 0;
        this.progressBar.setVisibility(z ? 0 : 4);
        RecyclerView recyclerView = this.mRecyclerView;
        if (z) {
            i = 4;
        }
        recyclerView.setVisibility(i);
    }

    public OnUserCardCallback getOnUserCardCallback() {
        return this.onUserCardCallback;
    }

    public void setOnUserCardCallback(OnUserCardCallback onUserCardCallback) {
        this.onUserCardCallback = onUserCardCallback;
    }
}
