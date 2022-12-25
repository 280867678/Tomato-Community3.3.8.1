package com.one.tomato.mvp.p080ui.p082up.view;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.RewardRecordBean;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.p080ui.p082up.adapter.RewardRecordAdapter;
import com.one.tomato.mvp.p080ui.p082up.impl.IRecordCountListener;
import com.one.tomato.mvp.p080ui.p082up.impl.RewardRecordContact;
import com.one.tomato.mvp.p080ui.p082up.presenter.RewardRecordPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RewardRecordFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.RewardRecordFragment */
/* loaded from: classes3.dex */
public final class RewardRecordFragment extends MvpBaseRecyclerViewFragment<RewardRecordContact, RewardRecordPresenter, RewardRecordAdapter, RewardRecordBean> implements RewardRecordContact {
    private HashMap _$_findViewCache;
    private Long artcId;
    private IRecordCountListener recordCountListener;

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.reward_record_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
    }

    public final void setArtcId(Long l) {
        this.artcId = l;
    }

    public final void setRecordCountListener(IRecordCountListener recordCountListener) {
        Intrinsics.checkParameterIsNotNull(recordCountListener, "recordCountListener");
        this.recordCountListener = recordCountListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void refresh() {
        request();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void loadMore() {
        request();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    /* renamed from: createAdapter */
    public RewardRecordAdapter mo6434createAdapter() {
        Context mContext = getMContext();
        if (mContext == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            return new RewardRecordAdapter(mContext, recyclerView);
        }
        Intrinsics.throwNpe();
        throw null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public RewardRecordPresenter mo6441createPresenter() {
        return new RewardRecordPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        super.initView();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.autoRefresh(100);
        }
    }

    public final void request() {
        RewardRecordPresenter rewardRecordPresenter = (RewardRecordPresenter) getMPresenter();
        if (rewardRecordPresenter != null) {
            Long l = this.artcId;
            rewardRecordPresenter.requestUpPayRecordList(l != null ? l.longValue() : 0L, getPageNo(), getPageSize());
        }
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.RewardRecordContact
    public void handlerPayList(ArrayList<RewardRecordBean> arrayList) {
        updateData(arrayList);
    }

    @Override // com.one.tomato.mvp.p080ui.p082up.impl.RewardRecordContact
    public void handleListTotalCount(int i) {
        IRecordCountListener iRecordCountListener = this.recordCountListener;
        if (iRecordCountListener == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recordCountListener");
            throw null;
        } else if (iRecordCountListener == null) {
        } else {
            if (iRecordCountListener != null) {
                iRecordCountListener.getRecordCount(i);
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("recordCountListener");
                throw null;
            }
        }
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onEmpty(Object obj) {
        List<RewardRecordBean> data;
        RewardRecordAdapter adapter;
        RewardRecordAdapter adapter2;
        SmartRefreshLayout refreshLayout;
        Intrinsics.checkParameterIsNotNull(obj, "obj");
        if (getState() == getGET_LIST_REFRESH() && (refreshLayout = getRefreshLayout()) != null) {
            refreshLayout.mo6481finishRefresh();
        }
        if (getState() == getGET_LIST_LOAD() && (adapter2 = getAdapter()) != null) {
            adapter2.loadMoreFail();
        }
        RewardRecordAdapter adapter3 = getAdapter();
        if (adapter3 == null || (data = adapter3.getData()) == null || data.size() != 0 || (adapter = getAdapter()) == null) {
            return;
        }
        adapter.setEmptyViewState(2, getRefreshLayout());
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        List<RewardRecordBean> data;
        RewardRecordAdapter adapter;
        RewardRecordAdapter adapter2;
        SmartRefreshLayout refreshLayout;
        if (getState() == getGET_LIST_REFRESH() && (refreshLayout = getRefreshLayout()) != null) {
            refreshLayout.mo6481finishRefresh();
        }
        if (getState() == getGET_LIST_LOAD() && (adapter2 = getAdapter()) != null) {
            adapter2.loadMoreFail();
        }
        RewardRecordAdapter adapter3 = getAdapter();
        if (adapter3 == null || (data = adapter3.getData()) == null || data.size() != 0 || (adapter = getAdapter()) == null) {
            return;
        }
        adapter.setEmptyViewState(1, getRefreshLayout());
    }
}
