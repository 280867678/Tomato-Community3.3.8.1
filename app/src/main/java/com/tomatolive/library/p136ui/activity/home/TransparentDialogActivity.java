package com.tomatolive.library.p136ui.activity.home;

import android.os.Build;
import android.os.Bundle;
import android.support.p005v7.app.AppCompatActivity;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.dialog.alert.WarnDialog;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.confirm.AnchorAuthDialog;
import com.tomatolive.library.utils.ConstantUtils;

/* renamed from: com.tomatolive.library.ui.activity.home.TransparentDialogActivity */
/* loaded from: classes3.dex */
public class TransparentDialogActivity extends AppCompatActivity {
    public static final String DIALOG_TYPE_AUTH = "AnchorAuth";
    public static final String DIALOG_TYPE_WARN = "Warn";
    private BaseDialogFragment baseDialogFragment;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(67108864);
        setContentView(R$layout.fq_activity_transparent_dialog);
        this.baseDialogFragment = getBaseDialogFragment(getIntent().getStringExtra(ConstantUtils.RESULT_FLAG));
        BaseDialogFragment baseDialogFragment = this.baseDialogFragment;
        if (baseDialogFragment != null) {
            baseDialogFragment.show(getSupportFragmentManager());
            this.baseDialogFragment.setOnDismissListener(new BaseRxDialogFragment.DialogDismissListener() { // from class: com.tomatolive.library.ui.activity.home.TransparentDialogActivity.1
                @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment.DialogDismissListener
                public void onDialogDismiss(BaseRxDialogFragment baseRxDialogFragment) {
                    TransparentDialogActivity.this.finish();
                }
            });
        }
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        if (Build.VERSION.SDK_INT >= 16) {
            overridePendingTransition(0, 0);
        }
    }

    private BaseDialogFragment getBaseDialogFragment(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != 2688678) {
            if (hashCode == 695832093 && str.equals(DIALOG_TYPE_AUTH)) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals(DIALOG_TYPE_WARN)) {
                c = 1;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c == 1) {
                return WarnDialog.newInstance(WarnDialog.FROZEN_TIP);
            }
            return null;
        }
        return AnchorAuthDialog.newInstance();
    }
}
