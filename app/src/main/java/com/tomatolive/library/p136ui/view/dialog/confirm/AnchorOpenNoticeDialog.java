package com.tomatolive.library.p136ui.view.dialog.confirm;

import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.interfaces.CommonConfirmClickListener;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.confirm.AnchorOpenNoticeDialog */
/* loaded from: classes3.dex */
public class AnchorOpenNoticeDialog extends BaseDialogFragment {
    private static final String COUNT_KEY = "COUNT_KEY";
    private CommonConfirmClickListener openListener;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public boolean getCancelOutside() {
        return false;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.75d;
    }

    public static AnchorOpenNoticeDialog newInstance() {
        Bundle bundle = new Bundle();
        AnchorOpenNoticeDialog anchorOpenNoticeDialog = new AnchorOpenNoticeDialog();
        anchorOpenNoticeDialog.setArguments(bundle);
        return anchorOpenNoticeDialog;
    }

    public static AnchorOpenNoticeDialog newInstance(String str, CommonConfirmClickListener commonConfirmClickListener) {
        Bundle bundle = new Bundle();
        bundle.putString(COUNT_KEY, str);
        AnchorOpenNoticeDialog anchorOpenNoticeDialog = new AnchorOpenNoticeDialog();
        anchorOpenNoticeDialog.setArguments(bundle);
        anchorOpenNoticeDialog.setOpenListener(commonConfirmClickListener);
        return anchorOpenNoticeDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_auth_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_content);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_sure);
        TextView textView3 = (TextView) view.findViewById(R$id.tv_cancel);
        ((TextView) view.findViewById(R$id.tv_title)).setText(R$string.fq_achieve_open_notice);
        textView.setText(R$string.fq_achieve_open_notice_content);
        textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
        textView2.setText(R$string.fq_achieve_no_use);
        textView3.setText(this.mContext.getString(R$string.fq_achieve_use, getArgumentsString(COUNT_KEY)));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$AnchorOpenNoticeDialog$i36qTWR4aMG3gTGIkBlcSkAt7tY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AnchorOpenNoticeDialog.this.lambda$initView$0$AnchorOpenNoticeDialog(view2);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$AnchorOpenNoticeDialog$0n60VCAVa0BFfmOGh1m4Rgxydzg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AnchorOpenNoticeDialog.this.lambda$initView$1$AnchorOpenNoticeDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$AnchorOpenNoticeDialog(View view) {
        dismiss();
        CommonConfirmClickListener commonConfirmClickListener = this.openListener;
        if (commonConfirmClickListener != null) {
            commonConfirmClickListener.onCancel(view);
        }
    }

    public /* synthetic */ void lambda$initView$1$AnchorOpenNoticeDialog(View view) {
        dismiss();
        CommonConfirmClickListener commonConfirmClickListener = this.openListener;
        if (commonConfirmClickListener != null) {
            commonConfirmClickListener.onSure(view);
        }
    }

    private void setOpenListener(CommonConfirmClickListener commonConfirmClickListener) {
        this.openListener = commonConfirmClickListener;
    }
}
