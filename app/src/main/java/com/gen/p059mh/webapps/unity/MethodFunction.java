package com.gen.p059mh.webapps.unity;

import com.gen.p059mh.webapps.unity.Unity;

/* renamed from: com.gen.mh.webapps.unity.MethodFunction */
/* loaded from: classes2.dex */
public class MethodFunction extends Function {
    Unity.Method method;

    public <T> MethodFunction(Unity.Method<T> method) {
        this.method = method;
        registerMethod("invoke", method);
    }

    @Override // com.gen.p059mh.webapps.unity.Function
    public void invoke(Unity.MethodCallback methodCallback, Object... objArr) {
        this.method.call(methodCallback, objArr);
    }
}
