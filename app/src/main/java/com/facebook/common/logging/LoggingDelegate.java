package com.facebook.common.logging;

/* loaded from: classes2.dex */
public interface LoggingDelegate {
    /* renamed from: d */
    void mo4137d(String str, String str2);

    /* renamed from: d */
    void mo4136d(String str, String str2, Throwable th);

    /* renamed from: e */
    void mo4135e(String str, String str2);

    /* renamed from: e */
    void mo4134e(String str, String str2, Throwable th);

    boolean isLoggable(int i);

    /* renamed from: v */
    void mo4133v(String str, String str2);

    /* renamed from: w */
    void mo4132w(String str, String str2);

    /* renamed from: w */
    void mo4131w(String str, String str2, Throwable th);

    void wtf(String str, String str2);

    void wtf(String str, String str2, Throwable th);
}
