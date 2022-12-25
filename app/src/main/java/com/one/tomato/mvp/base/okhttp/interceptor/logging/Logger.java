package com.one.tomato.mvp.base.okhttp.interceptor.logging;

import okhttp3.internal.platform.Platform;

/* loaded from: classes3.dex */
public interface Logger {
    void log(int i, String str, String str2);

    static {
        new Logger() { // from class: com.one.tomato.mvp.base.okhttp.interceptor.logging.Logger.1
            @Override // com.one.tomato.mvp.base.okhttp.interceptor.logging.Logger
            public void log(int i, String str, String str2) {
                Platform.get().log(i, str2, null);
            }
        };
    }
}
