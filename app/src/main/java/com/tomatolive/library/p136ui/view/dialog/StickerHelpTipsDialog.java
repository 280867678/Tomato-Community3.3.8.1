package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.utils.GlideUtils;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* renamed from: com.tomatolive.library.ui.view.dialog.StickerHelpTipsDialog */
/* loaded from: classes3.dex */
public class StickerHelpTipsDialog extends BaseDialogFragment {
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getHeightScale() {
        return 0.8d;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.8d;
    }

    public static StickerHelpTipsDialog newInstance() {
        Bundle bundle = new Bundle();
        StickerHelpTipsDialog stickerHelpTipsDialog = new StickerHelpTipsDialog();
        stickerHelpTipsDialog.setArguments(bundle);
        return stickerHelpTipsDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_sticker_help_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        GlideUtils.loadRoundCornersImage(this.mContext, (ImageView) view.findViewById(R$id.iv_save_img), R$drawable.fq_ic_sticker_help_1, 6, RoundedCornersTransformation.CornerType.ALL);
        GlideUtils.loadRoundCornersImage(this.mContext, (ImageView) view.findViewById(R$id.iv_see_img), R$drawable.fq_ic_sticker_help_2, 6, RoundedCornersTransformation.CornerType.ALL);
        GlideUtils.loadRoundCornersImage(this.mContext, (ImageView) view.findViewById(R$id.iv_add_img), R$drawable.fq_ic_sticker_help_3, 6, RoundedCornersTransformation.CornerType.ALL);
        GlideUtils.loadRoundCornersImage(this.mContext, (ImageView) view.findViewById(R$id.iv_move_img), R$drawable.fq_ic_sticker_help_4, 6, RoundedCornersTransformation.CornerType.ALL);
        view.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$StickerHelpTipsDialog$yMQe5_cAzgQJKhrvTk2RFbobSl0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StickerHelpTipsDialog.this.lambda$initView$0$StickerHelpTipsDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$StickerHelpTipsDialog(View view) {
        dismiss();
    }
}
