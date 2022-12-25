package com.one.tomato.thirdpart.video.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.broccoli.p150bh.R;
import com.dueeeke.videoplayer.player.BaseIjkVideoView;
import com.one.tomato.thirdpart.domain.DomainRequest;
import com.one.tomato.utils.BaseToTVideoController;
import java.util.Map;

/* loaded from: classes3.dex */
public class StartUpVideoController extends BaseToTVideoController {
    private ProgressBar progressBar;
    private ImageView thumb;

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    protected int getLayoutId() {
        return R.layout.layout_start_up_controller;
    }

    @Override // com.one.tomato.utils.BaseToTVideoController
    public Map<String, Object> getVideoErrorInfo() {
        return null;
    }

    public StartUpVideoController(@NonNull Context context) {
        super(context);
    }

    public StartUpVideoController(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public StartUpVideoController(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.controller.GestureVideoController, com.dueeeke.videoplayer.controller.BaseVideoController
    public void initView() {
        super.initView();
        this.thumb = (ImageView) this.mControllerView.findViewById(R.id.iv_thumb);
        this.progressBar = (ProgressBar) this.mControllerView.findViewById(R.id.progressBar);
    }

    public ImageView getThumb() {
        return this.thumb;
    }

    @Override // com.one.tomato.utils.BaseToTVideoController, com.dueeeke.videoplayer.controller.BaseVideoController
    public void setPlayState(int i) {
        super.setPlayState(i);
        switch (i) {
            case -1:
                DomainRequest.getInstance().switchDomainUrlByType("ttViewVideoNew");
                this.thumb.setVisibility(0);
                this.progressBar.setVisibility(4);
                hideStatusView();
                return;
            case 0:
                this.thumb.setVisibility(0);
                this.progressBar.setVisibility(4);
                return;
            case 1:
                this.thumb.setVisibility(0);
                this.progressBar.setVisibility(0);
                return;
            case 2:
                this.thumb.setVisibility(0);
                this.progressBar.setVisibility(0);
                return;
            case 3:
                this.thumb.setVisibility(8);
                this.progressBar.setVisibility(4);
                return;
            case 4:
            default:
                return;
            case 5:
                this.progressBar.setVisibility(4);
                return;
            case 6:
                this.progressBar.setVisibility(0);
                return;
            case 7:
                this.progressBar.setVisibility(4);
                return;
        }
    }

    @Override // com.one.tomato.utils.BaseToTVideoController
    public boolean isReplay() {
        setPlayState(5);
        setKeepScreenOn(false);
        ((BaseIjkVideoView) this.mMediaPlayer).onPlayStopped();
        return false;
    }
}
