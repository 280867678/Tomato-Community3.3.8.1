package com.google.android.exoplayer2;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.source.MediaSource;

/* loaded from: classes2.dex */
public interface ExoPlayer extends Player {

    @Deprecated
    /* loaded from: classes2.dex */
    public interface EventListener extends Player.EventListener {
    }

    PlayerMessage createMessage(PlayerMessage.Target target);

    void prepare(MediaSource mediaSource, boolean z, boolean z2);
}
