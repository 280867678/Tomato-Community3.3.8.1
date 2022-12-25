package org.litepal.crud.async;

import org.litepal.crud.callback.UpdateOrDeleteCallback;

/* loaded from: classes4.dex */
public class UpdateOrDeleteExecutor extends AsyncExecutor {

    /* renamed from: cb */
    private UpdateOrDeleteCallback f6062cb;

    public void listen(UpdateOrDeleteCallback updateOrDeleteCallback) {
        this.f6062cb = updateOrDeleteCallback;
        execute();
    }

    public UpdateOrDeleteCallback getListener() {
        return this.f6062cb;
    }
}
