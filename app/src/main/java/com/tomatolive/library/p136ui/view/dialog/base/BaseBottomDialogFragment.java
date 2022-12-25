package com.tomatolive.library.p136ui.view.dialog.base;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.tomatolive.library.R$style;

/* renamed from: com.tomatolive.library.ui.view.dialog.base.BaseBottomDialogFragment */
/* loaded from: classes3.dex */
public abstract class BaseBottomDialogFragment extends BaseRxDialogFragment {
    protected Dialog mDialog;
    protected Window mWindow;
    protected double maxHeightScale = 0.8d;
    public View rootView;

    protected boolean getCancelOutside() {
        return true;
    }

    protected double getHeightScale() {
        return 0.0d;
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    /* JADX INFO: Access modifiers changed from: protected */
    public void initListener(View view) {
    }

    protected abstract void initView(View view);

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment, com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(1, R$style.fq_BottomDialog);
    }

    @Override // android.support.p002v4.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mDialog = getDialog();
        this.mWindow = this.mDialog.getWindow();
        Window window = this.mWindow;
        if (window != null) {
            window.requestFeature(1);
            this.mWindow.setBackgroundDrawable(new ColorDrawable(0));
        }
        View view = this.rootView;
        if (view != null) {
            ViewGroup viewGroup2 = (ViewGroup) view.getParent();
            if (viewGroup2 != null) {
                viewGroup2.removeView(this.rootView);
            }
        } else {
            this.rootView = layoutInflater.inflate(getLayoutRes(), viewGroup, false);
            initView(this.rootView);
            initListener(this.rootView);
        }
        return this.rootView;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment, com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onStart() {
        if (this.mDialog != null) {
            Window window = this.mWindow;
            if (window != null) {
                window.setLayout(-1, getDialogHeight());
                this.mWindow.setDimAmount(getDimAmount());
                this.mWindow.setGravity(80);
            }
            setDialogCancelable(getCancelOutside());
        }
        super.onStart();
    }

    protected int getDialogHeight() {
        int i;
        if (getHeightScale() <= 0.0d || (i = this.mHeightPixels) <= 0) {
            return -2;
        }
        return (int) (i * getHeightScale());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setDialogCancelable(boolean z) {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.setCancelable(z);
            this.mDialog.setCanceledOnTouchOutside(z);
        }
    }
}
