package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.p002v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$mipmap;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.C2345Wx;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.ZZBMediaPlayer;
import com.p076mh.webappStart.util.ActivityManager;
import com.p076mh.webappStart.util.DensityUtil;
import com.p076mh.webappStart.util.ImgUtils;
import java.io.File;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayService */
/* loaded from: classes3.dex */
public class PlayService extends Service {
    private static final String TAG = "PlayService";
    private BackgroundAudioManagerZZBMediaPlayer backgroundAudioManager;
    private Mp3Receiver mp3Receiver;
    private Notification notification;
    private NotificationManager notificationManager;
    private RemoteViews remoteView;
    private final String ACTION_CENTER = "action_center";
    private final String ACTION_NEXT = "action_next";
    private final String ACTION_LAST = "action_last";
    private final String ACTION_CANCEL = "action_cancel";
    private final int PID = Process.myPid();
    private boolean isServiceDestroyed = true;
    private CZMediaPlayer.PlayStateListener playStateListener = new CZMediaPlayer.PlayStateListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayService.1
        @Override // android.media.MediaPlayer.OnBufferingUpdateListener
        public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        }

        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(MediaPlayer mediaPlayer) {
        }

        @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
        public void onPlaying(int i, int i2) {
        }

        @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
        public void onPrepare() {
        }

        @Override // android.media.MediaPlayer.OnSeekCompleteListener
        public void onSeekComplete(MediaPlayer mediaPlayer) {
        }

        @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
        public void onSeeking() {
        }

        @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
        public void onMpStart() {
            Logger.m4112i(PlayService.TAG, "onMpStart: ");
            PlayService.this.remoteView.setImageViewResource(R$id.btn_center, R$mipmap.btn_pause);
            PlayService playService = PlayService.this;
            playService.updateRemoteView(playService.notification);
        }

        @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
        public void onMpStop() {
            Logger.m4112i(PlayService.TAG, "onMpStop: ");
            PlayService.this.remoteView.setImageViewResource(R$id.btn_center, R$mipmap.btn_play);
            PlayService playService = PlayService.this;
            playService.updateRemoteView(playService.notification);
        }

        @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.CZMediaPlayer.PlayStateListener
        public void onMpPause() {
            Logger.m4112i(PlayService.TAG, "onMpPause: ");
            PlayService.this.remoteView.setImageViewResource(R$id.btn_center, R$mipmap.btn_play);
            PlayService playService = PlayService.this;
            playService.updateRemoteView(playService.notification);
        }

        @Override // android.media.MediaPlayer.OnErrorListener
        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            Logger.m4112i(PlayService.TAG, "onError: what = " + i + ",extra = " + i2);
            return false;
        }

        @Override // android.media.MediaPlayer.OnInfoListener
        public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
            Logger.m4112i(PlayService.TAG, "onInfo: what = " + i + ",extra = " + i2);
            return false;
        }
    };

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.isServiceDestroyed = false;
        Log.e(TAG, "onCreate: ");
        this.notificationManager = (NotificationManager) getSystemService("notification");
        initMediaPlayer();
        registMap3Receiver();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        this.notification = initNotificationBar();
        Log.e(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, 1, i2);
    }

    @Override // android.app.Service
    public void onDestroy() {
        Logger.m4112i(TAG, "onDestroy");
        this.isServiceDestroyed = true;
        Mp3Receiver mp3Receiver = this.mp3Receiver;
        if (mp3Receiver != null) {
            unregisterReceiver(mp3Receiver);
            this.mp3Receiver = null;
        }
        ((NotificationManager) getSystemService("notification")).cancel(this.PID);
        this.backgroundAudioManager.stop();
        if (ActivityManager.getInstance().getActivity(PlayingDetailActivity.class) != null) {
            ActivityManager.getInstance().finishActivity(PlayingDetailActivity.class);
        }
        super.onDestroy();
    }

    private void registMap3Receiver() {
        this.mp3Receiver = new Mp3Receiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action_center");
        intentFilter.addAction("action_last");
        intentFilter.addAction("action_next");
        intentFilter.addAction("action_cancel");
        registerReceiver(this.mp3Receiver, intentFilter);
    }

    private void initMediaPlayer() {
        this.backgroundAudioManager = C2345Wx.getBackgroundAudioManager();
        this.backgroundAudioManager.addPlayStateListener(this.playStateListener);
    }

    public Notification initNotificationBar() {
        if (Build.VERSION.SDK_INT >= 26) {
            this.notificationManager.createNotificationChannel(new NotificationChannel("001", "channel_name", 4));
        }
        Intent intent = new Intent(getApplicationContext(), PlayingDetailActivity.class);
        intent.addFlags(268435456);
        PendingIntent activity = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        this.remoteView = new RemoteViews(getPackageName(), R$layout.web_sdk_notification_control2);
        Notification build = new NotificationCompat.Builder(getApplicationContext(), "001").setWhen(System.currentTimeMillis()).setSmallIcon(R$mipmap.app_logo).setLargeIcon(BitmapFactory.decodeResource(getResources(), R$mipmap.app_logo)).setContentIntent(activity).build();
        build.flags |= 16;
        initRemoteView(build);
        return build;
    }

    private void initRemoteView(final Notification notification) {
        this.remoteView.setOnClickPendingIntent(R$id.btn_center, PendingIntent.getBroadcast(this, 0, new Intent("action_center"), 0));
        this.remoteView.setOnClickPendingIntent(R$id.btn_next, PendingIntent.getBroadcast(this, 0, new Intent("action_next"), 0));
        this.remoteView.setOnClickPendingIntent(R$id.btn_last, PendingIntent.getBroadcast(this, 0, new Intent("action_last"), 0));
        this.remoteView.setOnClickPendingIntent(R$id.btn_cancel, PendingIntent.getBroadcast(this, 0, new Intent("action_cancel"), 0));
        Log.e(TAG, "initNotificationBarXXX: ");
        if (TextUtils.isEmpty(this.backgroundAudioManager.getCoverImgUrl())) {
            Log.e(TAG, "initNotificationBar000: ");
        } else if (this.backgroundAudioManager.getCoverImgUrl().startsWith("app")) {
            File file = new File(this.backgroundAudioManager.getWebViewFragment().realPath(this.backgroundAudioManager.getCoverImgUrl()));
            Log.e(TAG, "initNotificationBar111: " + file.getAbsolutePath());
            this.remoteView.setImageViewUri(R$id.img, Uri.parse(file.toString()));
        } else {
            Log.e(TAG, "initNotificationBar222: " + this.backgroundAudioManager.getCoverImgUrl());
            SelectionSpec.getInstance().imageEngine.download(WebApplication.getInstance().getApplication(), this.backgroundAudioManager.getCoverImgUrl(), new CommonCallBack<File>() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayService.2
                @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
                public void onResult(File file2) {
                    Log.e(PlayService.TAG, "initNotificationBar333: " + file2.getAbsolutePath());
                    Bitmap zoomImage = ImgUtils.zoomImage(BitmapFactory.decodeFile(file2.getAbsolutePath()), (double) DensityUtil.dip2px(50.0f), (double) DensityUtil.dip2px(50.0f));
                    PlayService.this.remoteView.setImageViewBitmap(R$id.img, zoomImage);
                    Log.e(PlayService.TAG, "onResult: setImageViewBitmap " + zoomImage);
                    PlayService.this.updateRemoteView(notification);
                }

                @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
                public void onFailure(Exception exc) {
                    Log.e(PlayService.TAG, "initNotificationBar444: ");
                    exc.printStackTrace();
                }
            });
        }
        this.remoteView.setTextViewText(R$id.tv_title, this.backgroundAudioManager.getTitle());
        RemoteViews remoteViews = this.remoteView;
        int i = R$id.tv_desc;
        remoteViews.setTextViewText(i, this.backgroundAudioManager.getSinger() + this.backgroundAudioManager.getEpname());
        updateRemoteView(notification);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRemoteView(Notification notification) {
        if (this.isServiceDestroyed) {
            return;
        }
        notification.contentView = this.remoteView;
        this.notificationManager.notify(this.PID, notification);
    }

    private String createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("channelId", "channelName", 3);
            notificationChannel.setDescription("channelDescription");
            notificationChannel.enableVibration(true);
            notificationChannel.setLockscreenVisibility(1);
            ((NotificationManager) context.getSystemService("notification")).createNotificationChannel(notificationChannel);
            return "channelId";
        }
        return null;
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: ");
        return new MyBinder();
    }

    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayService$MyBinder */
    /* loaded from: classes3.dex */
    public class MyBinder extends Binder implements IBinderService {
        public MyBinder() {
        }

        @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.IBinderService
        public ZZBMediaPlayer getMediaPlayer() {
            return PlayService.this.backgroundAudioManager;
        }
    }

    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayService$Mp3Receiver */
    /* loaded from: classes3.dex */
    public class Mp3Receiver extends BroadcastReceiver {
        public Mp3Receiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            char c;
            String action = intent.getAction();
            switch (action.hashCode()) {
                case 1064330403:
                    if (action.equals("action_cancel")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1068040830:
                    if (action.equals("action_center")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1583496959:
                    if (action.equals("action_last")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1583560540:
                    if (action.equals("action_next")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                if (PlayService.this.backgroundAudioManager.isPlaying()) {
                    PlayService.this.backgroundAudioManager.pause();
                } else {
                    PlayService.this.backgroundAudioManager.start();
                }
            } else if (c == 1) {
                Logger.m4112i(PlayService.TAG, "上一首");
            } else if (c == 2) {
                Logger.m4112i(PlayService.TAG, "下一首");
            } else if (c != 3) {
            } else {
                Logger.m4112i(PlayService.TAG, "取消");
                PlayerServiceMananger.getInstance().destroy();
            }
        }
    }
}
