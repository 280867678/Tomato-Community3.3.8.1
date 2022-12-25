package com.tomatolive.library.p136ui.view.dialog.alert;

import android.os.Bundle;
import android.support.p002v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.alert.WarnDialog */
/* loaded from: classes3.dex */
public class WarnDialog extends BaseDialogFragment {
    public static final String BAN_TIP = "BAN_TIP";
    private static final String CONTENT_KEY = "CONTENT_KEY";
    public static final String FROZEN_TIP = "FROZEN_TIP";
    public static final String OPERATION_AUTHORITY = "OPERATION_AUTHORITY";
    public static final String REPORT_TIP = "REPORT_TIP";
    public static final String STOP_WARN_TIP = "STOP_WARN_TIP";
    private static final String TIP_KEY = "TIP_KEY";
    public static final String TRANSLATION_TIP = "TRANSLATION_TIP";
    public static final String WARN_TIP = "WARN_TIP";
    private View.OnClickListener listener;

    public static WarnDialog newInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(TIP_KEY, str);
        WarnDialog warnDialog = new WarnDialog();
        warnDialog.setArguments(bundle);
        return warnDialog;
    }

    public static WarnDialog newInstance(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(TIP_KEY, str);
        bundle.putString(CONTENT_KEY, str2);
        WarnDialog warnDialog = new WarnDialog();
        warnDialog.setArguments(bundle);
        return warnDialog;
    }

    public static WarnDialog newInstance(String str, View.OnClickListener onClickListener) {
        Bundle bundle = new Bundle();
        bundle.putString(TIP_KEY, str);
        WarnDialog warnDialog = new WarnDialog();
        warnDialog.setListener(onClickListener);
        warnDialog.setArguments(bundle);
        return warnDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_chat_tip_dialog;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        char c;
        String string;
        String string2;
        String string3;
        TextView textView = (TextView) view.findViewById(R$id.tv_title);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_tips);
        TextView textView3 = (TextView) view.findViewById(R$id.tv_sure);
        String argumentsString = getArgumentsString(TIP_KEY);
        switch (argumentsString.hashCode()) {
            case -1877212432:
                if (argumentsString.equals(REPORT_TIP)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -460174899:
                if (argumentsString.equals(TRANSLATION_TIP)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 381543467:
                if (argumentsString.equals(BAN_TIP)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 928205355:
                if (argumentsString.equals(OPERATION_AUTHORITY)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1281379138:
                if (argumentsString.equals(WARN_TIP)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1805920860:
                if (argumentsString.equals(FROZEN_TIP)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1980849887:
                if (argumentsString.equals(STOP_WARN_TIP)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                string = getString(R$string.fq_tip);
                string2 = getString(R$string.fq_anchor_ban_content);
                string3 = getString(R$string.fq_know);
                break;
            case 1:
                string = getString(R$string.fq_warn_tip);
                string2 = getString(R$string.fq_warn_content);
                string3 = getString(R$string.fq_know);
                break;
            case 2:
                string = getString(R$string.fq_back_out_warn);
                string2 = getString(R$string.fq_back_out_warn_content);
                string3 = getString(R$string.fq_know);
                break;
            case 3:
                string = getString(R$string.fq_frozen_tip);
                string2 = getString(R$string.fq_frozen_content);
                string3 = getString(R$string.fq_btn_sure);
                break;
            case 4:
                string = getString(R$string.fq_tip);
                string2 = getArgumentsString(CONTENT_KEY);
                string3 = getString(R$string.fq_know);
                break;
            case 5:
                string = getString(R$string.fq_report_live_success_title);
                string2 = getString(R$string.fq_report_live_success_tips);
                string3 = getString(R$string.fq_btn_sure);
                break;
            case 6:
                string = getString(R$string.fq_no_operation_authority);
                string2 = getString(R$string.fq_no_operation_authority_tips);
                string3 = getString(R$string.fq_know);
                break;
            default:
                return;
        }
        textView.setText(string);
        textView2.setText(string2);
        textView3.setText(string3);
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.alert.-$$Lambda$WarnDialog$VgJ1q7KFBjK7lkVk5iy0xLiTZLU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                WarnDialog.this.lambda$initView$0$WarnDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$WarnDialog(View view) {
        dismiss();
        View.OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public boolean getCancelOutside() {
        return !TextUtils.equals(getArgumentsString(TIP_KEY), REPORT_TIP);
    }

    public void setListener(View.OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, getArgumentsString(TIP_KEY));
    }
}
