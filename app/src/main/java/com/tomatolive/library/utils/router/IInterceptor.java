package com.tomatolive.library.utils.router;

import android.content.Context;
import android.content.Intent;
import com.tomatolive.library.utils.router.callbacks.InterceptorCallback;

/* loaded from: classes4.dex */
public interface IInterceptor {
    void process(String str, Intent intent, Context context, InterceptorCallback interceptorCallback);
}
