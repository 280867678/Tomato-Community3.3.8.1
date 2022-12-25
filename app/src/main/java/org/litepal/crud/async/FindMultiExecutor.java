package org.litepal.crud.async;

import org.litepal.crud.callback.FindMultiCallback;

/* loaded from: classes4.dex */
public class FindMultiExecutor<T> extends AsyncExecutor {

    /* renamed from: cb */
    private FindMultiCallback<T> f6060cb;

    public void listen(FindMultiCallback<T> findMultiCallback) {
        this.f6060cb = findMultiCallback;
        execute();
    }

    public FindMultiCallback<T> getListener() {
        return this.f6060cb;
    }
}
