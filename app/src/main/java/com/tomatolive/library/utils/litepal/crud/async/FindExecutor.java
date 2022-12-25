package com.tomatolive.library.utils.litepal.crud.async;

import com.tomatolive.library.utils.litepal.crud.callback.FindCallback;

/* loaded from: classes4.dex */
public class FindExecutor<T> extends AsyncExecutor {

    /* renamed from: cb */
    private FindCallback<T> f5880cb;

    public void listen(FindCallback<T> findCallback) {
        this.f5880cb = findCallback;
        execute();
    }

    public FindCallback<T> getListener() {
        return this.f5880cb;
    }
}
