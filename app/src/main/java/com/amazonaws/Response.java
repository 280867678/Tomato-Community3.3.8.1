package com.amazonaws;

import com.amazonaws.http.HttpResponse;

/* loaded from: classes2.dex */
public final class Response<T> {
    private final T response;

    public Response(T t, HttpResponse httpResponse) {
        this.response = t;
    }

    public T getAwsResponse() {
        return this.response;
    }
}
