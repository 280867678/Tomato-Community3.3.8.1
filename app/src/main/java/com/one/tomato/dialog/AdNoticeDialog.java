package com.one.tomato.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.p087ad.AdUtil;

/* loaded from: classes3.dex */
public class AdNoticeDialog extends CustomAlertDialog {
    private ImageView iv_close;
    private ImageView iv_notice;

    public AdNoticeDialog(Context context, final AdPage adPage) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_ad_notify, (ViewGroup) null);
        setContentView(inflate);
        setMiddleNeedPadding(false);
        bottomLayoutGone();
        this.iv_notice = (ImageView) inflate.findViewById(R.id.iv_notice);
        this.iv_close = (ImageView) inflate.findViewById(R.id.iv_close);
        setBackgroundColor(context.getResources().getColor(R.color.transparent));
        this.iv_notice.setVisibility(0);
        ImageLoaderUtil.loadSecImage(context, this.iv_notice, new ImageBean(adPage.getImageUrlSec()), ImageLoaderUtil.getCenterInsideImageOption(this.iv_notice));
        this.iv_notice.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.AdNoticeDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AdNoticeDialog.this.clickEvent(adPage);
            }
        });
        this.iv_close.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.AdNoticeDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AdNoticeDialog.this.dismiss();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clickEvent(AdPage adPage) {
        dismiss();
        new AdUtil((Activity) this.context).clickAd(adPage.getAdId(), adPage.getAdType(), adPage.getAdJumpModule(), adPage.getAdJumpDetail(), adPage.getOpenType(), adPage.getAdLink());
    }
}
