package com.one.tomato.dialog;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.VideoPay;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.mvp.p080ui.vip.p083ui.VipActivity;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public class VideoSaveTipDialog extends CustomAlertDialog {
    private boolean isNeedPayDown;
    private VideoSaveTipListener listener;
    private Context mContext;
    private TextView tv_left;
    private TextView tv_potato_currency;
    private TextView tv_potato_tip;
    private TextView tv_right;
    private int type;

    /* loaded from: classes3.dex */
    public interface VideoSaveTipListener {
        void potatoDownload();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Unit lambda$setData$1() {
        return null;
    }

    public VideoSaveTipDialog(final Context context) {
        super(context, false);
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_video_save_tip, (ViewGroup) null);
        bottomLayoutGone();
        setCanceledOnTouchOutside(true);
        setMiddleNeedPadding(false);
        setBackgroundColor(this.mContext.getResources().getColor(R.color.transparent));
        setContentView(inflate);
        this.tv_potato_tip = (TextView) inflate.findViewById(R.id.tv_potato_tip);
        this.tv_potato_currency = (TextView) inflate.findViewById(R.id.tv_potato_currency);
        this.tv_left = (TextView) inflate.findViewById(R.id.tv_left);
        this.tv_right = (TextView) inflate.findViewById(R.id.tv_right);
        this.tv_left.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.VideoSaveTipDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VideoSaveTipDialog.this.dismiss();
                if (VideoSaveTipDialog.this.type == 1 || VideoSaveTipDialog.this.type == 3) {
                    if (VideoSaveTipDialog.this.listener == null) {
                        return;
                    }
                    VideoSaveTipDialog.this.listener.potatoDownload();
                } else if (VideoSaveTipDialog.this.type != 2 && VideoSaveTipDialog.this.type != 4) {
                } else {
                    RechargeActivity.startActivity(context);
                }
            }
        });
        this.tv_right.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.VideoSaveTipDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VideoSaveTipDialog.this.dismiss();
                if (VideoSaveTipDialog.this.isNeedPayDown || VideoSaveTipDialog.this.type == 1 || VideoSaveTipDialog.this.type == 2) {
                    return;
                }
                if (VideoSaveTipDialog.this.type != 3 && VideoSaveTipDialog.this.type != 4) {
                    return;
                }
                VipActivity.Companion.startActivity(context);
            }
        });
    }

    public void setInfo(VideoPay videoPay) {
        if (!isShow()) {
            show();
        }
        int photoVideoPayTomatoAmount = videoPay.getPhotoVideoPayTomatoAmount();
        double tmtBalance = videoPay.getTmtBalance();
        this.tv_potato_currency.setText(AppUtil.getString(R.string.video_save_cur_potato, FormatUtil.formatTomato2RMB(tmtBalance)));
        if (DBUtil.getUserInfo().getVipType() > 0) {
            double d = photoVideoPayTomatoAmount;
            this.tv_potato_tip.setText(AppUtil.getString(R.string.video_save_vip_need_potato_label, FormatUtil.formatTomato2RMB(d)));
            if (d <= tmtBalance) {
                this.tv_left.setText(AppUtil.getString(R.string.video_save_potato_num, FormatUtil.formatTomato2RMB(d)));
                this.type = 1;
            } else {
                this.tv_left.setText(R.string.video_save_vip_recharge);
                this.type = 2;
            }
            this.tv_right.setText(R.string.common_cancel);
            return;
        }
        double d2 = photoVideoPayTomatoAmount;
        this.tv_potato_tip.setText(AppUtil.getString(R.string.video_save_need_potato_label, FormatUtil.formatTomato2RMB(d2)));
        if (d2 <= tmtBalance) {
            this.tv_left.setText(AppUtil.getString(R.string.video_save_potato_num, FormatUtil.formatTomato2RMB(d2)));
            this.type = 3;
        } else {
            this.tv_left.setText(R.string.video_save_vip_recharge);
            this.type = 4;
        }
        this.tv_right.setText(R.string.video_save_vip_free);
    }

    public void setData(final int i) {
        if (!isShow()) {
            show();
        }
        PostUtils.INSTANCE.requestBalance(this.mContext, new Function1() { // from class: com.one.tomato.dialog.-$$Lambda$VideoSaveTipDialog$svsr88FhZ27magY3L2-8Sy_cjjQ
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo6794invoke(Object obj) {
                return VideoSaveTipDialog.this.lambda$setData$0$VideoSaveTipDialog(i, (String) obj);
            }
        }, $$Lambda$VideoSaveTipDialog$Ag9QEvZTUlInIevTlBgZ9lQUag.INSTANCE);
    }

    public /* synthetic */ Unit lambda$setData$0$VideoSaveTipDialog(int i, String str) {
        this.tv_potato_currency.setText(AppUtil.getString(R.string.video_save_cur_potato, FormatUtil.formatTomato2RMB(str)));
        double d = i;
        this.tv_potato_tip.setText(AppUtil.getString(R.string.video_save_need_potato_label, FormatUtil.formatTomato2RMB(d)));
        if (d > Double.parseDouble(str)) {
            this.tv_left.setText(R.string.video_save_vip_recharge);
            this.type = 4;
        } else {
            this.tv_left.setText(AppUtil.getString(R.string.video_save_potato_num, FormatUtil.formatTomato2RMB(d)));
            this.type = 3;
        }
        this.isNeedPayDown = true;
        this.tv_right.setText(R.string.common_cancel);
        this.tv_right.setTextColor(ContextCompat.getColor(this.context, R.color.disable));
        return null;
    }

    public void setVideoSaveTipListener(VideoSaveTipListener videoSaveTipListener) {
        this.listener = videoSaveTipListener;
    }
}
