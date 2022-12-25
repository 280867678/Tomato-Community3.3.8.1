package com.p076mh.webappStart.test.audio;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.p005v7.app.AppCompatActivity;
import android.view.View;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.C2345Wx;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.inner.InnerAudioContextZZBMediaPlayer;
import java.io.IOException;

/* renamed from: com.mh.webappStart.test.audio.ManyAudioPlayActivity */
/* loaded from: classes3.dex */
public class ManyAudioPlayActivity extends AppCompatActivity implements MediaPlayer.OnBufferingUpdateListener {
    InnerAudioContextZZBMediaPlayer[] innerAudioContexts = new InnerAudioContextZZBMediaPlayer[3];

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.web_sdk_activity_many_audio_play);
    }

    public void start(View view) throws IOException {
        for (int i = 0; i < 3; i++) {
            this.innerAudioContexts[i] = C2345Wx.createInnerAudioContext();
            this.innerAudioContexts[i].setAudioStreamType(3);
            this.innerAudioContexts[i].setOnBufferingUpdateListener(this);
            if (i == 0) {
                this.innerAudioContexts[i].setDataSource("http://49.234.81.36/3M.mp3");
            } else if (i == 1) {
                this.innerAudioContexts[i].setDataSource("http://49.234.81.36/4M.m4a");
            } else if (i == 2) {
                this.innerAudioContexts[i].setDataSource("http://49.234.81.36/18M.flac");
            }
            this.innerAudioContexts[i].start();
        }
    }

    public void stop(View view) {
        for (int i = 0; i < 3; i++) {
            this.innerAudioContexts[i].stop();
        }
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        Logger.m4112i(mediaPlayer.toString(), Integer.valueOf(i));
    }
}
