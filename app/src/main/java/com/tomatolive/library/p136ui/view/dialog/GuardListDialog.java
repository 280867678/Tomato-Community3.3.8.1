package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.GuardItemEntity;
import com.tomatolive.library.p136ui.adapter.AnchorGuardAdapter;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.p136ui.view.emptyview.GuardListEmptyView;
import com.tomatolive.library.p136ui.view.headview.GuardListHeadView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.NumberUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.GuardListDialog */
/* loaded from: classes3.dex */
public class GuardListDialog extends BaseDialogFragment {
    private static final String LIVE_TYPE = "liveType";
    private static final String SER_ITEM = "serItem";
    private GuardListEmptyView emptyView;
    private GuardItemEntity guardItem;
    private GuardListHeadView headView;
    private AnchorGuardAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private StateView mStateView;
    private View.OnClickListener openListener;
    private int pageNum = 1;
    private TextView tvContinue;
    private TextView tvOpen;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getHeightScale() {
        return 0.65d;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.75d;
    }

    static /* synthetic */ int access$008(GuardListDialog guardListDialog) {
        int i = guardListDialog.pageNum;
        guardListDialog.pageNum = i + 1;
        return i;
    }

    public static GuardListDialog newInstance(int i, GuardItemEntity guardItemEntity, View.OnClickListener onClickListener) {
        GuardListDialog guardListDialog = new GuardListDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SER_ITEM, guardItemEntity);
        bundle.putInt("liveType", i);
        guardListDialog.setOpenGuardListener(onClickListener);
        guardListDialog.setArguments(bundle);
        return guardListDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_anchor_guard;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        GuardItemEntity guardItemEntity;
        this.guardItem = (GuardItemEntity) getArguments().getParcelable(SER_ITEM);
        int argumentsInt = getArgumentsInt("liveType", 2);
        this.mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.refreshLayout);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        this.tvOpen = (TextView) view.findViewById(R$id.tv_open);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R$id.rl_continue_bg);
        TextView textView = (TextView) view.findViewById(R$id.tv_continue_tips);
        this.tvContinue = (TextView) view.findViewById(R$id.tv_continue);
        RelativeLayout relativeLayout2 = (RelativeLayout) view.findViewById(R$id.rl_open_bg);
        View findViewById = view.findViewById(R$id.v_divider);
        this.emptyView = (GuardListEmptyView) view.findViewById(R$id.empty_view);
        this.mStateView = StateView.inject((ViewGroup) ((FrameLayout) view.findViewById(R$id.fl_content_view)));
        ((TextView) view.findViewById(R$id.tv_guard_num)).setText(formatGuardCountStr());
        initAdapter();
        sendRequest(true, true);
        findViewById.setVisibility(AppUtils.isAudienceLiveType(argumentsInt) ? 0 : 4);
        relativeLayout2.setVisibility(AppUtils.isAudienceLiveType(argumentsInt) ? 0 : 4);
        if (!AppUtils.isAudienceLiveType(argumentsInt) || (guardItemEntity = this.guardItem) == null) {
            return;
        }
        if (TextUtils.equals(guardItemEntity.userGuardType, "0")) {
            relativeLayout.setVisibility(4);
            this.tvOpen.setVisibility(0);
            return;
        }
        relativeLayout.setVisibility(0);
        this.tvOpen.setVisibility(4);
        textView.setText(Html.fromHtml(this.mContext.getString(R$string.fq_guard_open_maturity_date_tips, getGuardType(this.guardItem.userGuardType), this.guardItem.userGuardExpireTime)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardListDialog$6mTtmcrRWBFanmri4SiEZP5t7CE
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                GuardListDialog.this.lambda$initListener$0$GuardListDialog();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.view.dialog.GuardListDialog.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                GuardListDialog.access$008(GuardListDialog.this);
                GuardListDialog.this.sendRequest(false, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                GuardListDialog.this.pageNum = 1;
                GuardListDialog.this.sendRequest(true, false);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.tvOpen.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardListDialog$-1PU67rfuCO4PtTX9_cVqH3-TAg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GuardListDialog.this.lambda$initListener$1$GuardListDialog(view2);
            }
        });
        this.tvContinue.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardListDialog$jRJr47zCjNOlyJmFS7CvHdXVrGk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GuardListDialog.this.lambda$initListener$2$GuardListDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$GuardListDialog() {
        this.pageNum = 1;
        sendRequest(true, true);
    }

    public /* synthetic */ void lambda$initListener$1$GuardListDialog(View view) {
        if (this.openListener != null) {
            dismiss();
            this.openListener.onClick(view);
        }
    }

    public /* synthetic */ void lambda$initListener$2$GuardListDialog(View view) {
        if (this.openListener != null) {
            dismiss();
            this.openListener.onClick(view);
        }
    }

    private void initAdapter() {
        this.headView = new GuardListHeadView(this.mContext);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new AnchorGuardAdapter(R$layout.fq_item_list_anchor_guard);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.addHeaderView(this.headView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest(final boolean z, boolean z2) {
        if (this.guardItem == null) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().getAnchorGuardListService(new RequestParams().getAnchorGuardListParams(this.guardItem.anchorId, this.pageNum)).map(new ServerResultFunction<HttpResultPageModel<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.GuardListDialog.3
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new HttpRxObserver(this.mContext, new ResultCallBack<HttpResultPageModel<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.GuardListDialog.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(HttpResultPageModel<AnchorEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                if (z) {
                    GuardListDialog.this.headView.initData(httpResultPageModel.dataList);
                    if (httpResultPageModel.dataList.size() >= 1) {
                        if (NumberUtils.string2int(httpResultPageModel.dataList.get(0).contribution, 0) > 0) {
                            AnchorGuardAdapter anchorGuardAdapter = GuardListDialog.this.mAdapter;
                            List<AnchorEntity> list = httpResultPageModel.dataList;
                            anchorGuardAdapter.setNewData(list.subList(1, list.size()));
                        } else {
                            GuardListDialog.this.mAdapter.setNewData(httpResultPageModel.dataList);
                        }
                    }
                } else {
                    GuardListDialog.this.mAdapter.addData((Collection) httpResultPageModel.dataList);
                }
                AppUtils.updateRefreshLayoutFinishStatus(GuardListDialog.this.mSmartRefreshLayout, httpResultPageModel.isMorePage(), z);
                GuardListDialog.this.showContentView(httpResultPageModel.isNoEmptyData());
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                if (!z) {
                    GuardListDialog.this.mSmartRefreshLayout.finishLoadMore();
                }
            }
        }, this.mStateView, z2));
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        setOpenGuardListener(null);
    }

    private void setOpenGuardListener(View.OnClickListener onClickListener) {
        this.openListener = onClickListener;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private String getGuardType(String str) {
        char c;
        switch (str.hashCode()) {
            case 49:
                if (str.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (str.equals("2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (str.equals("3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c != 0) {
            if (c == 1) {
                return this.mContext.getString(R$string.fq_guard_month);
            }
            return c != 2 ? "" : this.mContext.getString(R$string.fq_guard_year);
        }
        return this.mContext.getString(R$string.fq_guard_week);
    }

    private String formatGuardCountStr() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.mContext.getString(R$string.fq_guard_anchor));
        stringBuffer.append("(");
        stringBuffer.append(this.guardItem.anchorGuardCount);
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showContentView(boolean z) {
        int i = 4;
        this.emptyView.setVisibility(z ? 4 : 0);
        RecyclerView recyclerView = this.mRecyclerView;
        if (z) {
            i = 0;
        }
        recyclerView.setVisibility(i);
    }
}
