package com.p076mh.webappStart.test.audio;

import android.os.Bundle;
import android.support.p005v7.app.AppCompatActivity;
import android.view.View;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.ZZBMediaPlayer;

/* renamed from: com.mh.webappStart.test.audio.TestZZBMediaPlayerActivity */
/* loaded from: classes3.dex */
public class TestZZBMediaPlayerActivity extends AppCompatActivity {
    private ZZBMediaPlayer zzbMediaPlayer;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.web_sdk_activity_test_zzbmedia_player);
        this.zzbMediaPlayer = new ZZBMediaPlayer();
        this.zzbMediaPlayer.setAutoplay(true);
        try {
            this.zzbMediaPlayer.setWxLoop(true);
            this.zzbMediaPlayer.start();
            this.zzbMediaPlayer.setSrc("http://demo.srs.com/live/livestream.m3u8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer != null) {
            zZBMediaPlayer.wx_destroy();
        }
    }

    public void wx_play(View view) {
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer != null) {
            zZBMediaPlayer.wx_play();
        }
    }

    public void wx_pause(View view) {
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer != null) {
            zZBMediaPlayer.wx_pause();
        }
    }

    public void wx_stop(View view) {
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer != null) {
            zZBMediaPlayer.wx_stop();
        }
    }

    public void wx_seek(View view) {
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer != null) {
            zZBMediaPlayer.wx_seek(60);
        }
    }
}
