package com.one.tomato.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;

/* loaded from: classes3.dex */
public class LevelUpdateDialog extends CustomAlertDialog {
    private ImageView iv_close;
    private TextView tv_content1;
    private TextView tv_content2;
    private TextView tv_content3;
    private TextView tv_title;

    public LevelUpdateDialog(final Context context, LevelBean levelBean) {
        super(context);
        setBackground(null);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_level_update, (ViewGroup) null);
        setContentView(inflate);
        setMiddleNeedPadding(false);
        bottomLayoutGone();
        this.tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        this.tv_content1 = (TextView) inflate.findViewById(R.id.tv_content1);
        this.tv_content2 = (TextView) inflate.findViewById(R.id.tv_content2);
        this.tv_content3 = (TextView) inflate.findViewById(R.id.tv_content3);
        this.iv_close = (ImageView) inflate.findViewById(R.id.iv_close);
        final int levelNickIndex = levelBean.getLevelNickIndex();
        this.tv_title.setText(AppUtil.getString(R.string.level_update_dialog_title, Integer.valueOf(levelBean.getCurrentLevelIndex())));
        this.tv_content1.setText(AppUtil.getString(R.string.level_update_dialog_content1, Integer.valueOf(levelBean.getLevelUpReward())));
        if (levelBean.getCurrentLevelIndex() >= 3) {
            this.tv_content3.setVisibility(0);
        } else if (levelBean.getCurrentLevelIndex() >= 2) {
            this.tv_content2.setVisibility(0);
        }
        final LevelBean levelBean2 = DBUtil.getLevelBean();
        levelBean2.setCurrentLevelIndex(levelBean.getCurrentLevelIndex());
        DBUtil.saveLevelBean(levelBean);
        this.iv_close.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.LevelUpdateDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LevelUpdateDialog.this.dismiss();
            }
        });
        setOnDismissListener(new DialogInterface.OnDismissListener(this) { // from class: com.one.tomato.dialog.LevelUpdateDialog.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                if (levelNickIndex > levelBean2.getLevelNickIndex()) {
                    new LevelNickUpdateDialog(context);
                }
            }
        });
    }
}
