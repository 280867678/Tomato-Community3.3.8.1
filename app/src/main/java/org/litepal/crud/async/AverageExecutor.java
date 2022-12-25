package org.litepal.crud.async;

import org.litepal.crud.callback.AverageCallback;

/* loaded from: classes4.dex */
public class AverageExecutor extends AsyncExecutor {

    /* renamed from: cb */
    private AverageCallback f6057cb;

    public void listen(AverageCallback averageCallback) {
        this.f6057cb = averageCallback;
        execute();
    }

    public AverageCallback getListener() {
        return this.f6057cb;
    }
}
