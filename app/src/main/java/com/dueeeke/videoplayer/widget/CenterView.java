package com.dueeeke.videoplayer.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.dueeeke.videoplayer.C1228R;

/* loaded from: classes2.dex */
public class CenterView extends LinearLayout {
    private ImageView ivIcon;
    private ProgressBar proPercent;
    private TextView tvPercent;

    public CenterView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setGravity(17);
        View inflate = LayoutInflater.from(getContext()).inflate(C1228R.C1232layout.dkplayer_layout_center_window, this);
        this.ivIcon = (ImageView) inflate.findViewById(C1228R.C1231id.iv_icon);
        this.tvPercent = (TextView) inflate.findViewById(C1228R.C1231id.tv_percent);
        this.proPercent = (ProgressBar) inflate.findViewById(C1228R.C1231id.pro_percent);
    }

    public void setIcon(int i) {
        ImageView imageView = this.ivIcon;
        if (imageView != null) {
            imageView.setImageResource(i);
        }
    }

    public void setTextView(String str) {
        TextView textView = this.tvPercent;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setProPercent(int i) {
        ProgressBar progressBar = this.proPercent;
        if (progressBar != null) {
            progressBar.setProgress(i);
        }
    }

    public void setProVisibility(int i) {
        ProgressBar progressBar = this.proPercent;
        if (progressBar != null) {
            progressBar.setVisibility(i);
        }
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i != 0) {
            startAnimation(AnimationUtils.loadAnimation(getContext(), C1228R.C1229anim.dkplayer_anim_center_view));
        }
    }
}
