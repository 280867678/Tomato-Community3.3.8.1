package com.tomatolive.library.p136ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.WeekStarAnchorEntity;
import com.tomatolive.library.model.WeekStarRankingEntity;
import com.tomatolive.library.model.event.WeekStarAnchorEvent;
import com.tomatolive.library.p136ui.adapter.WeekStarRankingAdapter;
import com.tomatolive.library.p136ui.presenter.WeekStarRankingPresenter;
import com.tomatolive.library.p136ui.view.dialog.CommonRuleTipsDialog;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.iview.IWeekStarRankingView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.fragment.RankingWeekStarFragment */
/* loaded from: classes3.dex */
public class RankingWeekStarFragment extends BaseFragment<WeekStarRankingPresenter> implements IWeekStarRankingView {
    private WeekStarRankingAdapter mAdapter;
    private List<WeekStarRankingEntity> mDataList = new ArrayList();
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override // com.tomatolive.library.p136ui.view.iview.IWeekStarRankingView
    public void onDataListFail(boolean z) {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    public static RankingWeekStarFragment newInstance() {
        Bundle bundle = new Bundle();
        RankingWeekStarFragment rankingWeekStarFragment = new RankingWeekStarFragment();
        rankingWeekStarFragment.setArguments(bundle);
        return rankingWeekStarFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public WeekStarRankingPresenter mo6641createPresenter() {
        return new WeekStarRankingPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_ranking_week_star;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    protected View injectStateView(View view) {
        return view.findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        this.mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.refreshLayout);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        initAdapter();
        ((WeekStarRankingPresenter) this.mPresenter).getDataList(this.mStateView, true, false);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.tomatolive.library.ui.fragment.RankingWeekStarFragment.1
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ((WeekStarRankingPresenter) ((BaseFragment) RankingWeekStarFragment.this).mPresenter).getDataList(((BaseFragment) RankingWeekStarFragment.this).mStateView, false, true);
                RankingWeekStarFragment.this.mSmartRefreshLayout.mo6481finishRefresh();
            }
        });
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.RankingWeekStarFragment.2
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public void onRetryClick() {
                ((WeekStarRankingPresenter) ((BaseFragment) RankingWeekStarFragment.this).mPresenter).getDataList(((BaseFragment) RankingWeekStarFragment.this).mStateView, true, false);
            }
        });
        this.mAdapter.setListener(new WeekStarRankingAdapter.OnWeekStarRankingListener() { // from class: com.tomatolive.library.ui.fragment.RankingWeekStarFragment.3
            @Override // com.tomatolive.library.p136ui.adapter.WeekStarRankingAdapter.OnWeekStarRankingListener
            public void onRuleClick() {
                CommonRuleTipsDialog.newInstance(ConstantUtils.APP_PARAM_WEEK_STAR_DESC, RankingWeekStarFragment.this.getString(R$string.fq_week_star_rule_description)).show(RankingWeekStarFragment.this.getChildFragmentManager());
            }

            @Override // com.tomatolive.library.p136ui.adapter.WeekStarRankingAdapter.OnWeekStarRankingListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                WeekStarAnchorEntity weekStarAnchorEntity = (WeekStarAnchorEntity) baseQuickAdapter.getItem(i);
                if (weekStarAnchorEntity != null && !TextUtils.isEmpty(weekStarAnchorEntity.liveId)) {
                    AppUtils.startTomatoLiveActivity(((BaseFragment) RankingWeekStarFragment.this).mContext, AppUtils.formatLiveEntity(AppUtils.formatLiveEntity(weekStarAnchorEntity)), "2", RankingWeekStarFragment.this.getString(R$string.fq_live_enter_source_week_star_ranking));
                }
            }
        });
    }

    private void initAdapter() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new WeekStarRankingAdapter(getChildFragmentManager(), this.mDataList);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 42));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWeekStarRankingView
    public void onDataListSuccess(List<WeekStarRankingEntity> list, boolean z) {
        this.mDataList = list;
        this.mAdapter.setNewData(this.mDataList);
        if (z) {
            String currentViewPagerMarkId = this.mAdapter.getCurrentViewPagerMarkId();
            if (TextUtils.isEmpty(currentViewPagerMarkId)) {
                return;
            }
            EventBus.getDefault().post(new WeekStarAnchorEvent(currentViewPagerMarkId));
        }
    }
}
