package com.tomatolive.library.p136ui.view.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tomatolive.library.R$style;
import com.tomatolive.library.utils.SoftKeyboardUtils;

/* renamed from: com.tomatolive.library.ui.view.dialog.base.BaseGeneralDialog */
/* loaded from: classes3.dex */
public abstract class BaseGeneralDialog extends Dialog {
    protected Context mContext;
    protected int mHeightPixels;
    protected int mWidthPixels;
    protected Window mWindow;
    public int pageNum;

    protected boolean getCancelOutside() {
        return true;
    }

    public float getDimAmount() {
        return 0.7f;
    }

    public int getGravityType() {
        return 80;
    }

    protected double getHeightScale() {
        return 0.0d;
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected double getWidthScale() {
        return 0.0d;
    }

    protected abstract void initView();

    protected boolean isDynamicSetWindowHeight() {
        return false;
    }

    public BaseGeneralDialog(@NonNull Context context) {
        this(context, R$style.fq_GeneralBottomDialogStyle);
    }

    public BaseGeneralDialog(@NonNull Context context, int i) {
        super(context, i);
        this.pageNum = 1;
        this.mContext = context;
        this.mWidthPixels = ScreenUtils.getScreenWidth();
        this.mHeightPixels = ScreenUtils.getScreenHeight();
        setContentView(getLayoutRes());
        initWindowInfo();
        initView();
        initListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initListener() {
        initSoftInputListener();
    }

    @Override // android.app.Dialog
    public void show() {
        Window window = this.mWindow;
        if (window != null) {
            window.setFlags(8, 8);
            super.show();
            this.mWindow.clearFlags(8);
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        SoftKeyboardUtils.hideDialogSoftKeyboard(this);
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(String str) {
        ToastUtils.showShort(str);
    }

    public void showToast(@StringRes int i) {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        showToast(context.getString(i));
    }

    public void setWindowHeightByProportion(double d) {
        if (this.mWindow != null) {
            int i = -2;
            if (d > 0.0d) {
                i = (int) (this.mWidthPixels / d);
            }
            this.mWindow.setLayout(-1, i);
        }
    }

    protected int getDialogHeight() {
        int i;
        if (getHeightScale() <= 0.0d || (i = this.mHeightPixels) <= 0) {
            return -2;
        }
        return (int) (i * getHeightScale());
    }

    protected void setDialogCancelable(boolean z) {
        setCancelable(z);
        setCanceledOnTouchOutside(z);
    }

    private void initWindowInfo() {
        int i;
        this.mWindow = getWindow();
        if (this.mWindow != null) {
            int i2 = -1;
            if (getWidthScale() > 0.0d && (i = this.mWidthPixels) > 0) {
                i2 = (int) (i * getWidthScale());
            }
            if (!isDynamicSetWindowHeight()) {
                this.mWindow.setLayout(i2, getDialogHeight());
            }
            this.mWindow.setDimAmount(getDimAmount());
            this.mWindow.setGravity(getGravityType());
            this.mWindow.setSoftInputMode(16);
            this.mWindow.addFlags(2);
        }
        setDialogCancelable(getCancelOutside());
    }

    private void initSoftInputListener() {
        Window window = this.mWindow;
        if (window != null) {
            window.getDecorView().setOnTouchListener(new View.OnTouchListener() { // from class: com.tomatolive.library.ui.view.dialog.base.-$$Lambda$BaseGeneralDialog$T1d58lM3r2UYe5eAN1XpD6rbGRE
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return BaseGeneralDialog.this.lambda$initSoftInputListener$0$BaseGeneralDialog(view, motionEvent);
                }
            });
        }
    }

    public /* synthetic */ boolean lambda$initSoftInputListener$0$BaseGeneralDialog(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0 || getCurrentFocus() == null || getCurrentFocus().getWindowToken() == null) {
            return false;
        }
        SoftKeyboardUtils.hideDialogSoftKeyboard(this);
        return false;
    }
}
