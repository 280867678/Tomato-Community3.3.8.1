package com.tomatolive.library.p136ui.view.dialog.confirm;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.confirm.CoverSettingDialog */
/* loaded from: classes3.dex */
public class CoverSettingDialog extends BaseDialogFragment {
    private static final String ISLOCATION = "islocation";
    private static final String TIPS = "tips";
    private static final String TITLE = "title";
    private View.OnClickListener cancelListener;
    private View.OnClickListener sureListener;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public boolean getCancelOutside() {
        return false;
    }

    public static CoverSettingDialog newInstance(String str, String str2, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        Bundle bundle = new Bundle();
        CoverSettingDialog coverSettingDialog = new CoverSettingDialog();
        coverSettingDialog.setArguments(bundle);
        bundle.putString("title", str);
        bundle.putString(TIPS, str2);
        coverSettingDialog.setSureListener(onClickListener);
        coverSettingDialog.setCancelListener(onClickListener2);
        return coverSettingDialog;
    }

    public static CoverSettingDialog newInstance(boolean z, String str, String str2, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        Bundle bundle = new Bundle();
        CoverSettingDialog coverSettingDialog = new CoverSettingDialog();
        coverSettingDialog.setArguments(bundle);
        bundle.putString("title", str);
        bundle.putString(TIPS, str2);
        bundle.putBoolean(ISLOCATION, z);
        coverSettingDialog.setSureListener(onClickListener);
        coverSettingDialog.setCancelListener(onClickListener2);
        return coverSettingDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_auth_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_title);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_content);
        TextView textView3 = (TextView) view.findViewById(R$id.tv_cancel);
        TextView textView4 = (TextView) view.findViewById(R$id.tv_sure);
        textView3.setText(R$string.fq_direct_live);
        textView4.setText(R$string.fq_go_setting);
        if (getArgumentsBoolean(ISLOCATION)) {
            textView3.setText(R$string.fq_btn_sure);
            textView4.setText(R$string.fq_btn_cancel);
        }
        textView.setText(getArgumentsString("title"));
        textView2.setText(getArgumentsString(TIPS));
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$CoverSettingDialog$x93prknikt9gREH4EowowMKsajE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                CoverSettingDialog.this.lambda$initView$0$CoverSettingDialog(view2);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$CoverSettingDialog$ROoStsmTdui_UeKe0Vq7DCGwlPo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                CoverSettingDialog.this.lambda$initView$1$CoverSettingDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$CoverSettingDialog(View view) {
        if (this.cancelListener != null) {
            dismiss();
            this.cancelListener.onClick(view);
        }
    }

    public /* synthetic */ void lambda$initView$1$CoverSettingDialog(View view) {
        if (this.sureListener != null) {
            dismiss();
            this.sureListener.onClick(view);
        }
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.sureListener != null) {
            this.sureListener = null;
        }
        if (this.cancelListener != null) {
            this.cancelListener = null;
        }
    }

    public View.OnClickListener getAuthListener() {
        return this.sureListener;
    }

    public void setSureListener(View.OnClickListener onClickListener) {
        this.sureListener = onClickListener;
    }

    public View.OnClickListener getCancelListener() {
        return this.cancelListener;
    }

    public void setCancelListener(View.OnClickListener onClickListener) {
        this.cancelListener = onClickListener;
    }
}
