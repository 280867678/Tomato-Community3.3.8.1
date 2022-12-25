package com.tomatolive.library.utils.live;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.p134ui.TXCloudVideoView;
import com.tomato.ijk.media.player.IMediaPlayer;
import com.tomato.ijk.media.player.IjkMediaPlayer;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.p136ui.view.ijkplayer.IjkVideoView;
import com.tomatolive.library.p136ui.view.widget.RoundRelativeLayout;
import com.tomatolive.library.utils.AnimUtils;
import com.tomatolive.library.utils.LogManager;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class PlayManager {
    private boolean isFromRecyclerView;
    private ImageView ivCover;
    private OnPlayListener listener;
    private Context mContext;
    private RoundRelativeLayout mCurrentPlayView;
    private IjkVideoView mIjkplayerView;
    private TXCloudVideoView mPlayerView;
    private RecyclerView recyclerView;
    private TXLivePlayer mLivePlayer = null;
    private ArrayList<Integer> videoPositionList = new ArrayList<>();
    private int[] childLocal = new int[2];
    private int[] parentLocal = new int[2];
    private int mPlayType = 0;
    private int currentPlayIndex = 0;
    private int playPosition = -1;
    private IjkVideoView.OnPlayStateListener onPlayStateListener = new IjkVideoView.OnPlayStateListener() { // from class: com.tomatolive.library.utils.live.PlayManager.1
        @Override // com.tomatolive.library.p136ui.view.ijkplayer.IjkVideoView.OnPlayStateListener, com.tomato.ijk.media.player.IMediaPlayer.OnInfoListener
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2) {
            LogManager.m237p("拉流监听 :" + i);
            if (i == 3) {
                PlayManager.this.showPlayerView();
                if (PlayManager.this.isFromRecyclerView && PlayManager.this.ivCover != null) {
                    AnimUtils.playHideAnimation(PlayManager.this.ivCover, 800L);
                }
                if (PlayManager.this.listener == null) {
                    return true;
                }
                PlayManager.this.listener.onPlaySuccess();
                return true;
            } else if (i == 701) {
                if (PlayManager.this.isFromRecyclerView) {
                    PlayManager.this.hidePlayerView();
                }
                if (PlayManager.this.listener == null) {
                    return true;
                }
                PlayManager.this.listener.onStartBuffering();
                return true;
            } else if (i != 702) {
                return true;
            } else {
                PlayManager.this.showPlayerView();
                if (PlayManager.this.isFromRecyclerView && PlayManager.this.ivCover != null) {
                    AnimUtils.playHideAnimation(PlayManager.this.ivCover, 800L);
                }
                if (PlayManager.this.listener == null) {
                    return true;
                }
                PlayManager.this.listener.onEndBuffering();
                return true;
            }
        }

        @Override // com.tomatolive.library.p136ui.view.ijkplayer.IjkVideoView.OnPlayStateListener, com.tomato.ijk.media.player.IMediaPlayer.OnErrorListener
        public boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
            if (PlayManager.this.listener == null || i2 == 0) {
                return true;
            }
            PlayManager.this.listener.onNetError();
            return true;
        }
    };
    private ITXLivePlayListener playListener = new ITXLivePlayListener() { // from class: com.tomatolive.library.utils.live.PlayManager.2
        @Override // com.tencent.rtmp.ITXLivePlayListener
        public void onNetStatus(Bundle bundle) {
        }

        @Override // com.tencent.rtmp.ITXLivePlayListener
        public void onPlayEvent(int i, Bundle bundle) {
            LogManager.m237p("拉流监听 :" + i + " msg：" + bundle.getString("EVT_MSG"));
            if (i != -2301) {
                if (i == 2003) {
                    if (PlayManager.this.listener == null) {
                        return;
                    }
                    PlayManager.this.listener.onPlaySuccess();
                    return;
                } else if (i == 2004) {
                    PlayManager.this.showPlayerView();
                    if (PlayManager.this.isFromRecyclerView && PlayManager.this.ivCover != null) {
                        AnimUtils.playHideAnimation(PlayManager.this.ivCover, 800L);
                    }
                    if (PlayManager.this.listener == null) {
                        return;
                    }
                    PlayManager.this.listener.onEndBuffering();
                    return;
                } else if (i != 2006) {
                    if (i != 2007) {
                        return;
                    }
                    if (PlayManager.this.isFromRecyclerView) {
                        PlayManager.this.hidePlayerView();
                    }
                    if (PlayManager.this.listener == null) {
                        return;
                    }
                    PlayManager.this.listener.onStartBuffering();
                    return;
                }
            }
            if (PlayManager.this.isFromRecyclerView) {
                PlayManager.this.hidePlayerView();
            }
            if (PlayManager.this.listener != null) {
                PlayManager.this.listener.onNetError();
            }
        }
    };
    private boolean isEnableVideoStreamEncode = SysConfigInfoManager.getInstance().isEnableVideoStreamEncode();

    /* loaded from: classes4.dex */
    public interface OnPlayListener {
        void onEndBuffering();

        void onNetError();

        void onPlayError();

        void onPlaySuccess();

        void onScreenshot(Bitmap bitmap);

        void onStartBuffering();
    }

    public PlayManager(Context context) {
        this.mContext = context;
    }

    public void initRoomPlayManager(ViewGroup viewGroup, String str) {
        this.isFromRecyclerView = false;
        if (viewGroup != null) {
            if (this.isEnableVideoStreamEncode) {
                initIjkVideoView();
                viewGroup.addView(this.mIjkplayerView, 0, new ViewGroup.LayoutParams(-1, -1));
            } else {
                initTXPlayer();
                this.mLivePlayer.setMute(false);
                viewGroup.addView(this.mPlayerView, 0, new ViewGroup.LayoutParams(-1, -1));
            }
            startPlayWithListener(str);
        }
    }

    public void initRecyclerViewPlayManager(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.isFromRecyclerView = true;
        if (this.isEnableVideoStreamEncode) {
            initIjkVideoView();
        } else {
            initTXPlayer();
        }
    }

    private void initIjkVideoView() {
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libfqplayer.so");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mIjkplayerView = new IjkVideoView(this.mContext);
        if (!this.isFromRecyclerView) {
            this.mIjkplayerView.setMute(false);
        }
    }

    private void checkPlayUrl(String str) {
        if (str.startsWith("rtmp://")) {
            this.mPlayType = 0;
        } else if ((str.startsWith("http://") || str.startsWith("https://")) && str.contains(".flv")) {
            this.mPlayType = 1;
        } else if ((!str.startsWith("http://") && !str.startsWith("https://")) || !str.contains(".m3u8")) {
        } else {
            this.mPlayType = 3;
        }
    }

    public void setOnPlayListener(OnPlayListener onPlayListener) {
        this.listener = onPlayListener;
    }

    public OnPlayListener getListener() {
        return this.listener;
    }

    public void startPlayWithListener(String str) {
        checkPlayUrl(str);
        if (this.isEnableVideoStreamEncode) {
            IjkVideoView ijkVideoView = this.mIjkplayerView;
            if (ijkVideoView == null) {
                return;
            }
            ijkVideoView.setOnPlayStateListener(this.onPlayStateListener);
            this.mIjkplayerView.setVideoPath(str);
            this.mIjkplayerView.start();
            return;
        }
        TXLivePlayer tXLivePlayer = this.mLivePlayer;
        if (tXLivePlayer == null) {
            return;
        }
        tXLivePlayer.setPlayListener(this.playListener);
        this.mLivePlayer.startPlay(str, this.mPlayType);
    }

    public void switchStream(String str) {
        checkPlayUrl(str);
        if (this.isEnableVideoStreamEncode) {
            IjkVideoView ijkVideoView = this.mIjkplayerView;
            if (ijkVideoView == null) {
                return;
            }
            ijkVideoView.switchStream(str);
            return;
        }
        TXLivePlayer tXLivePlayer = this.mLivePlayer;
        if (tXLivePlayer == null) {
            return;
        }
        tXLivePlayer.startPlay(str, this.mPlayType);
    }

    public void initTXPlayer() {
        this.mPlayerView = new TXCloudVideoView(this.mContext);
        this.mLivePlayer = new TXLivePlayer(this.mContext);
        TXLivePlayConfig tXLivePlayConfig = new TXLivePlayConfig();
        tXLivePlayConfig.setAutoAdjustCacheTime(true);
        tXLivePlayConfig.setMinAutoAdjustCacheTime(1.0f);
        tXLivePlayConfig.setMaxAutoAdjustCacheTime(1.0f);
        this.mLivePlayer.setConfig(tXLivePlayConfig);
        this.mLivePlayer.setRenderRotation(0);
        this.mLivePlayer.setRenderMode(0);
        this.mLivePlayer.setPlayerView(this.mPlayerView);
    }

    public void onScreenshot() {
        OnPlayListener onPlayListener;
        if (this.isEnableVideoStreamEncode) {
            IjkVideoView ijkVideoView = this.mIjkplayerView;
            if (ijkVideoView == null || (onPlayListener = this.listener) == null) {
                return;
            }
            onPlayListener.onScreenshot(ijkVideoView.getShortcut());
            return;
        }
        TXLivePlayer tXLivePlayer = this.mLivePlayer;
        if (tXLivePlayer == null) {
            return;
        }
        if (!tXLivePlayer.isPlaying()) {
            OnPlayListener onPlayListener2 = this.listener;
            if (onPlayListener2 == null) {
                return;
            }
            onPlayListener2.onScreenshot(null);
            return;
        }
        this.mLivePlayer.snapshot(new TXLivePlayer.ITXSnapshotListener() { // from class: com.tomatolive.library.utils.live.-$$Lambda$PlayManager$OldKuRpENWqhtOLO2xh-6ufOjew
            @Override // com.tencent.rtmp.TXLivePlayer.ITXSnapshotListener
            public final void onSnapshot(Bitmap bitmap) {
                PlayManager.this.lambda$onScreenshot$0$PlayManager(bitmap);
            }
        });
    }

    public /* synthetic */ void lambda$onScreenshot$0$PlayManager(Bitmap bitmap) {
        OnPlayListener onPlayListener = this.listener;
        if (onPlayListener != null) {
            onPlayListener.onScreenshot(bitmap);
        }
    }

    public void resumePlay(boolean z) {
        if (this.isEnableVideoStreamEncode) {
            IjkVideoView ijkVideoView = this.mIjkplayerView;
            if (ijkVideoView == null || !z) {
                return;
            }
            ijkVideoView.start();
            return;
        }
        TXLivePlayer tXLivePlayer = this.mLivePlayer;
        if (tXLivePlayer == null || !z) {
            return;
        }
        tXLivePlayer.resume();
    }

    public void pausePlay() {
        if (this.isEnableVideoStreamEncode) {
            IjkVideoView ijkVideoView = this.mIjkplayerView;
            if (ijkVideoView == null || !ijkVideoView.isPlaying()) {
                return;
            }
            this.mIjkplayerView.pause();
            return;
        }
        TXLivePlayer tXLivePlayer = this.mLivePlayer;
        if (tXLivePlayer == null || !tXLivePlayer.isPlaying()) {
            return;
        }
        this.mLivePlayer.pause();
    }

    public void stopLastPlay() {
        if (this.isEnableVideoStreamEncode) {
            IjkVideoView ijkVideoView = this.mIjkplayerView;
            if (ijkVideoView == null) {
                return;
            }
            ijkVideoView.stopPlayback();
            return;
        }
        TXLivePlayer tXLivePlayer = this.mLivePlayer;
        if (tXLivePlayer == null) {
            return;
        }
        tXLivePlayer.stopPlay(true);
    }

    private void clearAnimationAndSetVisible(View view) {
        if (view != null) {
            if (view.getAnimation() != null) {
                view.getAnimation().setAnimationListener(null);
            }
            view.clearAnimation();
            view.setVisibility(0);
        }
    }

    public void stopPlay() {
        ViewParent parent;
        ViewParent parent2;
        ImageView imageView = this.ivCover;
        if (imageView != null) {
            clearAnimationAndSetVisible(imageView);
        }
        if (this.isEnableVideoStreamEncode) {
            IjkVideoView ijkVideoView = this.mIjkplayerView;
            if (ijkVideoView != null) {
                if (this.isFromRecyclerView && (parent2 = ijkVideoView.getParent()) != null && (parent2 instanceof RelativeLayout)) {
                    ((RelativeLayout) parent2).removeView(this.mIjkplayerView);
                }
                this.mIjkplayerView.stopPlayback();
            }
        } else {
            TXLivePlayer tXLivePlayer = this.mLivePlayer;
            if (tXLivePlayer != null) {
                tXLivePlayer.stopPlay(true);
                this.mLivePlayer.setPlayListener(null);
                this.mCurrentPlayView = null;
            }
            TXCloudVideoView tXCloudVideoView = this.mPlayerView;
            if (tXCloudVideoView != null) {
                if (this.isFromRecyclerView && (parent = tXCloudVideoView.getParent()) != null && (parent instanceof RelativeLayout)) {
                    ((RelativeLayout) parent).removeView(this.mPlayerView);
                }
                this.mPlayerView.onDestroy();
            }
        }
        this.listener = null;
    }

    public void onDestroyPlay() {
        stopPlay();
        if (!this.isEnableVideoStreamEncode || this.mIjkplayerView == null) {
            return;
        }
        IjkMediaPlayer.native_profileEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hidePlayerView() {
        ImageView imageView;
        if (this.isFromRecyclerView && (imageView = this.ivCover) != null) {
            clearAnimationAndSetVisible(imageView);
        }
        TXCloudVideoView tXCloudVideoView = this.mPlayerView;
        if (tXCloudVideoView != null) {
            tXCloudVideoView.setVisibility(4);
        }
        IjkVideoView ijkVideoView = this.mIjkplayerView;
        if (ijkVideoView != null) {
            ijkVideoView.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPlayerView() {
        TXCloudVideoView tXCloudVideoView = this.mPlayerView;
        if (tXCloudVideoView != null) {
            tXCloudVideoView.setVisibility(0);
        }
        IjkVideoView ijkVideoView = this.mIjkplayerView;
        if (ijkVideoView != null) {
            ijkVideoView.setVisibility(0);
        }
    }

    public void release() {
        stopLastPlay();
        this.listener = null;
    }

    public void onRecyclerViewResume() {
        ImageView imageView = this.ivCover;
        if (imageView != null) {
            clearAnimationAndSetVisible(imageView);
        }
        refreshVideo();
    }

    public void onRecyclerViewPause() {
        stopPlay();
    }

    public void refreshVideo() {
        checkPlayVideo();
        playVideoByPosition();
    }

    public void onScrolled() {
        RoundRelativeLayout roundRelativeLayout = this.mCurrentPlayView;
        if (roundRelativeLayout == null || isPlayRange(roundRelativeLayout, this.recyclerView)) {
            return;
        }
        stopPlay();
    }

    public void onScrollStateChanged() {
        if (this.mCurrentPlayView == null) {
            checkPlayVideo();
            playVideoByPosition();
        }
    }

    private boolean isPlayRange(View view, View view2) {
        if (view == null || view2 == null) {
            return false;
        }
        view.getLocationOnScreen(this.childLocal);
        view2.getLocationOnScreen(this.parentLocal);
        int[] iArr = this.childLocal;
        int i = iArr[1];
        int[] iArr2 = this.parentLocal;
        return i >= iArr2[1] && iArr[1] <= (iArr2[1] + view2.getHeight()) - view.getHeight();
    }

    private void checkPlayVideo() {
        LiveEntity liveEntity;
        this.currentPlayIndex = 0;
        this.videoPositionList.clear();
        BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) this.recyclerView.getAdapter();
        int itemCount = baseQuickAdapter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (isPlayRange(baseQuickAdapter.getViewByPosition(i, R$id.sq_root), this.recyclerView) && (liveEntity = (LiveEntity) baseQuickAdapter.getItem(i - baseQuickAdapter.getHeaderLayoutCount())) != null && liveEntity.isCoverPreview() && i >= 0 && !this.videoPositionList.contains(Integer.valueOf(i))) {
                this.videoPositionList.add(Integer.valueOf(i));
            }
        }
        if (this.videoPositionList.size() > 1) {
            this.currentPlayIndex = NumberUtils.getIntRandom(this.videoPositionList.size());
        }
    }

    private void playVideoByPosition() {
        if (this.videoPositionList.size() == 0) {
            return;
        }
        playVideoByPosition(this.videoPositionList.get(this.currentPlayIndex).intValue());
    }

    public void playVideoByPosition(int i) {
        if (this.playPosition == i) {
            return;
        }
        stopPlay();
        BaseViewHolder baseViewHolder = (BaseViewHolder) this.recyclerView.findViewHolderForAdapterPosition(i);
        if (baseViewHolder == null) {
            return;
        }
        BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) this.recyclerView.getAdapter();
        LiveEntity liveEntity = (LiveEntity) baseQuickAdapter.getData().get(i - baseQuickAdapter.getHeaderLayoutCount());
        if (liveEntity == null) {
            return;
        }
        this.playPosition = i;
        if (!liveEntity.isCoverPreview()) {
            refreshVideo();
            return;
        }
        this.mCurrentPlayView = (RoundRelativeLayout) baseViewHolder.getView(R$id.sq_root);
        this.ivCover = (ImageView) baseViewHolder.getView(R$id.iv_cover);
        if (this.isEnableVideoStreamEncode) {
            IjkVideoView ijkVideoView = this.mIjkplayerView;
            if (ijkVideoView == null) {
                return;
            }
            ijkVideoView.setMute(true);
            this.mCurrentPlayView.addView(this.mIjkplayerView, 0, new ViewGroup.LayoutParams(-1, -1));
            startPlayWithListener(liveEntity.getDefPullStreamUrlStr());
            return;
        }
        TXLivePlayer tXLivePlayer = this.mLivePlayer;
        if (tXLivePlayer == null || this.mPlayerView == null) {
            return;
        }
        tXLivePlayer.setMute(true);
        this.mCurrentPlayView.addView(this.mPlayerView, 0, new ViewGroup.LayoutParams(-1, -1));
        startPlayWithListener(liveEntity.getDefPullStreamUrlStr());
    }
}
