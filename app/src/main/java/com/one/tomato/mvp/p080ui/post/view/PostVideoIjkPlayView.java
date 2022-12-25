package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.AbstractPlayer;
import com.dueeeke.videoplayer.player.BaseIjkVideoView;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.ProgressManager;
import com.dueeeke.videoplayer.util.PlayerUtils;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.utils.CrashHandler;
import com.one.tomato.utils.DataUploadUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostVideoIjkPlayView.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PostVideoIjkPlayView */
/* loaded from: classes3.dex */
public final class PostVideoIjkPlayView extends IjkVideoView {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostVideoIjkPlayView(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.mOrientationEventListener.disable();
    }

    public final void setmPlayerContainerBackGraound(int i) {
        if (i != 0) {
            this.mPlayerContainer.setBackgroundResource(i);
        }
    }

    public final void setmPlayerContainerBackGraoundColor(int i) {
        this.mPlayerContainer.setBackgroundColor(i);
    }

    public final void setmPlayerContainerBackGraound(Drawable drawable) {
        FrameLayout frameLayout;
        if (drawable == null || (frameLayout = this.mPlayerContainer) == null) {
            return;
        }
        frameLayout.setBackground(drawable);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    public void startPlay() {
        super.startPlay();
    }

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    protected boolean checkNetwork() {
        BaseVideoController baseVideoController;
        if (!PostUtils.INSTANCE.is4GVideoPlay() && PlayerUtils.getNetworkType(getContext()) == 4 && !BaseIjkVideoView.IS_PLAY_ON_MOBILE_NETWORK && (baseVideoController = this.mVideoController) != null) {
            if (baseVideoController != null) {
                baseVideoController.showStatusView();
            }
            PostUtils.INSTANCE.set4GVideoPlay(true);
        }
        return false;
    }

    public final String getCurrentUrl() {
        return getUrl();
    }

    public final ProgressManager getProgressManger() {
        return this.mProgressManager;
    }

    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView, com.dueeeke.videoplayer.listener.PlayerEventListener
    public void onError(String str) {
        super.onError(str);
        String str2 = "error: " + str + " url:" + getUrl();
        AbstractPlayer abstractPlayer = this.mMediaPlayer;
        DataUploadUtil.uploadPlayError(str2, 0, "PostVideoIjkPlayView", abstractPlayer != null ? (int) abstractPlayer.getCurrentPosition() : 0);
        CrashHandler.getInstance().handleException("url = " + getUrl() + "\n" + str, 15);
    }
}
