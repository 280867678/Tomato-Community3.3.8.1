package org.xutils.http.loader;

import android.text.TextUtils;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import org.xutils.cache.DiskCacheEntity;
import org.xutils.common.util.IOUtil;
import org.xutils.common.util.ParameterizedTypeUtil;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpResponse;
import org.xutils.http.app.InputStreamResponseParser;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class ObjectLoader extends Loader<Object> {
    private final Class<?> objectClass;
    private final Type objectType;
    private final ResponseParser parser;
    private String charset = "UTF-8";
    private String resultStr = null;

    public ObjectLoader(Type type) {
        RuntimeException runtimeException;
        Class cls;
        this.objectType = type;
        if (type instanceof ParameterizedType) {
            this.objectClass = (Class) ((ParameterizedType) type).getRawType();
        } else if (type instanceof TypeVariable) {
            throw new IllegalArgumentException("not support callback type " + type.toString());
        } else {
            this.objectClass = (Class) type;
        }
        if (List.class.equals(this.objectClass)) {
            Type parameterizedType = ParameterizedTypeUtil.getParameterizedType(this.objectType, List.class, 0);
            if (parameterizedType instanceof ParameterizedType) {
                cls = (Class) ((ParameterizedType) parameterizedType).getRawType();
            } else if (parameterizedType instanceof TypeVariable) {
                throw new IllegalArgumentException("not support callback type " + parameterizedType.toString());
            } else {
                cls = (Class) parameterizedType;
            }
            HttpResponse httpResponse = (HttpResponse) cls.getAnnotation(HttpResponse.class);
            if (httpResponse != null) {
                try {
                    this.parser = httpResponse.parser().newInstance();
                    return;
                } finally {
                }
            }
            throw new IllegalArgumentException("not found @HttpResponse from " + parameterizedType);
        }
        HttpResponse httpResponse2 = (HttpResponse) this.objectClass.getAnnotation(HttpResponse.class);
        if (httpResponse2 != null) {
            try {
                this.parser = httpResponse2.parser().newInstance();
                return;
            } finally {
            }
        }
        throw new IllegalArgumentException("not found @HttpResponse from " + this.objectType);
    }

    @Override // org.xutils.http.loader.Loader
    public Loader<Object> newInstance() {
        throw new IllegalAccessError("use constructor create ObjectLoader.");
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
    /* renamed from: load */
    public Object mo6877load(InputStream inputStream) throws Throwable {
        ResponseParser responseParser = this.parser;
        if (responseParser instanceof InputStreamResponseParser) {
            return ((InputStreamResponseParser) responseParser).parse(this.objectType, this.objectClass, inputStream);
        }
        this.resultStr = IOUtil.readStr(inputStream, this.charset);
        return this.parser.parse(this.objectType, this.objectClass, this.resultStr);
    }

    @Override // org.xutils.http.loader.Loader
    /* renamed from: load */
    public Object mo6878load(UriRequest uriRequest) throws Throwable {
        try {
            uriRequest.sendRequest();
            this.parser.checkResponse(uriRequest);
            return mo6877load(uriRequest.getInputStream());
        } catch (Throwable th) {
            this.parser.checkResponse(uriRequest);
            throw th;
        }
    }

    @Override // org.xutils.http.loader.Loader
    /* renamed from: loadFromCache */
    public Object mo6879loadFromCache(DiskCacheEntity diskCacheEntity) throws Throwable {
        if (diskCacheEntity != null) {
            String textContent = diskCacheEntity.getTextContent();
            if (TextUtils.isEmpty(textContent)) {
                return null;
            }
            return this.parser.parse(this.objectType, this.objectClass, textContent);
        }
        return null;
    }

    @Override // org.xutils.http.loader.Loader
    public void save2Cache(UriRequest uriRequest) {
        saveStringCache(uriRequest, this.resultStr);
    }
}
