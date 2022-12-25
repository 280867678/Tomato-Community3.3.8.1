package com.tomatolive.library.p136ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.IncomeMenuEntity;
import com.tomatolive.library.model.TotalAccountEntity;
import com.tomatolive.library.p136ui.activity.mylive.IncomeDetailActivity;
import com.tomatolive.library.p136ui.adapter.IncomeMenuAdapter;
import com.tomatolive.library.p136ui.presenter.IncomePresenter;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.headview.IncomeListHeadView;
import com.tomatolive.library.p136ui.view.iview.IIncomeView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.fragment.IncomeFragment */
/* loaded from: classes3.dex */
public class IncomeFragment extends BaseFragment<IncomePresenter> implements IIncomeView {
    private IncomeMenuAdapter mAdapter;
    private String mChoosedDate = DateUtils.getCurrentDateTime("yyyy-MM-dd");
    private IncomeListHeadView mDateQueryView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    public static IncomeFragment newInstance() {
        Bundle bundle = new Bundle();
        IncomeFragment incomeFragment = new IncomeFragment();
        incomeFragment.setArguments(bundle);
        return incomeFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public IncomePresenter mo6641createPresenter() {
        return new IncomePresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_income;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    protected View injectStateView(View view) {
        return view.findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        this.mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.refreshLayout);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        this.mDateQueryView = (IncomeListHeadView) view.findViewById(R$id.date_query_view);
        this.mSmartRefreshLayout.mo6487setEnableLoadMore(false);
        this.mDateQueryView.initData(-1, false);
        initAdapter();
    }

    private void initAdapter() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new IncomeMenuAdapter(R$layout.fq_item_list_income_menu);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 32));
        ((IncomePresenter) this.mPresenter).getDataList(this.mStateView, true, this.mChoosedDate);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mDateQueryView.setOnDateSelectedListener(new IncomeListHeadView.OnDateSelectedListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$IncomeFragment$v0kzuiA16k6jx8yFuu9_ScmOk-0
            @Override // com.tomatolive.library.p136ui.view.headview.IncomeListHeadView.OnDateSelectedListener
            public final void onDateSelected(Date date) {
                IncomeFragment.this.lambda$initListener$0$IncomeFragment(date);
            }
        });
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$IncomeFragment$_aOJLhUr3QtL_6bYMJcvq7qv-Uc
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                IncomeFragment.this.lambda$initListener$1$IncomeFragment();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$IncomeFragment$1_GDT1kz5mdtbOU4FE89lcARHOM
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                IncomeFragment.this.lambda$initListener$2$IncomeFragment(refreshLayout);
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$IncomeFragment$0M9Joy-tV2D8O8r01QsPob2k9lY
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                IncomeFragment.this.lambda$initListener$3$IncomeFragment(baseQuickAdapter, view2, i);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$IncomeFragment(Date date) {
        this.mChoosedDate = DateUtils.dateToString(date, "yyyy-MM-dd");
        ((IncomePresenter) this.mPresenter).getDataList(this.mStateView, true, this.mChoosedDate);
    }

    public /* synthetic */ void lambda$initListener$1$IncomeFragment() {
        ((IncomePresenter) this.mPresenter).getDataList(this.mStateView, true, this.mChoosedDate);
    }

    public /* synthetic */ void lambda$initListener$2$IncomeFragment(RefreshLayout refreshLayout) {
        ((IncomePresenter) this.mPresenter).getDataList(this.mStateView, false, this.mChoosedDate);
        this.mSmartRefreshLayout.mo6481finishRefresh();
    }

    public /* synthetic */ void lambda$initListener$3$IncomeFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        TotalAccountEntity totalAccountEntity = (TotalAccountEntity) baseQuickAdapter.getItem(i);
        if (totalAccountEntity == null) {
            return;
        }
        Intent intent = new Intent(this.mContext, IncomeDetailActivity.class);
        intent.putExtra(ConstantUtils.RESULT_ITEM, totalAccountEntity.getType());
        intent.putExtra(ConstantUtils.RESULT_FLAG, false);
        intent.putExtra(ConstantUtils.RESULT_DATE, this.mChoosedDate);
        startActivity(intent);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IIncomeView
    public void onDataListSuccess(IncomeMenuEntity incomeMenuEntity) {
        this.mDateQueryView.setCurrentGold(incomeMenuEntity.getTotalIncomePrice());
        this.mAdapter.setNewData(getTotalAccount(incomeMenuEntity));
    }

    private List<TotalAccountEntity> getTotalAccount(IncomeMenuEntity incomeMenuEntity) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TotalAccountEntity(1, R$drawable.fq_ic_gift, R$string.fq_gift_income, incomeMenuEntity.getGiftIncomePrice()));
        arrayList.add(new TotalAccountEntity(2, R$drawable.fq_ic_my_live_guard, R$string.fq_guard_income, incomeMenuEntity.getGuardIncomePrice()));
        arrayList.add(new TotalAccountEntity(5, R$drawable.fq_icons_nobilityxf, R$string.fq_noble_income, incomeMenuEntity.getNobilityIncomePrice()));
        arrayList.add(new TotalAccountEntity(4, R$drawable.fq_ic_props, R$string.fq_props_income, incomeMenuEntity.getPropsIncome()));
        arrayList.add(new TotalAccountEntity(6, R$drawable.fq_ic_turntable_gift, R$string.fq_turntable_gift, incomeMenuEntity.getLuckyGiftIncomePrice()));
        arrayList.add(new TotalAccountEntity(7, R$drawable.fq_ic_score_gift, R$string.fq_score_gift, incomeMenuEntity.getScoreGiftIncomePrice(), false));
        arrayList.add(new TotalAccountEntity(8, R$drawable.fq_ic_paidlive, R$string.fq_paid_live, incomeMenuEntity.getPaidLiveIncomePrice()));
        return arrayList;
    }
}
