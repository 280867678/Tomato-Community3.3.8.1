package com.one.tomato.p085ui.recharge;

import android.content.Context;
import android.support.p002v4.app.FragmentActivity;
import android.support.p002v4.widget.NestedScrollView;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.eclipsesource.p056v8.Platform;
import com.google.gson.Gson;
import com.one.tomato.R$id;
import com.one.tomato.adapter.RechargeMoneyAdapter;
import com.one.tomato.adapter.RechargeTypeAdapter;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.JCOrderMsg;
import com.one.tomato.entity.JCOrderRecord;
import com.one.tomato.entity.JCParameter;
import com.one.tomato.entity.RechargeAccount;
import com.one.tomato.entity.RechargeOrder;
import com.one.tomato.entity.RechargeOrderConfirm;
import com.one.tomato.entity.RechargeTypeAndMoney;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.feedback.view.FeedbackRechargeIssuesActivity;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.p085ui.income.IncomeActivity;
import com.one.tomato.p085ui.income.InspectRecordActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.p084jc.JCRechargeUtil;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DeviceInfoUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.security.sdk.open.RSAEncrypt;
import com.security.sdk.open.Security;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: RechargeFragment.kt */
/* renamed from: com.one.tomato.ui.recharge.RechargeFragment */
/* loaded from: classes3.dex */
public final class RechargeFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private boolean isGoPay;
    private boolean isQuery;
    private JCParameter jcParameter;
    private RechargeAccount rechargeAccount;
    private RechargeMoneyAdapter rechargeMoneyAdapter;
    private RechargeOrder rechargeOrder;
    private RechargeTypeAdapter rechargeTypeAdapter;
    private SystemParam systemParam;

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.fragment_recharge;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        this.systemParam = DBUtil.getSystemParam();
        Gson gson = BaseApplication.getGson();
        SystemParam systemParam = this.systemParam;
        this.jcParameter = (JCParameter) gson.fromJson(systemParam != null ? systemParam.getJCParameter() : null, (Class<Object>) JCParameter.class);
        requestRechargeTypeAndMoneyList();
        setListener();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z) {
            getRechargeAccountBalance();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        getRechargeAccountBalance();
        if (this.isGoPay) {
            confirmOnlineOrder();
        }
    }

    private final void setListener() {
        BaseApplication application = BaseApplication.getApplication();
        Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
        if (!application.isChess()) {
            ((ImageView) _$_findCachedViewById(R$id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$setListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FragmentActivity activity = RechargeFragment.this.getActivity();
                    if (activity == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else if (activity == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.ui.recharge.RechargeActivity");
                    } else {
                        ((RechargeActivity) activity).onBackPressed();
                    }
                }
            });
        }
        ((TextView) _$_findCachedViewById(R$id.tv_recharge_feedback)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$setListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                FeedbackRechargeIssuesActivity.Companion companion = FeedbackRechargeIssuesActivity.Companion;
                mContext = RechargeFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, 1);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_recharge_confirm)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$setListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RechargeFragment.this.rechargeConfirmDialog();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_withdraw)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$setListener$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                IncomeActivity.Companion companion = IncomeActivity.Companion;
                mContext = RechargeFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, 4);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_record)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$setListener$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = RechargeFragment.this.getMContext();
                if (mContext != null) {
                    RechargeRecordActivity.startActivity(mContext, 0);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_avail_withdraw)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$setListener$6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                InspectRecordActivity.Companion companion = InspectRecordActivity.Companion;
                mContext = RechargeFragment.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((LinearLayout) _$_findCachedViewById(R$id.ll_empty)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$setListener$7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RechargeFragment.this.requestRechargeTypeAndMoneyList();
            }
        });
        ((RelativeLayout) _$_findCachedViewById(R$id.rl_agent_order)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$setListener$8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = RechargeFragment.this.getMContext();
                JCRechargeUtil.startOrderList(mContext);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getRechargeAccountBalance() {
        ApiImplService.Companion.getApiImplService().requestRechargeAccount(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<RechargeAccount>() { // from class: com.one.tomato.ui.recharge.RechargeFragment$getRechargeAccountBalance$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(RechargeAccount bean) {
                RechargeAccount rechargeAccount;
                RechargeAccount rechargeAccount2;
                Intrinsics.checkParameterIsNotNull(bean, "bean");
                RechargeFragment.this.rechargeAccount = bean;
                TextView textView = (TextView) RechargeFragment.this._$_findCachedViewById(R$id.tv_balance);
                rechargeAccount = RechargeFragment.this.rechargeAccount;
                String str = null;
                textView.setText(FormatUtil.formatTomato2RMB(rechargeAccount != null ? String.valueOf(rechargeAccount.balance) : null));
                TextView textView2 = (TextView) RechargeFragment.this._$_findCachedViewById(R$id.tv_avail_balance);
                rechargeAccount2 = RechargeFragment.this.rechargeAccount;
                if (rechargeAccount2 != null) {
                    str = String.valueOf(rechargeAccount2.withdrawPassBalance);
                }
                textView2.setText(FormatUtil.formatTomato2RMB(str));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestRechargeTypeAndMoneyList() {
        showWaitingDialog();
        ApiImplService.Companion.getApiImplService().requestRechargeTypeAndMoneyList().compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<RechargeTypeAndMoney>>() { // from class: com.one.tomato.ui.recharge.RechargeFragment$requestRechargeTypeAndMoneyList$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<RechargeTypeAndMoney> list) {
                Intrinsics.checkParameterIsNotNull(list, "list");
                RechargeFragment.this.hideWaitingDialog();
                LinearLayout ll_empty = (LinearLayout) RechargeFragment.this._$_findCachedViewById(R$id.ll_empty);
                Intrinsics.checkExpressionValueIsNotNull(ll_empty, "ll_empty");
                ll_empty.setVisibility(8);
                LinearLayout ll_content = (LinearLayout) RechargeFragment.this._$_findCachedViewById(R$id.ll_content);
                Intrinsics.checkExpressionValueIsNotNull(ll_content, "ll_content");
                ll_content.setVisibility(0);
                LinearLayout rl_recharge_bottom = (LinearLayout) RechargeFragment.this._$_findCachedViewById(R$id.rl_recharge_bottom);
                Intrinsics.checkExpressionValueIsNotNull(rl_recharge_bottom, "rl_recharge_bottom");
                rl_recharge_bottom.setVisibility(0);
                if (!list.isEmpty()) {
                    RechargeFragment.this.initRechargeTypeAdapter(list);
                    RechargeFragment rechargeFragment = RechargeFragment.this;
                    RechargeTypeAndMoney rechargeTypeAndMoney = list.get(0);
                    Intrinsics.checkExpressionValueIsNotNull(rechargeTypeAndMoney, "list[0]");
                    rechargeFragment.initRechargeMoneyAdapter(rechargeTypeAndMoney);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                RechargeFragment.this.hideWaitingDialog();
                LinearLayout ll_empty = (LinearLayout) RechargeFragment.this._$_findCachedViewById(R$id.ll_empty);
                Intrinsics.checkExpressionValueIsNotNull(ll_empty, "ll_empty");
                ll_empty.setVisibility(0);
                LinearLayout ll_content = (LinearLayout) RechargeFragment.this._$_findCachedViewById(R$id.ll_content);
                Intrinsics.checkExpressionValueIsNotNull(ll_content, "ll_content");
                ll_content.setVisibility(8);
                LinearLayout rl_recharge_bottom = (LinearLayout) RechargeFragment.this._$_findCachedViewById(R$id.rl_recharge_bottom);
                Intrinsics.checkExpressionValueIsNotNull(rl_recharge_bottom, "rl_recharge_bottom");
                rl_recharge_bottom.setVisibility(8);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initRechargeTypeAdapter(ArrayList<RechargeTypeAndMoney> arrayList) {
        RecyclerView recyclerView_type = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_type);
        Intrinsics.checkExpressionValueIsNotNull(recyclerView_type, "recyclerView_type");
        recyclerView_type.setLayoutManager(new BaseLinearLayoutManager(getMContext(), 1, false));
        this.rechargeTypeAdapter = new RechargeTypeAdapter(getMContext(), (RecyclerView) _$_findCachedViewById(R$id.recyclerView_type));
        RecyclerView recyclerView_type2 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_type);
        Intrinsics.checkExpressionValueIsNotNull(recyclerView_type2, "recyclerView_type");
        RechargeTypeAdapter rechargeTypeAdapter = this.rechargeTypeAdapter;
        if (rechargeTypeAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rechargeTypeAdapter");
            throw null;
        }
        recyclerView_type2.setAdapter(rechargeTypeAdapter);
        RechargeTypeAdapter rechargeTypeAdapter2 = this.rechargeTypeAdapter;
        if (rechargeTypeAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rechargeTypeAdapter");
            throw null;
        }
        rechargeTypeAdapter2.setNewData(arrayList);
        RechargeTypeAdapter rechargeTypeAdapter3 = this.rechargeTypeAdapter;
        if (rechargeTypeAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rechargeTypeAdapter");
            throw null;
        }
        rechargeTypeAdapter3.setEnableLoadMore(false);
        RechargeTypeAdapter rechargeTypeAdapter4 = this.rechargeTypeAdapter;
        if (rechargeTypeAdapter4 != null) {
            rechargeTypeAdapter4.setRechargeTypeListener(new RechargeTypeAdapter.RechargeTypeListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$initRechargeTypeAdapter$1
                @Override // com.one.tomato.adapter.RechargeTypeAdapter.RechargeTypeListener
                public void onItemClick(RechargeTypeAndMoney itemData) {
                    Intrinsics.checkParameterIsNotNull(itemData, "itemData");
                    RechargeFragment.this.notifyRechargeMoneyAdapter(itemData);
                }
            });
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("rechargeTypeAdapter");
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initRechargeMoneyAdapter(RechargeTypeAndMoney rechargeTypeAndMoney) {
        RecyclerView recyclerView_money = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_money);
        Intrinsics.checkExpressionValueIsNotNull(recyclerView_money, "recyclerView_money");
        recyclerView_money.setLayoutManager(new BaseLinearLayoutManager(getMContext(), 1, false));
        this.rechargeMoneyAdapter = new RechargeMoneyAdapter(getMContext(), (RecyclerView) _$_findCachedViewById(R$id.recyclerView_money));
        RecyclerView recyclerView_money2 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView_money);
        Intrinsics.checkExpressionValueIsNotNull(recyclerView_money2, "recyclerView_money");
        RechargeMoneyAdapter rechargeMoneyAdapter = this.rechargeMoneyAdapter;
        if (rechargeMoneyAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rechargeMoneyAdapter");
            throw null;
        }
        recyclerView_money2.setAdapter(rechargeMoneyAdapter);
        notifyRechargeMoneyAdapter(rechargeTypeAndMoney);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyRechargeMoneyAdapter(RechargeTypeAndMoney rechargeTypeAndMoney) {
        RechargeMoneyAdapter rechargeMoneyAdapter = this.rechargeMoneyAdapter;
        if (rechargeMoneyAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rechargeMoneyAdapter");
            throw null;
        }
        rechargeMoneyAdapter.setSelectPosition(0);
        RechargeMoneyAdapter rechargeMoneyAdapter2 = this.rechargeMoneyAdapter;
        if (rechargeMoneyAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rechargeMoneyAdapter");
            throw null;
        }
        rechargeMoneyAdapter2.setNewData(rechargeTypeAndMoney.rechargeFaceList);
        RechargeMoneyAdapter rechargeMoneyAdapter3 = this.rechargeMoneyAdapter;
        if (rechargeMoneyAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rechargeMoneyAdapter");
            throw null;
        }
        rechargeMoneyAdapter3.setEnableLoadMore(false);
        ((NestedScrollView) _$_findCachedViewById(R$id.nestedScrollView)).scrollTo(0, 0);
        if (Intrinsics.areEqual(rechargeTypeAndMoney.payMethod, RechargeTypeAndMoney.RECHARGE_AGENT)) {
            RelativeLayout rl_agent_order = (RelativeLayout) _$_findCachedViewById(R$id.rl_agent_order);
            Intrinsics.checkExpressionValueIsNotNull(rl_agent_order, "rl_agent_order");
            rl_agent_order.setVisibility(0);
            LinearLayout ll_agent_tip = (LinearLayout) _$_findCachedViewById(R$id.ll_agent_tip);
            Intrinsics.checkExpressionValueIsNotNull(ll_agent_tip, "ll_agent_tip");
            ll_agent_tip.setVisibility(0);
            LinearLayout ll_online_tip = (LinearLayout) _$_findCachedViewById(R$id.ll_online_tip);
            Intrinsics.checkExpressionValueIsNotNull(ll_online_tip, "ll_online_tip");
            ll_online_tip.setVisibility(8);
            setProxyRechargeMessage();
            return;
        }
        RelativeLayout rl_agent_order2 = (RelativeLayout) _$_findCachedViewById(R$id.rl_agent_order);
        Intrinsics.checkExpressionValueIsNotNull(rl_agent_order2, "rl_agent_order");
        rl_agent_order2.setVisibility(8);
        LinearLayout ll_agent_tip2 = (LinearLayout) _$_findCachedViewById(R$id.ll_agent_tip);
        Intrinsics.checkExpressionValueIsNotNull(ll_agent_tip2, "ll_agent_tip");
        ll_agent_tip2.setVisibility(8);
        LinearLayout ll_online_tip2 = (LinearLayout) _$_findCachedViewById(R$id.ll_online_tip);
        Intrinsics.checkExpressionValueIsNotNull(ll_online_tip2, "ll_online_tip");
        ll_online_tip2.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void rechargeConfirmDialog() {
        LoginInfo loginInfo = DBUtil.getLoginInfo();
        Intrinsics.checkExpressionValueIsNotNull(loginInfo, "DBUtil.getLoginInfo()");
        if (!loginInfo.isLogin()) {
            final CustomAlertDialog customAlertDialog = new CustomAlertDialog(getMContext());
            customAlertDialog.setCanceledOnTouchOutside(true);
            customAlertDialog.setTitle(R.string.recharge_login_tip_title);
            customAlertDialog.setMessage(R.string.recharge_login_tip_message);
            customAlertDialog.setCancelButton(R.string.recharge_login_tip_cancel_btn, new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$rechargeConfirmDialog$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    customAlertDialog.dismiss();
                    LoginActivity.Companion companion = LoginActivity.Companion;
                    mContext = RechargeFragment.this.getMContext();
                    if (mContext != null) {
                        companion.startActivity(mContext);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
            customAlertDialog.setConfirmButton(R.string.recharge_login_tip_confirm_btn, new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$rechargeConfirmDialog$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    customAlertDialog.dismiss();
                    RechargeFragment.this.startRecharge();
                }
            });
            BaseApplication application = BaseApplication.getApplication();
            Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
            if (!application.isChess()) {
                return;
            }
            customAlertDialog.setConfirmButtonBackgroundRes(R.drawable.chess_bright_bg);
            customAlertDialog.setCancelButtonBackgroundRes(R.drawable.chess_bright_bg);
            return;
        }
        startRecharge();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startRecharge() {
        RechargeTypeAdapter rechargeTypeAdapter = this.rechargeTypeAdapter;
        if (rechargeTypeAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rechargeTypeAdapter");
            throw null;
        }
        RechargeTypeAndMoney selectItem = rechargeTypeAdapter.getSelectItem();
        Intrinsics.checkExpressionValueIsNotNull(selectItem, "rechargeTypeAdapter.selectItem");
        RechargeMoneyAdapter rechargeMoneyAdapter = this.rechargeMoneyAdapter;
        if (rechargeMoneyAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rechargeMoneyAdapter");
            throw null;
        }
        RechargeTypeAndMoney.RechargeMoney selectItem2 = rechargeMoneyAdapter.getSelectItem();
        Intrinsics.checkExpressionValueIsNotNull(selectItem2, "rechargeMoneyAdapter.selectItem");
        if (selectItem2 == null) {
            ToastUtil.showCenterToast((int) R.string.recharge_select_amount);
        } else if (Intrinsics.areEqual(RechargeTypeAndMoney.RECHARGE_AGENT, selectItem.payMethod)) {
            JCRechargeUtil.createOrder(getActivity(), selectItem2.skuId, selectItem2.amount * 100);
        } else {
            startRechargeWithOnline(selectItem, selectItem2);
        }
    }

    private final void startRechargeWithOnline(RechargeTypeAndMoney rechargeTypeAndMoney, RechargeTypeAndMoney.RechargeMoney rechargeMoney) {
        if (!Intrinsics.areEqual(RechargeTypeAndMoney.RECHARGE_PTPAY, rechargeTypeAndMoney.payMethod) || !AppUtil.showPtInstallDialog(getMContext())) {
            showWaitingDialog();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
            linkedHashMap.put("settingId", Integer.valueOf(rechargeMoney.f1731id));
            Security security = Security.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(security, "Security.getInstance()");
            linkedHashMap.put("metadata", security.getSec());
            linkedHashMap.put("payType", rechargeTypeAndMoney.payMethod);
            linkedHashMap.put("platform", Platform.ANDROID);
            linkedHashMap.put("deviceNo", DeviceInfoUtil.getUniqueDeviceID());
            linkedHashMap.put("orderCode", "P000");
            if (rechargeTypeAndMoney.isFix == 1) {
                linkedHashMap.put("fixAmount", Integer.valueOf(rechargeMoney.amount * 100));
            }
            if (Intrinsics.areEqual(RechargeTypeAndMoney.RECHARGE_PTPAY, rechargeTypeAndMoney.payMethod)) {
                linkedHashMap.put("redirectUrl", "tomato://pt/recharge/purse");
            }
            ApiImplService.Companion.getApiImplService().requestRechargeOnline(linkedHashMap).compose(RxUtils.schedulersTransformer()).subscribe(new RechargeFragment$startRechargeWithOnline$1(this, rechargeTypeAndMoney));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openPayPlatform(String str) {
        String decrypt = RSAEncrypt.decrypt(str);
        String tag = getTAG();
        LogUtil.m3785e(tag, "url>>" + str);
        String tag2 = getTAG();
        LogUtil.m3785e(tag2, "decodeUrl>>" + decrypt);
        AppUtil.startBrowseView(decrypt);
    }

    private final void confirmOnlineOrder() {
        if (this.rechargeOrder != null && !this.isQuery) {
            this.isQuery = true;
            showWaitingDialog();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
            RechargeOrder rechargeOrder = this.rechargeOrder;
            linkedHashMap.put("orderId", rechargeOrder != null ? rechargeOrder.getOrderId() : null);
            ApiImplService.Companion.getApiImplService().requestRechargeOnlineConfirm(linkedHashMap).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<RechargeOrderConfirm>() { // from class: com.one.tomato.ui.recharge.RechargeFragment$confirmOnlineOrder$1
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(RechargeOrderConfirm confirm) {
                    Context mContext;
                    Intrinsics.checkParameterIsNotNull(confirm, "confirm");
                    RechargeFragment.this.isGoPay = false;
                    RechargeFragment.this.isQuery = false;
                    RechargeFragment.this.hideWaitingDialog();
                    if (confirm.getStatus() == 1) {
                        ToastUtil.showCenterToast((int) R.string.recharge_success_message);
                        RechargeFragment.this.getRechargeAccountBalance();
                    } else if (confirm.getStatus() == 0) {
                        mContext = RechargeFragment.this.getMContext();
                        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(mContext);
                        customAlertDialog.bottomButtonVisiblity(2);
                        customAlertDialog.setTitle(AppUtil.getString(R.string.common_notify));
                        customAlertDialog.setMessage(AppUtil.getString(R.string.recharge_order_delay));
                        customAlertDialog.setConfirmButton(AppUtil.getString(R.string.common_dialog_ok), new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$confirmOnlineOrder$1$onResult$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                CustomAlertDialog.this.dismiss();
                            }
                        });
                    } else if (confirm.getStatus() != 2) {
                    } else {
                        ToastUtil.showCenterToast((int) R.string.recharge_fail_message);
                        RechargeFragment.this.showRechargeFailDialog();
                    }
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable e) {
                    Intrinsics.checkParameterIsNotNull(e, "e");
                    RechargeFragment.this.isGoPay = false;
                    RechargeFragment.this.isQuery = false;
                    RechargeFragment.this.hideWaitingDialog();
                    RechargeFragment.this.showRechargeFailDialog();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showRechargeFailDialog() {
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(getMContext());
        customAlertDialog.bottomButtonVisiblity(2);
        customAlertDialog.setTitle(R.string.common_notify);
        customAlertDialog.setMessage(getResources().getString(R.string.recharge_order_query_fail_message));
        customAlertDialog.setConfirmButtonBackgroundRes(R.drawable.common_selector_solid_corner30_coloraccent);
        customAlertDialog.setConfirmButtonTextColor(R.color.white);
        customAlertDialog.setConfirmButton(R.string.common_confirm, new View.OnClickListener() { // from class: com.one.tomato.ui.recharge.RechargeFragment$showRechargeFailDialog$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomAlertDialog.this.dismiss();
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x014e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void setProxyRechargeMessage() {
        JSONObject jSONObject;
        JSONObject jSONObject2;
        LinearLayout liner_show_have_message_and_order = (LinearLayout) _$_findCachedViewById(R$id.liner_show_have_message_and_order);
        Intrinsics.checkExpressionValueIsNotNull(liner_show_have_message_and_order, "liner_show_have_message_and_order");
        liner_show_have_message_and_order.setVisibility(8);
        if (this.jcParameter == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        sb.append(domainServer.getJCUrl());
        sb.append(":");
        JCParameter jCParameter = this.jcParameter;
        sb.append(jCParameter != null ? jCParameter.httpPort : null);
        String sb2 = sb.toString();
        try {
            jSONObject = new JSONObject();
            try {
                jSONObject.put("page", 1);
                jSONObject.put("size", 10);
                jSONObject.put("appUserId", DBUtil.getMemberId());
                JCParameter jCParameter2 = this.jcParameter;
                jSONObject.put("appFlag", jCParameter2 != null ? jCParameter2.appFlag : null);
                JCParameter jCParameter3 = this.jcParameter;
                jSONObject.put("clientApiKey", jCParameter3 != null ? jCParameter3.appKey : null);
                JCParameter jCParameter4 = this.jcParameter;
                jSONObject.put("clientSecretKey", jCParameter4 != null ? jCParameter4.appSecret : null);
            } catch (JSONException e) {
                e = e;
                e.printStackTrace();
                MediaType parse = MediaType.parse("application/json");
                if (jSONObject != null) {
                }
            }
        } catch (JSONException e2) {
            e = e2;
            jSONObject = null;
        }
        MediaType parse2 = MediaType.parse("application/json");
        if (jSONObject != null) {
            Intrinsics.throwNpe();
            throw null;
        }
        RequestBody body = RequestBody.create(parse2, jSONObject.toString());
        Intrinsics.checkExpressionValueIsNotNull(body, "body");
        ApiImplService.Companion.getApiImplService().requestJCOrders(sb2 + "/application/public/order/list", body).compose(RxUtils.schedulersTransformer()).subscribe(new Observer<JCOrderRecord>() { // from class: com.one.tomato.ui.recharge.RechargeFragment$setProxyRechargeMessage$1$1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e3) {
                Intrinsics.checkParameterIsNotNull(e3, "e");
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d) {
                Intrinsics.checkParameterIsNotNull(d, "d");
            }

            @Override // io.reactivex.Observer
            public void onNext(JCOrderRecord jcOrderRecord) {
                Intrinsics.checkParameterIsNotNull(jcOrderRecord, "jcOrderRecord");
                ArrayList<JCOrderRecord.ListBean> arrayList = jcOrderRecord.data;
                if (arrayList == null || arrayList.isEmpty()) {
                    return;
                }
                Iterator<JCOrderRecord.ListBean> it2 = jcOrderRecord.data.iterator();
                while (it2.hasNext()) {
                    if (it2.next().status == 1) {
                        LinearLayout liner_show_have_message_and_order2 = (LinearLayout) RechargeFragment.this._$_findCachedViewById(R$id.liner_show_have_message_and_order);
                        Intrinsics.checkExpressionValueIsNotNull(liner_show_have_message_and_order2, "liner_show_have_message_and_order");
                        liner_show_have_message_and_order2.setVisibility(0);
                        TextView text_have_message = (TextView) RechargeFragment.this._$_findCachedViewById(R$id.text_have_message);
                        Intrinsics.checkExpressionValueIsNotNull(text_have_message, "text_have_message");
                        text_have_message.setText(AppUtil.getString(R.string.agent_order_nodone));
                        return;
                    }
                }
            }
        });
        try {
            jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("appUserId", DBUtil.getMemberId());
                JCParameter jCParameter5 = this.jcParameter;
                jSONObject2.put("appFlag", jCParameter5 != null ? jCParameter5.appFlag : null);
                JCParameter jCParameter6 = this.jcParameter;
                jSONObject2.put("clientApiKey", jCParameter6 != null ? jCParameter6.appKey : null);
                JCParameter jCParameter7 = this.jcParameter;
                jSONObject2.put("clientSecretKey", jCParameter7 != null ? jCParameter7.appSecret : null);
            } catch (JSONException e3) {
                e = e3;
                e.printStackTrace();
                MediaType parse3 = MediaType.parse("application/json");
                if (jSONObject2 != null) {
                }
            }
        } catch (JSONException e4) {
            e = e4;
            jSONObject2 = null;
        }
        MediaType parse32 = MediaType.parse("application/json");
        if (jSONObject2 != null) {
            Intrinsics.throwNpe();
            throw null;
        }
        RequestBody body2 = RequestBody.create(parse32, jSONObject2.toString());
        Intrinsics.checkExpressionValueIsNotNull(body2, "body");
        ApiImplService.Companion.getApiImplService().requestJCOrderMsg(sb2 + "/application/public/message/get", body2).compose(RxUtils.schedulersTransformer()).subscribe(new Observer<JCOrderMsg>() { // from class: com.one.tomato.ui.recharge.RechargeFragment$setProxyRechargeMessage$2$1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e5) {
                Intrinsics.checkParameterIsNotNull(e5, "e");
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d) {
                Intrinsics.checkParameterIsNotNull(d, "d");
            }

            @Override // io.reactivex.Observer
            public void onNext(JCOrderMsg bean) {
                Intrinsics.checkParameterIsNotNull(bean, "bean");
                if (bean.msg.messageCount > 0) {
                    LinearLayout liner_show_have_message_and_order2 = (LinearLayout) RechargeFragment.this._$_findCachedViewById(R$id.liner_show_have_message_and_order);
                    Intrinsics.checkExpressionValueIsNotNull(liner_show_have_message_and_order2, "liner_show_have_message_and_order");
                    liner_show_have_message_and_order2.setVisibility(0);
                    TextView text_have_message = (TextView) RechargeFragment.this._$_findCachedViewById(R$id.text_have_message);
                    Intrinsics.checkExpressionValueIsNotNull(text_have_message, "text_have_message");
                    text_have_message.setText(AppUtil.getString(R.string.agent_order_new_message));
                }
            }
        });
    }
}
