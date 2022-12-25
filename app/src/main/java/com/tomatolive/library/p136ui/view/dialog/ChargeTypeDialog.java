package com.tomatolive.library.p136ui.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.RelationLastLiveEntity;
import com.tomatolive.library.model.StartLiveVerifyEntity;
import com.tomatolive.library.p136ui.interfaces.OnPayLiveCallback;
import com.tomatolive.library.p136ui.view.dialog.SpinnerDialog;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.math.BigDecimal;
import java.util.Arrays;

/* renamed from: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog */
/* loaded from: classes3.dex */
public class ChargeTypeDialog extends BaseDialogFragment {
    private SpinnerDialog chargeTypePop;
    private EditText etTicketsPrice;
    private LinearLayout llRelationBg;
    private LinearLayout llTicketsPriceBg;
    private LinearLayout llUsePropsBg;
    private OnPayLiveCallback onSubmitListener;
    private RelationLastLiveEntity relationLastLiveEntity;
    private SpinnerDialog relationLivePop;
    private String ticketPrice;
    private TextView tvCancel;
    private TextView tvChargeType;
    private TextView tvPriceTimes;
    private TextView tvQueryTips;
    private TextView tvRelationLive;
    private TextView tvSubmit;
    private TextView tvUseProps;
    private SpinnerDialog usePropsPop;
    private StartLiveVerifyEntity verifyEntity;
    private String chargeType = "0";
    private String isAllowTicket = "0";
    private String isRelation = "0";

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.72d;
    }

    public static ChargeTypeDialog newInstance(StartLiveVerifyEntity startLiveVerifyEntity, OnPayLiveCallback onPayLiveCallback) {
        Bundle bundle = new Bundle();
        ChargeTypeDialog chargeTypeDialog = new ChargeTypeDialog();
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, startLiveVerifyEntity);
        chargeTypeDialog.setArguments(bundle);
        chargeTypeDialog.setOnSubmitListener(onPayLiveCallback);
        return chargeTypeDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.verifyEntity = (StartLiveVerifyEntity) bundle.getParcelable(ConstantUtils.RESULT_ITEM);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_pay_charge_type;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected void initView(View view) {
        this.llTicketsPriceBg = (LinearLayout) view.findViewById(R$id.ll_tickets_price_bg);
        this.llUsePropsBg = (LinearLayout) view.findViewById(R$id.ll_use_props_bg);
        this.llRelationBg = (LinearLayout) view.findViewById(R$id.ll_last_live_bg);
        this.tvChargeType = (TextView) view.findViewById(R$id.tv_charge_type);
        this.tvUseProps = (TextView) view.findViewById(R$id.tv_use_props);
        this.tvRelationLive = (TextView) view.findViewById(R$id.tv_relation_live);
        this.tvQueryTips = (TextView) view.findViewById(R$id.tv_query_tips);
        this.tvCancel = (TextView) view.findViewById(R$id.tv_cancel);
        this.tvSubmit = (TextView) view.findViewById(R$id.tv_submit);
        this.tvPriceTimes = (TextView) view.findViewById(R$id.tv_price_times);
        this.etTicketsPrice = (EditText) view.findViewById(R$id.et_tickets_price);
        StartLiveVerifyEntity startLiveVerifyEntity = this.verifyEntity;
        if (startLiveVerifyEntity != null && !TextUtils.isEmpty(startLiveVerifyEntity.getTicketPriceIntervalTips())) {
            this.etTicketsPrice.setHint(this.mContext.getString(R$string.fq_pay_tickets_price_interval_tips, this.verifyEntity.getTicketPriceIntervalTips()));
        }
        TextView textView = this.tvPriceTimes;
        Context context = this.mContext;
        textView.setText(context.getString(R$string.fq_pay_ticket_price, context.getString(R$string.fq_tomato_money_str)));
        initDefaultData();
    }

    private void initDefaultData() {
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_charge_type_menu);
        String[] stringArray2 = this.mContext.getResources().getStringArray(R$array.fq_allow_type_menu);
        String[] stringArray3 = this.mContext.getResources().getStringArray(R$array.fq_relation_type_menu);
        this.chargeTypePop = new SpinnerDialog(this.mContext, Arrays.asList(stringArray));
        this.chargeTypePop.setBackground(0);
        this.usePropsPop = new SpinnerDialog(this.mContext, Arrays.asList(stringArray2));
        this.usePropsPop.setBackground(0);
        this.relationLivePop = new SpinnerDialog(this.mContext, Arrays.asList(stringArray3));
        this.relationLivePop.setBackground(0);
        this.tvChargeType.setText(stringArray[0]);
        this.tvUseProps.setText(stringArray2[0]);
        this.tvRelationLive.setText(stringArray3[0]);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.tvChargeType.post(new Runnable() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.1
            @Override // java.lang.Runnable
            public void run() {
                ChargeTypeDialog.this.chargeTypePop.setWidth(ChargeTypeDialog.this.tvChargeType.getMeasuredWidth());
            }
        });
        this.tvUseProps.post(new Runnable() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.2
            @Override // java.lang.Runnable
            public void run() {
                ChargeTypeDialog.this.usePropsPop.setWidth(ChargeTypeDialog.this.tvUseProps.getMeasuredWidth());
            }
        });
        this.tvRelationLive.post(new Runnable() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.3
            @Override // java.lang.Runnable
            public void run() {
                ChargeTypeDialog.this.relationLivePop.setWidth(ChargeTypeDialog.this.tvRelationLive.getMeasuredWidth());
            }
        });
        this.tvChargeType.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                ChargeTypeDialog.this.chargeTypePop.showPopupWindow(view2);
            }
        });
        this.tvUseProps.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                ChargeTypeDialog.this.usePropsPop.showPopupWindow(view2);
            }
        });
        this.tvRelationLive.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                ChargeTypeDialog.this.relationLivePop.showPopupWindow(view2);
            }
        });
        this.chargeTypePop.setOnSpinnerItemClickListener(new SpinnerDialog.OnSpinnerItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.7
            @Override // com.tomatolive.library.p136ui.view.dialog.SpinnerDialog.OnSpinnerItemClickListener
            public void onItemClick(String str, int i) {
                ChargeTypeDialog.this.tvChargeType.setText(str);
                ChargeTypeDialog.this.chargeType = i == 0 ? "0" : "1";
                ChargeTypeDialog chargeTypeDialog = ChargeTypeDialog.this;
                boolean z = true;
                if (i != 1) {
                    z = false;
                }
                chargeTypeDialog.initContentView(z);
            }
        });
        this.usePropsPop.setOnSpinnerItemClickListener(new SpinnerDialog.OnSpinnerItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.8
            @Override // com.tomatolive.library.p136ui.view.dialog.SpinnerDialog.OnSpinnerItemClickListener
            public void onItemClick(String str, int i) {
                ChargeTypeDialog.this.tvUseProps.setText(str);
                ChargeTypeDialog.this.isAllowTicket = i == 1 ? "1" : "0";
            }
        });
        this.relationLivePop.setOnSpinnerItemClickListener(new SpinnerDialog.OnSpinnerItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.9
            @Override // com.tomatolive.library.p136ui.view.dialog.SpinnerDialog.OnSpinnerItemClickListener
            public void onItemClick(String str, int i) {
                ChargeTypeDialog.this.tvRelationLive.setText(str);
                if (i == 1) {
                    ChargeTypeDialog.this.isRelation = "1";
                    ChargeTypeDialog.this.sendRelationRequest();
                    return;
                }
                ChargeTypeDialog.this.isRelation = "0";
                ChargeTypeDialog.this.tvQueryTips.setVisibility(4);
            }
        });
        this.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                ChargeTypeDialog.this.dismiss();
            }
        });
        this.tvSubmit.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                double doubleValue;
                if (TextUtils.equals(ChargeTypeDialog.this.chargeType, "0")) {
                    if (ChargeTypeDialog.this.onSubmitListener == null) {
                        return;
                    }
                    ChargeTypeDialog.this.dismiss();
                    ChargeTypeDialog.this.onSubmitListener.onPayLiveInfoSubmit("0", "", ChargeTypeDialog.this.isAllowTicket, ChargeTypeDialog.this.isRelation, null);
                    return;
                }
                ChargeTypeDialog chargeTypeDialog = ChargeTypeDialog.this;
                chargeTypeDialog.ticketPrice = chargeTypeDialog.etTicketsPrice.getText().toString().trim();
                if (!TextUtils.isEmpty(ChargeTypeDialog.this.ticketPrice)) {
                    if (!ChargeTypeDialog.this.ticketPrice.startsWith("0") && !ChargeTypeDialog.this.ticketPrice.startsWith(".")) {
                        double string2Double = NumberUtils.string2Double(ChargeTypeDialog.this.ticketPrice);
                        if (SysConfigInfoManager.getInstance().isEnableExchangeProportion()) {
                            doubleValue = NumberUtils.mul(string2Double, 100.0d);
                        } else {
                            doubleValue = BigDecimal.valueOf(NumberUtils.mul(string2Double, 100.0d)).divide(new BigDecimal(10)).doubleValue();
                        }
                        if (ChargeTypeDialog.this.onSubmitListener == null) {
                            return;
                        }
                        if (ChargeTypeDialog.this.relationLastLiveEntity == null) {
                            ChargeTypeDialog.this.isRelation = "0";
                        }
                        ChargeTypeDialog.this.onSubmitListener.onPayLiveInfoSubmit(ChargeTypeDialog.this.chargeType, String.valueOf(new Double(doubleValue).longValue()), ChargeTypeDialog.this.isAllowTicket, ChargeTypeDialog.this.isRelation, ChargeTypeDialog.this.relationLastLiveEntity);
                        ChargeTypeDialog.this.resetData();
                        ChargeTypeDialog.this.dismiss();
                        return;
                    }
                    ChargeTypeDialog.this.showToast(R$string.fq_pay_live_price_error);
                    return;
                }
                ChargeTypeDialog.this.showToast(R$string.fq_pay_tickets_price_empty_tips);
            }
        });
    }

    public void setOnSubmitListener(OnPayLiveCallback onPayLiveCallback) {
        this.onSubmitListener = onPayLiveCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initContentView(boolean z) {
        int i = 0;
        this.llTicketsPriceBg.setVisibility(z ? 0 : 4);
        this.llUsePropsBg.setVisibility(z ? 0 : 4);
        this.llRelationBg.setVisibility(z ? 0 : 4);
        TextView textView = this.tvQueryTips;
        if (!z) {
            i = 4;
        }
        textView.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRelationRequest() {
        ApiRetrofit.getInstance().getApiService().getRecentlyTicketRoomService(new RequestParams().getStartPayLiveVerifyParams()).map(new ServerResultFunction<RelationLastLiveEntity>() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.13
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<RelationLastLiveEntity>() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.12
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                ChargeTypeDialog.this.tvQueryTips.setVisibility(0);
                ChargeTypeDialog.this.tvQueryTips.setText(R$string.fq_pay_relation_live_times_loading);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(RelationLastLiveEntity relationLastLiveEntity) {
                if (relationLastLiveEntity == null || TextUtils.isEmpty(relationLastLiveEntity.relationStartLiveId)) {
                    ChargeTypeDialog.this.setDefaultRelation();
                    return;
                }
                ChargeTypeDialog.this.relationLastLiveEntity = relationLastLiveEntity;
                ChargeTypeDialog.this.tvQueryTips.setText(((BaseRxDialogFragment) ChargeTypeDialog.this).mContext.getString(R$string.fq_pay_relation_live_times, relationLastLiveEntity.getCreateTime() + ConstantUtils.PLACEHOLDER_STR_ONE + relationLastLiveEntity.relationStartLiveTopic));
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                ChargeTypeDialog.this.setDefaultRelation();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDefaultRelation() {
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_relation_type_menu);
        this.isRelation = "0";
        this.relationLivePop.setSpinnerItemSelected(0);
        this.tvRelationLive.setText(stringArray[0]);
        this.tvQueryTips.setText(R$string.fq_pay_relation_live_times_empty);
        this.tvQueryTips.postDelayed(new Runnable() { // from class: com.tomatolive.library.ui.view.dialog.ChargeTypeDialog.14
            @Override // java.lang.Runnable
            public void run() {
                ChargeTypeDialog.this.tvQueryTips.setVisibility(4);
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetData() {
        this.chargeType = "0";
        this.tvChargeType.setText(this.chargeTypePop.getDefaultItemMenu());
        this.chargeTypePop.setSpinnerItemSelected(0);
        this.isAllowTicket = "0";
        this.tvUseProps.setText(this.usePropsPop.getDefaultItemMenu());
        this.usePropsPop.setSpinnerItemSelected(0);
        this.isRelation = "0";
        this.tvRelationLive.setText(this.relationLivePop.getDefaultItemMenu());
        this.relationLivePop.setSpinnerItemSelected(0);
        this.tvQueryTips.setText("");
        this.tvQueryTips.setVisibility(4);
        this.ticketPrice = "";
        this.etTicketsPrice.setText("");
        initContentView(false);
        this.relationLastLiveEntity = null;
    }
}
