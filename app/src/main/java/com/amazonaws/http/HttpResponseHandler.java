package com.amazonaws.http;

/* loaded from: classes2.dex */
public interface HttpResponseHandler<T> {
    /* renamed from: handle */
    T mo5855handle(HttpResponse httpResponse) throws Exception;

    boolean needsConnectionLeftOpen();
}
