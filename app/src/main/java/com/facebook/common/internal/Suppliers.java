package com.facebook.common.internal;

/* loaded from: classes2.dex */
public class Suppliers {
    /* renamed from: of */
    public static <T> Supplier<T> m4160of(final T t) {
        return new Supplier<T>() { // from class: com.facebook.common.internal.Suppliers.1
            /* JADX WARN: Type inference failed for: r0v0, types: [T, java.lang.Object] */
            @Override // com.facebook.common.internal.Supplier
            /* renamed from: get */
            public T mo5939get() {
                return t;
            }
        };
    }

    static {
        new Supplier<Boolean>() { // from class: com.facebook.common.internal.Suppliers.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.facebook.common.internal.Supplier
            /* renamed from: get */
            public Boolean mo5939get() {
                return true;
            }
        };
        new Supplier<Boolean>() { // from class: com.facebook.common.internal.Suppliers.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.facebook.common.internal.Supplier
            /* renamed from: get */
            public Boolean mo5939get() {
                return false;
            }
        };
    }
}
