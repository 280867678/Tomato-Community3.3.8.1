package com.tomatolive.library.p136ui.view.dialog.confirm;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.confirm.TransDialog */
/* loaded from: classes3.dex */
public class TransDialog extends BaseDialogFragment {
    private View.OnClickListener transListener;

    public static TransDialog newInstance(View.OnClickListener onClickListener) {
        Bundle bundle = new Bundle();
        TransDialog transDialog = new TransDialog();
        transDialog.setArguments(bundle);
        transDialog.setTransListener(onClickListener);
        return transDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_auth_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        ((TextView) view.findViewById(R$id.tv_title)).setText(R$string.fq_open_translation_tips);
        ((TextView) view.findViewById(R$id.tv_content)).setText(R$string.fq_text_list_trans_tips);
        ((TextView) view.findViewById(R$id.tv_sure)).setText(R$string.fq_open_translation);
        view.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$TransDialog$b5XmNJCkTuNRuuk00Vuamqz0tSM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                TransDialog.this.lambda$initView$0$TransDialog(view2);
            }
        });
        view.findViewById(R$id.tv_sure).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$TransDialog$On3HlNMeOV-d-uW9j_0_EJOLg30
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                TransDialog.this.lambda$initView$1$TransDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$TransDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initView$1$TransDialog(View view) {
        if (this.transListener != null) {
            dismiss();
            this.transListener.onClick(view);
        }
    }

    public View.OnClickListener getAuthListener() {
        return this.transListener;
    }

    private void setTransListener(View.OnClickListener onClickListener) {
        this.transListener = onClickListener;
    }
}
