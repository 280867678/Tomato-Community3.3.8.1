package com.one.tomato.utils;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.p053s3.transfermanager.TransferManager;
import com.amazonaws.regions.Region;
import com.amazonaws.services.p054s3.AmazonS3;
import com.amazonaws.services.p054s3.AmazonS3Client;
import com.amazonaws.services.p054s3.S3ClientOptions;
import com.amazonaws.services.p054s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.p054s3.model.CannedAccessControlList;
import com.amazonaws.services.p054s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.p054s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.p054s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.p054s3.model.UploadPartRequest;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.entity.TTSts;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.domain.DomainRequest;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.encrypt.AESUtil;
import com.one.tomato.utils.encrypt.MD5Util;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes3.dex */
public class TTUtil {
    private String bucketName;
    private int fileType;
    private UploadFileToTTListener listener;

    /* renamed from: tm */
    private TransferManager f1758tm;
    private AmazonS3 s3Client = null;
    Handler handler = new Handler() { // from class: com.one.tomato.utils.TTUtil.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 1) {
                if (TTUtil.this.listener == null) {
                    return;
                }
                TTUtil.this.listener.uploadSuccess((LocalMedia) message.obj);
            } else if (i != 2) {
            } else {
                if (TTUtil.this.listener != null) {
                    TTUtil.this.listener.uploadFail();
                }
                DomainRequest.getInstance().switchDomainUrlByType("ttUpload");
            }
        }
    };

    /* loaded from: classes3.dex */
    public interface UploadFileToTTListener {
        void start();

        void uploadFail();

        void uploadSuccess(LocalMedia localMedia);
    }

    public TTUtil(int i, UploadFileToTTListener uploadFileToTTListener) {
        this.fileType = i;
        this.listener = uploadFileToTTListener;
    }

    public void getStsToken(final List<LocalMedia> list) {
        UploadFileToTTListener uploadFileToTTListener = this.listener;
        if (uploadFileToTTListener != null) {
            uploadFileToTTListener.start();
        }
        StringBuilder sb = new StringBuilder();
        long j = 0;
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i).getPath());
            sb.append(MD5Util.md5(file));
            if (i < list.size() - 1) {
                sb.append(";");
            }
            j += file.length();
        }
        ApiImplService.Companion.getApiImplService().getSTSToken(DBUtil.getMemberId(), sb.toString(), j).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<TTSts>() { // from class: com.one.tomato.utils.TTUtil.2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(TTSts tTSts) {
                if (tTSts == null) {
                    if (TTUtil.this.listener == null) {
                        return;
                    }
                    TTUtil.this.listener.uploadFail();
                    return;
                }
                TTUtil.this.bucketName = tTSts.getBucketName();
                try {
                    String decryptAES = AESUtil.decryptAES(tTSts.getSecJson(), tTSts.getKey());
                    TTUtil.this.initS3Client((TTSts.TTKeys) BaseApplication.getGson().fromJson(decryptAES, (Class<Object>) TTSts.TTKeys.class));
                    TTUtil.this.uploadToTT(list);
                } catch (Exception e) {
                    LogUtil.m3787d("yan", "tt上傳文件報錯---------" + e.getMessage());
                    if (TTUtil.this.listener != null) {
                        TTUtil.this.listener.uploadFail();
                    }
                    e.printStackTrace();
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                if (TTUtil.this.listener != null) {
                    TTUtil.this.listener.uploadFail();
                }
            }
        });
    }

    public void initS3Client(TTSts.TTKeys tTKeys) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setMaxErrorRetry(3);
        clientConfiguration.setConnectionTimeout(60000);
        clientConfiguration.setSocketTimeout(60000);
        clientConfiguration.setProtocol(Protocol.HTTP);
        clientConfiguration.setCurlLogging(true);
        this.s3Client = new AmazonS3Client(new BasicAWSCredentials(tTKeys.getAccessKey(), tTKeys.getSecretKey()), clientConfiguration);
        this.s3Client.setRegion(Region.getRegion("us-east-1"));
        S3ClientOptions.Builder builder = S3ClientOptions.builder();
        builder.setPathStyleAccess(true);
        this.s3Client.setS3ClientOptions(builder.build());
        String ttUploadUrl = DomainServer.getInstance().getTtUploadUrl();
        LogUtil.m3784i("tt上传url：" + ttUploadUrl);
        this.s3Client.setEndpoint(ttUploadUrl);
    }

    public void uploadToTT(final List<LocalMedia> list) {
        new Thread(new Runnable() { // from class: com.one.tomato.utils.TTUtil.3
            @Override // java.lang.Runnable
            public void run() {
                List list2 = list;
                if (list2 == null || list2.isEmpty()) {
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    LocalMedia localMedia = (LocalMedia) list.get(i);
                    File file = new File(localMedia.getPath());
                    Message obtain = Message.obtain();
                    try {
                        if (!TextUtils.isEmpty(TTUtil.this.fileBlockUpload(localMedia, file))) {
                            obtain.what = 1;
                            obtain.obj = localMedia;
                        } else {
                            obtain.what = 2;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        obtain.what = 2;
                    }
                    TTUtil.this.handler.sendMessage(obtain);
                    if (obtain.what == 2) {
                        return;
                    }
                }
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String fileBlockUpload(LocalMedia localMedia, File file) {
        ArrayList arrayList = new ArrayList();
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(this.bucketName, getCeph(localMedia));
        initiateMultipartUploadRequest.setCannedACL(CannedAccessControlList.PublicRead);
        InitiateMultipartUploadResult initiateMultipartUpload = this.s3Client.initiateMultipartUpload(initiateMultipartUploadRequest);
        long fileOrDirSize = FileUtil.getFileOrDirSize(file);
        int i = 1;
        long j = 0;
        long j2 = 5242880;
        while (j < fileOrDirSize) {
            try {
                j2 = Math.min(j2, fileOrDirSize - j);
                try {
                    arrayList.add(this.s3Client.uploadPart(new UploadPartRequest().withBucketName(this.bucketName).withKey(getCeph(localMedia)).withUploadId(initiateMultipartUpload.getUploadId()).withPartNumber(i).withFileOffset(j).withFile(file).withPartSize(j2)).getPartETag());
                } catch (Exception e) {
                    e.printStackTrace();
                    PrintStream printStream = System.err;
                    printStream.println("上传失败的子部分序号：" + i);
                }
                j += j2;
                PrintStream printStream2 = System.err;
                printStream2.println("已上传字节：" + j);
                i++;
            } catch (Exception e2) {
                this.s3Client.abortMultipartUpload(new AbortMultipartUploadRequest(this.bucketName, getCeph(localMedia), initiateMultipartUpload.getUploadId()));
                e2.printStackTrace();
                return null;
            }
        }
        return this.s3Client.completeMultipartUpload(new CompleteMultipartUploadRequest(this.bucketName, getCeph(localMedia), initiateMultipartUpload.getUploadId(), arrayList)).getETag();
    }

    public String getCeph(LocalMedia localMedia) {
        File file = new File(localMedia.getPath());
        StringBuilder sb = new StringBuilder();
        int i = this.fileType;
        String str = "";
        String str2 = ".png";
        if (i == 1) {
            str = "_s3/images/";
        } else if (i == 2) {
            str = "_s3/avatars/";
        } else if (i != 3) {
            str2 = str;
        } else if (localMedia.isCover()) {
            str = "_s3/covers/";
        } else {
            str = "_s3/videos/";
            str2 = ".mp4";
        }
        String formatTime = FormatUtil.formatTime("yyyyMMdd", new Date());
        String name = file.getName();
        String md5 = MD5Util.md5(file);
        if (name.contains(".")) {
            str2 = name.substring(name.lastIndexOf("."));
        }
        sb.append(str);
        sb.append(formatTime);
        sb.append("/");
        sb.append(md5);
        sb.append(str2);
        return sb.toString().trim();
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void close() {
        this.s3Client = null;
        TransferManager transferManager = this.f1758tm;
        if (transferManager != null) {
            transferManager.shutdownNow(true);
        }
    }
}
