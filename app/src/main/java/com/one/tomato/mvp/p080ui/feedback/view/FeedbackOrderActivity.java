package com.one.tomato.mvp.p080ui.feedback.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.FeedbackOrderCheck;
import com.one.tomato.entity.JCOrderRecord;
import com.one.tomato.entity.JCParameter;
import com.one.tomato.entity.RechargeOnlineUnCallback;
import com.one.tomato.entity.RechargeProblemOrder;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity;
import com.one.tomato.mvp.p080ui.feedback.adapter.FeedbackOrderAdapter;
import com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackOrderContact$IFeedbackOrderView;
import com.one.tomato.mvp.p080ui.feedback.presenter.FeedbackOrderPresenter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FeedbackOrderActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.feedback.view.FeedbackOrderActivity */
/* loaded from: classes3.dex */
public final class FeedbackOrderActivity extends MvpBaseRecyclerViewActivity<IFeedbackOrderContact$IFeedbackOrderView, FeedbackOrderPresenter, FeedbackOrderAdapter, RechargeProblemOrder> implements IFeedbackOrderContact$IFeedbackOrderView {
    public static final Companion Companion = new Companion(null);
    private String intent_type;
    private JCParameter jcParameter;
    private SystemParam systemParam;

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_recharge_problem;
    }

    public static final /* synthetic */ FeedbackOrderPresenter access$getMPresenter$p(FeedbackOrderActivity feedbackOrderActivity) {
        return (FeedbackOrderPresenter) feedbackOrderActivity.getMPresenter();
    }

    /* compiled from: FeedbackOrderActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.feedback.view.FeedbackOrderActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context, String type) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(type, "type");
            Intent intent = new Intent();
            intent.setClass(context, FeedbackOrderActivity.class);
            intent.putExtra("intent_type", type);
            ((Activity) context).startActivityForResult(intent, 1);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public FeedbackOrderPresenter mo6439createPresenter() {
        return new FeedbackOrderPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        TextView titleTV;
        Intent intent = getIntent();
        Intrinsics.checkExpressionValueIsNotNull(intent, "intent");
        String string = intent.getExtras().getString("intent_type");
        Intrinsics.checkExpressionValueIsNotNull(string, "intent.extras.getString(INTENT_TYPE)");
        this.intent_type = string;
        initTitleBar();
        this.systemParam = DBUtil.getSystemParam();
        String str = this.intent_type;
        String str2 = null;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("intent_type");
            throw null;
        }
        int hashCode = str.hashCode();
        if (hashCode != -1302586317) {
            if (hashCode == -1156401075 && str.equals("feedback_online") && (titleTV = getTitleTV()) != null) {
                titleTV.setText(R.string.problem_online_order_title);
            }
        } else if (str.equals("feedback_jc")) {
            TextView titleTV2 = getTitleTV();
            if (titleTV2 != null) {
                titleTV2.setText(R.string.problem_agent_order_title);
            }
            Gson gson = BaseApplication.getGson();
            SystemParam systemParam = this.systemParam;
            if (systemParam != null) {
                str2 = systemParam.getJCParameter();
            }
            this.jcParameter = (JCParameter) gson.fromJson(str2, (Class<Object>) JCParameter.class);
        }
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
        FeedbackOrderAdapter adapter = getAdapter();
        if (adapter == null) {
            return;
        }
        adapter.setFeedbackOrderAdapterListener(new FeedbackOrderAdapter.FeedbackOrderAdapterListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackOrderActivity$initData$1
            @Override // com.one.tomato.mvp.p080ui.feedback.adapter.FeedbackOrderAdapter.FeedbackOrderAdapterListener
            public void onItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
                FeedbackOrderAdapter adapter2;
                FeedbackOrderActivity.this.showWaitingDialog();
                FeedbackOrderPresenter access$getMPresenter$p = FeedbackOrderActivity.access$getMPresenter$p(FeedbackOrderActivity.this);
                if (access$getMPresenter$p != null) {
                    int memberId = DBUtil.getMemberId();
                    adapter2 = FeedbackOrderActivity.this.getAdapter();
                    RechargeProblemOrder item = adapter2 != null ? adapter2.getItem(i) : null;
                    if (item == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    Intrinsics.checkExpressionValueIsNotNull(item, "adapter?.getItem(position)!!");
                    access$getMPresenter$p.requestCheckOrder(memberId, item);
                }
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    /* renamed from: createAdapter */
    public FeedbackOrderAdapter mo6446createAdapter() {
        return new FeedbackOrderAdapter(this, getRecyclerView());
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
        FeedbackOrderPresenter feedbackOrderPresenter;
        FeedbackOrderPresenter feedbackOrderPresenter2;
        String str = this.intent_type;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("intent_type");
            throw null;
        }
        int hashCode = str.hashCode();
        if (hashCode == -1302586317) {
            if (!str.equals("feedback_jc") || (feedbackOrderPresenter = (FeedbackOrderPresenter) getMPresenter()) == null) {
                return;
            }
            feedbackOrderPresenter.requestJC(this.jcParameter, getPageNo(), getPageSize());
        } else if (hashCode != -1156401075 || !str.equals("feedback_online") || (feedbackOrderPresenter2 = (FeedbackOrderPresenter) getMPresenter()) == null) {
        } else {
            feedbackOrderPresenter2.requestOnline(DBUtil.getMemberId());
        }
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        updateDataFail();
    }

    @Override // com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackOrderContact$IFeedbackOrderView
    public void handleOnline(ArrayList<RechargeOnlineUnCallback> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        if (arrayList != null && (!arrayList.isEmpty())) {
            Iterator<RechargeOnlineUnCallback> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                RechargeOnlineUnCallback bean = it2.next();
                RechargeProblemOrder rechargeProblemOrder = new RechargeProblemOrder();
                Intrinsics.checkExpressionValueIsNotNull(bean, "bean");
                rechargeProblemOrder.setId(bean.getOrderId());
                rechargeProblemOrder.setMoney(FormatUtil.formatTwo(String.valueOf(bean.getRmbAmount())));
                rechargeProblemOrder.setDate(bean.getCreateDate());
                rechargeProblemOrder.setIsJinYu(bean.getIsJinYu());
                arrayList2.add(rechargeProblemOrder);
            }
        }
        updateData(getState(), arrayList2);
    }

    @Override // com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackOrderContact$IFeedbackOrderView
    public void handleJC(JCOrderRecord jcOrderRecord) {
        Intrinsics.checkParameterIsNotNull(jcOrderRecord, "jcOrderRecord");
        ArrayList<JCOrderRecord.ListBean> arrayList = jcOrderRecord.data;
        ArrayList arrayList2 = new ArrayList();
        if (arrayList != null && (!arrayList.isEmpty())) {
            Iterator<JCOrderRecord.ListBean> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                JCOrderRecord.ListBean next = it2.next();
                RechargeProblemOrder rechargeProblemOrder = new RechargeProblemOrder();
                rechargeProblemOrder.setId(next.f1714id);
                rechargeProblemOrder.setMoney(FormatUtil.formatTwo(String.valueOf(next.amount / 100)));
                rechargeProblemOrder.setDate(next.createTime);
                arrayList2.add(rechargeProblemOrder);
            }
        }
        updateData(getState(), arrayList2);
    }

    @Override // com.one.tomato.mvp.p080ui.feedback.impl.IFeedbackOrderContact$IFeedbackOrderView
    public void handleCheckOrder(FeedbackOrderCheck feedbackOrderCheck, RechargeProblemOrder rechargeProblemOrder) {
        Intrinsics.checkParameterIsNotNull(rechargeProblemOrder, "rechargeProblemOrder");
        String str = feedbackOrderCheck != null ? feedbackOrderCheck.fbYn : null;
        if (str == null) {
            return;
        }
        int hashCode = str.hashCode();
        if (hashCode != 3521) {
            if (hashCode != 119527 || !str.equals("yes")) {
                return;
            }
            showCheckedDialog();
        } else if (!str.equals("no")) {
        } else {
            Intent intent = new Intent();
            intent.putExtra("orderBean", rechargeProblemOrder);
            setResult(-1, intent);
            finish();
        }
    }

    private final void showCheckedDialog() {
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
        customAlertDialog.bottomLayoutGone();
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_order_feedback_check, (ViewGroup) null);
        customAlertDialog.setContentView(inflate);
        ((ImageView) inflate.findViewById(R.id.iv_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackOrderActivity$showCheckedDialog$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomAlertDialog.this.dismiss();
            }
        });
        ((TextView) inflate.findViewById(R.id.tv_enter)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.view.FeedbackOrderActivity$showCheckedDialog$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemParam systemParam;
                customAlertDialog.dismiss();
                systemParam = FeedbackOrderActivity.this.systemParam;
                AppUtil.startBrowseView(systemParam != null ? systemParam.getPotatoUrl() : null);
            }
        });
    }
}
