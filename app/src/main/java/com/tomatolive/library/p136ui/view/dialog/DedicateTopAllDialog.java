package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.adapter.DedicateTopAdapter;
import com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.UserInfoManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.DedicateTopAllDialog */
/* loaded from: classes3.dex */
public class DedicateTopAllDialog extends BaseBottomDialogFragment {
    private static final String ANCHORID_KEY = "anchorId_key";
    private static final String LIVE_TYPE = "liveType";
    private String anchorId;
    private AnchorEntity anchorInfoItem;
    private int liveType = 2;
    private DedicateTopAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private OnLivePusherInfoCallback onLivePusherInfoCallback;
    private ProgressBar progressBar;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        return 0.0f;
    }

    public static DedicateTopAllDialog newInstance(int i, AnchorEntity anchorEntity, OnLivePusherInfoCallback onLivePusherInfoCallback) {
        Bundle bundle = new Bundle();
        DedicateTopAllDialog dedicateTopAllDialog = new DedicateTopAllDialog();
        bundle.putInt("liveType", i);
        bundle.putParcelable(ANCHORID_KEY, anchorEntity);
        dedicateTopAllDialog.setArguments(bundle);
        dedicateTopAllDialog.setOnLivePusherInfoCallback(onLivePusherInfoCallback);
        return dedicateTopAllDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_dedicate_top_all;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.liveType = getArgumentsInt("liveType", 2);
        this.anchorInfoItem = (AnchorEntity) bundle.getParcelable(ANCHORID_KEY);
        AnchorEntity anchorEntity = this.anchorInfoItem;
        this.anchorId = anchorEntity != null ? anchorEntity.userId : "";
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initView(View view) {
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        this.progressBar = (ProgressBar) view.findViewById(R$id.progress_wheel);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new DedicateTopAdapter(R$layout.fq_item_list_dedicate_top_live, true);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 38));
        sendRequest(this.anchorId);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$DedicateTopAllDialog$TZPbp0XrYuLrj4FNsqjMEK0axD0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                DedicateTopAllDialog.this.lambda$initListener$1$DedicateTopAllDialog(baseQuickAdapter, view2, i);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$DedicateTopAllDialog(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AnchorEntity anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i);
        if (anchorEntity == null) {
            return;
        }
        if (anchorEntity.isRankHideBoolean() && !TextUtils.equals(anchorEntity.userId, UserInfoManager.getInstance().getUserId())) {
            NobilityOpenTipsDialog.newInstance(13, new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$DedicateTopAllDialog$7p8HKF7BVF3SsL_RxCoVuShJBC8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    DedicateTopAllDialog.this.lambda$null$0$DedicateTopAllDialog(view2);
                }
            }).show(getChildFragmentManager());
            return;
        }
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback == null) {
            return;
        }
        onLivePusherInfoCallback.onClickUserAvatarListener(AppUtils.formatUserEntity(anchorEntity));
    }

    public /* synthetic */ void lambda$null$0$DedicateTopAllDialog(View view) {
        OnLivePusherInfoCallback onLivePusherInfoCallback;
        if (!AppUtils.isAnchorLiveType(this.liveType) && (onLivePusherInfoCallback = this.onLivePusherInfoCallback) != null) {
            onLivePusherInfoCallback.onNobilityOpenListener();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public double getHeightScale() {
        return this.maxHeightScale;
    }

    private void sendRequest(String str) {
        ApiRetrofit.getInstance().getApiService().getDedicateTopListService(new RequestParams().getContributionListParams("all", str)).map(new ServerResultFunction<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.DedicateTopAllDialog.2
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.DedicateTopAllDialog.1
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                DedicateTopAllDialog.this.showLoading(true);
            }

            @Override // io.reactivex.Observer
            public void onNext(List<AnchorEntity> list) {
                if (list == null) {
                    return;
                }
                DedicateTopAllDialog.this.mAdapter.setNewData(list);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                DedicateTopAllDialog.this.showLoading(false);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                DedicateTopAllDialog.this.showLoading(false);
            }
        });
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

    public OnLivePusherInfoCallback getOnLivePusherInfoCallback() {
        return this.onLivePusherInfoCallback;
    }

    public void setOnLivePusherInfoCallback(OnLivePusherInfoCallback onLivePusherInfoCallback) {
        this.onLivePusherInfoCallback = onLivePusherInfoCallback;
    }
}
