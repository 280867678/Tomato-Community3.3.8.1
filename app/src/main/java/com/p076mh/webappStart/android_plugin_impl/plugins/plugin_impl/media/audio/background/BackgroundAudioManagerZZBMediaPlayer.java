package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.ZZBMediaPlayer;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayerServiceMananger;
import com.p076mh.webappStart.util.NotificationUtil;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.BackgroundAudioManagerZZBMediaPlayer */
/* loaded from: classes3.dex */
public class BackgroundAudioManagerZZBMediaPlayer extends ZZBMediaPlayer {
    private static final String TAG = "BackgroundAudioManagerZ";
    private static BackgroundAudioManagerZZBMediaPlayer instance = new BackgroundAudioManagerZZBMediaPlayer();

    private BackgroundAudioManagerZZBMediaPlayer() {
    }

    public static BackgroundAudioManagerZZBMediaPlayer getInstance() {
        return instance;
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer, android.media.MediaPlayer
    public void start() throws IllegalStateException {
        if (TextUtils.isEmpty(getTitle())) {
            Log.e(TAG, "start: 未设置播放的title");
            onCommonError();
        } else if (PlayerServiceMananger.getInstance().isStarted()) {
            super.start();
        } else if (Build.VERSION.SDK_INT >= 19) {
            NotificationUtil.openNotificationSetting(this.webViewFragment.getActivity(), new NotificationUtil.OnNextLitener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.BackgroundAudioManagerZZBMediaPlayer.1
                @Override // com.p076mh.webappStart.util.NotificationUtil.OnNextLitener
                public void onNext() {
                    Toast.makeText(((ZZBMediaPlayer) BackgroundAudioManagerZZBMediaPlayer.this).webViewFragment.getActivity(), "已开启通知权限", 0).show();
                    PlayerServiceMananger.getInstance().start(new PlayerServiceMananger.OnServiceStateListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.BackgroundAudioManagerZZBMediaPlayer.1.1
                        @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayerServiceMananger.OnServiceStateListener
                        public void onServiceCreated() {
                            Log.e(BackgroundAudioManagerZZBMediaPlayer.TAG, "onServiceCreated: ");
                            BackgroundAudioManagerZZBMediaPlayer.super.start();
                        }
                    });
                }
            });
        } else {
            PlayerServiceMananger.getInstance().start(new PlayerServiceMananger.OnServiceStateListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.BackgroundAudioManagerZZBMediaPlayer.2
                @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayerServiceMananger.OnServiceStateListener
                public void onServiceCreated() {
                    Log.e(BackgroundAudioManagerZZBMediaPlayer.TAG, "onServiceCreated: ");
                    BackgroundAudioManagerZZBMediaPlayer.super.start();
                }
            });
        }
    }
}
