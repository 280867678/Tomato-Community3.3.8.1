package com.tomatolive.library.p136ui.view.dialog;

import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.p136ui.adapter.PopularCardAdapter;
import com.tomatolive.library.p136ui.view.dialog.UsePopularCardDialog;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.utils.ConstantUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.PopularCardDialog */
/* loaded from: classes3.dex */
public class PopularCardDialog extends BaseBottomDialogFragment {
    private PopularCardAdapter adapter;
    private PopularCardAdapter.OnPopularCardListener listener;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView recyclerView;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        return 0.0f;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public double getHeightScale() {
        return 0.45d;
    }

    public void setListener(PopularCardAdapter.OnPopularCardListener onPopularCardListener) {
        this.listener = onPopularCardListener;
    }

    public static PopularCardDialog newInstance(PopularCardAdapter.OnPopularCardListener onPopularCardListener) {
        PopularCardDialog popularCardDialog = new PopularCardDialog();
        popularCardDialog.setListener(onPopularCardListener);
        return popularCardDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_layout_bottom_popular_card_view;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initView(View view) {
        this.mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.refreshLayout);
        this.adapter = new PopularCardAdapter(R$layout.fq_item_list_popular_card_view);
        this.recyclerView = (RecyclerView) view.findViewById(R$id.rv_operate);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.bindToRecyclerView(this.recyclerView);
        this.adapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 46));
        this.adapter.setHeaderAndEmpty(true);
        this.adapter.setOnPopularCardListener(new PopularCardAdapter.OnPopularCardListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$PopularCardDialog$HW0Ae9cw2bNgQ0cZTUg0Abh3SdI
            @Override // com.tomatolive.library.p136ui.adapter.PopularCardAdapter.OnPopularCardListener
            public final void onItemClick(PopularCardEntity popularCardEntity) {
                PopularCardDialog.this.lambda$initView$1$PopularCardDialog(popularCardEntity);
            }
        });
        view.findViewById(R$id.iv_ask).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$PopularCardDialog$TJu-DMm83OrDOAyzmxfvFd0kt7w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PopularCardDialog.this.lambda$initView$2$PopularCardDialog(view2);
            }
        });
        this.mSmartRefreshLayout.mo6487setEnableLoadMore(false);
        this.mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$PopularCardDialog$JD5vuTPpH0T-pmvgmplVBSSho84
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                PopularCardDialog.this.lambda$initView$3$PopularCardDialog(refreshLayout);
            }
        });
        this.mSmartRefreshLayout.autoRefresh();
        sendRequest();
    }

    public /* synthetic */ void lambda$initView$1$PopularCardDialog(final PopularCardEntity popularCardEntity) {
        UsePopularCardDialog.newInstance(popularCardEntity, new UsePopularCardDialog.PopularClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$PopularCardDialog$uExgNyjDN-WsP8TjJjufVHxn0UI
            @Override // com.tomatolive.library.p136ui.view.dialog.UsePopularCardDialog.PopularClickListener
            public final void onClick() {
                PopularCardDialog.this.lambda$null$0$PopularCardDialog(popularCardEntity);
            }
        }).show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$null$0$PopularCardDialog(PopularCardEntity popularCardEntity) {
        dismiss();
        this.listener.onItemClick(popularCardEntity);
    }

    public /* synthetic */ void lambda$initView$2$PopularCardDialog(View view) {
        CommonRuleTipsDialog.newInstance(ConstantUtils.APP_PARAM_POPULARITY_CARD_DESC, getString(R$string.fq_popular_card)).show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$initView$3$PopularCardDialog(RefreshLayout refreshLayout) {
        sendRequest();
        refreshLayout.mo6481finishRefresh();
    }

    private void sendRequest() {
        ApiRetrofit.getInstance().getApiService().getPopularityCardListService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<PopularCardEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.PopularCardDialog.2
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new HttpRxObserver(this.mContext, new ResultCallBack<List<PopularCardEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.PopularCardDialog.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<PopularCardEntity> list) {
                if (list != null) {
                    PopularCardDialog.this.adapter.setNewData(list);
                }
            }
        }));
    }

    public void refresh() {
        this.mSmartRefreshLayout.autoRefresh();
        sendRequest();
    }
}
