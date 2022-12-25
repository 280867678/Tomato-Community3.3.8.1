package com.tomatolive.library.p136ui.activity.mylive;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.MyClanEntity;
import com.tomatolive.library.p136ui.adapter.MyClanAdapter;
import com.tomatolive.library.p136ui.presenter.MyClanPresenter;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.iview.IMyClanView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.mylive.MyClanActivity */
/* loaded from: classes3.dex */
public class MyClanActivity extends BaseActivity<MyClanPresenter> implements IMyClanView {
    public static final int LIVE_STATUS_ALL = -1;
    public static final int LIVE_STATUS_ALREADY = 1;
    public static final int LIVE_STATUS_NOT = 0;
    private int liveStatus = -1;
    private MyClanAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private TextView tvAllRecord;
    private TextView tvAlreadyRecord;
    private TextView tvNotRecord;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public MyClanPresenter mo6636createPresenter() {
        return new MyClanPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_my_clan;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_my_clan);
        this.tvAllRecord = (TextView) findViewById(R$id.tv_all_record);
        this.tvAlreadyRecord = (TextView) findViewById(R$id.tv_already_record);
        this.tvNotRecord = (TextView) findViewById(R$id.tv_not_record);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_live_status_menu);
        this.tvAllRecord.setText(stringArray[0]);
        this.tvAlreadyRecord.setText(stringArray[1]);
        this.tvNotRecord.setText(stringArray[2]);
        initAdapter();
        initRecordStatus(-1, true, true);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyClanActivity$pI6B2NJptBEONFqo4HH0HKMai8g
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                MyClanActivity.this.lambda$initListener$0$MyClanActivity();
            }
        });
        this.tvAllRecord.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.MyClanActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (MyClanActivity.this.tvAllRecord.isSelected()) {
                    return;
                }
                MyClanActivity.this.initRecordStatus(-1, false, true);
            }
        });
        this.tvAlreadyRecord.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.MyClanActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (MyClanActivity.this.tvAlreadyRecord.isSelected()) {
                    return;
                }
                MyClanActivity.this.initRecordStatus(1, false, true);
            }
        });
        this.tvNotRecord.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.MyClanActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (MyClanActivity.this.tvNotRecord.isSelected()) {
                    return;
                }
                MyClanActivity.this.initRecordStatus(0, false, true);
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.activity.mylive.MyClanActivity.4
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                MyClanActivity myClanActivity = MyClanActivity.this;
                myClanActivity.pageNum++;
                myClanActivity.sendRequest(false, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                MyClanActivity myClanActivity = MyClanActivity.this;
                myClanActivity.pageNum = 1;
                myClanActivity.sendRequest(false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.MyClanActivity.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MyClanEntity myClanEntity = (MyClanEntity) baseQuickAdapter.getItem(i);
                if (myClanEntity == null) {
                    return;
                }
                LiveEntity liveEntity = new LiveEntity();
                liveEntity.liveId = myClanEntity.liveId;
                AppUtils.startTomatoLiveActivity(((BaseActivity) MyClanActivity.this).mContext, liveEntity, "2", MyClanActivity.this.getString(R$string.fq_hot_list));
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$MyClanActivity() {
        this.pageNum = 1;
        sendRequest(true, true);
    }

    private void initAdapter() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerView.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_view_divider_color));
        this.mAdapter = new MyClanAdapter(R$layout.fq_item_list_my_clan);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext));
        this.mAdapter.setLiveStatus(this.liveStatus);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRecordStatus(int i, boolean z, boolean z2) {
        boolean z3 = true;
        this.pageNum = 1;
        this.liveStatus = i;
        this.mAdapter.setLiveStatus(i);
        this.tvAllRecord.setSelected(i == -1);
        this.tvAlreadyRecord.setSelected(i == 1);
        TextView textView = this.tvNotRecord;
        if (i != 0) {
            z3 = false;
        }
        textView.setSelected(z3);
        sendRequest(z, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest(boolean z, boolean z2) {
        this.mSmartRefreshLayout.resetNoMoreData();
        ((MyClanPresenter) this.mPresenter).getDataList(this.mStateView, this.liveStatus, this.pageNum, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyClanView
    public void onDataListSuccess(List<MyClanEntity> list, boolean z, boolean z2) {
        if (z2) {
            this.mAdapter.setNewData(list);
        } else {
            this.mAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyClanView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }
}
