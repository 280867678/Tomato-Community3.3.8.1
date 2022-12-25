package org.xutils.http.request;

import android.text.TextUtils;
import java.lang.reflect.Type;
import java.util.HashMap;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.http.app.RequestTracker;

/* loaded from: classes4.dex */
public final class UriRequestFactory {
    private static final HashMap<String, Class<? extends UriRequest>> SCHEME_CLS_MAP = new HashMap<>();
    private static Class<? extends RequestTracker> defaultTrackerCls;

    private UriRequestFactory() {
    }

    public static UriRequest getUriRequest(RequestParams requestParams, Type type) throws Throwable {
        String str;
        String uri = requestParams.getUri();
        int indexOf = uri.indexOf(":");
        if (indexOf > 0) {
            str = uri.substring(0, indexOf);
        } else {
            str = uri.startsWith("/") ? "file" : null;
        }
        if (!TextUtils.isEmpty(str)) {
            Class<? extends UriRequest> cls = SCHEME_CLS_MAP.get(str);
            if (cls != null) {
                return cls.getConstructor(RequestParams.class, Class.class).newInstance(requestParams, type);
            }
            if (str.startsWith("http")) {
                return new HttpRequest(requestParams, type);
            }
            if (str.equals("assets")) {
                return new AssetsRequest(requestParams, type);
            }
            if (str.equals("file")) {
                return new LocalFileRequest(requestParams, type);
            }
            throw new IllegalArgumentException("The url not be support: " + uri);
        }
        throw new IllegalArgumentException("The url not be support: " + uri);
    }

    public static void registerDefaultTrackerClass(Class<? extends RequestTracker> cls) {
        defaultTrackerCls = cls;
    }

    public static RequestTracker getDefaultTracker() {
        try {
            if (defaultTrackerCls != null) {
                return defaultTrackerCls.newInstance();
            }
            return null;
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
            return null;
        }
    }

    public static void registerRequestClass(String str, Class<? extends UriRequest> cls) {
        SCHEME_CLS_MAP.put(str, cls);
    }
}
