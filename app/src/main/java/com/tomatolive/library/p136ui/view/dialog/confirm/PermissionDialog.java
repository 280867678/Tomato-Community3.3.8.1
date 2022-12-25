package com.tomatolive.library.p136ui.view.dialog.confirm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.p002v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.confirm.PermissionDialog */
/* loaded from: classes3.dex */
public class PermissionDialog extends BaseDialogFragment {
    public static final String CAMERA_TIP = "CAMERA_TIP";
    private static final String LOCATION_TIP = "LOCATION_TIP";
    public static final String MIC_TIP = "MIC_TIP";
    private static final String TIP_KEY = "TIP_KEY";

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public boolean getCancelOutside() {
        return false;
    }

    public static PermissionDialog newInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(TIP_KEY, str);
        PermissionDialog permissionDialog = new PermissionDialog();
        permissionDialog.setArguments(bundle);
        return permissionDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_auth_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        char c;
        String string;
        String string2;
        TextView textView = (TextView) view.findViewById(R$id.tv_title);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_content);
        TextView textView3 = (TextView) view.findViewById(R$id.tv_sure);
        String argumentsString = getArgumentsString(TIP_KEY);
        int hashCode = argumentsString.hashCode();
        if (hashCode == -2034687759) {
            if (argumentsString.equals(LOCATION_TIP)) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != -1525739423) {
            if (hashCode == 1773023843 && argumentsString.equals(MIC_TIP)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (argumentsString.equals(CAMERA_TIP)) {
                c = 0;
            }
            c = 65535;
        }
        if (c == 0) {
            string = getString(R$string.fq_camera_bannd);
            string2 = getString(R$string.fq_permission_camera_allow, this.mContext.getString(R$string.fq_live_sdk_app_name));
        } else if (c == 1) {
            string = getString(R$string.fq_mic_bannd);
            string2 = getString(R$string.fq_permission_mic_allow, this.mContext.getString(R$string.fq_live_sdk_app_name));
        } else if (c != 2) {
            return;
        } else {
            string = getString(R$string.fq_no_permission_location);
            string2 = getString(R$string.fq_permission_location_allow, this.mContext.getString(R$string.fq_live_sdk_app_name));
        }
        textView.setText(string);
        textView2.setText(string2);
        textView3.setText(R$string.fq_goto_setting);
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$PermissionDialog$grWN1dHXw2c6tsKmwOskycm8Kao
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PermissionDialog.this.lambda$initView$0$PermissionDialog(view2);
            }
        });
        view.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$PermissionDialog$yLI9sCkczk52bboX7wM9QmzuV5Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PermissionDialog.this.lambda$initView$1$PermissionDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$PermissionDialog(View view) {
        dismiss();
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:" + this.mDialogActivity.getPackageName()));
        intent.addFlags(268435456);
        intent.addFlags(1073741824);
        intent.addFlags(8388608);
        startActivity(intent);
        this.mDialogActivity.finish();
    }

    public /* synthetic */ void lambda$initView$1$PermissionDialog(View view) {
        dismiss();
        if (!TextUtils.equals(getArgumentsString(TIP_KEY), LOCATION_TIP)) {
            getActivity().finish();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, getArgumentsString(TIP_KEY));
    }
}
