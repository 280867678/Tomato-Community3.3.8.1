package io.reactivex.internal.util;

import io.reactivex.functions.BiFunction;
import java.util.List;

/* loaded from: classes4.dex */
public enum ListAddBiConsumer implements BiFunction<List, Object, List> {
    INSTANCE;

    public static <T> BiFunction<List<T>, T, List<T>> instance() {
        return INSTANCE;
    }

    @Override // io.reactivex.functions.BiFunction
    /* renamed from: apply  reason: avoid collision after fix types in other method */
    public List mo6745apply(List list, Object obj) throws Exception {
        list.add(obj);
        return list;
    }
}
