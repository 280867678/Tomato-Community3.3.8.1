package com.gen.p059mh.webapp_extensions.views.player.custom;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.common.use.util.NetworkUtils;
import com.common.use.util.StringUtils;
import com.common.use.util.ToastUtils;
import com.gen.p059mh.webapp_extensions.R$drawable;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$mipmap;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.utils.AnimationUtil;
import com.gen.p059mh.webapp_extensions.utils.DeviceUtils;
import com.gen.p059mh.webapp_extensions.views.RoundCornerImageView;
import com.gen.p059mh.webapp_extensions.views.VideoPhoneView;
import com.gen.p059mh.webapp_extensions.views.player.IPlayer;
import com.gen.p059mh.webapp_extensions.views.player.PlayerControllerCallBack;
import com.gen.p059mh.webapp_extensions.views.player.custom.CustomClarityChoiceView;
import com.gen.p059mh.webapp_extensions.views.player.custom.CustomPlayerDialogFrameLayout;
import com.gen.p059mh.webapp_extensions.views.player.custom.CustomVideoSpeedPlayView;
import com.shuyu.gsyvideoplayer.listener.GSYVideoShotListener;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.HVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/* renamed from: com.gen.mh.webapp_extensions.views.player.custom.HlwPlayerView */
/* loaded from: classes2.dex */
public class HlwPlayerView extends HVideoPlayer implements IPlayer {
    private String headerVodUrl;
    private ImageView imgError;
    private ImageView imgSetting;
    private ImageView ivAds;
    private ImageView ivBack;
    private ImageView ivLogo;
    private ImageView ivOtherAds;
    private ImageView ivOtherAdsClose;
    private ImageView ivSeekBack;
    private ImageView ivSeekTo;
    private LinearLayout layoutProgress;
    private LinearLayout layoutSkip;
    private LinearLayout llNoNetwork;
    private LinearLayout llNoVip;
    RelativeLayout mPreviewLayout;
    protected PlayerStatesListener mStatesListener;
    private LinearLayout phoneInfoLayout;
    private CustomPlayerDialogFrameLayout playerDialogFrameLayout;
    List<ResourceEntity> resourceList;
    private RelativeLayout rlAds;
    private RelativeLayout rlError;
    private RelativeLayout rlLeft;
    private RelativeLayout rlOtherAds;
    private RelativeLayout rlRight;
    private LinearLayout titleLayout;
    private TextView tvResolution;
    private TextView tvSpeed;
    private ImageView tvVolume;
    private VideoPhoneView videoPhoneView;
    private boolean isMiniSize = false;
    private int resolutionPosition = 1;
    private boolean isNoVip = false;
    private boolean isNoNetWork = false;
    boolean adModel = false;
    boolean otherAdsModel = false;
    int lastPosition = 0;
    boolean isLocalMode = false;
    long currentPosition = 0;
    VideoInfo videoInfo = new VideoInfo();
    boolean verticalScreen = true;
    private String adsUrl = "";
    private String otherAdsUrl = "";
    boolean isShowAds = false;

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void changeTextureViewShowType() {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public float getVolume() {
        return 0.0f;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public boolean isPlaying() {
        return false;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public StandardGSYVideoPlayer provideView() {
        return this;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setControl(boolean z) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setCover(String str) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setLoopPlay(boolean z) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setPlayerControllerCallBack(PlayerControllerCallBack playerControllerCallBack) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setResolution(String str) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public /* bridge */ /* synthetic */ View getFullscreenButton() {
        return super.getFullscreenButton();
    }

    public HlwPlayerView(Context context) {
        super(context);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public boolean getVerticalScreen() {
        return this.verticalScreen;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void onVideoResume() {
        super.onVideoResume();
        Timber.tag("HVideoView").mo18i("onVideoResume", new Object[0]);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setLogo(String str) {
        SelectionSpec.getInstance().imageEngine.load(this.mContext, this.ivLogo, str);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void onVideoReset() {
        super.onVideoReset();
        Timber.tag("HVideoView").mo18i("onVideoReset", new Object[0]);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void onVideoPause() {
        super.onVideoPause();
        isLockLand();
        if (this.mThumbImageViewLayout != null && !this.isLocalMode && this.mTextureView != null && this.mThumbImageViewLayout.getVisibility() == 4) {
            setThumbImageView(new ImageView(getContext()));
            this.mThumbImageView.setClickable(false);
            this.mThumbImageViewLayout.setVisibility(0);
            this.mTextureView.taskShotPic(new GSYVideoShotListener(this) { // from class: com.gen.mh.webapp_extensions.views.player.custom.HlwPlayerView.1
            }, false);
        }
        Timber.tag("HVideoView").mo18i("onVideoPause", new Object[0]);
    }

    protected void setViewShowState(View view, int i) {
        if (view == null) {
            return;
        }
        boolean z = view.getVisibility() == i;
        super.setViewShowState(view, i);
        if (z || this.isMiniSize || getCurrentState() == 1 || getCurrentState() == 3) {
            return;
        }
        int id = view.getId();
        if (id == R$id.layout_top) {
            if (i == 0) {
                this.mTopContainer.setAnimation(AnimationUtil.moveTop2Self(150));
                ((TextView) this.titleLayout.findViewById(R$id.tv_video_network)).setText(NetworkUtils.getNetText());
                return;
            }
            this.mTopContainer.setAnimation(AnimationUtil.moveSelf2Top(150));
        } else if (id != R$id.layout_bottom) {
        } else {
            if (i == 0) {
                this.mBottomContainer.setAnimation(AnimationUtil.moveBottom2Self(150));
            } else {
                this.mBottomContainer.setAnimation(AnimationUtil.moveSelf2Bottom(150));
            }
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void speedOnClickBack() {
        startSpeedUI();
    }

    private void startSpeedUI() {
        hideAllWidget();
        CustomVideoSpeedPlayView customVideoSpeedPlayView = new CustomVideoSpeedPlayView(getActivityContext());
        customVideoSpeedPlayView.setPlayerDialogCallback(new CustomVideoSpeedPlayView.VideoSpeedPlayCallback() { // from class: com.gen.mh.webapp_extensions.views.player.custom.HlwPlayerView.17
            @Override // com.gen.p059mh.webapp_extensions.views.player.custom.CustomVideoSpeedPlayView.VideoSpeedPlayCallback
            public void speedPlay(float f, float f2, String str, int i) {
                HlwPlayerView.this.onVideoSpeedPlay(f, f2, str);
                HlwPlayerView.this.mStatesListener.onSpeedChange(i);
                HlwPlayerView.this.playerDialogFrameLayout.dismissView();
            }
        });
        this.playerDialogFrameLayout.addContentView(customVideoSpeedPlayView);
        this.playerDialogFrameLayout.setCallback(new CustomPlayerDialogFrameLayout.Callback() { // from class: com.gen.mh.webapp_extensions.views.player.custom.HlwPlayerView.18
            @Override // com.gen.p059mh.webapp_extensions.views.player.custom.CustomPlayerDialogFrameLayout.Callback
            public void showControlWidget() {
                HlwPlayerView.this.onClickUiToggle();
            }
        });
        this.playerDialogFrameLayout.setCanceledOnTouchOutside(true);
        this.playerDialogFrameLayout.show();
    }

    public void resetCollectState(boolean z) {
        this.videoInfo.setLike(z);
        if (this.playerDialogFrameLayout.getContentView() == null || !(this.playerDialogFrameLayout.getContentView() instanceof CustomVideoSettingView) || !this.playerDialogFrameLayout.isShowing()) {
            return;
        }
        ((CustomVideoSettingView) this.playerDialogFrameLayout.getContentView()).collectChange(z);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void onVideoSpeedPlay(float f, float f2, String str) {
        super.onVideoSpeedPlay(f);
        this.tvSpeed.setText(str);
    }

    public int getEnlargeImageRes() {
        return R$mipmap.play_ic_screen_full;
    }

    public int getShrinkImageRes() {
        return R$mipmap.icon_player_closefull;
    }

    public void lockTouchLogic() {
        if (this.mLockCurScreen) {
            this.mLockScreen.setImageResource(R$mipmap.playpage_ic_open);
            this.mLockCurScreen = false;
        } else {
            this.mLockScreen.setImageResource(R$mipmap.playpage_ic_locking);
            this.mLockCurScreen = true;
            hideAllWidget();
        }
        setLockLand(this.mLockCurScreen);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setPreViewList(String str, List list, int i) {
        ((TextView) this.mPreviewLayout.findViewById(R$id.tv_preview_time)).setText(CommonUtil.stringForTime(i));
        PreviewUtils.startPreView(str, list, (ImageView) this.mPreviewLayout.findViewById(R$id.preview_image));
    }

    public void showResolution() {
        CustomClarityChoiceView customClarityChoiceView = new CustomClarityChoiceView(this.mContext, this.resolutionPosition);
        customClarityChoiceView.setData(this.resourceList);
        customClarityChoiceView.setPlayerDialogCallback(new CustomClarityChoiceView.ClarityChoiceViewCallback() { // from class: com.gen.mh.webapp_extensions.views.player.custom.HlwPlayerView.22
            @Override // com.gen.p059mh.webapp_extensions.views.player.custom.CustomClarityChoiceView.ClarityChoiceViewCallback
            public void onChoiceClarity(int i) {
                ResourceEntity resourceEntity = HlwPlayerView.this.resourceList.get(i);
                if (resourceEntity.getResolution() != HlwPlayerView.this.resolutionPosition) {
                    if (StringUtils.isNotEmpty(resourceEntity.getUrl()) && !resourceEntity.isCallback()) {
                        HlwPlayerView.this.clarityOnClickBack(resourceEntity.getResolution());
                        return;
                    }
                    PlayerStatesListener playerStatesListener = HlwPlayerView.this.mStatesListener;
                    if (playerStatesListener == null) {
                        return;
                    }
                    playerStatesListener.onClarityClick(resourceEntity);
                }
            }
        });
        this.playerDialogFrameLayout.addContentView(customClarityChoiceView);
        this.playerDialogFrameLayout.hasNoTitle().setCanceledOnTouchOutside(true).show();
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void clarityOnClickBack(int i) {
        if (this.playerDialogFrameLayout.getContentView() instanceof CustomClarityChoiceView) {
            this.playerDialogFrameLayout.dismissView();
        }
        if (this.mStatesListener != null) {
            this.resolutionPosition = i;
            if (getCurrentPositionWhenPlaying() != 0) {
                this.currentPosition = getCurrentPositionWhenPlaying();
            }
            onVideoReset();
            setUp(this.mStatesListener.provideResource(i), this.mCache, this.mCachePath, this.mTitle);
            setSeekOnStart(this.currentPosition);
            startPlayLogic();
            ResourceEntity resourceByResolution = ResolutionUtils.getResourceByResolution(this.resourceList, i);
            this.tvResolution.setText(resourceByResolution.getName());
            ToastUtils.showShort(String.format("已成功切换到%s", resourceByResolution.getName()));
            this.mStatesListener.onClarityChange(i);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void onFullScreen() {
        int i = 0;
        this.isMiniSize = false;
        setViewShowState(this.mLockScreen, 0);
        setViewShowState(this.mFullscreenButton, 8);
        setViewShowState(this.ivLogo, 0);
        if (getCurrentState() != 5 || !this.mStartButton.isSelected()) {
            i = 8;
        }
        updateAdsState(i);
        if (this.ivOtherAdsClose.isSelected()) {
            updateOtherAds(this.rlAds.getVisibility());
        }
        getBackButton().setImageResource(R$drawable.video_back);
        this.ivBack.setImageResource(R$drawable.video_back);
        this.playerDialogFrameLayout.displayInScreenOrientation(true);
        showTopInfo(!this.verticalScreen);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void release() {
        super.release();
        if (this.mThumbImageViewLayout != null) {
            this.mThumbImageViewLayout.removeAllViews();
        }
        RelativeLayout relativeLayout = this.rlAds;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        VideoPhoneView videoPhoneView = this.videoPhoneView;
        if (videoPhoneView == null) {
            return;
        }
        videoPhoneView.destroyView();
        throw null;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void quiteFullScreen() {
        this.isMiniSize = false;
        setViewShowState(this.mFullscreenButton, 0);
        setViewShowState(this.ivLogo, 0);
        updateAdsState((getCurrentState() != 5 || !this.mStartButton.isSelected()) ? 8 : 0);
        if (this.ivOtherAdsClose.isSelected()) {
            updateOtherAds(this.rlOtherAds.getVisibility());
        }
        getBackButton().setImageResource(R$drawable.video_back);
        this.ivBack.setImageResource(R$drawable.video_back);
        setViewShowState(this.mLockScreen, 8);
        showTopInfo(false);
        this.mFullscreenButton.setImageResource(getEnlargeImageRes());
        this.playerDialogFrameLayout.displayInScreenOrientation(false);
        if (this.isNoVip || this.isNoNetWork) {
            setViewShowState(this.rlError, 0);
            setViewShowState(this.ivLogo, 8);
        }
    }

    public void showTopInfo(boolean z) {
        int i = 0;
        this.titleLayout.setVisibility(z ? 0 : 8);
        this.phoneInfoLayout.setVisibility(z ? 0 : 8);
        this.imgSetting.setVisibility((z || this.isLocalMode) ? 0 : 8);
        ImageView imageView = this.imgError;
        if (z || this.isLocalMode) {
            i = 8;
        }
        imageView.setVisibility(i);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setIfCurrentIsFullscreen(boolean z) {
        super.setIfCurrentIsFullscreen(z);
        this.mFullscreenButton.setVisibility(z ? 8 : 0);
        int dpToPixel = (int) DeviceUtils.dpToPixel(getContext(), 30.0f);
        if (z) {
            getFullscreenButton().setImageResource(getShrinkImageRes());
            if (this.verticalScreen) {
                return;
            }
            this.rlLeft.setPadding(dpToPixel, 0, 0, 0);
            this.rlRight.setPadding(0, 0, dpToPixel, 0);
            return;
        }
        getFullscreenButton().setImageResource(getEnlargeImageRes());
        this.rlLeft.setPadding(0, 0, 0, 0);
        this.rlRight.setPadding(0, 0, 0, 0);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void startClick(View.OnClickListener onClickListener) {
        getFullscreenButton().setOnClickListener(onClickListener);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void hideAllWidget() {
        super.hideAllWidget();
        setViewShowState(this.tvVolume, 4);
        if (this.isMiniSize) {
            setViewShowState(this.ivLogo, 8);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void onClickUiToggle() {
        if (!this.isMiniSize) {
            super.onClickUiToggle();
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public int getCurPosition() {
        if (!isInPlayingState()) {
            return -1;
        }
        if (this.hasHeaderAdPlay || getCurrentState() == 6) {
            return 0;
        }
        return getCurrentPositionWhenPlaying();
    }

    public void updateOtherAds(int i) {
        if (!this.otherAdsModel) {
            return;
        }
        if (i == 0) {
            if (this.ivOtherAds.getDrawable() == null) {
                try {
                    SelectionSpec.getInstance().imageEngine.load(getContext(), this.ivOtherAds, this.otherAdsUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            PlayerAdsSizeLayoutUtils.onLayoutOtherAds(isIfCurrentIsFullscreen(), getContext(), this.rlOtherAds);
        }
        setViewShowState(this.rlOtherAds, i);
    }

    public void updateAdsState(int i) {
        if (!this.adModel) {
            return;
        }
        if (i == 0) {
            if (this.rlOtherAds.getVisibility() == 0) {
                updateOtherAds(8);
            }
            if (this.ivAds.getDrawable() == null) {
                try {
                    SelectionSpec.getInstance().imageEngine.load(getContext(), this.ivAds, this.adsUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            PlayerAdsSizeLayoutUtils.onLayout(isIfCurrentIsFullscreen(), this.verticalScreen, getContext(), this.rlAds);
        } else if (this.isShowAds && getCurrentState() == 2) {
            updateOtherAds(0);
        }
        setViewShowState(this.rlAds, i);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setStatusListener(PlayerStatesListener playerStatesListener) {
        this.mStatesListener = playerStatesListener;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setResolutions(List<ResourceEntity> list, String str, File file) {
        setResourcesList(list, str, file);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void toggleLock() {
        lockTouchLogic();
    }

    public int getPlayResolution() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i = 1;
        while (true) {
            if (i <= 4) {
                for (int i2 = 0; i2 < this.resourceList.size(); i2++) {
                    int resolution = this.resourceList.get(i2).getResolution();
                    if (resolution == i && StringUtils.isNotEmpty(this.resourceList.get(i2).getUrl())) {
                        int i3 = this.resolutionPosition;
                        if (resolution < i3) {
                            arrayList.add(Integer.valueOf(i));
                        } else if (resolution <= i3) {
                            return i3;
                        } else {
                            arrayList2.add(Integer.valueOf(i));
                        }
                    }
                }
                i++;
            } else if (!arrayList.isEmpty()) {
                this.resolutionPosition = ((Integer) arrayList.get(arrayList.size() - 1)).intValue();
                return this.resolutionPosition;
            } else if (!arrayList2.isEmpty()) {
                this.resolutionPosition = ((Integer) arrayList2.get(0)).intValue();
                return this.resolutionPosition;
            } else {
                if (!this.isLocalMode) {
                    ToastUtils.showShort("获取分辨率出错，请重试");
                }
                return 0;
            }
        }
    }

    public void setResourcesList(List<ResourceEntity> list, String str, File file) {
        int playResolution;
        setTitle(str);
        resetParams();
        this.resourceList = list;
        if (this.resourceList.size() <= 0 || (playResolution = getPlayResolution()) == 0) {
            return;
        }
        playUrl(this.mStatesListener.provideResource(playResolution), true, file);
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                break;
            } else if (list.get(i).getResolution() == playResolution) {
                this.tvResolution.setText(list.get(i).getName());
                break;
            } else {
                i++;
            }
        }
        this.ivOtherAdsClose.setSelected(true);
        this.isNoVip = false;
        onNormal();
        this.tvResolution.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.views.player.custom.HlwPlayerView.24
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                HlwPlayerView hlwPlayerView = HlwPlayerView.this;
                if (hlwPlayerView.mStatesListener == null || hlwPlayerView.isLocalMode) {
                    return;
                }
                hlwPlayerView.showResolution();
            }
        });
    }

    public void playUrl(String str, boolean z, File file) {
        if (StringUtils.isNotEmpty(this.headerVodUrl) && this.lastPosition == 0) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.headerVodUrl);
            arrayList.add(str);
            setupTwoMovie(arrayList, z, file);
            return;
        }
        setUp(str, z, file, "");
        startPlayLogic();
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public boolean setUp(String str, boolean z, String str2) {
        this.ivOtherAdsClose.setSelected(true);
        return super.setUp(str, z, str2);
    }

    public void resetParams() {
        this.isNoVip = false;
        this.isNoNetWork = false;
        onNormal();
        updateOtherAds(8);
        onVideoReset();
        showHeaderAdUI(true);
        GSYVideoType.setShowType(0);
        this.playerDialogFrameLayout.dismissView(false);
        this.mAudioManager.getStreamVolume(3);
        this.currentPosition = 0L;
        this.isShowAds = false;
    }

    public void setTitle(String str) {
        TextView textView = (TextView) this.titleLayout.findViewById(R$id.tv_video_title);
        textView.setText(str);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSingleLine(true);
        textView.setSelected(true);
    }

    public void onNormal() {
        setViewShowState(this.llNoVip, 8);
        setViewShowState(this.llNoNetwork, 8);
        setViewShowState(this.rlError, 8);
        if (!this.isMiniSize) {
            setViewShowState(this.ivLogo, 0);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setAdsUrl(String str) {
        this.adModel = true;
        this.adsUrl = str;
        ((RoundCornerImageView) this.ivAds).setRadiusDp(6.0f);
        try {
            SelectionSpec.getInstance().imageEngine.load(this.mContext, this.ivAds, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setOtherAdsUrl(String str, long j) {
        this.otherAdsModel = true;
        this.otherAdsUrl = str;
        ((RoundCornerImageView) this.ivOtherAds).setRadiusDp(6.0f);
        try {
            SelectionSpec.getInstance().imageEngine.load(this.mContext, this.ivOtherAds, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setCollection(boolean z) {
        resetCollectState(z);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.player.IPlayer
    public void setHeaderVodUrl(String str) {
        this.headerVodUrl = str;
    }

    public void showHeaderAdUI(boolean z) {
        this.layoutSkip.setVisibility(4);
        int i = 0;
        this.layoutProgress.setVisibility(!z ? 0 : 8);
        this.ivSeekBack.setVisibility(!z ? 0 : 8);
        this.ivSeekTo.setVisibility(!z ? 0 : 8);
        this.tvSpeed.setVisibility(!z ? 0 : 8);
        TextView textView = this.tvResolution;
        if (z) {
            i = 8;
        }
        textView.setVisibility(i);
    }
}
