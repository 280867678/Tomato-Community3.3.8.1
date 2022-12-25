package org.litepal.crud.async;

import org.litepal.crud.callback.CountCallback;

/* loaded from: classes4.dex */
public class CountExecutor extends AsyncExecutor {

    /* renamed from: cb */
    private CountCallback f6058cb;

    public void listen(CountCallback countCallback) {
        this.f6058cb = countCallback;
        execute();
    }

    public CountCallback getListener() {
        return this.f6058cb;
    }
}
