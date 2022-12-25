package com.one.tomato.mvp.p080ui.feedback.view;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.RechargeFeedbackIssues;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity;
import com.one.tomato.mvp.p080ui.feedback.adapter.FeedbackIssuesAdapter;
import com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackIssuesContact$IFeedbackIssuesView;
import com.one.tomato.mvp.p080ui.feedback.presenter.FeedbackIssuesPresenter;
import com.one.tomato.p085ui.feedback.FeedbackDetailActivity;
import com.one.tomato.thirdpart.p084jc.JCRechargeUtil;
import com.one.tomato.utils.AppUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FeedbackRechargeIssuesActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeIssuesActivity */
/* loaded from: classes3.dex */
public final class FeedbackRechargeIssuesActivity extends MvpBaseRecyclerViewActivity<IFeedbackIssuesContact$IFeedbackIssuesView, FeedbackIssuesPresenter, FeedbackIssuesAdapter, RechargeFeedbackIssues> implements IFeedbackIssuesContact$IFeedbackIssuesView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private int type = 1;

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_feedback_issues;
    }

    /* compiled from: FeedbackRechargeIssuesActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeIssuesActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context, int i) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.putExtra("type", i);
            intent.setClass(context, FeedbackRechargeIssuesActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public FeedbackIssuesPresenter mo6439createPresenter() {
        return new FeedbackIssuesPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        super.initView();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        Intent intent = getIntent();
        Intrinsics.checkExpressionValueIsNotNull(intent, "intent");
        this.type = intent.getExtras().getInt("type");
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.recharge_issues);
        }
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setVisibility(0);
        }
        TextView rightTV2 = getRightTV();
        if (rightTV2 != null) {
            rightTV2.setText(R.string.recharge_feedback_online_service);
        }
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
        TextView rightTV3 = getRightTV();
        if (rightTV3 != null) {
            rightTV3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeIssuesActivity$initData$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    JCRechargeUtil.startHelp(FeedbackRechargeIssuesActivity.this);
                }
            });
        }
        ((TextView) _$_findCachedViewById(R$id.tv_feedback)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeIssuesActivity$initData$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i;
                Context mContext;
                i = FeedbackRechargeIssuesActivity.this.type;
                if (i != 1) {
                    mContext = FeedbackRechargeIssuesActivity.this.getMContext();
                    FeedbackDetailActivity.startActivity(mContext, "cash", AppUtil.getString(R.string.feedback_cash_title));
                    return;
                }
                final CustomAlertDialog customAlertDialog = new CustomAlertDialog(FeedbackRechargeIssuesActivity.this);
                customAlertDialog.bottomLayoutGone();
                View inflate = LayoutInflater.from(FeedbackRechargeIssuesActivity.this).inflate(R.layout.dialog_recharge_feedback, (ViewGroup) null);
                customAlertDialog.setContentView(inflate);
                ((ImageView) inflate.findViewById(R.id.iv_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeIssuesActivity$initData$2.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        CustomAlertDialog.this.dismiss();
                    }
                });
                ((ConstraintLayout) inflate.findViewById(R.id.cl_recharge)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeIssuesActivity$initData$2.2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        customAlertDialog.dismiss();
                        FeedbackRechargeActivity.Companion.startActivity(FeedbackRechargeIssuesActivity.this);
                    }
                });
                ((ConstraintLayout) inflate.findViewById(R.id.cl_other)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackRechargeIssuesActivity$initData$2.3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        Context mContext2;
                        customAlertDialog.dismiss();
                        mContext2 = FeedbackRechargeIssuesActivity.this.getMContext();
                        FeedbackDetailActivity.startActivity(mContext2, "recharge", AppUtil.getString(R.string.feedback_type_recharge));
                    }
                });
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    /* renamed from: createAdapter */
    public FeedbackIssuesAdapter mo6446createAdapter() {
        return new FeedbackIssuesAdapter(this, getRecyclerView());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void refresh() {
        setState(getGET_LIST_REFRESH());
        setPageNo(1);
        requestList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void loadMore() {
        setState(getGET_LIST_LOAD());
        requestList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        refresh();
    }

    private final void requestList() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pageNo", Integer.valueOf(getPageNo()));
        hashMap.put(RequestParams.PAGE_SIZE, Integer.valueOf(getPageSize()));
        hashMap.put("type", Integer.valueOf(this.type));
        FeedbackIssuesPresenter feedbackIssuesPresenter = (FeedbackIssuesPresenter) getMPresenter();
        if (feedbackIssuesPresenter != null) {
            feedbackIssuesPresenter.requestIssues(hashMap);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackIssuesContact$IFeedbackIssuesView
    public void handleIssues(ArrayList<RechargeFeedbackIssues> list) {
        Intrinsics.checkParameterIsNotNull(list, "list");
        updateData(getState(), list);
    }
}
