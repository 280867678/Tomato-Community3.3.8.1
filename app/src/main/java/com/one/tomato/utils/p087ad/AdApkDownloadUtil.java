package com.one.tomato.utils.p087ad;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.RemoteViews;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.download.DownLoadManager;
import com.one.tomato.mvp.base.okhttp.download.ProgressCallBack;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DeviceInfoUtil;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.encrypt.Base64Util;
import java.io.File;
import okhttp3.ResponseBody;

/* renamed from: com.one.tomato.utils.ad.AdApkDownloadUtil */
/* loaded from: classes3.dex */
public class AdApkDownloadUtil {
    private static AdApkDownloadUtil instance;
    private Notification notification;
    private int preProgress;
    private boolean downloadIng = false;
    private Context mContext = BaseApplication.getApplication();
    private NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
    private RemoteViews remoteViews = new RemoteViews(this.mContext.getPackageName(), (int) R.layout.dialog_update_notification);

    private AdApkDownloadUtil() {
    }

    public static AdApkDownloadUtil getInstance() {
        if (instance == null) {
            instance = new AdApkDownloadUtil();
        }
        return instance;
    }

    public void downloadApk(String str) {
        String path = FileUtil.getApkCacheDir().getPath();
        String str2 = Base64Util.base64EncodeStr(str) + ".apk";
        String str3 = str2 + ".tmp";
        final String str4 = path + File.separator + str2;
        final File file = new File(str4);
        final File file2 = new File(path + File.separator + str3);
        if (file.exists()) {
            AppUtil.installApk(str4);
        } else if (this.downloadIng) {
            ToastUtil.showCenterToast((int) R.string.common_ad_download_task);
        } else {
            this.downloadIng = true;
            LogUtil.m3784i("广告下载地址：" + str);
            createNotification();
            DownLoadManager.getInstance().load(str, new ProgressCallBack<ResponseBody>(path, str3) { // from class: com.one.tomato.utils.ad.AdApkDownloadUtil.1
                @Override // com.one.tomato.mvp.base.okhttp.download.ProgressCallBack
                public void onSuccess(ResponseBody responseBody) {
                    AdApkDownloadUtil.this.cancelNotify();
                    File file3 = file2;
                    if (file3 != null && file3.length() == responseBody.contentLength()) {
                        if (!file2.renameTo(file)) {
                            return;
                        }
                        AppUtil.installApk(str4);
                        return;
                    }
                    LogUtil.m3786e("下载文件不完整");
                    AdApkDownloadUtil.this.cancelNotify();
                }

                @Override // com.one.tomato.mvp.base.okhttp.download.ProgressCallBack
                public void progress(long j, long j2) {
                    AdApkDownloadUtil.this.notifyChange((int) (((j * 1.0d) / j2) * 100.0d));
                }

                @Override // com.one.tomato.mvp.base.okhttp.download.ProgressCallBack
                public void onError(Throwable th) {
                    AdApkDownloadUtil.this.cancelNotify();
                }
            });
        }
    }

    private void createNotification() {
        if (DeviceInfoUtil.isOverO()) {
            this.notificationManager.createNotificationChannel(new NotificationChannel("ad_download_channel_id", "ad_download_channel_name", 4));
            Notification.Builder builder = new Notification.Builder(this.mContext, "ad_download_channel_id");
            builder.setChannelId("ad_download_channel_id");
            builder.setSmallIcon(R.drawable.ic_launcher).setContentTitle(AppUtil.getString(R.string.uptate_ing)).setCustomContentView(this.remoteViews).setOnlyAlertOnce(true).setOngoing(false).setAutoCancel(false).setNumber(1);
            this.notification = builder.build();
            this.notificationManager.notify(3, this.notification);
            return;
        }
        this.notification = new Notification();
        Notification notification = this.notification;
        notification.icon = R.drawable.ic_launcher;
        notification.tickerText = AppUtil.getString(R.string.uptate_ing);
        Notification notification2 = this.notification;
        notification2.contentView = this.remoteViews;
        this.notificationManager.notify(3, notification2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyChange(int i) {
        if (this.notification == null) {
            LogUtil.m3784i("Notification出现为空的情况");
            createNotification();
        }
        if (i - this.preProgress > 0.5d) {
            this.preProgress = i;
            RemoteViews remoteViews = this.notification.contentView;
            remoteViews.setTextViewText(R.id.tv_progress, AppUtil.getString(R.string.update_download_progress, Integer.valueOf(this.preProgress)) + "%");
            this.notification.contentView.setProgressBar(R.id.progress, 100, this.preProgress, false);
            this.notificationManager.notify(3, this.notification);
        }
    }

    public void cancelNotify() {
        this.downloadIng = false;
        this.preProgress = 0;
        NotificationManager notificationManager = this.notificationManager;
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
        this.notification = null;
    }
}
