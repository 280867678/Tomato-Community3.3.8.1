package com.one.tomato.thirdpart.recyclerview;

import android.os.Bundle;
import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.SimpleItemAnimator;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/* loaded from: classes3.dex */
public abstract class BaseRecyclerViewActivity extends BaseActivity {
    protected int pageNo = 1;
    protected int pageSize = 10;
    protected RecyclerView recyclerView;
    protected SmartRefreshLayout refreshLayout;

    /* JADX INFO: Access modifiers changed from: protected */
    public void loadMore() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void refresh() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initRefreshLayout();
        initRecyclerView();
    }

    protected void initRefreshLayout() {
        this.refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        SmartRefreshLayout smartRefreshLayout = this.refreshLayout;
        if (smartRefreshLayout == null) {
            return;
        }
        smartRefreshLayout.setEnableRefresh(true);
        this.refreshLayout.mo6487setEnableLoadMore(false);
        this.refreshLayout.mo6486setEnableAutoLoadMore(false);
        this.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshLayout.mo6485getLayout().postDelayed(new Runnable(this) { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                    }
                }, 500L);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshLayout.mo6485getLayout().postDelayed(new Runnable() { // from class: com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewActivity.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        BaseRecyclerViewActivity.this.refresh();
                    }
                }, 500L);
            }
        });
    }

    protected void initRecyclerView() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView == null) {
            return;
        }
        recyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) this.recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        configLinearLayoutVerticalManager(this.recyclerView);
    }

    protected final void configLinearLayoutVerticalManager(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new BaseLinearLayoutManager(this, 1, false));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void configLinearLayoutHorizontalManager(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new BaseLinearLayoutManager(this, 0, false));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }
}
