package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.view.dialog.GiftBoxPresenterDialog */
/* loaded from: classes3.dex */
public class GiftBoxPresenterDialog extends BaseDialogFragment {
    private static final String AVATAR = "avatar";
    private static final String NAME = "name";

    public static GiftBoxPresenterDialog newInstance(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(AVATAR, str);
        bundle.putString("name", str2);
        GiftBoxPresenterDialog giftBoxPresenterDialog = new GiftBoxPresenterDialog();
        giftBoxPresenterDialog.setArguments(bundle);
        return giftBoxPresenterDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_giftbox_avatar;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        ((TextView) view.findViewById(R$id.tv_name)).setText(getArgumentsString("name"));
        view.findViewById(R$id.iv_finish).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftBoxPresenterDialog$6VzyEdjAqVyrj79wNBCxLzEe1dY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftBoxPresenterDialog.this.lambda$initView$0$GiftBoxPresenterDialog(view2);
            }
        });
        GlideUtils.loadAvatar(this.mContext, (ImageView) view.findViewById(R$id.iv_avatar), getArgumentsString(AVATAR), 6, ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
    }

    public /* synthetic */ void lambda$initView$0$GiftBoxPresenterDialog(View view) {
        dismiss();
    }
}
