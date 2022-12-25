package com.one.tomato.dialog;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.dialog.RechargeExGameDialog;
import com.one.tomato.entity.RechargeAccount;
import com.one.tomato.entity.RechargeExGame;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.RxUtils;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RechargeExGameDialog.kt */
/* loaded from: classes3.dex */
public final class RechargeExGameDialog extends CustomAlertDialog {
    private int mExType;
    private RechargeAccount mRechargeAccount;
    private RechargeExGame mRechargeExGame;
    private RechargeExGameListener rechargeExGameListener;

    /* compiled from: RechargeExGameDialog.kt */
    /* loaded from: classes3.dex */
    public interface RechargeExGameListener {
        void rechargeExGameFail();

        void rechargeExGameStart();

        void rechargeExGameSuccess(RechargeExGame rechargeExGame, int i, String str);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RechargeExGameDialog(final Context context, int i, final RechargeAccount rechargeAccount, RechargeExGame rechargeExGame, int i2, RechargeExGameListener rechargeExGameListener) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(rechargeExGameListener, "rechargeExGameListener");
        this.mExType = 2;
        this.mRechargeAccount = rechargeAccount;
        this.mRechargeExGame = rechargeExGame;
        this.mExType = i2;
        this.rechargeExGameListener = rechargeExGameListener;
        setWidth(17, (int) (DisplayMetricsUtils.getWidth() * 0.8d), 1.0f);
        Double d = null;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_recharge_ex_game, (ViewGroup) null);
        setContentView(inflate);
        setMiddleNeedPadding(false);
        setCancelButtonBackgroundRes(R.drawable.common_shape_solid_corner5_disable);
        BaseApplication baseApplication = BaseApplication.instance;
        Intrinsics.checkExpressionValueIsNotNull(baseApplication, "BaseApplication.instance");
        if (baseApplication.isChess()) {
            setConfirmButtonBackgroundRes(R.drawable.chess_bright_bg);
        }
        TextView textView = (TextView) inflate.findViewById(R.id.tv_title);
        ConstraintLayout cl_switch = (ConstraintLayout) inflate.findViewById(R.id.cl_switch);
        TextView textView2 = (TextView) inflate.findViewById(R.id.tv_balance);
        TextView textView3 = (TextView) inflate.findViewById(R.id.tv_recharge);
        final EditText editText = (EditText) inflate.findViewById(R.id.et_input_balance);
        TextView textView4 = (TextView) inflate.findViewById(R.id.tv_ex_all);
        if (i == 1) {
            Intrinsics.checkExpressionValueIsNotNull(cl_switch, "cl_switch");
            cl_switch.setVisibility(8);
        } else if (i == 2) {
            Intrinsics.checkExpressionValueIsNotNull(cl_switch, "cl_switch");
            cl_switch.setVisibility(0);
        }
        int i3 = this.mExType;
        if (i3 == 1) {
            Object[] objArr = new Object[1];
            RechargeExGame rechargeExGame2 = this.mRechargeExGame;
            objArr[0] = rechargeExGame2 != null ? rechargeExGame2.name : null;
            textView.setText(AppUtil.getString(R.string.recharge_ex_out_title, objArr));
            Object[] objArr2 = new Object[1];
            RechargeExGame rechargeExGame3 = this.mRechargeExGame;
            objArr2[0] = FormatUtil.formatTwo(rechargeExGame3 != null ? Double.valueOf(rechargeExGame3.balance / 100) : d);
            textView2.setText(AppUtil.getString(R.string.recharge_ex_game_balance, objArr2));
        } else if (i3 == 2) {
            Object[] objArr3 = new Object[1];
            RechargeExGame rechargeExGame4 = this.mRechargeExGame;
            objArr3[0] = rechargeExGame4 != null ? rechargeExGame4.name : null;
            textView.setText(AppUtil.getString(R.string.recharge_ex_in_title, objArr3));
            Object[] objArr4 = new Object[1];
            RechargeAccount rechargeAccount2 = this.mRechargeAccount;
            objArr4[0] = FormatUtil.formatTwo(rechargeAccount2 != null ? Double.valueOf(rechargeAccount2.balance / 10) : d);
            textView2.setText(AppUtil.getString(R.string.recharge_ex_purse_balance, objArr4));
        }
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.RechargeExGameDialog.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RechargeExGameDialog.this.dismiss();
                RechargeActivity.startActivity(context);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.RechargeExGameDialog.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i4 = RechargeExGameDialog.this.mExType;
                Double d2 = null;
                if (i4 == 1) {
                    EditText editText2 = editText;
                    RechargeExGame rechargeExGame5 = RechargeExGameDialog.this.mRechargeExGame;
                    if (rechargeExGame5 != null) {
                        d2 = Double.valueOf(rechargeExGame5.balance / 100);
                    }
                    editText2.setText(FormatUtil.formatTwo(d2));
                } else if (i4 != 2 || rechargeAccount == null) {
                } else {
                    EditText editText3 = editText;
                    RechargeAccount rechargeAccount3 = RechargeExGameDialog.this.mRechargeAccount;
                    if (rechargeAccount3 != null) {
                        d2 = Double.valueOf(rechargeAccount3.balance / 10);
                    }
                    editText3.setText(FormatUtil.formatTwo(d2));
                }
            }
        });
        setCancelButtonListener();
        setConfirmButtonListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.RechargeExGameDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RechargeExGameDialog rechargeExGameDialog = RechargeExGameDialog.this;
                EditText et_input_balance = editText;
                Intrinsics.checkExpressionValueIsNotNull(et_input_balance, "et_input_balance");
                rechargeExGameDialog.exChange(et_input_balance.getText().toString());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void exChange(final String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        double parseDouble = Double.parseDouble(str);
        int i = this.mExType;
        if (i == 1) {
            RechargeExGame rechargeExGame = this.mRechargeExGame;
            if (rechargeExGame == null) {
                return;
            }
            if (rechargeExGame != null && rechargeExGame.balance == 0.0d) {
                return;
            }
            str2 = "/app/liveGame/reduceMoney";
        } else if (i != 2) {
            str2 = "";
        } else {
            RechargeAccount rechargeAccount = this.mRechargeAccount;
            if (rechargeAccount == null) {
                return;
            }
            if (rechargeAccount != null && rechargeAccount.balance == 0.0d) {
                return;
            }
            str2 = "/app/liveGame/addMoney";
        }
        this.rechargeExGameListener.rechargeExGameStart();
        ApiImplService apiImplService = ApiImplService.Companion.getApiImplService();
        StringBuilder sb = new StringBuilder();
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        sb.append(domainServer.getServerUrl());
        sb.append(str2);
        String sb2 = sb.toString();
        String formatZero = FormatUtil.formatZero(Double.valueOf(parseDouble * 100));
        Intrinsics.checkExpressionValueIsNotNull(formatZero, "FormatUtil.formatZero(inputDouble * 100)");
        RechargeExGame rechargeExGame2 = this.mRechargeExGame;
        if (rechargeExGame2 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        String str3 = rechargeExGame2.brandId;
        Intrinsics.checkExpressionValueIsNotNull(str3, "mRechargeExGame!!.brandId");
        apiImplService.requestEx(sb2, formatZero, str3).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.dialog.RechargeExGameDialog$exChange$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                RechargeExGameDialog.RechargeExGameListener rechargeExGameListener;
                RechargeExGameDialog.this.dismiss();
                rechargeExGameListener = RechargeExGameDialog.this.rechargeExGameListener;
                RechargeExGame rechargeExGame3 = RechargeExGameDialog.this.mRechargeExGame;
                if (rechargeExGame3 != null) {
                    rechargeExGameListener.rechargeExGameSuccess(rechargeExGame3, RechargeExGameDialog.this.mExType, str);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                RechargeExGameDialog.RechargeExGameListener rechargeExGameListener;
                Intrinsics.checkParameterIsNotNull(e, "e");
                RechargeExGameDialog.this.dismiss();
                rechargeExGameListener = RechargeExGameDialog.this.rechargeExGameListener;
                rechargeExGameListener.rechargeExGameFail();
            }
        });
    }
}
