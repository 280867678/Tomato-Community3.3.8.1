package com.one.tomato.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.p002v4.app.NotificationCompat;
import android.text.TextUtils;
import com.broccoli.p150bh.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.entity.PostSame;
import com.one.tomato.entity.PublishInfo;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.AppSecretUtil;
import com.one.tomato.utils.BitmapUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DeviceInfoUtil;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.TTUtil;
import com.one.tomato.utils.encrypt.MD5Util;
import com.one.tomato.utils.post.PapaPublishUtil;
import com.one.tomato.utils.post.PublishUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import org.slf4j.Marker;

/* loaded from: classes3.dex */
public class PublishService extends Service {
    private int downPrice;
    private int price;
    private PublishInfo publishInfo;
    private int publishType;
    private TTUtil ttUtil;
    private ArrayList<LocalMedia> selectList = new ArrayList<>();
    private ArrayList<LocalMedia> uploadSuccessList = new ArrayList<>();

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void startService(Context context, PublishInfo publishInfo, int i, int i2) {
        Intent intent = new Intent();
        intent.setClass(context, PublishService.class);
        intent.putExtra("publish_info", publishInfo);
        intent.putExtra("publish_info_price", i);
        intent.putExtra("publish_info_down_price", i2);
        if (DeviceInfoUtil.isOverO()) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (DeviceInfoUtil.isOverO()) {
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(new NotificationChannel("publish_channel_id", "publish_channel_name", 3));
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "publish_channel_id");
            builder.setChannelId("publish_channel_id").setContentTitle("New Message").setContentText("You've received new messages.").setSmallIcon(R.drawable.ic_launcher);
            startForeground(2, builder.build());
        }
        if (intent == null || intent.getExtras() == null) {
            stopSelf();
            return super.onStartCommand(intent, i, i2);
        }
        this.publishInfo = (PublishInfo) intent.getExtras().getParcelable("publish_info");
        this.price = intent.getExtras().getInt("publish_info_price");
        this.downPrice = intent.getExtras().getInt("publish_info_down_price");
        this.publishType = this.publishInfo.getPostType();
        if (this.publishType == 3) {
            publishPost();
            return super.onStartCommand(intent, i, i2);
        }
        this.selectList.clear();
        if (this.publishInfo.getSelectList() != null && !this.publishInfo.getSelectList().isEmpty()) {
            this.selectList.addAll(this.publishInfo.getSelectList());
        }
        this.uploadSuccessList.clear();
        int i3 = this.publishType;
        if (i3 == 1) {
            PublishUtil.getInstance().onPublishIng();
            uploadTT(1, this.selectList);
        } else if (i3 == 2) {
            PublishUtil.getInstance().onPublishIng();
            asynBuildImage(this.selectList, 3);
        } else if (i3 == 4) {
            PapaPublishUtil.getInstance().onPublishIng();
            asynBuildImage(this.selectList, 3);
        }
        return super.onStartCommand(intent, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadTT(int i, final ArrayList<LocalMedia> arrayList) {
        if (arrayList == null && arrayList.isEmpty()) {
            LogUtil.m3785e("yan", "獲取視頻封面為空");
            return;
        }
        this.ttUtil = new TTUtil(i, new TTUtil.UploadFileToTTListener() { // from class: com.one.tomato.service.PublishService.1
            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void start() {
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadSuccess(LocalMedia localMedia) {
                PublishService.this.uploadSuccessList.add(localMedia);
                if (PublishService.this.uploadSuccessList.size() == arrayList.size()) {
                    PublishService.this.publishPost();
                }
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadFail() {
                PublishService.this.publishFail(new ResponseThrowable("", -111));
            }
        });
        this.ttUtil.getStsToken(arrayList);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean stopService(Intent intent) {
        TTUtil tTUtil = this.ttUtil;
        if (tTUtil != null) {
            tTUtil.close();
        }
        return super.stopService(intent);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        if (DeviceInfoUtil.isOverO()) {
            stopForeground(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0148, code lost:
        if (r9 != 4) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:64:0x02bc  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x02c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void publishPost() {
        int i;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("postType", Integer.valueOf(this.publishType));
        hashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        hashMap.put("title", AppSecretUtil.decodeResponse(this.publishInfo.getTitle()));
        hashMap.put("description", !TextUtils.isEmpty(this.publishInfo.getDescription()) ? AppSecretUtil.decodeResponse(this.publishInfo.getDescription()) : "");
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        StringBuilder sb5 = new StringBuilder();
        for (int i2 = 0; i2 < this.uploadSuccessList.size(); i2++) {
            LocalMedia localMedia = this.uploadSuccessList.get(i2);
            sb2.append(MD5Util.md5(new File(localMedia.getPath())));
            String ceph = this.ttUtil.getCeph(localMedia);
            if (localMedia.isCover()) {
                sb5.append("/");
                sb5.append(this.ttUtil.getBucketName());
                sb5.append("/");
                sb5.append(ceph);
                sb3.append(localMedia.getWidth() + Marker.ANY_MARKER + localMedia.getHeight());
            } else {
                sb.append("/");
                sb.append(this.ttUtil.getBucketName());
                sb.append("/");
                sb.append(ceph);
                if (this.publishType == 1) {
                    sb3.append(localMedia.getWidth() + Marker.ANY_MARKER + localMedia.getHeight());
                }
            }
            if (localMedia.getDuration() > 0) {
                sb4.append(stringForTime(localMedia.getDuration()));
            }
            if (i2 < this.uploadSuccessList.size() - 1) {
                sb2.append(";");
                if (this.publishType == 1) {
                    sb.append(";");
                    sb3.append(";");
                }
            }
        }
        int i3 = this.publishType;
        if (i3 == 1) {
            hashMap.put("groupId", Integer.valueOf(this.publishInfo.getCircleList().getId()));
            hashMap.put("imageUrl", sb.toString());
            hashMap.put("size", sb3.toString());
            hashMap.put("fileMd5", sb2.toString());
        } else {
            if (i3 != 2) {
                if (i3 == 3) {
                    hashMap.put("groupId", Integer.valueOf(this.publishInfo.getCircleList().getId()));
                }
            }
            if (this.publishType == 2) {
                hashMap.put("groupId", Integer.valueOf(this.publishInfo.getCircleList().getId()));
            }
            hashMap.put("videoUrl", sb.toString());
            hashMap.put("videoTime", sb4.toString());
            hashMap.put("videoCover", sb5.toString());
            hashMap.put("size", sb3.toString());
            hashMap.put("fileMd5", sb2.toString());
        }
        if (this.publishInfo.getTagList() != null && !this.publishInfo.getTagList().isEmpty()) {
            StringBuilder sb6 = new StringBuilder();
            StringBuilder sb7 = new StringBuilder();
            for (int i4 = 0; i4 < this.publishInfo.getTagList().size(); i4++) {
                if (this.publishInfo.getTagList().get(i4).getTagId() != 0) {
                    sb6.append(this.publishInfo.getTagList().get(i4).getTagId());
                    if (i4 < this.publishInfo.getTagList().size() - 1) {
                        sb6.append(",");
                    }
                } else {
                    sb7.append(this.publishInfo.getTagList().get(i4).getTagName());
                    if (i4 < this.publishInfo.getTagList().size() - 1) {
                        sb7.append(",");
                    }
                }
            }
            hashMap.put("tagids", sb6.toString());
            hashMap.put("tagNames", sb7.toString());
        } else {
            hashMap.put("tagids", "");
        }
        hashMap.put("anonymous", 0);
        int i5 = this.price;
        if (i5 > 0) {
            hashMap.put("price", Integer.valueOf(i5));
        }
        int i6 = this.downPrice;
        if (i6 > 0) {
            hashMap.put("downPrice", Integer.valueOf(i6));
            hashMap.put("canDownload", 1);
        } else if (i6 == -1) {
            hashMap.put("canDownload", 1);
        } else {
            i = 0;
            hashMap.put("canDownload", 0);
            if (!this.publishInfo.isOriginal()) {
                hashMap.put("originalFlag", 1);
            } else {
                hashMap.put("originalFlag", Integer.valueOf(i));
            }
            ApiImplService.Companion.getApiImplService().sendPost(hashMap).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<PostSame>() { // from class: com.one.tomato.service.PublishService.2
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(PostSame postSame) {
                    if (PublishService.this.publishType == 4) {
                        PapaPublishUtil.getInstance().onPublishSuccess();
                    } else {
                        PublishUtil.getInstance().onPublishSuccess();
                    }
                    DBUtil.getUserInfo().setAlreadyPublishCnt(DBUtil.getUserInfo().getAlreadyPublishCnt() + 1);
                    DBUtil.getUserInfo().saveOrUpdate(new String[0]);
                    PublishService.this.stopSelf();
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    PublishService.this.publishFail(responseThrowable);
                }
            });
            if (this.publishType != 4) {
                PapaPublishUtil.getInstance().onPublishIng();
                return;
            } else {
                PublishUtil.getInstance().onPublishIng();
                return;
            }
        }
        i = 0;
        if (!this.publishInfo.isOriginal()) {
        }
        ApiImplService.Companion.getApiImplService().sendPost(hashMap).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<PostSame>() { // from class: com.one.tomato.service.PublishService.2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(PostSame postSame) {
                if (PublishService.this.publishType == 4) {
                    PapaPublishUtil.getInstance().onPublishSuccess();
                } else {
                    PublishUtil.getInstance().onPublishSuccess();
                }
                DBUtil.getUserInfo().setAlreadyPublishCnt(DBUtil.getUserInfo().getAlreadyPublishCnt() + 1);
                DBUtil.getUserInfo().saveOrUpdate(new String[0]);
                PublishService.this.stopSelf();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                PublishService.this.publishFail(responseThrowable);
            }
        });
        if (this.publishType != 4) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void publishFail(ResponseThrowable responseThrowable) {
        if (this.publishType == 4) {
            PapaPublishUtil.getInstance().onPublishFail(this.publishInfo, responseThrowable);
        } else {
            PublishUtil.getInstance().onPublishFail(this.publishInfo, responseThrowable);
        }
        stopSelf();
    }

    protected String stringForTime(long j) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.getDefault());
        int i = ((int) j) / 1000;
        int i2 = i % 60;
        int i3 = (i / 60) % 60;
        int i4 = i / 3600;
        sb.setLength(0);
        return i4 > 0 ? formatter.format("%d:%02d:%02d", Integer.valueOf(i4), Integer.valueOf(i3), Integer.valueOf(i2)).toString() : formatter.format("%02d:%02d", Integer.valueOf(i3), Integer.valueOf(i2)).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<LocalMedia> buildVideoCover(ArrayList<LocalMedia> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(arrayList);
        for (int i = 0; i < arrayList2.size(); i++) {
            LocalMedia localMedia = (LocalMedia) arrayList2.get(i);
            Bitmap compressImage = BitmapUtil.compressImage(ThumbnailUtils.createVideoThumbnail(localMedia.getPath(), 2));
            if (compressImage != null) {
                File imageCacheDir = FileUtil.getImageCacheDir();
                File file = new File(imageCacheDir, MD5Util.md5(localMedia.getPath()) + ".png");
                if (BitmapUtil.saveImage(compressImage, file.getPath())) {
                    LocalMedia localMedia2 = new LocalMedia();
                    localMedia2.setPath(file.getPath());
                    localMedia2.setWidth(compressImage.getWidth());
                    localMedia2.setHeight(compressImage.getHeight());
                    localMedia2.setMimeType(PictureMimeType.ofImage());
                    localMedia2.setCover(true);
                    arrayList.add(localMedia2);
                    compressImage.recycle();
                }
            }
        }
        return arrayList;
    }

    private void asynBuildImage(final ArrayList<LocalMedia> arrayList, final int i) {
        Observable create = Observable.create(new ObservableOnSubscribe<ArrayList<LocalMedia>>() { // from class: com.one.tomato.service.PublishService.3
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<ArrayList<LocalMedia>> observableEmitter) throws Exception {
                PublishService publishService = PublishService.this;
                ArrayList<LocalMedia> arrayList2 = arrayList;
                publishService.buildVideoCover(arrayList2);
                observableEmitter.onNext(arrayList2);
                observableEmitter.onComplete();
            }
        });
        create.subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ArrayList<LocalMedia>>() { // from class: com.one.tomato.service.PublishService.4
            @Override // io.reactivex.functions.Consumer
            public void accept(ArrayList<LocalMedia> arrayList2) throws Exception {
                PublishService.this.uploadTT(i, arrayList2);
            }
        });
    }
}
