package com.tomatolive.library.p136ui.view.dialog;

import android.content.Context;
import android.view.View;
import com.tomatolive.library.R$layout;
import razerdp.basepopup.BasePopupWindow;

/* renamed from: com.tomatolive.library.ui.view.dialog.MorePopDialog */
/* loaded from: classes3.dex */
public class MorePopDialog extends BasePopupWindow {
    public MorePopDialog(Context context) {
        super(context);
    }

    @Override // razerdp.basepopup.BasePopup
    public View onCreateContentView() {
        return createPopupById(R$layout.fq_dialog_pop_lottery_more);
    }
}
