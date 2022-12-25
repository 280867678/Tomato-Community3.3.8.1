package org.dom4j.util;

import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class PerThreadSingleton<T> implements SingletonStrategy<T> {
    private String singletonClassName = null;
    private ThreadLocal<WeakReference<T>> perThreadCache = new ThreadLocal<>();

    @Override // org.dom4j.util.SingletonStrategy
    public void reset() {
        this.perThreadCache = new ThreadLocal<>();
    }

    @Override // org.dom4j.util.SingletonStrategy
    public T instance() {
        T t;
        WeakReference<T> weakReference = this.perThreadCache.get();
        if (weakReference == null || weakReference.get() == null) {
            try {
                try {
                    t = (T) Thread.currentThread().getContextClassLoader().loadClass(this.singletonClassName).newInstance();
                } catch (Exception unused) {
                    t = (T) Class.forName(this.singletonClassName).newInstance();
                }
            } catch (Exception unused2) {
                t = null;
            }
            this.perThreadCache.set(new WeakReference<>(t));
            return t;
        }
        return weakReference.get();
    }

    @Override // org.dom4j.util.SingletonStrategy
    public void setSingletonClassName(String str) {
        this.singletonClassName = str;
    }
}
