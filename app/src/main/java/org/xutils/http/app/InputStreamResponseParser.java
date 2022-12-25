package org.xutils.http.app;

import java.io.InputStream;
import java.lang.reflect.Type;

/* loaded from: classes4.dex */
public abstract class InputStreamResponseParser implements ResponseParser {
    public abstract Object parse(Type type, Class<?> cls, InputStream inputStream) throws Throwable;

    @Override // org.xutils.http.app.ResponseParser
    @Deprecated
    public final Object parse(Type type, Class<?> cls, String str) throws Throwable {
        return null;
    }
}
