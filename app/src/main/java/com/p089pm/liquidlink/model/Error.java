package com.p089pm.liquidlink.model;

/* renamed from: com.pm.liquidlink.model.Error */
/* loaded from: classes3.dex */
public class Error {

    /* renamed from: a */
    private int f1827a;

    /* renamed from: b */
    private String f1828b;

    public Error(int i, String str) {
        this.f1827a = i;
        this.f1828b = str;
    }

    public String toString() {
        return "Error{errorCode=" + this.f1827a + ", errorMsg='" + this.f1828b + "'}";
    }
}
