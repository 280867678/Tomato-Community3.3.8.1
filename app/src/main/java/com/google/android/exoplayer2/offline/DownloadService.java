package com.google.android.exoplayer2.offline;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.util.NotificationUtil;
import java.io.IOException;
import java.util.HashMap;

/* loaded from: classes.dex */
public abstract class DownloadService extends Service {
    private static final HashMap<Class<? extends DownloadService>, Object> requirementsHelpers = new HashMap<>();
    @Nullable
    private final String channelId;
    @StringRes
    private final int channelName;
    private DownloadManager downloadManager;
    private DownloadManagerListener downloadManagerListener;
    private final ForegroundNotificationUpdater foregroundNotificationUpdater;
    private int lastStartId;
    private boolean startedInForeground;
    private boolean taskRemoved;

    /* loaded from: classes.dex */
    private final class ForegroundNotificationUpdater implements Runnable {
        public void stopPeriodicUpdates() {
            throw null;
        }
    }

    private void logd(String str) {
    }

    protected abstract DownloadManager getDownloadManager();

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        logd("onCreate");
        String str = this.channelId;
        if (str != null) {
            NotificationUtil.createNotificationChannel(this, str, this.channelName, 2);
        }
        this.downloadManager = getDownloadManager();
        this.downloadManagerListener = new DownloadManagerListener();
        this.downloadManager.addListener(this.downloadManagerListener);
        throw null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0069, code lost:
        if (r3.equals("com.google.android.exoplayer.downloadService.action.INIT") != false) goto L17;
     */
    @Override // android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int onStartCommand(Intent intent, int i, int i2) {
        String str;
        this.lastStartId = i2;
        char c = 0;
        this.taskRemoved = false;
        if (intent != null) {
            str = intent.getAction();
            this.startedInForeground |= intent.getBooleanExtra("foreground", false) || "com.google.android.exoplayer.downloadService.action.RESTART".equals(str);
        } else {
            str = null;
        }
        if (str == null) {
            str = "com.google.android.exoplayer.downloadService.action.INIT";
        }
        logd("onStartCommand action: " + str + " startId: " + i2);
        switch (str.hashCode()) {
            case -871181424:
                if (str.equals("com.google.android.exoplayer.downloadService.action.RESTART")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -382886238:
                if (str.equals("com.google.android.exoplayer.downloadService.action.ADD")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -337334865:
                if (str.equals("com.google.android.exoplayer.downloadService.action.START_DOWNLOADS")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1015676687:
                break;
            case 1286088717:
                if (str.equals("com.google.android.exoplayer.downloadService.action.STOP_DOWNLOADS")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c != 0 && c != 1) {
            if (c == 2) {
                byte[] byteArrayExtra = intent.getByteArrayExtra("download_action");
                if (byteArrayExtra == null) {
                    Log.e("DownloadService", "Ignoring ADD action with no action data");
                } else {
                    try {
                        this.downloadManager.handleAction(byteArrayExtra);
                        throw null;
                    } catch (IOException e) {
                        Log.e("DownloadService", "Failed to handle ADD action", e);
                    }
                }
            } else if (c == 3) {
                this.downloadManager.stopDownloads();
                throw null;
            } else if (c == 4) {
                this.downloadManager.startDownloads();
                throw null;
            } else {
                Log.e("DownloadService", "Ignoring unrecognized action: " + str);
            }
        }
        maybeStartWatchingRequirements();
        throw null;
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent intent) {
        logd("onTaskRemoved rootIntent: " + intent);
        this.taskRemoved = true;
    }

    @Override // android.app.Service
    public void onDestroy() {
        logd("onDestroy");
        this.foregroundNotificationUpdater.stopPeriodicUpdates();
        throw null;
    }

    private void maybeStartWatchingRequirements() {
        this.downloadManager.getDownloadCount();
        throw null;
    }

    /* loaded from: classes3.dex */
    private final class DownloadManagerListener implements DownloadManager.Listener {
        private DownloadManagerListener(DownloadService downloadService) {
        }
    }
}
