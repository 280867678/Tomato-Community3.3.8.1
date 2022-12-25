package com.one.tomato.thirdpart.video.controller;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.broccoli.p150bh.R;
import com.dueeeke.videoplayer.player.BaseIjkVideoView;
import com.dueeeke.videoplayer.util.WindowUtil;
import com.one.tomato.thirdpart.domain.DomainRequest;
import com.one.tomato.utils.BaseToTVideoController;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.widget.MarqueeTextView;
import java.util.Map;

/* loaded from: classes3.dex */
public class VideoDownloadController extends BaseToTVideoController implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private Activity activity;
    protected ImageView backButton;
    protected LinearLayout bottomContainer;
    private ProgressBar bottomProgress;
    private LinearLayout completeContainer;
    protected TextView currTime;
    protected ImageView fullScreenButton;
    private boolean isDragging;
    private LinearLayout ll_loading;
    private LinearLayout ll_play;
    protected ImageView lock;
    private String marqueeTitle;
    public OnProgressListener onProgressListener;
    private ImageView playButton;
    protected ImageView refresh;
    private TextView sysTime;
    private ImageView thumb;
    protected MarqueeTextView title;
    protected LinearLayout topContainer;
    protected TextView totalTime;
    protected SeekBar videoProgress;
    private Animation showAnim = AnimationUtils.loadAnimation(getContext(), R.anim.dkplayer_anim_alpha_in);
    private Animation hideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.dkplayer_anim_alpha_out);

    /* loaded from: classes3.dex */
    public interface OnProgressListener {
        void onProgress(int i);
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    protected int getLayoutId() {
        return R.layout.layout_videodown_controller;
    }

    @Override // com.one.tomato.utils.BaseToTVideoController
    public Map<String, Object> getVideoErrorInfo() {
        return null;
    }

    @Override // com.dueeeke.videoplayer.controller.GestureVideoController
    protected void slideToChangeBrightness(float f) {
    }

    @Override // com.dueeeke.videoplayer.controller.GestureVideoController
    protected void slideToChangeVolume(float f) {
    }

    public VideoDownloadController(@NonNull Context context) {
        super(context);
        this.activity = (Activity) context;
    }

    public VideoDownloadController(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VideoDownloadController(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.controller.GestureVideoController, com.dueeeke.videoplayer.controller.BaseVideoController
    public void initView() {
        super.initView();
        this.fullScreenButton = (ImageView) this.mControllerView.findViewById(R.id.fullscreen);
        this.fullScreenButton.setOnClickListener(this);
        this.bottomContainer = (LinearLayout) this.mControllerView.findViewById(R.id.bottom_container);
        this.topContainer = (LinearLayout) this.mControllerView.findViewById(R.id.top_container);
        this.videoProgress = (SeekBar) this.mControllerView.findViewById(R.id.seekBar);
        this.videoProgress.setOnSeekBarChangeListener(this);
        this.totalTime = (TextView) this.mControllerView.findViewById(R.id.total_time);
        this.currTime = (TextView) this.mControllerView.findViewById(R.id.curr_time);
        this.backButton = (ImageView) this.mControllerView.findViewById(R.id.back);
        this.backButton.setOnClickListener(this);
        this.lock = (ImageView) this.mControllerView.findViewById(R.id.lock);
        this.lock.setOnClickListener(this);
        this.thumb = (ImageView) this.mControllerView.findViewById(R.id.thumb);
        this.playButton = (ImageView) this.mControllerView.findViewById(R.id.iv_play);
        this.playButton.setOnClickListener(this);
        this.ll_play = (LinearLayout) this.mControllerView.findViewById(R.id.ll_play);
        this.ll_play.setOnClickListener(this);
        this.bottomProgress = (ProgressBar) this.mControllerView.findViewById(R.id.bottom_progress);
        ((ImageView) this.mControllerView.findViewById(R.id.iv_replay)).setOnClickListener(this);
        this.completeContainer = (LinearLayout) this.mControllerView.findViewById(R.id.complete_container);
        this.completeContainer.setOnClickListener(this);
        this.title = (MarqueeTextView) this.mControllerView.findViewById(R.id.title);
        this.sysTime = (TextView) this.mControllerView.findViewById(R.id.sys_time);
        this.refresh = (ImageView) this.mControllerView.findViewById(R.id.iv_refresh);
        this.refresh.setOnClickListener(this);
        this.ll_loading = (LinearLayout) this.mControllerView.findViewById(R.id.ll_loading);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.controller.BaseVideoController, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.controller.BaseVideoController, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        show(1000);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back /* 2131296336 */:
                doStartStopFullScreen(1);
                return;
            case R.id.fullscreen /* 2131296855 */:
                doStartStopFullScreen(2);
                return;
            case R.id.iv_play /* 2131297296 */:
                doPauseResume();
                return;
            case R.id.iv_replay /* 2131297335 */:
                this.mMediaPlayer.replay(true);
                return;
            case R.id.ll_play /* 2131297634 */:
                this.mMediaPlayer.start();
                return;
            case R.id.lock /* 2131297711 */:
                doLockUnlock();
                return;
            default:
                return;
        }
    }

    public void showTitle() {
        this.title.setVisibility(0);
    }

    private void doStartStopFullScreen(int i) {
        if (!this.mMediaPlayer.isFullScreen()) {
            if (i == 1) {
                this.activity.onBackPressed();
                return;
            }
            WindowUtil.scanForActivity(this.activity).setRequestedOrientation(0);
            this.mMediaPlayer.startFullScreen();
            this.fullScreenButton.setSelected(true);
        } else if (WindowUtil.scanForActivity(this.activity).getRequestedOrientation() == 0) {
            WindowUtil.scanForActivity(this.activity).setRequestedOrientation(1);
            this.mMediaPlayer.stopFullScreen();
            this.fullScreenButton.setSelected(false);
        } else if (i == 1) {
            this.activity.onBackPressed();
        } else {
            WindowUtil.scanForActivity(this.activity).setRequestedOrientation(0);
            this.mMediaPlayer.startFullScreen();
            this.fullScreenButton.setSelected(true);
        }
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void setPlayerState(int i) {
        if (i == 10) {
            LogUtil.m3786e("PLAYER_NORMAL");
            if (this.mIsLocked) {
                return;
            }
            setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            this.mIsGestureEnabled = false;
            this.fullScreenButton.setSelected(false);
            this.lock.setVisibility(8);
            this.title.setVisibility(0);
            this.sysTime.setVisibility(8);
            this.topContainer.setVisibility(0);
            this.backButton.setVisibility(0);
        } else if (i != 11) {
        } else {
            LogUtil.m3786e("PLAYER_FULL_SCREEN");
            if (this.mIsLocked) {
                return;
            }
            this.mIsGestureEnabled = true;
            this.fullScreenButton.setSelected(true);
            this.backButton.setVisibility(0);
            this.title.setVisibility(0);
            this.sysTime.setVisibility(8);
            if (this.mShowing) {
                this.lock.setVisibility(0);
                this.topContainer.setVisibility(0);
                return;
            }
            this.lock.setVisibility(8);
        }
    }

    @Override // com.one.tomato.utils.BaseToTVideoController, com.dueeeke.videoplayer.controller.BaseVideoController
    public void setPlayState(int i) {
        super.setPlayState(i);
        switch (i) {
            case -1:
                DomainRequest.getInstance().switchDomainUrlByType("ttViewVideoNew");
                this.thumb.setVisibility(8);
                this.ll_loading.setVisibility(8);
                this.ll_play.setVisibility(8);
                this.completeContainer.setVisibility(8);
                this.topContainer.setVisibility(0);
                this.backButton.setVisibility(0);
                this.bottomContainer.setVisibility(8);
                this.bottomProgress.setVisibility(8);
                return;
            case 0:
                hide();
                this.virtualConsume = true;
                this.mIsGestureEnabled = true;
                this.mIsLocked = false;
                this.lock.setSelected(false);
                this.mMediaPlayer.setLock(false);
                this.bottomProgress.setProgress(0);
                this.bottomProgress.setSecondaryProgress(0);
                this.videoProgress.setProgress(0);
                this.videoProgress.setSecondaryProgress(0);
                this.thumb.setVisibility(0);
                this.ll_play.setVisibility(8);
                this.ll_loading.setVisibility(8);
                this.completeContainer.setVisibility(8);
                this.topContainer.setVisibility(0);
                this.backButton.setVisibility(0);
                this.bottomContainer.setVisibility(8);
                this.bottomProgress.setVisibility(8);
                return;
            case 1:
                hide();
                this.virtualConsume = true;
                this.mIsGestureEnabled = true;
                this.mIsLocked = false;
                this.lock.setSelected(false);
                this.mMediaPlayer.setLock(false);
                this.bottomProgress.setProgress(0);
                this.bottomProgress.setSecondaryProgress(0);
                this.videoProgress.setProgress(0);
                this.videoProgress.setSecondaryProgress(0);
                this.thumb.setVisibility(0);
                this.ll_play.setVisibility(8);
                this.ll_loading.setVisibility(0);
                this.completeContainer.setVisibility(8);
                this.topContainer.setVisibility(0);
                this.backButton.setVisibility(0);
                this.bottomContainer.setVisibility(8);
                this.bottomProgress.setVisibility(8);
                return;
            case 2:
                this.ll_loading.setVisibility(8);
                return;
            case 3:
                post(this.mShowProgress);
                this.thumb.setVisibility(8);
                this.ll_play.setVisibility(8);
                this.playButton.setSelected(true);
                this.bottomProgress.setVisibility(0);
                return;
            case 4:
                this.ll_play.setVisibility(0);
                this.backButton.setVisibility(0);
                this.playButton.setSelected(false);
                return;
            case 5:
                hide();
                removeCallbacks(this.mShowProgress);
                this.bottomProgress.setProgress(0);
                this.bottomProgress.setSecondaryProgress(0);
                this.mMediaPlayer.setLock(false);
                if (this.mIsLocked) {
                    this.mShowing = false;
                    this.lock.setSelected(false);
                }
                this.mIsLocked = false;
                this.completeContainer.setVisibility(0);
                this.topContainer.setVisibility(0);
                this.backButton.setVisibility(0);
                this.playButton.setSelected(false);
                this.bottomProgress.setVisibility(8);
                return;
            case 6:
                this.ll_loading.setVisibility(0);
                this.ll_play.setVisibility(8);
                return;
            case 7:
                this.ll_loading.setVisibility(8);
                return;
            default:
                return;
        }
    }

    protected void doLockUnlock() {
        if (this.mIsLocked) {
            this.mIsLocked = false;
            this.mShowing = false;
            this.mIsGestureEnabled = true;
            show();
            this.lock.setSelected(false);
            Toast.makeText(getContext(), (int) R.string.dkplayer_unlocked, 0).show();
        } else {
            hide();
            this.mIsLocked = true;
            this.mIsGestureEnabled = false;
            this.lock.setSelected(true);
            Toast.makeText(getContext(), (int) R.string.dkplayer_locked, 0).show();
        }
        this.mMediaPlayer.setLock(this.mIsLocked);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        this.isDragging = true;
        removeCallbacks(this.mShowProgress);
        removeCallbacks(this.mFadeOut);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        this.mMediaPlayer.seekTo((int) ((this.mMediaPlayer.getDuration() * seekBar.getProgress()) / this.videoProgress.getMax()));
        this.isDragging = false;
        post(this.mShowProgress);
        show();
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (!z) {
            return;
        }
        long duration = (this.mMediaPlayer.getDuration() * i) / this.videoProgress.getMax();
        TextView textView = this.currTime;
        if (textView != null) {
            textView.setText(stringForTime((int) duration));
        }
        OnProgressListener onProgressListener = this.onProgressListener;
        if (onProgressListener == null) {
            return;
        }
        onProgressListener.onProgress((int) duration);
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void hide() {
        if (this.mShowing) {
            if (this.mMediaPlayer.isFullScreen()) {
                this.lock.setVisibility(8);
                if (!this.mIsLocked) {
                    if (this.mMediaPlayer.isPlaying()) {
                        hideAllViews();
                    } else {
                        this.bottomContainer.setVisibility(8);
                    }
                }
            } else if (this.mMediaPlayer.isPlaying()) {
                this.bottomContainer.setVisibility(8);
                this.bottomContainer.startAnimation(this.hideAnim);
                this.bottomProgress.setVisibility(0);
            } else {
                this.topContainer.setVisibility(0);
                this.backButton.setVisibility(0);
            }
            this.mShowing = false;
        }
    }

    private String getTitle() {
        return TextUtils.isEmpty(this.marqueeTitle) ? "" : this.marqueeTitle;
    }

    public void setTitle(String str) {
        this.marqueeTitle = str;
    }

    private void hideAllViews() {
        this.topContainer.setVisibility(8);
        this.topContainer.startAnimation(this.hideAnim);
        this.bottomContainer.setVisibility(8);
        this.bottomContainer.startAnimation(this.hideAnim);
        this.ll_play.setVisibility(8);
        this.bottomProgress.setVisibility(0);
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void show() {
        show(this.mDefaultTimeout);
    }

    private void show(int i) {
        TextView textView = this.sysTime;
        if (textView != null) {
            textView.setText(getCurrentSystemTime());
        }
        if (!this.mShowing) {
            if (this.mMediaPlayer.isFullScreen()) {
                this.lock.setVisibility(0);
                if (!this.mIsLocked) {
                    if (this.mMediaPlayer.isPlaying()) {
                        showAllViews();
                    } else {
                        this.topContainer.setVisibility(0);
                    }
                }
            } else {
                if (this.mMediaPlayer.isPlaying()) {
                    this.bottomContainer.setVisibility(0);
                    this.bottomContainer.startAnimation(this.showAnim);
                    this.bottomProgress.setVisibility(8);
                }
                this.topContainer.setVisibility(0);
            }
            this.mShowing = true;
        }
        removeCallbacks(this.mFadeOut);
        if (i != 0) {
            postDelayed(this.mFadeOut, i);
        }
    }

    private void showAllViews() {
        this.bottomContainer.setVisibility(0);
        this.bottomContainer.startAnimation(this.showAnim);
        this.topContainer.setVisibility(0);
        this.topContainer.startAnimation(this.showAnim);
        this.bottomProgress.setVisibility(8);
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    protected int setProgress() {
        OnProgressListener onProgressListener;
        if (this.mMediaPlayer == null || this.isDragging) {
            return 0;
        }
        MarqueeTextView marqueeTextView = this.title;
        if (marqueeTextView != null && TextUtils.isEmpty(marqueeTextView.getText())) {
            this.title.setText(getTitle());
        }
        int currentPosition = (int) this.mMediaPlayer.getCurrentPosition();
        int duration = (int) this.mMediaPlayer.getDuration();
        if (((BaseIjkVideoView) this.mMediaPlayer).getCurrentPlayState() == 3 && (onProgressListener = this.onProgressListener) != null) {
            onProgressListener.onProgress(currentPosition);
        }
        SeekBar seekBar = this.videoProgress;
        if (seekBar != null) {
            if (duration > 0) {
                seekBar.setEnabled(true);
                int max = (int) (((currentPosition * 1.0d) / duration) * this.videoProgress.getMax());
                this.videoProgress.setProgress(max);
                this.bottomProgress.setProgress(max);
            } else {
                seekBar.setEnabled(false);
            }
            int bufferPercentage = ((BaseIjkVideoView) this.mMediaPlayer).getBufferPercentage();
            if (bufferPercentage >= 95) {
                SeekBar seekBar2 = this.videoProgress;
                seekBar2.setSecondaryProgress(seekBar2.getMax());
                ProgressBar progressBar = this.bottomProgress;
                progressBar.setSecondaryProgress(progressBar.getMax());
            } else {
                int i = bufferPercentage * 10;
                this.videoProgress.setSecondaryProgress(i);
                this.bottomProgress.setSecondaryProgress(i);
            }
        }
        TextView textView = this.totalTime;
        if (textView != null) {
            textView.setText(stringForTime(duration));
        }
        TextView textView2 = this.currTime;
        if (textView2 != null) {
            textView2.setText(stringForTime(currentPosition));
        }
        return currentPosition;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.controller.GestureVideoController
    public void slideToChangePosition(float f) {
        super.slideToChangePosition(f);
    }

    public ImageView getThumb() {
        return this.thumb;
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public boolean onBackPressed() {
        if (this.mIsLocked) {
            show();
            Toast.makeText(getContext(), (int) R.string.dkplayer_lock_tip, 0).show();
            return true;
        } else if (!this.mMediaPlayer.isFullScreen()) {
            return false;
        } else {
            if (WindowUtil.scanForActivity(this.activity).getRequestedOrientation() == 0) {
                WindowUtil.scanForActivity(this.activity).setRequestedOrientation(1);
                this.mMediaPlayer.stopFullScreen();
                this.fullScreenButton.setSelected(false);
                return true;
            }
            this.mMediaPlayer.stopFullScreen();
            this.fullScreenButton.setSelected(false);
            return true;
        }
    }

    @Override // com.one.tomato.utils.BaseToTVideoController
    public boolean isReplay() {
        setPlayState(5);
        setKeepScreenOn(false);
        ((BaseIjkVideoView) this.mMediaPlayer).onPlayStopped();
        return false;
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }
}
