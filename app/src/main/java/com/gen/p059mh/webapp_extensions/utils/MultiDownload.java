package com.gen.p059mh.webapp_extensions.utils;

import android.os.Handler;
import android.support.p002v4.media.session.PlaybackStateCompat;
import com.gen.p059mh.webapps.listener.IErrorInfo;
import com.gen.p059mh.webapps.modules.ErrorInfoImpl;
import com.gen.p059mh.webapps.modules.LoadErrorInfo;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Request;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: com.gen.mh.webapp_extensions.utils.MultiDownload */
/* loaded from: classes2.dex */
public class MultiDownload {
    long completeSize;
    OnRequestListener onRequestListener;
    String path;
    long total;
    URL url;
    int blockSize = 0;
    ArrayList<MultiRequest> downloads = new ArrayList<>();
    ArrayList<MultiRequest> queued = new ArrayList<>();
    Handler handler = new Handler();
    int errorCount = 0;
    boolean completed = false;
    boolean update = false;
    Runnable timer = new Runnable() { // from class: com.gen.mh.webapp_extensions.utils.MultiDownload.3
        @Override // java.lang.Runnable
        public void run() {
            MultiDownload multiDownload = MultiDownload.this;
            if (multiDownload.completed) {
                return;
            }
            if (multiDownload.update && multiDownload.onRequestListener != null) {
                long j = multiDownload.completeSize;
                synchronized (multiDownload) {
                    int size = MultiDownload.this.downloads.size();
                    for (int i = 0; i < size; i++) {
                        j += MultiDownload.this.downloads.get(i).getLoaded();
                    }
                }
                MultiDownload multiDownload2 = MultiDownload.this;
                multiDownload2.onRequestListener.onProgress(j, multiDownload2.total);
            }
            MultiDownload multiDownload3 = MultiDownload.this;
            multiDownload3.handler.postDelayed(multiDownload3.timer, 200L);
        }
    };

    /* renamed from: com.gen.mh.webapp_extensions.utils.MultiDownload$OnRequestListener */
    /* loaded from: classes2.dex */
    public interface OnRequestListener {
        void onComplete(MultiDownload multiDownload);

        void onFail(IErrorInfo iErrorInfo);

        void onProgress(long j, long j2);
    }

    public void setOnRequestListener(OnRequestListener onRequestListener) {
        this.onRequestListener = onRequestListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.utils.MultiDownload$MultiRequest */
    /* loaded from: classes2.dex */
    public class MultiRequest extends Request {
        private long loaded;
        private long requireLength;

        MultiRequest(MultiDownload multiDownload) {
        }

        public void setRequireLength(long j) {
            this.requireLength = j;
        }

        public long getRequireLength() {
            return this.requireLength;
        }

        public void setLoaded(long j) {
            this.loaded = j;
        }

        public long getLoaded() {
            return this.loaded;
        }
    }

    public MultiDownload(URL url, String str) {
        this.url = url;
        this.path = str;
    }

    public void start() {
        Request request = new Request();
        request.setUrl(this.url);
        request.setMethod("HEAD");
        request.setRequestHeaders("Range", "bytes=0-");
        request.setRequestListener(new Request.RequestListener() { // from class: com.gen.mh.webapp_extensions.utils.MultiDownload.1
            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            public void onComplete(int i, byte[] bArr) {
            }

            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            public void onProgress(long j, long j2) {
            }

            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            public boolean onReceiveResponse(Request.Response response) {
                try {
                    long parseLong = Long.parseLong(response.headers.get("Content-Length").get(0));
                    if (parseLong > 0) {
                        MultiDownload.this.total = parseLong;
                        MultiDownload.this.blockSize = (int) (parseLong / PlaybackStateCompat.ACTION_SET_REPEAT_MODE);
                        if (parseLong % PlaybackStateCompat.ACTION_SET_REPEAT_MODE > 0) {
                            MultiDownload.this.blockSize++;
                        }
                        if (MultiDownload.this.blockSize == 0) {
                            if (MultiDownload.this.onRequestListener != null) {
                                MultiDownload.this.onRequestListener.onFail(LoadErrorInfo.FILE_INFO_FAIL);
                            }
                        } else {
                            MultiDownload.this.startDownload();
                        }
                    } else if (MultiDownload.this.onRequestListener != null) {
                        MultiDownload.this.onRequestListener.onFail(LoadErrorInfo.FILE_INFO_FAIL);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    OnRequestListener onRequestListener = MultiDownload.this.onRequestListener;
                    if (onRequestListener != null) {
                        onRequestListener.onFail(LoadErrorInfo.FILE_INFO_FAIL);
                    }
                }
                return false;
            }

            @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
            public void onFail(int i, String str) {
                OnRequestListener onRequestListener = MultiDownload.this.onRequestListener;
                if (onRequestListener == null || i == 101 || i == 102) {
                    return;
                }
                onRequestListener.onFail(ErrorInfoImpl.newInstance(i, str));
            }
        });
        request.start();
    }

    void startDownload() {
        File file = new File(this.path);
        if (file.exists()) {
            if (!file.isDirectory()) {
                file.delete();
                file.mkdirs();
            }
        } else {
            file.mkdirs();
        }
        this.completeSize = 0L;
        Logger.m4112i("downLoadZip", "zipUrl = " + this.url);
        for (int i = 0; i < this.blockSize; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.path);
            sb.append("/");
            long j = i;
            sb.append(34816 + j);
            final String sb2 = sb.toString();
            File file2 = new File(sb2);
            if (file2.exists()) {
                this.completeSize += file2.length();
            } else {
                final MultiRequest multiRequest = new MultiRequest(this);
                multiRequest.setUrl(this.url);
                multiRequest.setMethod("GET");
                long j2 = j * PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
                long min = Math.min((i + 1) * PlaybackStateCompat.ACTION_SET_REPEAT_MODE, this.total) - 1;
                Logger.m4112i("downLoadZip", "file = " + file2.getAbsoluteFile() + "  block = " + i + "/" + this.blockSize + " start = " + j2 + "  end =" + min);
                StringBuilder sb3 = new StringBuilder();
                sb3.append("bytes=");
                sb3.append(j2);
                sb3.append("-");
                sb3.append(min);
                multiRequest.setRequestHeaders("Range", sb3.toString());
                multiRequest.setRequireLength((min - j2) + 1);
                multiRequest.setRequestListener(new Request.RequestListener() { // from class: com.gen.mh.webapp_extensions.utils.MultiDownload.2
                    @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                    public boolean onReceiveResponse(Request.Response response) {
                        return true;
                    }

                    @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                    public void onFail(int i2, String str) {
                        boolean z;
                        synchronized (MultiDownload.this) {
                            MultiDownload.this.downloads.remove(multiRequest);
                            z = true;
                            MultiDownload.this.errorCount++;
                            if (MultiDownload.this.errorCount <= 5) {
                                MultiRequest multiRequest2 = new MultiRequest(MultiDownload.this);
                                multiRequest2.setUrl(multiRequest.getUrl());
                                multiRequest2.setMethod(multiRequest.getMethod());
                                multiRequest2.setRequestHeaders("Range", multiRequest.getRequestHeader("Range"));
                                multiRequest2.setRequireLength(multiRequest.getRequireLength());
                                multiRequest2.setRequestListener(multiRequest.getRequestListener());
                                MultiDownload.this.queued.add(multiRequest2);
                                z = false;
                            } else {
                                MultiDownload.this.queued.clear();
                                Iterator<MultiRequest> it2 = MultiDownload.this.downloads.iterator();
                                while (it2.hasNext()) {
                                    it2.next().cancel();
                                }
                                MultiDownload.this.downloads.clear();
                            }
                        }
                        if (z) {
                            Logger.m4113i("request failed code:" + i2 + " msg:" + str);
                            MultiDownload.this.requestFailed();
                            return;
                        }
                        MultiDownload.this.checkQueue();
                    }

                    @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                    public void onComplete(int i2, byte[] bArr) {
                        if (bArr.length == multiRequest.getRequireLength()) {
                            synchronized (MultiDownload.this) {
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(sb2);
                                    fileOutputStream.write(bArr);
                                    fileOutputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                MultiDownload.this.downloads.remove(multiRequest);
                                MultiDownload.this.completeSize += bArr.length;
                            }
                            MultiDownload.this.checkQueue();
                            return;
                        }
                        onFail(500, "长度不对");
                    }

                    @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                    public void onProgress(long j3, long j4) {
                        multiRequest.setLoaded(j3);
                        MultiDownload.this.update = true;
                    }
                });
                this.queued.add(multiRequest);
            }
        }
        checkQueue();
        this.handler.postDelayed(this.timer, 200L);
    }

    void checkQueue() {
        OnRequestListener onRequestListener;
        synchronized (this) {
            if (this.completed) {
                return;
            }
            if (this.queued.size() == 0) {
                if (this.downloads.size() == 0) {
                    this.completed = true;
                }
            } else {
                while (this.downloads.size() < 5 && this.queued.size() > 0) {
                    MultiRequest multiRequest = this.queued.get(0);
                    this.queued.remove(0);
                    this.downloads.add(multiRequest);
                    multiRequest.start();
                }
            }
            if (!this.completed || (onRequestListener = this.onRequestListener) == null) {
                return;
            }
            onRequestListener.onComplete(this);
        }
    }

    void requestFailed() {
        synchronized (this) {
            if (this.completed) {
                return;
            }
            this.completed = true;
            OnRequestListener onRequestListener = this.onRequestListener;
            if (onRequestListener == null) {
                return;
            }
            onRequestListener.onFail(LoadErrorInfo.DOWNLOAD_ZIP_FAIL);
        }
    }

    public byte[] loadData() {
        if (this.completed) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i = 0; i < this.blockSize; i++) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(this.path + "/" + (i + 34816));
                    byte[] bArr = new byte[16384];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read > 0) {
                            byteArrayOutputStream.write(bArr, 0, read);
                        }
                    }
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        }
        return null;
    }

    public void cancel() {
        synchronized (this) {
            if (this.completed) {
                return;
            }
            this.completed = true;
            Iterator<MultiRequest> it2 = this.downloads.iterator();
            while (it2.hasNext()) {
                it2.next().cancel();
            }
            this.downloads.clear();
            this.queued.clear();
        }
    }
}
