package com.tomatolive.library.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.p005v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

/* loaded from: classes4.dex */
public class SoftKeyboardUtils {
    public static void init(Activity activity) {
        new SoftKeyboardUtils(activity, null);
    }

    public static void init(Activity activity, ViewGroup viewGroup) {
        new SoftKeyboardUtils(activity, viewGroup);
    }

    public static void hideSoftKeyboard(Activity activity) {
        View currentFocus;
        if (activity == null || (currentFocus = activity.getCurrentFocus()) == null) {
            return;
        }
        ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 2);
    }

    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 2);
        }
    }

    public static void showSoftKeyboard(View view) {
        if (view != null) {
            ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 0);
        }
    }

    public static void hideDialogSoftKeyboard(Dialog dialog) {
        View currentFocus;
        if (dialog == null || (currentFocus = dialog.getCurrentFocus()) == null) {
            return;
        }
        ((InputMethodManager) dialog.getContext().getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 2);
    }

    private SoftKeyboardUtils(final Activity activity, ViewGroup viewGroup) {
        viewGroup = viewGroup == null ? (ViewGroup) activity.findViewById(16908290) : viewGroup;
        getScrollView(viewGroup, activity);
        viewGroup.setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.utils.SoftKeyboardUtils.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SoftKeyboardUtils.this.dispatchTouchEvent(activity, motionEvent);
                return false;
            }
        });
    }

    private void getScrollView(ViewGroup viewGroup, final Activity activity) {
        if (viewGroup == null) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ScrollView) {
                ((ScrollView) childAt).setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.utils.SoftKeyboardUtils.2
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        SoftKeyboardUtils.this.dispatchTouchEvent(activity, motionEvent);
                        return false;
                    }
                });
            } else if (childAt instanceof AbsListView) {
                ((AbsListView) childAt).setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.utils.SoftKeyboardUtils.3
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        SoftKeyboardUtils.this.dispatchTouchEvent(activity, motionEvent);
                        return false;
                    }
                });
            } else if (childAt instanceof RecyclerView) {
                ((RecyclerView) childAt).setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.utils.SoftKeyboardUtils.4
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        SoftKeyboardUtils.this.dispatchTouchEvent(activity, motionEvent);
                        return false;
                    }
                });
            } else if (childAt instanceof ViewGroup) {
                getScrollView((ViewGroup) childAt, activity);
            }
            if (childAt.isClickable() && (childAt instanceof TextView) && !(childAt instanceof EditText)) {
                childAt.setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.utils.SoftKeyboardUtils.5
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        SoftKeyboardUtils.this.dispatchTouchEvent(activity, motionEvent);
                        return false;
                    }
                });
            }
        }
    }

    public boolean dispatchTouchEvent(Activity activity, MotionEvent motionEvent) {
        View currentFocus;
        if (motionEvent.getAction() != 0 || (currentFocus = activity.getCurrentFocus()) == null || !isShouldHideInput(currentFocus, motionEvent)) {
            return false;
        }
        hideSoftInput(activity, currentFocus.getWindowToken());
        return false;
    }

    private boolean isShouldHideInput(View view, MotionEvent motionEvent) {
        if (view instanceof EditText) {
            Rect rect = new Rect();
            view.getHitRect(rect);
            return !rect.contains((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        return true;
    }

    private void hideSoftInput(Activity activity, IBinder iBinder) {
        if (iBinder != null) {
            ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(iBinder, 2);
        }
    }

    public static int getVirtualBarHeight(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics.class).invoke(defaultDisplay, displayMetrics);
            return displayMetrics.heightPixels - defaultDisplay.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
