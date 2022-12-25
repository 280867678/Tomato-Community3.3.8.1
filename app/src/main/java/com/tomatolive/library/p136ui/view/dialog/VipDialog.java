package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.adapter.LiveVipAdapter;
import com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.emptyview.VipEmptyView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.RxViewUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.VipDialog */
/* loaded from: classes3.dex */
public class VipDialog extends BaseBottomDialogFragment {
    private AnchorEntity anchorInfoItem;
    private LinearLayout llContentBg;
    private LinearLayout llFooterBg;
    private LiveVipAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private OnLivePusherInfoCallback onLivePusherInfoCallback;
    private ProgressBar progressBar;
    private TextView tvCount;
    private TextView tvOpen;
    private long vipCount = 0;
    private int liveType = 2;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        return 0.0f;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected double getHeightScale() {
        return 0.74d;
    }

    public static VipDialog newInstance(AnchorEntity anchorEntity, long j, int i, OnLivePusherInfoCallback onLivePusherInfoCallback) {
        Bundle bundle = new Bundle();
        VipDialog vipDialog = new VipDialog();
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
        bundle.putLong(ConstantUtils.RESULT_COUNT, j);
        bundle.putInt(ConstantUtils.RESULT_FLAG, i);
        vipDialog.setArguments(bundle);
        vipDialog.setOnLivePusherInfoCallback(onLivePusherInfoCallback);
        return vipDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.anchorInfoItem = (AnchorEntity) bundle.getParcelable(ConstantUtils.RESULT_ITEM);
        this.vipCount = bundle.getLong(ConstantUtils.RESULT_COUNT, 0L);
        this.liveType = bundle.getInt(ConstantUtils.RESULT_FLAG, 2);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_vip_top;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        this.tvCount = (TextView) view.findViewById(R$id.tv_vip_count);
        this.tvOpen = (TextView) view.findViewById(R$id.tv_open);
        this.progressBar = (ProgressBar) view.findViewById(R$id.progress_wheel);
        this.llContentBg = (LinearLayout) view.findViewById(R$id.ll_content_bg);
        this.llFooterBg = (LinearLayout) view.findViewById(R$id.ll_footer_bg);
        this.tvCount.setText(getString(R$string.fq_vip_place_count, AppUtils.formatLiveVipCount(this.vipCount)));
        this.tvOpen.setText(AppUtils.isNobilityUser() ? R$string.fq_nobility_renewal_fee : R$string.fq_nobility_open);
        int i = 4;
        if (!AppUtils.isEnableNobility()) {
            this.tvOpen.setVisibility(4);
        } else {
            TextView textView = this.tvOpen;
            if (AppUtils.isAudienceLiveType(this.liveType)) {
                i = 0;
            }
            textView.setVisibility(i);
        }
        initAdapter();
        sendRequest();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$VipDialog$B1uG5SXT_uNQoPZ4uZbo2zqNX0U
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                VipDialog.this.lambda$initListener$0$VipDialog(baseQuickAdapter, view2, i);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.tvOpen, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$VipDialog$-UhrUdEUD6cw1iu8z-KYGfrTaAs
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                VipDialog.this.lambda$initListener$1$VipDialog(obj);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$VipDialog(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        OnLivePusherInfoCallback onLivePusherInfoCallback;
        UserEntity userEntity = (UserEntity) baseQuickAdapter.getItem(i);
        if (userEntity == null || (onLivePusherInfoCallback = this.onLivePusherInfoCallback) == null) {
            return;
        }
        onLivePusherInfoCallback.onClickUserAvatarListener(userEntity);
    }

    public /* synthetic */ void lambda$initListener$1$VipDialog(Object obj) {
        toNobilityOpenActivity();
    }

    private void initAdapter() {
        this.mAdapter = new LiveVipAdapter(R$layout.fq_item_list_vip);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new VipEmptyView(this.mContext));
    }

    private void sendRequest() {
        AnchorEntity anchorEntity = this.anchorInfoItem;
        if (anchorEntity == null) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().getVipSeatListService(new RequestParams().getVipSeatListParams(anchorEntity.liveId)).map(new ServerResultFunction<List<UserEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.VipDialog.2
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<List<UserEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.VipDialog.1
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                VipDialog.this.showLoading(true);
            }

            @Override // io.reactivex.Observer
            public void onNext(List<UserEntity> list) {
                if (list == null) {
                    return;
                }
                VipDialog.this.mAdapter.setNewData(list);
                VipDialog.this.llFooterBg.setVisibility(list.isEmpty() ? 8 : 0);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                VipDialog.this.showLoading(false);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                VipDialog.this.showLoading(false);
            }
        });
    }

    public OnLivePusherInfoCallback getOnLivePusherInfoCallback() {
        return this.onLivePusherInfoCallback;
    }

    public void setOnLivePusherInfoCallback(OnLivePusherInfoCallback onLivePusherInfoCallback) {
        this.onLivePusherInfoCallback = onLivePusherInfoCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoading(boolean z) {
        int i = 0;
        this.progressBar.setVisibility(z ? 0 : 4);
        LinearLayout linearLayout = this.llContentBg;
        if (z) {
            i = 4;
        }
        linearLayout.setVisibility(i);
    }

    private void toNobilityOpenActivity() {
        if (AppUtils.isAudienceLiveType(this.liveType)) {
            AppUtils.toNobilityOpenActivity(this.mContext, this.anchorInfoItem);
        }
    }
}
