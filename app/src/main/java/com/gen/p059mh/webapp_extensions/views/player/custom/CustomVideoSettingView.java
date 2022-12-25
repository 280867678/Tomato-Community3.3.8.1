package com.gen.p059mh.webapp_extensions.views.player.custom;

import android.support.p002v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.common.use.delayclick.HOnClickListener;
import com.gen.p059mh.webapp_extensions.R$color;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$mipmap;
import com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView;
import com.gen.p059mh.webapp_extensions.views.player.PlayerDialogCallback;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;

/* renamed from: com.gen.mh.webapp_extensions.views.player.custom.CustomVideoSettingView */
/* loaded from: classes2.dex */
public class CustomVideoSettingView extends BasePlayerDialogView {
    ImageView imgCollect;
    TextView[] sizeViews;
    TextView textCollect;

    /* renamed from: com.gen.mh.webapp_extensions.views.player.custom.CustomVideoSettingView$VideoSettingCallback */
    /* loaded from: classes2.dex */
    public interface VideoSettingCallback extends PlayerDialogCallback {
        void onChangeSize(int i);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    protected int getViewForRes() {
        return R$layout.custom_player_view_video_setting;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    public void initView() {
        super.initView();
        LinearLayout linearLayout = (LinearLayout) this.contentView.findViewById(R$id.ll_collect);
        this.imgCollect = (ImageView) this.contentView.findViewById(R$id.img_collect);
        this.imgCollect.setOnClickListener(new HOnClickListener(this) { // from class: com.gen.mh.webapp_extensions.views.player.custom.CustomVideoSettingView.1
        });
        this.textCollect = (TextView) this.contentView.findViewById(R$id.text_collect);
        this.sizeViews = new TextView[3];
        int i = 0;
        this.sizeViews[0] = (TextView) this.contentView.findViewById(R$id.text_type1);
        this.sizeViews[0].setOnClickListener(this);
        this.sizeViews[1] = (TextView) this.contentView.findViewById(R$id.text_type2);
        this.sizeViews[1].setOnClickListener(this);
        this.sizeViews[2] = (TextView) this.contentView.findViewById(R$id.text_type3);
        this.sizeViews[2].setOnClickListener(this);
        if (GSYVideoType.getShowType() == -4) {
            i = 1;
        } else if (GSYVideoType.getShowType() == 4) {
            i = 2;
        }
        checkSelect(this.sizeViews, i);
    }

    public void collectChange(boolean z) {
        updateCollectState(z);
    }

    public void updateCollectState(boolean z) {
        this.imgCollect.setImageResource(z ? R$mipmap.ic_playpage_collect_white_click : R$mipmap.ic_playpage_collect_white);
        this.textCollect.setText(z ? "已收藏" : "收藏");
        this.textCollect.setTextColor(ContextCompat.getColor(this.mContext, z ? R$color.yellow : R$color.white));
    }

    public void checkSelect(TextView[] textViewArr, int i) {
        int i2 = 0;
        int max = Math.max(0, i);
        while (i2 < textViewArr.length) {
            textViewArr[i2].setTextColor(this.mContext.getResources().getColor(max == i2 ? R$color.yellow : R$color.white));
            i2++;
        }
        PlayerDialogCallback playerDialogCallback = this.playerDialogCallback;
        if (playerDialogCallback != null) {
            ((VideoSettingCallback) playerDialogCallback).onChangeSize(max);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R$id.text_type1) {
            checkSelect(this.sizeViews, 0);
        } else if (id == R$id.text_type2) {
            checkSelect(this.sizeViews, 1);
        } else if (id != R$id.text_type3) {
        } else {
            checkSelect(this.sizeViews, 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView
    public void initData() {
        super.initData();
    }
}
