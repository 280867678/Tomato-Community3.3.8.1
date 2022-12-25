package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.IncomeEntity;
import com.tomatolive.library.model.PaidLiveIncomeExpenseEntity;
import com.tomatolive.library.p136ui.adapter.IncomeDetailAdapter;
import com.tomatolive.library.p136ui.presenter.IncomeDetailPresenter;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.headview.IncomeListHeadView;
import com.tomatolive.library.p136ui.view.iview.IIncomeDetailView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.mylive.IncomeDetailActivity */
/* loaded from: classes3.dex */
public class IncomeDetailActivity extends BaseActivity<IncomeDetailPresenter> implements IIncomeDetailView {
    private int incomeType;
    private IncomeDetailAdapter mAdapter;
    private IncomeListHeadView mDateQueryView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private boolean isExpend = false;
    private boolean isFree = true;
    private String mChoosedDate = DateUtils.getCurrentDateTime("yyyy-MM-dd");

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public IncomeDetailPresenter mo6636createPresenter() {
        return new IncomeDetailPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_income_detail;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.incomeType = getIntent().getIntExtra(ConstantUtils.RESULT_ITEM, 1);
        this.isExpend = getIntent().getBooleanExtra(ConstantUtils.RESULT_FLAG, false);
        String stringExtra = getIntent().getStringExtra(ConstantUtils.RESULT_DATE);
        if (!TextUtils.isEmpty(stringExtra)) {
            this.mChoosedDate = stringExtra;
        }
        setActivityTitle(formatTitleStr());
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mDateQueryView = (IncomeListHeadView) findViewById(R$id.date_query_view);
        this.mDateQueryView.initData(this.incomeType, this.isExpend);
        initAdapter();
        this.mDateQueryView.setSelectDate(this.mChoosedDate);
    }

    private void initAdapter() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new IncomeDetailAdapter(R$layout.fq_item_list_income_detail, this.incomeType, this.isExpend);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, this.isExpend ? 37 : 32));
        sendRequest(true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest(boolean z, boolean z2) {
        int i = this.incomeType;
        if (i == 4) {
            if (this.isExpend) {
                ((IncomeDetailPresenter) this.mPresenter).getPropsExpenseDataList(this.mStateView, this.pageNum, z, z2, this.mChoosedDate, this.isFree);
            } else {
                ((IncomeDetailPresenter) this.mPresenter).getPropsIncomeDataList(this.mStateView, this.pageNum, z, z2, this.mChoosedDate, this.isFree);
            }
        } else if (this.isExpend) {
            ((IncomeDetailPresenter) this.mPresenter).getExpenseDataList(this.mStateView, this.pageNum, z, z2, i, this.mChoosedDate);
        } else {
            ((IncomeDetailPresenter) this.mPresenter).getIncomeDataList(this.mStateView, this.pageNum, z, z2, i, this.mChoosedDate);
        }
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$IncomeDetailActivity$OEubVopUvQtu-1zvHeNLD8_d8XY
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                IncomeDetailActivity.this.lambda$initListener$0$IncomeDetailActivity();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.activity.mylive.IncomeDetailActivity.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                IncomeDetailActivity incomeDetailActivity = IncomeDetailActivity.this;
                incomeDetailActivity.pageNum++;
                incomeDetailActivity.sendRequest(false, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                IncomeDetailActivity incomeDetailActivity = IncomeDetailActivity.this;
                incomeDetailActivity.pageNum = 1;
                incomeDetailActivity.sendRequest(false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.mDateQueryView.setOnDateSelectedListener(new IncomeListHeadView.OnDateSelectedListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$IncomeDetailActivity$j6T9JM2T43w_aDoLibGNiPSGI7w
            @Override // com.tomatolive.library.p136ui.view.headview.IncomeListHeadView.OnDateSelectedListener
            public final void onDateSelected(Date date) {
                IncomeDetailActivity.this.lambda$initListener$1$IncomeDetailActivity(date);
            }
        });
        this.mDateQueryView.setOnPropsDateSelectedListener(new IncomeListHeadView.OnPropsDateSelectedListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$IncomeDetailActivity$AYL65M1WwgOVbOc-vbjGzCInlcI
            @Override // com.tomatolive.library.p136ui.view.headview.IncomeListHeadView.OnPropsDateSelectedListener
            public final void onDateSelected(Date date, boolean z) {
                IncomeDetailActivity.this.lambda$initListener$2$IncomeDetailActivity(date, z);
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.IncomeDetailActivity.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                IncomeEntity incomeEntity = (IncomeEntity) baseQuickAdapter.getItem(i);
                if (incomeEntity != null && !IncomeDetailActivity.this.isExpend && IncomeDetailActivity.this.incomeType == 8 && (incomeEntity instanceof PaidLiveIncomeExpenseEntity)) {
                    Intent intent = new Intent(((BaseActivity) IncomeDetailActivity.this).mContext, UserIncomeDetailActivity.class);
                    intent.putExtra(ConstantUtils.RESULT_ITEM, (PaidLiveIncomeExpenseEntity) incomeEntity);
                    IncomeDetailActivity.this.startActivity(intent);
                }
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$IncomeDetailActivity() {
        this.pageNum = 1;
        sendRequest(true, true);
    }

    public /* synthetic */ void lambda$initListener$1$IncomeDetailActivity(Date date) {
        this.pageNum = 1;
        this.mChoosedDate = DateUtils.dateToString(date, "yyyy-MM-dd");
        sendRequest(false, true);
    }

    public /* synthetic */ void lambda$initListener$2$IncomeDetailActivity(Date date, boolean z) {
        this.isFree = z;
        this.pageNum = 1;
        this.mChoosedDate = DateUtils.dateToString(date, "yyyy-MM-dd");
        sendRequest(false, true);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IIncomeDetailView
    public void onDataListSuccess(List<? extends IncomeEntity> list, boolean z, boolean z2, String str) {
        this.mDateQueryView.setCurrentGold(str);
        if (z2) {
            this.mAdapter.replaceData(list);
        } else {
            this.mAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IIncomeDetailView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }

    @StringRes
    private int formatTitleStr() {
        switch (this.incomeType) {
            case 1:
                return this.isExpend ? R$string.fq_gift_expend_detail : R$string.fq_gift_income_detail;
            case 2:
                return this.isExpend ? R$string.fq_guard_expend_detail : R$string.fq_guard_income_detail;
            case 3:
                return this.isExpend ? R$string.fq_car_expend_detail : R$string.fq_my_live_consume_detail;
            case 4:
                return this.isExpend ? R$string.fq_props_expend_detail : R$string.fq_props_income_detail;
            case 5:
                return this.isExpend ? R$string.fq_noble_expend : R$string.fq_noble_income;
            case 6:
                boolean z = this.isExpend;
                return R$string.fq_turntable_gift;
            case 7:
                boolean z2 = this.isExpend;
                return R$string.fq_score_gift;
            case 8:
                boolean z3 = this.isExpend;
                return R$string.fq_paid_live;
            default:
                return this.isExpend ? R$string.fq_my_live_consume_detail : R$string.fq_my_live_income_detail;
        }
    }
}
