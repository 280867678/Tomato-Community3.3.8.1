package com.tomatolive.library.p136ui.view.dialog.confirm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.activity.home.AnchorAuthActivity;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.confirm.AnchorAuthDialog */
/* loaded from: classes3.dex */
public class AnchorAuthDialog extends BaseDialogFragment {
    private View.OnClickListener authListener;

    public static AnchorAuthDialog newInstance() {
        Bundle bundle = new Bundle();
        AnchorAuthDialog anchorAuthDialog = new AnchorAuthDialog();
        anchorAuthDialog.setArguments(bundle);
        return anchorAuthDialog;
    }

    public static AnchorAuthDialog newInstance(View.OnClickListener onClickListener) {
        Bundle bundle = new Bundle();
        AnchorAuthDialog anchorAuthDialog = new AnchorAuthDialog();
        anchorAuthDialog.setArguments(bundle);
        anchorAuthDialog.setAuthListener(onClickListener);
        return anchorAuthDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_auth_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_sure);
        textView.setText(R$string.fq_btn_go_auth);
        view.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$AnchorAuthDialog$Jni2gTlJhkEUErJo8dxnQfmDcdQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AnchorAuthDialog.this.lambda$initView$0$AnchorAuthDialog(view2);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$AnchorAuthDialog$KWEaXPl0VqHGBFMh0RRVvxK4kEs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AnchorAuthDialog.this.lambda$initView$1$AnchorAuthDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$AnchorAuthDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initView$1$AnchorAuthDialog(View view) {
        dismiss();
        Context context = this.mContext;
        context.startActivity(new Intent(context, AnchorAuthActivity.class));
    }

    private void setAuthListener(View.OnClickListener onClickListener) {
        this.authListener = onClickListener;
    }
}
