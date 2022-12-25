package org.xutils.http.loader;

import android.text.TextUtils;
import java.io.InputStream;
import org.json.JSONObject;
import org.xutils.cache.DiskCacheEntity;
import org.xutils.common.util.IOUtil;
import org.xutils.http.RequestParams;
import org.xutils.http.request.UriRequest;

/* loaded from: classes4.dex */
class JSONObjectLoader extends Loader<JSONObject> {
    private String charset = "UTF-8";
    private String resultStr = null;

    @Override // org.xutils.http.loader.Loader
    public Loader<JSONObject> newInstance() {
        return new JSONObjectLoader();
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
    public JSONObject mo6877load(InputStream inputStream) throws Throwable {
        this.resultStr = IOUtil.readStr(inputStream, this.charset);
        return new JSONObject(this.resultStr);
    }

    @Override // org.xutils.http.loader.Loader
    /* renamed from: load  reason: collision with other method in class */
    public JSONObject mo6878load(UriRequest uriRequest) throws Throwable {
        uriRequest.sendRequest();
        return mo6877load(uriRequest.getInputStream());
    }

    @Override // org.xutils.http.loader.Loader
    /* renamed from: loadFromCache  reason: collision with other method in class */
    public JSONObject mo6879loadFromCache(DiskCacheEntity diskCacheEntity) throws Throwable {
        if (diskCacheEntity != null) {
            String textContent = diskCacheEntity.getTextContent();
            if (TextUtils.isEmpty(textContent)) {
                return null;
            }
            return new JSONObject(textContent);
        }
        return null;
    }

    @Override // org.xutils.http.loader.Loader
    public void save2Cache(UriRequest uriRequest) {
        saveStringCache(uriRequest, this.resultStr);
    }
}
