package com.one.tomato.mvp.p080ui.start.view;

import android.view.View;
import com.one.tomato.mvp.base.AppManager;

/* compiled from: WarnActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.start.view.WarnActivity$initData$2 */
/* loaded from: classes3.dex */
final class WarnActivity$initData$2 implements View.OnClickListener {
    public static final WarnActivity$initData$2 INSTANCE = new WarnActivity$initData$2();

    WarnActivity$initData$2() {
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        AppManager.getAppManager().exitApp();
    }
}
