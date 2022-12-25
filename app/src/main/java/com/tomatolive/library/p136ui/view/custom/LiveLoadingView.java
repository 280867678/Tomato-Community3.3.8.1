package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.widget.LiveLoadingAnimationView;

/* renamed from: com.tomatolive.library.ui.view.custom.LiveLoadingView */
/* loaded from: classes3.dex */
public class LiveLoadingView extends RelativeLayout implements View.OnClickListener {
    public static final int CHANGE_LINE_TYPE_1 = 1;
    public static final int CHANGE_LINE_TYPE_2 = 2;
    public static final int CHANGE_LINE_TYPE_3 = 3;
    public static final int RELOAD_TYPE_ROOM = 1;
    public static final int RELOAD_TYPE_VIDEO = 2;
    private LinearLayout llContentBg;
    private LinearLayout llLineReloadBg;
    private LinearLayout llPlayLoadingBg;
    private LinearLayout llReloadPartBg;
    private LiveLoadingAnimationView loadingView;
    private OnLiveLoadingListener onLiveLoadingListener;
    private int reloadType = 1;
    private TextView tvFailTips;

    /* renamed from: com.tomatolive.library.ui.view.custom.LiveLoadingView$OnLiveLoadingListener */
    /* loaded from: classes3.dex */
    public interface OnLiveLoadingListener {
        void onChangeLineClickListener(int i);

        void onReloadClickListener(int i);
    }

    public LiveLoadingView(Context context) {
        super(context);
        initView(context);
    }

    public LiveLoadingView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        RelativeLayout.inflate(context, R$layout.fq_layout_live_loading_view, this);
        this.loadingView = (LiveLoadingAnimationView) findViewById(R$id.iv_play_loading);
        this.tvFailTips = (TextView) findViewById(R$id.tv_fail_tips);
        this.llContentBg = (LinearLayout) findViewById(R$id.ll_content_bg);
        this.llReloadPartBg = (LinearLayout) findViewById(R$id.ll_reload_part_bg);
        this.llLineReloadBg = (LinearLayout) findViewById(R$id.ll_line_reload_bg);
        this.llPlayLoadingBg = (LinearLayout) findViewById(R$id.ll_play_loading_bg);
        findViewById(R$id.tv_reload_btn).setOnClickListener(this);
        findViewById(R$id.tv_line_1).setOnClickListener(this);
        findViewById(R$id.tv_line_2).setOnClickListener(this);
        findViewById(R$id.tv_line_3).setOnClickListener(this);
    }

    public void setOnLiveLoadingListener(OnLiveLoadingListener onLiveLoadingListener) {
        this.onLiveLoadingListener = onLiveLoadingListener;
    }

    public void showLoadingView() {
        this.llPlayLoadingBg.setVisibility(0);
        this.llContentBg.setVisibility(4);
        this.loadingView.showLoading();
    }

    public void showReloadView(int i) {
        this.reloadType = i;
        stopLoadingViewAnimation();
        int i2 = 0;
        this.llContentBg.setVisibility(0);
        this.llReloadPartBg.setVisibility(0);
        LinearLayout linearLayout = this.llLineReloadBg;
        if (i != 2) {
            i2 = 8;
        }
        linearLayout.setVisibility(i2);
        this.tvFailTips.setText(i == 1 ? R$string.fq_room_info_fail : R$string.fq_video_load_fail);
    }

    public void showReloadLineView() {
        this.loadingView.setVisibility(4);
        this.llContentBg.setVisibility(0);
        this.llReloadPartBg.setVisibility(0);
        this.llLineReloadBg.setVisibility(0);
    }

    public void stopLoadingViewAnimation() {
        this.llPlayLoadingBg.setVisibility(4);
        this.loadingView.stopLoading();
    }

    public void onDestroy() {
        this.loadingView.release();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnLiveLoadingListener onLiveLoadingListener;
        if (view.getId() == R$id.tv_reload_btn) {
            OnLiveLoadingListener onLiveLoadingListener2 = this.onLiveLoadingListener;
            if (onLiveLoadingListener2 == null) {
                return;
            }
            onLiveLoadingListener2.onReloadClickListener(this.reloadType);
        } else if (view.getId() == R$id.tv_line_1) {
            OnLiveLoadingListener onLiveLoadingListener3 = this.onLiveLoadingListener;
            if (onLiveLoadingListener3 == null) {
                return;
            }
            onLiveLoadingListener3.onChangeLineClickListener(1);
        } else if (view.getId() == R$id.tv_line_2) {
            OnLiveLoadingListener onLiveLoadingListener4 = this.onLiveLoadingListener;
            if (onLiveLoadingListener4 == null) {
                return;
            }
            onLiveLoadingListener4.onChangeLineClickListener(2);
        } else if (view.getId() != R$id.tv_line_3 || (onLiveLoadingListener = this.onLiveLoadingListener) == null) {
        } else {
            onLiveLoadingListener.onChangeLineClickListener(3);
        }
    }
}
