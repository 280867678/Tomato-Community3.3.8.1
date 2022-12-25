package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.LotteryNoticeCenterDialog */
/* loaded from: classes3.dex */
public class LotteryNoticeCenterDialog extends BaseDialogFragment {
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getHeightScale() {
        return 0.48d;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.65d;
    }

    public static LotteryNoticeCenterDialog newInstance() {
        Bundle bundle = new Bundle();
        LotteryNoticeCenterDialog lotteryNoticeCenterDialog = new LotteryNoticeCenterDialog();
        lotteryNoticeCenterDialog.setArguments(bundle);
        return lotteryNoticeCenterDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_lottery_notice;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected void initView(View view) {
        ((TextView) view.findViewById(R$id.tv_title)).setText(R$string.fq_turntable_revision_tips);
        ((TextView) view.findViewById(R$id.tv_desc)).setText(R$string.fq_turntable_guide_notice_tips);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        view.findViewById(R$id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryNoticeCenterDialog$nEGRpXu0ig00tpXFEQT8b7aRWvY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LotteryNoticeCenterDialog.this.lambda$initListener$0$LotteryNoticeCenterDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$LotteryNoticeCenterDialog(View view) {
        dismiss();
    }
}
