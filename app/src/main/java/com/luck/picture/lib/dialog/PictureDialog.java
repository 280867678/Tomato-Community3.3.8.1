package com.luck.picture.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import com.luck.picture.lib.R$layout;
import com.luck.picture.lib.R$style;

/* loaded from: classes3.dex */
public class PictureDialog extends Dialog {
    public PictureDialog(Context context) {
        super(context, R$style.picture_alert_dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        getWindow().setWindowAnimations(R$style.DialogWindowStyle);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.picture_alert_dialog);
    }
}
