package razerdp.util;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

/* loaded from: classes4.dex */
public class InputMethodUtils {
    public static void showInputMethod(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(view, 1);
        }
    }

    public static void showInputMethod(final View view, long j) {
        if (view == null) {
            return;
        }
        view.postDelayed(new Runnable() { // from class: razerdp.util.InputMethodUtils.1
            @Override // java.lang.Runnable
            public void run() {
                InputMethodUtils.showInputMethod(view);
            }
        }, j);
    }

    public static void close(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
            if (inputMethodManager == null) {
                return;
            }
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
