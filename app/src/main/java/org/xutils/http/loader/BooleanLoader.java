package org.xutils.http.loader;

import java.io.InputStream;
import org.xutils.cache.DiskCacheEntity;
import org.xutils.http.request.UriRequest;

/* loaded from: classes4.dex */
class BooleanLoader extends Loader<Boolean> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.http.loader.Loader
    /* renamed from: loadFromCache */
    public Boolean mo6879loadFromCache(DiskCacheEntity diskCacheEntity) throws Throwable {
        return null;
    }

    @Override // org.xutils.http.loader.Loader
    public void save2Cache(UriRequest uriRequest) {
    }

    @Override // org.xutils.http.loader.Loader
    public Loader<Boolean> newInstance() {
        return new BooleanLoader();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.http.loader.Loader
    /* renamed from: load */
    public Boolean mo6877load(InputStream inputStream) throws Throwable {
        return false;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.http.loader.Loader
    /* renamed from: load */
    public Boolean mo6878load(UriRequest uriRequest) throws Throwable {
        uriRequest.sendRequest();
        return Boolean.valueOf(uriRequest.getResponseCode() < 300);
    }
}
