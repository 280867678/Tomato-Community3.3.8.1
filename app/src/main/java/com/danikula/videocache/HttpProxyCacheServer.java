package com.danikula.videocache;

import android.content.Context;
import com.danikula.videocache.entity.HlsPlay;
import com.danikula.videocache.entity.HlsPlayConstant;
import com.danikula.videocache.file.DiskUsage;
import com.danikula.videocache.file.FileNameGenerator;
import com.danikula.videocache.file.FileNameUtil;
import com.danikula.videocache.file.Md5FileNameGenerator;
import com.danikula.videocache.file.TotalSizeLruDiskUsage;
import com.danikula.videocache.headers.EmptyHeadersInjector;
import com.danikula.videocache.headers.HeaderInjector;
import com.danikula.videocache.sourcestorage.SourceInfoStorage;
import com.danikula.videocache.sourcestorage.SourceInfoStorageFactory;
import com.tencent.ijk.media.player.IjkMediaMeta;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import timber.log.Timber;

/* loaded from: classes2.dex */
public class HttpProxyCacheServer {
    private String PROXY_HOST;
    private CacheListener cacheListener;
    private final Object clientsLock;
    private final Map<String, HttpProxyCacheServerClients> clientsMap;
    public Config config;
    private final Map<String, Object> hlsPlayMap;
    private final Pinger pinger;
    private final int port;
    private final ServerSocket serverSocket;
    private final ExecutorService socketProcessor;
    private String urlToken;
    private final Thread waitConnectionThread;

    private HttpProxyCacheServer(Config config) {
        this.PROXY_HOST = "127.0.0.1";
        this.clientsLock = new Object();
        this.socketProcessor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.clientsMap = new ConcurrentHashMap();
        this.hlsPlayMap = new ConcurrentHashMap();
        Preconditions.checkNotNull(config);
        this.config = config;
        try {
            this.serverSocket = new ServerSocket(0, 8, InetAddress.getByName(this.PROXY_HOST));
            this.port = this.serverSocket.getLocalPort();
            IgnoreHostProxySelector.install(this.PROXY_HOST, this.port);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.waitConnectionThread = new Thread(new WaitRequestsRunnable(countDownLatch));
            this.waitConnectionThread.start();
            countDownLatch.await();
            this.pinger = new Pinger(this.PROXY_HOST, this.port);
            Timber.Tree tag = Timber.tag("TTT");
            tag.mo18i("HttpProxyCacheServer==>> HttpProxyCacheServer : Proxy cache server started. Is it alive? = " + isAlive(), new Object[0]);
        } catch (IOException | InterruptedException e) {
            this.socketProcessor.shutdown();
            throw new IllegalStateException("Error starting local proxy server", e);
        }
    }

    /* loaded from: classes2.dex */
    private final class WaitRequestsRunnable implements Runnable {
        private final CountDownLatch startSignal;

        public WaitRequestsRunnable(CountDownLatch countDownLatch) {
            this.startSignal = countDownLatch;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.startSignal.countDown();
            HttpProxyCacheServer.this.waitForRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void waitForRequest() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket accept = this.serverSocket.accept();
                Timber.Tree tag = Timber.tag("TTT");
                tag.mo18i("HttpProxyCacheServer==>> waitForRequest : Accept new socket " + accept, new Object[0]);
                this.socketProcessor.submit(new SocketProcessorRunnable(accept));
            } catch (IOException e) {
                onError(new ProxyCacheException("Error during waiting connection", e));
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class SocketProcessorRunnable implements Runnable {
        private final Socket socket;

        public SocketProcessorRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override // java.lang.Runnable
        public void run() {
            HttpProxyCacheServer.this.processSocket(this.socket);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSocket(Socket socket) {
        HlsPlay hlsPlay;
        int indexOf;
        try {
            try {
                GetRequest read = GetRequest.read(socket.getInputStream());
                String str = read.uri;
                if (!str.equals("ping") && (indexOf = (str = ProxyCacheUtils.decode(read.uri)).indexOf("\\?")) != -1) {
                    str = str.substring(0, indexOf);
                }
                Timber.tag("TTT").mo18i("HttpProxyCacheServer==>> processSocket : socket中获取的url = " + str, new Object[0]);
                String str2 = "";
                if (!str.equals("ping") && !str.startsWith("http") && (str.endsWith("ts") || str.endsWith("key"))) {
                    if (HlsPlayConstant.getHlsPlay(this.hlsPlayMap, FileNameUtil.getFileNameKey(str)) == null) {
                        Timber.tag("TTT").mo21e("HttpProxyCacheServer==>> processSocket : url parse error", new Object[0]);
                        releaseSocket(socket);
                        Timber.tag("TTT").mo21e("HttpProxyCacheServer==>> processSocket : Opened connections " + getClientsCount(), new Object[0]);
                        return;
                    }
                    if (str.contains("/") && !FileNameUtil.isPlayCache(this.config.sourceInfoStorage.getContext(), this.config.cacheRoot.getAbsolutePath())) {
                        str2 = str.substring(0, str.indexOf(47));
                    }
                    str = hlsPlay.rootUrl + "/" + str;
                }
                if (str.endsWith(IjkMediaMeta.IJKM_KEY_M3U8)) {
                    str = str + this.urlToken;
                    HlsPlayConstant.loadM3U8(this.hlsPlayMap);
                }
                Timber.tag("TTT").mo18i("HttpProxyCacheServer==>> processSocket : 传给播放器的代理url = " + str, new Object[0]);
                if (this.pinger.isPingRequest(str)) {
                    this.pinger.responseToPing(socket);
                } else {
                    getClients(str, str2).processRequest(read, socket);
                }
                releaseSocket(socket);
                Timber.tag("TTT").mo21e("HttpProxyCacheServer==>> processSocket : Opened connections " + getClientsCount(), new Object[0]);
            } catch (ProxyCacheException e) {
                e = e;
                onError(new ProxyCacheException("Error processing request", e));
                e.printStackTrace();
                releaseSocket(socket);
                Timber.tag("TTT").mo21e("HttpProxyCacheServer==>> processSocket : Opened connections " + getClientsCount(), new Object[0]);
            } catch (SocketException e2) {
                Timber.tag("TTT").mo21e("HttpProxyCacheServer==>> processSocket : Closing socket… Socket is closed by client. ", new Object[0]);
                e2.printStackTrace();
                releaseSocket(socket);
                Timber.tag("TTT").mo21e("HttpProxyCacheServer==>> processSocket : Opened connections " + getClientsCount(), new Object[0]);
            } catch (IOException e3) {
                e = e3;
                onError(new ProxyCacheException("Error processing request", e));
                e.printStackTrace();
                releaseSocket(socket);
                Timber.tag("TTT").mo21e("HttpProxyCacheServer==>> processSocket : Opened connections " + getClientsCount(), new Object[0]);
            }
        } catch (Throwable th) {
            releaseSocket(socket);
            Timber.tag("TTT").mo21e("HttpProxyCacheServer==>> processSocket : Opened connections " + getClientsCount(), new Object[0]);
            throw th;
        }
    }

    public String getProxyUrl(String str) {
        String str2;
        Timber.tag("TTT").mo18i("HttpProxyCacheServer==>> getProxyUrl : 转换前的原始url = " + str, new Object[0]);
        HlsPlayConstant.clear();
        this.hlsPlayMap.clear();
        if (isAlive()) {
            int indexOf = str.indexOf("token=");
            if (indexOf != -1) {
                int i = indexOf - 1;
                String substring = str.substring(0, i);
                this.urlToken = str.substring(i, str.length());
                str = substring;
            } else {
                this.urlToken = "";
            }
            str2 = appendToProxyUrl(str);
        } else {
            str2 = str;
        }
        Timber.tag("TTT").mo18i("HttpProxyCacheServer==>> getProxyUrl : 转换后的代理url = " + str, new Object[0]);
        return str2;
    }

    private String appendToProxyUrl(String str) {
        return String.format(Locale.US, "http://%s:%d/%s", this.PROXY_HOST, Integer.valueOf(this.port), ProxyCacheUtils.encode(str));
    }

    public void registerCacheListener(CacheListener cacheListener, String str) {
        Preconditions.checkAllNotNull(cacheListener, str);
        synchronized (this.clientsLock) {
            try {
                this.cacheListener = cacheListener;
                getClients(str).registerCacheListener(cacheListener);
            } catch (ProxyCacheException e) {
                Timber.Tree tag = Timber.tag("TTT");
                tag.mo21e("HttpProxyCacheServer==>> registerCacheListener : Error registering cache listener " + e.getMessage(), new Object[0]);
            }
        }
    }

    public void unregisterCacheListener(CacheListener cacheListener) {
        Preconditions.checkNotNull(cacheListener);
        synchronized (this.clientsLock) {
            for (HttpProxyCacheServerClients httpProxyCacheServerClients : this.clientsMap.values()) {
                httpProxyCacheServerClients.unregisterCacheListener(cacheListener);
            }
        }
    }

    private boolean isAlive() {
        return this.pinger.ping(3, 70);
    }

    private HttpProxyCacheServerClients getClients(String str) throws ProxyCacheException {
        HttpProxyCacheServerClients httpProxyCacheServerClients;
        synchronized (this.clientsLock) {
            httpProxyCacheServerClients = this.clientsMap.get(str);
            if (httpProxyCacheServerClients == null) {
                httpProxyCacheServerClients = new HttpProxyCacheServerClients(str, this.config, this.hlsPlayMap);
                this.clientsMap.put(str, httpProxyCacheServerClients);
                httpProxyCacheServerClients.registerCacheListener(this.cacheListener);
            }
        }
        return httpProxyCacheServerClients;
    }

    private HttpProxyCacheServerClients getClients(String str, String str2) throws ProxyCacheException {
        HttpProxyCacheServerClients httpProxyCacheServerClients;
        synchronized (this.clientsLock) {
            httpProxyCacheServerClients = this.clientsMap.get(str);
            if (httpProxyCacheServerClients == null) {
                httpProxyCacheServerClients = new HttpProxyCacheServerClients(str, this.config, this.hlsPlayMap, str2);
                this.clientsMap.put(str, httpProxyCacheServerClients);
                httpProxyCacheServerClients.registerCacheListener(this.cacheListener);
            }
        }
        return httpProxyCacheServerClients;
    }

    private int getClientsCount() {
        int i;
        synchronized (this.clientsLock) {
            i = 0;
            for (HttpProxyCacheServerClients httpProxyCacheServerClients : this.clientsMap.values()) {
                i += httpProxyCacheServerClients.getClientsCount();
            }
        }
        return i;
    }

    private void releaseSocket(Socket socket) {
        closeSocketInput(socket);
        closeSocketOutput(socket);
        closeSocket(socket);
    }

    private void closeSocketInput(Socket socket) {
        try {
            if (socket.isInputShutdown()) {
                return;
            }
            socket.shutdownInput();
        } catch (SocketException unused) {
            Timber.tag("TTT").mo21e("HttpProxyCacheServer==>> closeSocketInput : Releasing input stream… Socket is closed by client.", new Object[0]);
        } catch (IOException e) {
            onError(new ProxyCacheException("Error closing socket input stream", e));
        }
    }

    private void closeSocketOutput(Socket socket) {
        try {
            if (socket.isOutputShutdown()) {
                return;
            }
            socket.shutdownOutput();
        } catch (IOException e) {
            Timber.Tree tag = Timber.tag("TTT");
            tag.mo21e("HttpProxyCacheServer==>> closeSocketOutput : Failed to close socket on proxy side: {}. It seems client have already closed connection " + e.getMessage(), new Object[0]);
        }
    }

    private void closeSocket(Socket socket) {
        try {
            if (socket.isClosed()) {
                return;
            }
            socket.close();
        } catch (IOException e) {
            onError(new ProxyCacheException("Error closing socket", e));
        }
    }

    private void onError(Throwable th) {
        Timber.Tree tag = Timber.tag("TTT");
        tag.mo21e("HttpProxyCacheServer==>> onError : HttpProxyCacheServer error " + th.getMessage(), new Object[0]);
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private File cacheRoot;
        private String cdnUrl;
        private DiskUsage diskUsage = new TotalSizeLruDiskUsage(1073741824);
        private FileNameGenerator fileNameGenerator = new Md5FileNameGenerator();
        private HeaderInjector headerInjector = new EmptyHeadersInjector();
        private String ipfsUrl;
        private SourceInfoStorage sourceInfoStorage;

        public Builder(Context context) {
            this.sourceInfoStorage = SourceInfoStorageFactory.newEmptySourceInfoStorage(context);
            this.cacheRoot = StorageUtils.getIndividualCacheDirectory(context);
        }

        public Builder cacheDirectory(File file) {
            Preconditions.checkNotNull(file);
            this.cacheRoot = file;
            return this;
        }

        public Builder headerInjector(HeaderInjector headerInjector) {
            Preconditions.checkNotNull(headerInjector);
            this.headerInjector = headerInjector;
            return this;
        }

        public Builder cdnUrl(String str) {
            this.cdnUrl = str;
            return this;
        }

        public Builder ipfsUrl(String str) {
            this.ipfsUrl = str;
            return this;
        }

        public HttpProxyCacheServer build() {
            Config buildConfig = buildConfig();
            Timber.Tree tag = Timber.tag("TTT");
            tag.mo18i("HttpProxyCacheServer==>> HttpProxyCacheServer build : \ncacheRoot = " + this.cacheRoot.getPath() + "\ncdnUrl = " + this.cdnUrl + "\nipfsUrl = " + this.ipfsUrl, new Object[0]);
            return new HttpProxyCacheServer(buildConfig);
        }

        private Config buildConfig() {
            return new Config(this.cacheRoot, this.fileNameGenerator, this.diskUsage, this.sourceInfoStorage, this.headerInjector, this.cdnUrl, this.ipfsUrl);
        }
    }
}
