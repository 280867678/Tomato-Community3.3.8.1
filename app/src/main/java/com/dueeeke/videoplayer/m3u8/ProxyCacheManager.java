package com.dueeeke.videoplayer.m3u8;

import android.content.Context;
import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.StorageUtils;
import com.danikula.videocache.headers.HeaderInjector;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import timber.log.Timber;

/* loaded from: classes2.dex */
public class ProxyCacheManager {
    private static ProxyCacheManager proxyCacheManager;
    protected File mCacheDir;
    protected boolean mCacheFile;
    protected Map<String, String> mMapHeadData;

    public static synchronized ProxyCacheManager instance() {
        ProxyCacheManager proxyCacheManager2;
        synchronized (ProxyCacheManager.class) {
            if (proxyCacheManager == null) {
                proxyCacheManager = new ProxyCacheManager();
                Timber.plant(new ReleaseTree());
            }
            proxyCacheManager2 = proxyCacheManager;
        }
        return proxyCacheManager2;
    }

    public HttpProxyCacheServer getProxy(Context context, boolean z, String str, String str2) {
        File individualCacheDirectory;
        if (z) {
            individualCacheDirectory = StorageUtils.getVideoDownloadDir(context);
        } else {
            individualCacheDirectory = StorageUtils.getIndividualCacheDirectory(context);
        }
        return newProxy(context, individualCacheDirectory, str, str2);
    }

    private HttpProxyCacheServer newProxy(Context context, File file, String str, String str2) {
        if (!file.exists()) {
            file.mkdirs();
        }
        HttpProxyCacheServer.Builder builder = new HttpProxyCacheServer.Builder(context);
        builder.cacheDirectory(file);
        builder.headerInjector(new UserAgentHeadersInjector());
        builder.cdnUrl(str);
        builder.ipfsUrl(str2);
        this.mCacheDir = file;
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class UserAgentHeadersInjector implements HeaderInjector {
        private UserAgentHeadersInjector() {
        }

        public Map<String, String> addHeaders(String str) {
            Map<String, String> map = ProxyCacheManager.this.mMapHeadData;
            return map == null ? new HashMap() : map;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class ReleaseTree extends Timber.Tree {
        @Override // timber.log.Timber.Tree
        protected void log(int i, String str, String str2, Throwable th) {
        }

        private ReleaseTree() {
        }
    }
}
