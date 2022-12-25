package com.p076mh.webappStart.util.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

/* renamed from: com.mh.webappStart.util.dialog.SystemDialogFactory */
/* loaded from: classes3.dex */
public class SystemDialogFactory {
    private static AlertDialog.Builder alertDialogBuilder;
    private static ProgressDialog progDialog;

    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = alertDialogBuilder;
        if (builder == null) {
            alertDialogBuilder = new AlertDialog.Builder(context);
        } else if (builder.getContext() != context) {
            alertDialogBuilder = new AlertDialog.Builder(context);
        }
        return alertDialogBuilder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String str, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2) {
        AlertDialog.Builder dialog = getDialog(context);
        dialog.setMessage(str);
        dialog.setPositiveButton("确定", onClickListener);
        dialog.setNegativeButton("取消", onClickListener2);
        return dialog;
    }

    public static AlertDialog.Builder getItemDialog(Context context, String str, String[] strArr, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder dialog = getDialog(context);
        dialog.setItems(strArr, onClickListener);
        if (!TextUtils.isEmpty(str)) {
            dialog.setTitle(str);
        }
        return dialog;
    }

    public static AlertDialog.Builder getItemDialog(Context context, String[] strArr, DialogInterface.OnClickListener onClickListener) {
        return getItemDialog(context, "", strArr, onClickListener);
    }

    public static ProgressDialog getProgressDialog(Context context, String... strArr) {
        ProgressDialog progressDialog = progDialog;
        if (progressDialog == null) {
            progDialog = new ProgressDialog(context);
        } else if (progressDialog.getContext() != context) {
            progDialog = new ProgressDialog(context);
        }
        progDialog.setProgressStyle(0);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        if (strArr != null && strArr.length > 0) {
            progDialog.setMessage(strArr[0]);
        } else {
            progDialog.setMessage("正在加载...");
        }
        return progDialog;
    }
}
