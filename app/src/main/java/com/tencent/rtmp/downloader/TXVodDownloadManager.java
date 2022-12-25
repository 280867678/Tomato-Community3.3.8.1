package com.tencent.rtmp.downloader;

import android.net.Uri;
import android.text.TextUtils;
import com.tencent.ijk.media.player.IjkDownloadCenter;
import com.tencent.ijk.media.player.IjkDownloadMedia;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.network.TXCVodPlayerNetApi;
import com.tencent.liteav.network.TXCVodPlayerNetListener;
import com.tencent.liteav.network.TXPlayInfoResponse;
import com.tencent.liteav.network.TXPlayInfoStream;
import com.tencent.rtmp.TXPlayerAuthBuilder;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class TXVodDownloadManager {
    public static final int DOWNLOAD_AUTH_FAILED = -5001;
    public static final int DOWNLOAD_DISCONNECT = -5005;
    public static final int DOWNLOAD_FORMAT_ERROR = -5004;
    public static final int DOWNLOAD_HLS_KEY_ERROR = -5006;
    public static final int DOWNLOAD_NO_FILE = -5003;
    public static final int DOWNLOAD_PATH_ERROR = -5007;
    public static final int DOWNLOAD_SUCCESS = 0;
    private static final int IJKDM_EVT_FILE_OPEN_ERROR = 1008;
    private static final int IJKDM_EVT_HLS_KEY_ERROR = 1008;
    private static final int IJKDM_EVT_NET_DISCONNECT = 1001;
    private static final String TAG = "TXVodDownloadManager";
    private static TXVodDownloadManager instance;
    protected String mDownloadPath;
    protected ITXVodDownloadListener mListener;
    IjkDownloadCenter.OnDownloadListener mDownloadCenterListener = new IjkDownloadCenter.OnDownloadListener() { // from class: com.tencent.rtmp.downloader.TXVodDownloadManager.2
        @Override // com.tencent.ijk.media.player.IjkDownloadCenter.OnDownloadListener
        public void downloadBegin(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia) {
            TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
            if (convertMedia != null) {
                TXCLog.m2913i(TXVodDownloadManager.TAG, "downloadBegin " + convertMedia.playPath);
                TXVodDownloadManager.this.mListener.onDownloadStart(convertMedia);
                if (new File(convertMedia.playPath).isFile()) {
                    TXCLog.m2915d(TXVodDownloadManager.TAG, "file state ok");
                } else {
                    TXCLog.m2914e(TXVodDownloadManager.TAG, "file not create!");
                }
            }
        }

        @Override // com.tencent.ijk.media.player.IjkDownloadCenter.OnDownloadListener
        public void downloadEnd(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia) {
            TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
            if (convertMedia != null) {
                TXCLog.m2913i(TXVodDownloadManager.TAG, "downloadEnd " + convertMedia.playPath);
                TXVodDownloadManager.this.mMediaInfoArray.remove(convertMedia);
                TXVodDownloadManager.this.mListener.onDownloadStop(convertMedia);
            }
        }

        @Override // com.tencent.ijk.media.player.IjkDownloadCenter.OnDownloadListener
        public void downloadFinish(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia) {
            TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
            if (convertMedia != null) {
                TXCLog.m2913i(TXVodDownloadManager.TAG, "downloadFinish " + convertMedia.playPath);
                TXVodDownloadManager.this.mMediaInfoArray.remove(convertMedia);
                if (new File(convertMedia.playPath).isFile()) {
                    TXVodDownloadManager.this.mListener.onDownloadFinish(convertMedia);
                } else {
                    TXVodDownloadManager.this.mListener.onDownloadError(convertMedia, TXVodDownloadManager.DOWNLOAD_NO_FILE, "文件已被删除");
                }
            }
        }

        @Override // com.tencent.ijk.media.player.IjkDownloadCenter.OnDownloadListener
        public void downloadError(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia, int i, String str) {
            TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
            if (convertMedia != null) {
                TXCLog.m2914e(TXVodDownloadManager.TAG, "downloadError " + convertMedia.playPath + ConstantUtils.PLACEHOLDER_STR_ONE + str);
                TXVodDownloadManager.this.mMediaInfoArray.remove(convertMedia);
                if (convertMedia.isStop) {
                    TXVodDownloadManager.this.mListener.onDownloadStop(convertMedia);
                } else if (i == 1008) {
                    TXVodDownloadManager.this.mListener.onDownloadError(convertMedia, TXVodDownloadManager.DOWNLOAD_HLS_KEY_ERROR, str);
                } else {
                    TXVodDownloadManager.this.mListener.onDownloadError(convertMedia, TXVodDownloadManager.DOWNLOAD_DISCONNECT, str);
                }
            }
        }

        @Override // com.tencent.ijk.media.player.IjkDownloadCenter.OnDownloadListener
        public void downloadProgress(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia) {
            TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
            if (convertMedia != null) {
                TXVodDownloadManager.this.mListener.onDownloadProgress(convertMedia);
            }
        }

        @Override // com.tencent.ijk.media.player.IjkDownloadCenter.OnDownloadListener
        public int hlsKeyVerify(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia, String str, byte[] bArr) {
            TXVodDownloadMediaInfo convertMedia = TXVodDownloadManager.this.convertMedia(ijkDownloadMedia);
            if (convertMedia != null) {
                return TXVodDownloadManager.this.mListener.hlsKeyVerify(convertMedia, str, bArr);
            }
            return 0;
        }
    };
    protected IjkDownloadCenter mDownloadCenter = IjkDownloadCenter.getInstance();
    protected ArrayList<TXVodDownloadMediaInfo> mMediaInfoArray = new ArrayList<>();

    private TXVodDownloadManager() {
        this.mDownloadCenter.setListener(this.mDownloadCenterListener);
    }

    public static TXVodDownloadManager getInstance() {
        if (instance == null) {
            instance = new TXVodDownloadManager();
        }
        return instance;
    }

    public void setDownloadPath(String str) {
        if (str == null) {
            return;
        }
        new File(str).mkdirs();
        this.mDownloadPath = str;
    }

    public void setListener(ITXVodDownloadListener iTXVodDownloadListener) {
        this.mListener = iTXVodDownloadListener;
    }

    public TXVodDownloadMediaInfo startDownloadUrl(String str) {
        TXVodDownloadMediaInfo tXVodDownloadMediaInfo = new TXVodDownloadMediaInfo();
        tXVodDownloadMediaInfo.url = str;
        this.mMediaInfoArray.add(tXVodDownloadMediaInfo);
        downloadMedia(tXVodDownloadMediaInfo);
        return tXVodDownloadMediaInfo;
    }

    public TXVodDownloadMediaInfo startDownload(final TXVodDownloadDataSource tXVodDownloadDataSource) {
        final TXVodDownloadMediaInfo tXVodDownloadMediaInfo = new TXVodDownloadMediaInfo();
        tXVodDownloadMediaInfo.dataSource = tXVodDownloadDataSource;
        TXPlayerAuthBuilder tXPlayerAuthBuilder = tXVodDownloadDataSource.authBuilder;
        if (tXPlayerAuthBuilder != null) {
            TXCVodPlayerNetApi tXCVodPlayerNetApi = new TXCVodPlayerNetApi();
            tXCVodPlayerNetApi.m1129a(tXPlayerAuthBuilder.isHttps());
            tXCVodPlayerNetApi.m1133a(new TXCVodPlayerNetListener() { // from class: com.tencent.rtmp.downloader.TXVodDownloadManager.1
                @Override // com.tencent.liteav.network.TXCVodPlayerNetListener
                public void onNetSuccess(TXCVodPlayerNetApi tXCVodPlayerNetApi2) {
                    TXVodDownloadMediaInfo tXVodDownloadMediaInfo2 = tXVodDownloadMediaInfo;
                    if (tXVodDownloadMediaInfo2.isStop) {
                        TXVodDownloadManager.this.mMediaInfoArray.remove(tXVodDownloadMediaInfo2);
                        ITXVodDownloadListener iTXVodDownloadListener = TXVodDownloadManager.this.mListener;
                        if (iTXVodDownloadListener != null) {
                            iTXVodDownloadListener.onDownloadStop(tXVodDownloadMediaInfo);
                        }
                        TXCLog.m2911w(TXVodDownloadManager.TAG, "已取消下载任务");
                        return;
                    }
                    TXPlayInfoStream classificationSource = TXVodDownloadManager.this.getClassificationSource(tXCVodPlayerNetApi2.m1138a(), tXVodDownloadDataSource.quality);
                    if (classificationSource == null) {
                        TXVodDownloadManager.this.mMediaInfoArray.remove(tXVodDownloadMediaInfo);
                        ITXVodDownloadListener iTXVodDownloadListener2 = TXVodDownloadManager.this.mListener;
                        if (iTXVodDownloadListener2 == null) {
                            return;
                        }
                        iTXVodDownloadListener2.onDownloadError(tXVodDownloadMediaInfo, TXVodDownloadManager.DOWNLOAD_NO_FILE, "无此清晰度");
                        return;
                    }
                    tXVodDownloadMediaInfo.url = classificationSource.m1116b();
                    tXVodDownloadMediaInfo.size = classificationSource.m1114d();
                    tXVodDownloadMediaInfo.duration = classificationSource.m1115c();
                    TXVodDownloadManager.this.downloadMedia(tXVodDownloadMediaInfo);
                }

                @Override // com.tencent.liteav.network.TXCVodPlayerNetListener
                public void onNetFailed(TXCVodPlayerNetApi tXCVodPlayerNetApi2, String str, int i) {
                    TXVodDownloadManager.this.mMediaInfoArray.remove(tXVodDownloadMediaInfo);
                    ITXVodDownloadListener iTXVodDownloadListener = TXVodDownloadManager.this.mListener;
                    if (iTXVodDownloadListener != null) {
                        iTXVodDownloadListener.onDownloadError(tXVodDownloadMediaInfo, TXVodDownloadManager.DOWNLOAD_AUTH_FAILED, str);
                    }
                }
            });
            if (tXCVodPlayerNetApi.m1137a(tXPlayerAuthBuilder.getAppId(), tXPlayerAuthBuilder.getFileId(), tXPlayerAuthBuilder.getTimeout(), tXPlayerAuthBuilder.getUs(), tXPlayerAuthBuilder.getExper(), tXPlayerAuthBuilder.getSign()) == 0) {
                tXVodDownloadMediaInfo.netApi = tXCVodPlayerNetApi;
                this.mMediaInfoArray.add(tXVodDownloadMediaInfo);
                return tXVodDownloadMediaInfo;
            }
            TXCLog.m2914e(TAG, "unable to getPlayInfo");
            return null;
        }
        return null;
    }

    public void stopDownload(TXVodDownloadMediaInfo tXVodDownloadMediaInfo) {
        if (tXVodDownloadMediaInfo == null) {
            return;
        }
        tXVodDownloadMediaInfo.isStop = true;
        int i = tXVodDownloadMediaInfo.tid;
        if (i < 0) {
            TXCLog.m2911w(TAG, "stop download not start task");
            return;
        }
        this.mDownloadCenter.stop(i);
        TXCLog.m2915d(TAG, "stop download " + tXVodDownloadMediaInfo.url);
    }

    public boolean deleteDownloadFile(String str) {
        TXCLog.m2915d(TAG, "delete file " + str);
        Iterator<TXVodDownloadMediaInfo> it2 = this.mMediaInfoArray.iterator();
        while (it2.hasNext()) {
            String str2 = it2.next().playPath;
            if (str2 != null && str2.equals(str)) {
                TXCLog.m2914e(TAG, "file is downloading, can not be delete");
                return false;
            }
        }
        new File(str).delete();
        TXCLog.m2914e(TAG, "delete success");
        return true;
    }

    protected void downloadMedia(TXVodDownloadMediaInfo tXVodDownloadMediaInfo) {
        String str = tXVodDownloadMediaInfo.url;
        if (str == null) {
            return;
        }
        if (Uri.parse(str).getPath().endsWith(".m3u8")) {
            tXVodDownloadMediaInfo.playPath = makePlayPath(str);
            if (tXVodDownloadMediaInfo.playPath == null) {
                ITXVodDownloadListener iTXVodDownloadListener = this.mListener;
                if (iTXVodDownloadListener == null) {
                    return;
                }
                iTXVodDownloadListener.onDownloadError(tXVodDownloadMediaInfo, DOWNLOAD_PATH_ERROR, "本地路径创建失败");
                return;
            }
            TXVodDownloadDataSource tXVodDownloadDataSource = tXVodDownloadMediaInfo.dataSource;
            if (tXVodDownloadDataSource != null && tXVodDownloadDataSource.token != null) {
                String[] split = str.split("/");
                if (split.length > 0) {
                    int lastIndexOf = str.lastIndexOf(split[split.length - 1]);
                    str = str.substring(0, lastIndexOf) + "voddrm.token." + tXVodDownloadMediaInfo.dataSource.token + "." + str.substring(lastIndexOf);
                }
            }
            TXCLog.m2915d(TAG, "download hls " + str + " to " + tXVodDownloadMediaInfo.playPath);
            tXVodDownloadMediaInfo.tid = this.mDownloadCenter.downloadHls(str, tXVodDownloadMediaInfo.playPath);
            if (tXVodDownloadMediaInfo.tid >= 0) {
                return;
            }
            TXCLog.m2914e(TAG, "start download failed");
            ITXVodDownloadListener iTXVodDownloadListener2 = this.mListener;
            if (iTXVodDownloadListener2 == null) {
                return;
            }
            iTXVodDownloadListener2.onDownloadError(tXVodDownloadMediaInfo, DOWNLOAD_FORMAT_ERROR, "Internal error");
            return;
        }
        TXCLog.m2914e(TAG, "format error: " + str);
        ITXVodDownloadListener iTXVodDownloadListener3 = this.mListener;
        if (iTXVodDownloadListener3 == null) {
            return;
        }
        iTXVodDownloadListener3.onDownloadError(tXVodDownloadMediaInfo, DOWNLOAD_FORMAT_ERROR, "No support format");
    }

    protected String makePlayPath(String str) {
        String str2 = this.mDownloadPath + "/txdownload";
        File file = new File(str2);
        if ((!file.exists() || !file.isDirectory()) && !file.mkdir()) {
            TXCLog.m2914e(TAG, "创建下载路径失败 " + str2);
            return null;
        } else if (Uri.parse(str).getPath().endsWith(".m3u8")) {
            return str2 + "/" + md5(str) + ".m3u8.sqlite";
        } else {
            TXCLog.m2914e(TAG, "不支持格式");
            return null;
        }
    }

    protected static String md5(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            String str2 = "";
            for (byte b : MessageDigest.getInstance("MD5").digest(str.getBytes())) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }
                str2 = str2 + hexString;
            }
            return str2;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    TXPlayInfoStream getClassificationSource(TXPlayInfoResponse tXPlayInfoResponse, int i) {
        if (i == 0) {
            return tXPlayInfoResponse.m1124d();
        }
        return tXPlayInfoResponse.m1127a(TXVodDownloadDataSource.qualityToId(i), "hls");
    }

    TXVodDownloadMediaInfo convertMedia(IjkDownloadMedia ijkDownloadMedia) {
        Iterator<TXVodDownloadMediaInfo> it2 = this.mMediaInfoArray.iterator();
        while (it2.hasNext()) {
            TXVodDownloadMediaInfo next = it2.next();
            if (next.tid == ijkDownloadMedia.tid) {
                next.downloadSize = ijkDownloadMedia.downloadSize;
                if (next.size == 0) {
                    next.size = ijkDownloadMedia.size;
                }
                return next;
            }
        }
        return null;
    }
}
