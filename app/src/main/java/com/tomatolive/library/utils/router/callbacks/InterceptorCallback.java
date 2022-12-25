package com.tomatolive.library.utils.router.callbacks;

import android.content.Intent;

/* loaded from: classes4.dex */
public interface InterceptorCallback {
    void onContinue(Intent intent);

    void onInterrupt(Throwable th);
}
