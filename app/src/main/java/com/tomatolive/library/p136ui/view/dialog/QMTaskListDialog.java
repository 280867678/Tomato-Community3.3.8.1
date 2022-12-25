package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.p136ui.adapter.QMTaskListAdapter;
import com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.emptyview.QMTaskListEmptyView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* renamed from: com.tomatolive.library.ui.view.dialog.QMTaskListDialog */
/* loaded from: classes3.dex */
public class QMTaskListDialog extends BaseBottomDialogFragment {
    private QMTaskListAdapter acceptTaskListAdapter;
    private ImageView ivAdd;
    private ImageView ivDialogBg;
    private String liveId;
    private LoadingDialog loadingDialog = null;
    private OnQMInteractCallback onQMInteractCallback;
    private SureCancelDialog qmTaskStartPlayAnimDialog;
    private SmartRefreshLayout refreshSelectedList;
    private View rlSelectedListBg;
    private RecyclerView rvAcceptedList;
    private RecyclerView rvSelectedList;
    private QMTaskListAdapter selectTaskListAdapter;
    private Disposable timerDisposable;
    private TextView tvAccepted;
    private TextView tvSelected;

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isStartTimer(int i) {
        return i == 200074 || i == 200006 || i == 200073 || i == 200503;
    }

    public static QMTaskListDialog newInstance(String str, OnQMInteractCallback onQMInteractCallback) {
        Bundle bundle = new Bundle();
        QMTaskListDialog qMTaskListDialog = new QMTaskListDialog();
        bundle.putString(ConstantUtils.RESULT_ID, str);
        qMTaskListDialog.setOnQMInteractCallback(onQMInteractCallback);
        qMTaskListDialog.setArguments(bundle);
        return qMTaskListDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.liveId = bundle.getString(ConstantUtils.RESULT_ID);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_qm_task;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.ivDialogBg = (ImageView) view.findViewById(R$id.iv_dialog_bg);
        this.tvSelected = (TextView) view.findViewById(R$id.tv_selected);
        this.tvAccepted = (TextView) view.findViewById(R$id.tv_accepted);
        this.rvSelectedList = (RecyclerView) view.findViewById(R$id.rv_selected_list);
        this.rvAcceptedList = (RecyclerView) view.findViewById(R$id.rv_accepted_list);
        this.ivAdd = (ImageView) view.findViewById(R$id.iv_add);
        this.refreshSelectedList = (SmartRefreshLayout) view.findViewById(R$id.refresh_selected_list);
        this.rlSelectedListBg = view.findViewById(R$id.rl_selected_list_bg);
        GlideUtils.loadRoundCornersImage(this.mContext, this.ivDialogBg, R$drawable.fq_ic_qm_dialog_bg, 10, RoundedCornersTransformation.CornerType.TOP);
        initSelectedTaskListAdapter();
        initAcceptTaskListAdapter();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.ivAdd.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (QMTaskListDialog.this.onQMInteractCallback != null) {
                    QMTaskListDialog.this.onQMInteractCallback.onBackQMInteractConfigListener();
                }
            }
        });
        this.tvSelected.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (QMTaskListDialog.this.tvSelected.isSelected()) {
                    return;
                }
                QMTaskListDialog.this.changeTaskTabView(true);
            }
        });
        this.tvAccepted.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (QMTaskListDialog.this.tvAccepted.isSelected()) {
                    return;
                }
                QMTaskListDialog.this.changeTaskTabView(false);
            }
        });
        this.refreshSelectedList.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.4
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                QMTaskListDialog qMTaskListDialog = QMTaskListDialog.this;
                qMTaskListDialog.pageNum++;
                qMTaskListDialog.sendSelectedTaskListRequest(false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                QMTaskListDialog.this.sendSelectedTaskListRequest(true);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.selectTaskListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                int id = view2.getId();
                QMInteractTaskEntity qMInteractTaskEntity = (QMInteractTaskEntity) baseQuickAdapter.getItem(i);
                if (qMInteractTaskEntity == null) {
                    return;
                }
                if (id == R$id.tv_refuse) {
                    QMTaskListDialog.this.sendTaskStatusUpdateRequest(false, ConstantUtils.QM_TASK_STATUS_202, i, baseQuickAdapter, qMInteractTaskEntity);
                } else if (id != R$id.tv_accept) {
                } else {
                    QMTaskListDialog.this.sendTaskStatusUpdateRequest(true, ConstantUtils.QM_TASK_STATUS_203, i, baseQuickAdapter, qMInteractTaskEntity);
                }
            }
        });
        this.acceptTaskListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                String str;
                String str2;
                int id = view2.getId();
                QMInteractTaskEntity qMInteractTaskEntity = (QMInteractTaskEntity) baseQuickAdapter.getItem(i);
                if (qMInteractTaskEntity != null && id == R$id.tv_status) {
                    if (TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_203)) {
                        str2 = ConstantUtils.QM_TASK_STATUS_204;
                    } else {
                        if (TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_204)) {
                            str = ConstantUtils.QM_TASK_STATUS_205;
                        } else if (TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_103)) {
                            str = ConstantUtils.QM_TASK_STATUS_104;
                        } else {
                            str = TextUtils.equals(qMInteractTaskEntity.status, ConstantUtils.QM_TASK_STATUS_101) ? ConstantUtils.QM_TASK_STATUS_102 : "";
                        }
                        str2 = str;
                    }
                    QMTaskListDialog.this.sendTaskStatusUpdateRequest(false, str2, i, baseQuickAdapter, qMInteractTaskEntity);
                }
            }
        });
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        changeTaskTabView(true);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment, android.support.p002v4.app.DialogFragment
    public void dismiss() {
        super.dismiss();
        dismissDialogFragment(this.qmTaskStartPlayAnimDialog);
    }

    public OnQMInteractCallback getOnQMInteractCallback() {
        return this.onQMInteractCallback;
    }

    public void setOnQMInteractCallback(OnQMInteractCallback onQMInteractCallback) {
        this.onQMInteractCallback = onQMInteractCallback;
    }

    public void sendTaskListRequest() {
        if (this.tvSelected.isSelected()) {
            sendSelectedTaskListRequest(true);
        }
        if (this.tvAccepted.isSelected()) {
            sendAcceptTaskListRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeTaskTabView(boolean z) {
        this.tvSelected.setTextColor(ContextCompat.getColor(this.mContext, z ? R$color.fq_qm_primary : R$color.fq_qm_primary_light_2));
        int i = 0;
        this.tvSelected.setBackgroundResource(z ? R$drawable.fq_qm_primary_btn_light : 0);
        this.tvAccepted.setTextColor(ContextCompat.getColor(this.mContext, !z ? R$color.fq_qm_primary : R$color.fq_qm_primary_light_2));
        this.tvAccepted.setBackgroundResource(!z ? R$drawable.fq_qm_primary_btn_light : 0);
        this.rlSelectedListBg.setVisibility(z ? 0 : 4);
        this.rvAcceptedList.setVisibility(!z ? 0 : 4);
        this.tvSelected.setSelected(z);
        this.tvAccepted.setSelected(!z);
        ImageView imageView = this.ivAdd;
        if (z) {
            i = 4;
        }
        imageView.setVisibility(i);
        if (z) {
            sendSelectedTaskListRequest(true);
        } else {
            sendAcceptTaskListRequest();
        }
    }

    private void initSelectedTaskListAdapter() {
        ((DefaultItemAnimator) this.rvSelectedList.getItemAnimator()).setSupportsChangeAnimations(false);
        this.rvSelectedList.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.selectTaskListAdapter = new QMTaskListAdapter(R$layout.fq_item_list_qm_task_detail);
        this.rvSelectedList.setAdapter(this.selectTaskListAdapter);
        this.selectTaskListAdapter.bindToRecyclerView(this.rvSelectedList);
        this.selectTaskListAdapter.setEmptyView(new QMTaskListEmptyView(this.mContext, 58));
    }

    private void initAcceptTaskListAdapter() {
        ((DefaultItemAnimator) this.rvAcceptedList.getItemAnimator()).setSupportsChangeAnimations(false);
        this.rvAcceptedList.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.acceptTaskListAdapter = new QMTaskListAdapter(R$layout.fq_item_list_qm_task_detail);
        this.rvAcceptedList.setAdapter(this.acceptTaskListAdapter);
        this.acceptTaskListAdapter.bindToRecyclerView(this.rvAcceptedList);
        this.acceptTaskListAdapter.setEmptyView(new QMTaskListEmptyView(this.mContext, 58));
    }

    private void sendAcceptTaskListRequest() {
        ApiRetrofit.getInstance().getApiService().anchorAcceptedTaskListService(new RequestParams().getIntimateTaskListParams(this.liveId, "2")).map(new ServerResultFunction<List<QMInteractTaskEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.8
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<List<QMInteractTaskEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.7
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<QMInteractTaskEntity> list) {
                if (list == null) {
                    return;
                }
                QMTaskListDialog.this.acceptTaskListAdapter.setNewData(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSelectedTaskListRequest(final boolean z) {
        if (z) {
            this.pageNum = 1;
        }
        timerDispose();
        ApiRetrofit.getInstance().getApiService().anchorSelectTaskListService(new RequestParams().getIntimateTaskListParams(this.pageNum, this.liveId, "1")).map(new ServerResultFunction<HttpResultPageModel<QMInteractTaskEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.10
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<HttpResultPageModel<QMInteractTaskEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.9
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(HttpResultPageModel<QMInteractTaskEntity> httpResultPageModel) {
                if (httpResultPageModel == null || httpResultPageModel.dataList == null) {
                    return;
                }
                if (z) {
                    QMTaskListDialog.this.selectTaskListAdapter.replaceData(httpResultPageModel.dataList);
                } else {
                    QMTaskListDialog.this.selectTaskListAdapter.addData((Collection) httpResultPageModel.dataList);
                }
                AppUtils.updateRefreshLayoutFinishStatus(QMTaskListDialog.this.refreshSelectedList, httpResultPageModel.isMorePage(), z);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                if (!z) {
                    QMTaskListDialog.this.refreshSelectedList.finishLoadMore();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendTaskStatusUpdateRequest(final boolean z, final String str, final int i, final BaseQuickAdapter baseQuickAdapter, final QMInteractTaskEntity qMInteractTaskEntity) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().statusUpdateService(new RequestParams().getTaskStatusUpdateParams(qMInteractTaskEntity.f5845id, str)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.12
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.11
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                if (z) {
                    QMTaskListDialog.this.showLoadingDialog(true);
                }
            }

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Code restructure failed: missing block: B:18:0x0036, code lost:
                if (r8.equals(com.tomatolive.library.utils.ConstantUtils.QM_TASK_STATUS_202) != false) goto L10;
             */
            /* JADX WARN: Code restructure failed: missing block: B:45:0x0083, code lost:
                if (r8.equals(com.tomatolive.library.utils.ConstantUtils.QM_TASK_STATUS_102) != false) goto L25;
             */
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void accept(Object obj) {
                QMTaskListDialog.this.dismissProgressDialog();
                if (baseQuickAdapter instanceof QMTaskListAdapter) {
                    char c = 0;
                    if (QMTaskListDialog.this.tvSelected.isSelected()) {
                        String str2 = str;
                        switch (str2.hashCode()) {
                            case 49588:
                                break;
                            case 49589:
                                if (str2.equals(ConstantUtils.QM_TASK_STATUS_203)) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        if (c == 0 || c != 1) {
                            return;
                        }
                        QMTaskListDialog.this.showToast(R$string.fq_qm_task_accept_success_tips);
                        QMTaskListDialog.this.showTaskStartPlayDialog(qMInteractTaskEntity);
                        return;
                    }
                    String str3 = str;
                    switch (str3.hashCode()) {
                        case 48627:
                            break;
                        case 48629:
                            if (str3.equals(ConstantUtils.QM_TASK_STATUS_104)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 49590:
                            if (str3.equals(ConstantUtils.QM_TASK_STATUS_204)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 49591:
                            if (str3.equals(ConstantUtils.QM_TASK_STATUS_205)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    if (c == 0) {
                        return;
                    }
                    if (c == 1 || c == 2) {
                        if (QMTaskListDialog.this.onQMInteractCallback == null) {
                            return;
                        }
                        QMTaskListDialog.this.onQMInteractCallback.onTaskStatusUpdateListener(qMInteractTaskEntity.f5845id, str);
                    } else if (c != 3) {
                    } else {
                        QMInteractTaskEntity qMInteractTaskEntity2 = qMInteractTaskEntity;
                        qMInteractTaskEntity2.status = ConstantUtils.QM_TASK_STATUS_204;
                        baseQuickAdapter.setData(i, qMInteractTaskEntity2);
                        QMTaskListDialog.this.dismiss();
                    }
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                QMTaskListDialog.this.dismissProgressDialog();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void onError(int i2, String str2) {
                super.onError(i2, str2);
                QMTaskListDialog.this.dismissProgressDialog();
                if (TextUtils.equals(str, ConstantUtils.QM_TASK_STATUS_203) && QMTaskListDialog.this.isStartTimer(i2)) {
                    QMInteractTaskEntity qMInteractTaskEntity2 = qMInteractTaskEntity;
                    qMInteractTaskEntity2.isStartTask = true;
                    baseQuickAdapter.setData(i, qMInteractTaskEntity2);
                    QMTaskListDialog.this.startTimer(qMInteractTaskEntity.f5845id, str);
                }
                if (i2 == 200158 || i2 == 200159) {
                    QMTaskListDialog.this.sendSelectedTaskListRequest(true);
                }
            }
        });
    }

    public void sendTaskStatusUpdateRequest(String str, String str2) {
        sendTaskStatusUpdateRequest(str, str2, null);
    }

    public void sendTaskStatusUpdateRequest(String str, final String str2, final ResultCallBack<Object> resultCallBack) {
        ApiRetrofit.getInstance().getApiService().statusUpdateService(new RequestParams().getTaskStatusUpdateParams(str, str2)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.14
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.13
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                if (TextUtils.equals(str2, ConstantUtils.QM_TASK_STATUS_204)) {
                    QMTaskListDialog.this.dismiss();
                }
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onSuccess(obj);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void onError(int i, String str3) {
                super.onError(i, str3);
                ResultCallBack resultCallBack2 = resultCallBack;
                if (resultCallBack2 != null) {
                    resultCallBack2.onError(i, str3);
                }
            }
        });
    }

    public void updateTaskChargeList(final List<QMInteractTaskEntity> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Observable.just(this.acceptTaskListAdapter.getData()).map(new Function() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$QMTaskListDialog$tE4Ud3UjazCp4FdUOE-ZyOa2jSU
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return QMTaskListDialog.lambda$updateTaskChargeList$0(list, (List) obj);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.15
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                QMTaskListDialog.this.acceptTaskListAdapter.notifyDataSetChanged();
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
                if (TextUtils.equals(qMInteractTaskEntity.f5845id, qMInteractTaskEntity2.taskId)) {
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
        Observable.just(this.acceptTaskListAdapter.getData()).map(new Function() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$QMTaskListDialog$g1V1OIhn7HsEAXPpLayCHBDsjs8
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return QMTaskListDialog.lambda$completeTaskCharge$1(str, (List) obj);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.16
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                QMTaskListDialog.this.acceptTaskListAdapter.notifyDataSetChanged();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Boolean lambda$completeTaskCharge$1(String str, List list) throws Exception {
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            QMInteractTaskEntity qMInteractTaskEntity = (QMInteractTaskEntity) it2.next();
            if (TextUtils.equals(qMInteractTaskEntity.f5845id, str)) {
                qMInteractTaskEntity.status = ConstantUtils.QM_TASK_STATUS_103;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTaskStartPlayDialog(final QMInteractTaskEntity qMInteractTaskEntity) {
        this.qmTaskStartPlayAnimDialog = SureCancelDialog.newInstance(this.mContext.getString(R$string.fq_qm_task_start_tips, qMInteractTaskEntity.putName), qMInteractTaskEntity.taskName, this.mContext.getString(R$string.fq_qm_task_later_play_tips), this.mContext.getString(R$string.fq_qm_task_start_play_tips), R$color.fq_colorRed, new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        }, new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                QMTaskListDialog.this.sendTaskStatusUpdateRequest(qMInteractTaskEntity.f5845id, ConstantUtils.QM_TASK_STATUS_204);
            }
        });
        this.qmTaskStartPlayAnimDialog.show(getChildFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTimer(String str, String str2) {
        this.timerDisposable = Observable.timer(10L, TimeUnit.SECONDS).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C477219(str, str2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.view.dialog.QMTaskListDialog$19 */
    /* loaded from: classes3.dex */
    public class C477219 implements Consumer<Long> {
        final /* synthetic */ String val$id;
        final /* synthetic */ String val$status;

        C477219(String str, String str2) {
            this.val$id = str;
            this.val$status = str2;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Long l) throws Exception {
            if (QMTaskListDialog.this.timerDisposable != null) {
                QMTaskListDialog.this.sendTaskStatusUpdateRequest(this.val$id, this.val$status, new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.19.1
                    @Override // com.tomatolive.library.http.ResultCallBack
                    public void onSuccess(Object obj) {
                    }

                    @Override // com.tomatolive.library.http.ResultCallBack
                    public void onError(int i, String str) {
                        C477219 c477219 = C477219.this;
                        QMTaskListDialog.this.sendTaskStatusUpdateRequest(c477219.val$id, ConstantUtils.QM_TASK_STATUS_202, new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMTaskListDialog.19.1.1
                            @Override // com.tomatolive.library.http.ResultCallBack
                            public void onError(int i2, String str2) {
                            }

                            @Override // com.tomatolive.library.http.ResultCallBack
                            public void onSuccess(Object obj) {
                                QMTaskListDialog.this.sendSelectedTaskListRequest(true);
                            }
                        });
                    }
                });
            }
        }
    }

    private void timerDispose() {
        Disposable disposable = this.timerDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.timerDisposable.dispose();
        this.timerDisposable = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoadingDialog(boolean z) {
        LoadingDialog loadingDialog;
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(this.mContext, false);
        }
        if (!z || (loadingDialog = this.loadingDialog) == null || loadingDialog.isShowing()) {
            return;
        }
        this.loadingDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissProgressDialog() {
        try {
            if (this.loadingDialog == null || !this.loadingDialog.isShowing()) {
                return;
            }
            this.loadingDialog.dismiss();
        } catch (Exception unused) {
        }
    }
}
