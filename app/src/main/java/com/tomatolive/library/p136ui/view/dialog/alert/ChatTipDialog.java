package com.tomatolive.library.p136ui.view.dialog.alert;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.alert.ChatTipDialog */
/* loaded from: classes3.dex */
public class ChatTipDialog extends BaseDialogFragment {
    private static final String CONTENT = "content";
    private static final String IS_RED_TIPS = "isRedTips";

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public boolean getCancelOutside() {
        return false;
    }

    public static ChatTipDialog newInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT, str);
        bundle.putBoolean(IS_RED_TIPS, false);
        ChatTipDialog chatTipDialog = new ChatTipDialog();
        chatTipDialog.setArguments(bundle);
        return chatTipDialog;
    }

    public static ChatTipDialog newInstance(String str, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT, str);
        bundle.putBoolean(IS_RED_TIPS, z);
        ChatTipDialog chatTipDialog = new ChatTipDialog();
        chatTipDialog.setArguments(bundle);
        return chatTipDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_chat_tip_dialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        ((TextView) view.findViewById(R$id.tv_sure)).setText(R$string.fq_know);
        TextView textView = (TextView) view.findViewById(R$id.tv_tips);
        String argumentsString = getArgumentsString(CONTENT);
        if (getArgumentsBoolean(IS_RED_TIPS, false)) {
            textView.setText(Html.fromHtml(argumentsString));
        } else {
            textView.setText(argumentsString);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        view.findViewById(R$id.ll_button).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.alert.-$$Lambda$ChatTipDialog$sOblhV_wvzXhNlHV6-jxF18Noog
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChatTipDialog.this.lambda$initListener$0$ChatTipDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$ChatTipDialog(View view) {
        dismiss();
    }
}
