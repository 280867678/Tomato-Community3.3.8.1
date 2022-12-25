package com.alipay.sdk.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import com.alipay.sdk.util.C0996c;

/* renamed from: com.alipay.sdk.widget.e */
/* loaded from: classes2.dex */
public class C1016e {
    /* renamed from: a */
    public static Dialog m4349a(Context context, String str, String str2, String str3, DialogInterface.OnClickListener onClickListener, String str4, DialogInterface.OnClickListener onClickListener2) {
        AlertDialog.Builder m4350a = m4350a(context, str, str3, onClickListener, str4, onClickListener2);
        m4350a.setTitle(str);
        m4350a.setMessage(str2);
        AlertDialog create = m4350a.create();
        create.setCanceledOnTouchOutside(false);
        create.setOnKeyListener(new DialogInterface$OnKeyListenerC1017f());
        try {
            create.show();
        } catch (Throwable th) {
            C0996c.m4437a("mspl", "showDialog ", th);
        }
        return create;
    }

    /* renamed from: a */
    private static AlertDialog.Builder m4350a(Context context, String str, String str2, DialogInterface.OnClickListener onClickListener, String str3, DialogInterface.OnClickListener onClickListener2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(str3) && onClickListener2 != null) {
            builder.setPositiveButton(str3, onClickListener2);
        }
        if (!TextUtils.isEmpty(str2) && onClickListener != null) {
            builder.setNegativeButton(str2, onClickListener);
        }
        return builder;
    }
}
