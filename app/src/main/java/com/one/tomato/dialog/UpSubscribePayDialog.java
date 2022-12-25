package com.one.tomato.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.RechargeAccount;
import com.one.tomato.entity.UpSubscribePayBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.p082up.adapter.UpSubscribePayChooseAdapter;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpSubscribePayDialog.kt */
/* loaded from: classes3.dex */
public final class UpSubscribePayDialog extends Dialog {
    private UpSubscribePayChooseAdapter adapter;
    private double balance;
    private int memberId;

    public final void addSubscribeCallBack(Functions<Unit> functions) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UpSubscribePayDialog(Context context) {
        super(context, R.style.PostRewardDialog);
        Intrinsics.checkParameterIsNotNull(context, "context");
        setContentView(LayoutInflater.from(context).inflate(R.layout.up_subscribe_pay_dialog, (ViewGroup) null));
        Window window = getWindow();
        Intrinsics.checkExpressionValueIsNotNull(window, "window");
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 80;
        attributes.width = -1;
        attributes.alpha = 1.0f;
        window.setAttributes(attributes);
        initView();
    }

    private final void initView() {
        this.adapter = new UpSubscribePayChooseAdapter(getContext(), (RecyclerView) findViewById(R$id.recycler_view_pay));
        RecyclerView recyclerView = (RecyclerView) findViewById(R$id.recycler_view_pay);
        if (recyclerView != null) {
            recyclerView.setAdapter(this.adapter);
        }
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R$id.recycler_view_pay);
        if (recyclerView2 != null) {
            recyclerView2.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getContext());
        builder.setColorResource(R.color.transparent);
        builder.setVerticalSpan(R.dimen.dimen_16);
        GridItemDecoration build = builder.build();
        RecyclerView recyclerView3 = (RecyclerView) findViewById(R$id.recycler_view_pay);
        if (recyclerView3 != null) {
            recyclerView3.addItemDecoration(build);
        }
        TextView textView = (TextView) findViewById(R$id.text_recharge);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.UpSubscribePayDialog$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RechargeActivity.startActivity(UpSubscribePayDialog.this.getOwnerActivity());
                }
            });
        }
        UpSubscribePayChooseAdapter upSubscribePayChooseAdapter = this.adapter;
        if (upSubscribePayChooseAdapter != null) {
            upSubscribePayChooseAdapter.setOnItemClickListener(UpSubscribePayDialog$initView$2.INSTANCE);
        }
        Button button = (Button) findViewById(R$id.button_pay);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.UpSubscribePayDialog$initView$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UpSubscribePayChooseAdapter upSubscribePayChooseAdapter2;
                    int i;
                    double d;
                    upSubscribePayChooseAdapter2 = UpSubscribePayDialog.this.adapter;
                    List<UpSubscribePayBean> data = upSubscribePayChooseAdapter2 != null ? upSubscribePayChooseAdapter2.getData() : null;
                    int i2 = 0;
                    if (!(data == null || data.isEmpty())) {
                        for (Object obj : data) {
                            int i3 = i2 + 1;
                            if (i2 >= 0) {
                                UpSubscribePayBean upSubscribePayBean = (UpSubscribePayBean) obj;
                                Intrinsics.checkExpressionValueIsNotNull(upSubscribePayBean, "upSubscribePayBean");
                                if (upSubscribePayBean.isSelect()) {
                                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                                    linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
                                    i = UpSubscribePayDialog.this.memberId;
                                    linkedHashMap.put("toMemberId", Integer.valueOf(i));
                                    if (i2 == 0) {
                                        linkedHashMap.put("payType", 5);
                                    } else if (i2 == 1) {
                                        linkedHashMap.put("payType", 6);
                                    } else if (i2 == 2) {
                                        linkedHashMap.put("payType", 7);
                                    }
                                    int price = upSubscribePayBean.getPrice() * 10;
                                    d = UpSubscribePayDialog.this.balance;
                                    if (price > d) {
                                        ToastUtil.showCenterToast(AppUtil.getString(R.string.up_reward_pay_no));
                                        return;
                                    }
                                    linkedHashMap.put("money", Integer.valueOf(price));
                                    UpSubscribePayDialog.this.requestRewardPay(linkedHashMap);
                                    return;
                                }
                                i2 = i3;
                            } else {
                                CollectionsKt.throwIndexOverflow();
                                throw null;
                            }
                        }
                    }
                }
            });
        }
        requestBalance();
    }

    private final void requestBalance() {
        ApiImplService.Companion.getApiImplService().requestRechargeAccount(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<RechargeAccount>() { // from class: com.one.tomato.dialog.UpSubscribePayDialog$requestBalance$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(RechargeAccount rechargeAccount) {
                if (rechargeAccount != null) {
                    UpSubscribePayDialog.this.balance = rechargeAccount.balance;
                    TextView textView = (TextView) UpSubscribePayDialog.this.findViewById(R$id.text_current_balance);
                    if (textView == null) {
                        return;
                    }
                    textView.setText(AppUtil.getString(R.string.up_subscribe_pay_current_balance) + FormatUtil.formatTomato2RMB(rechargeAccount.balance));
                }
            }
        });
    }

    public final void setListPrice(ArrayList<Integer> listPrice, int i) {
        Intrinsics.checkParameterIsNotNull(listPrice, "listPrice");
        this.memberId = i;
        if (!listPrice.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            int i2 = 0;
            for (Object obj : listPrice) {
                int i3 = i2 + 1;
                if (i2 >= 0) {
                    int intValue = ((Number) obj).intValue();
                    UpSubscribePayBean upSubscribePayBean = new UpSubscribePayBean();
                    if (i2 == 0) {
                        upSubscribePayBean.setSelect(true);
                        upSubscribePayBean.setGift(AppUtil.getString(R.string.up_pay_gift_3));
                        upSubscribePayBean.setTitle(AppUtil.getString(R.string.up_pay_title1));
                    } else if (i2 == 1) {
                        upSubscribePayBean.setGift(AppUtil.getString(R.string.up_pay_gift_10));
                        upSubscribePayBean.setTitle(AppUtil.getString(R.string.up_pay_title2));
                    } else if (i2 == 2) {
                        upSubscribePayBean.setGift(AppUtil.getString(R.string.up_pay_gift_30));
                        upSubscribePayBean.setTitle(AppUtil.getString(R.string.up_pay_title3));
                    }
                    upSubscribePayBean.setPrice(intValue);
                    arrayList.add(upSubscribePayBean);
                    i2 = i3;
                } else {
                    CollectionsKt.throwIndexOverflow();
                    throw null;
                }
            }
            UpSubscribePayChooseAdapter upSubscribePayChooseAdapter = this.adapter;
            if (upSubscribePayChooseAdapter != null) {
                upSubscribePayChooseAdapter.setNewData(arrayList);
            }
            UpSubscribePayChooseAdapter upSubscribePayChooseAdapter2 = this.adapter;
            if (upSubscribePayChooseAdapter2 == null) {
                return;
            }
            upSubscribePayChooseAdapter2.setEnableLoadMore(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestRewardPay(Map<String, Object> map) {
        ApiImplService.Companion.getApiImplService().requestUpSubscribePay(map).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.dialog.UpSubscribePayDialog$requestRewardPay$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                if (UpSubscribePayDialog.this.getOwnerActivity() instanceof MvpBaseActivity) {
                    Activity ownerActivity = UpSubscribePayDialog.this.getOwnerActivity();
                    if (ownerActivity == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) ownerActivity).showWaitingDialog();
                }
            }
        }).subscribe(new ApiDisposableObserver<String>() { // from class: com.one.tomato.dialog.UpSubscribePayDialog$requestRewardPay$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(String str) {
                if (UpSubscribePayDialog.this.getOwnerActivity() instanceof MvpBaseActivity) {
                    Activity ownerActivity = UpSubscribePayDialog.this.getOwnerActivity();
                    if (ownerActivity == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) ownerActivity).hideWaitingDialog();
                }
                ToastUtil.showCenterToast(AppUtil.getString(R.string.up_subscribe_sucess));
                UpSubscribePayDialog.this.dismiss();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                if (UpSubscribePayDialog.this.getOwnerActivity() instanceof MvpBaseActivity) {
                    Activity ownerActivity = UpSubscribePayDialog.this.getOwnerActivity();
                    if (ownerActivity == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.base.view.MvpBaseActivity<*, *>");
                    }
                    ((MvpBaseActivity) ownerActivity).hideWaitingDialog();
                }
            }
        });
    }
}
