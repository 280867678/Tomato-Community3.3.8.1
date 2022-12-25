package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.ZZBMediaPlayer;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayerServiceMananger */
/* loaded from: classes3.dex */
public class PlayerServiceMananger implements IBinderService {
    private static final String TAG = "PlayerServiceMananger";
    private static final PlayerServiceMananger ourInstance = new PlayerServiceMananger();
    private MyConn conn;
    private Intent intent;
    private boolean isStarted = false;
    private OnServiceStateListener onServiceStateListener;
    private IBinderService serviceBinder;

    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayerServiceMananger$OnServiceStateListener */
    /* loaded from: classes3.dex */
    public interface OnServiceStateListener {
        void onServiceCreated();
    }

    public static PlayerServiceMananger getInstance() {
        return ourInstance;
    }

    private PlayerServiceMananger() {
    }

    public void start(OnServiceStateListener onServiceStateListener) {
        this.onServiceStateListener = onServiceStateListener;
        Logger.m4112i(TAG, "start: ");
        this.intent = new Intent(WebApplication.getInstance().getApplication(), PlayService.class);
        WebApplication.getInstance().getApplication().startService(this.intent);
        unbind();
        this.conn = new MyConn();
        Application application = WebApplication.getInstance().getApplication();
        Intent intent = this.intent;
        MyConn myConn = this.conn;
        WebApplication.getInstance().getApplication();
        application.bindService(intent, myConn, 1);
    }

    public void destroy() {
        Logger.m4112i(TAG, "destroy1: ");
        unbind();
        if (this.intent != null) {
            Logger.m4112i(TAG, "destroy3: ");
            WebApplication.getInstance().getApplication().stopService(this.intent);
            this.intent = null;
        }
        this.isStarted = false;
    }

    private void unbind() {
        if (this.conn != null) {
            Logger.m4112i(TAG, "destroy2: ");
            WebApplication.getInstance().getApplication().unbindService(this.conn);
            this.conn = null;
            this.serviceBinder = null;
        }
    }

    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.IBinderService
    public ZZBMediaPlayer getMediaPlayer() {
        if (checkBinder()) {
            return this.serviceBinder.getMediaPlayer();
        }
        return null;
    }

    public void change() {
        ZZBMediaPlayer mediaPlayer = getMediaPlayer();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }
    }

    private boolean checkBinder() {
        if (this.serviceBinder == null) {
            Logger.m4112i(TAG, "please wait the serviceBinder created");
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.media.audio.background.PlayerServiceMananger$MyConn */
    /* loaded from: classes3.dex */
    public class MyConn implements ServiceConnection {
        private MyConn() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Logger.m4112i(PlayerServiceMananger.TAG, "绑定后台播放服务成功");
            PlayerServiceMananger.this.serviceBinder = (IBinderService) iBinder;
            if (PlayerServiceMananger.this.onServiceStateListener != null) {
                PlayerServiceMananger.this.onServiceStateListener.onServiceCreated();
            }
            PlayerServiceMananger.this.isStarted = true;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Logger.m4112i(PlayerServiceMananger.TAG, "绑定后台播放服务失败");
            PlayerServiceMananger.this.isStarted = false;
        }
    }

    public boolean isStarted() {
        return this.isStarted;
    }
}
