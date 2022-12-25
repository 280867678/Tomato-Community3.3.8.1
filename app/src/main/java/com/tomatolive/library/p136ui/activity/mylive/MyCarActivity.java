package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.MyCarEntity;
import com.tomatolive.library.p136ui.activity.home.WebViewActivity;
import com.tomatolive.library.p136ui.adapter.MyCarAdapter;
import com.tomatolive.library.p136ui.presenter.MyCarPresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerCarMall;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.iview.IMyCarView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.mylive.MyCarActivity */
/* loaded from: classes3.dex */
public class MyCarActivity extends BaseActivity<MyCarPresenter> implements IMyCarView {
    private MyCarAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initListener$2(BaseQuickAdapter baseQuickAdapter, View view, int i) {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyCarView
    public void onUseCarFail() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public MyCarPresenter mo6636createPresenter() {
        return new MyCarPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_car_my;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        setActivityRightIconTitle(getString(R$string.fq_my_live_my_garage), R$drawable.fq_ic_my_live_car_help, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyCarActivity$EF2hL1TDP2S0mS-KuIq5TF24lDw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyCarActivity.this.lambda$initView$0$MyCarActivity(view);
            }
        });
        initAdapter();
        ((MyCarPresenter) this.mPresenter).getMyCar(this.mStateView, this.pageNum, true, true);
    }

    public /* synthetic */ void lambda$initView$0$MyCarActivity(View view) {
        Intent intent = new Intent(this.mContext, WebViewActivity.class);
        intent.putExtra(ConstantUtils.WEB_VIEW_FROM_SERVICE, true);
        intent.putExtra("url", ConstantUtils.APP_PARAM_CAR_DESC);
        intent.putExtra("title", this.mContext.getString(R$string.fq_car_desc));
        this.mContext.startActivity(intent);
    }

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 2));
        this.mRecyclerView.addItemDecoration(new RVDividerCarMall(this.mContext, 17170445));
        this.mAdapter = new MyCarAdapter(R$layout.fq_item_list_car_mall);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 41));
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyCarActivity$S179k1720c5BQmw5regG-mY8hok
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                MyCarActivity.this.lambda$initListener$1$MyCarActivity();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.activity.mylive.MyCarActivity.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                MyCarActivity myCarActivity = MyCarActivity.this;
                myCarActivity.pageNum++;
                ((MyCarPresenter) ((BaseActivity) myCarActivity).mPresenter).getMyCar(((BaseActivity) MyCarActivity.this).mStateView, MyCarActivity.this.pageNum, false, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                MyCarActivity myCarActivity = MyCarActivity.this;
                myCarActivity.pageNum = 1;
                ((MyCarPresenter) ((BaseActivity) myCarActivity).mPresenter).getMyCar(((BaseActivity) MyCarActivity.this).mStateView, MyCarActivity.this.pageNum, false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.mAdapter.setOnItemClickListener($$Lambda$MyCarActivity$YrQMsTA1BajIZyZmClZWvlj1KI.INSTANCE);
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyCarActivity$1HrXOxEDkspIitlzkreWhYlMuZA
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MyCarActivity.this.lambda$initListener$3$MyCarActivity(baseQuickAdapter, view, i);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$MyCarActivity() {
        this.pageNum = 1;
        ((MyCarPresenter) this.mPresenter).getMyCar(this.mStateView, this.pageNum, true, true);
    }

    public /* synthetic */ void lambda$initListener$3$MyCarActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        MyCarEntity myCarEntity = (MyCarEntity) baseQuickAdapter.getItem(i);
        if (view.getId() != R$id.tv_money || myCarEntity == null) {
            return;
        }
        myCarEntity.isUsed = myCarEntity.isEquipage() ? "0" : "1";
        baseQuickAdapter.setData(i, myCarEntity);
        showToast(myCarEntity.isEquipage() ? R$string.fq_car_equipage_success_tips : R$string.fq_car_equipage_cancel_tips);
        ((MyCarPresenter) this.mPresenter).useCar(myCarEntity);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyCarView
    public void onDataListSuccess(List<MyCarEntity> list, boolean z, boolean z2) {
        this.mAdapter.setNewData(list);
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyCarView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyCarView
    public void onUseCarSuccess(MyCarEntity myCarEntity) {
        ((MyCarPresenter) this.mPresenter).getMyCar(this.mStateView, this.pageNum, false, true);
    }
}
