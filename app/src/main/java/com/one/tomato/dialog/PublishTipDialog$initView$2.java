package com.one.tomato.dialog;

import android.widget.CompoundButton;
import com.one.tomato.utils.PreferencesUtil;

/* compiled from: PublishTipDialog.kt */
/* loaded from: classes3.dex */
final class PublishTipDialog$initView$2 implements CompoundButton.OnCheckedChangeListener {
    public static final PublishTipDialog$initView$2 INSTANCE = new PublishTipDialog$initView$2();

    PublishTipDialog$initView$2() {
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (z) {
            PreferencesUtil.getInstance().putBoolean("publish_tip_check", true);
        } else {
            PreferencesUtil.getInstance().putBoolean("publish_tip_check", false);
        }
    }
}
