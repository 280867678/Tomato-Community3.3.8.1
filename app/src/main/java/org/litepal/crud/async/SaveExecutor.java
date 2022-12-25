package org.litepal.crud.async;

import org.litepal.crud.callback.SaveCallback;

/* loaded from: classes4.dex */
public class SaveExecutor extends AsyncExecutor {

    /* renamed from: cb */
    private SaveCallback f6061cb;

    public void listen(SaveCallback saveCallback) {
        this.f6061cb = saveCallback;
        execute();
    }

    public SaveCallback getListener() {
        return this.f6061cb;
    }
}
