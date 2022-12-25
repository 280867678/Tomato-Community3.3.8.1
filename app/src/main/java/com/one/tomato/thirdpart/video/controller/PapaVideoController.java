package com.one.tomato.thirdpart.video.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.dueeeke.videoplayer.controller.MediaPlayerControl;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.dialog.VipPayDialog;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaVideoPlayFragment;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.thirdpart.domain.DomainRequest;
import com.one.tomato.thirdpart.video.widget.TouchFlower;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.BaseToTVideoController;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.widget.VideoLoadView;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class PapaVideoController extends BaseToTVideoController implements SeekBar.OnSeekBarChangeListener {
    private TextView curr_time;
    private int currentPostion = 0;
    private GestureDetector gestureDetector;
    private boolean isExceed;
    private ImageView iv_favor;
    private LinearLayout liner_papa_bar;
    private VideoLoadView loadView;
    private boolean mIsDragging;
    private OnFavorDoubleClickListener onFavorDoubleClickListener;
    public OnProgressListener onProgressListener;
    private NewPaPaVideoPlayFragment papaTabFragment;
    private PostList postList;
    private SeekBar seekBar;
    private ImageView start_play;
    private TextView text_look_complete;
    private ImageView thumb;
    private TextView total_time;
    private TouchFlower touchFlower;
    private TextView tv_favor_num;

    /* loaded from: classes3.dex */
    public interface OnFavorDoubleClickListener {
        boolean onFavourDoubleClick(PostList postList);
    }

    /* loaded from: classes3.dex */
    public interface OnProgressListener {
        void onProgress(long j, long j2);
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    protected int getLayoutId() {
        return R.layout.layout_papa_controller;
    }

    @Override // com.one.tomato.utils.BaseToTVideoController
    public boolean isReplay() {
        return true;
    }

    public void setShowPauseIcon(boolean z) {
    }

    public PapaVideoController(@NonNull NewPaPaVideoPlayFragment newPaPaVideoPlayFragment) {
        super(newPaPaVideoPlayFragment.getContext());
        this.papaTabFragment = newPaPaVideoPlayFragment;
    }

    public PapaVideoController(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PapaVideoController(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.dueeeke.videoplayer.controller.GestureVideoController, com.dueeeke.videoplayer.controller.BaseVideoController
    public void initView() {
        super.initView();
        RelativeLayout relativeLayout = (RelativeLayout) this.mControllerView.findViewById(R.id.rl_root);
        this.thumb = (ImageView) this.mControllerView.findViewById(R.id.iv_thumb);
        this.start_play = (ImageView) this.mControllerView.findViewById(R.id.start_play);
        this.loadView = (VideoLoadView) this.mControllerView.findViewById(R.id.loading);
        this.liner_papa_bar = (LinearLayout) this.mControllerView.findViewById(R.id.liner_papa_bar);
        this.text_look_complete = (TextView) this.mControllerView.findViewById(R.id.text_look_complete);
        this.seekBar = (SeekBar) this.mControllerView.findViewById(R.id.seekBar);
        this.curr_time = (TextView) this.mControllerView.findViewById(R.id.curr_time);
        this.total_time = (TextView) this.mControllerView.findViewById(R.id.total_time);
        this.seekBar.setOnSeekBarChangeListener(this);
        this.gestureDetector = new GestureDetector(getContext(), new MyGestureListener());
        setOnTouchListener(new View.OnTouchListener() { // from class: com.one.tomato.thirdpart.video.controller.PapaVideoController.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PapaVideoController.this.gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
        this.touchFlower = new TouchFlower(this, DisplayMetricsUtils.getWidth(), DisplayMetricsUtils.getHeight(), getContext());
        this.touchFlower.init();
        this.text_look_complete.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.thirdpart.video.controller.PapaVideoController.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new VipPayDialog(PapaVideoController.this.getContext()).show();
            }
        });
    }

    @Override // com.one.tomato.utils.BaseToTVideoController, com.dueeeke.videoplayer.controller.BaseVideoController
    public void setPlayState(int i) {
        super.setPlayState(i);
        switch (i) {
            case -1:
                DomainRequest.getInstance().switchDomainUrlByType("ttViewVideoNew");
                this.thumb.setVisibility(0);
                this.loadView.setVisibility(4);
                this.loadView.stop();
                this.start_play.setVisibility(4);
                hideStatusView();
                return;
            case 0:
                this.thumb.setVisibility(0);
                this.loadView.setVisibility(4);
                this.loadView.stop();
                this.start_play.setVisibility(4);
                return;
            case 1:
                this.thumb.setVisibility(0);
                this.loadView.setVisibility(0);
                this.loadView.start();
                this.start_play.setVisibility(4);
                return;
            case 2:
                this.thumb.setVisibility(0);
                this.loadView.setVisibility(4);
                this.loadView.stop();
                this.start_play.setVisibility(4);
                return;
            case 3:
                NewPaPaVideoPlayFragment newPaPaVideoPlayFragment = this.papaTabFragment;
                if (newPaPaVideoPlayFragment != null && (newPaPaVideoPlayFragment.getActivity() == null || this.papaTabFragment.getActivity().isDestroyed() || !this.papaTabFragment.getIsResume())) {
                    this.mMediaPlayer.pause();
                }
                this.thumb.setVisibility(8);
                this.loadView.setVisibility(4);
                this.loadView.stop();
                this.start_play.setVisibility(4);
                post(this.mShowProgress);
                return;
            case 4:
                this.loadView.setVisibility(4);
                this.loadView.stop();
                this.start_play.setVisibility(0);
                return;
            case 5:
                this.loadView.setVisibility(4);
                this.loadView.stop();
                this.start_play.setVisibility(4);
                this.mMediaPlayer.replay(true);
                return;
            case 6:
                this.loadView.setVisibility(0);
                this.loadView.start();
                this.start_play.setVisibility(4);
                return;
            case 7:
                this.loadView.setVisibility(4);
                this.loadView.stop();
                this.start_play.setVisibility(4);
                return;
            default:
                return;
        }
    }

    public ImageView getThumb() {
        return this.thumb;
    }

    public void setPostList(PostList postList) {
        this.postList = postList;
        checkVideoTime(postList);
    }

    private void checkVideoTime(PostList postList) {
        this.text_look_complete.setVisibility(8);
        this.liner_papa_bar.setVisibility(8);
        this.text_look_complete.setText("");
        this.isExceed = false;
        this.seekBar.setProgress(0);
        if (postList != null) {
            String videoTime = postList.getVideoTime();
            if (PostUtils.INSTANCE.isLongPaPaVideo(videoTime) && !postList.isAd()) {
                this.isExceed = true;
                TextView textView = this.text_look_complete;
                textView.setText(AppUtil.getString(R.string.papa_look_complete) + ConstantUtils.PLACEHOLDER_STR_ONE + videoTime);
                if (DBUtil.getUserInfo().getVipType() > 0) {
                    this.text_look_complete.setVisibility(8);
                    this.liner_papa_bar.setVisibility(0);
                    return;
                }
                this.text_look_complete.setVisibility(0);
                this.liner_papa_bar.setVisibility(8);
                return;
            }
            this.isExceed = false;
            this.text_look_complete.setVisibility(8);
            this.liner_papa_bar.setVisibility(8);
        }
    }

    @Override // com.one.tomato.utils.BaseToTVideoController
    public Map<String, Object> getVideoErrorInfo() {
        HashMap hashMap = new HashMap();
        hashMap.put("postion", Integer.valueOf(this.currentPostion));
        hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(this.postList.getId()));
        hashMap.put("type", "papa視頻");
        return hashMap;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (!z) {
            return;
        }
        long duration = this.mMediaPlayer.getDuration();
        long max = (i * duration) / seekBar.getMax();
        if (this.curr_time != null) {
            this.currentPostion = (int) max;
        }
        this.curr_time.setText(stringForTime(this.currentPostion));
        this.onProgressListener.onProgress(this.mMediaPlayer.getCurrentPosition(), duration);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        this.mIsDragging = true;
        removeCallbacks(this.mShowProgress);
        removeCallbacks(this.mFadeOut);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        this.mMediaPlayer.seekTo((this.mMediaPlayer.getDuration() * seekBar.getProgress()) / seekBar.getMax());
        this.mIsDragging = false;
        post(this.mShowProgress);
        show();
    }

    public void setOnPlayViewClickListener(OnFavorDoubleClickListener onFavorDoubleClickListener, ImageView imageView, TextView textView) {
        this.onFavorDoubleClickListener = onFavorDoubleClickListener;
        this.iv_favor = imageView;
        this.tv_favor_num = textView;
    }

    /* loaded from: classes3.dex */
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private MyGestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            LogUtil.m3785e("PapaController", "onSingleTapConfirmed");
            PapaVideoController.this.doPauseResume();
            return false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            LogUtil.m3785e("PapaController", "onDoubleTap");
            PapaVideoController.this.touchFlower.addFlower(motionEvent.getX(), motionEvent.getY());
            if (PapaVideoController.this.postList.getIsThumbUp() != 1) {
                int goodNum = PapaVideoController.this.postList.getGoodNum();
                PapaVideoController.this.postList.setIsThumbUp(1);
                PapaVideoController.this.iv_favor.setImageResource(R.drawable.video_favor_s);
                PapaVideoController.this.postList.setGoodNum(goodNum + 1);
                PapaVideoController.this.tv_favor_num.setText(FormatUtil.formatNumOverTenThousand(PapaVideoController.this.postList.getGoodNum() + ""));
                if (PapaVideoController.this.onFavorDoubleClickListener == null) {
                    return false;
                }
                PapaVideoController.this.onFavorDoubleClickListener.onFavourDoubleClick(PapaVideoController.this.postList);
                return false;
            }
            return false;
        }
    }

    @Override // com.dueeeke.videoplayer.controller.BaseVideoController
    protected int setProgress() {
        MediaPlayerControl mediaPlayerControl;
        MediaPlayerControl mediaPlayerControl2 = this.mMediaPlayer;
        if (mediaPlayerControl2 == null || this.mIsDragging) {
            return 0;
        }
        int currentPosition = (int) mediaPlayerControl2.getCurrentPosition();
        int duration = (int) this.mMediaPlayer.getDuration();
        int i = (currentPosition / 1000) % 60;
        if (this.isExceed && i > 30 && DBUtil.getUserInfo().getVipType() < 1 && (mediaPlayerControl = this.mMediaPlayer) != null) {
            mediaPlayerControl.replay(true);
            return 0;
        }
        this.currentPostion = duration;
        OnProgressListener onProgressListener = this.onProgressListener;
        if (onProgressListener != null) {
            onProgressListener.onProgress(currentPosition, duration);
        }
        SeekBar seekBar = this.seekBar;
        if (seekBar != null) {
            if (duration > 0) {
                seekBar.setEnabled(true);
                this.seekBar.setProgress((int) (((currentPosition * 1.0d) / duration) * this.seekBar.getMax()));
            } else {
                seekBar.setEnabled(false);
            }
            int bufferedPercentage = this.mMediaPlayer.getBufferedPercentage();
            if (bufferedPercentage >= 95) {
                SeekBar seekBar2 = this.seekBar;
                seekBar2.setSecondaryProgress(seekBar2.getMax());
            } else {
                this.seekBar.setSecondaryProgress(bufferedPercentage * 10);
            }
        }
        TextView textView = this.total_time;
        if (textView != null) {
            textView.setText(stringForTime(duration));
        }
        TextView textView2 = this.curr_time;
        if (textView2 != null) {
            textView2.setText(stringForTime(currentPosition));
        }
        return currentPosition;
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }
}
