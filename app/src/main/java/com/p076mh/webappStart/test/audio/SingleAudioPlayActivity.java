package com.p076mh.webappStart.test.audio;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.p005v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.C2345Wx;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextZZBMediaPlayer;
import java.io.IOException;

/* renamed from: com.mh.webappStart.test.audio.SingleAudioPlayActivity */
/* loaded from: classes3.dex */
public class SingleAudioPlayActivity extends AppCompatActivity implements MediaPlayer.OnBufferingUpdateListener {
    private InnerAudioContextZZBMediaPlayer innerAudioContext;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.web_sdk_activity_audio_play);
        this.innerAudioContext = C2345Wx.createInnerAudioContext();
        try {
            this.innerAudioContext.setAudioStreamType(3);
            this.innerAudioContext.setOnBufferingUpdateListener(this);
            this.innerAudioContext.setDataSource("https://m10.music.126.net/20190919093926/6d199bf69d07ffab8c264c70d3585b66/yyaac/555a/0652/050c/b005df5eda18803f28d02f18f7668b1a.m4a");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.innerAudioContext.setOnCompletionListener(new MediaPlayer.OnCompletionListener(this) { // from class: com.mh.webappStart.test.audio.SingleAudioPlayActivity.1
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.e("SingleAudioPlayActivity", "onCompletion: ");
            }
        });
    }

    public void play(View view) {
        this.innerAudioContext.start();
    }

    public void pause(View view) {
        this.innerAudioContext.pause();
    }

    public void stop(View view) {
        this.innerAudioContext.stop();
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        Log.e("SingleAudioPlayActivity", "onBufferingUpdate: " + i);
    }
}
