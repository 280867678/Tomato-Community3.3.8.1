package com.tomatolive.library.utils.litepal.crud.async;

import com.tomatolive.library.utils.litepal.crud.callback.FindMultiCallback;

/* loaded from: classes4.dex */
public class FindMultiExecutor<T> extends AsyncExecutor {

    /* renamed from: cb */
    private FindMultiCallback<T> f5881cb;

    public void listen(FindMultiCallback<T> findMultiCallback) {
        this.f5881cb = findMultiCallback;
        execute();
    }

    public FindMultiCallback<T> getListener() {
        return this.f5881cb;
    }
}
