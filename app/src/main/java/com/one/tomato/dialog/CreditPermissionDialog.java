package com.one.tomato.dialog;

import android.content.Context;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.mvp.p080ui.vip.p083ui.VipActivity;
import com.one.tomato.utils.AppUtil;

/* loaded from: classes3.dex */
public class CreditPermissionDialog extends CustomAlertDialog {
    public CreditPermissionDialog(final Context context, int i, LevelBean levelBean) {
        super(context);
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        if (levelBean.getReplyCount() == 0) {
                            setTitle(R.string.credit_dialog_reply_deny_title);
                            setMessage(AppUtil.getString(R.string.credit_dialog_reply_deny_content, Integer.valueOf(levelBean.getPrestigeBalance())));
                        } else {
                            setTitle(R.string.credit_dialog_reply_title);
                            setMessage(AppUtil.getString(R.string.credit_dialog_reply_content, Integer.valueOf(levelBean.getPrestigeBalance()), Integer.valueOf(levelBean.getReplyCount()), Integer.valueOf(levelBean.getReplyCount_times())));
                        }
                    }
                } else if (levelBean.getCommentCount() == 0) {
                    setTitle(R.string.credit_dialog_comment_deny_title);
                    setMessage(AppUtil.getString(R.string.credit_dialog_comment_deny_content, Integer.valueOf(levelBean.getPrestigeBalance())));
                } else {
                    setTitle(R.string.credit_dialog_comment_title);
                    setMessage(AppUtil.getString(R.string.credit_dialog_comment_content, Integer.valueOf(levelBean.getPrestigeBalance()), Integer.valueOf(levelBean.getCommentCount()), Integer.valueOf(levelBean.getCommentCount_times())));
                }
            }
        } else if (levelBean.getPubCount() == 0) {
            setTitle(R.string.credit_dialog_publish_deny_title);
            setMessage(AppUtil.getString(R.string.credit_dialog_publish_deny_content, Integer.valueOf(levelBean.getPrestigeBalance())));
        } else {
            setTitle(R.string.credit_dialog_publish_title);
            setMessage(AppUtil.getString(R.string.credit_dialog_publish_content, Integer.valueOf(levelBean.getPrestigeBalance()), Integer.valueOf(levelBean.getPubCount()), Integer.valueOf(levelBean.getPubCount_times())));
        }
        setCancelButtonTextColor(R.color.white);
        setCancelButtonBackgroundRes(R.drawable.common_shape_solid_corner30_disable);
        setCancelButton(R.string.common_cancel);
        setConfirmButtonTextColor(R.color.white);
        setConfirmButtonBackgroundRes(R.drawable.common_selector_solid_corner30_coloraccent);
        setConfirmButton(R.string.post_goto_open_vip, new View.OnClickListener() { // from class: com.one.tomato.dialog.CreditPermissionDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CreditPermissionDialog.this.dismiss();
                VipActivity.Companion.startActivity(context);
            }
        });
    }
}
