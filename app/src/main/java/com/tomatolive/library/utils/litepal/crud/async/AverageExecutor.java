package com.tomatolive.library.utils.litepal.crud.async;

import com.tomatolive.library.utils.litepal.crud.callback.AverageCallback;

/* loaded from: classes4.dex */
public class AverageExecutor extends AsyncExecutor {

    /* renamed from: cb */
    private AverageCallback f5878cb;

    public void listen(AverageCallback averageCallback) {
        this.f5878cb = averageCallback;
        execute();
    }

    public AverageCallback getListener() {
        return this.f5878cb;
    }
}
