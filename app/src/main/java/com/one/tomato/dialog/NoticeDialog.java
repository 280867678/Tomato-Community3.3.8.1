package com.one.tomato.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.MainNotifyBean;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.p087ad.AdUtil;

/* loaded from: classes3.dex */
public class NoticeDialog extends CustomAlertDialog {
    private ImageView iv_close;
    private TextView tv_action;
    private TextView tv_app_market;
    private TextView tv_content;
    private TextView tv_title_label;

    public NoticeDialog(Context context, final MainNotifyBean mainNotifyBean) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_main_notify, (ViewGroup) null);
        setContentView(inflate);
        setMiddleNeedPadding(false);
        bottomLayoutGone();
        this.tv_content = (TextView) inflate.findViewById(R.id.tv_content);
        this.tv_app_market = (TextView) inflate.findViewById(R.id.tv_app_market);
        this.tv_action = (TextView) inflate.findViewById(R.id.tv_action);
        this.iv_close = (ImageView) inflate.findViewById(R.id.iv_close);
        this.tv_title_label = (TextView) inflate.findViewById(R.id.tv_title_label);
        setBackground(null);
        if (!TextUtils.isEmpty(mainNotifyBean.getLinkUrl())) {
            PreferencesUtil.getInstance().putString("app_market", mainNotifyBean.getLinkUrl());
            this.tv_app_market.setVisibility(0);
        }
        if (!TextUtils.isEmpty(mainNotifyBean.getJumpTitle())) {
            this.tv_action.setVisibility(0);
            this.tv_action.setText(mainNotifyBean.getJumpTitle());
        }
        this.tv_content.setMovementMethod(new ScrollingMovementMethod());
        this.tv_content.setText(mainNotifyBean.getNoticeContent());
        this.tv_app_market.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.NoticeDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NoticeDialog.this.dismiss();
                AppUtil.startBrowseView(mainNotifyBean.getLinkUrl());
            }
        });
        this.tv_action.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.NoticeDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NoticeDialog.this.clickEvent(mainNotifyBean);
            }
        });
        this.iv_close.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.NoticeDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NoticeDialog.this.dismiss();
            }
        });
    }

    public void setTv_title_labelText(String str) {
        this.tv_title_label.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void clickEvent(MainNotifyBean mainNotifyBean) {
        char c;
        String jumpLink;
        int i;
        String str;
        int i2;
        dismiss();
        AdUtil adUtil = new AdUtil((Activity) this.context);
        int id = mainNotifyBean.getId();
        String jumpType = mainNotifyBean.getJumpType();
        switch (jumpType.hashCode()) {
            case 49:
                if (jumpType.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (jumpType.equals("2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (jumpType.equals("3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        String str2 = "";
        if (c != 0) {
            if (c == 1) {
                i2 = 3;
                jumpLink = mainNotifyBean.getLittleAppStr();
                str = str2;
            } else if (c != 2) {
                str = str2;
                jumpLink = str;
                i2 = 0;
            } else {
                String adJumpModule = mainNotifyBean.getAdJumpModule();
                str = mainNotifyBean.getAdJumpDetail();
                jumpLink = str2;
                i = 0;
                str2 = adJumpModule;
                i2 = 1;
            }
            i = 0;
        } else {
            int openType = mainNotifyBean.getOpenType();
            jumpLink = mainNotifyBean.getJumpLink();
            i = openType;
            str = str2;
            i2 = 2;
        }
        adUtil.clickAd(id, i2, str2, str, i, jumpLink);
    }
}
