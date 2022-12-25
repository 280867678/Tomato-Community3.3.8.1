package com.zyyoona7.popup;

import android.view.View;

/* loaded from: classes4.dex */
public class EasyPopup extends BasePopup<EasyPopup> {
    private OnViewListener mOnViewListener;

    /* loaded from: classes4.dex */
    public interface OnViewListener {
        void initViews(View view, EasyPopup easyPopup);
    }

    @Override // com.zyyoona7.popup.BasePopup
    protected void initAttributes() {
    }

    public static EasyPopup create() {
        return new EasyPopup();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.zyyoona7.popup.BasePopup
    public void initViews(View view, EasyPopup easyPopup) {
        OnViewListener onViewListener = this.mOnViewListener;
        if (onViewListener != null) {
            onViewListener.initViews(view, easyPopup);
        }
    }
}
