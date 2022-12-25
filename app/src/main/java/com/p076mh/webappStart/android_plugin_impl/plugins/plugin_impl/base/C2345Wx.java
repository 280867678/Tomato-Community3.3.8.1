package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base;

import com.p076mh.webappStart.android_plugin_impl.plugins.plugin.unity.BaseUnity;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.BackgroundAudioManagerZZBMediaPlayer;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextZZBMediaPlayer;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.Wx */
/* loaded from: classes3.dex */
public class C2345Wx {
    public static InnerAudioContextZZBMediaPlayer createInnerAudioContext() {
        return new InnerAudioContextZZBMediaPlayer();
    }

    public static InnerAudioContextZZBMediaPlayer createInnerAudioContext(BaseUnity baseUnity) {
        return new InnerAudioContextZZBMediaPlayer(baseUnity);
    }

    public static BackgroundAudioManagerZZBMediaPlayer getBackgroundAudioManager() {
        return BackgroundAudioManagerZZBMediaPlayer.getInstance();
    }
}
