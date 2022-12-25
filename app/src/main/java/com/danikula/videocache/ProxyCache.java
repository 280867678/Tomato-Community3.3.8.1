package com.danikula.videocache;

import com.danikula.videocache.net.OkHttpUrlSource;
import java.lang.Thread;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class ProxyCache {
    private final Cache cache;
    private final Source source;
    private volatile Thread sourceReaderThread;
    private volatile boolean stopped;

    /* renamed from: wc */
    private final Object f1227wc = new Object();
    private final Object stopLock = new Object();
    private volatile int percentsAvailable = -1;
    private final AtomicInteger readSourceErrorsCount = new AtomicInteger();

    protected void onCachePercentsAvailableChanged(int i) {
        throw null;
    }

    public ProxyCache(Source source, Cache cache) {
        Preconditions.checkNotNull(source);
        this.source = source;
        Preconditions.checkNotNull(cache);
        this.cache = cache;
    }

    public int read(byte[] bArr, long j, int i) throws ProxyCacheException {
        ProxyCacheUtils.assertBuffer(bArr, j, i);
        while (!this.cache.isCompleted() && this.cache.available() < i + j && !this.stopped) {
            readSourceAsync();
            waitForSourceData();
            checkReadSourceErrorsCount();
        }
        int read = this.cache.read(bArr, j, i);
        if (this.cache.isCompleted() && this.percentsAvailable != 100) {
            this.percentsAvailable = 100;
            onCachePercentsAvailableChanged(100);
        }
        return read;
    }

    private synchronized void readSourceAsync() throws ProxyCacheException {
        boolean z = (this.sourceReaderThread == null || this.sourceReaderThread.getState() == Thread.State.TERMINATED) ? false : true;
        if (!this.stopped && !this.cache.isCompleted() && !z) {
            SourceReaderRunnable sourceReaderRunnable = new SourceReaderRunnable();
            this.sourceReaderThread = new Thread(sourceReaderRunnable, "Source reader for " + this.source);
            this.sourceReaderThread.start();
        }
    }

    private void waitForSourceData() throws ProxyCacheException {
        synchronized (this.f1227wc) {
            try {
                try {
                    this.f1227wc.wait(500L);
                } catch (InterruptedException e) {
                    throw new ProxyCacheException("Waiting source data is interrupted!", e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void checkReadSourceErrorsCount() throws ProxyCacheException {
        int i = this.readSourceErrorsCount.get();
        if (i < 3) {
            return;
        }
        this.readSourceErrorsCount.set(0);
        throw new ProxyCacheException("Error reading source " + i + " times");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class SourceReaderRunnable implements Runnable {
        private SourceReaderRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ProxyCache.this.readSource();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readSource() {
        Throwable th;
        long j;
        long j2 = 0;
        try {
            j2 = this.cache.available();
            this.source.open(j2);
            j = this.source.length();
            try {
                byte[] bArr = new byte[102400];
                while (true) {
                    int read = this.source.read(bArr);
                    if (read != -1) {
                        synchronized (this.stopLock) {
                            if (isStopped()) {
                                return;
                            }
                            this.cache.append(bArr, read);
                        }
                        j2 += read;
                        notifyNewCacheDataAvailable(j2, j);
                    } else {
                        Timber.tag("TTT").mo18i("ProxyCache==>> readSource : 网络读取结束offset = " + j2 + ",sourceLength = " + j, new Object[0]);
                        tryComplete();
                        onSourceRead();
                        if (j == -1) {
                            this.readSourceErrorsCount.incrementAndGet();
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                try {
                    this.readSourceErrorsCount.incrementAndGet();
                    onError(th);
                } finally {
                    closeSource();
                    notifyNewCacheDataAvailable(j2, j);
                }
            }
        } catch (Throwable th3) {
            th = th3;
            j = -1;
        }
    }

    public void shutdown() {
        synchronized (this.stopLock) {
            try {
                this.stopped = true;
                if (this.sourceReaderThread != null) {
                    this.sourceReaderThread.interrupt();
                }
                this.cache.close();
            } catch (ProxyCacheException e) {
                onError(e);
            }
        }
    }

    private void notifyNewCacheDataAvailable(long j, long j2) {
        onCacheAvailable(j, j2);
        synchronized (this.f1227wc) {
            this.f1227wc.notifyAll();
        }
    }

    protected void onCacheAvailable(long j, long j2) {
        boolean z = true;
        int i = (j2 > 0L ? 1 : (j2 == 0L ? 0 : -1));
        int i2 = i == 0 ? 100 : (int) ((((float) j) / ((float) j2)) * 100.0f);
        boolean z2 = i2 != this.percentsAvailable;
        if (i < 0) {
            z = false;
        }
        if (z && z2) {
            onCachePercentsAvailableChanged(i2);
        }
        this.percentsAvailable = i2;
    }

    private void onSourceRead() {
        this.percentsAvailable = 100;
        onCachePercentsAvailableChanged(this.percentsAvailable);
    }

    private void tryComplete() throws ProxyCacheException {
        synchronized (this.stopLock) {
            if (!isStopped() && this.cache.available() == this.source.length()) {
                this.cache.complete();
            }
            OkHttpUrlSource okHttpUrlSource = (OkHttpUrlSource) this.source;
            if (okHttpUrlSource != null && okHttpUrlSource.isGzip()) {
                this.cache.complete();
            }
        }
    }

    private boolean isStopped() {
        return Thread.currentThread().isInterrupted() || this.stopped;
    }

    private void closeSource() {
        try {
            this.source.close();
        } catch (ProxyCacheException e) {
            onError(new ProxyCacheException("Error closing source " + this.source, e));
        }
    }

    protected final void onError(Throwable th) {
        if (th instanceof InterruptedProxyCacheException) {
            Timber.tag("TTT").mo21e("ProxyCache==>> onError : ProxyCache is interrupted", new Object[0]);
            return;
        }
        Timber.Tree tag = Timber.tag("TTT");
        tag.mo21e("ProxyCache==>> onError : ProxyCache error " + th.getMessage(), new Object[0]);
    }
}
