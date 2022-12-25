package com.eclipsesource.p056v8.utils;

import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.ReferenceHandler;
import com.eclipsesource.p056v8.V8Value;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: com.eclipsesource.v8.utils.MemoryManager */
/* loaded from: classes2.dex */
public class MemoryManager {

    /* renamed from: v8 */
    private C1257V8 f1247v8;
    private ArrayList<V8Value> references = new ArrayList<>();
    private boolean releasing = false;
    private boolean released = false;
    private MemoryManagerReferenceHandler memoryManagerReferenceHandler = new MemoryManagerReferenceHandler();

    public MemoryManager(C1257V8 c1257v8) {
        this.f1247v8 = c1257v8;
        c1257v8.addReferenceHandler(this.memoryManagerReferenceHandler);
    }

    public int getObjectReferenceCount() {
        checkReleased();
        return this.references.size();
    }

    public void persist(V8Value v8Value) {
        this.f1247v8.getLocker().checkThread();
        checkReleased();
        this.references.remove(v8Value);
    }

    public boolean isReleased() {
        return this.released;
    }

    public void release() {
        this.f1247v8.getLocker().checkThread();
        if (this.released) {
            return;
        }
        this.releasing = true;
        try {
            Iterator<V8Value> it2 = this.references.iterator();
            while (it2.hasNext()) {
                it2.next().close();
            }
            this.f1247v8.removeReferenceHandler(this.memoryManagerReferenceHandler);
            this.references.clear();
            this.releasing = false;
            this.released = true;
        } catch (Throwable th) {
            this.releasing = false;
            throw th;
        }
    }

    private void checkReleased() {
        if (!this.released) {
            return;
        }
        throw new IllegalStateException("Memory manager released");
    }

    /* renamed from: com.eclipsesource.v8.utils.MemoryManager$MemoryManagerReferenceHandler */
    /* loaded from: classes2.dex */
    private class MemoryManagerReferenceHandler implements ReferenceHandler {
        private MemoryManagerReferenceHandler() {
        }

        @Override // com.eclipsesource.p056v8.ReferenceHandler
        public void v8HandleCreated(V8Value v8Value) {
            MemoryManager.this.references.add(v8Value);
        }

        @Override // com.eclipsesource.p056v8.ReferenceHandler
        public void v8HandleDisposed(V8Value v8Value) {
            if (!MemoryManager.this.releasing) {
                Iterator it2 = MemoryManager.this.references.iterator();
                while (it2.hasNext()) {
                    if (it2.next() == v8Value) {
                        it2.remove();
                        return;
                    }
                }
            }
        }
    }
}
