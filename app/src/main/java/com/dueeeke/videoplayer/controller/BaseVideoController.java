package com.dueeeke.videoplayer.controller;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.dueeeke.videoplayer.C1228R;
import com.dueeeke.videoplayer.player.BaseIjkVideoView;
import com.dueeeke.videoplayer.util.PlayerUtils;
import com.dueeeke.videoplayer.widget.StatusView;
import com.zzz.ipfssdk.callback.exception.CodeState;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/* loaded from: classes2.dex */
public abstract class BaseVideoController extends FrameLayout {
    protected FullScreamCallBack fullScreamCallBack;
    protected View mControllerView;
    protected int mCurrentPlayState;
    protected int mDefaultTimeout;
    protected final Runnable mFadeOut;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    protected boolean mIsLocked;
    protected MediaPlayerControl mMediaPlayer;
    protected Runnable mShowProgress;
    protected boolean mShowing;
    protected StatusView mStatusView;

    /* loaded from: classes2.dex */
    public interface FullScreamCallBack {
        void fullCallBack(boolean z);
    }

    protected abstract int getLayoutId();

    public void hide() {
    }

    public boolean onBackPressed() {
        return false;
    }

    public void setPlayerState(int i) {
    }

    protected int setProgress() {
        return 0;
    }

    public void show() {
    }

    public BaseVideoController(@NonNull Context context) {
        this(context, null);
    }

    public BaseVideoController(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseVideoController(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        super(context, attributeSet, i);
        this.mDefaultTimeout = CodeState.CODES.CODE_PLAYER_PLAY_ERROR;
        this.mShowProgress = new Runnable() { // from class: com.dueeeke.videoplayer.controller.BaseVideoController.3
            @Override // java.lang.Runnable
            public void run() {
                int progress = BaseVideoController.this.setProgress();
                if (BaseVideoController.this.mMediaPlayer.isPlaying()) {
                    BaseVideoController baseVideoController = BaseVideoController.this;
                    baseVideoController.postDelayed(baseVideoController.mShowProgress, 1000 - (progress % 1000));
                }
            }
        };
        this.mFadeOut = new Runnable() { // from class: com.dueeeke.videoplayer.controller.BaseVideoController.4
            @Override // java.lang.Runnable
            public void run() {
                BaseVideoController.this.hide();
            }
        };
        initView();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initView() {
        this.mControllerView = LayoutInflater.from(getContext()).inflate(getLayoutId(), this);
        this.mFormatBuilder = new StringBuilder();
        this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
        this.mStatusView = new StatusView(getContext());
        setClickable(true);
        setFocusable(true);
    }

    public void setPlayState(int i) {
        this.mCurrentPlayState = i;
        hideStatusView();
        if (i != -1) {
            return;
        }
        this.mStatusView.setMessage(getResources().getString(C1228R.string.dkplayer_error_message));
        this.mStatusView.setButtonTextAndAction(getResources().getString(C1228R.string.dkplayer_retry), new View.OnClickListener() { // from class: com.dueeeke.videoplayer.controller.BaseVideoController.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BaseVideoController.this.hideStatusView();
                BaseVideoController.this.mMediaPlayer.replay(false);
            }
        });
        addView(this.mStatusView, 0);
    }

    public void showStatusView() {
        removeView(this.mStatusView);
        this.mStatusView.setMessage(getResources().getString(C1228R.string.dkplayer_wifi_tip));
        this.mStatusView.setButtonTextAndAction(getResources().getString(C1228R.string.dkplayer_continue_play), new View.OnClickListener() { // from class: com.dueeeke.videoplayer.controller.BaseVideoController.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BaseVideoController.this.hideStatusView();
                BaseIjkVideoView.IS_PLAY_ON_MOBILE_NETWORK = true;
                BaseVideoController.this.mMediaPlayer.start();
            }
        });
        addView(this.mStatusView);
    }

    public void hideStatusView() {
        removeView(this.mStatusView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void doPauseResume() {
        if (this.mCurrentPlayState == 6) {
            return;
        }
        if (this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
        } else {
            this.mMediaPlayer.start();
        }
    }

    public void setFullScreamCallBack(FullScreamCallBack fullScreamCallBack) {
        this.fullScreamCallBack = fullScreamCallBack;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void doStartStopFullScreen() {
        Activity scanForActivity = PlayerUtils.scanForActivity(getContext());
        if (scanForActivity == null) {
            return;
        }
        if (this.mMediaPlayer.isFullScreen()) {
            FullScreamCallBack fullScreamCallBack = this.fullScreamCallBack;
            if (fullScreamCallBack != null) {
                fullScreamCallBack.fullCallBack(false);
            }
            this.mMediaPlayer.stopFullScreen();
            scanForActivity.setRequestedOrientation(1);
            return;
        }
        scanForActivity.setRequestedOrientation(0);
        FullScreamCallBack fullScreamCallBack2 = this.fullScreamCallBack;
        if (fullScreamCallBack2 != null) {
            fullScreamCallBack2.fullCallBack(true);
        }
        this.mMediaPlayer.startFullScreen();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getCurrentSystemTime() {
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String stringForTime(int i) {
        int i2 = i / 1000;
        int i3 = i2 % 60;
        int i4 = (i2 / 60) % 60;
        int i5 = i2 / 3600;
        this.mFormatBuilder.setLength(0);
        return i5 > 0 ? this.mFormatter.format("%d:%02d:%02d", Integer.valueOf(i5), Integer.valueOf(i4), Integer.valueOf(i3)).toString() : this.mFormatter.format("%02d:%02d", Integer.valueOf(i4), Integer.valueOf(i3)).toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(this.mShowProgress);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mShowProgress);
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i == 0) {
            post(this.mShowProgress);
        }
    }

    public void setMediaPlayer(MediaPlayerControl mediaPlayerControl) {
        this.mMediaPlayer = mediaPlayerControl;
    }
}
