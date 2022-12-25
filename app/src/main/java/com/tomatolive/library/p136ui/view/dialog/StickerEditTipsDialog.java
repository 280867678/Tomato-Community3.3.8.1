package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.StickerEditTipsDialog */
/* loaded from: classes3.dex */
public class StickerEditTipsDialog extends BaseDialogFragment {
    private static final String TIPS = "tips";
    private View.OnClickListener authListener;

    public static StickerEditTipsDialog newInstance(View.OnClickListener onClickListener) {
        Bundle bundle = new Bundle();
        StickerEditTipsDialog stickerEditTipsDialog = new StickerEditTipsDialog();
        stickerEditTipsDialog.setArguments(bundle);
        stickerEditTipsDialog.setSureListener(onClickListener);
        return stickerEditTipsDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_auth_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_cancel);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_sure);
        ((TextView) view.findViewById(R$id.tv_content)).setText(R$string.fq_sticker_add_edit_dialog_tips);
        textView.setText(R$string.fq_waiver_exit);
        textView2.setText(R$string.fq_think_again);
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$StickerEditTipsDialog$358onK5YdGpyHCt6E4Nee6ln708
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StickerEditTipsDialog.this.lambda$initView$0$StickerEditTipsDialog(view2);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$StickerEditTipsDialog$Q8w0Lu_tnOh6XJEedKx5zLblXtA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StickerEditTipsDialog.this.lambda$initView$1$StickerEditTipsDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$StickerEditTipsDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initView$1$StickerEditTipsDialog(View view) {
        if (this.authListener != null) {
            dismiss();
            this.authListener.onClick(view);
        }
    }

    private void setSureListener(View.OnClickListener onClickListener) {
        this.authListener = onClickListener;
    }
}
