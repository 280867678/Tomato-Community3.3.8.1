package com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p002v4.app.DialogFragment;
import android.support.p005v7.app.AlertDialog;
import android.text.TextUtils;
import com.tomatolive.library.R$string;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.widget.IncapableDialog */
/* loaded from: classes4.dex */
public class IncapableDialog extends DialogFragment {
    public static final String EXTRA_MESSAGE = "extra_message";
    public static final String EXTRA_TITLE = "extra_title";

    public static IncapableDialog newInstance(String str, String str2) {
        IncapableDialog incapableDialog = new IncapableDialog();
        Bundle bundle = new Bundle();
        bundle.putString("extra_title", str);
        bundle.putString("extra_message", str2);
        incapableDialog.setArguments(bundle);
        return incapableDialog;
    }

    @Override // android.support.p002v4.app.DialogFragment
    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        String string = getArguments().getString("extra_title");
        String string2 = getArguments().getString("extra_message");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (!TextUtils.isEmpty(string)) {
            builder.setTitle(string);
        }
        if (!TextUtils.isEmpty(string2)) {
            builder.setMessage(string2);
        }
        builder.setPositiveButton(R$string.fq_matisse_button_ok, new DialogInterface.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.matisse.internal.ui.widget.IncapableDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }
}
