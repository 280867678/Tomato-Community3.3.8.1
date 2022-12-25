package com.dueeeke.videocontroller;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.dueeeke.videoplayer.controller.GestureVideoController;
import com.dueeeke.videoplayer.controller.MediaPlayerControl;
import com.dueeeke.videoplayer.util.C1252L;
import com.dueeeke.videoplayer.util.PlayerUtils;

/* loaded from: classes2.dex */
public class StandardVideoController extends GestureVideoController implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    protected ImageView mBackButton;
    private ImageView mBatteryLevel;
    private BatteryReceiver mBatteryReceiver;
    protected LinearLayout mBottomContainer;
    private ProgressBar mBottomProgress;
    private LinearLayout mCompleteContainer;
    protected TextView mCurrTime;
    protected ImageView mFullScreenButton;
    private Animation mHideAnim;
    private boolean mIsDragging;
    private boolean mIsLive;
    private ProgressBar mLoadingProgress;
    protected ImageView mLockButton;
    private ImageView mPlayButton;
    protected ImageView mRefreshButton;
    private Animation mShowAnim;
    private ImageView mStartPlayButton;
    private TextView mSysTime;
    private ImageView mThumb;
    protected MarqueeTextView mTitle;
    protected LinearLayout mTopContainer;
    protected TextView mTotalTime;
    protected SeekBar mVideoProgress;

    public StandardVideoController(@NonNull Context context) {
        this(context, null);
    }

    public StandardVideoController(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StandardVideoController(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        super(context, attributeSet, i);
        this.mShowAnim = AnimationUtils.loadAnimation(getContext(), R$anim.dkplayer_anim_alpha_in);
        this.mHideAnim = AnimationUtils.loadAnimation(getContext(), R$anim.dkplayer_anim_alpha_out);
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    protected int getLayoutId() {
        return R$layout.dkplayer_layout_standard_controller;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.controller.GestureVideoController, com.dueeeke.videoplayer.controller.BaseVideoController
    public void initView() {
        super.initView();
        this.mFullScreenButton = (ImageView) this.mControllerView.findViewById(R$id.fullscreen);
        this.mFullScreenButton.setOnClickListener(this);
        this.mBottomContainer = (LinearLayout) this.mControllerView.findViewById(R$id.bottom_container);
        this.mTopContainer = (LinearLayout) this.mControllerView.findViewById(R$id.top_container);
        this.mVideoProgress = (SeekBar) this.mControllerView.findViewById(R$id.seekBar);
        this.mVideoProgress.setOnSeekBarChangeListener(this);
        this.mTotalTime = (TextView) this.mControllerView.findViewById(R$id.total_time);
        this.mCurrTime = (TextView) this.mControllerView.findViewById(R$id.curr_time);
        this.mBackButton = (ImageView) this.mControllerView.findViewById(R$id.back);
        this.mBackButton.setOnClickListener(this);
        this.mLockButton = (ImageView) this.mControllerView.findViewById(R$id.lock);
        this.mLockButton.setOnClickListener(this);
        this.mThumb = (ImageView) this.mControllerView.findViewById(R$id.thumb);
        this.mThumb.setOnClickListener(this);
        this.mPlayButton = (ImageView) this.mControllerView.findViewById(R$id.iv_play);
        this.mPlayButton.setOnClickListener(this);
        this.mStartPlayButton = (ImageView) this.mControllerView.findViewById(R$id.start_play);
        this.mLoadingProgress = (ProgressBar) this.mControllerView.findViewById(R$id.loading);
        this.mBottomProgress = (ProgressBar) this.mControllerView.findViewById(R$id.bottom_progress);
        ((ImageView) this.mControllerView.findViewById(R$id.iv_replay)).setOnClickListener(this);
        this.mCompleteContainer = (LinearLayout) this.mControllerView.findViewById(R$id.complete_container);
        this.mCompleteContainer.setOnClickListener(this);
        this.mTitle = (MarqueeTextView) this.mControllerView.findViewById(R$id.title);
        this.mSysTime = (TextView) this.mControllerView.findViewById(R$id.sys_time);
        this.mBatteryLevel = (ImageView) this.mControllerView.findViewById(R$id.iv_battery);
        this.mBatteryReceiver = new BatteryReceiver(this.mBatteryLevel);
        this.mRefreshButton = (ImageView) this.mControllerView.findViewById(R$id.iv_refresh);
        this.mRefreshButton.setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.controller.BaseVideoController, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(this.mBatteryReceiver);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.controller.BaseVideoController, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getContext().registerReceiver(this.mBatteryReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R$id.fullscreen || id == R$id.back) {
            doStartStopFullScreen();
        } else if (id == R$id.lock) {
            doLockUnlock();
        } else if (id == R$id.iv_play || id == R$id.thumb) {
            doPauseResume();
        } else if (id != R$id.iv_replay && id != R$id.iv_refresh) {
        } else {
            this.mMediaPlayer.replay(true);
        }
    }

    public void setTitle(String str) {
        this.mTitle.setText(str);
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void setPlayerState(int i) {
        if (i == 10) {
            C1252L.m4171e("PLAYER_NORMAL");
            if (this.mIsLocked) {
                return;
            }
            setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            this.mIsGestureEnabled = false;
            this.mFullScreenButton.setSelected(false);
            this.mBackButton.setVisibility(8);
            this.mLockButton.setVisibility(8);
            this.mTitle.setVisibility(4);
            this.mSysTime.setVisibility(8);
            this.mBatteryLevel.setVisibility(8);
            this.mTopContainer.setVisibility(8);
        } else if (i != 11) {
        } else {
            C1252L.m4171e("PLAYER_FULL_SCREEN");
            if (this.mIsLocked) {
                return;
            }
            this.mIsGestureEnabled = true;
            this.mFullScreenButton.setSelected(true);
            this.mBackButton.setVisibility(0);
            this.mTitle.setVisibility(0);
            this.mSysTime.setVisibility(0);
            this.mBatteryLevel.setVisibility(0);
            if (this.mShowing) {
                this.mLockButton.setVisibility(0);
                this.mTopContainer.setVisibility(0);
                return;
            }
            this.mLockButton.setVisibility(8);
        }
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void setPlayState(int i) {
        super.setPlayState(i);
        switch (i) {
            case -1:
                C1252L.m4171e("STATE_ERROR");
                this.mStartPlayButton.setVisibility(8);
                this.mLoadingProgress.setVisibility(8);
                this.mThumb.setVisibility(8);
                this.mBottomProgress.setVisibility(8);
                this.mTopContainer.setVisibility(8);
                return;
            case 0:
                C1252L.m4171e("STATE_IDLE");
                hide();
                this.mIsLocked = false;
                this.mLockButton.setSelected(false);
                this.mMediaPlayer.setLock(false);
                this.mBottomProgress.setProgress(0);
                this.mBottomProgress.setSecondaryProgress(0);
                this.mVideoProgress.setProgress(0);
                this.mVideoProgress.setSecondaryProgress(0);
                this.mCompleteContainer.setVisibility(8);
                this.mBottomProgress.setVisibility(8);
                this.mLoadingProgress.setVisibility(8);
                this.mStartPlayButton.setVisibility(0);
                this.mThumb.setVisibility(0);
                return;
            case 1:
                C1252L.m4171e("STATE_PREPARING");
                this.mCompleteContainer.setVisibility(8);
                this.mStartPlayButton.setVisibility(8);
                this.mLoadingProgress.setVisibility(0);
                return;
            case 2:
                C1252L.m4171e("STATE_PREPARED");
                if (!this.mIsLive) {
                    this.mBottomProgress.setVisibility(0);
                }
                this.mStartPlayButton.setVisibility(8);
                return;
            case 3:
                C1252L.m4171e("STATE_PLAYING");
                post(this.mShowProgress);
                this.mPlayButton.setSelected(true);
                this.mLoadingProgress.setVisibility(8);
                this.mCompleteContainer.setVisibility(8);
                this.mThumb.setVisibility(8);
                this.mStartPlayButton.setVisibility(8);
                return;
            case 4:
                C1252L.m4171e("STATE_PAUSED");
                this.mPlayButton.setSelected(false);
                this.mStartPlayButton.setVisibility(8);
                return;
            case 5:
                C1252L.m4171e("STATE_PLAYBACK_COMPLETED");
                hide();
                removeCallbacks(this.mShowProgress);
                this.mStartPlayButton.setVisibility(8);
                this.mThumb.setVisibility(0);
                this.mCompleteContainer.setVisibility(0);
                this.mBottomProgress.setProgress(0);
                this.mBottomProgress.setSecondaryProgress(0);
                this.mIsLocked = false;
                this.mMediaPlayer.setLock(false);
                return;
            case 6:
                C1252L.m4171e("STATE_BUFFERING");
                this.mStartPlayButton.setVisibility(8);
                this.mLoadingProgress.setVisibility(0);
                this.mThumb.setVisibility(8);
                this.mPlayButton.setSelected(this.mMediaPlayer.isPlaying());
                return;
            case 7:
                C1252L.m4171e("STATE_BUFFERED");
                this.mLoadingProgress.setVisibility(8);
                this.mStartPlayButton.setVisibility(8);
                this.mThumb.setVisibility(8);
                this.mPlayButton.setSelected(this.mMediaPlayer.isPlaying());
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
            this.mLockButton.setSelected(false);
            Toast.makeText(getContext(), R$string.dkplayer_unlocked, 0).show();
        } else {
            hide();
            this.mIsLocked = true;
            this.mIsGestureEnabled = false;
            this.mLockButton.setSelected(true);
            Toast.makeText(getContext(), R$string.dkplayer_locked, 0).show();
        }
        this.mMediaPlayer.setLock(this.mIsLocked);
    }

    public void setLive() {
        this.mIsLive = true;
        this.mBottomProgress.setVisibility(8);
        this.mVideoProgress.setVisibility(4);
        this.mTotalTime.setVisibility(4);
        this.mCurrTime.setVisibility(4);
        this.mRefreshButton.setVisibility(0);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        this.mIsDragging = true;
        removeCallbacks(this.mShowProgress);
        removeCallbacks(this.mFadeOut);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        this.mMediaPlayer.seekTo((int) ((this.mMediaPlayer.getDuration() * seekBar.getProgress()) / this.mVideoProgress.getMax()));
        this.mIsDragging = false;
        post(this.mShowProgress);
        show();
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (!z) {
            return;
        }
        long duration = (this.mMediaPlayer.getDuration() * i) / this.mVideoProgress.getMax();
        TextView textView = this.mCurrTime;
        if (textView == null) {
            return;
        }
        textView.setText(stringForTime((int) duration));
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void hide() {
        if (this.mShowing) {
            if (this.mMediaPlayer.isFullScreen()) {
                this.mLockButton.setVisibility(8);
                if (!this.mIsLocked) {
                    hideAllViews();
                }
            } else {
                this.mBottomContainer.setVisibility(8);
                this.mBottomContainer.startAnimation(this.mHideAnim);
            }
            if (!this.mIsLive && !this.mIsLocked) {
                this.mBottomProgress.setVisibility(0);
                this.mBottomProgress.startAnimation(this.mShowAnim);
            }
            this.mShowing = false;
        }
    }

    private void hideAllViews() {
        this.mTopContainer.setVisibility(8);
        this.mTopContainer.startAnimation(this.mHideAnim);
        this.mBottomContainer.setVisibility(8);
        this.mBottomContainer.startAnimation(this.mHideAnim);
    }

    private void show(int i) {
        TextView textView = this.mSysTime;
        if (textView != null) {
            textView.setText(getCurrentSystemTime());
        }
        if (!this.mShowing) {
            if (this.mMediaPlayer.isFullScreen()) {
                this.mLockButton.setVisibility(0);
                if (!this.mIsLocked) {
                    showAllViews();
                }
            } else {
                this.mBottomContainer.setVisibility(0);
                this.mBottomContainer.startAnimation(this.mShowAnim);
            }
            if (!this.mIsLocked && !this.mIsLive) {
                this.mBottomProgress.setVisibility(8);
                this.mBottomProgress.startAnimation(this.mHideAnim);
            }
            this.mShowing = true;
        }
        removeCallbacks(this.mFadeOut);
        if (i != 0) {
            postDelayed(this.mFadeOut, i);
        }
    }

    private void showAllViews() {
        this.mBottomContainer.setVisibility(0);
        this.mBottomContainer.startAnimation(this.mShowAnim);
        this.mTopContainer.setVisibility(0);
        this.mTopContainer.startAnimation(this.mShowAnim);
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public void show() {
        show(this.mDefaultTimeout);
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    protected int setProgress() {
        MediaPlayerControl mediaPlayerControl = this.mMediaPlayer;
        if (mediaPlayerControl == null || this.mIsDragging || this.mIsLive) {
            return 0;
        }
        int currentPosition = (int) mediaPlayerControl.getCurrentPosition();
        int duration = (int) this.mMediaPlayer.getDuration();
        SeekBar seekBar = this.mVideoProgress;
        if (seekBar != null) {
            if (duration > 0) {
                seekBar.setEnabled(true);
                int max = (int) (((currentPosition * 1.0d) / duration) * this.mVideoProgress.getMax());
                this.mVideoProgress.setProgress(max);
                this.mBottomProgress.setProgress(max);
            } else {
                seekBar.setEnabled(false);
            }
            int bufferedPercentage = this.mMediaPlayer.getBufferedPercentage();
            if (bufferedPercentage >= 95) {
                SeekBar seekBar2 = this.mVideoProgress;
                seekBar2.setSecondaryProgress(seekBar2.getMax());
                ProgressBar progressBar = this.mBottomProgress;
                progressBar.setSecondaryProgress(progressBar.getMax());
            } else {
                int i = bufferedPercentage * 10;
                this.mVideoProgress.setSecondaryProgress(i);
                this.mBottomProgress.setSecondaryProgress(i);
            }
        }
        TextView textView = this.mTotalTime;
        if (textView != null) {
            textView.setText(stringForTime(duration));
        }
        TextView textView2 = this.mCurrTime;
        if (textView2 != null) {
            textView2.setText(stringForTime(currentPosition));
        }
        return currentPosition;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.controller.GestureVideoController
    public void slideToChangePosition(float f) {
        if (this.mIsLive) {
            this.mNeedSeek = false;
        } else {
            super.slideToChangePosition(f);
        }
    }

    public ImageView getThumb() {
        return this.mThumb;
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    public boolean onBackPressed() {
        if (this.mIsLocked) {
            show();
            Toast.makeText(getContext(), R$string.dkplayer_lock_tip, 0).show();
            return true;
        }
        Activity scanForActivity = PlayerUtils.scanForActivity(getContext());
        if (scanForActivity == null) {
            return super.onBackPressed();
        }
        if (this.mMediaPlayer.isFullScreen()) {
            scanForActivity.setRequestedOrientation(1);
            this.mMediaPlayer.stopFullScreen();
            return true;
        }
        return super.onBackPressed();
    }
}
