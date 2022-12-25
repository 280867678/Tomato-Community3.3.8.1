package com.one.tomato.mvp.p080ui.vip.p083ui;

import android.view.View;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;

/* compiled from: VipRightActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.vip.ui.VipRightActivity$initVipExplain$1 */
/* loaded from: classes3.dex */
final class VipRightActivity$initVipExplain$1 implements View.OnClickListener {
    public static final VipRightActivity$initVipExplain$1 INSTANCE = new VipRightActivity$initVipExplain$1();

    VipRightActivity$initVipExplain$1() {
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        AppUtil.startBrowseView(DBUtil.getSystemParam().getPotatoUrl());
    }
}
