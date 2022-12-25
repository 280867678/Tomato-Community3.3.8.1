package org.xutils.http.loader;

import android.text.TextUtils;
import java.io.InputStream;
import org.json.JSONArray;
import org.xutils.cache.DiskCacheEntity;
import org.xutils.common.util.IOUtil;
import org.xutils.http.RequestParams;
import org.xutils.http.request.UriRequest;

/* loaded from: classes4.dex */
class JSONArrayLoader extends Loader<JSONArray> {
    private String charset = "UTF-8";
    private String resultStr = null;

    @Override // org.xutils.http.loader.Loader
    public Loader<JSONArray> newInstance() {
        return new JSONArrayLoader();
    }

    @Override // org.xutils.http.loader.Loader
    public void setParams(RequestParams requestParams) {
        if (requestParams != null) {
            String charset = requestParams.getCharset();
            if (TextUtils.isEmpty(charset)) {
                return;
            }
            this.charset = charset;
        }
    }

    @Override // org.xutils.http.loader.Loader
    /* renamed from: load  reason: collision with other method in class */
    public JSONArray mo6877load(InputStream inputStream) throws Throwable {
        this.resultStr = IOUtil.readStr(inputStream, this.charset);
        return new JSONArray(this.resultStr);
    }

    @Override // org.xutils.http.loader.Loader
    /* renamed from: load  reason: collision with other method in class */
    public JSONArray mo6878load(UriRequest uriRequest) throws Throwable {
        uriRequest.sendRequest();
        return mo6877load(uriRequest.getInputStream());
    }

    @Override // org.xutils.http.loader.Loader
    /* renamed from: loadFromCache  reason: collision with other method in class */
    public JSONArray mo6879loadFromCache(DiskCacheEntity diskCacheEntity) throws Throwable {
        if (diskCacheEntity != null) {
            String textContent = diskCacheEntity.getTextContent();
            if (TextUtils.isEmpty(textContent)) {
                return null;
            }
            return new JSONArray(textContent);
        }
        return null;
    }

    @Override // org.xutils.http.loader.Loader
    public void save2Cache(UriRequest uriRequest) {
        saveStringCache(uriRequest, this.resultStr);
    }
}
