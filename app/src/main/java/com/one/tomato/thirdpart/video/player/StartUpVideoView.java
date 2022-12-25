package com.one.tomato.thirdpart.video.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.dueeeke.videoplayer.player.IjkVideoView;

/* loaded from: classes3.dex */
public class StartUpVideoView extends IjkVideoView {
    @Override // com.dueeeke.videoplayer.player.BaseIjkVideoView
    protected boolean checkNetwork() {
        return false;
    }

    public StartUpVideoView(@NonNull Context context) {
        super(context);
    }

    public StartUpVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public StartUpVideoView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
