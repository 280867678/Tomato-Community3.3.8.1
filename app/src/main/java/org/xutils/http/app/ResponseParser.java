package org.xutils.http.app;

import java.lang.reflect.Type;
import org.xutils.http.request.UriRequest;

/* loaded from: classes4.dex */
public interface ResponseParser {
    void checkResponse(UriRequest uriRequest) throws Throwable;

    Object parse(Type type, Class<?> cls, String str) throws Throwable;
}
