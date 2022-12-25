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
import com.tomatolive.library.model.ExpenseMenuEntity;
import com.tomatolive.library.model.TotalAccountEntity;
import com.tomatolive.library.p136ui.activity.mylive.IncomeDetailActivity;
import com.tomatolive.library.p136ui.adapter.IncomeMenuAdapter;
import com.tomatolive.library.p136ui.presenter.ConsumePresenter;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.headview.IncomeListHeadView;
import com.tomatolive.library.p136ui.view.iview.IConsumeView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.fragment.ConsumeFragment */
/* loaded from: classes3.dex */
public class ConsumeFragment extends BaseFragment<ConsumePresenter> implements IConsumeView {
    private IncomeMenuAdapter mAdapter;
    private String mChoosedDate = DateUtils.getCurrentDateTime("yyyy-MM-dd");
    private IncomeListHeadView mDateQueryView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    public static ConsumeFragment newInstance() {
        Bundle bundle = new Bundle();
        ConsumeFragment consumeFragment = new ConsumeFragment();
        consumeFragment.setArguments(bundle);
        return consumeFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public ConsumePresenter mo6641createPresenter() {
        return new ConsumePresenter(this.mContext, this);
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
        this.mDateQueryView.initData(-1, true);
        initAdapter();
        ((ConsumePresenter) this.mPresenter).getDataList(this.mStateView, true, this.mChoosedDate);
    }

    private void initAdapter() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new IncomeMenuAdapter(R$layout.fq_item_list_income_menu);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 37));
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mDateQueryView.setOnDateSelectedListener(new IncomeListHeadView.OnDateSelectedListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$ConsumeFragment$ZnDQTmwxqBDBrOh_sjtUkzJukOY
            @Override // com.tomatolive.library.p136ui.view.headview.IncomeListHeadView.OnDateSelectedListener
            public final void onDateSelected(Date date) {
                ConsumeFragment.this.lambda$initListener$0$ConsumeFragment(date);
            }
        });
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$ConsumeFragment$hGAmTjfI7H42CJtjMCFaA2WhPRk
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                ConsumeFragment.this.lambda$initListener$1$ConsumeFragment();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$ConsumeFragment$GM54WWsji5GpVlRnAT-zlT2pMc8
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ConsumeFragment.this.lambda$initListener$2$ConsumeFragment(refreshLayout);
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$ConsumeFragment$pOPCBuunI0-1c6v9EKu3Uy6s9X4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                ConsumeFragment.this.lambda$initListener$3$ConsumeFragment(baseQuickAdapter, view2, i);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$ConsumeFragment(Date date) {
        this.mChoosedDate = DateUtils.dateToString(date, "yyyy-MM-dd");
        ((ConsumePresenter) this.mPresenter).getDataList(this.mStateView, true, this.mChoosedDate);
    }

    public /* synthetic */ void lambda$initListener$1$ConsumeFragment() {
        ((ConsumePresenter) this.mPresenter).getDataList(this.mStateView, true, this.mChoosedDate);
    }

    public /* synthetic */ void lambda$initListener$2$ConsumeFragment(RefreshLayout refreshLayout) {
        ((ConsumePresenter) this.mPresenter).getDataList(this.mStateView, false, this.mChoosedDate);
        this.mSmartRefreshLayout.mo6481finishRefresh();
    }

    public /* synthetic */ void lambda$initListener$3$ConsumeFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        TotalAccountEntity totalAccountEntity = (TotalAccountEntity) baseQuickAdapter.getItem(i);
        if (totalAccountEntity == null) {
            return;
        }
        Intent intent = new Intent(this.mContext, IncomeDetailActivity.class);
        intent.putExtra(ConstantUtils.RESULT_ITEM, totalAccountEntity.getType());
        intent.putExtra(ConstantUtils.RESULT_FLAG, true);
        intent.putExtra(ConstantUtils.RESULT_DATE, this.mChoosedDate);
        startActivity(intent);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IConsumeView
    public void onDataListSuccess(ExpenseMenuEntity expenseMenuEntity) {
        this.mDateQueryView.setCurrentGold(expenseMenuEntity.getTotalExpensePrice());
        this.mAdapter.setNewData(getTotalAccount(expenseMenuEntity));
    }

    private List<TotalAccountEntity> getTotalAccount(ExpenseMenuEntity expenseMenuEntity) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TotalAccountEntity(1, R$drawable.fq_ic_gift, R$string.fq_gift_expend, expenseMenuEntity.getGiftExpensePrice()));
        arrayList.add(new TotalAccountEntity(2, R$drawable.fq_ic_my_live_guard, R$string.fq_guard_expend, expenseMenuEntity.getGuardExpensePrice()));
        arrayList.add(new TotalAccountEntity(5, R$drawable.fq_icons_nobilityxf, R$string.fq_noble_expend, expenseMenuEntity.getNobilityExpensePrice()));
        arrayList.add(new TotalAccountEntity(3, R$drawable.fq_ic_my_live_car, R$string.fq_car_expend, expenseMenuEntity.getCarExpensePrice()));
        arrayList.add(new TotalAccountEntity(4, R$drawable.fq_ic_props, R$string.fq_props_expend, expenseMenuEntity.getPropsExpense()));
        arrayList.add(new TotalAccountEntity(6, R$drawable.fq_ic_turntable_gift, R$string.fq_turntable_gift, expenseMenuEntity.getLuckyGiftExpensePrice()));
        arrayList.add(new TotalAccountEntity(7, R$drawable.fq_ic_score_gift, R$string.fq_score_gift, expenseMenuEntity.getScoreGiftExpensePrice(), false));
        arrayList.add(new TotalAccountEntity(8, R$drawable.fq_ic_paidlive, R$string.fq_paid_live, expenseMenuEntity.getPaidLiveExpensePrice()));
        return arrayList;
    }
}
