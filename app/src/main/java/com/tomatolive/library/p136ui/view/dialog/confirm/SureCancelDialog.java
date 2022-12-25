package com.tomatolive.library.p136ui.view.dialog.confirm;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.confirm.SureCancelDialog */
/* loaded from: classes3.dex */
public class SureCancelDialog extends BaseDialogFragment {
    private static final String BTN_CANCEL = "btnCancel";
    private static final String BTN_SURE = "btnSure";
    private static final String CONTENT_COLOR = "content_color";
    private static final String TIPS = "tips";
    private static final String TITLE = "title";
    private View.OnClickListener onCancelListener;
    private View.OnClickListener onSureListener;

    public static SureCancelDialog newInstance(String str, View.OnClickListener onClickListener) {
        Bundle bundle = new Bundle();
        SureCancelDialog sureCancelDialog = new SureCancelDialog();
        sureCancelDialog.setArguments(bundle);
        bundle.putString(TIPS, str);
        sureCancelDialog.setOnSureListener(onClickListener);
        return sureCancelDialog;
    }

    public static SureCancelDialog newInstance(String str, String str2, @ColorRes int i, View.OnClickListener onClickListener) {
        return newInstance(str, str2, null, null, i, onClickListener);
    }

    public static SureCancelDialog newInstance(String str, String str2, String str3, String str4, View.OnClickListener onClickListener) {
        return newInstance(str, str2, str3, str4, R$color.fq_text_gray, onClickListener);
    }

    public static SureCancelDialog newInstance(String str, String str2, String str3, String str4, @ColorRes int i, View.OnClickListener onClickListener) {
        return newInstance(str, str2, str3, str4, i, null, onClickListener);
    }

    public static SureCancelDialog newInstance(String str, String str2, String str3, String str4, @ColorRes int i, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        Bundle bundle = new Bundle();
        SureCancelDialog sureCancelDialog = new SureCancelDialog();
        sureCancelDialog.setArguments(bundle);
        bundle.putString("title", str);
        bundle.putString(TIPS, str2);
        bundle.putString(BTN_CANCEL, str3);
        bundle.putString(BTN_SURE, str4);
        bundle.putInt(CONTENT_COLOR, i);
        sureCancelDialog.setOnCancelListener(onClickListener);
        sureCancelDialog.setOnSureListener(onClickListener2);
        return sureCancelDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_auth_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_title);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_content);
        TextView textView3 = (TextView) view.findViewById(R$id.tv_sure);
        TextView textView4 = (TextView) view.findViewById(R$id.tv_cancel);
        String argumentsString = getArgumentsString("title");
        String argumentsString2 = getArgumentsString(TIPS);
        String argumentsString3 = getArgumentsString(BTN_CANCEL);
        String argumentsString4 = getArgumentsString(BTN_SURE);
        if (getArgumentsInt(CONTENT_COLOR, -1) != -1) {
            textView2.setTextColor(ContextCompat.getColor(this.mContext, getArgumentsInt(CONTENT_COLOR)));
        }
        if (!TextUtils.isEmpty(argumentsString)) {
            textView.setText(argumentsString);
        }
        textView2.setText(argumentsString2);
        if (!TextUtils.isEmpty(argumentsString4)) {
            textView3.setText(argumentsString4);
        } else {
            textView3.setText(getString(R$string.fq_btn_sure));
        }
        if (!TextUtils.isEmpty(argumentsString3)) {
            textView4.setText(argumentsString3);
        }
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$SureCancelDialog$OFspkq1AeV8KWzckqGhOiMuecMI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SureCancelDialog.this.lambda$initView$0$SureCancelDialog(view2);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$SureCancelDialog$z-K4Yco8a0m9qTXp3YuBXAR_69M
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SureCancelDialog.this.lambda$initView$1$SureCancelDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$SureCancelDialog(View view) {
        dismiss();
        View.OnClickListener onClickListener = this.onCancelListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    public /* synthetic */ void lambda$initView$1$SureCancelDialog(View view) {
        if (this.onSureListener != null) {
            dismiss();
            this.onSureListener.onClick(view);
        }
    }

    public void setOnSureListener(View.OnClickListener onClickListener) {
        this.onSureListener = onClickListener;
    }

    public View.OnClickListener getOnCancelListener() {
        return this.onCancelListener;
    }

    public void setOnCancelListener(View.OnClickListener onClickListener) {
        this.onCancelListener = onClickListener;
    }
}
