package com.p076mh.webappStart.test.audio;

import android.os.Build;
import android.os.Bundle;
import android.support.p005v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.ZZBMediaPlayer;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayerServiceMananger;
import com.p076mh.webappStart.util.NotificationUtil;
import java.io.IOException;

/* renamed from: com.mh.webappStart.test.audio.BackgroundPlayActivity */
/* loaded from: classes3.dex */
public class BackgroundPlayActivity extends AppCompatActivity {
    private ZZBMediaPlayer zzbMediaPlayer;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.web_sdk_activity_background_play);
        if (Build.VERSION.SDK_INT >= 19) {
            NotificationUtil.openNotificationSetting(this, new NotificationUtil.OnNextLitener() { // from class: com.mh.webappStart.test.audio.BackgroundPlayActivity.1
                @Override // com.p076mh.webappStart.util.NotificationUtil.OnNextLitener
                public void onNext() {
                    Toast.makeText(BackgroundPlayActivity.this, "已开启通知权限", 0).show();
                }
            });
        }
    }

    public void open(View view) {
        PlayerServiceMananger.getInstance().start(new PlayerServiceMananger.OnServiceStateListener() { // from class: com.mh.webappStart.test.audio.BackgroundPlayActivity.2
            @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayerServiceMananger.OnServiceStateListener
            public void onServiceCreated() {
                BackgroundPlayActivity.this.zzbMediaPlayer = PlayerServiceMananger.getInstance().getMediaPlayer();
            }
        });
    }

    public void close(View view) {
        PlayerServiceMananger.getInstance().destroy();
        this.zzbMediaPlayer = null;
    }

    public void play(View view) throws IOException {
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer != null) {
            zZBMediaPlayer.setAutoplay(true);
            this.zzbMediaPlayer.setWxLoop(true);
            this.zzbMediaPlayer.setSrc("http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8");
        }
    }

    public void pause(View view) {
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer != null) {
            zZBMediaPlayer.wx_pause();
        }
    }

    public void stop(View view) {
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer != null) {
            zZBMediaPlayer.wx_stop();
        }
    }

    public void volume_low(View view) {
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer != null) {
            zZBMediaPlayer.setVolume(0.2f);
        }
    }

    public void volume_high(View view) {
        ZZBMediaPlayer zZBMediaPlayer = this.zzbMediaPlayer;
        if (zZBMediaPlayer != null) {
            zZBMediaPlayer.setVolume(1.0f);
        }
    }
}
