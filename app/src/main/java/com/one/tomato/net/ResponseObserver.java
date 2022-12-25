package com.one.tomato.net;

import android.os.Message;

/* loaded from: classes3.dex */
public interface ResponseObserver {
    boolean handleHttpRequestError(Message message);

    boolean handleRequestCancel(Message message);

    void handleResponse(Message message);

    boolean handleResponseError(Message message);
}
