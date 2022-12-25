package com.tomatolive.library.p136ui.view.dialog.confirm;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.confirm.NetworkPromptDialog */
/* loaded from: classes3.dex */
public class NetworkPromptDialog extends BaseDialogFragment {
    private static final String CANCEL_STR = "cancelStr";
    private static final String SURE_STR = "sureStr";
    private static final String TIPS = "tips";
    private View.OnClickListener cancelListener;
    private View.OnClickListener sureListener;

    public static NetworkPromptDialog newInstance(String str, String str2, String str3, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        Bundle bundle = new Bundle();
        NetworkPromptDialog networkPromptDialog = new NetworkPromptDialog();
        networkPromptDialog.setArguments(bundle);
        bundle.putString(TIPS, str);
        bundle.putString(SURE_STR, str2);
        bundle.putString(CANCEL_STR, str3);
        networkPromptDialog.setCancelListener(onClickListener2);
        networkPromptDialog.setSureListener(onClickListener);
        return networkPromptDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_auth_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_cancel);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_sure);
        ((TextView) view.findViewById(R$id.tv_content)).setText(getArgumentsString(TIPS));
        textView.setText(getArgumentsString(CANCEL_STR));
        textView2.setText(getArgumentsString(SURE_STR));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$NetworkPromptDialog$BTqf-WNv7QY8xgH-Y169t6t2OYs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                NetworkPromptDialog.this.lambda$initView$0$NetworkPromptDialog(view2);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$NetworkPromptDialog$vhMMlDjxVx7_UroW_zzLLNeGEHU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                NetworkPromptDialog.this.lambda$initView$1$NetworkPromptDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$NetworkPromptDialog(View view) {
        if (this.cancelListener != null) {
            dismiss();
            this.cancelListener.onClick(view);
        }
    }

    public /* synthetic */ void lambda$initView$1$NetworkPromptDialog(View view) {
        if (this.sureListener != null) {
            dismiss();
            this.sureListener.onClick(view);
        }
    }

    public View.OnClickListener getSureListener() {
        return this.sureListener;
    }

    private void setSureListener(View.OnClickListener onClickListener) {
        this.sureListener = onClickListener;
    }

    public View.OnClickListener getCancelListener() {
        return this.cancelListener;
    }

    private void setCancelListener(View.OnClickListener onClickListener) {
        this.cancelListener = onClickListener;
    }
}
