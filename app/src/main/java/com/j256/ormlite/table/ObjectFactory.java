package com.j256.ormlite.table;

import java.lang.reflect.Constructor;

/* loaded from: classes3.dex */
public interface ObjectFactory<T> {
    T createObject(Constructor<T> constructor, Class<T> cls);
}
