package com.tomatolive.library.p136ui.activity.mylive;

import android.os.Bundle;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.p135db.WatchRecordEntity;
import com.tomatolive.library.p136ui.adapter.WatchRecordAdapter;
import com.tomatolive.library.p136ui.presenter.WatchRecordPresenter;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.iview.IWatchRecordView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.UserInfoManager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.mylive.WatchRecordActivity */
/* loaded from: classes3.dex */
public class WatchRecordActivity extends BaseActivity<WatchRecordPresenter> implements IWatchRecordView {
    private WatchRecordAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override // com.tomatolive.library.p136ui.view.iview.IWatchRecordView
    public void onDataListFail() {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWatchRecordView
    public void onDataListSuccess(List<LiveEntity> list, boolean z, boolean z2) {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public WatchRecordPresenter mo6636createPresenter() {
        return new WatchRecordPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_watch_record;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityRightTitle(R$string.fq_my_live_watch_record, R$string.fq_text_history_clear, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.WatchRecordActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (WatchRecordActivity.this.mAdapter == null || WatchRecordActivity.this.mAdapter.getData().size() != 0) {
                    SureCancelDialog.newInstance(WatchRecordActivity.this.getString(R$string.fq_sure_clear_all_record), new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.WatchRecordActivity.1.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view2) {
                            DBUtils.deleteAll(WatchRecordEntity.class);
                            WatchRecordActivity.this.mAdapter.clearAll();
                        }
                    }).show(WatchRecordActivity.this.getSupportFragmentManager());
                }
            }
        });
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mSmartRefreshLayout.setEnableRefresh(false);
        this.mSmartRefreshLayout.mo6487setEnableLoadMore(false);
        initAdapter();
        loadLocalData();
    }

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mRecyclerView.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_view_divider_color));
        this.mAdapter = new WatchRecordAdapter(R$layout.fq_item_list_watch_record);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 39));
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.WatchRecordActivity.2
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public void onRetryClick() {
                WatchRecordActivity watchRecordActivity = WatchRecordActivity.this;
                watchRecordActivity.pageNum = 1;
                watchRecordActivity.loadLocalData();
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.WatchRecordActivity.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
                final LiveEntity formatLiveEntity = WatchRecordActivity.this.formatLiveEntity((WatchRecordEntity) baseQuickAdapter.getItem(i));
                if (view.getId() == R$id.rl_content) {
                    if (formatLiveEntity == null) {
                        return;
                    }
                    AppUtils.startTomatoLiveActivity(((BaseActivity) WatchRecordActivity.this).mContext, formatLiveEntity, "2", WatchRecordActivity.this.getString(R$string.fq_live_enter_source_watch_history));
                } else if (view.getId() != R$id.tv_delete) {
                } else {
                    SureCancelDialog.newInstance(WatchRecordActivity.this.getString(R$string.fq_sure_del_all_record), new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.WatchRecordActivity.3.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view2) {
                            DBUtils.deleteAllWithCondition(WatchRecordEntity.class, "liveId = ?", formatLiveEntity.liveId);
                            WatchRecordActivity.this.mAdapter.remove(i);
                        }
                    }).show(WatchRecordActivity.this.getSupportFragmentManager());
                }
            }
        });
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWatchRecordView
    public void onDeleteSuccess(int i) {
        if (i > -1) {
            this.mAdapter.remove(i);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWatchRecordView
    public void onDeleteAllSuccess() {
        this.mAdapter.clearAll();
        this.mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadLocalData() {
        Observable.just(true).map(new Function<Boolean, List<WatchRecordEntity>>() { // from class: com.tomatolive.library.ui.activity.mylive.WatchRecordActivity.5
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public List<WatchRecordEntity> mo6755apply(Boolean bool) {
                return DBUtils.findAllWithWhereOrder(WatchRecordEntity.class, "liveTime desc", "userId = ?", UserInfoManager.getInstance().getUserId());
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<List<WatchRecordEntity>>() { // from class: com.tomatolive.library.ui.activity.mylive.WatchRecordActivity.4
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                if (((BaseActivity) WatchRecordActivity.this).mStateView != null) {
                    ((BaseActivity) WatchRecordActivity.this).mStateView.showLoading();
                }
            }

            @Override // io.reactivex.Observer
            public void onNext(List<WatchRecordEntity> list) {
                WatchRecordActivity.this.mAdapter.setNewData(list);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                if (((BaseActivity) WatchRecordActivity.this).mStateView != null) {
                    ((BaseActivity) WatchRecordActivity.this).mStateView.showRetry();
                }
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (((BaseActivity) WatchRecordActivity.this).mStateView != null) {
                    ((BaseActivity) WatchRecordActivity.this).mStateView.showContent();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LiveEntity formatLiveEntity(WatchRecordEntity watchRecordEntity) {
        LiveEntity liveEntity = new LiveEntity();
        if (watchRecordEntity == null) {
            return liveEntity;
        }
        liveEntity.liveId = watchRecordEntity.liveId;
        liveEntity.liveCoverUrl = watchRecordEntity.coverUrl;
        liveEntity.tag = watchRecordEntity.label;
        liveEntity.topic = watchRecordEntity.title;
        liveEntity.nickname = watchRecordEntity.anchorNickname;
        return liveEntity;
    }
}
