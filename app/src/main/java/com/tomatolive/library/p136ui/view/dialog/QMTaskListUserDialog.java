package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.download.GiftDownLoadManager;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.model.QMInteractTaskListEntity;
import com.tomatolive.library.p136ui.adapter.QMTaskListUserAdapter;
import com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.emptyview.QMTaskListEmptyView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* renamed from: com.tomatolive.library.ui.view.dialog.QMTaskListUserDialog */
/* loaded from: classes3.dex */
public class QMTaskListUserDialog extends BaseBottomDialogFragment {
    private ImageView ivDialogBg;
    private String liveId;
    private OnQMInteractCallback onQMInteractCallback;
    private QMInteractTaskEntity pendingTask;
    private SmartRefreshLayout refreshSelectedList;
    private RecyclerView rvSelectedList;
    private QMTaskListUserAdapter selectTaskListAdapter;

    public static QMTaskListUserDialog newInstance(String str, QMInteractTaskEntity qMInteractTaskEntity, OnQMInteractCallback onQMInteractCallback) {
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtils.RESULT_ID, str);
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, qMInteractTaskEntity);
        QMTaskListUserDialog qMTaskListUserDialog = new QMTaskListUserDialog();
        qMTaskListUserDialog.setArguments(bundle);
        qMTaskListUserDialog.setOnQMInteractCallback(onQMInteractCallback);
        return qMTaskListUserDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.liveId = bundle.getString(ConstantUtils.RESULT_ID);
        this.pendingTask = (QMInteractTaskEntity) bundle.getParcelable(ConstantUtils.RESULT_ITEM);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_qm_task_user;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.ivDialogBg = (ImageView) view.findViewById(R$id.iv_dialog_bg);
        this.rvSelectedList = (RecyclerView) view.findViewById(R$id.rv_selected_list);
        this.refreshSelectedList = (SmartRefreshLayout) view.findViewById(R$id.refresh_selected_list);
        GlideUtils.loadRoundCornersImage(this.mContext, this.ivDialogBg, R$drawable.fq_ic_qm_dialog_bg, 10, RoundedCornersTransformation.CornerType.TOP);
        initTaskListAdapter();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.refreshSelectedList.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListUserDialog.1
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                QMTaskListUserDialog qMTaskListUserDialog = QMTaskListUserDialog.this;
                qMTaskListUserDialog.pageNum++;
                qMTaskListUserDialog.sendTaskListRequest(false);
            }
        });
        this.selectTaskListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListUserDialog.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                QMInteractTaskEntity qMInteractTaskEntity = (QMInteractTaskEntity) baseQuickAdapter.getItem(i);
                if (qMInteractTaskEntity == null || view2.getId() != R$id.tv_status || QMTaskListUserDialog.this.onQMInteractCallback == null) {
                    return;
                }
                QMTaskListUserDialog.this.onQMInteractCallback.onSendGiftListener(QMTaskListUserDialog.this.getGiftDownloadItemEntity(qMInteractTaskEntity));
            }
        });
        view.findViewById(R$id.tv_send_invitation).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListUserDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (QMTaskListUserDialog.this.onQMInteractCallback != null) {
                    if (QMTaskListUserDialog.this.isSendInvitationTask()) {
                        QMTaskListUserDialog.this.onQMInteractCallback.onBackQMInteractConfigListener();
                    } else {
                        QMTaskListUserDialog.this.showToast(R$string.fq_qm_start_task_wait_tips);
                    }
                }
            }
        });
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.pendingTask != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.pendingTask);
            this.selectTaskListAdapter.setNewData(arrayList);
            return;
        }
        onSendTaskListRequest();
    }

    public void onSendTaskListRequest() {
        this.pageNum = 1;
        sendTaskListRequest(true);
    }

    public OnQMInteractCallback getOnQMInteractCallback() {
        return this.onQMInteractCallback;
    }

    public void setOnQMInteractCallback(OnQMInteractCallback onQMInteractCallback) {
        this.onQMInteractCallback = onQMInteractCallback;
    }

    private void initTaskListAdapter() {
        ((DefaultItemAnimator) this.rvSelectedList.getItemAnimator()).setSupportsChangeAnimations(false);
        this.rvSelectedList.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.selectTaskListAdapter = new QMTaskListUserAdapter(R$layout.fq_item_list_qm_task_detail);
        this.rvSelectedList.setAdapter(this.selectTaskListAdapter);
        this.selectTaskListAdapter.bindToRecyclerView(this.rvSelectedList);
        this.selectTaskListAdapter.setEmptyView(new QMTaskListEmptyView(this.mContext, 58));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendTaskListRequest(boolean z) {
        ApiRetrofit.getInstance().getApiService().userTaskListService(new RequestParams().getLiveId(this.liveId)).map(new ServerResultFunction<QMInteractTaskListEntity>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListUserDialog.5
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<QMInteractTaskListEntity>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListUserDialog.4
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(QMInteractTaskListEntity qMInteractTaskListEntity) {
                if (qMInteractTaskListEntity == null) {
                    QMTaskListUserDialog.this.selectTaskListAdapter.setNewData(null);
                } else if (qMInteractTaskListEntity.intimateTaskList == null) {
                    QMTaskListUserDialog.this.selectTaskListAdapter.setNewData(null);
                } else {
                    QMTaskListUserDialog.this.selectTaskListAdapter.setNewData(qMInteractTaskListEntity.intimateTaskList);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public GiftDownloadItemEntity getGiftDownloadItemEntity(QMInteractTaskEntity qMInteractTaskEntity) {
        return GiftDownLoadManager.getInstance().getLocalDownloadGiftItem(qMInteractTaskEntity.giftMarkId);
    }

    public boolean isSendInvitationTask() {
        for (QMInteractTaskEntity qMInteractTaskEntity : this.selectTaskListAdapter.getData()) {
            if (TextUtils.equals(qMInteractTaskEntity.putUserId, UserInfoManager.getInstance().getUserId())) {
                return false;
            }
        }
        return true;
    }

    public void updateTaskChargeList(final List<QMInteractTaskEntity> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Observable.just(this.selectTaskListAdapter.getData()).map(new Function() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$QMTaskListUserDialog$AyFJ-9y9ppqPxnu4sKXPS8aPjQA
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return QMTaskListUserDialog.lambda$updateTaskChargeList$0(list, (List) obj);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListUserDialog.6
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                QMTaskListUserDialog.this.selectTaskListAdapter.notifyDataSetChanged();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Boolean lambda$updateTaskChargeList$0(List list, List list2) throws Exception {
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            QMInteractTaskEntity qMInteractTaskEntity = (QMInteractTaskEntity) it2.next();
            Iterator it3 = list.iterator();
            while (it3.hasNext()) {
                QMInteractTaskEntity qMInteractTaskEntity2 = (QMInteractTaskEntity) it3.next();
                if (TextUtils.equals(qMInteractTaskEntity.taskId, qMInteractTaskEntity2.taskId)) {
                    qMInteractTaskEntity.chargeGiftNum = qMInteractTaskEntity2.chargeGiftNum;
                }
            }
        }
        return true;
    }

    public void completeTaskCharge(final String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Observable.just(this.selectTaskListAdapter.getData()).map(new Function() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$QMTaskListUserDialog$rxM8Oew8TzFb_cUyo4s3nKLvbZ0
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return QMTaskListUserDialog.lambda$completeTaskCharge$1(str, (List) obj);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListUserDialog.7
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                QMTaskListUserDialog.this.selectTaskListAdapter.notifyDataSetChanged();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Boolean lambda$completeTaskCharge$1(String str, List list) throws Exception {
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            QMInteractTaskEntity qMInteractTaskEntity = (QMInteractTaskEntity) it2.next();
            if (TextUtils.equals(qMInteractTaskEntity.taskId, str)) {
                qMInteractTaskEntity.status = ConstantUtils.QM_TASK_STATUS_103;
            }
        }
        return true;
    }
}
