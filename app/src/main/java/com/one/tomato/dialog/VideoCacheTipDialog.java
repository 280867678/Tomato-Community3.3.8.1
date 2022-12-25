package com.one.tomato.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.VideoPay;
import com.one.tomato.mvp.p080ui.vip.p083ui.VipActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;

/* loaded from: classes3.dex */
public class VideoCacheTipDialog extends CustomAlertDialog {
    private VideoDownloadTipListener listener;
    private Context mContext;
    private TextView tv_currency_download;
    private TextView tv_open_vip;
    private TextView tv_virtual_currency;
    private TextView tv_virtual_tip;

    /* loaded from: classes3.dex */
    public interface VideoDownloadTipListener {
        void currencyDownload();
    }

    public VideoCacheTipDialog(final Context context) {
        super(context, false);
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_video_cache_tip, (ViewGroup) null);
        bottomLayoutGone();
        setCanceledOnTouchOutside(true);
        setMiddleNeedPadding(false);
        setBackgroundColor(this.mContext.getResources().getColor(R.color.transparent));
        setContentView(inflate);
        this.tv_virtual_tip = (TextView) inflate.findViewById(R.id.tv_virtual_tip);
        this.tv_virtual_currency = (TextView) inflate.findViewById(R.id.tv_virtual_currency);
        this.tv_currency_download = (TextView) inflate.findViewById(R.id.tv_currency_download);
        this.tv_open_vip = (TextView) inflate.findViewById(R.id.tv_open_vip);
        this.tv_currency_download.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.VideoCacheTipDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (VideoCacheTipDialog.this.listener != null) {
                    VideoCacheTipDialog.this.listener.currencyDownload();
                }
            }
        });
        this.tv_open_vip.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.VideoCacheTipDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VideoCacheTipDialog.this.dismiss();
                VipActivity.Companion.startActivity(context);
            }
        });
    }

    public void setInfo(VideoPay videoPay) {
        if (!isShow()) {
            show();
        }
        int videoPayTomatoAmount = videoPay.getVideoPayTomatoAmount();
        double tmtBalance = videoPay.getTmtBalance();
        double d = videoPayTomatoAmount;
        this.tv_virtual_tip.setText(AppUtil.getString(R.string.post_video_consume_tip2, FormatUtil.formatTomato2RMB(d)));
        this.tv_virtual_currency.setText(AppUtil.getString(R.string.video_save_cur_potato, FormatUtil.formatTomato2RMB(tmtBalance)));
        if (tmtBalance < d) {
            this.tv_currency_download.setText(R.string.post_video_consume_tip7);
        } else {
            this.tv_currency_download.setText(AppUtil.getString(R.string.post_video_consume_tip6, FormatUtil.formatTomato2RMB(d)));
        }
    }

    public void setVideoDownloadTipListener(VideoDownloadTipListener videoDownloadTipListener) {
        this.listener = videoDownloadTipListener;
    }
}
