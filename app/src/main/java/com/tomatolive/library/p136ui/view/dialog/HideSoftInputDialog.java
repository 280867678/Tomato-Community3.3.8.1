package com.tomatolive.library.p136ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/* renamed from: com.tomatolive.library.ui.view.dialog.HideSoftInputDialog */
/* loaded from: classes3.dex */
public class HideSoftInputDialog extends Dialog {
    public HideSoftInputDialog(@NonNull Context context) {
        super(context);
    }

    public HideSoftInputDialog(@NonNull Context context, int i) {
        super(context, i);
    }

    protected HideSoftInputDialog(@NonNull Context context, boolean z, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        View currentFocus = getCurrentFocus();
        if (currentFocus instanceof EditText) {
            ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
        super.dismiss();
    }
}
