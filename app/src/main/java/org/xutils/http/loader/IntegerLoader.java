package org.xutils.http.loader;

import java.io.InputStream;
import org.xutils.cache.DiskCacheEntity;
import org.xutils.http.request.UriRequest;

/* loaded from: classes4.dex */
class IntegerLoader extends Loader<Integer> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.http.loader.Loader
    /* renamed from: loadFromCache */
    public Integer mo6879loadFromCache(DiskCacheEntity diskCacheEntity) throws Throwable {
        return null;
    }

    @Override // org.xutils.http.loader.Loader
    public void save2Cache(UriRequest uriRequest) {
    }

    @Override // org.xutils.http.loader.Loader
    public Loader<Integer> newInstance() {
        return new IntegerLoader();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.http.loader.Loader
    /* renamed from: load */
    public Integer mo6877load(InputStream inputStream) throws Throwable {
        return 100;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.http.loader.Loader
    /* renamed from: load */
    public Integer mo6878load(UriRequest uriRequest) throws Throwable {
        uriRequest.sendRequest();
        return Integer.valueOf(uriRequest.getResponseCode());
    }
}
