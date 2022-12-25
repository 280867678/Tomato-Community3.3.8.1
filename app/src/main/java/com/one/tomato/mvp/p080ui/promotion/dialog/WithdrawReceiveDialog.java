package com.one.tomato.mvp.p080ui.promotion.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.broccoli.p150bh.R;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.mvp.p080ui.promotion.p081ui.XiaobaiHtmlActivity;

/* renamed from: com.one.tomato.mvp.ui.promotion.dialog.WithdrawReceiveDialog */
/* loaded from: classes3.dex */
public class WithdrawReceiveDialog extends CustomAlertDialog {
    public WithdrawReceiveDialog(final Context context) {
        super(context);
        bottomLayoutGone();
        setMiddleNeedPadding(false);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_withdraw_receive, (ViewGroup) null);
        setContentView(inflate);
        inflate.findViewById(R.id.tv_withdraw_confirm).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.dialog.-$$Lambda$WithdrawReceiveDialog$7v9KRt8u1Yuq9TuO9lX_bZ6a0rI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WithdrawReceiveDialog.this.lambda$new$0$WithdrawReceiveDialog(view);
            }
        });
        inflate.findViewById(R.id.tv_withdraw_tip_xiaobai).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.dialog.-$$Lambda$WithdrawReceiveDialog$B2x6rquQCYPO2j0dOojV0kMIKos
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WithdrawReceiveDialog.this.lambda$new$1$WithdrawReceiveDialog(context, view);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$WithdrawReceiveDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$new$1$WithdrawReceiveDialog(Context context, View view) {
        dismiss();
        XiaobaiHtmlActivity.Companion.startActivity(context);
    }
}
