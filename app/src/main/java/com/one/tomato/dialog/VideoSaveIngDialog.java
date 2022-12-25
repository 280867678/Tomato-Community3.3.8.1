package com.one.tomato.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.thirdpart.m3u8.download.M3U8DownloadManager;
import com.one.tomato.thirdpart.m3u8.download.M3U8DownloadTask;
import com.one.tomato.thirdpart.m3u8.download.entity.M3U8;
import com.one.tomato.thirdpart.m3u8.download.entity.M3U8Ts;
import com.one.tomato.thirdpart.m3u8.download.listener.ITaskDownloadListener;
import com.one.tomato.thirdpart.m3u8.download.utils.MUtils;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.encrypt.MD5Util;
import com.one.tomato.utils.post.VideoDownloadCountUtils;
import com.one.tomato.widget.CircleProgressBar;
import com.zzbwuhan.beard.BCrypto;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/* loaded from: classes3.dex */
public class VideoSaveIngDialog extends CustomAlertDialog {
    private CircleProgressBar circleProgressBar;
    private Context context;
    private boolean downloading;
    public VideoSaveLocalListener listener;
    private TextView tv_cancel;
    private TextView tv_count;

    /* loaded from: classes3.dex */
    public interface VideoSaveLocalListener {
        void fail();

        void success();
    }

    public VideoSaveIngDialog(Context context, final String str, String str2, int i) {
        super(context, true);
        this.context = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_video_save_ing, (ViewGroup) null);
        this.circleProgressBar = (CircleProgressBar) inflate.findViewById(R.id.circleProgress);
        this.tv_count = (TextView) inflate.findViewById(R.id.tv_count);
        this.tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        setMiddleNeedPadding(false);
        setContentView(inflate);
        bottomLayoutGone();
        setBackgroundResource(R.color.transparent);
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        WindowManager.LayoutParams attributes = this.window.getAttributes();
        attributes.gravity = 17;
        attributes.width = (int) (defaultDisplay.getWidth() * 0.4d);
        attributes.alpha = 1.0f;
        this.window.setAttributes(attributes);
        if (DBUtil.getUserInfo().getVipType() > 0) {
            TextView textView = this.tv_count;
            textView.setText(AppUtil.getString(R.string.video_save_remain_count, VideoDownloadCountUtils.getInstance().getVipSaveCount() + ""));
        } else {
            TextView textView2 = this.tv_count;
            textView2.setText(AppUtil.getString(R.string.video_save_vip_count, VideoDownloadCountUtils.getInstance().getVipSaveTotalCount() + ""));
        }
        final M3U8DownloadTask m3U8DownloadTask = new M3U8DownloadTask();
        m3U8DownloadTask.download(false, str, str2, i, new ITaskDownloadListener() { // from class: com.one.tomato.dialog.VideoSaveIngDialog.1
            @Override // com.one.tomato.thirdpart.m3u8.download.listener.ITaskDownloadListener
            public void onStartDownload(M3U8 m3u8, long j, int i2, int i3) {
                VideoSaveIngDialog.this.downloading = true;
            }

            @Override // com.one.tomato.thirdpart.m3u8.download.listener.ITaskDownloadListener
            public void onProgress(M3U8 m3u8, long j, int i2, int i3) {
                VideoSaveIngDialog.this.downloading = true;
                VideoSaveIngDialog.this.circleProgressBar.setCurrentPercent(i2, i3);
            }

            @Override // com.one.tomato.thirdpart.m3u8.download.listener.ITaskDownloadListener
            public void onSuccess(M3U8 m3u8) {
                VideoSaveIngDialog.this.saveVideoToLocal(m3u8, str);
            }

            @Override // com.one.tomato.thirdpart.m3u8.download.listener.ITaskDownloadListener
            public void onError(M3U8 m3u8, Throwable th) {
                VideoSaveIngDialog.this.downloading = false;
                VideoSaveIngDialog.this.dismiss();
                ToastUtil.showCenterToast((int) R.string.video_save_local_fail);
                VideoSaveLocalListener videoSaveLocalListener = VideoSaveIngDialog.this.listener;
                if (videoSaveLocalListener != null) {
                    videoSaveLocalListener.fail();
                }
            }
        });
        this.tv_cancel.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.VideoSaveIngDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VideoSaveIngDialog.this.downloading = false;
                m3U8DownloadTask.stop("保存到本地下载停止");
                VideoSaveIngDialog.this.dismiss();
            }
        });
        setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.one.tomato.dialog.VideoSaveIngDialog.3
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
                return VideoSaveIngDialog.this.downloading;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"checkResult"})
    public void saveVideoToLocal(final M3U8 m3u8, final String str) {
        String md5 = MD5Util.md5(MUtils.generatePrimaryKeyUrl(str));
        File sDDCIMDir = FileUtil.getSDDCIMDir();
        final File file = new File(sDDCIMDir, md5 + ".mp4");
        Observable.create(new ObservableOnSubscribe<Boolean>(this) { // from class: com.one.tomato.dialog.VideoSaveIngDialog.5
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Boolean> observableEmitter) throws Exception {
                long longValue = m3u8.getKeyPtr().longValue();
                String dirFilePath = m3u8.getDirFilePath();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                for (M3U8Ts m3U8Ts : m3u8.getTsList()) {
                    File file2 = new File(dirFilePath, m3U8Ts.getUrl());
                    if (!file2.exists()) {
                        observableEmitter.onNext(false);
                        return;
                    }
                    FileInputStream fileInputStream = new FileInputStream(file2);
                    byte[] bArr = new byte[5000];
                    long j = 0;
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read != -1) {
                            fileOutputStream.write(bArr, 0, BCrypto.decodeVideoBuf2(bArr, read, longValue, (int) j));
                            j += read;
                        }
                    }
                    fileInputStream.close();
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                observableEmitter.onNext(true);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() { // from class: com.one.tomato.dialog.VideoSaveIngDialog.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) throws Exception {
                if (bool.booleanValue()) {
                    ToastUtil.showCenterToast((int) R.string.video_save_local_success);
                    m3u8.getDirFilePath();
                    VideoSaveIngDialog.this.context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
                    VideoSaveLocalListener videoSaveLocalListener = VideoSaveIngDialog.this.listener;
                    if (videoSaveLocalListener != null) {
                        videoSaveLocalListener.success();
                    }
                } else {
                    ToastUtil.showCenterToast((int) R.string.video_save_local_fail);
                    M3U8DownloadManager.getInstance().cancelAndDelete(str, !m3u8.isPreDownload());
                    VideoSaveLocalListener videoSaveLocalListener2 = VideoSaveIngDialog.this.listener;
                    if (videoSaveLocalListener2 != null) {
                        videoSaveLocalListener2.fail();
                    }
                }
                VideoSaveIngDialog.this.dismiss();
            }
        });
    }

    public void setVideoSaveLocalListener(VideoSaveLocalListener videoSaveLocalListener) {
        this.listener = videoSaveLocalListener;
    }
}
