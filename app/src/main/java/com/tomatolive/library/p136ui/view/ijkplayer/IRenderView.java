package com.tomatolive.library.p136ui.view.ijkplayer;

import android.graphics.SurfaceTexture;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import com.tomato.ijk.media.player.IMediaPlayer;

/* renamed from: com.tomatolive.library.ui.view.ijkplayer.IRenderView */
/* loaded from: classes3.dex */
public interface IRenderView {
    public static final int AR_16_9_FIT_PARENT = 4;
    public static final int AR_4_3_FIT_PARENT = 5;
    public static final int AR_ASPECT_FILL_PARENT = 1;
    public static final int AR_ASPECT_FIT_PARENT = 0;
    public static final int AR_ASPECT_WRAP_CONTENT = 2;
    public static final int AR_MATCH_PARENT = 3;

    /* renamed from: com.tomatolive.library.ui.view.ijkplayer.IRenderView$IRenderCallback */
    /* loaded from: classes3.dex */
    public interface IRenderCallback {
        void onSurfaceChanged(@NonNull ISurfaceHolder iSurfaceHolder, int i, int i2, int i3);

        void onSurfaceCreated(@NonNull ISurfaceHolder iSurfaceHolder, int i, int i2);

        void onSurfaceDestroyed(@NonNull ISurfaceHolder iSurfaceHolder);
    }

    /* renamed from: com.tomatolive.library.ui.view.ijkplayer.IRenderView$ISurfaceHolder */
    /* loaded from: classes3.dex */
    public interface ISurfaceHolder {
        void bindToMediaPlayer(IMediaPlayer iMediaPlayer);

        @NonNull
        IRenderView getRenderView();

        @Nullable
        SurfaceHolder getSurfaceHolder();

        @Nullable
        SurfaceTexture getSurfaceTexture();

        @Nullable
        Surface openSurface();
    }

    void addRenderCallback(@NonNull IRenderCallback iRenderCallback);

    View getView();

    void removeRenderCallback(@NonNull IRenderCallback iRenderCallback);

    void setAspectRatio(int i);

    void setVideoRotation(int i);

    void setVideoSampleAspectRatio(int i, int i2);

    void setVideoSize(int i, int i2);

    boolean shouldWaitForResize();
}
