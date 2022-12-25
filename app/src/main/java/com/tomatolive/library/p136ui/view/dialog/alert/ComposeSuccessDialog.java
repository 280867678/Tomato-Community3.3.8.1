package com.tomatolive.library.p136ui.view.dialog.alert;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.download.ResHotLoadManager;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.SvgaUtils;

/* renamed from: com.tomatolive.library.ui.view.dialog.alert.ComposeSuccessDialog */
/* loaded from: classes3.dex */
public class ComposeSuccessDialog extends BaseDialogFragment implements View.OnClickListener {
    private static final String URL_KEY = "CODE_KEY";
    private ImageView ivGiftIcon;
    private SVGAImageView svLuckAnim;
    private SVGAParser svgaParser;
    private ImageView tvText;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.8d;
    }

    public static ComposeSuccessDialog newInstance(String str) {
        Bundle bundle = new Bundle();
        ComposeSuccessDialog composeSuccessDialog = new ComposeSuccessDialog();
        bundle.putString("CODE_KEY", str);
        composeSuccessDialog.setArguments(bundle);
        return composeSuccessDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_compose_suc_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        String string = getArguments().getString("CODE_KEY");
        this.svLuckAnim = (SVGAImageView) view.findViewById(R$id.iv_svga);
        this.svgaParser = new SVGAParser(getContext());
        this.ivGiftIcon = (ImageView) view.findViewById(R$id.iv_gift_icon);
        this.tvText = (ImageView) view.findViewById(R$id.iv_text);
        if (!TextUtils.isEmpty(string)) {
            loadAnim();
            GlideUtils.loadImage(getContext(), this.ivGiftIcon, string);
        }
        view.findViewById(R$id.tv_start).setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        dismiss();
    }

    private void loadAnim() {
        SvgaUtils.playHotLoadRes(ResHotLoadManager.getInstance().getResHotLoadPath(ResHotLoadManager.COMPOSE_ANIM_RES), this.svLuckAnim, this.svgaParser);
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onDetach() {
        super.onDetach();
        SVGAImageView sVGAImageView = this.svLuckAnim;
        if (sVGAImageView != null) {
            sVGAImageView.stopAnimation(true);
        }
    }
}
