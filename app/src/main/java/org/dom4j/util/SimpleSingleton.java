package org.dom4j.util;

/* loaded from: classes4.dex */
public class SimpleSingleton<T> implements SingletonStrategy<T> {
    private String singletonClassName = null;
    private T singletonInstance = null;

    @Override // org.dom4j.util.SingletonStrategy
    public T instance() {
        return this.singletonInstance;
    }

    @Override // org.dom4j.util.SingletonStrategy
    public void reset() {
        if (this.singletonClassName != null) {
            try {
                try {
                    this.singletonInstance = (T) Thread.currentThread().getContextClassLoader().loadClass(this.singletonClassName).newInstance();
                } catch (Exception unused) {
                    this.singletonInstance = (T) Class.forName(this.singletonClassName).newInstance();
                }
            } catch (Exception unused2) {
            }
        }
    }

    @Override // org.dom4j.util.SingletonStrategy
    public void setSingletonClassName(String str) {
        this.singletonClassName = str;
        reset();
    }
}
