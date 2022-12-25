package org.dom4j.util;

/* loaded from: classes4.dex */
public interface SingletonStrategy<T> {
    T instance();

    void reset();

    void setSingletonClassName(String str);
}
