package com.gen.p059mh.webapp_extensions.views.player;

import android.view.View;
import com.gen.p059mh.webapp_extensions.views.player.custom.PlayerStatesListener;
import com.gen.p059mh.webapp_extensions.views.player.custom.ResourceEntity;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import java.io.File;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.views.player.IPlayer */
/* loaded from: classes2.dex */
public interface IPlayer {
    void changeTextureViewShowType();

    void clarityOnClickBack(int i);

    int getCurPosition();

    int getCurrentPositionWhenPlaying();

    int getDuration();

    View getFullscreenButton();

    boolean getVerticalScreen();

    float getVolume();

    void hideAllWidget();

    boolean isIfCurrentIsFullscreen();

    boolean isPlaying();

    void onClickUiToggle();

    void onFullScreen();

    void onVideoPause();

    void onVideoReset();

    void onVideoResume();

    void onVideoSpeedPlay(float f, float f2, String str);

    StandardGSYVideoPlayer provideView();

    void quiteFullScreen();

    void release();

    void seekTo(long j);

    void setAdsUrl(String str);

    void setCollection(boolean z);

    void setControl(boolean z);

    void setCover(String str);

    void setHeaderVodUrl(String str);

    void setIfCurrentIsFullscreen(boolean z);

    void setLogo(String str);

    void setLoopPlay(boolean z);

    void setOtherAdsUrl(String str, long j);

    void setPlayerControllerCallBack(PlayerControllerCallBack playerControllerCallBack);

    void setPreViewList(String str, List list, int i);

    void setResolution(String str);

    void setResolutions(List<ResourceEntity> list, String str, File file);

    void setSeekOnStart(long j);

    void setStatusListener(PlayerStatesListener playerStatesListener);

    boolean setUp(String str, boolean z, String str2);

    void speedOnClickBack();

    void startClick(View.OnClickListener onClickListener);

    void startPlayLogic();

    void toggleLock();
}
