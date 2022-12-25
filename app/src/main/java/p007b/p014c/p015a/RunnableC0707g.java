package p007b.p014c.p015a;

import android.util.Log;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: b.c.a.g */
/* loaded from: classes2.dex */
public class RunnableC0707g implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ C0604G f352a;

    public RunnableC0707g(C0604G c0604g) {
        this.f352a = c0604g;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.f352a.m5482e();
        } catch (Exception unused) {
            Log.i("NIO", "Selector Exception? L Preview?");
        }
    }
}
