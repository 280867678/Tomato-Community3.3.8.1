package org.xutils.http.loader;

import java.io.InputStream;
import org.xutils.cache.DiskCacheEntity;
import org.xutils.common.util.IOUtil;
import org.xutils.http.request.UriRequest;

/* loaded from: classes4.dex */
class ByteArrayLoader extends Loader<byte[]> {
    @Override // org.xutils.http.loader.Loader
    /* renamed from: loadFromCache  reason: collision with other method in class */
    public byte[] mo6879loadFromCache(DiskCacheEntity diskCacheEntity) throws Throwable {
        return null;
    }

    @Override // org.xutils.http.loader.Loader
    public void save2Cache(UriRequest uriRequest) {
    }

    @Override // org.xutils.http.loader.Loader
    public Loader<byte[]> newInstance() {
        return new ByteArrayLoader();
    }

    @Override // org.xutils.http.loader.Loader
    /* renamed from: load  reason: collision with other method in class */
    public byte[] mo6877load(InputStream inputStream) throws Throwable {
        return IOUtil.readBytes(inputStream);
    }

    @Override // org.xutils.http.loader.Loader
    /* renamed from: load  reason: collision with other method in class */
    public byte[] mo6878load(UriRequest uriRequest) throws Throwable {
        uriRequest.sendRequest();
        return mo6877load(uriRequest.getInputStream());
    }
}
