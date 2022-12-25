package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextCalls */
/* loaded from: classes3.dex */
public interface InnerAudioContextCalls {
    void wx_destroy();

    int wx_getBuffered();

    int wx_getCurrent();

    int wx_getDuration();

    void wx_offCanplay();

    void wx_offEnded();

    void wx_offError();

    void wx_offPause();

    void wx_offPlay();

    void wx_offSeeked();

    void wx_offSeeking();

    void wx_offStop();

    void wx_offTimeUpdate();

    void wx_offWaiting();

    void wx_onCanplay();

    void wx_onEnded();

    void wx_onError();

    void wx_onPause();

    void wx_onPlay();

    void wx_onSeeked();

    void wx_onSeeking();

    void wx_onStop();

    void wx_onTimeUpdate();

    void wx_onWaiting();

    void wx_pause();

    void wx_play();

    void wx_seek(int i);

    void wx_stop();
}
