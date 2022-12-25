package com.gen.p059mh.webapp_extensions.utils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.gen.p059mh.webapp_extensions.WebApplication;

/* renamed from: com.gen.mh.webapp_extensions.utils.SoftKeyboardUtils */
/* loaded from: classes2.dex */
public final class SoftKeyboardUtils {
    public static void hideSoftInput(Activity activity) {
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        hideSoftInput(currentFocus);
    }

    public static void hideSoftInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) WebApplication.getInstance().getApplication().getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0, new ResultReceiver(new Handler()) { // from class: com.gen.mh.webapp_extensions.utils.SoftKeyboardUtils.2
            @Override // android.os.ResultReceiver
            protected void onReceiveResult(int i, Bundle bundle) {
                if (i == 0 || i == 2) {
                    SoftKeyboardUtils.toggleSoftInput();
                }
            }
        });
    }

    public static void toggleSoftInput() {
        ((InputMethodManager) WebApplication.getInstance().getApplication().getSystemService("input_method")).toggleSoftInput(2, 0);
    }
}
