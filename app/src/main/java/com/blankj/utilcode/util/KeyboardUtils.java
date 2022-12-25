package com.blankj.utilcode.util;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.inputmethod.InputMethodManager;

/* loaded from: classes2.dex */
public final class KeyboardUtils {

    /* renamed from: com.blankj.utilcode.util.KeyboardUtils$1 */
    /* loaded from: classes2.dex */
    final class ResultReceiverC11191 extends ResultReceiver {
        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, Bundle bundle) {
            if (i == 1 || i == 3) {
                KeyboardUtils.toggleSoftInput();
            }
        }
    }

    /* renamed from: com.blankj.utilcode.util.KeyboardUtils$2 */
    /* loaded from: classes2.dex */
    final class ResultReceiverC11202 extends ResultReceiver {
        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, Bundle bundle) {
            if (i == 0 || i == 2) {
                KeyboardUtils.toggleSoftInput();
            }
        }
    }

    public static void toggleSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) Utils.getApp().getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        inputMethodManager.toggleSoftInput(2, 0);
    }
}
