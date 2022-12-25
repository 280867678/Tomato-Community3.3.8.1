package com.eclipsesource.p056v8.utils;

import com.eclipsesource.p056v8.C1257V8;

/* renamed from: com.eclipsesource.v8.utils.ConcurrentV8 */
/* loaded from: classes2.dex */
public final class ConcurrentV8 {

    /* renamed from: v8 */
    private C1257V8 f1246v8;

    public ConcurrentV8() {
        this.f1246v8 = null;
        this.f1246v8 = C1257V8.createV8Runtime();
        this.f1246v8.getLocker().release();
    }

    public C1257V8 getV8() {
        return this.f1246v8;
    }

    public synchronized void run(V8Runnable v8Runnable) {
        this.f1246v8.getLocker().acquire();
        v8Runnable.run(this.f1246v8);
        if (this.f1246v8 != null && this.f1246v8.getLocker() != null && this.f1246v8.getLocker().hasLock()) {
            this.f1246v8.getLocker().release();
        }
    }

    public void release() {
        C1257V8 c1257v8 = this.f1246v8;
        if (c1257v8 == null || c1257v8.isReleased()) {
            return;
        }
        run(new V8Runnable() { // from class: com.eclipsesource.v8.utils.ConcurrentV8.1
            @Override // com.eclipsesource.p056v8.utils.V8Runnable
            public void run(C1257V8 c1257v82) {
                if (c1257v82 == null || c1257v82.isReleased()) {
                    return;
                }
                c1257v82.close();
            }
        });
    }
}
