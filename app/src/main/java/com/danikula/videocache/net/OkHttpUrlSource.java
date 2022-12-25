package com.danikula.videocache.net;

import com.danikula.videocache.InterruptedProxyCacheException;
import com.danikula.videocache.Preconditions;
import com.danikula.videocache.ProxyCacheException;
import com.danikula.videocache.ProxyCacheUtils;
import com.danikula.videocache.Source;
import com.danikula.videocache.SourceInfo;
import com.danikula.videocache.headers.HeaderInjector;
import com.danikula.videocache.sourcestorage.SourceInfoStorage;
import com.danikula.videocache.sourcestorage.SourceInfoStorageFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import okhttp3.Call;
import okhttp3.Response;
import timber.log.Timber;

/* loaded from: classes2.dex */
public class OkHttpUrlSource implements Source {
    private InputStream inputStream;
    private boolean isGzip;
    private Call requestCall;
    private SourceInfo sourceInfo;
    public final SourceInfoStorage sourceInfoStorage;

    public OkHttpUrlSource(String str) {
        this(str, SourceInfoStorageFactory.newEmptySourceInfoStorage());
    }

    public OkHttpUrlSource(String str, SourceInfoStorage sourceInfoStorage) {
        this.requestCall = null;
        Preconditions.checkNotNull(sourceInfoStorage);
        this.sourceInfoStorage = sourceInfoStorage;
        SourceInfo sourceInfo = sourceInfoStorage.get(str);
        this.sourceInfo = sourceInfo == null ? new SourceInfo(str, -2147483648L, System.currentTimeMillis(), ProxyCacheUtils.getSupposablyMime(str)) : sourceInfo;
    }

    public OkHttpUrlSource(String str, SourceInfoStorage sourceInfoStorage, HeaderInjector headerInjector) {
        this.requestCall = null;
        Preconditions.checkNotNull(sourceInfoStorage);
        this.sourceInfoStorage = sourceInfoStorage;
        Preconditions.checkNotNull(headerInjector);
        SourceInfo sourceInfo = sourceInfoStorage.get(str);
        this.sourceInfo = sourceInfo == null ? new SourceInfo(str, -2147483648L, System.currentTimeMillis(), ProxyCacheUtils.getSupposablyMime(str)) : sourceInfo;
    }

    public OkHttpUrlSource(OkHttpUrlSource okHttpUrlSource) {
        this.requestCall = null;
        this.sourceInfo = okHttpUrlSource.sourceInfo;
        this.sourceInfoStorage = okHttpUrlSource.sourceInfoStorage;
    }

    @Override // com.danikula.videocache.Source
    public synchronized long length() throws ProxyCacheException {
        if (this.sourceInfo.length == -2147483648L) {
            fetchContentInfo();
        }
        return this.sourceInfo.length;
    }

    @Override // com.danikula.videocache.Source
    public void open(long j) throws ProxyCacheException {
        try {
            Timber.Tree tag = Timber.tag("TTT");
            tag.mo18i("OkHttpUrlSource==>> open : offset = " + j + ", url = " + getUrl(), new Object[0]);
            Response openConnection = openConnection(j, -1);
            String header = openConnection.header("Content-Type");
            this.inputStream = new BufferedInputStream(openConnection.body().byteStream(), 102400);
            long readSourceAvailableBytes = readSourceAvailableBytes(openConnection, j, openConnection.code()) + j;
            Timber.Tree tag2 = Timber.tag("TTT");
            tag2.mo18i("OkHttpUrlSource==>> open : contentLength = " + readSourceAvailableBytes, new Object[0]);
            this.sourceInfo = new SourceInfo(this.sourceInfo.url, readSourceAvailableBytes, System.currentTimeMillis(), header);
            this.sourceInfoStorage.put(this.sourceInfo.url, this.sourceInfo);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ProxyCacheException("Error opening okHttpClient for " + this.sourceInfo.url + " with offset " + j, e);
        }
    }

    private long readSourceAvailableBytes(Response response, long j, int i) throws IOException {
        return getContentLength(response);
    }

    private long getContentLength(Response response) {
        String header = response.header("Content-Length");
        response.header("Content-Encoding");
        if (header == null) {
            this.isGzip = true;
        }
        if (header == null) {
            return -1L;
        }
        return Long.parseLong(header);
    }

    @Override // com.danikula.videocache.Source
    public void close() throws ProxyCacheException {
        Call call = this.requestCall;
        if (call != null) {
            try {
                call.cancel();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        InputStream inputStream = this.inputStream;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
                throw new RuntimeException(e2.getMessage(), e2);
            }
        }
    }

    @Override // com.danikula.videocache.Source
    public int read(byte[] bArr) throws ProxyCacheException {
        InputStream inputStream = this.inputStream;
        if (inputStream == null) {
            throw new ProxyCacheException("Error reading data from " + this.sourceInfo.url + ": okHttpClient is absent!");
        }
        try {
            return inputStream.read(bArr, 0, bArr.length);
        } catch (InterruptedIOException e) {
            throw new InterruptedProxyCacheException("Reading source " + this.sourceInfo.url + " is interrupted", e);
        } catch (IOException e2) {
            throw new ProxyCacheException("Error reading data from " + this.sourceInfo.url, e2);
        }
    }

    private void fetchContentInfo() throws ProxyCacheException {
        Response response;
        Call call;
        Call call2;
        try {
            try {
                Timber.tag("TTT").mo18i("OkHttpUrlSource==>> fetchContentInfo : url = " + getUrl(), new Object[0]);
                response = openConnectionForHeader(20000);
            } catch (Throwable th) {
                th = th;
                ProxyCacheUtils.close(null);
                if (0 != 0 && (call = this.requestCall) != null) {
                    call.cancel();
                }
                throw th;
            }
        } catch (IOException e) {
            e = e;
            response = null;
        } catch (Throwable th2) {
            th = th2;
            ProxyCacheUtils.close(null);
            if (0 != 0) {
                call.cancel();
            }
            throw th;
        }
        if (response != null) {
            try {
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                ProxyCacheUtils.close(null);
                if (response == null || (call2 = this.requestCall) == null) {
                    return;
                }
                call2.cancel();
                return;
            }
            if (response.isSuccessful()) {
                long contentLength = getContentLength(response);
                String header = response.header("Content-Type", "application/mp4");
                InputStream byteStream = response.body().byteStream();
                this.sourceInfo = new SourceInfo(this.sourceInfo.url, contentLength, System.currentTimeMillis(), header);
                this.sourceInfoStorage.put(this.sourceInfo.url, this.sourceInfo);
                ProxyCacheUtils.close(byteStream);
                if (response == null || (call2 = this.requestCall) == null) {
                    return;
                }
                call2.cancel();
                return;
            }
        }
        throw new ProxyCacheException("Fail to fetchContentInfo: " + this.sourceInfo.url);
    }

    private Response openConnectionForHeader(int i) throws IOException, ProxyCacheException {
        return OkHttpManager.getInstance().requestHeader(this.sourceInfo.url);
    }

    private Response openConnection(long j, int i) throws IOException, ProxyCacheException {
        this.requestCall = OkHttpManager.getInstance().initRequest(this.sourceInfo.url, j);
        return this.requestCall.execute();
    }

    public String getUrl() {
        return this.sourceInfo.url;
    }

    public String toString() {
        return "OkHttpUrlSource{sourceInfo='" + this.sourceInfo + "}";
    }

    public boolean isGzip() {
        return this.isGzip;
    }
}
