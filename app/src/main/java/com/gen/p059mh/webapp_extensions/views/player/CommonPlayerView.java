package com.gen.p059mh.webapp_extensions.views.player;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.gen.p059mh.webapp_extensions.R$mipmap;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.utils.DeviceUtils;
import com.gen.p059mh.webapp_extensions.views.player.custom.PlayerStatesListener;
import com.gen.p059mh.webapp_extensions.views.player.custom.ResourceEntity;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import java.io.File;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.views.player.CommonPlayerView */
/* loaded from: classes2.dex */
public class CommonPlayerView extends StandardGSYVideoPlayer implements IPlayer {
    ImageView fullScreen2;
    ImageView ivFullScreen;
    ImageView ivLogo;
    LinearLayout llError;
    RelativeLayout rlRootTop;

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void clarityOnClickBack(int i) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public int getCurPosition() {
        return 0;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public boolean getVerticalScreen() {
        return false;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void onFullScreen() {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public StandardGSYVideoPlayer provideView() {
        return this;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void quiteFullScreen() {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setAdsUrl(String str) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setCollection(boolean z) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setControl(boolean z) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setHeaderVodUrl(String str) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setLoopPlay(boolean z) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setOtherAdsUrl(String str, long j) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setPlayerControllerCallBack(PlayerControllerCallBack playerControllerCallBack) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setPreViewList(String str, List list, int i) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setResolution(String str) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setResolutions(List<ResourceEntity> list, String str, File file) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setStatusListener(PlayerStatesListener playerStatesListener) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void speedOnClickBack() {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void toggleLock() {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public /* bridge */ /* synthetic */ View getFullscreenButton() {
        return super.getFullscreenButton();
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void changeTextureViewShowType() {
        super.changeTextureViewShowType();
    }

    public CommonPlayerView(Context context) {
        super(context);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setCover(String str) {
        if (this.mThumbImageViewLayout != null) {
            ImageView imageView = new ImageView(getContext());
            setThumbImageView(imageView);
            this.mThumbImageView.setClickable(false);
            this.mThumbImageViewLayout.setVisibility(0);
            try {
                SelectionSpec.getInstance().imageEngine.load(imageView.getContext(), imageView, Uri.parse(str));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void onClickUiToggle() {
        super.onClickUiToggle();
    }

    public int getEnlargeImageRes() {
        return R$mipmap.play_ic_screen_full;
    }

    public int getShrinkImageRes() {
        return R$mipmap.icon_player_closefull;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setIfCurrentIsFullscreen(boolean z) {
        super.setIfCurrentIsFullscreen(z);
        int dpToPixel = (int) DeviceUtils.dpToPixel(getContext(), 30.0f);
        if (z) {
            this.fullScreen2.setVisibility(0);
            this.llError.setPadding(0, dpToPixel * 2, 0, 0);
            this.ivFullScreen.setImageResource(getShrinkImageRes());
            this.rlRootTop.setPadding(dpToPixel, 0, dpToPixel, 0);
            this.mBottomContainer.setPadding(dpToPixel, 0, dpToPixel, 0);
            return;
        }
        this.fullScreen2.setVisibility(8);
        this.llError.setPadding(0, 0, 0, 0);
        this.ivFullScreen.setImageResource(getEnlargeImageRes());
        this.rlRootTop.setPadding(0, 0, 0, 0);
        this.mBottomContainer.setPadding(0, 0, 0, 0);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void onVideoSpeedPlay(float f, float f2, String str) {
        if (getCurrentState() == 2) {
            super.onVideoSpeedPlay(f);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void hideAllWidget() {
        super.hideAllWidget();
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public boolean setUp(String str, boolean z, String str2) {
        VideoScaleView.videoPlaySaleType = 1;
        GSYVideoType.setShowType(0);
        return super.setUp(str, z, str2);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setLogo(String str) {
        SelectionSpec.getInstance().imageEngine.load(this.ivLogo.getContext(), this.ivLogo, Uri.parse(str));
        this.ivLogo.setVisibility(0);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public boolean isPlaying() {
        return getCurrentState() == 2;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public float getVolume() {
        return this.mAudioManager.getStreamVolume(3) / this.mAudioManager.getStreamMaxVolume(3);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void startClick(View.OnClickListener onClickListener) {
        getFullscreenButton().setOnClickListener(onClickListener);
        this.fullScreen2.setOnClickListener(onClickListener);
    }
}
