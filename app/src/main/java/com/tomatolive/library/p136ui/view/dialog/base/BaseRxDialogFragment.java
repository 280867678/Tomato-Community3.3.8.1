package com.tomatolive.library.p136ui.view.dialog.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.p002v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.SoftKeyboardUtils;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.base.BaseRxDialogFragment */
/* loaded from: classes3.dex */
public abstract class BaseRxDialogFragment extends RxDialogFragment {
    protected Context mContext;
    protected Activity mDialogActivity;
    private DialogDismissListener mDialogDismissListener;
    protected int mHeightPixels;
    protected int mWidthPixels;
    private final float DEFAULT_DIM = 0.5f;
    public int pageNum = 1;

    /* renamed from: com.tomatolive.library.ui.view.dialog.base.BaseRxDialogFragment$DialogDismissListener */
    /* loaded from: classes3.dex */
    public interface DialogDismissListener {
        void onDialogDismiss(BaseRxDialogFragment baseRxDialogFragment);
    }

    public void getBundle(Bundle bundle) {
    }

    public float getDimAmount() {
        return 0.5f;
    }

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < 23) {
            onAttachToContext(activity);
        }
    }

    public void onAttachToContext(Context context) {
        this.mContext = context;
        this.mDialogActivity = getActivity() == null ? (Activity) context : getActivity();
        this.mWidthPixels = ScreenUtils.getScreenWidth();
        this.mHeightPixels = ScreenUtils.getScreenHeight();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        getBundle(getArguments());
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initSoftInputListener();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onStart() {
        try {
            super.onStart();
        } catch (Exception unused) {
        }
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, getFragmentTag());
    }

    @Override // android.support.p002v4.app.DialogFragment
    public void show(FragmentManager fragmentManager, String str) {
        try {
            fragmentManager.beginTransaction().remove(this).commit();
            super.show(fragmentManager, str);
        } catch (IllegalStateException unused) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFragmentTag() {
        return getClass().getSimpleName();
    }

    @Override // android.support.p002v4.app.DialogFragment
    public void dismiss() {
        SoftKeyboardUtils.hideDialogSoftKeyboard(getDialog());
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.support.p002v4.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        DialogDismissListener dialogDismissListener = this.mDialogDismissListener;
        if (dialogDismissListener != null) {
            dialogDismissListener.onDialogDismiss(this);
        }
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onDestroyView() {
        if (getDialog() != null) {
            getDialog().setOnShowListener(null);
            getDialog().setOnCancelListener(null);
            getDialog().setOnDismissListener(null);
        }
        super.onDestroyView();
    }

    public void showToast(String str) {
        ToastUtils.showShort(str);
    }

    public void showToast(@StringRes int i) {
        showToast(getString(i));
    }

    public void setOnDismissListener(DialogDismissListener dialogDismissListener) {
        this.mDialogDismissListener = dialogDismissListener;
    }

    public void dismissDialogFragment(BaseRxDialogFragment baseRxDialogFragment) {
        if (baseRxDialogFragment == null || !baseRxDialogFragment.isAdded()) {
            return;
        }
        baseRxDialogFragment.dismiss();
    }

    public boolean isLoginUser() {
        if (!AppUtils.isLogin(this.mContext)) {
            dismiss();
            return false;
        }
        return true;
    }

    public boolean isRestrictionUser() {
        if (!AppUtils.isConsumptionPermissionUser(this.mContext)) {
            dismiss();
            return false;
        }
        return true;
    }

    public String getArgumentsString(String str) {
        return getArgumentsString(str, "");
    }

    public String getArgumentsString(String str, String str2) {
        return getArguments() == null ? str2 : getArguments().getString(str, str2);
    }

    public int getArgumentsInt(String str) {
        return getArgumentsInt(str, 0);
    }

    public int getArgumentsInt(String str, int i) {
        return getArguments() == null ? i : getArguments().getInt(str, i);
    }

    public double getArgumentsDouble(String str) {
        return getArgumentsDouble(str, 0.0d);
    }

    public double getArgumentsDouble(String str, double d) {
        return getArguments() == null ? d : getArguments().getDouble(str, d);
    }

    public float getArgumentsFloat(String str) {
        return getArgumentsFloat(str, 0.0f);
    }

    public float getArgumentsFloat(String str, float f) {
        return getArguments() == null ? f : getArguments().getFloat(str, f);
    }

    public boolean getArgumentsBoolean(String str) {
        return getArgumentsBoolean(str, false);
    }

    public boolean getArgumentsBoolean(String str, boolean z) {
        return getArguments() == null ? z : getArguments().getBoolean(str, z);
    }

    private void initSoftInputListener() {
        Window window;
        if (getDialog() == null || (window = getDialog().getWindow()) == null) {
            return;
        }
        window.getDecorView().setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.ui.view.dialog.base.-$$Lambda$BaseRxDialogFragment$z8SdAN0fot0l2WTJjlPeIu1749Y
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return BaseRxDialogFragment.this.lambda$initSoftInputListener$0$BaseRxDialogFragment(view, motionEvent);
            }
        });
    }

    public /* synthetic */ boolean lambda$initSoftInputListener$0$BaseRxDialogFragment(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0 || getDialog().getCurrentFocus() == null || getDialog().getCurrentFocus().getWindowToken() == null) {
            return false;
        }
        SoftKeyboardUtils.hideDialogSoftKeyboard(getDialog());
        return false;
    }
}
