package com.tomatolive.library.p136ui.activity.mylive;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.adapter.DedicateTopAdapter;
import com.tomatolive.library.p136ui.presenter.DedicateTopPresenter;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.iview.IDedicateTopView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.mylive.DedicateTopActivity */
/* loaded from: classes3.dex */
public class DedicateTopActivity extends BaseActivity<DedicateTopPresenter> implements IDedicateTopView {
    private static final int ALL_TOP_VALUE = 2;
    private static final int DAY_TOP_VALUE = 1;
    private static final int MONTH_TOP_VALUE = 4;
    private static final int WEEK_TOP_VALUE = 3;
    private DedicateTopAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private TextView tvAllTop;
    private TextView tvDayTop;
    private TextView tvMonthTop;
    private TextView tvWeekTop;
    private int dayTagValue = 1;
    private SparseArray<List<AnchorEntity>> listMap = new SparseArray<>();
    private final int CHARM_DAY_KEY = 11;
    private final int CHARM_WEEK_KEY = 12;
    private final int CHARM_All_KEY = 13;
    private final int CHARM_MONTH_KEY = 14;

    @Override // com.tomatolive.library.base.BaseActivity
    public boolean isAutoRefreshDataEnable() {
        return true;
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public DedicateTopPresenter mo6636createPresenter() {
        return new DedicateTopPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_dedicate_top;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_my_live_dedicate);
        this.tvDayTop = (TextView) findViewById(R$id.tv_day_top);
        this.tvWeekTop = (TextView) findViewById(R$id.tv_week_top);
        this.tvMonthTop = (TextView) findViewById(R$id.tv_month_top);
        this.tvAllTop = (TextView) findViewById(R$id.tv_all_top);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        initAdapter();
        hideTopTagView(1, true, true);
    }

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new DedicateTopAdapter(R$layout.fq_item_list_dedicate_top);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 33));
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.tvDayTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$DedicateTopActivity$HVhvWA5fEl6jPQTiiQ0pK3oM790
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DedicateTopActivity.this.lambda$initListener$0$DedicateTopActivity(view);
            }
        });
        this.tvMonthTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$DedicateTopActivity$SHX4jXUm3CKQy_EZEmy46IS3Qas
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DedicateTopActivity.this.lambda$initListener$1$DedicateTopActivity(view);
            }
        });
        this.tvWeekTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$DedicateTopActivity$UBzNA-LPrGfLW6zugywh34b6qqc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DedicateTopActivity.this.lambda$initListener$2$DedicateTopActivity(view);
            }
        });
        this.tvAllTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$DedicateTopActivity$MzTE0zH1-QzoILiDJBlB_2c39s8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DedicateTopActivity.this.lambda$initListener$3$DedicateTopActivity(view);
            }
        });
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$DedicateTopActivity$PgwYHAJFKJY2StUVNPy139vqWNc
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                DedicateTopActivity.this.lambda$initListener$4$DedicateTopActivity();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$DedicateTopActivity$it_aWPXIlN-jrV1PNsCKHOO-5uo
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                DedicateTopActivity.this.lambda$initListener$5$DedicateTopActivity(refreshLayout);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$DedicateTopActivity(View view) {
        hideTopTagView(1, true, true);
    }

    public /* synthetic */ void lambda$initListener$1$DedicateTopActivity(View view) {
        hideTopTagView(4, true, true);
    }

    public /* synthetic */ void lambda$initListener$2$DedicateTopActivity(View view) {
        hideTopTagView(3, true, true);
    }

    public /* synthetic */ void lambda$initListener$3$DedicateTopActivity(View view) {
        hideTopTagView(2, true, true);
    }

    public /* synthetic */ void lambda$initListener$4$DedicateTopActivity() {
        this.listMap.clear();
        this.pageNum = 1;
        sendRequest(true, true);
    }

    public /* synthetic */ void lambda$initListener$5$DedicateTopActivity(RefreshLayout refreshLayout) {
        removeData();
        this.pageNum = 1;
        sendRequest(false, true);
        this.mSmartRefreshLayout.mo6481finishRefresh();
    }

    private void hideTopTagView(int i, boolean z, boolean z2) {
        this.dayTagValue = i;
        boolean z3 = false;
        this.tvDayTop.setSelected(i == 1);
        this.tvWeekTop.setSelected(i == 3);
        this.tvMonthTop.setSelected(i == 4);
        TextView textView = this.tvAllTop;
        if (i == 2) {
            z3 = true;
        }
        textView.setSelected(z3);
        setTextViewDrawable(i);
        sendRequest(z, z2);
    }

    private void setTextViewDrawable(int i) {
        Drawable drawable = getResources().getDrawable(R$drawable.fq_shape_top_tag_red_divider);
        this.tvDayTop.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, i == 1 ? drawable : null);
        this.tvMonthTop.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, i == 4 ? drawable : null);
        this.tvWeekTop.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, i == 3 ? drawable : null);
        TextView textView = this.tvAllTop;
        if (i != 2) {
            drawable = null;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, drawable);
    }

    private void sendRequest(boolean z, boolean z2) {
        List<AnchorEntity> charmDataList = getCharmDataList(this.dayTagValue);
        if (charmDataList != null) {
            this.mAdapter.setNewData(charmDataList);
        } else {
            ((DedicateTopPresenter) this.mPresenter).getDataList(this.mStateView, getDateType(), this.pageNum, z, z2);
        }
    }

    private String getDateType() {
        int i = this.dayTagValue;
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? ConstantUtils.TOP_DAY : ConstantUtils.TOP_MONTH : ConstantUtils.TOP_WEEK : "all" : ConstantUtils.TOP_DAY;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IDedicateTopView
    public void onDataListSuccess(List<AnchorEntity> list, boolean z, boolean z2) {
        putCharmDataList(list, this.dayTagValue);
        this.mAdapter.setNewData(list);
    }

    private void putCharmDataList(List<AnchorEntity> list, int i) {
        if (this.listMap == null) {
            this.listMap = new SparseArray<>();
        }
        if (i == 1) {
            this.listMap.put(11, formatList(list));
        } else if (i == 2) {
            this.listMap.put(13, formatList(list));
        } else if (i == 3) {
            this.listMap.put(12, formatList(list));
        } else if (i != 4) {
        } else {
            this.listMap.put(14, formatList(list));
        }
    }

    private List<AnchorEntity> formatList(List<AnchorEntity> list) {
        if (list == null) {
            return new ArrayList();
        }
        return list.isEmpty() ? new ArrayList() : list;
    }

    private List<AnchorEntity> getCharmDataList(int i) {
        SparseArray<List<AnchorEntity>> sparseArray = this.listMap;
        if (sparseArray == null) {
            return null;
        }
        if (i == 1) {
            return sparseArray.get(11);
        }
        if (i == 2) {
            return sparseArray.get(13);
        }
        if (i == 3) {
            return sparseArray.get(12);
        }
        if (i == 4) {
            return sparseArray.get(14);
        }
        return new ArrayList();
    }

    private void removeData() {
        SparseArray<List<AnchorEntity>> sparseArray = this.listMap;
        if (sparseArray == null) {
            return;
        }
        int i = this.dayTagValue;
        if (i == 1) {
            sparseArray.remove(11);
        } else if (i == 2) {
            sparseArray.remove(13);
        } else if (i == 3) {
            sparseArray.remove(12);
        } else if (i != 4) {
        } else {
            sparseArray.remove(14);
        }
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onAutoRefreshData() {
        super.onAutoRefreshData();
        removeData();
        this.pageNum = 1;
        sendRequest(false, true);
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        SparseArray<List<AnchorEntity>> sparseArray = this.listMap;
        if (sparseArray != null) {
            sparseArray.clear();
            this.listMap = null;
        }
    }
}
