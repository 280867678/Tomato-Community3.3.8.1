package com.danikula.videocache;

import com.danikula.videocache.net.OkHttpUrlSource;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class Pinger {
    private static final Logger LOG = LoggerFactory.getLogger("Pinger");
    private final String host;
    private final ExecutorService pingExecutor = Executors.newSingleThreadExecutor();
    private final int port;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Pinger(String str, int i) {
        Preconditions.checkNotNull(str);
        this.host = str;
        this.port = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean ping(int i, int i2) {
        Preconditions.checkArgument(i >= 1);
        Preconditions.checkArgument(i2 > 0);
        int i3 = i2;
        int i4 = 0;
        while (i4 < i) {
            try {
            } catch (InterruptedException e) {
                e = e;
                LOG.error("Error pinging server due to unexpected error", e);
            } catch (ExecutionException e2) {
                e = e2;
                LOG.error("Error pinging server due to unexpected error", e);
            } catch (TimeoutException unused) {
                Logger logger = LOG;
                logger.warn("Error pinging server (attempt: " + i4 + ", timeout: " + i3 + "). ");
            }
            if (((Boolean) this.pingExecutor.submit(new PingCallable()).get(i3, TimeUnit.MILLISECONDS)).booleanValue()) {
                return true;
            }
            i4++;
            i3 *= 2;
        }
        LOG.error(String.format(Locale.US, "Error pinging server (attempts: %d, max timeout: %d). If you see this message, please, report at https://github.com/danikula/AndroidVideoCache/issues/134. Default proxies are: %s", Integer.valueOf(i4), Integer.valueOf(i3 / 2), getDefaultProxies()));
        return false;
    }

    private List<Proxy> getDefaultProxies() {
        try {
            return ProxySelector.getDefault().select(new URI(getPingUrl()));
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isPingRequest(String str) {
        return "ping".equals(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void responseToPing(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("HTTP/1.1 200 OK\n\n".getBytes());
        outputStream.write("ping ok".getBytes());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean pingServer() throws ProxyCacheException {
        OkHttpUrlSource okHttpUrlSource = new OkHttpUrlSource(getPingUrl());
        try {
            byte[] bytes = "ping ok".getBytes();
            okHttpUrlSource.open(0L);
            byte[] bArr = new byte[bytes.length];
            okHttpUrlSource.read(bArr);
            boolean equals = Arrays.equals(bytes, bArr);
            Logger logger = LOG;
            logger.info("Ping response: `" + new String(bArr) + "`, pinged? " + equals);
            return equals;
        } catch (ProxyCacheException e) {
            LOG.error("Error reading ping response", (Throwable) e);
            return false;
        } finally {
            okHttpUrlSource.close();
        }
    }

    private String getPingUrl() {
        return String.format(Locale.US, "http://%s:%d/%s", this.host, Integer.valueOf(this.port), "ping");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class PingCallable implements Callable<Boolean> {
        private PingCallable() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        /* renamed from: call */
        public Boolean mo5907call() throws Exception {
            return Boolean.valueOf(Pinger.this.pingServer());
        }
    }
}
