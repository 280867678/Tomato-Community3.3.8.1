package com.tomatolive.library.utils.litepal.crud.async;

import com.tomatolive.library.utils.litepal.crud.callback.SaveCallback;

/* loaded from: classes4.dex */
public class SaveExecutor extends AsyncExecutor {

    /* renamed from: cb */
    private SaveCallback f5882cb;

    public void listen(SaveCallback saveCallback) {
        this.f5882cb = saveCallback;
        execute();
    }

    public SaveCallback getListener() {
        return this.f5882cb;
    }
}
