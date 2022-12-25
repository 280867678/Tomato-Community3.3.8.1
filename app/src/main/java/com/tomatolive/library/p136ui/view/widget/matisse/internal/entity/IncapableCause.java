package com.tomatolive.library.p136ui.view.widget.matisse.internal.entity;

import android.content.Context;
import android.support.p002v4.app.FragmentActivity;
import android.widget.Toast;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget.IncapableDialog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.entity.IncapableCause */
/* loaded from: classes4.dex */
public class IncapableCause {
    public static final int DIALOG = 1;
    public static final int NONE = 2;
    public static final int TOAST = 0;
    private int mForm;
    private String mMessage;
    private String mTitle;

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.entity.IncapableCause$Form */
    /* loaded from: classes4.dex */
    public @interface Form {
    }

    public IncapableCause(String str) {
        this.mForm = 0;
        this.mMessage = str;
    }

    public IncapableCause(String str, String str2) {
        this.mForm = 0;
        this.mTitle = str;
        this.mMessage = str2;
    }

    public IncapableCause(int i, String str) {
        this.mForm = 0;
        this.mForm = i;
        this.mMessage = str;
    }

    public IncapableCause(int i, String str, String str2) {
        this.mForm = 0;
        this.mForm = i;
        this.mTitle = str;
        this.mMessage = str2;
    }

    public static void handleCause(Context context, IncapableCause incapableCause) {
        if (incapableCause == null) {
            return;
        }
        int i = incapableCause.mForm;
        if (i == 1) {
            IncapableDialog.newInstance(incapableCause.mTitle, incapableCause.mMessage).show(((FragmentActivity) context).getSupportFragmentManager(), IncapableDialog.class.getName());
        } else if (i == 2) {
        } else {
            Toast.makeText(context, incapableCause.mMessage, 0).show();
        }
    }
}
