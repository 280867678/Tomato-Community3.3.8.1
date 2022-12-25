package com.one.tomato.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.mvp.p080ui.level.MyLevelActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class LevelNickUpdateDialog extends CustomAlertDialog {
    private ImageView iv_close;
    private ImageView iv_nick_head;
    private TextView tv_action;
    private TextView tv_content2;
    private TextView tv_content3;
    private TextView tv_level_nick;
    private TextView tv_level_nick_desc;

    public LevelNickUpdateDialog(final Context context) {
        super(context);
        setBackground(null);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_level_nick_update, (ViewGroup) null);
        setContentView(inflate);
        setMiddleNeedPadding(false);
        bottomLayoutGone();
        this.iv_nick_head = (ImageView) inflate.findViewById(R.id.iv_nick_head);
        this.tv_level_nick = (TextView) inflate.findViewById(R.id.tv_level_nick);
        this.tv_level_nick_desc = (TextView) inflate.findViewById(R.id.tv_level_nick_desc);
        this.tv_content2 = (TextView) inflate.findViewById(R.id.tv_content2);
        this.tv_content3 = (TextView) inflate.findViewById(R.id.tv_content3);
        this.tv_action = (TextView) inflate.findViewById(R.id.tv_action);
        this.iv_close = (ImageView) inflate.findViewById(R.id.iv_close);
        LevelBean levelBean = DBUtil.getLevelBean();
        int levelNickIndex = levelBean.getLevelNickIndex();
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf((int) R.drawable.level_nick1_s));
        arrayList.add(Integer.valueOf((int) R.drawable.level_nick2_s));
        arrayList.add(Integer.valueOf((int) R.drawable.level_nick3_s));
        arrayList.add(Integer.valueOf((int) R.drawable.level_nick4_s));
        arrayList.add(Integer.valueOf((int) R.drawable.level_nick5_s));
        arrayList.add(Integer.valueOf((int) R.drawable.level_nick6_s));
        int i = levelNickIndex - 1;
        this.iv_nick_head.setImageResource(((Integer) arrayList.get(i)).intValue());
        TextView textView = this.tv_level_nick;
        textView.setText(AppUtil.getString(R.string.common_tomato) + levelBean.getLevelNickName());
        this.tv_level_nick_desc.setText(AppUtil.getString(R.string.level_nick_update_dialog_desc, levelBean.getLevelNickName()));
        List<LevelBean.LevelCfg> listLevelCfg = levelBean.getListLevelCfg();
        if (listLevelCfg != null && listLevelCfg.size() != 0) {
            try {
                this.tv_content2.setText(AppUtil.getString(R.string.level_nick_update_dialog_content2, Integer.valueOf(listLevelCfg.get(i).getFreeLookTime())));
                this.tv_content3.setText(AppUtil.getString(R.string.level_nick_update_dialog_content3, Integer.valueOf(listLevelCfg.get(i).getLevelUpReward())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.tv_action.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.LevelNickUpdateDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LevelNickUpdateDialog.this.dismiss();
                MyLevelActivity.Companion.startActivity(context);
            }
        });
        this.iv_close.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.LevelNickUpdateDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LevelNickUpdateDialog.this.dismiss();
            }
        });
    }
}
