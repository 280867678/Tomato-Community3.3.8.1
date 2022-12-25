package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.view.View;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.utils.ConstantUtils;

/* renamed from: com.tomatolive.library.ui.view.dialog.AnchorEndLiveDialog */
/* loaded from: classes3.dex */
public class AnchorEndLiveDialog extends BaseBottomDialogFragment {
    private BottomDialogUtils.BottomPromptMenuListener bottomPromptMenuListener;
    private boolean isPayLive = false;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected boolean getCancelOutside() {
        return false;
    }

    public static AnchorEndLiveDialog newInstance(boolean z, BottomDialogUtils.BottomPromptMenuListener bottomPromptMenuListener) {
        Bundle bundle = new Bundle();
        AnchorEndLiveDialog anchorEndLiveDialog = new AnchorEndLiveDialog();
        bundle.putBoolean(ConstantUtils.RESULT_FLAG, z);
        anchorEndLiveDialog.setArguments(bundle);
        anchorEndLiveDialog.setPromptMenuListener(bottomPromptMenuListener);
        return anchorEndLiveDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.isPayLive = bundle.getBoolean(ConstantUtils.RESULT_FLAG, false);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_bottom_prompt;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        view.findViewById(R$id.tv_close_tips).setVisibility(this.isPayLive ? 0 : 8);
        view.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.AnchorEndLiveDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                AnchorEndLiveDialog.this.dismiss();
                if (AnchorEndLiveDialog.this.bottomPromptMenuListener != null) {
                    AnchorEndLiveDialog.this.bottomPromptMenuListener.onCancel();
                }
            }
        });
        view.findViewById(R$id.tv_sure).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.AnchorEndLiveDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                AnchorEndLiveDialog.this.dismiss();
                if (AnchorEndLiveDialog.this.bottomPromptMenuListener != null) {
                    AnchorEndLiveDialog.this.bottomPromptMenuListener.onSure();
                }
            }
        });
    }

    public void setPromptMenuListener(BottomDialogUtils.BottomPromptMenuListener bottomPromptMenuListener) {
        this.bottomPromptMenuListener = bottomPromptMenuListener;
    }
}
