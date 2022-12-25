package com.fasterxml.jackson.databind.ser;

/* loaded from: classes2.dex */
public abstract class FilterProvider {
    @Deprecated
    public abstract BeanPropertyFilter findFilter(Object obj);

    public abstract PropertyFilter findPropertyFilter(Object obj, Object obj2);
}
