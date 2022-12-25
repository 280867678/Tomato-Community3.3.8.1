package com.google.android.exoplayer2.p063ui;

import android.content.Context;
import android.util.AttributeSet;

@Deprecated
/* renamed from: com.google.android.exoplayer2.ui.PlaybackControlView */
/* loaded from: classes3.dex */
public class PlaybackControlView extends PlayerControlView {

    @Deprecated
    /* renamed from: com.google.android.exoplayer2.ui.PlaybackControlView$ControlDispatcher */
    /* loaded from: classes3.dex */
    public interface ControlDispatcher extends com.google.android.exoplayer2.ControlDispatcher {
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlaybackControlView$DefaultControlDispatcher */
    /* loaded from: classes3.dex */
    private static final class DefaultControlDispatcher extends com.google.android.exoplayer2.DefaultControlDispatcher implements ControlDispatcher {
        private DefaultControlDispatcher() {
        }
    }

    static {
        new DefaultControlDispatcher();
    }

    public PlaybackControlView(Context context) {
        super(context);
    }

    public PlaybackControlView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PlaybackControlView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
