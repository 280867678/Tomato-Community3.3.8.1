package com.alipay.android.phone.mrpc.core;

import android.os.Looper;
import com.alipay.android.phone.mrpc.core.p040a.C0882d;
import com.alipay.android.phone.mrpc.core.p040a.C0883e;
import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.framework.service.annotation.ResetCookie;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.alipay.android.phone.mrpc.core.z */
/* loaded from: classes2.dex */
public final class C0916z {

    /* renamed from: a */
    private static final ThreadLocal<Object> f837a = new ThreadLocal<>();

    /* renamed from: b */
    private static final ThreadLocal<Map<String, Object>> f838b = new ThreadLocal<>();

    /* renamed from: c */
    private byte f839c = 0;

    /* renamed from: d */
    private AtomicInteger f840d = new AtomicInteger();

    /* renamed from: e */
    private C0914x f841e;

    public C0916z(C0914x c0914x) {
        this.f841e = c0914x;
    }

    /* renamed from: a */
    public final Object m4794a(Method method, Object[] objArr) {
        if (!(Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper())) {
            OperationType operationType = (OperationType) method.getAnnotation(OperationType.class);
            boolean z = method.getAnnotation(ResetCookie.class) != null;
            Type genericReturnType = method.getGenericReturnType();
            method.getAnnotations();
            f837a.set(null);
            f838b.set(null);
            if (operationType == null) {
                throw new IllegalStateException("OperationType must be set.");
            }
            String value = operationType.value();
            int incrementAndGet = this.f840d.incrementAndGet();
            try {
                if (this.f839c == 0) {
                    C0883e c0883e = new C0883e(incrementAndGet, value, objArr);
                    if (f838b.get() != null) {
                        c0883e.mo4870a(f838b.get());
                    }
                    byte[] mo4871a = c0883e.mo4871a();
                    f838b.set(null);
                    Object mo4872a = new C0882d(genericReturnType, (byte[]) new C0899j(this.f841e.m4796a(), method, incrementAndGet, value, mo4871a, z).mo4798a()).mo4872a();
                    if (genericReturnType != Void.TYPE) {
                        f837a.set(mo4872a);
                    }
                }
                return f837a.get();
            } catch (RpcException e) {
                e.setOperationType(value);
                throw e;
            }
        }
        throw new IllegalThreadStateException("can't in main thread call rpc .");
    }
}
