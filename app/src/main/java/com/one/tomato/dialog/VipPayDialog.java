package com.one.tomato.dialog;

import android.content.Context;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.p080ui.vip.p083ui.VipActivity;
import com.one.tomato.utils.AppUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VipPayDialog.kt */
/* loaded from: classes3.dex */
public final class VipPayDialog extends CustomAlertDialog {
    public VipPayDialog(final Context context) {
        super(context);
        setTitle(R.string.video_save_tip_title);
        setMessage(AppUtil.getString(R.string.vip_papa_complete));
        setCancelButtonBackgroundRes(R.drawable.common_shape_solid_corner5_disable);
        setCancelButtonTextColor(R.color.white);
        setCancelButton(R.string.common_cancel, new View.OnClickListener() { // from class: com.one.tomato.dialog.VipPayDialog.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipPayDialog.this.dismiss();
            }
        });
        setConfirmButtonBackgroundRes(R.drawable.common_shape_solid_corner5_coloraccent);
        setConfirmButtonTextColor(R.color.white);
        setConfirmButton(R.string.video_save_tip_confirm_btn, new View.OnClickListener() { // from class: com.one.tomato.dialog.VipPayDialog.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipPayDialog.this.dialog.dismiss();
                VipActivity.Companion companion = VipActivity.Companion;
                Context context2 = context;
                if (context2 != null) {
                    companion.startActivity(context2);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
    }
}
