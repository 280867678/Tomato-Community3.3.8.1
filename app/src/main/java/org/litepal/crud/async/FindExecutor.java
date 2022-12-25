package org.litepal.crud.async;

import org.litepal.crud.callback.FindCallback;

/* loaded from: classes4.dex */
public class FindExecutor<T> extends AsyncExecutor {

    /* renamed from: cb */
    private FindCallback<T> f6059cb;

    public void listen(FindCallback<T> findCallback) {
        this.f6059cb = findCallback;
        execute();
    }

    public FindCallback<T> getListener() {
        return this.f6059cb;
    }
}
