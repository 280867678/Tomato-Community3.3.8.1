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

/* renamed from: com.tomatolive.library.ui.view.dialog.base.BaseDialogFragment */
/* loaded from: classes3.dex */
public abstract class BaseDialogFragment extends BaseRxDialogFragment {
    protected Dialog mDialog;
    protected Window mWindow;
    public View rootView;

    public boolean getCancelOutside() {
        return true;
    }

    public double getHeightScale() {
        return 0.0d;
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    public double getWidthScale() {
        return 0.7d;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initListener(View view) {
    }

    protected abstract void initView(View view);

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment, com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(1, R$style.fq_CenterDialog);
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
        int i;
        int i2;
        if (this.mDialog != null) {
            if (this.mWindow != null) {
                int i3 = -2;
                int widthScale = (getWidthScale() <= 0.0d || (i2 = this.mWidthPixels) <= 0) ? -2 : (int) (i2 * getWidthScale());
                if (getHeightScale() > 0.0d && (i = this.mHeightPixels) > 0) {
                    i3 = (int) (i * getHeightScale());
                }
                this.mWindow.setLayout(widthScale, i3);
                this.mWindow.setDimAmount(getDimAmount());
            }
            this.mDialog.setCancelable(getCancelOutside());
            this.mDialog.setCanceledOnTouchOutside(getCancelOutside());
        }
        super.onStart();
    }
}
