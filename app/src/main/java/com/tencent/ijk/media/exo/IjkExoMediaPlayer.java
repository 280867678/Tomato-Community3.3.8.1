package com.tencent.ijk.media.exo;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.tencent.ijk.media.exo.demo.EventLogger;
import com.tencent.ijk.media.player.AbstractMediaPlayer;
import com.tencent.ijk.media.player.IjkBitrateItem;
import com.tencent.ijk.media.player.MediaInfo;
import com.tencent.ijk.media.player.misc.IjkTrackInfo;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes3.dex */
public class IjkExoMediaPlayer extends AbstractMediaPlayer implements ExoPlayer.EventListener {
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private Context mAppContext;
    private Uri mDataSource;
    private Surface mSurface;
    private int mVideoHeight;
    private int mVideoWidth;
    private SimpleExoPlayer player;
    private SimplePlayerListener mSimpleListener = new SimplePlayerListener();
    private DataSource.Factory mediaDataSourceFactory = buildDataSourceFactory(true);
    private Handler mainHandler = new Handler();
    private DefaultTrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(BANDWIDTH_METER));
    private EventLogger eventLogger = new EventLogger(this.trackSelector);

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public int getAudioSessionId() {
        return 0;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public int getBitrateIndex() {
        return 0;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public MediaInfo getMediaInfo() {
        return null;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    /* renamed from: getTrackInfo  reason: collision with other method in class */
    public IjkTrackInfo[] mo6537getTrackInfo() {
        return null;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public int getVideoSarDen() {
        return 1;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public int getVideoSarNum() {
        return 1;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public boolean isLooping() {
        return false;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public boolean isPlayable() {
        return true;
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public void onLoadingChanged(boolean z) {
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }

    public void onPositionDiscontinuity() {
    }

    public void onTimelineChanged(Timeline timeline, Object obj) {
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setAudioStreamType(int i) {
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setBitrateIndex(int i) {
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setKeepInBackground(boolean z) {
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setLogEnabled(boolean z) {
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setScreenOnWhilePlaying(boolean z) {
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setWakeMode(Context context, int i) {
    }

    public IjkExoMediaPlayer(Context context) {
        this.mAppContext = context.getApplicationContext();
        DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(context);
        this.player = ExoPlayerFactory.newSimpleInstance(defaultRenderersFactory, this.trackSelector);
        this.player.addListener((ExoPlayer.EventListener) this);
        this.player.addListener((ExoPlayer.EventListener) this.eventLogger);
        this.player.setAudioDebugListener(this.eventLogger);
        this.player.setVideoDebugListener(this.eventLogger);
        this.player.setMetadataOutput(this.eventLogger);
        this.player.setVideoListener(this.mSimpleListener);
    }

    public SimpleExoPlayer getPlayer() {
        return this.player;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setDisplay(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            setSurface(null);
        } else {
            setSurface(surfaceHolder.getSurface());
        }
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setSurface(Surface surface) {
        this.mSurface = surface;
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setVideoSurface(surface);
        }
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public Surface getSurface() {
        return this.mSurface;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setDataSource(Context context, Uri uri) {
        this.mDataSource = uri;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setDataSource(Context context, Uri uri, Map<String, String> map) {
        setDataSource(context, uri);
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setDataSource(String str) {
        setDataSource(this.mAppContext, Uri.parse(str));
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setDataSource(FileDescriptor fileDescriptor) {
        throw new UnsupportedOperationException("no support");
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public String getDataSource() {
        return this.mDataSource.toString();
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void prepareAsync() {
        this.player.prepare(buildMediaSource(this.mDataSource, null));
        this.player.setPlayWhenReady(false);
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void start() {
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer == null) {
            return;
        }
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void stop() throws IllegalStateException {
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer == null) {
            return;
        }
        simpleExoPlayer.release();
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void pause() throws IllegalStateException {
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer == null) {
            return;
        }
        simpleExoPlayer.setPlayWhenReady(false);
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public int getVideoWidth() {
        return this.mVideoWidth;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public int getVideoHeight() {
        return this.mVideoHeight;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public boolean isPlaying() {
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer == null) {
            return false;
        }
        int playbackState = simpleExoPlayer.getPlaybackState();
        if (playbackState != 2 && playbackState != 3) {
            return false;
        }
        return this.player.getPlayWhenReady();
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void seekTo(long j) throws IllegalStateException {
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer == null) {
            return;
        }
        simpleExoPlayer.seekTo(j);
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public long getCurrentPosition() {
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer == null) {
            return 0L;
        }
        return simpleExoPlayer.getCurrentPosition();
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public long getDuration() {
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer == null) {
            return 0L;
        }
        return simpleExoPlayer.getDuration();
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void reset() {
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
            this.player.removeListener((ExoPlayer.EventListener) this.eventLogger);
            this.player = null;
        }
        this.mSurface = null;
        this.mDataSource = null;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setLooping(boolean z) {
        throw new UnsupportedOperationException("no support");
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setRate(float f) {
        this.player.setPlaybackParameters(new PlaybackParameters(f, f));
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void setVolume(float f, float f2) {
        this.player.setVolume((f + f2) / 2.0f);
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public void release() {
        if (this.player != null) {
            reset();
            this.eventLogger = null;
        }
    }

    public int getBufferedPercentage() {
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer == null) {
            return 0;
        }
        return simpleExoPlayer.getBufferedPercentage();
    }

    public Format getVideoFormat() {
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer == null) {
            return null;
        }
        return simpleExoPlayer.getVideoFormat();
    }

    public int getObservedBitrate() {
        return this.eventLogger.getObservedBitrate();
    }

    public DecoderCounters getVideoDecoderCounters() {
        return this.player.getVideoDecoderCounters();
    }

    public MediaSource buildMediaSource(Uri uri, String str) {
        int inferContentType;
        if (TextUtils.isEmpty(str)) {
            inferContentType = Util.inferContentType(uri);
        } else {
            inferContentType = Util.inferContentType("." + str);
        }
        if (inferContentType != 0) {
            if (inferContentType == 1) {
                return new SsMediaSource(uri, buildDataSourceFactory(false), new DefaultSsChunkSource.Factory(this.mediaDataSourceFactory), this.mainHandler, this.eventLogger);
            }
            if (inferContentType == 2) {
                return new HlsMediaSource(uri, this.mediaDataSourceFactory, this.mainHandler, this.eventLogger);
            }
            if (inferContentType == 3) {
                return new ExtractorMediaSource(uri, this.mediaDataSourceFactory, new DefaultExtractorsFactory(), this.mainHandler, this.eventLogger);
            }
            throw new IllegalStateException("Unsupported type: " + inferContentType);
        }
        return new DashMediaSource(uri, buildDataSourceFactory(false), new DefaultDashChunkSource.Factory(this.mediaDataSourceFactory), this.mainHandler, this.eventLogger);
    }

    private DataSource.Factory buildDataSourceFactory(boolean z) {
        DefaultBandwidthMeter defaultBandwidthMeter = z ? BANDWIDTH_METER : null;
        return new DefaultDataSourceFactory(this.mAppContext, defaultBandwidthMeter, buildHttpDataSourceFactory(defaultBandwidthMeter));
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter defaultBandwidthMeter) {
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(this.mAppContext, "ExoPlayerDemo"), defaultBandwidthMeter);
    }

    /* loaded from: classes3.dex */
    private class SimplePlayerListener implements SimpleExoPlayer.VideoListener {
        private SimplePlayerListener() {
        }

        @Override // com.google.android.exoplayer2.video.VideoListener
        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
            IjkExoMediaPlayer.this.mVideoWidth = i;
            IjkExoMediaPlayer.this.mVideoHeight = i2;
            IjkExoMediaPlayer.this.notifyOnVideoSizeChanged(i, i2, 1, 1);
            if (i3 > 0) {
                IjkExoMediaPlayer.this.notifyOnInfo(10001, i3);
            }
        }

        @Override // com.google.android.exoplayer2.video.VideoListener
        public void onRenderedFirstFrame() {
            IjkExoMediaPlayer.this.notifyOnInfo(3, 0);
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public void onPlayerStateChanged(boolean z, int i) {
        if (i == 1) {
            notifyOnCompletion();
        } else if (i == 2) {
            notifyOnInfo(701, this.player.getBufferedPercentage());
        } else if (i == 3) {
            notifyOnPrepared();
        } else if (i != 4) {
        } else {
            notifyOnCompletion();
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public void onPlayerError(ExoPlaybackException exoPlaybackException) {
        notifyOnError(1, 1);
    }

    @Override // com.tencent.ijk.media.player.IMediaPlayer
    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        return new ArrayList<>();
    }
}
