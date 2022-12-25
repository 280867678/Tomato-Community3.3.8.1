package com.tencent.ijk.media.player;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import java.util.ArrayList;

@TargetApi(14)
/* loaded from: classes3.dex */
public class TextureMediaPlayer extends MediaPlayerProxy implements IMediaPlayer, ISurfaceTextureHolder {
    private IMediaPlayer mBackEndMediaPlayer;
    private boolean mReuseSurfaceTexture;
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;
    private ISurfaceTextureHost mSurfaceTextureHost;

    public TextureMediaPlayer(IMediaPlayer iMediaPlayer) {
        super(iMediaPlayer);
        this.mBackEndMediaPlayer = iMediaPlayer;
    }

    public void releaseSurfaceTexture() {
        SurfaceTexture surfaceTexture = this.mSurfaceTexture;
        if (surfaceTexture == null || this.mReuseSurfaceTexture) {
            return;
        }
        ISurfaceTextureHost iSurfaceTextureHost = this.mSurfaceTextureHost;
        if (iSurfaceTextureHost != null) {
            iSurfaceTextureHost.releaseSurfaceTexture(surfaceTexture);
        } else {
            surfaceTexture.release();
        }
        this.mSurfaceTexture = null;
    }

    @Override // com.tencent.ijk.media.player.MediaPlayerProxy, com.tencent.ijk.media.player.IMediaPlayer
    public void reset() {
        super.reset();
        releaseSurfaceTexture();
    }

    @Override // com.tencent.ijk.media.player.MediaPlayerProxy, com.tencent.ijk.media.player.IMediaPlayer
    public void release() {
        super.release();
        releaseSurfaceTexture();
    }

    @Override // com.tencent.ijk.media.player.MediaPlayerProxy, com.tencent.ijk.media.player.IMediaPlayer
    public void setDisplay(SurfaceHolder surfaceHolder) {
        if (this.mSurfaceTexture == null) {
            super.setDisplay(surfaceHolder);
        }
    }

    @Override // com.tencent.ijk.media.player.MediaPlayerProxy, com.tencent.ijk.media.player.IMediaPlayer
    public void setSurface(Surface surface) {
        if (this.mSurfaceTexture == null) {
            super.setSurface(surface);
        }
        this.mSurface = surface;
    }

    @Override // com.tencent.ijk.media.player.MediaPlayerProxy, com.tencent.ijk.media.player.IMediaPlayer
    public Surface getSurface() {
        return super.getSurface();
    }

    @Override // com.tencent.ijk.media.player.ISurfaceTextureHolder
    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        if (this.mSurfaceTexture == surfaceTexture) {
            return;
        }
        releaseSurfaceTexture();
        this.mSurfaceTexture = surfaceTexture;
        if (surfaceTexture == null) {
            this.mSurface = null;
            super.setSurface(null);
            return;
        }
        if (this.mSurface == null) {
            this.mSurface = new Surface(surfaceTexture);
        }
        super.setSurface(this.mSurface);
    }

    @Override // com.tencent.ijk.media.player.ISurfaceTextureHolder
    public SurfaceTexture getSurfaceTexture() {
        return this.mSurfaceTexture;
    }

    @Override // com.tencent.ijk.media.player.ISurfaceTextureHolder
    public void setSurfaceTextureHost(ISurfaceTextureHost iSurfaceTextureHost) {
        this.mSurfaceTextureHost = iSurfaceTextureHost;
    }

    public void setReuseSurfaceTexture(boolean z) {
        this.mReuseSurfaceTexture = z;
    }

    public IMediaPlayer getBackEndMediaPlayer() {
        return this.mBackEndMediaPlayer;
    }

    @Override // com.tencent.ijk.media.player.MediaPlayerProxy, com.tencent.ijk.media.player.IMediaPlayer
    public int getBitrateIndex() {
        return this.mBackEndMediaPlayer.getBitrateIndex();
    }

    @Override // com.tencent.ijk.media.player.MediaPlayerProxy, com.tencent.ijk.media.player.IMediaPlayer
    public void setBitrateIndex(int i) {
        this.mBackEndMediaPlayer.setBitrateIndex(i);
    }

    @Override // com.tencent.ijk.media.player.MediaPlayerProxy, com.tencent.ijk.media.player.IMediaPlayer
    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        return this.mBackEndMediaPlayer.getSupportedBitrates();
    }
}
