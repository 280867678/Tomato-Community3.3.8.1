package com.tomatolive.library.utils.litepal.crud.async;

import com.tomatolive.library.utils.litepal.crud.callback.UpdateOrDeleteCallback;

/* loaded from: classes4.dex */
public class UpdateOrDeleteExecutor extends AsyncExecutor {

    /* renamed from: cb */
    private UpdateOrDeleteCallback f5883cb;

    public void listen(UpdateOrDeleteCallback updateOrDeleteCallback) {
        this.f5883cb = updateOrDeleteCallback;
        execute();
    }

    public UpdateOrDeleteCallback getListener() {
        return this.f5883cb;
    }
}
