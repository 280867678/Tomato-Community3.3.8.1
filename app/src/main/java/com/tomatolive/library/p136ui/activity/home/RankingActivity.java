package com.tomatolive.library.p136ui.activity.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.model.event.AttentionEvent;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.LiveTopAttentionEvent;
import com.tomatolive.library.p136ui.adapter.RankingAdapter;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback;
import com.tomatolive.library.p136ui.presenter.RankingPresenter;
import com.tomatolive.library.p136ui.view.dialog.NobilityOpenTipsDialog;
import com.tomatolive.library.p136ui.view.dialog.RankingAllDialog;
import com.tomatolive.library.p136ui.view.dialog.UserAchieveDialog;
import com.tomatolive.library.p136ui.view.dialog.UserNobilityAvatarDialog;
import com.tomatolive.library.p136ui.view.dialog.UserNormalAvatarDialog;
import com.tomatolive.library.p136ui.view.headview.RankingHeadView;
import com.tomatolive.library.p136ui.view.iview.IRankingView;
import com.tomatolive.library.p136ui.view.widget.LoadingView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.activity.home.RankingActivity */
/* loaded from: classes3.dex */
public class RankingActivity extends BaseActivity<RankingPresenter> implements IRankingView {
    private static final int DAY_TOP_VALUE = 1;
    private static final int MONTH_TOP_VALUE = 2;
    private static final int WEEK_TOP_VALUE = 3;
    private LoadingView ivLoadingView;
    private LinearLayout llContentBg;
    private LinearLayout llEmptyView;
    private RankingAdapter mAdapter;
    private RankingHeadView mHeadView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RelativeLayout rlBottomTopBg;
    private TextView tvCharmTop;
    private TextView tvDayTop;
    private TextView tvMonthTop;
    private TextView tvStrengthTop;
    private TextView tvWeekTop;
    private int topTagValue = 4;
    private int dayTagValue = 1;
    private SparseArray<List<AnchorEntity>> listMap = new SparseArray<>();
    private final int CHARM_DAY_KEY = 11;
    private final int CHARM_MONTH_KEY = 12;
    private final int CHARM_WEEK_KEY = 13;
    private final int STRENGTH_DAY_KEY = 21;
    private final int STRENGTH_MONTH_KEY = 22;
    private final int STRENGTH_WEEK_KEY = 23;

    @Override // com.tomatolive.library.base.BaseActivity
    public boolean isAutoRefreshDataEnable() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public RankingPresenter mo6636createPresenter() {
        return new RankingPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_ranking;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_home_top);
        this.tvDayTop = (TextView) findViewById(R$id.tv_day_top);
        this.tvMonthTop = (TextView) findViewById(R$id.tv_month_top);
        this.tvWeekTop = (TextView) findViewById(R$id.tv_all_top);
        this.tvCharmTop = (TextView) findViewById(R$id.tv_charm_top);
        this.tvStrengthTop = (TextView) findViewById(R$id.tv_strength_top);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.llEmptyView = (LinearLayout) findViewById(R$id.ll_empty_view);
        this.ivLoadingView = (LoadingView) findViewById(R$id.iv_loading);
        this.llContentBg = (LinearLayout) findViewById(R$id.ll_content_bg);
        TextView textView = (TextView) findViewById(R$id.tv_bottom_top);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        this.rlBottomTopBg = (RelativeLayout) findViewById(R$id.rl_bottom_top_bg);
        textView.getPaint().setFlags(8);
        textView.getPaint().setAntiAlias(true);
        initAdapter();
        setTextViewDrawable(4);
        ((RankingPresenter) this.mPresenter).getRankConfig(this.ivLoadingView, this.llContentBg);
    }

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mHeadView = new RankingHeadView(this.mContext);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new RankingAdapter(R$layout.fq_item_list_live_top_new, 4);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.addHeaderView(this.mHeadView);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.tvCharmTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingActivity$jBvDG-9xetYi8AR89xep4WxsJJk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RankingActivity.this.lambda$initListener$0$RankingActivity(view);
            }
        });
        this.tvStrengthTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingActivity$GkrmlT-tghdRz-isdYdNqgmYiV0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RankingActivity.this.lambda$initListener$1$RankingActivity(view);
            }
        });
        this.tvDayTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingActivity$epdRZEvlt9Pv6MmuXk_HDUsTd_o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RankingActivity.this.lambda$initListener$2$RankingActivity(view);
            }
        });
        this.tvMonthTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingActivity$0YfTxIFra9lJI0QtvytnQ6kaCL0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RankingActivity.this.lambda$initListener$3$RankingActivity(view);
            }
        });
        this.tvWeekTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingActivity$ekDekoqAPMzmuW2NxVT-VWkjmGg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RankingActivity.this.lambda$initListener$4$RankingActivity(view);
            }
        });
        this.mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingActivity$e125mbupllcEDsomzBof7oJ9X2w
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                RankingActivity.this.lambda$initListener$5$RankingActivity(refreshLayout);
            }
        });
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingActivity$_Fk4ayqnrux1HR5rAW1xbg8nHqU
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                RankingActivity.this.lambda$initListener$6$RankingActivity();
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingActivity$DgKHJWVqKZH7xrUxTfagDA2-KA0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                RankingActivity.this.lambda$initListener$7$RankingActivity(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingActivity$sVa24CDTrn8IkIp6zaFLKU-fMrU
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                RankingActivity.this.lambda$initListener$8$RankingActivity(baseQuickAdapter, view, i);
            }
        });
        this.mHeadView.setOnUserCardCallback(new SimpleUserCardCallback() { // from class: com.tomatolive.library.ui.activity.home.RankingActivity.1
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
            public void onAnchorItemClickListener(AnchorEntity anchorEntity) {
                if (anchorEntity == null) {
                    return;
                }
                if (RankingActivity.this.topTagValue == 5) {
                    RankingActivity.this.showUserCard(anchorEntity);
                } else {
                    RankingActivity.this.toLiveActivity(anchorEntity);
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
            public void onAttentionAnchorListener(View view, AnchorEntity anchorEntity) {
                if (AppUtils.isAttentionUser(((BaseActivity) RankingActivity.this).mContext, anchorEntity.userId) && RankingActivity.this.topTagValue != 5) {
                    RankingActivity.this.attentionAnchor(anchorEntity, view);
                }
            }
        });
        findViewById(R$id.tv_bottom_top).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingActivity$sZkTr1oA8AubvOKDuYOdM1AtfHw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RankingActivity.this.lambda$initListener$9$RankingActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$RankingActivity(View view) {
        setTextViewDrawable(4);
        this.mAdapter.setType(4);
        sendRequest(true, false);
    }

    public /* synthetic */ void lambda$initListener$1$RankingActivity(View view) {
        setTextViewDrawable(5);
        this.mAdapter.setType(5);
        sendRequest(true, false);
    }

    public /* synthetic */ void lambda$initListener$2$RankingActivity(View view) {
        hideTopTagView(1, true, false);
    }

    public /* synthetic */ void lambda$initListener$3$RankingActivity(View view) {
        hideTopTagView(2, true, false);
    }

    public /* synthetic */ void lambda$initListener$4$RankingActivity(View view) {
        hideTopTagView(3, true, false);
    }

    public /* synthetic */ void lambda$initListener$5$RankingActivity(RefreshLayout refreshLayout) {
        removeData();
        sendRequest(false, false);
        this.mSmartRefreshLayout.mo6481finishRefresh();
    }

    public /* synthetic */ void lambda$initListener$6$RankingActivity() {
        this.listMap.clear();
        sendRequest(true, false);
    }

    public /* synthetic */ void lambda$initListener$7$RankingActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AnchorEntity anchorEntity;
        if (view.getId() != R$id.tv_attention || (anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i)) == null || !AppUtils.isAttentionUser(this.mContext, anchorEntity.userId)) {
            return;
        }
        attentionAnchor(anchorEntity, view);
    }

    public /* synthetic */ void lambda$initListener$8$RankingActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AnchorEntity anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i);
        if (this.topTagValue == 5) {
            showUserCard(anchorEntity);
        } else if (anchorEntity == null) {
        } else {
            toLiveActivity(anchorEntity);
        }
    }

    public /* synthetic */ void lambda$initListener$9$RankingActivity(View view) {
        RankingAllDialog.newInstance(this.topTagValue, new SimpleUserCardCallback() { // from class: com.tomatolive.library.ui.activity.home.RankingActivity.2
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
            public void onAnchorItemClickListener(AnchorEntity anchorEntity) {
                if (anchorEntity == null) {
                    return;
                }
                if (RankingActivity.this.topTagValue == 5) {
                    RankingActivity.this.showUserCard(anchorEntity);
                } else {
                    RankingActivity.this.toLiveActivity(anchorEntity);
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
            public void onAttentionAnchorListener(View view2, AnchorEntity anchorEntity) {
                if (AppUtils.isAttentionUser(((BaseActivity) RankingActivity.this).mContext, anchorEntity.userId) && RankingActivity.this.topTagValue != 5) {
                    RankingActivity.this.attentionAnchor(anchorEntity, view2);
                }
            }
        }).show(getSupportFragmentManager());
    }

    private void hideTopTagView(int i, boolean z, boolean z2) {
        this.dayTagValue = i;
        boolean z3 = false;
        this.tvDayTop.setSelected(i == 1);
        this.tvMonthTop.setSelected(i == 2);
        TextView textView = this.tvWeekTop;
        if (i == 3) {
            z3 = true;
        }
        textView.setSelected(z3);
        setTagTextViewDrawable(i);
        sendRequest(z, z2);
    }

    private void setTagTextViewDrawable(int i) {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_top_tag_red_divider);
        this.tvDayTop.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, i == 1 ? drawable : null);
        this.tvMonthTop.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, i == 2 ? drawable : null);
        TextView textView = this.tvWeekTop;
        if (i != 3) {
            drawable = null;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, drawable);
    }

    private void sendRequest(boolean z, boolean z2) {
        boolean z3 = z && !z2;
        if (this.topTagValue == 4) {
            List<AnchorEntity> charmDataList = getCharmDataList(this.dayTagValue);
            if (charmDataList != null) {
                initDataList(charmDataList);
                return;
            }
            T t = this.mPresenter;
            if (t == 0) {
                return;
            }
            ((RankingPresenter) t).getCharmTopList(this.mStateView, getDateType(), this.dayTagValue, z3, z2);
            return;
        }
        List<AnchorEntity> strengthDataList = getStrengthDataList(this.dayTagValue);
        if (strengthDataList != null) {
            initDataList(strengthDataList);
            return;
        }
        T t2 = this.mPresenter;
        if (t2 == 0) {
            return;
        }
        ((RankingPresenter) t2).getStrengthTopList(this.mStateView, getDateType(), this.dayTagValue, z3, z2);
    }

    private void setTextViewDrawable(int i) {
        this.topTagValue = i;
        boolean z = true;
        this.tvCharmTop.setSelected(i == 4);
        TextView textView = this.tvStrengthTop;
        if (i != 5) {
            z = false;
        }
        textView.setSelected(z);
    }

    private String getDateType() {
        int i = this.dayTagValue;
        return i != 1 ? i != 2 ? i != 3 ? ConstantUtils.TOP_DAY : ConstantUtils.TOP_WEEK : ConstantUtils.TOP_MONTH : ConstantUtils.TOP_DAY;
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
        showContentView();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRankingView
    public void onCharmTopListSuccess(List<AnchorEntity> list, int i, boolean z) {
        if (z) {
            showContentView();
        }
        if (list == null) {
            return;
        }
        putCharmDataList(list, i);
        initDataList(list);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRankingView
    public void onStrengthTopListSuccess(List<AnchorEntity> list, int i, boolean z) {
        if (z) {
            showContentView();
        }
        if (list == null) {
            return;
        }
        putStrengthDataList(list, i);
        initDataList(list);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRankingView
    public void onAttentionSuccess() {
        EventBus.getDefault().postSticky(new AttentionEvent());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRankingView
    public void onRankConfigSuccess(List<String> list) {
        if (list == null || list.isEmpty()) {
            this.tvDayTop.setVisibility(0);
            this.tvWeekTop.setVisibility(0);
            this.tvMonthTop.setVisibility(0);
            hideTopTagView(1, false, true);
            return;
        }
        for (String str : list) {
            char c = 65535;
            switch (str.hashCode()) {
                case 96673:
                    if (str.equals("all")) {
                        c = 3;
                        break;
                    }
                    break;
                case 99228:
                    if (str.equals(ConstantUtils.TOP_DAY)) {
                        c = 0;
                        break;
                    }
                    break;
                case 3645428:
                    if (str.equals(ConstantUtils.TOP_WEEK)) {
                        c = 1;
                        break;
                    }
                    break;
                case 104080000:
                    if (str.equals(ConstantUtils.TOP_MONTH)) {
                        c = 2;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                this.tvDayTop.setVisibility(0);
            } else if (c == 1) {
                this.tvWeekTop.setVisibility(0);
            } else if (c == 2) {
                this.tvMonthTop.setVisibility(0);
            } else if (c == 3) {
                this.rlBottomTopBg.setVisibility(0);
            }
        }
        hideTopTagView(formatTopValue(list), false, true);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRankingView
    public void onRankConfigFail() {
        this.tvDayTop.setVisibility(0);
        this.tvWeekTop.setVisibility(0);
        this.tvMonthTop.setVisibility(0);
        hideTopTagView(1, false, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0044, code lost:
        if (r7.equals(com.tomatolive.library.utils.ConstantUtils.TOP_DAY) != false) goto L14;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int formatTopValue(List<String> list) {
        if (list != null && !list.isEmpty()) {
            char c = 0;
            String str = list.get(0);
            int hashCode = str.hashCode();
            if (hashCode != 99228) {
                if (hashCode != 3645428) {
                    if (hashCode == 104080000 && str.equals(ConstantUtils.TOP_MONTH)) {
                        c = 2;
                        if (c != 0) {
                            if (c == 1) {
                                return 3;
                            }
                            return c != 2 ? 1 : 2;
                        }
                    }
                    c = 65535;
                    if (c != 0) {
                    }
                } else {
                    if (str.equals(ConstantUtils.TOP_WEEK)) {
                        c = 1;
                        if (c != 0) {
                        }
                    }
                    c = 65535;
                    if (c != 0) {
                    }
                }
            }
        }
        return 1;
    }

    private void initDataList(List<AnchorEntity> list) {
        if (list == null) {
            return;
        }
        this.mHeadView.initData(list, this.topTagValue);
        if (list.size() >= 1) {
            this.mAdapter.setNewData(list.subList(1, list.size()));
        }
        int i = 0;
        this.llEmptyView.setVisibility(list.size() == 0 ? 0 : 4);
        RecyclerView recyclerView = this.mRecyclerView;
        if (list.size() == 0) {
            i = 4;
        }
        recyclerView.setVisibility(i);
    }

    private void putCharmDataList(List<AnchorEntity> list, int i) {
        if (i == 1) {
            this.listMap.put(11, list);
        } else if (i == 2) {
            this.listMap.put(12, list);
        } else if (i != 3) {
        } else {
            this.listMap.put(13, list);
        }
    }

    private void putStrengthDataList(List<AnchorEntity> list, int i) {
        if (i == 1) {
            this.listMap.put(21, list);
        } else if (i == 2) {
            this.listMap.put(22, list);
        } else if (i != 3) {
        } else {
            this.listMap.put(23, list);
        }
    }

    private List<AnchorEntity> getCharmDataList(int i) {
        if (i != 1) {
            if (i == 2) {
                return this.listMap.get(12);
            }
            if (i == 3) {
                return this.listMap.get(13);
            }
            return new ArrayList();
        }
        return this.listMap.get(11);
    }

    private List<AnchorEntity> getStrengthDataList(int i) {
        if (i != 1) {
            if (i == 2) {
                return this.listMap.get(22);
            }
            if (i == 3) {
                return this.listMap.get(23);
            }
            return new ArrayList();
        }
        return this.listMap.get(21);
    }

    private void removeData() {
        if (this.topTagValue == 4) {
            int i = this.dayTagValue;
            if (i == 1) {
                this.listMap.remove(11);
                return;
            } else if (i == 2) {
                this.listMap.remove(12);
                return;
            } else if (i != 3) {
                return;
            } else {
                this.listMap.remove(13);
                return;
            }
        }
        int i2 = this.dayTagValue;
        if (i2 == 1) {
            this.listMap.remove(21);
        } else if (i2 == 2) {
            this.listMap.remove(22);
        } else if (i2 != 3) {
        } else {
            this.listMap.remove(23);
        }
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onEventMainThreadSticky(BaseEvent baseEvent) {
        super.onEventMainThreadSticky(baseEvent);
        if (baseEvent instanceof LiveTopAttentionEvent) {
            removeData();
            sendRequest(false, false);
        }
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

    @Override // com.tomatolive.library.base.BaseActivity
    public void onAutoRefreshData() {
        super.onAutoRefreshData();
        removeData();
        sendRequest(false, false);
    }

    private void showContentView() {
        this.ivLoadingView.setVisibility(4);
        this.llContentBg.setVisibility(0);
        this.ivLoadingView.stopLoading();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attentionAnchor(AnchorEntity anchorEntity, View view) {
        boolean z = !AppUtils.isAttentionAnchor(anchorEntity.userId) ? 1 : 0;
        showToast(z ? R$string.fq_text_attention_success : R$string.fq_text_attention_cancel_success);
        view.setSelected(z);
        DBUtils.attentionAnchor(anchorEntity.userId, z);
        ((RankingPresenter) this.mPresenter).attentionAnchor(anchorEntity.userId, z ? 1 : 0);
        LogEventUtils.uploadFollow(anchorEntity.openId, anchorEntity.appId, getString(R$string.fq_home_hot), anchorEntity.expGrade, anchorEntity.name, getString(R$string.fq_live_enter_source_ranking), z, anchorEntity.liveId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserCard(AnchorEntity anchorEntity) {
        if (anchorEntity.isRankHideBoolean() && !TextUtils.equals(anchorEntity.userId, UserInfoManager.getInstance().getUserId())) {
            NobilityOpenTipsDialog.newInstance(13, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$RankingActivity$J12V7JQBoYnLwN4llOEqtarET6I
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RankingActivity.this.lambda$showUserCard$10$RankingActivity(view);
                }
            }).show(getSupportFragmentManager());
        } else if (AppUtils.isNobilityUser(anchorEntity.nobilityType)) {
            UserNobilityAvatarDialog.newInstance(anchorEntity, 2, false, false, false, new SimpleUserCardCallback() { // from class: com.tomatolive.library.ui.activity.home.RankingActivity.3
                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                public void onClickNobilityListener(View view) {
                    AppUtils.toNobilityOpenActivity(((BaseActivity) RankingActivity.this).mContext, null);
                }

                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                public void onUserAchieveListener(UserEntity userEntity, String str) {
                    super.onUserAchieveListener(userEntity, str);
                    UserAchieveDialog.newInstance(userEntity, str).show(RankingActivity.this.getSupportFragmentManager());
                }
            }).show(getSupportFragmentManager());
        } else {
            UserNormalAvatarDialog.newInstance(anchorEntity, 2, false, false, false, new SimpleUserCardCallback() { // from class: com.tomatolive.library.ui.activity.home.RankingActivity.4
                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                public void onUserAchieveListener(UserEntity userEntity, String str) {
                    super.onUserAchieveListener(userEntity, str);
                    UserAchieveDialog.newInstance(userEntity, str).show(RankingActivity.this.getSupportFragmentManager());
                }

                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                public void onGiftWallClickListener(AnchorEntity anchorEntity2) {
                    super.onGiftWallClickListener(anchorEntity2);
                }
            }).show(getSupportFragmentManager());
        }
    }

    public /* synthetic */ void lambda$showUserCard$10$RankingActivity(View view) {
        AppUtils.toNobilityOpenActivity(this.mContext, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toLiveActivity(AnchorEntity anchorEntity) {
        AppUtils.startTomatoLiveActivity(this.mContext, AppUtils.formatLiveEntity(anchorEntity), "2", getString(R$string.fq_live_enter_source_ranking));
    }
}
