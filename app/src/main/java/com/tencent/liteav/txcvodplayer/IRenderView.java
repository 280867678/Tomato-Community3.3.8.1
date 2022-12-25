package com.tencent.liteav.txcvodplayer;

import android.support.annotation.NonNull;
import android.view.View;
import com.tencent.ijk.media.player.IMediaPlayer;

/* renamed from: com.tencent.liteav.txcvodplayer.a */
/* loaded from: classes3.dex */
public interface IRenderView {

    /* compiled from: IRenderView.java */
    /* renamed from: com.tencent.liteav.txcvodplayer.a$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3655a {
        /* renamed from: a */
        void mo677a(@NonNull AbstractC3656b abstractC3656b);

        /* renamed from: a */
        void mo676a(@NonNull AbstractC3656b abstractC3656b, int i, int i2);

        /* renamed from: a */
        void mo675a(@NonNull AbstractC3656b abstractC3656b, int i, int i2, int i3);
    }

    /* compiled from: IRenderView.java */
    /* renamed from: com.tencent.liteav.txcvodplayer.a$b */
    /* loaded from: classes3.dex */
    public interface AbstractC3656b {
        @NonNull
        /* renamed from: a */
        IRenderView mo670a();

        /* renamed from: a */
        void mo668a(IMediaPlayer iMediaPlayer);
    }

    void addRenderCallback(@NonNull AbstractC3655a abstractC3655a);

    View getView();

    void removeRenderCallback(@NonNull AbstractC3655a abstractC3655a);

    void setAspectRatio(int i);

    void setVideoRotation(int i);

    void setVideoSampleAspectRatio(int i, int i2);

    void setVideoSize(int i, int i2);

    boolean shouldWaitForResize();
}
