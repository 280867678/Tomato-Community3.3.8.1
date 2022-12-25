package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.RechargeDialog */
/* loaded from: classes3.dex */
public class RechargeDialog extends BaseDialogFragment {
    private static final String TIPS = "tips";
    private View.OnClickListener rechargeListener;

    public static RechargeDialog newInstance(View.OnClickListener onClickListener) {
        Bundle bundle = new Bundle();
        bundle.putString(TIPS, "");
        RechargeDialog rechargeDialog = new RechargeDialog();
        rechargeDialog.setRechargeListener(onClickListener);
        rechargeDialog.setArguments(bundle);
        return rechargeDialog;
    }

    public static RechargeDialog newInstance(String str, View.OnClickListener onClickListener) {
        Bundle bundle = new Bundle();
        bundle.putString(TIPS, str);
        RechargeDialog rechargeDialog = new RechargeDialog();
        rechargeDialog.setRechargeListener(onClickListener);
        rechargeDialog.setArguments(bundle);
        return rechargeDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_auth_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_content);
        String argumentsString = getArgumentsString(TIPS);
        if (!TextUtils.isEmpty(argumentsString)) {
            textView.setText(argumentsString);
        } else {
            textView.setText(R$string.fq_money_not_enough);
        }
        view.findViewById(R$id.tv_sure).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$RechargeDialog$ZqqoLKGaB1SAjdoMCvJXPBDdxhw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                RechargeDialog.this.lambda$initView$0$RechargeDialog(view2);
            }
        });
        view.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$RechargeDialog$14yPXGWYTxcWUvkuK6HqNAd8a0o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                RechargeDialog.this.lambda$initView$1$RechargeDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$RechargeDialog(View view) {
        dismiss();
        View.OnClickListener onClickListener = this.rechargeListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    public /* synthetic */ void lambda$initView$1$RechargeDialog(View view) {
        dismiss();
    }

    public View.OnClickListener getAuthListener() {
        return this.rechargeListener;
    }

    private void setRechargeListener(View.OnClickListener onClickListener) {
        this.rechargeListener = onClickListener;
    }
}
