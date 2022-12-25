package com.tomatolive.library.utils.litepal.crud.async;

import com.tomatolive.library.utils.litepal.crud.callback.CountCallback;

/* loaded from: classes4.dex */
public class CountExecutor extends AsyncExecutor {

    /* renamed from: cb */
    private CountCallback f5879cb;

    public void listen(CountCallback countCallback) {
        this.f5879cb = countCallback;
        execute();
    }

    public CountCallback getListener() {
        return this.f5879cb;
    }
}
