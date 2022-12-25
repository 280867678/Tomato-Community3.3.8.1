package com.alipay.apmobilesecuritysdk.p046f;

import android.os.Process;
import java.util.LinkedList;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.apmobilesecuritysdk.f.c */
/* loaded from: classes2.dex */
public final class RunnableC0938c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C0937b f872a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RunnableC0938c(C0937b c0937b) {
        this.f872a = c0937b;
    }

    /* JADX WARN: Incorrect condition in loop: B:5:0x000e */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        LinkedList linkedList;
        LinkedList linkedList2;
        LinkedList linkedList3;
        try {
            Process.setThreadPriority(0);
            while (!linkedList.isEmpty()) {
                linkedList2 = this.f872a.f871c;
                Runnable runnable = (Runnable) linkedList2.get(0);
                linkedList3 = this.f872a.f871c;
                linkedList3.remove(0);
                if (runnable != null) {
                    runnable.run();
                }
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            this.f872a.f870b = null;
            throw th;
        }
        this.f872a.f870b = null;
    }
}
