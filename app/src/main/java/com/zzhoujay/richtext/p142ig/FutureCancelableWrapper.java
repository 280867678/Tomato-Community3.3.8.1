package com.zzhoujay.richtext.p142ig;

import java.util.concurrent.Future;

/* renamed from: com.zzhoujay.richtext.ig.FutureCancelableWrapper */
/* loaded from: classes4.dex */
class FutureCancelableWrapper implements Cancelable {
    private Future future;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FutureCancelableWrapper(Future future) {
        this.future = future;
    }

    @Override // com.zzhoujay.richtext.p142ig.Cancelable
    public void cancel() {
        Future future = this.future;
        if (future == null || future.isDone() || this.future.isCancelled()) {
            return;
        }
        this.future.cancel(true);
        this.future = null;
    }
}
