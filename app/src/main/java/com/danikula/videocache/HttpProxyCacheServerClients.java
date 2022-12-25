package com.danikula.videocache;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.danikula.videocache.entity.HlsPlayConstant;
import com.danikula.videocache.file.FileCache;
import com.danikula.videocache.file.FileNameUtil;
import com.danikula.videocache.net.OkHttpUrlSource;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class HttpProxyCacheServerClients {
    private final Config config;
    private Map<String, Object> hlsPlayMap;
    private volatile HttpProxyCache proxyCache;
    private String tsMultistagePath;
    private final CacheListener uiCacheListener;
    private final String url;
    private final AtomicInteger clientsCount = new AtomicInteger(0);
    private final List<CacheListener> listeners = new CopyOnWriteArrayList();

    public HttpProxyCacheServerClients(String str, Config config, Map<String, Object> map) {
        Preconditions.checkNotNull(str);
        this.url = str;
        Preconditions.checkNotNull(config);
        this.config = config;
        this.uiCacheListener = new UiListenerHandler(str, this.listeners);
        this.hlsPlayMap = map;
    }

    public HttpProxyCacheServerClients(String str, Config config, Map<String, Object> map, String str2) {
        Preconditions.checkNotNull(str);
        this.url = str;
        Preconditions.checkNotNull(config);
        this.config = config;
        this.uiCacheListener = new UiListenerHandler(str, this.listeners);
        this.hlsPlayMap = map;
        this.tsMultistagePath = str2;
    }

    public void processRequest(GetRequest getRequest, Socket socket) throws ProxyCacheException, IOException {
        startProcessRequest();
        try {
            try {
                this.clientsCount.incrementAndGet();
                this.proxyCache.processRequest(getRequest, socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            finishProcessRequest();
        }
    }

    private synchronized void startProcessRequest() throws ProxyCacheException {
        this.proxyCache = this.proxyCache == null ? newHttpProxyCache() : this.proxyCache;
    }

    private HttpProxyCache newHttpProxyCache() throws ProxyCacheException {
        FileCache fileCache;
        Timber.Tree tag = Timber.tag("TTT");
        tag.mo18i("HttpProxyCacheServerClients==>> newHttpProxyCache : url = " + this.url, new Object[0]);
        String str = this.url;
        Config config = this.config;
        OkHttpUrlSource okHttpUrlSource = new OkHttpUrlSource(str, config.sourceInfoStorage, config.headerInjector);
        if (this.url.trim().toLowerCase().endsWith("ts") || this.url.trim().toLowerCase().endsWith("key")) {
            String fileNameKey = FileNameUtil.getFileNameKey(this.url);
            Timber.Tree tag2 = Timber.tag("TTT");
            tag2.mo18i("HttpProxyCacheServerClients==>> newHttpProxyCache : fileNameKey = " + fileNameKey, new Object[0]);
            if (HlsPlayConstant.getHlsPlay(this.hlsPlayMap, fileNameKey) == null) {
                Timber.tag("TTT").mo21e("HttpProxyCacheServerClients==>> newHttpProxyCache : url parse error", new Object[0]);
                throw new ProxyCacheException("url parse error for HttpProxyCache");
            }
            String generateFileName = FileNameUtil.generateFileName(this.url);
            String generate = FileNameUtil.generate(this.config.cdnUrl, generateFileName, this.tsMultistagePath);
            Timber.Tree tag3 = Timber.tag("TTT");
            tag3.mo18i("HttpProxyCacheServerClients==>> newHttpProxyCache : cdnUrl = " + this.config.cdnUrl + ", fileName = " + generateFileName + ", cacheFile = " + generate, new Object[0]);
            fileCache = new FileCache(new File(this.config.cacheRoot, generate), this.config.diskUsage);
        } else {
            String generateFileName2 = FileNameUtil.generateFileName(this.url);
            String generate2 = FileNameUtil.generate(this.config.cdnUrl, generateFileName2);
            Timber.Tree tag4 = Timber.tag("TTT");
            tag4.mo18i("HttpProxyCacheServerClients==>> newHttpProxyCache : cdnUrl = " + this.config.cdnUrl + ", fileName = " + generateFileName2 + ", cacheFile = " + generate2, new Object[0]);
            fileCache = new FileCache(new File(this.config.cacheRoot, generate2), this.config.diskUsage);
        }
        Timber.Tree tag5 = Timber.tag("TTT");
        tag5.mo18i("HttpProxyCacheServerClients==>> newHttpProxyCache : 完整的FileCache = " + fileCache.getFile().getAbsolutePath(), new Object[0]);
        HttpProxyCache httpProxyCache = new HttpProxyCache(okHttpUrlSource, fileCache, this.hlsPlayMap, this.config);
        httpProxyCache.registerCacheListener(this.uiCacheListener);
        return httpProxyCache;
    }

    private synchronized void finishProcessRequest() {
        if (this.clientsCount.decrementAndGet() <= 0) {
            this.proxyCache.shutdown();
            this.proxyCache = null;
        }
    }

    public void registerCacheListener(CacheListener cacheListener) {
        this.listeners.add(cacheListener);
    }

    public void unregisterCacheListener(CacheListener cacheListener) {
        this.listeners.remove(cacheListener);
    }

    public int getClientsCount() {
        return this.clientsCount.get();
    }

    /* loaded from: classes2.dex */
    private static final class UiListenerHandler extends Handler implements CacheListener {
        private final List<CacheListener> listeners;
        private final String url;

        public UiListenerHandler(String str, List<CacheListener> list) {
            super(Looper.getMainLooper());
            this.url = str;
            this.listeners = list;
        }

        @Override // com.danikula.videocache.CacheListener
        public void onCacheAvailable(File file, String str, int i) {
            Message obtainMessage = obtainMessage();
            obtainMessage.arg1 = i;
            obtainMessage.obj = file;
            sendMessage(obtainMessage);
        }

        @Override // com.danikula.videocache.CacheListener
        public void onFileLoadIsWrong() {
            for (CacheListener cacheListener : this.listeners) {
                cacheListener.onFileLoadIsWrong();
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            for (CacheListener cacheListener : this.listeners) {
                cacheListener.onCacheAvailable((File) message.obj, this.url, message.arg1);
            }
        }
    }
}
