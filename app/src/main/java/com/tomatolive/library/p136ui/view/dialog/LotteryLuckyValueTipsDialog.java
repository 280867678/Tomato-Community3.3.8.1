package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.view.View;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.LotteryLuckyValueTipsDialog */
/* loaded from: classes3.dex */
public class LotteryLuckyValueTipsDialog extends BaseDialogFragment {
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getHeightScale() {
        return 0.2d;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.7d;
    }

    public static LotteryLuckyValueTipsDialog newInstance() {
        Bundle bundle = new Bundle();
        LotteryLuckyValueTipsDialog lotteryLuckyValueTipsDialog = new LotteryLuckyValueTipsDialog();
        lotteryLuckyValueTipsDialog.setArguments(bundle);
        return lotteryLuckyValueTipsDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_lottery_lucky_value_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        view.findViewById(R$id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryLuckyValueTipsDialog$ukLzETd8ufkjN65ZLZntP1p0MTg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LotteryLuckyValueTipsDialog.this.lambda$initView$0$LotteryLuckyValueTipsDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$LotteryLuckyValueTipsDialog(View view) {
        dismiss();
    }
}
